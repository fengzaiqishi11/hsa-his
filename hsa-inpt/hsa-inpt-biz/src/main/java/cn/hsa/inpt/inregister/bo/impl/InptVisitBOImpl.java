package cn.hsa.inpt.inregister.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.center.message.dao.MessageInfoDAO;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.inpt.advancepay.bo.InptAdvancePayBO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inregister.bo.InptVisitBO;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.module.insure.module.entity.InsureIndividualBasicDO;
import cn.hsa.module.insure.module.service.*;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.inregister.bo.impl
 * @Class_name: InRegister
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/8 15:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class InptVisitBOImpl extends HsafBO implements InptVisitBO {

    @Resource
    InptVisitDAO inptVisitDAO;

    @Resource
    OutptProfileFileService outptProfileFileService_consumer;

    @Resource
    OutptVisitService outptVisitService_consumer;

    @Resource
    InptAdvancePayBO advancePayBO;

    @Resource
    InptAdviceDAO inptAdviceDAO;

    @Resource
    BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    InptService inptService_consumer;

    @Resource
    InsureIndividualBasicService insureIndividualBasicService_consumer;

    @Resource
    InsureIndividualVisitService insureIndividualVisitService_consumer;

    @Resource
    InsureDictService insureDictService_consumer;

    @Resource
    InsureUnifiedPayInptService insureUnifiedPayInptService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private InsureNHPatientService insureNHPatientService_consumer;

    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;
    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService_consumer;

    private static final String JX_ORGCODE = "H36";


    /**
     * @Method queryRegisteredPage
     * @Desrciption 1.查询住院就诊表查询已登记的病人信息
     * 2.通过住院就诊表的档案ID查询出病人的额外信息
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/15 16:31
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryRegisteredPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOS = new ArrayList<>();
        if (StringUtils.isNotEmpty(inptVisitDTO.getType())) {
            if ("1".equals(inptVisitDTO.getType())) {
                inptVisitDTOS = inptVisitDAO.queryInptVisitPageS(inptVisitDTO);
            } else {
                inptVisitDTOS = inptVisitDAO.queryInptVisitPage(inptVisitDTO);
            }
        } else {
            throw new AppException("请传type值，1.入院登记时  2.病人综合查询");
        }

        if (!ListUtils.isEmpty(inptVisitDTOS)) {
            List<String> newProfileIds = inptVisitDTOS.stream().map(InptVisitDTO::getProfileId).distinct().collect(Collectors.toList());

            if (!ListUtils.isEmpty(newProfileIds)) {
                // 根据档案ids查询查询本地档案信息
                List<OutptProfileFileDTO> data = this.queryBaseProfileByIds(newProfileIds, inptVisitDTO.getHospCode());

                if (!ListUtils.isEmpty(data)) {
                    // 存储处理后的就诊的患者的资料
                    Map<String, List<OutptProfileFileDTO>> profileMap = data.stream().collect(Collectors.groupingBy(OutptProfileFileDTO::getId));

                    for (int i = 0; i < inptVisitDTOS.size(); i++) {
                        InptVisitDTO inptVisit = new InptVisitDTO();
                        //设置医院编码
                        inptVisit.setHospCode(inptVisitDTO.getHospCode());
                        //住院就诊信息存入
                        if (inptVisitDTOS.get(i) != null) {
                            BeanUtils.copyProperties(inptVisitDTOS.get(i), inptVisit);
                        }
                        if (StringUtils.isNotEmpty(inptVisitDTOS.get(i).getProfileId())) {
                            List<OutptProfileFileDTO> list = MapUtils.get(profileMap, inptVisitDTOS.get(i).getProfileId());
                            if (!ListUtils.isEmpty(list)) {
                                OutptProfileFileDTO opt = list.get(0);
                                if (opt != null) {
                                    //档案信息存入
//                                    inptVisit.setNativeAddress(opt.getNativeProv() + "," + opt.getNativeCity() + "," + opt.getNativeArea());
                                    inptVisit.setNativeAddress(opt.getNativeAddress());
//                                    inptVisit.setNowAddress(opt.getNowProv() + "," + opt.getNowCity() + "," + opt.getNowArea());
                                    inptVisit.setNowAddress(opt.getNowAddress());
                                    inptVisit.setNativePostCode(opt.getNativePostCode());
                                    inptVisit.setNowPostCode(opt.getNowPostCode());
                                    inptVisit.setWork(opt.getWork());
                                    inptVisit.setWorkAddress(opt.getWorkAddress());
                                    inptVisit.setWorkPostCode(opt.getWorkPostCode());
                                    inptVisit.setWorkPhone(opt.getWorkPhone());
                                    inptVisit.setNativePlace(opt.getNativePlace());
                                    //入院次数
                                    inptVisit.setTotalIn(String.valueOf(opt.getTotalIn()));
                                }
                            }
                        }
                        inptVisitDTOS.set(i, inptVisit);
                    }
                }
            }
        }
        return PageDTO.of(inptVisitDTOS);
    }

    // 根据档案ids查询查询本地档案信息
    private List<OutptProfileFileDTO> queryBaseProfileByIds(List<String> newProfileIds, String hospCode) {
        OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
        outptProfileFileDTO.setIds(newProfileIds);
        outptProfileFileDTO.setHospCode(hospCode);
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.queryBaseProfileByIds(map).getData();
    }

    /**
     * @Method queryPrintInpt
     * @Desrciption 查询打印住院证
     * @Param [inptVisitDTO]
     * @Author yuelong.chen
     * @Date 2021/11/22 16:08
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public OutptVisitDTO queryPrintInpt(Map map) {
        OutptVisitDTO outptVisitDTO = inptVisitDAO.queryPrintInpt(map);
        return outptVisitDTO;
    }

    /**
     * @Method queryUnregisteredPage
     * @Desrciption 1.查询出所有门诊就诊表的信息
     * 2.根据门诊就诊表的ID查出档案中需要的信息
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/15 16:08
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryUnregisteredPage(OutptVisitDTO outptVisitDTO) {
        outptVisitDTO.setTranInCode(Constants.ZRY.YKZYZ);
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        // 查询出门诊就诊表信息
        List<OutptVisitDTO> outptVisitDTOS = inptVisitDAO.queryOutptVisitAll(outptVisitDTO);
        if (!ListUtils.isEmpty(outptVisitDTOS)) {
            List<String> profileIds = new ArrayList<>();
            for (int i = 0; i < outptVisitDTOS.size(); i++) {
                if (StringUtils.isNotEmpty(outptVisitDTOS.get(i).getProfileId())) {
                    profileIds.add(outptVisitDTOS.get(i).getProfileId());
                }
            }
            if (!ListUtils.isEmpty(profileIds)) {

                // 去掉重复的档案ID
                List<String> newProfileIds = profileIds.stream().distinct().collect(Collectors.toList());
//                List<OutptProfileFileDTO> data = queryProfileS(newProfileIds,outptVisitDTO.getHospCode());
                List<OutptProfileFileDTO> data = this.queryBaseProfileByIds(newProfileIds, outptVisitDTO.getHospCode());
                if (!ListUtils.isEmpty(data)) {
                    Map profileMap = new HashMap();
                    for (int i = 0; i < data.size(); i++) {
                        if (!profileMap.containsKey(data.get(i).getId())) {
                            profileMap.put(data.get(i).getId(), data.get(i));
                        }
                    }

                    for (int i = 0; i < outptVisitDTOS.size(); i++) {
                        //档案信息存入
                        if (StringUtils.isNotEmpty(outptVisitDTOS.get(i).getProfileId())) {
                            OutptProfileFileDTO opt = (OutptProfileFileDTO) profileMap.get(outptVisitDTOS.get(i).getProfileId());
                            if (opt != null) {
                                outptVisitDTOS.get(i).setNationalityCation(opt.getNationalityCation());
                                outptVisitDTOS.get(i).setOccupationCode(opt.getOccupationCode());
                                outptVisitDTOS.get(i).setEducationCode(opt.getEducationCode());
                                outptVisitDTOS.get(i).setContactRelaCode(opt.getContactRelaCode());
                                outptVisitDTOS.get(i).setContactName(opt.getContactName());
                                outptVisitDTOS.get(i).setContactPhone(opt.getContactPhone());
                                outptVisitDTOS.get(i).setContactAddress(opt.getContactAddress());
                                outptVisitDTOS.get(i).setNowAddress(opt.getNowAddress());
                            }
                        }
                    }
                }
            }
        }
        return PageDTO.of(outptVisitDTOS);
    }


    @Override
    public String save(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            // 排除证件类别为其他的，证件类别为其他的直接进行入院登记
            if (StringUtils.isNotEmpty(inptVisitDTO.getCertCode()) && !Constants.ZJLB.QT.equals(inptVisitDTO.getCertCode())) {
                // 校验是否已登记在院
                InptVisitDTO visitDTO = this.queryByCertNo(inptVisitDTO);
                if (visitDTO != null && StringUtils.isNotEmpty(visitDTO.getStatusCode()) && Constants.BRZT.ZY.equals(visitDTO.getStatusCode())) {
                    throw new AppException("该病人已住院，不可重复住院！");
                }
            }
            return insert(inptVisitDTO);
        } else {
            return update(inptVisitDTO);
        }
    }

    /**
     * @return
     * @Method queryByCertNo
     * @Desrciption 查询病人住院状态
     * @Param [String]
     * @Author caoliang
     * @Date 2021/6/1 16:07
     * @Return java.lang.Boolean
     */
    @Override
    public InptVisitDTO queryByCertNo(InptVisitDTO inptVisitDTO) {

        return inptVisitDAO.queryByCertNo(inptVisitDTO);
    }


    /**
     * @Method cancelRegister
     * @Desrciption 取消入院登记
     * 1，判断 （1）是否有医嘱  （2)是否有费用  （3）是否有预交金 4.是否进行了医保登记 如果存在医保登记信息，则先提示取消医保登记
     * 2. 如果上面都通过，删除此记录
     * 3. 如果上面不通过，按123优先级进行提醒
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/17 10:07
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean deleteRegister(InptVisitDTO inptVisitDTO) {
        // 判断是否有医嘱
        InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
        inptAdviceDTO.setHospCode(inptVisitDTO.getHospCode());
        inptAdviceDTO.setVisitId(inptVisitDTO.getId());

        Map<String, Object> selectMap = new HashMap<>();
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setVisitId(inptVisitDTO.getId());
        insureIndividualVisitDTO.setHospCode(inptVisitDTO.getHospCode());
        selectMap.put("hospCode", inptVisitDTO.getHospCode());
        selectMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        List<InsureIndividualVisitDTO> list = insureIndividualVisitService_consumer.findByCondition(selectMap);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("当前已完成医保登记，请先取消医保登记");
        }

        Integer adviceNum = inptAdviceDAO.queryAdviceNumByVisitId(inptAdviceDTO);
        if (adviceNum > 0) {
            throw new AppException("当前存在医嘱，无法取消入院登记");
        }
        InptVisitDTO inptVisitById = this.inptVisitDAO.getInptVisitById(inptVisitDTO);
//        if (StringUtils.isNotEmpty(inptVisitById.getBedId())) {
//            throw new AppException("当前已分配床位，无法取消入院登记");
//        }

        boolean b = BigDecimalUtils.greaterZero(inptVisitById.getTotalCost());
        if (b) {
            throw new AppException("当前存在费用信息，无法取消入院登记");
        }

        boolean c = BigDecimalUtils.greaterZero(inptVisitById.getTotalBalance());
        if (c) {
            throw new AppException("当前存在预交金余额，无法取消入院登记");
        }
        inptVisitById.setStatusCode(Constants.BRZT.ZF);
        inptVisitDAO.invalidPatientStatus(inptVisitById);
        // 费用为0时 取消住院登记，需要作废医嘱
        if (inptVisitById.getId() == null || "".equals(inptVisitById.getId())) {
            throw new AppException("取消住院登记时没有获取到当前患者就诊id，请联系管理员");
        }
        inptVisitDAO.deleteInptAdviceAndLongCost(inptVisitById);
        inptVisitDAO.updateBaseBed(inptVisitById);
        return true;
    }

    /**
     * @Method: updateInptVisitAmount
     * @Description: 回写就诊表金额，余额
     * @Param: [inptVisitDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:47
     * @Return: Boolean
     **/
    @Override
    public Boolean updateInptVisitAmount(List<InptVisitDTO> visitDTOList) {
        return inptVisitDAO.updateInptVisitAmount(visitDTOList) > 0;
    }

    /**
     * @Method queryInptVisitById()
     * @Desrciption 根据就诊id 查询医保病人的医保登记号 医疗机构编码
     * @Param id:就诊id hospCode:医院编码
     * @Author fuhui
     * @Date 2020/11/16 16:17
     * @Return InptVisitDTO 住院病人数据传输对象
     **/
    @Override
    public InptVisitDTO getInptVisitById(InptVisitDTO inptVisitDTO) {
        return inptVisitDAO.getInptVisitById(inptVisitDTO);
    }

    /**
     * @Method updateInsureInptRegister
     * @Desrciption 修改医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateInsureInptRegister(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new AppException("未获取到就诊ID");
        }

        InsureIndividualVisitDTO insureIndividualVisitDTO = inptVisitDAO.getInsureIndividualVisitInfo(inptVisitDTO);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("修改医保入院登记失败：此病人未进行医保登记");
        }

        InsureInptRegisterDTO insureInptRegisterDTO = new InsureInptRegisterDTO();
        insureInptRegisterDTO.setHospCode(inptVisitDTO.getHospCode());
        insureInptRegisterDTO.setInsureOrgCode(insureIndividualVisitDTO.getInsureOrgCode());

        //TODO 必要入参
        insureInptRegisterDTO.setAaz217(insureIndividualVisitDTO.getMedicalRegNo());
        insureInptRegisterDTO.setAkb020(insureInptRegisterDTO.getInsureOrgCode());
        insureInptRegisterDTO.setAae006(inptVisitDTO.getAddress()); // 联系地址
        insureInptRegisterDTO.setAae030(this.getDay(inptVisitDTO.getInTime())); // 入院时间
        insureInptRegisterDTO.setAkc190(inptVisitDTO.getInNo());
        insureInptRegisterDTO.setAae036(DateUtils.getNow()); // 经办时间

        //TODO 入院诊断处理
        String akc193 = insureIndividualVisitDTO.getVisitIcdCode();
        String[] akc193Arr = null;
        if (StringUtils.isNotEmpty(akc193)) {
            akc193Arr = akc193.split(",");
        }
        insureInptRegisterDTO.setAkc193(insureIndividualVisitDTO.getVisitIcdCode());
        insureInptRegisterDTO.setAkc193(akc193Arr[0]);

        //TODO 床位号处理,床位号为空,给默认值
        if (StringUtils.isEmpty(inptVisitDTO.getBedName())) {
            inptVisitDTO.setBedName("1");
        }
        insureInptRegisterDTO.setAke020(inptVisitDTO.getBedName());
        insureInptRegisterDTO.setAke022(inptVisitDTO.getDoctorName());

        BaseDeptDTO dept = this.getDeptInfo(inptVisitDTO.getHospCode(), inptVisitDTO.getInDeptId());
        insureInptRegisterDTO.setAkf001(dept.getCode());
        insureInptRegisterDTO.setBka020(dept.getName());

        BaseDeptDTO inWard = this.getDeptInfo(inptVisitDTO.getHospCode(), inptVisitDTO.getInWardId());
        insureInptRegisterDTO.setBka021(inWard.getCode());
        insureInptRegisterDTO.setBka022(inWard.getName());

        insureInptRegisterDTO.setBkz101(inptVisitDTO.getInDiseaseName());

        List<Map<String, Object>> diagnoseList = new ArrayList<>();
        for (int i = 0; i < akc193Arr.length; i++) {
            Map<String, Object> diagnoseMap = new HashMap<>();
            diagnoseMap.put("bke558", i + 1); // 诊断序号(只会产生一条入院诊断)
            diagnoseMap.put("bke559", "1"); // 诊断类别编码（1-入院诊断;2-出院诊断）
            diagnoseMap.put("aka120", akc193Arr[i]); // 诊断编码
            diagnoseMap.put("akb020", inptVisitDTO.getInsureRegCode()); // 医疗机构编码
            diagnoseList.add(diagnoseMap);

        }
        insureInptRegisterDTO.setDiagnoseinfos(diagnoseList);

        //TODO 非必要入参
        insureInptRegisterDTO.setAke024(""); // 主要病情描述

        //TODO 封装医保个人就诊信数据，做更新操作
        insureIndividualVisitDTO.setMibId(inptVisitDTO.getMibId());
        insureIndividualVisitDTO.setInsureOrgCode(inptVisitDTO.getInsureOrgCode());
        insureIndividualVisitDTO.setInsureRegCode(inptVisitDTO.getInsureRegCode());
        insureIndividualVisitDTO.setMedicineOrgCode(insureInptRegisterDTO.getAkb020());
        insureIndividualVisitDTO.setIsHospital(Constants.SF.S);
        insureIndividualVisitDTO.setVisitNo(inptVisitDTO.getInNo());
        insureIndividualVisitDTO.setInjuryBorthSn(inptVisitDTO.getInjuryBorthSn()); // 业务申请号
        insureIndividualVisitDTO.setVisitIcdCode(insureInptRegisterDTO.getAkc193());
        insureIndividualVisitDTO.setVisitIcdName(inptVisitDTO.getInDiseaseName());
        insureIndividualVisitDTO.setVisitTime(inptVisitDTO.getInTime());
        insureIndividualVisitDTO.setVisitDrptId(inptVisitDTO.getInDeptId());
        insureIndividualVisitDTO.setVisitDrptName(inptVisitDTO.getInDeptName());
        insureIndividualVisitDTO.setVisitAreaId(inptVisitDTO.getInWardId());
        insureIndividualVisitDTO.setVisitAreaName(inWard.getName());
        insureIndividualVisitDTO.setVisitBerth(inptVisitDTO.getBedName());
        insureIndividualVisitDTO.setRemark(inptVisitDTO.getInRemark());
        inptVisitDAO.updateInsureIndividualVisit(insureIndividualVisitDTO);

        Map<String, Object> insureParam = new HashMap<>();
        insureParam.put("hospCode", inptVisitDTO.getHospCode());
        insureParam.put("insureInptRegisterDTO", insureInptRegisterDTO);
        return inptService_consumer.updateInptRegister(insureParam);
    }

    /**
     * @param paramMap
     * @return PageDTO
     * @Menthod queryPatients
     * @Desrciption 在院病人信息查询
     * @Author pengbo
     * @Date 2021/3/5 14:45
     **/
    @Override
    public PageDTO queryPatients(Map<String, Object> paramMap) {
        String pageNo = (String) paramMap.get("pageNo");
        String pageSize = (String) paramMap.get("pageSize");
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        List<Map<String, Object>> inptVisitDTOS = null;
        // 获取系统参数 是否开启大人婴儿合并结算
        SysParameterDTO mergeParameterDTO = null;
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", paramMap.get("hospCode"));
        isMergeParam.put("code", "BABY_INSURE_FEE");
        mergeParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        //《========新生婴儿试算========》
        if (mergeParameterDTO != null && "1".equals(mergeParameterDTO.getValue())) {
            // 开启合并结算
            inptVisitDTOS = inptVisitDAO.queryPatients(paramMap);
        } else {
            // 大人婴儿费用单独结算
            inptVisitDTOS = inptVisitDAO.queryNoMergePatients(paramMap);
            return PageDTO.of(inptVisitDTOS);
        }
//        List<Map<String, Object>> finalResult = new ArrayList<>();
        if (!ListUtils.isEmpty(inptVisitDTOS)) {
            List<String> visitIdList = inptVisitDTOS.stream().map(map -> (String) map.get("id")).collect(Collectors.toList());
            List<Map<String, Object>> patientCostDataList = inptVisitDAO.queryPatientsCostsByVisitIds(MapUtils.get(paramMap, "hospCode"), visitIdList);
//            finalResult = inptVisitDTOS.stream().map(data -> {
//                patientCostDataList.stream().map(costsData ->{
//                    if(data.get("id").equals(costsData.get("visit_id")))
//                    {
//                        data.put("ypfy",costsData.get("item_price"));
//                        data.put("total_price",costsData.get("total_price"));
//                        data.put("fyb",costsData.get("fyb"));
//                    }
//                    return costsData;
//                });
//                return data;
//            }).collect(Collectors.toList());
            outer:
            for (Map<String, Object> map : inptVisitDTOS) {
                for (Map<String, Object> map2 : patientCostDataList) {
                    if (map.get("id").equals(map2.get("visit_id"))) {
                        map.put("ypfy", map2.get("item_price"));
                        map.put("total_price", map2.get("total_price"));
                        map.put("fyb", map2.get("fyb"));
                        continue outer;
                    }
                }
            }
        }
        return PageDTO.of(inptVisitDTOS);
    }

    /**
     * @Method deleteInsureRegister
     * @Desrciption 取消医保入院登记
     * 业务处理说明
     * 1.查询是否为医保病人
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteInsureRegister(InptVisitDTO inptVisitDTO) {
        String hospCode = inptVisitDTO.getHospCode();
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new AppException("未获取到就诊ID");
        }
        InptVisitDTO visit = inptVisitDAO.getInptVisitById(inptVisitDTO);
        Map<String, Object> insureParam = new HashMap<>();
        InsureIndividualVisitDTO insureIndividualVisitDTO = inptVisitDAO.getInsureIndividualVisitInfo(inptVisitDTO);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("取消医保入院登记失败：此病人未进行医保登记");
        }

        // 根据医院编码、医保注册编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", hospCode);
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        InsureInptOutFeeDTO insureInptOutFeeDTO = new InsureInptOutFeeDTO();
        insureInptOutFeeDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        insureInptOutFeeDTO.setInsureOrgCode(insureIndividualVisitDTO.getInsureOrgCode());
        insureInptOutFeeDTO.setAaz217(insureIndividualVisitDTO.getMedicalRegNo());
        insureInptOutFeeDTO.setAkb020(insureIndividualVisitDTO.getInsureOrgCode());

        // 长沙医保必要入参
        insureInptOutFeeDTO.setAac001(insureIndividualVisitDTO.getAac001());
        insureInptOutFeeDTO.setBka015(inptVisitDTO.getCrteName());
        insureInptOutFeeDTO.setUserCode(inptVisitDTO.getUserCode());

        insureParam.put("hospCode", insureIndividualVisitDTO.getHospCode());
        insureParam.put("insureInptOutFeeDTO", insureInptOutFeeDTO);
        /*// 获取系统参数中配置的是否走统一支付平台
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台*/
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {  // 调用统一支付平台
            /**统一支付平台调用   开始*/
            Map<String, Object> insureUnifiedPayParam = new HashMap<>();
            insureUnifiedPayParam.put("hospCode", insureIndividualVisitDTO.getHospCode());
            insureUnifiedPayParam.put("insureInptOutFeeDTO", insureInptOutFeeDTO);
            insureUnifiedPayParam.put("inptVisitDTO", inptVisitDTO);
            insureUnifiedPayParam.put("medicalRegNo", insureIndividualVisitDTO.getMedicalRegNo());
            insureUnifiedPayParam.put("id", inptVisitDTO.getId());

            /**
             *  如果存在中途结算、则取消结算时，应该要先取消最后一笔登记记录
             *  可以根据中途结算次数来判断
             */
            checkSettleCount(inptVisitDTO,insureIndividualVisitDTO);
            insureUnifiedPayInptService_consumer.UP_2404(insureUnifiedPayParam);
            // 删除医保费用数据
            insureIndividualCostService_consumer.deleteOutptInsureCost(insureUnifiedPayParam);
            /**统一支付平台调用   结束*/
        } else {
            if (!Constants.BRLX.SNYDBR.equals(visit.getPatientCode()) && !Constants.BRLX.SWYDBR.equals(visit.getPatientCode())) {
                inptService_consumer.deteleInptRegister(insureParam);
            } else {
                InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO = new InsureRemoteInptRegisterDTO();
                insureRemoteInptRegisterDTO.setAkb020(insureIndividualVisitDTO.getInsureOrgCode());
                insureRemoteInptRegisterDTO.setAaz217(insureIndividualVisitDTO.getMedicalRegNo());
                insureRemoteInptRegisterDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
                insureRemoteInptRegisterDTO.setAae011(inptVisitDTO.getCrteId());
                insureRemoteInptRegisterDTO.setBka015(inptVisitDTO.getCrteName());
                insureRemoteInptRegisterDTO.setInsureOrgCode(insureIndividualVisitDTO.getInsureOrgCode());
                insureRemoteInptRegisterDTO.setCardIden(insureIndividualVisitDTO.getCardIden());
                insureParam.put("insureRemoteInptRegisterDTO", insureRemoteInptRegisterDTO);
                inptService_consumer.deteleRemoteInptRegister(insureParam);
            }
        }
        inptVisitDAO.deleteInsureInptRegisterByParams(insureIndividualVisitDTO);
        /**
         * 如果取消医保入院登记的时候 还存在中途结算的数据 则不修改病人类型
         * 否则 修改为普通病人类型
         */
        insureParam.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        Integer count = insureIndividualVisitService_consumer.selectHalfVisit(insureParam).getData();
        if (count == null || count < 1) {
            inptVisitDTO.setPatientCode(Constants.BRLX.PTBR);
        }
        // 取消登记需要把医疗类别置空
        inptVisitDTO.setInsureBizCode("");
        inptVisitDAO.updateInptVisit(inptVisitDTO);
        return true;
    }

    /**
     * @Method checkSettleCount
     * @Desrciption  验证是否取消入院登记的规则 后登记的先取消
     * @Param
     *
     * @Author fuhui
     * @Date   2022/2/15 10:52
     * @Return
    **/
    private void checkSettleCount(InptVisitDTO inptVisitDTO, InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String, Object> insureUnifiedPayParam = new HashMap<>();
        insureUnifiedPayParam.put("hospCode",inptVisitDTO.getHospCode());
        insureUnifiedPayParam.put("id",inptVisitDTO.getId());
        InsureIndividualVisitDTO data = insureIndividualVisitService_consumer.
                selectMaxAndMinRegisterTime(insureUnifiedPayParam).getData();
        if(data !=null){
            if(DateUtils.dateCompare(insureIndividualVisitDTO.getCrteTime(),data.getMaxRegister())){
                throw new AppException("请先取消最晚一次的医保登记记录信息");
            }
        }

    }

    /**
     * @param inptVisitDTO
     * @Menthod saveInsureInRegisterEMD
     * @Desrciption 电子凭证医保登记（住院）  往insure_individual_visit插入登记信息  并根据就诊id删除原有记录
     * @Author xingyu.xie
     * @Date 2021/3/1 14:30
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean saveInsureInRegisterEMD(InptVisitDTO inptVisitDTO) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureIndividualBasicDTO.getDiagnose();//诊断信息
        if (insureDiseaseMatchDTOS == null || insureDiseaseMatchDTOS.isEmpty()) {
            throw new AppException("未获取到诊断信息，请联系管理员");
        }
        InptVisitDTO inptVisitDto = inptVisitDAO.getInptVisitById(inptVisitDTO);//获取患者入院登记信息
        if (inptVisitDto == null) {
            throw new AppException("未查询到病人入院登记信息");
        }
        //编辑住院就诊信息
        String baseId = SnowflakeUtils.getId();
        inptVisitDTO.setInsureCode(insureIndividualBasicDTO.getBaa027());//参保类型
        inptVisitDTO.setInsureOrgCode(insureIndividualBasicDTO.getAkb020());//参保机构编码
        inptVisitDTO.setInsureNo(insureIndividualBasicDTO.getBka896());//参保号
        inptVisitDTO.setInsureBizCode(insureIndividualBasicDTO.getAka130());//医保业务类型编码
        inptVisitDTO.setInsureTreatCode(insureIndividualBasicDTO.getBka006());//医保待遇类型编码
        inptVisitDTO.setInsurePatientId(baseId);//医保病人ID
        inptVisitDTO.setInsureRemark(insureIndividualBasicDTO.getRemark());//医保合同单位备注
        inptVisitDAO.updateInptVisit(inptVisitDTO);

        //医保登记诊断信息保存
        int index = 0;
        StringBuilder icdCode = new StringBuilder();
        StringBuilder icdName = new StringBuilder();
        //副诊断
        List<InptDiagnoseDTO> inptDiagnoseDTODTOList = new ArrayList<InptDiagnoseDTO>();
        for (InsureDiseaseMatchDTO insureDiseaseMatchDTO1 : insureDiseaseMatchDTOS) {
            if (index > 0) {
                //副诊断存库参数
                InptDiagnoseDTO inptDiagnoseDTO = new InptDiagnoseDTO();
                inptDiagnoseDTO.setId(SnowflakeUtils.getId());//id
                inptDiagnoseDTO.setHospCode(inptVisitDto.getHospCode());//医院编码
                inptDiagnoseDTO.setDiseaseId(insureDiseaseMatchDTO1.getHospIllnessId());//疾病id
                inptDiagnoseDTO.setVisitId(inptVisitDto.getId());//就诊id
                inptDiagnoseDTO.setTypeCode(Constants.ZDLX.ZYCZD);//诊断类型代码 = 住院次诊断
                inptDiagnoseDTO.setIsMain(Constants.SF.F);//是否主诊断
                inptDiagnoseDTO.setCrteId(inptVisitDTO.getCrteId());//创建人id
                inptDiagnoseDTO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
                inptDiagnoseDTO.setCrteTime(DateUtils.getNow());//创建时间
                inptDiagnoseDTODTOList.add(inptDiagnoseDTO);
            }
            index++;
            icdCode.append(insureDiseaseMatchDTO1.getInsureIllnessCode()).append(",");
            icdName.append(insureDiseaseMatchDTO1.getInsureIllnessName()).append(",");
        }
        if (!inptDiagnoseDTODTOList.isEmpty()) {
            //先删除当前患者住院次诊断信息
            InptDiagnoseDTO inptDiagnoseDTO = new InptDiagnoseDTO();
            inptDiagnoseDTO.setHospCode(inptVisitDto.getHospCode());//医院编码
            inptDiagnoseDTO.setVisitId(inptVisitDto.getId());//就诊id
            inptAdviceDAO.deleteDiagnose(inptDiagnoseDTO);
            //保存次诊断信息
            inptAdviceDAO.insertDiagnose(inptDiagnoseDTODTOList);
        }

        Map<String, Object> electronicBillParam = JSONObject.parseObject(inptVisitDTO.getElectronicBillParam(), HashMap.class);//医保个人信息
        Map<String, String> app = (Map<String, String>) electronicBillParam.get("app");//解码信息
        String userId = inptVisitDTO.getCrteId();//当前登录用户id
        String userName = inptVisitDTO.getCrteName();//当前登录用户名称
        Date now = new Date();//创建时间

        //编辑 insure_individual_basic（编辑、删除）
        String individualBasicId = SnowflakeUtils.getId();//个人基本信息id
        InsureIndividualBasicDO insureIndividualBasicDO = new InsureIndividualBasicDO();
        insureIndividualBasicDO.setId(individualBasicId);//主键id
        insureIndividualBasicDO.setHospCode(insureIndividualBasicDTO.getHospCode());//医院编码
        insureIndividualBasicDO.setAaa027(app.get("insuOrg"));//分级统筹中心编码
        insureIndividualBasicDO.setAaa027Name(null);//分级统筹中心名称
        insureIndividualBasicDO.setAac003(app.get("userName"));//姓名
        insureIndividualBasicDO.setAac004(app.get("gender"));//性别
        insureIndividualBasicDO.setAac002(app.get("idNo"));//公民身份号码
        insureIndividualBasicDO.setAaz500(app.get("idNo"));//社会保障号码
        insureIndividualBasicDO.setAac006(null);//出生日期
        insureIndividualBasicDO.setAkc252(BigDecimalUtils.convert((String) electronicBillParam.get("acctBalc")));//个人帐户余额
        insureIndividualBasicDO.setAac066(null);//参保身份
        insureIndividualBasicDO.setBaa027(app.get("insuOrg"));//参保地中心编码
        insureIndividualBasicDO.setCrteId(userId);//创建人id
        insureIndividualBasicDO.setCrteName(userName);//创建人姓名
        insureIndividualBasicDO.setCrteTime(now);//创建人

        //编辑 insure_individual_Visit（编辑、删除）
        insureIndividualVisitService_consumer.deleteByVisitId(null);//删除医保就诊信息表
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setId(baseId);//id
        insureIndividualVisitDTO.setHospCode(insureIndividualBasicDTO.getHospCode());//医院编码
        insureIndividualVisitDTO.setVisitId(inptVisitDTO.getVisitId());//就诊id
        insureIndividualVisitDTO.setMibId(individualBasicId);//基本信息id
        insureIndividualVisitDTO.setInsureOrgCode(null);//医保机构编码
        insureIndividualVisitDTO.setInsureRegCode(null);//医保注册编码
        insureIndividualVisitDTO.setIsHospital(Constants.SF.S);//是否住院
        insureIndividualVisitDTO.setVisitNo(inptVisitDTO.getVisitNo());//就诊号
        insureIndividualVisitDTO.setAac001(insureIndividualBasicDTO.getAac001());//个人电脑号
        insureIndividualVisitDTO.setMedicalRegNo(inptVisitDTO.getMedicalRegNo());//医保登记号
        insureIndividualVisitDTO.setAka130(insureIndividualBasicDTO.getAka130());//业务类型
        insureIndividualVisitDTO.setAka130Name(null);//业务类型名称
        insureIndividualVisitDTO.setBka006(insureIndividualBasicDTO.getBka006());//待遇类型
        insureIndividualVisitDTO.setBka006Name(null);//待遇类型名称
        insureIndividualVisitDTO.setInjuryBorthSn(inptVisitDTO.getInjuryBorthSn());//业务申请号
        insureIndividualVisitDTO.setVisitIcdCode(icdCode.substring(0, icdCode.length() - 1));//就诊疾病编码
        insureIndividualVisitDTO.setVisitIcdName(icdName.substring(0, icdName.length() - 1));//就诊疾病名称
        insureIndividualVisitDTO.setVisitTime(now);//就诊时间
        insureIndividualVisitDTO.setVisitDrptId(inptVisitDto.getInDeptId());//科室id
        insureIndividualVisitDTO.setVisitDrptName(inptVisitDto.getInDeptName());//科室名称
        insureIndividualVisitDTO.setVisitAreaId(inptVisitDto.getInWardId());//就诊病区id
        insureIndividualVisitDTO.setVisitAreaName(null);//就诊病区名称
        insureIndividualVisitDTO.setVisitBerth(inptVisitDto.getBedName());//就诊床位
        insureIndividualVisitDTO.setStartingPrice(null);//起付线金额
        insureIndividualVisitDTO.setCrteId(userId);//创建人id
        insureIndividualVisitDTO.setCrteName(userName);//创建人姓名
        insureIndividualVisitDTO.setCrteTime(now);//创建时间
        insureIndividualVisitDTO.setIsEcqr(Constants.SF.S);//是否电子凭证
        insureIndividualVisitDTO.setPayOrdId(SnowflakeUtils.getId());//支付订单号
        insureIndividualVisitDTO.setPayToken(JSONObject.toJSONString(app));//支付toKen
        insureIndividualVisitDTO.setInsuplcAdmdvs(app.get("insuOrg"));//参保地医保区划

        // 获取码表值字段名称
        //insureIndividualBasicDTO.setHospCode(inptVisitDTO.getHospCode());
        //Map dictNameMap = this.getDictNameMapByCodeV(insureIndividualBasicDTO);
        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("hospCode", inptVisitDto.getHospCode());
        insertMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        insertMap.put("visitId", inptVisitDto.getId());
        // 先根据就诊ID删除原记录
        insureIndividualVisitService_consumer.deleteByVisitId(insertMap);
        // 再重新插入
        insureIndividualVisitService_consumer.insertIndividualVisit(insertMap);
        // 处理当前就诊人员的人员累计信息
        this.dealCumInfo(insureIndividualVisitDTO, inptVisitDTO);
        return true;
    }


    public void dealCumInfo(InsureIndividualVisitDTO insureIndividualVisitDTO,InptVisitDTO inptVisitDTO){
        Map<String, Object> param = new HashMap<>();
        param.put("hospCode", insureIndividualVisitDTO.getHospCode());
        param.put("psnNo", insureIndividualVisitDTO.getAac001());
        param.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        // 先根据个人电脑号删除本地累计信息
        Integer delete = insureIndividualVisitService_consumer.deletePatientSumInfoByPsnNo(param);
        // 从医保查询年度人员累计信息
        Map<String, Object> data = insureUnifiedBaseService_consumer.queryPatientSumInfoAllYears(param).getData();
        List<Map<String, Object>> resultMap = MapUtils.get(data, "resultDataMap");
        if (!ListUtils.isEmpty(resultMap)) {
            resultMap.stream().forEach(item -> {
                item.put("id", SnowflakeUtils.getId());
                item.put("crteName", inptVisitDTO.getCrteName());
                item.put("medicalRegNo", insureIndividualVisitDTO.getMedicalRegNo());
                item.put("crteId", inptVisitDTO.getCrteId());
                item.put("visitId", insureIndividualVisitDTO.getVisitId());
                item.put("hospCode", inptVisitDTO.getHospCode());
                item.put("psnNo", insureIndividualVisitDTO.getAac001());
                item.put("medisCode", insureIndividualVisitDTO.getMedicineOrgCode());
                item.put("insureSettleId", null);
                item.put("crteTime", DateUtils.getNow());
                Object cum = item.get("cum");
                if (cum == null || StringUtils.isEmpty(cum.toString())) {
                    item.put("cum", 0);
                }
            });
        }
        // 将查出来个人累计信息插入到数据库
        param.put("resultMap", resultMap);
        if(ObjectUtil.isNotEmpty(resultMap)){
            insureIndividualVisitService_consumer.insertPatientSumInfoAll(param);
        }
    }

    /**
     * @Method saveInsureInRegister
     * @Desrciption 医保入院登记(2020 - 12 - 21)
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean saveInsureInRegister(InptVisitDTO inptVisitDTO) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        if (insureIndividualBasicDTO == null) {
            throw new AppException("请先获取就诊人员参保信息");
        }

        // 获取诊断信息
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureIndividualBasicDTO.getDiagnose();
        if (ListUtils.isEmpty(insureDiseaseMatchDTOS)) {
            throw new AppException("未获取到诊断信息，请联系管理员");
        }

        // 获取入院登记信息
        InptVisitDTO inptVisitDto = inptVisitDAO.getInptVisitById(inptVisitDTO);
        if (inptVisitDto == null) {
            throw new AppException("未查询到病人入院登记信息");
        }

        if(StringUtils.isNotEmpty(inptVisitDto.getStatusCode()) && StringUtils.isNotEmpty(inptVisitDTO.getStatusCode()) && !inptVisitDto.getStatusCode().equals(inptVisitDTO.getStatusCode())){
            throw new AppException("当前界面患者状态未更新，请刷新界面重新登记！");
        }
        // 异地病人返回akb020参数为null,医保中心编码使用湘潭核三医保中心编码，否则使用医保端返回中心编码
        if (StringUtils.isEmpty(insureIndividualBasicDTO.getAkb020())) {
            insureIndividualBasicDTO.setAkb020(insureIndividualBasicDTO.getInsureRegCode());
        }

        // 获取码表值字段名称
        insureIndividualBasicDTO.setHospCode(inptVisitDTO.getHospCode());
        Map dictNameMap = this.getDictNameMapByCodeV(insureIndividualBasicDTO);

        // TODO 医保获取的人员信息保存
        String baseId = SnowflakeUtils.getId();
        inptVisitDTO.setPhone(inptVisitDto.getPhone());
        this.insetInsureIndividualBasic(inptVisitDTO, baseId, dictNameMap);

        // TODO 住院就诊信息更新
        inptVisitDTO.setInsureCode(insureIndividualBasicDTO.getBaa027());
        inptVisitDTO.setInsureOrgCode(insureIndividualBasicDTO.getAkb020());
        inptVisitDTO.setInsureNo(insureIndividualBasicDTO.getBka896());
        inptVisitDTO.setInsureBizCode(insureIndividualBasicDTO.getAka130());
        inptVisitDTO.setInsureTreatCode(insureIndividualBasicDTO.getBka006());
        inptVisitDTO.setInsurePatientId(baseId);
        inptVisitDTO.setInsureRemark(insureIndividualBasicDTO.getRemark());
        // 防止异步修改数据库，这里把异步能修改的重新把数据库里的数据对其赋值
        inptVisitDTO.setBedName(inptVisitDto.getBedName());
        inptVisitDTO.setBedId(inptVisitDto.getBedId());
        inptVisitDTO.setInDeptCode(inptVisitDto.getInDeptCode());
        inptVisitDTO.setInDeptId(inptVisitDto.getInDeptId());
        inptVisitDTO.setInDeptName(inptVisitDto.getInDeptName());
        inptVisitDAO.updateInptVisit(inptVisitDTO);

        // TODO 封装医保入院登记入参
        SysUserDTO user = this.getUserInfo(inptVisitDTO.getHospCode(), inptVisitDTO.getCrteId());


        // 必填参数
        InsureInptRegisterDTO insureInptRegisterDTO = new InsureInptRegisterDTO();
        inptVisitDTO.setInsureOrgCode(insureIndividualBasicDTO.getAkb020());
        insureInptRegisterDTO.setHospCode(inptVisitDto.getHospCode());
        insureInptRegisterDTO.setInsureOrgCode(insureIndividualBasicDTO.getAkb020());
        insureInptRegisterDTO.setAaa027(insureIndividualBasicDTO.getAaa027()); // 病人所属地医保机构编码
        insureInptRegisterDTO.setAac001(insureIndividualBasicDTO.getAac001()); // 电脑号
        insureInptRegisterDTO.setAae011(user.getCode()); // 登记人工号
        insureInptRegisterDTO.setAae030(this.getDay(inptVisitDto.getInTime())); // 就诊发生日期 yyyyMMdd
        insureInptRegisterDTO.setAka130(insureIndividualBasicDTO.getAka130()); // 业务类型
        insureInptRegisterDTO.setAkb020(insureIndividualBasicDTO.getAkb020()); // 医疗机构编码

        //入院主诊断信息
        InsureDiseaseMatchDTO insureDiseaseMatchDTO = insureDiseaseMatchDTOS.get(0);
        insureInptRegisterDTO.setAkc193(insureDiseaseMatchDTO.getInsureIllnessCode()); // 入院诊断编码
        insureInptRegisterDTO.setBkz101(insureDiseaseMatchDTO.getInsureIllnessName()); // 入院诊断名称

        // 床位号为空给默认值
        if (StringUtils.isEmpty(inptVisitDto.getBedName())) {
            inptVisitDto.setBedName("未安床");
        }
        insureInptRegisterDTO.setAke020(inptVisitDto.getBedName()); // 床位号
        insureInptRegisterDTO.setAkf001(this.getDeptInfo(inptVisitDto.getHospCode(), inptVisitDto.getInDeptId()).getCode()); // 科室编码
        insureInptRegisterDTO.setBka006(insureIndividualBasicDTO.getBka006()); // 医疗待遇类型
        insureInptRegisterDTO.setBka015(user.getName()); // 登记人姓名
        insureInptRegisterDTO.setBka020(this.getDeptInfo(inptVisitDto.getHospCode(), inptVisitDto.getInDeptId()).getName()); // 科室名称
        String inWardCode = this.getDeptInfo(inptVisitDto.getHospCode(), inptVisitDto.getInWardId()).getCode();
        insureInptRegisterDTO.setBka021(inWardCode); // 病区编码
        String inWardName = this.getDeptInfo(inptVisitDto.getHospCode(), inptVisitDto.getInWardId()).getName();
        insureInptRegisterDTO.setBka022(inWardName);
        insureInptRegisterDTO.setAkc190(inptVisitDto.getInNo()); // 住院号
        // 入院诊断信息
        List<Map<String, Object>> diagnoseList = new ArrayList<>();
        int index = 0;
        StringBuilder icdCode = new StringBuilder();
        StringBuilder icdName = new StringBuilder();
        //副诊断
        List<InptDiagnoseDTO> inptDiagnoseDTODTOList = new ArrayList<InptDiagnoseDTO>();
        for (InsureDiseaseMatchDTO insureDiseaseMatchDTO1 : insureDiseaseMatchDTOS) {
            if (index > 0) {
                Map<String, Object> diagnoseMap = new HashMap<String, Object>();
                diagnoseMap.put("bke558", index); // 诊断序号(只会产生一条入院诊断)
                diagnoseMap.put("bke559", "1"); // 诊断类别编码（1-入院诊断;2-出院诊断）
                diagnoseMap.put("aka120", insureDiseaseMatchDTO1.getInsureIllnessCode()); // 诊断编码
                diagnoseList.add(diagnoseMap);
                //副诊断存库参数
                InptDiagnoseDTO inptDiagnoseDTO = new InptDiagnoseDTO();
                inptDiagnoseDTO.setId(SnowflakeUtils.getId());//id
                inptDiagnoseDTO.setHospCode(inptVisitDto.getHospCode());//医院编码
                inptDiagnoseDTO.setDiseaseId(insureDiseaseMatchDTO1.getHospIllnessId());//疾病id
                inptDiagnoseDTO.setVisitId(inptVisitDto.getId());//就诊id
                inptDiagnoseDTO.setTypeCode(Constants.ZDLX.ZYCZD);//诊断类型代码 = 住院次诊断
                inptDiagnoseDTO.setIsMain(Constants.SF.F);//是否主诊断
                inptDiagnoseDTO.setCrteId(inptVisitDTO.getCrteId());//创建人id
                inptDiagnoseDTO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
                inptDiagnoseDTO.setCrteTime(DateUtils.getNow());//创建时间
                inptDiagnoseDTODTOList.add(inptDiagnoseDTO);
            }
            index++;
            icdCode.append(insureDiseaseMatchDTO1.getInsureIllnessCode()).append(",");
            icdName.append(insureDiseaseMatchDTO1.getInsureIllnessName()).append(",");
        }
        insureInptRegisterDTO.setDiagnoseinfos(diagnoseList);
        if (!inptDiagnoseDTODTOList.isEmpty()) {
            //先删除当前患者住院次诊断信息
            InptDiagnoseDTO inptDiagnoseDTO = new InptDiagnoseDTO();
            inptDiagnoseDTO.setHospCode(inptVisitDto.getHospCode());//医院编码
            inptDiagnoseDTO.setVisitId(inptVisitDto.getId());//就诊id
            inptAdviceDAO.deleteDiagnose(inptDiagnoseDTO);
            //保存次诊断信息
            inptAdviceDAO.insertDiagnose(inptDiagnoseDTODTOList);
        }

        insureInptRegisterDTO.setHcardBasinfo(insureIndividualBasicDTO.getHcardBasinfo());
        insureInptRegisterDTO.setHcardChkinfo(insureIndividualBasicDTO.getHcardChkinfo());
        insureInptRegisterDTO.setBka035(insureIndividualBasicDTO.getBka035()); // 人员类别


        // 非必填(系统中有数据来源)
        insureInptRegisterDTO.setAac003(inptVisitDto.getName()); // 姓名
        insureInptRegisterDTO.setAac004(this.getGenderName(inptVisitDto.getGenderCode())); // 性别
        insureInptRegisterDTO.setAac006(this.getDay(inptVisitDto.getBirthday())); // 出生日期 yyyyMMdd
        insureInptRegisterDTO.setAae004(inptVisitDto.getContactName()); // 联系人
        insureInptRegisterDTO.setAae005(inptVisitDto.getPhone()); // 联系电话
        insureInptRegisterDTO.setAae006(inptVisitDto.getContactAddress()); // 联系地址
        insureInptRegisterDTO.setAke022(inptVisitDto.getDoctorName()); // 主治医生
        insureInptRegisterDTO.setAke024(inptVisitDto.getInRemark()); // 病情备注
        insureInptRegisterDTO.setBka245(BigDecimalUtils.nullToZero(inptVisitDTO.getPrice()) + ""); // 预付款
        insureInptRegisterDTO.setBka008(inptVisitDto.getWork()); // 单位名称
        insureInptRegisterDTO.setAab001(insureIndividualBasicDTO.getAab001()); // 单位ID

        // 长沙医保必要参数
        insureInptRegisterDTO.setInNo(inptVisitDto.getInNo());
        insureInptRegisterDTO.setDoctorId(inptVisitDto.getJzDoctorId());
        insureInptRegisterDTO.setUserCode(inptVisitDTO.getUserCode());
        insureInptRegisterDTO.setCardIden(insureIndividualBasicDTO.getCardIden());

        // 湖南省医保必填
        insureInptRegisterDTO.setAae036(inptVisitDto.getInTime()); // 经办时间
        insureInptRegisterDTO.setUserName(inptVisitDTO.getCrteName());

        // 工伤生育必填
        insureInptRegisterDTO.setInjuryBorthSn(insureIndividualBasicDTO.getInjuryBorthSn());

        // TODO 远程调用医保接口
        Map<String, Object> insureParam = new HashMap<>();
        insureParam.put("hospCode", inptVisitDTO.getHospCode());
        insureParam.put("insureInptRegisterDTO", insureInptRegisterDTO);

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>(2);

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureOrgCode());
        dataMap.put("insureConfigurationDTO", insureConfigurationDTO);
        dataMap.put("hospCode", inptVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationService_consumer.queryInsureIndividualConfig(dataMap).getData();

        // 获取页面传递的是否走统一支付平台值，为1走，0/null不走
        String isUnifiedPay = StringUtils.isEmpty(inptVisitDTO.getIsUnifiedPay()) ? insureConfigurationDTO.getIsUnifiedPay() : inptVisitDTO.getIsUnifiedPay();

       /* // 获取系统参数中配置的是否走统一支付平台
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();

        if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台*/
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {  // 调用统一支付平台
            //在调用入院登记接口前，先调用2001 人员待遇享受检查 接口，判断待遇是否正常，如果冻结，则提示“该参保人待遇冻结，不能办理医保入院登记。”,只针对江西 H36
            if (ObjectUtil.isEmpty(insureConfigurationDTO.getOrgCode())){
              throw new AppException("未获取到机构配置信息医疗机构编码，请联系管理员");
            }
            SysParameterDTO parameterDTO = null;
            Map<String, Object> isMergeParam = new HashMap<>();
            isMergeParam.put("hospCode", inptVisitDTO.getHospCode());
            isMergeParam.put("code", "CHECK_TRTMENT");
            parameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
            //有校验待遇配置项目
            if (ObjectUtil.isNotEmpty(parameterDTO)){
              if (parameterDTO.getValue().equals(insureConfigurationDTO.getOrgCode().substring(0,3))){
                Map<String, Object> treatmentParam = new HashMap<>();
                treatmentParam.put("inptVisitDTO",inptVisitDTO);
                treatmentParam.put("hospCode",inptVisitDTO.getHospCode());
                treatmentParam.put("insureConfigurationDTO",insureConfigurationDTO);
                treatmentParam.put("insureInptRegisterDTO",insureInptRegisterDTO);
                treatmentParam.put("insureIndividualBasicDTO",insureIndividualBasicDTO);
                insureUnifiedPayInptService_consumer.UP_2001(treatmentParam);
              }
            }
            /**统一支付平台调用   开始*/
            Map<String, Object> insureUnifiedPayParam = new HashMap<>();
            String pracCertiNo = inptVisitDto.getPracCertiNo();
            if (StringUtils.isEmpty(pracCertiNo)) {
                throw new AppException("该【" + inptVisitDTO.getZzDoctorName() + "】医生的医师编码没有维护,请先去用户管理里面维护");
            }
            inptVisitDTO.setPracCertiNo(inptVisitDto.getPracCertiNo());
            insureUnifiedPayParam.put("inptVisitDTO", inptVisitDTO);
            insureUnifiedPayParam.put("hospCode", inptVisitDTO.getHospCode());
            insureUnifiedPayParam.put("insureInptRegisterDTO", insureInptRegisterDTO);
            insureUnifiedPayParam.put("diseinfo", insureDiseaseMatchDTOS);
            resultMap = insureUnifiedPayInptService_consumer.UP_2401(insureUnifiedPayParam);
            /**统一支付平台调用   结束*/
        } else {

            if (!Constants.BRLX.SNYDBR.equals(inptVisitDTO.getPatientCode()) && !Constants.BRLX.SWYDBR.equals(inptVisitDTO.getPatientCode())) {
                resultMap = inptService_consumer.insertInptRegister(insureParam);
            } else {
                InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO = new InsureRemoteInptRegisterDTO();
                BeanUtils.copyProperties(insureInptRegisterDTO, insureRemoteInptRegisterDTO);
                insureRemoteInptRegisterDTO.setHospCode(insureInptRegisterDTO.getHospCode());
                insureRemoteInptRegisterDTO.setAac001(insureIndividualBasicDTO.getAac001()); // 个人电脑号
                insureRemoteInptRegisterDTO.setAaa027(insureIndividualBasicDTO.getAaa027()); // 参保人行政编码
                insureRemoteInptRegisterDTO.setAkb020(insureIndividualBasicDTO.getAkb020()); // 医保中心编码
                insureRemoteInptRegisterDTO.setAac002(insureIndividualBasicDTO.getAac002()); // 社会保障号
                insureRemoteInptRegisterDTO.setAka130(insureIndividualBasicDTO.getAka130()); // 业务类型
                insureRemoteInptRegisterDTO.setBka006(insureIndividualBasicDTO.getBka006()); // 待遇类型
                insureRemoteInptRegisterDTO.setAae030(DateUtils.format(inptVisitDto.getInTime(), DateUtils.Y_M_DH_M_S)); // 入院日期
                insureRemoteInptRegisterDTO.setBka015(insureInptRegisterDTO.getBka015()); // 登记人姓名
                insureRemoteInptRegisterDTO.setAae011(insureInptRegisterDTO.getAae011()); // 登记人工号
                insureRemoteInptRegisterDTO.setBka021(insureInptRegisterDTO.getBka021()); // 病区编码
                insureRemoteInptRegisterDTO.setCardIden(insureIndividualBasicDTO.getCardIden()); // 卡识别码
                insureRemoteInptRegisterDTO.setBka022(insureInptRegisterDTO.getBka022()); // 入院病区名称
                insureRemoteInptRegisterDTO.setAkf001(insureInptRegisterDTO.getAkf001()); // 入院科室编号
                insureRemoteInptRegisterDTO.setBka020(insureInptRegisterDTO.getBka020()); // 科室名称
                insureRemoteInptRegisterDTO.setAkc193(insureInptRegisterDTO.getAkc193()); // 入院疾病诊断（icd码）
                insureRemoteInptRegisterDTO.setAke020(insureInptRegisterDTO.getAke020()); // 入院病床编号
                insureRemoteInptRegisterDTO.setAkc190(insureInptRegisterDTO.getAkc190()); // 住院号
                insureRemoteInptRegisterDTO.setBka245(insureInptRegisterDTO.getBka245()); // 预付款总额
                insureRemoteInptRegisterDTO.setAae005(insureInptRegisterDTO.getAae005()); // 联系电话
                insureRemoteInptRegisterDTO.setBka008(insureIndividualBasicDTO.getBka008());
                insureRemoteInptRegisterDTO.setAab001(insureIndividualBasicDTO.getAab001());//单位ID
                insureRemoteInptRegisterDTO.setInjuryBorthSn(insureInptRegisterDTO.getInjuryBorthSn());
                insureRemoteInptRegisterDTO.setAab001(insureIndividualBasicDTO.getAab001());//单位ID

                insureRemoteInptRegisterDTO.setBka036("0"); // 是否读卡
                if ("iccardno".equals(insureIndividualBasicDTO.getBka895())) {
                    insureRemoteInptRegisterDTO.setBka036("1"); // 是否读卡
                    insureRemoteInptRegisterDTO.setBka100(insureIndividualBasicDTO.getBka896());
                }
                insureParam.put("insureRemoteInptRegisterDTO", insureRemoteInptRegisterDTO);
                resultMap = inptService_consumer.insertRemoteInptRegister(insureParam);
            }
        }
        String medicalegNo = String.valueOf(resultMap.get("aaz217"));
        String omsgid = MapUtils.get(resultMap, "omsgid");
        String oinfno = MapUtils.get(resultMap, "oinfno");
        // 处理医保回参数据
        try {
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();

            // 人员身份类别判断是否一站式
            String isOneSettle = Constants.SF.F;
            List<Map<String, Object>> identMapList = inptVisitDTO.getIdetinList();
            if (!ListUtils.isEmpty(identMapList)) {
                String psnIdetTypes = "";
                String psnIdetTypesName = "";
                for (Map<String, Object> identMap : identMapList) {
                    String psnIdetType = MapUtils.get(identMap, "psn_idet_type"); // 人员身份类别
                    String psnIdetTypeName = MapUtils.get(identMap, "psn_idet_type_name"); // 人员身份类别码值
                    String begntime = MapUtils.get(identMap, "begntime"); // 开始时间
                    String endtime = MapUtils.get(identMap, "endtime"); // 结算时间
                    if (!StringUtils.isEmpty(endtime)) {
                        if (StringUtils.isEmpty(begntime)) {
                            continue;
                        }
                        Date begnDate = DateUtils.parse(DateUtils.calculateDate(begntime, -1), DateUtils.Y_M_D);

                        if (StringUtils.isEmpty(endtime)) {
                            endtime = "2099-12-31 00:00:00";
                        }
                        Date endDate = DateUtils.parse(DateUtils.calculateDate(endtime, 1), DateUtils.Y_M_D);

                        if (DateUtils.getNow().before(endDate) && DateUtils.getNow().after(begnDate)) {
                            isOneSettle = Constants.SF.S;
                            insureIndividualVisitDTO.setPsnIdetType(psnIdetType);
                            insureIndividualVisitDTO.setIdetStartDate(begnDate);
                            insureIndividualVisitDTO.setIdetEndDate(endDate);
                            psnIdetTypes = psnIdetTypes + psnIdetType + ",";
                            psnIdetTypesName = psnIdetTypesName + psnIdetTypeName + ",";
                            //break;
                        }
                    }
                }
              psnIdetTypes = psnIdetTypes.substring(0,psnIdetTypes.length()-1);
              psnIdetTypesName = psnIdetTypesName.substring(0,psnIdetTypesName.length()-1);
              insureIndividualVisitDTO.setPsnIdetType(psnIdetTypes);
              insureIndividualVisitDTO.setPsnIdetTypeName(psnIdetTypesName);
            }

            insureIndividualVisitDTO.setIsOneSettle(isOneSettle);
            insureIndividualVisitDTO.setOmsgid(omsgid);
            insureIndividualVisitDTO.setOinfno(oinfno);
            insureIndividualVisitDTO.setId(SnowflakeUtils.getId());
            insureIndividualVisitDTO.setHospCode(inptVisitDTO.getHospCode());
            insureIndividualVisitDTO.setVisitId(inptVisitDTO.getId());
            insureIndividualVisitDTO.setMibId(baseId);
            insureIndividualVisitDTO.setInsureOrgCode(inptVisitDTO.getInsureOrgCode());
            insureIndividualVisitDTO.setInsureRegCode(inptVisitDTO.getInsureOrgCode());
            insureIndividualVisitDTO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());
            insureIndividualVisitDTO.setIsHospital(Constants.SF.S);
            insureIndividualVisitDTO.setVisitNo(inptVisitDTO.getInNo());
            insureIndividualVisitDTO.setAac001(insureIndividualBasicDTO.getAac001());
            insureIndividualVisitDTO.setMedicalRegNo(medicalegNo);
            insureIndividualVisitDTO.setAka130(insureIndividualBasicDTO.getAka130());
            insureIndividualVisitDTO.setAka130Name(String.valueOf(dictNameMap.get("aka130Name")));
            insureIndividualVisitDTO.setBka006(insureIndividualBasicDTO.getBka006());
            String bka006Name =String.valueOf(dictNameMap.get("bka006Name"));
            if("null".equals(bka006Name) || StringUtils.isEmpty(bka006Name) ){
                insureIndividualVisitDTO.setBka006Name("");
            }else{
                insureIndividualVisitDTO.setBka006Name(bka006Name);
            }
            insureIndividualVisitDTO.setInjuryBorthSn(inptVisitDTO.getInjuryBorthSn()); // 业务申请号
            insureIndividualVisitDTO.setVisitIcdCode(icdCode.substring(0, icdCode.length() - 1));
            insureIndividualVisitDTO.setVisitIcdName(icdName.substring(0, icdName.length() - 1));
            insureIndividualVisitDTO.setVisitTime(inptVisitDTO.getInTime());
            insureIndividualVisitDTO.setVisitDrptId(inptVisitDTO.getInDeptId());
            insureIndividualVisitDTO.setVisitDrptName(inptVisitDTO.getInDeptName());
            insureIndividualVisitDTO.setVisitAreaId(inptVisitDTO.getInWardId());
            insureIndividualVisitDTO.setVisitAreaName(inWardName);
            insureIndividualVisitDTO.setVisitBerth(inptVisitDTO.getBedName());
            insureIndividualVisitDTO.setRemark(inptVisitDTO.getInRemark());
            insureIndividualVisitDTO.setCrteId(user.getId());
            insureIndividualVisitDTO.setCrteName(user.getName());
            insureIndividualVisitDTO.setCrteTime(DateUtils.getNow());
            insureIndividualVisitDTO.setInjuryBorthSn(insureIndividualBasicDTO.getInjuryBorthSn());
            //  统一支付平台需要的值 start
            insureIndividualVisitDTO.setInsuplcAdmdvs(insureIndividualBasicDTO.getInsuplc_admdvs()); // 参保地医保区划
            insureIndividualVisitDTO.setMdtrtCertType(insureIndividualBasicDTO.getBka895()); // 就诊凭证类型
            insureIndividualVisitDTO.setMdtrtCertNo(insureIndividualBasicDTO.getBka896());  // 就诊凭证号码
            insureIndividualVisitDTO.setHcardBasinfo(insureIndividualBasicDTO.getHcardBasinfo()); //广州读卡就诊基本填信息
            insureIndividualVisitDTO.setHcardChkinfo(insureIndividualBasicDTO.getHcardChkinfo()); //广州读卡就诊校验信息
            insureIndividualVisitDTO.setRepeatIptFlag(inptVisitDTO.getRepeatIptFlag());//重复住院标志
            // 统一支付平台需要的值 end

            Map<String, Object> insertMap = new HashMap<>();
            insertMap.put("hospCode", inptVisitDto.getHospCode());
            insertMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
            if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
                // 处理当前就诊人员的人员累计信息
                this.dealCumInfo(insureIndividualVisitDTO, inptVisitDTO);
            }
            return insureIndividualVisitService_consumer.insertIndividualVisit(insertMap);
        } catch (Exception e) { // TODO 出现异常，医保端取消入院登记
            inptVisitDTO.setMedicalRegNo(medicalegNo);
            InsureInptOutFeeDTO insureInptOutFeeDTO = new InsureInptOutFeeDTO();
            insureInptOutFeeDTO.setHospCode(insureInptRegisterDTO.getHospCode());
            insureInptOutFeeDTO.setInsureOrgCode(insureInptRegisterDTO.getInsureOrgCode());
            insureInptOutFeeDTO.setAaz217(medicalegNo);
            insureInptOutFeeDTO.setAkb020(insureInptRegisterDTO.getAkb020());

            // 长沙医保参数
            insureInptOutFeeDTO.setAac001(insureInptRegisterDTO.getAac001());
            insureInptOutFeeDTO.setBka015(inptVisitDTO.getCrteName());

            insureParam.put("hospCode", insureInptRegisterDTO.getHospCode());
            insureParam.put("insureInptOutFeeDTO", insureInptOutFeeDTO);
//            if (sys != null && sys.getValue().equals("1")) {  // 调用统一支付平台
            if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {  // 调用统一支付平台
                /**统一支付平台调用   开始*/
                Map<String, Object> insureUnifiedPayParam = new HashMap<>();
                insureUnifiedPayParam.put("hospCode", insureInptRegisterDTO.getHospCode());
                insureUnifiedPayParam.put("insureInptOutFeeDTO", insureInptOutFeeDTO);
//                insureUnifiedPayInptService_consumer.UP_2404(insureUnifiedPayParam);
                /**统一支付平台调用   结束*/
            } else {
                if (!Constants.BRLX.SNYDBR.equals(inptVisitDTO.getPatientCode()) && !Constants.BRLX.SWYDBR.equals(inptVisitDTO.getPatientCode())) {
                    inptService_consumer.deteleInptRegister(insureParam);
                } else {
                    InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO = new InsureRemoteInptRegisterDTO();
                    insureRemoteInptRegisterDTO.setAkb020(insureInptOutFeeDTO.getInsureOrgCode());
                    insureRemoteInptRegisterDTO.setAaz217(medicalegNo);
                    insureRemoteInptRegisterDTO.setHospCode(insureInptOutFeeDTO.getHospCode());
                    insureRemoteInptRegisterDTO.setAae011(inptVisitDTO.getCrteId());
                    insureRemoteInptRegisterDTO.setBka015(inptVisitDTO.getCrteName());
                    insureRemoteInptRegisterDTO.setInsureOrgCode(insureInptOutFeeDTO.getInsureOrgCode());
                    insureParam.put("insureRemoteInptRegisterDTO", insureRemoteInptRegisterDTO);
                    inptService_consumer.deteleRemoteInptRegister(insureParam);
                }
            }
            throw new AppException("医保登记失败：" + e.getMessage());
        }
    }

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 获取医保登记人员信息
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/22 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @Override
    public PageDTO queryInsureRegisterPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> list = inptVisitDAO.queryInsureRegisterPage(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @Description: 获取选择的农合患者信息保存到医院本地数据库
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/19 19:27
     * @Return
     */
    @Override
    public Boolean saveNHInsureData(InptVisitDTO inptVisitDTO) {
        // 准备需要下发的数据格式 1、患者就诊信息，2、患者费用信息
        if (inptVisitDTO.getIds() == null || inptVisitDTO.getIds().size() == 0) {
            throw new AppException("未获取到需要下发本地的患者id，请选择需要下发的患者");
        }

        // 获取医院kafka 的IP与端口
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", inptVisitDTO.getHospCode());
        sysMap.put("code", "KAFKA_IP");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (sys == null || sys.getValue() == null) {  // 调用统一支付平台
            throw new AppException("请在系统参数中配置医院医保中间件所在主机IP与端口 【KAFKA_IP】");
        }

        // 1、根据勾选的结算单号查询患者信息
        List<Map<String, Object>> nhPatient = inptVisitDAO.selectNHPatientData(inptVisitDTO);
        // 2、根据勾选的结算单号查询患者费用信息
        List<Map<String, Object>> nhPatientCost = inptVisitDAO.selectNHPatientCostData(inptVisitDTO);

        // 调用kafka 发送消息
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("mark", "NH");  // 农合标记，用于医院本地执行时区分方法
        map.put("nhPatient", nhPatient);
        map.put("nhPatientCost", nhPatientCost);
        map.put("ids", inptVisitDTO.getIds());
        map.put("KAFKA_IP", sys.getValue());
        insureNHPatientService_consumer.saveNHPatientToLocal(map);

        return true;
    }

    /**
     * @Description:农合
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/21 19:51
     * @Return
     */
    @Override
    public Boolean saveNHInsureDrugItemData(Map map) {
        String mark = MapUtils.get(map, "mark");
        // 获取医院kafka 的IP与端口
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "KAFKA_IP");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (sys == null || sys.getValue() == null) {  // 调用统一支付平台
            throw new AppException("请在系统参数中配置医院医保中间件所在主机IP与端口 【KAFKA_IP】");
        }
        List<Map<String, Object>> drugAndItem = new ArrayList<>();
        if (mark != null && !"".equals(mark)) {
            if ("material".equals(mark)) {
                drugAndItem = inptVisitDAO.saveMaterialData(map);
            } else if ("drug".equals(mark)) {
                drugAndItem = inptVisitDAO.saveDrugData(map);
            } else if ("item".equals(mark)) {
                drugAndItem = inptVisitDAO.saveItemData(map);
            }

            // 调用kafka 发送消息
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("hospCode", MapUtils.get(map, "hospCode"));
            resultMap.put("mark", mark); // 区分是药品、项目、材料
            resultMap.put("drugAndItem", drugAndItem);  // 农合标记，用于医院本地执行时区分方法
            resultMap.put("KAFKA_IP", sys.getValue());
            insureNHPatientService_consumer.saveNHPatientToLocal(resultMap);
        } else {
            return false;
        }
        return true;
    }

    /**
     * @param inptVisitDTO
     * @Description: 查询当前科室病人以及以前转科的病人用于退费管理查询，
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    @Override
    public PageDTO queryInptVisitAndZkPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryInptVisitAndZkPage(inptVisitDTO);
        return PageDTO.of(inptVisitDTOS);
    }


    /**
     * 科室编码
     *
     * @param hospCode
     * @param id
     * @return
     */
    private BaseDeptDTO getDeptInfo(String hospCode, String id) {
        if (StringUtils.isEmpty(id)) {
            return new BaseDeptDTO();
        }
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("hospCode", hospCode);
        selectMap.put("id", id);
        BaseDeptDTO baseDeptDTO = inptVisitDAO.getDeptCodeByDeptId(selectMap);
        return baseDeptDTO;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param hospCode
     * @param id
     * @return
     */
    private SysUserDTO getUserInfo(String hospCode, String id) {
        if (StringUtils.isEmpty(id)) {
            return new SysUserDTO();
        }
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("hospCode", hospCode);
        selectMap.put("id", id);
        return inptVisitDAO.getUserInfoById(selectMap);
    }

    /**
     * 获取出生日期，格式 YYYYMMDD
     *
     * @param birthday
     * @return
     */
    private String getDay(Date birthday) {
        if (birthday == null) {
            return "";
        }
        return DateUtils.SDF_YMD.format(birthday);
    }

    /**
     * 获取性别名称
     */
    private String getGenderName(String genderCode) {
        String genderName = "";
        if (StringUtils.isNotEmpty(genderCode)) {
            switch (genderCode) {
                case "0":
                    genderName = "未知的性别";
                    break;

                case "1":
                    genderName = "男";
                    break;

                case "2":
                    genderName = "女";
                    break;

                case "9":
                    genderName = "未说明的性别";
                    break;
            }
        }
        return genderName;
    }

    /**
     * @Method insert
     * @Desrciption 1.入院登记 住院就诊表增加一条记录  修改status_code 为1 待入院状态
     * 2.门诊就诊表的转入院代码转为2 已登记住院
     * 2.预交金管理增加一条记录
     * 3.档案表，查询是否有此人档案，再查询档案从表是否该档案有该医院对应的档案从表记录
     * 如果有，更新最后住院时间以及累计住院次数，以及修改档案中非必填字段（如果登记时修改了）
     * 如果没有档案，新建档案以及新建档案从表记录
     * 如果有档案无从表，新建档案从表记录，以及修改档案中非必填字段（如果登记时修改了）
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/17 9:17
     * @Return java.lang.Boolean
     * @update by 廖继广 on 2020/11/03  医保入院登记代码嵌入,原代码格式调整
     **/
    private String insert(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getInDiseaseId())) {
            throw new AppException("入院诊断不可为空");
        }
        if (StringUtils.isEmpty(inptVisitDTO.getInDeptName())) {
            throw new AppException("入院科室名称不可为空");
        }

        // 档案表操作
        OutptProfileFileDTO extend = getProfileFileDTO(inptVisitDTO);
        // 20210805 liuliyun 珠海住院号生成 start
        Map sysParam = new HashMap();
        sysParam.put("hospCode", inptVisitDTO.getHospCode());
        sysParam.put("code", "BAH_SF");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParam).getData();
        // 未配置系统参数时，添加默认值
        if (sysParameterDTO == null) {
            sysParameterDTO = new SysParameterDTO();
            sysParameterDTO.setValue("0");
        }
        //设置住院号
        String orderNo = getOrderNo(inptVisitDTO.getHospCode(), Constants.ORDERRULE.ZYH);
        inptVisitDTO.setInNo(orderNo);

        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && sysParameterDTO.getValue().equals("1")) {
            // 住院次数获取(未获取，默认1次)
            Map countMap = new HashMap();
            countMap.put("hospCode", inptVisitDTO.getHospCode());
            countMap.put("certCode", inptVisitDTO.getCertCode());
            countMap.put("certNo", inptVisitDTO.getCertNo());
            List<Map> info = inptVisitDAO.getBaseProfileInfo(countMap);
            int inCnt = 1;
            if (info == null || info.size() == 0) {
                inCnt = 1;
                orderNo = extend.getInProfile() + "-00" + inCnt;
            } else {
                if (info.get(0).get("total_in") != null) {
                    inCnt = (int) info.get(0).get("total_in");
                    inCnt = inCnt + 1;
                }
                orderNo = info.get(0).get("in_profile") + "-00" + inCnt;
            }
            inptVisitDTO.setInNo(orderNo);
        }
        // 20210805 liuliyun 珠海住院号生成 end
        //住院就诊表增加一条记录
        //设置ID
        String id = SnowflakeUtils.getId();
        inptVisitDTO.setId(id);

        //设置病案号/住院档案号
        inptVisitDTO.setInProfile(extend.getInProfile());

        //设置档案中的住院次数到就诊表中 update 2022-03-04 luoyong 入院登记时写入住院次数到就诊表
        inptVisitDTO.setTotalInCount(extend.getTotalIn());

        //设置个人档案ID
        inptVisitDTO.setProfileId(extend.getId());

        //设置 '累计费用'
        inptVisitDTO.setTotalCost(BigDecimalUtils.convert("0"));
        // 入院登记防止页面带入出院信息进来 liuliyun 2022-08-02
        inptVisitDTO.setOutDiseaseId(null);
        inptVisitDTO.setOutDiseaseName(null);
        inptVisitDTO.setOutDiseaseIcd10(null);
        inptVisitDTO.setOutWardId(null);
        inptVisitDTO.setOutDeptId(null);
        inptVisitDTO.setOutDeptName(null);
        inptVisitDTO.setOutTime(null);
        inptVisitDTO.setOutOperId(null);
        inptVisitDTO.setOutOperName(null);
        inptVisitDTO.setOutOperTime(null);
        inptVisitDTO.setIllnessCode(null); // 病情标识（医嘱回写）
        // 入院登记防止页面带入出院信息进来 liuliyun 2022-08-02

        //设置status_code 为1 待入院状态
        inptVisitDTO.setStatusCode("1");
        int i = inptVisitDAO.insertInptVisit(inptVisitDTO);
        if (i <= 0) {
            throw new AppException("登记失败");
        }
        // 入院登记信息写入消息
        insertUnInbedPatientList(inptVisitDTO);
        // 修改转入院标志
        if (StringUtils.isNotEmpty(inptVisitDTO.getOutptId())) {
            updateTranInCode(inptVisitDTO);
        }

        //预交金管理增加一条记录
        if (inptVisitDTO.getPrice() != null) {
            if (StringUtils.isEmpty(inptVisitDTO.getPayCode())) {
                throw new AppException("请先选择预交金方式再提交");
            }
            insertInptAdvance(inptVisitDTO);
        }

        //住院就诊表增加一条记录
        insertInptDiagnose(inptVisitDTO);
        return id;
    }

    // 修改门诊转入院状态
    public void updateTranInCode(InptVisitDTO inptVisitDTO) {
        //门诊就诊表的转入院代码转为 2-已登记住院
        Map map = new HashMap();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setTranInCode("2");
        outptVisitDTO.setHospCode(inptVisitDTO.getHospCode());
        outptVisitDTO.setId(inptVisitDTO.getOutptId());
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("outptVisitDTO", outptVisitDTO);
        WrapperResponse<Boolean> wr = outptVisitService_consumer.updateTranInCode(map);
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        if (!wr.getData()) {
            throw new AppException("修改转入院状态失败，请联系管理员进行处理");
        }
    }

    // 插入就诊表
    public void insertInptDiagnose(InptVisitDTO inptVisitDTO) {
        InptDiagnoseDTO inptDiagnoseDTO = new InptDiagnoseDTO();
        inptDiagnoseDTO.setId(SnowflakeUtils.getId());
        inptDiagnoseDTO.setHospCode(inptVisitDTO.getHospCode());
        inptDiagnoseDTO.setDiseaseId(inptVisitDTO.getInDiseaseId());
        inptDiagnoseDTO.setVisitId(inptVisitDTO.getId());
        inptDiagnoseDTO.setTypeCode(Constants.ZDLX.ZYRYZD);
        inptDiagnoseDTO.setIsMain(Constants.SF.S);
        inptDiagnoseDTO.setCrteId(inptVisitDTO.getCrteId());
        inptDiagnoseDTO.setCrteName(inptVisitDTO.getCrteName());
        Boolean flag = inptVisitDAO.insertDiagnose(inptDiagnoseDTO);
        if (!flag) {
            throw new AppException("插入入院诊断失败，请联系管理员进行处理");
        }
    }

    // 插入预交金
    private void insertInptAdvance(InptVisitDTO inptVisitDTO) {
        if (BigDecimalUtils.nullToZero(inptVisitDTO.getPrice()).intValue() != 0) {
            //预交金管理增加一条记录
            InptAdvancePayDTO advancePayDTO = new InptAdvancePayDTO();
            advancePayDTO.setId(SnowflakeUtils.getId());
            advancePayDTO.setHospCode(inptVisitDTO.getHospCode());
            advancePayDTO.setVisitId(inptVisitDTO.getId());
            //advancePayDTO.setApOrderNo(getOrderNo(inptVisitDTO.getHospCode(), Constants.ORDERRULE.YJJ));
            advancePayDTO.setPrice(inptVisitDTO.getPrice());
            advancePayDTO.setIsSettle(Constants.SF.F);
            advancePayDTO.setStatusCode(Constants.ZTBZ.ZC);
            // 支付来源：HIS、微信、支付宝、自助机
            advancePayDTO.setSourcePayCode(Constants.ZFLY.HIS);
            advancePayDTO.setPayCode(inptVisitDTO.getPayCode());
            // 手续费：TODO 需从系统参数中获取手续费比例计算
            advancePayDTO.setServicePrice(BigDecimal.ZERO);
            // 微信支付宝
            if (Constants.ZFFS.WX.equals(inptVisitDTO.getPayCode()) || Constants.ZFFS.ZFB.equals(inptVisitDTO.getPayCode())) {
                inptVisitDTO.setOrderNo(inptVisitDTO.getChequeNo());
            }
            // 支票
            else if (Constants.ZFFS.ZP.equals(inptVisitDTO.getPayCode())) {
                advancePayDTO.setChequeNo(inptVisitDTO.getChequeNo());
            }
            advancePayDTO.setCrteId(inptVisitDTO.getCrteId());
            advancePayDTO.setCrteName(inptVisitDTO.getCrteName());
            advancePayDTO.setCrteTime(DateUtils.getNow());
            boolean insert = advancePayBO.insert(advancePayDTO);
            if (!insert) {
                throw new AppException("新增预交金失败，请联系管理员进行处理");
            }
        }
    }

    // 单号生成
    private String getOrderNo(String hospCode, String typeCode) {
        //设置住院号
        Map map = new HashMap();
        map.put("typeCode", typeCode);
        map.put("hospCode", hospCode);

        WrapperResponse<String> wr = baseOrderRuleService_consumer.getOrderNo(map);
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        return wr.getData();
    }

    // 新增档案
    private OutptProfileFileDTO getProfileFileDTO(InptVisitDTO inptVisitDTO) {
        // 档案表
        OutptProfileFileDTO pf = new OutptProfileFileDTO();
        pf.setType("1");
        pf.setName(inptVisitDTO.getName());
        pf.setHospCode(inptVisitDTO.getHospCode());
        pf.setId(inptVisitDTO.getProfileId());
        pf.setGenderCode(inptVisitDTO.getGenderCode());
        pf.setAge(inptVisitDTO.getAge());
        pf.setAgeUnitCode(inptVisitDTO.getAgeUnitCode());
        pf.setBirthday(inptVisitDTO.getBirthday());
        pf.setNationCode(inptVisitDTO.getNationCode());
        pf.setMarryCode(inptVisitDTO.getMarryCode());
        pf.setPhone(inptVisitDTO.getPhone());
        pf.setName(inptVisitDTO.getName());
        pf.setCertNo(inptVisitDTO.getCertNo());
        pf.setCertCode(StringUtils.isEmpty(inptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : inptVisitDTO.getCertCode());
        pf.setNationalityCation(inptVisitDTO.getNationalityCation());
        pf.setOccupationCode(inptVisitDTO.getOccupationCode());
        pf.setEducationCode(inptVisitDTO.getEducationCode());
        pf.setContactRelaCode(inptVisitDTO.getContactRelaCode());
        pf.setContactName(inptVisitDTO.getContactName());
        pf.setContactPhone(inptVisitDTO.getContactPhone());
        pf.setContactAddress(inptVisitDTO.getContactAddress());
        pf.setCrteId(inptVisitDTO.getCrteId());
        pf.setCrteName(inptVisitDTO.getCrteName());
        pf.setCrteTime(DateUtils.getNow());
        pf.setContactAddress(inptVisitDTO.getAddress());
        pf.setPatientCode(inptVisitDTO.getPatientCode());
        pf.setPreferentialTypeId(inptVisitDTO.getPreferentialTypeId());
//        WrapperResponse<OutptProfileFileExtendDTO> wr = outptProfileFileService_consumer.save(pf);

        //调用本地建档服务
        log.debug("入出院登记调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
        Map localMap = new HashMap();
        localMap.put("hospCode", inptVisitDTO.getHospCode());
        localMap.put("outptProfileFileDTO", pf);
        WrapperResponse<OutptProfileFileDTO> wr = baseProfileFileService_consumer.save(localMap);


        log.debug("入出院登记调用本地建档服务结束：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        OutptProfileFileDTO outptProfileFileDTO = wr.getData();
        if (outptProfileFileDTO == null) {
            throw new AppException("获取档案返回信息失败，请联系管理员");
        }
        return outptProfileFileDTO;
    }

    /**
     * @Method queryProfileS
     * @Desrciption 根据ids查询档案
     * @Param [ids]
     * @Author liaojunjie
     * @Date 2020/12/22 21:50
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    public List<OutptProfileFileDTO> queryProfileS(List<String> ids, String hospCode) {
        OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
        outptProfileFileDTO.setHospCode(hospCode);
        outptProfileFileDTO.setIds(ids);
        // 查询出门诊就诊表信息对应的档案信息
        WrapperResponse<List<OutptProfileFileDTO>> wr = outptProfileFileService_consumer.queryAll(outptProfileFileDTO);
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        List<OutptProfileFileDTO> data = wr.getData();
        return data;
    }


    /**
     * @Method update
     * @Desrciption 修改已登记住院信息
     * 1.修改住院病人表
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/17 9:42
     * @Return java.lang.Boolean
     **/
    private String update(InptVisitDTO inptVisitDTO) {
        //查询住院已登记患者
        InptVisitDTO selectEntiey = inptVisitDAO.getInptVisitById(inptVisitDTO);
        //查询住院未登记患者信息
        Map<String, String> param = new HashMap<String, String>();
        param.put("hospCode", inptVisitDTO.getHospCode());//医院编码
        param.put("id", inptVisitDTO.getId());//就诊id
        OutptVisitDTO outptVisitDTO = outptVisitService_consumer.queryByVisitID(param);
        if (outptVisitDTO == null && selectEntiey == null) {
            throw new AppException("修改患者信息失败：门诊/住院均未找到患者就诊信息【就诊id:" + inptVisitDTO.getId() + "】");
        }
        // 修改入院登记信息，使用数据库中的病人状态 20211015
        if (selectEntiey != null) {
            inptVisitDTO.setStatusCode(selectEntiey.getStatusCode());
            inptVisitDTO.setBedId(selectEntiey.getBedId());
            inptVisitDTO.setBedName(selectEntiey.getBedName());
        }
//        param.put("code", "UNIFIED_PAY");
//        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(param).getData();
        // 获取医保个人信息
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", inptVisitDTO.getId());
        insureVisitParam.put("hospCode", inptVisitDTO.getHospCode());
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(insureVisitParam);

        if (insureIndividualVisitDTO != null) {
            // 根据医院编码、医保注册编码查询医保配置信息
            InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
            configDTO.setHospCode(inptVisitDTO.getHospCode()); //医院编码
            configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode()); // 医保注册编码
            configDTO.setIsValid(Constants.SF.S); // 是否有效
            Map configMap = new LinkedHashMap();
            configMap.put("hospCode", inptVisitDTO.getHospCode());
            configMap.put("insureConfigurationDTO", configDTO);
            List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
            if (ListUtils.isEmpty(configurationDTOList)) {
                throw new RuntimeException("未找到医保机构，请重新获取人员信息。");
            }
            InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
            // 获取该医保配置是否走统一支付平台，1走，0/null不走
            String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
            if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay) && !"0".equals(inptVisitDTO.getPatientCode())) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("hospCode", inptVisitDTO.getHospCode());
                dataMap.put("inptVisitDTO", inptVisitDTO);
                insureIndividualVisitService_consumer.updateInsureInidivdual(dataMap).getData();
            } else {
                throw new AppException("当前已完成医保登记，请先取消医保登记");
            }
        }

        //入院时间不能早于最小费用产生时间
        InptCostDTO minInptCost = inptVisitDAO.getMinTimeCostByVisitId(inptVisitDTO);
        if (minInptCost != null && DateUtils.dateCompare(minInptCost.getCostTime(), inptVisitDTO.getInTime())) {
            throw new AppException("入院时间不能大于最小费用时间：" + DateUtils.format(minInptCost.getCostTime(), DateUtils.Y_M_DH_M_S));
        }

        //入院时间不能早于最小医嘱开始时间
        InptAdviceDTO minInptAdvice = inptVisitDAO.getMinTimeInptAdviceByVisitId(inptVisitDTO);
        if (minInptAdvice != null && DateUtils.dateCompare(minInptAdvice.getLongStartTime(), inptVisitDTO.getInTime())) {
            throw new AppException("入院时间不能大于最小医嘱时间：" + DateUtils.format(minInptAdvice.getLongStartTime(), DateUtils.Y_M_DH_M_S));
        }

        // add by 张国瑞； 修改之前 比对入院科室是否一致，如果当前病人 已经安床住院且前端传过来的入院科室不一致，那么抛出异常
        String inDeptId = inptVisitDTO.getInDeptId();
        String inDeptIdForDataBase = selectEntiey.getInDeptId();
        if (!Constants.BRZT.DR.equals(selectEntiey.getStatusCode()) && inDeptId != null && !inDeptId.equals(inDeptIdForDataBase)) {
            throw new AppException("该病人不是待入, 不可修改入院科室");
        }
        return Integer.toString(inptVisitDAO.updateInptVisit(inptVisitDTO));
    }

    /**
     * @Method: updateVisit
     * @Description: 更新住院病人表
     * @Param: [inptVisitDO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 15:40
     * @Return: boolean
     **/
    @Override
    public boolean updateInptVisit(InptVisitDTO inptVisitDTO) {
        return inptVisitDAO.updateInptVisit(inptVisitDTO) > 0;
    }

    /**
     * @Method: getVisitByAdviceId
     * @Description: 通过医嘱ID获取住院病人信息
     * @Param: [adviceIds, hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **/
    @Override
    public List<InptVisitDTO> getVisitByAdviceId(List<String> adviceIds, String hospCode) {
        if (ListUtils.isEmpty(adviceIds)) {
            return null;
        }
        return inptVisitDAO.getVisitByAdviceId(adviceIds, hospCode);
    }

    private Map getDictNameMapByCodeV(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        Map<String, Object> resultMap = new HashMap();
        Map<String, Object> selectmap = new HashMap();

        // TODO 自定义规则。按顺序写入
        String codes[] = {"YWLX_ZY", "DYLX_ZY", "aac148", "aac013", "akc193"};
        String values[] = {insureIndividualBasicDTO.getAka130(), insureIndividualBasicDTO.getBka006(), insureIndividualBasicDTO.getAac148(), insureIndividualBasicDTO.getAac013(), insureIndividualBasicDTO.getAkc193()};
        String names[] = {"aka130Name", "bka006Name", "aac148Name", "aac013Name", "akc193Name"};

        InsureDictDTO insureDictEntity = new InsureDictDTO();
        insureDictEntity.setCodes(codes);
        insureDictEntity.setHospCode(insureIndividualBasicDTO.getHospCode());
        insureDictEntity.setInsureRegCode(insureIndividualBasicDTO.getAkb020());
        selectmap.put("insureDictDTO", insureDictEntity);
        selectmap.put("hospCode", insureIndividualBasicDTO.getHospCode());

        WrapperResponse wr = insureDictService_consumer.queryInsureDictList(selectmap);
        if (wr.getData() != null) {
            Map<String, Object> dataMap = (Map<String, Object>) wr.getData();
            List<InsureDictDO> list = (List<InsureDictDO>) (dataMap.get("insureDict"));
            if (!ListUtils.isEmpty(list)) {
                for (int i = 0; i < codes.length; i++) {
                    for (InsureDictDO insureDictDO : list) {
                        if (codes[i].equals(insureDictDO.getCode()) && values[i].equals(insureDictDO.getValue())) {
                            resultMap.put(names[i], insureDictDO.getName());
                            break;
                        }
                    }
                }
            }
        }
        return resultMap;
    }


    /**
     * 医保获取人员信息保存
     *
     * @param inptVisitDTO
     */
    private void insetInsureIndividualBasic(InptVisitDTO inptVisitDTO, String id, Map dictNameMap) {
        InsureIndividualBasicDTO insureIndividualBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        if (insureIndividualBasicDTO == null) {
            throw new AppException("未获取到就诊人员医保业务参数信息");
        }
        if (StringUtils.isEmpty(insureIndividualBasicDTO.getAkb020())) {
            insureIndividualBasicDTO.setAkb020(insureIndividualBasicDTO.getInsureRegCode());
        }
        insureIndividualBasicDTO.setId(id);
        insureIndividualBasicDTO.setHospCode(inptVisitDTO.getHospCode());
        insureIndividualBasicDTO.setCrteId(inptVisitDTO.getCrteId());
        insureIndividualBasicDTO.setCrteName(inptVisitDTO.getCrteName());
        insureIndividualBasicDTO.setCrteTime(DateUtils.getNow());
        insureIndividualBasicDTO.setAka130Name(MapUtils.get(dictNameMap, "aka130Name"));
        insureIndividualBasicDTO.setBka006Name(MapUtils.get(dictNameMap, "bka006Name"));
        insureIndividualBasicDTO.setAac148Name(MapUtils.get(dictNameMap, "aac148Name"));
        insureIndividualBasicDTO.setAac013Name(MapUtils.get(dictNameMap, "aac013Name"));
        insureIndividualBasicDTO.setBaa027Name(MapUtils.get(dictNameMap, "baa027Name"));
        insureIndividualBasicDTO.setAkc193Name(MapUtils.get(dictNameMap, "akc193Name"));
        insureIndividualBasicDTO.setBaa027Name(insureIndividualBasicDTO.getBaa027_name());
        insureIndividualBasicDTO.setAkc252(BigDecimalUtils.convert(insureIndividualBasicDTO.getBacu18()));
        insureIndividualBasicDTO.setAae005(inptVisitDTO.getPhone());
        Map params = new HashMap();
        params.put("hospCode", inptVisitDTO.getHospCode());
        params.put("insureIndividualBasicDTO", insureIndividualBasicDTO);
        insureIndividualBasicService_consumer.insertInsureIndividualBasic(params);
    }

    /**
     * @Method: inRegisterByArtificial
     * @Description: 手工登记入院
     * @Param: map
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2021/3/18 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **//*
    @Override
    public Boolean inRegisterByArtificial(InptVisitDTO inptVisitDTO) {
        // 获取入院登记信息
        InptVisitDTO inptVisitDto = inptVisitDAO.getInptVisitById(inptVisitDTO);
        if (inptVisitDto == null) {
            throw new AppException("未查询到病人入院登记信息");
        }
        return true;
    }*/

    /**
     * @Method: insertUnInbedPatientList
     * @Description: 入院登记写入消息
     * @Param: inptVisitDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/12/02 15:53
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **/
    public List<MessageInfoDTO> insertUnInbedPatientList(InptVisitDTO inptVisitDTO) {
        String hospCode = inptVisitDTO.getHospCode();
        String name = inptVisitDTO.getCrteName();
        String crteId = inptVisitDTO.getCrteId();
        Map openParam = new HashMap();
        openParam.put("hospCode", inptVisitDTO.getHospCode());
        openParam.put("code", "MSG_OPEN");
        // 是否开启消息推送 lly 2021-12-02
        SysParameterDTO openSysParameterDTO = sysParameterService_consumer.getParameterByCode(openParam).getData();
        List<MessageInfoDTO> messageInfoDTOList = new ArrayList<>();
        if (openSysParameterDTO != null && "1".equals(openSysParameterDTO.getValue())) {
            Map paramMap = new HashMap();
            paramMap.put("hospCode", hospCode);
            paramMap.put("code", "XXTS_SETTING");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(paramMap).getData();
            ConfigInfoDO configInfoDO = null;
            if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(), "inHospitalMsg");
            }
            if (configInfoDO == null) {
                return new ArrayList<>();
            }
            if (inptVisitDTO != null && configInfoDO != null) {
                MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                messageInfoDTO.setId(SnowflakeUtils.getId());
                messageInfoDTO.setHospCode(hospCode);
                messageInfoDTO.setSourceId("");
                messageInfoDTO.setVisitId(inptVisitDTO.getId());
                // 推送到科室
                if ("1".equals(configInfoDO.getIsPersonal())) {
                    messageInfoDTO.setReceiverId("");
                    messageInfoDTO.setDeptId(inptVisitDTO.getInDeptId());
                } else if ("0".equals(configInfoDO.getIsPersonal())) {
                    // 推送到个人
                    messageInfoDTO.setDeptId("");
                    messageInfoDTO.setReceiverId(configInfoDO.getReceiverId());
                } else {
                    // 默认推送到科室
                    messageInfoDTO.setReceiverId("");
                    messageInfoDTO.setDeptId(inptVisitDTO.getInDeptId());
                }
                messageInfoDTO.setLevel(configInfoDO.getLevel());
                messageInfoDTO.setSendCount(configInfoDO.getSendCount());
                messageInfoDTO.setType(Constants.MSG_TYPE.MSG_AC);
                messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_WD);
                messageInfoDTO.setContent(inptVisitDTO.getName() + "已办理入院登记，请及时安床");
                Date startTime = DateUtils.dateAddMinute(new Date(), configInfoDO.getStartTime());
                Date endTime = DateUtils.dateAddMinute(startTime, configInfoDO.getEndTime());
                messageInfoDTO.setStartTime(startTime);
                messageInfoDTO.setEndTime(endTime);
                messageInfoDTO.setIntervalTime(configInfoDO.getIntervalTime());
                messageInfoDTO.setUrl(configInfoDO.getUrl());
                messageInfoDTO.setCrteName(name);
                messageInfoDTO.setCrteTime(DateUtils.getNow());
                messageInfoDTO.setCrteId(crteId);
                messageInfoDTOList.add(messageInfoDTO);
                //messageInfoDAO.insertMessageInfoBatch(messageInfoDTOList);
                // 获取医院kafka 的IP与端口
                Map<String, Object> sysMap = new HashMap<>();
                sysMap.put("hospCode", hospCode);
                sysMap.put("code", "KAFKA_MSG_IP");
                SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
                if (sys == null || sys.getValue() == null) {  // 调用统一支付平台
                    return messageInfoDTOList;
                }
                String server = sys.getValue();
                // 1. 创建一个kafka生产者
                String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//生产者消息推送Topic
                KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
                String message = JSONObject.toJSONString(messageInfoDTOList);
                KafkaUtil.sendMessage(kafkaProducer, producerTopic, message);
            }
        }
        return messageInfoDTOList;
    }

    public Boolean updateUplod(InptVisitDTO inptVisitDTO) {
        inptVisitDAO.updateUplod(inptVisitDTO);
        return true;
    }

}

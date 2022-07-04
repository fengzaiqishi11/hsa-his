package cn.hsa.outpt.register.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.emr.emrarchivelogging.entity.ConfigInfoDO;
import cn.hsa.module.outpt.card.dao.BaseCardRechargeChangeDAO;
import cn.hsa.module.outpt.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.outpt.card.service.BaseCardRechargeChangeService;
import cn.hsa.module.outpt.classes.dao.OutptClassesDAO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.module.outpt.outptclassify.dao.OutptClassifyDAO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dao.OutptClassifyClassesDAO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.queue.dao.OutptClassesQueueDao;
import cn.hsa.module.outpt.queue.dao.OutptDoctorQueueDao;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.bo.OutptRegisterBO;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.register.entity.*;
import cn.hsa.module.outpt.triage.dao.OutptTriageVisitDAO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.entity.OutptVisitDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.register.bo.impl
 * @Class_name: OutptRegisterBOImpl
 * @Describe: 门诊挂号逻辑实现层
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/11 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptRegisterBOImpl extends HsafBO implements OutptRegisterBO {

    /**
     * 注入门诊挂号dao层对象
     */
    @Resource
    OutptRegisterDAO outptRegisterDAO;
    @Resource
    OutptVisitDAO outptVisitDAO;
    @Resource
    OutptClassifyDAO outptClassifyDAO;
    @Resource
    OutptClassifyClassesDAO outptClassifyClassesDAO;
    /**  医生队列数据访问 **/
    @Resource
    OutptDoctorQueueDao outptDoctorQueueDao;
    /**  门诊班次队列数据访问 **/
    @Resource
    OutptClassesQueueDao outptClassesQueueDao;
    /** 班次数据访问 **/
    @Resource
    OutptClassesDAO outptClassesDAO;
    @Resource
    BaseItemService baseItemService;
    @Resource
    BaseMaterialService baseMaterialService;
    @Resource
    OutptProfileFileService outptProfileFileService;
    @Resource
    OutinInvoiceService outinInvoiceService;
    @Resource
    BasePreferentialService basePreferentialService;
    @Resource
    BaseOrderRuleService baseOrderRuleService;
    @Resource
    SysParameterService sysParameterService;
    @Resource
    OutinInvoiceService outinInvoiceService_consumer;
    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;
    @Resource
    private OutptTriageVisitDAO outptTriageVisitDao;
    @Resource
    private BaseCardRechargeChangeDAO baseCardRechargeChangeDAO;
    @Resource
    private BaseCardRechargeChangeService baseCardRechargeChangeService;


    /**
     * 划价收费
     */
    @Resource
    private OutptCostDAO outptCostDAO;

    /** 分诊单号类型代码 **/
    private final String triageOrderNoType = "106";
    /**
     * 注入系统编码Service层对象
     */
    /*@Resource
    SysCodeService sysCodeService;*/

    /**
     * @Menthod queryRegisterInfoByParamsPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. outptRegisterDTO  参数数据传输对象
     * @Author liaojiguang
     * @Date   2020/8/11 11:14
     * @Return cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryRegisterInfoByParamsPage(OutptRegisterDTO outptRegisterDTO) {
        // 设置分页信息
        PageHelper.startPage(outptRegisterDTO.getPageNo(), outptRegisterDTO.getPageSize());

        // 根据条件查询所有数据
        List<OutptRegisterDTO> outpoRegisterDTOs = outptRegisterDAO.queryRegisterInfoByParamsPage(outptRegisterDTO);
        return PageDTO.of(outpoRegisterDTOs);
    }

    /**
     * @Menthod updateOutRegister
     * @Desrciption 门诊退号
     *    <p>
     *        1.检查是否可以退号
     *        2.挂号信息表作废
     *        3.支付费用冲红
     *        4.挂号结算数据冲红
     *        5.挂号支付方式冲红
     *        6.更新病人就诊信息标志为作废
     *        7.解锁号源信息
     *    </p>
     * @Param [map] 挂号id,hospCode
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean> 成功/失败
     **/
    @Override
    public Boolean updateOutRegister(Map map) {
        String crteId = String.valueOf(map.get("crteId"));
        String crteName = String.valueOf(map.get("crteName"));
        OutptRegisterDO outptRegister =  outptRegisterDAO.selectByPrimaryKey(map);
        if (outptRegister == null) {
            throw new AppException("未找到挂号信息");
        }
        outptRegister.setCrteId(crteId);
        outptRegister.setCrteName(crteName);

        // 1.检查是否可以退号
        isFlagOutRegister(outptRegister);

        // 2.挂号信息表作废
        updateRegisterState(outptRegister);

        // 3.支付费用冲红
        registerDetailChangeRed(outptRegister);

        // 4.挂号结算数据冲红/发票作废
        String redSettleId = SnowflakeUtils.getId();
        registerSettleChangeRed(outptRegister,redSettleId);

        // 5.挂号支付方式冲红
        registerPayChangeRed(outptRegister,redSettleId);

        // 6.更新病人就诊信息标志为作废
        updatePatientState(outptRegister);

        // 7.解锁号源信息
        unBlockNumberInfo(outptRegister);

        return true;
    }


    @Override
    public Map<String, String> saveOutRegisterSettle(Map map) {
        String hospCode = MapUtils.get(map,"hospCode");
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map,"outptRegisterDTO");
        List<OutptRegisterDetailDto> regDetailList = outptRegisterDTO.getRegDetailList();
        OutptRegisterSettleDto outptRegisterSettleDto = MapUtils.get(map,"outptRegisterSettleDto");
        // 系统参数 是否强制选择具体医生挂号校验 liuliyun 2021/11/03
        Map doctorParam = new HashMap();
        doctorParam.put("hospCode", hospCode);
        doctorParam.put("code", "SF_REGISTER_DOCTOR");
        SysParameterDTO sysParameter = sysParameterService.getParameterByCode(doctorParam).getData();
        if(sysParameter!=null && "1".equals(sysParameter.getValue())) {
            if (outptRegisterDTO!=null){
                if (StringUtils.isEmpty(outptRegisterDTO.getDoctorId())){
                    throw  new AppException("挂号提示：请选择具体医生进行挂号！");
                }
            }
        }
        String cardNo = outptRegisterDTO.getCardNo(); // 一卡通卡号
        BigDecimal price = outptRegisterDTO.getCardPrice() != null ? outptRegisterDTO.getCardPrice() : new BigDecimal(0);   // 一卡通支付金额
        BigDecimal creditPrice = outptRegisterDTO.getCreditPrice() != null ? outptRegisterDTO.getCreditPrice() : new BigDecimal(0);  // 挂账金额
        Boolean SFJS = MapUtils.get(map,"SFJS");
        Boolean isInvoice = MapUtils.get(map, "isInvoice");
        String docId = outptVisitDTO.getCrteId();
        String docName = outptVisitDTO.getCrteName();

        String ghid = SnowflakeUtils.getId();
        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", "100");
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        String ghdh = orderNo.getData();
        String jzid = SnowflakeUtils.getId();
        String profileId = outptVisitDTO.getProfileId();

        Date sysdate = new Date();

        if(!StringUtils.isEmpty(outptVisitDTO.getCertNo())){
            //校验同一个人同一个科室一天只能挂号一次
            //入参map certNo身份证号  deptId挂号科室  registerTime挂号时间
            //是否开启挂号限制 参数控制 1 开启 2 关闭
            Map mapOutptRegisterPatamater = new HashMap();
            mapOutptRegisterPatamater.put("hospCode", hospCode);
            mapOutptRegisterPatamater.put("code", "GH_XZ_SF");
            SysParameterDTO sysParameterDTOGHXZ = sysParameterService.getParameterByCode(mapOutptRegisterPatamater).getData();
            if("1".equals(sysParameterDTOGHXZ.getValue())){
                Map mapOutptRegister = new HashMap();
                mapOutptRegister.put("hospCode", hospCode);
                mapOutptRegister.put("certNo", outptVisitDTO.getCertNo());
                mapOutptRegister.put("deptId", outptRegisterDTO.getDeptId());
                mapOutptRegister.put("nowDate", new Date());
                List<OutptRegisterDTO> outptRegisterList = outptRegisterDAO.queryAll(mapOutptRegister);
                if(!ListUtils.isEmpty(outptRegisterList)){
                    throw new AppException("挂号限制！同一个人同一个科室每天只能挂一个号！");
                }
            }
        }

        // 处理门诊病人数据
        disposeMzbr(outptVisitDTO, outptRegisterDTO, ghid, ghdh, jzid, hospCode, docId, docName, sysdate, profileId);

        // 处理his挂号数据
        disposeHisGh(outptRegisterDTO, outptVisitDTO, ghid, ghdh, jzid, sysdate, hospCode, docId, docName);
        // 处理门诊挂号费用
        disposeGhfy(regDetailList, outptRegisterDTO, ghid, jzid, hospCode, docId, docName, sysdate, price, creditPrice);
        String settleId = "";
        // 在挂号处直接收费 直接插入数据进挂号结算表
        if (SFJS){
            // 处理挂号结算数据
            settleId = disposeGhjs(outptRegisterSettleDto, ghid, jzid, hospCode, docId, docName, sysdate, profileId, cardNo, price,isInvoice);
            // 门诊划价处再收费 则插入数据进费用表
        } else {
            List<OutptCostDTO> outptCostDTOS = this.directOutptCost(outptVisitDTO, regDetailList);
            if(!ListUtils.isEmpty(outptCostDTOS)){
                //插入费用明细
                outptCostDAO.batchInsert(outptCostDTOS);
            }
        }


        if(!StringUtils.isEmpty(outptRegisterSettleDto.getBillId()) && isInvoice){
            //修改发票主表当前号码， 插入发票明细表
            disposeFpxx(outptRegisterSettleDto, hospCode);
        }

        //是否开启分时段
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", hospCode);
        mapPatamater.put("code", "GH_FSD_SF");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(mapPatamater).getData();
        String seqNo = "";
        if("1".equals(sysParameterDTO.getValue())){ //开启
            //挂号到医生
            if(!StringUtils.isEmpty(outptRegisterDTO.getDoctorId())){
                //是加号
                if("1".equals(outptRegisterDTO.getIsAdd())){
                    // TODO: 2020/8/19 插入号源表
                    insertDoctouRegisterIsAdd(hospCode, outptRegisterDTO.getDqId(), outptVisitDTO.getProfileId(), docId, docName);
                }else{
                    //非加号
                    // 挂号表绑定号源明细数据 挂号id，医院编码，坐诊队列id
                    seqNo = updateRegisterDrid(ghid, hospCode, outptRegisterDTO.getDqId(), outptVisitDTO.getProfileId());
                }
            }
        }
        // 向分诊队列插入数据
        disposeTriageVisit(outptVisitDTO, outptRegisterDTO, ghid, seqNo, jzid, hospCode);
        // TODO: 2020/8/19 调用建档接口
        //判断有无证件号码，证件类别未其他也进行建档操作
        if(!StringUtils.isEmpty(outptVisitDTO.getCertNo()) || (StringUtils.isNotEmpty(outptVisitDTO.getCertCode()) && Constants.ZJLB.QT.equals(outptVisitDTO.getCertCode()))){
            OutptProfileFileDTO outptProfileFileDTO = new OutptProfileFileDTO();
            outptProfileFileDTO.setId(outptVisitDTO.getProfileId());
            outptProfileFileDTO.setHospCode(hospCode);
            outptProfileFileDTO.setCertNo(outptVisitDTO.getCertNo());
            outptProfileFileDTO.setCertCode(StringUtils.isEmpty(outptVisitDTO.getCertCode()) ? Constants.ZJLB.JMSFZ : outptVisitDTO.getCertCode());
            outptProfileFileDTO.setName(outptVisitDTO.getName());
            outptProfileFileDTO.setAge(outptVisitDTO.getAge());
            outptProfileFileDTO.setAgeUnitCode(outptVisitDTO.getAgeUnitCode());
            outptProfileFileDTO.setGenderCode(outptVisitDTO.getGenderCode());
            outptProfileFileDTO.setSex(outptVisitDTO.getGenderCode());
            outptProfileFileDTO.setBirthday(outptVisitDTO.getBirthday());
            outptProfileFileDTO.setPhone(outptVisitDTO.getPhone());
            outptProfileFileDTO.setNativeAddress(outptVisitDTO.getNativeAddress());
            outptProfileFileDTO.setNowAddress(outptVisitDTO.getNowAddress());
            outptProfileFileDTO.setSourceTjCode(outptVisitDTO.getSourceTjCode());
            outptProfileFileDTO.setSourceTjRemark(outptVisitDTO.getSourceTjRemark());
            outptProfileFileDTO.setType("2");
            outptProfileFileDTO.setCrteId(docId);
            outptProfileFileDTO.setCrteName(docName);
            outptProfileFileDTO.setCrteTime(new Date());
            outptProfileFileDTO.setPatientCode(outptVisitDTO.getPatientCode());
            outptProfileFileDTO.setPreferentialTypeId(outptVisitDTO.getPreferentialTypeId());
            outptProfileFileDTO.setContactAddress(outptVisitDTO.getContactAddress());
            outptProfileFileDTO.setContactName(outptVisitDTO.getContactName());
            outptProfileFileDTO.setOccupationCode(outptVisitDTO.getOccupationCode());
            /**
             * 档案不走中心端
             * @Date: 2021.12.02
             * @Author: luoyong
             */
            /*//调用中心端建档服务
            WrapperResponse<OutptProfileFileExtendDTO> response = outptProfileFileService.save(outptProfileFileDTO);
            OutptProfileFileExtendDTO outptProfileFileExtendDTO = response.getData();*/

            //调用本地建档服务
            log.debug("门诊挂号调用本地建档服务开始：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));
            Map localMap = new HashMap();
            localMap.put("hospCode", hospCode);
            localMap.put("outptProfileFileDTO", outptProfileFileDTO);
            outptProfileFileDTO = baseProfileFileService_consumer.save(localMap).getData();
            log.debug("门诊挂号调用本地建档服务结束：" + DateUtils.format("yyyy-MM-dd HH:mm:ss"));

            // 更新就诊表中档案信息
            OutptVisitDTO outptVisitDTOUpdate = new OutptVisitDTO();
            outptVisitDTOUpdate.setId(jzid);
            outptVisitDTOUpdate.setHospCode(hospCode);
            outptVisitDTOUpdate.setProfileId(outptProfileFileDTO.getId());
            outptVisitDTOUpdate.setOutProfile(outptProfileFileDTO.getOutProfile());
            outptVisitDAO.updateProfile(outptVisitDTOUpdate);
        }
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("settleId", settleId);
        returnMap.put("ghdh", ghdh);
        returnMap.put("registerid", ghid);
        // 推送消息到挂号医生处  liuliyun 2022-01-06
        sendMessage(outptRegisterDTO);
        return returnMap;
    }


    /**
     * @Menthod buildOutptDiagnose
     * @Desrciption  费用表赋值
     * @param outptRegisterDetailDtoList
     * @Author zengfeng
     * @Date   2020/9/14 10:24
     * @Return OutptPrescribeDTO
     **/
    public List<OutptCostDTO> directOutptCost(OutptVisitDTO outptVisitDTO, List<OutptRegisterDetailDto> outptRegisterDetailDtoList){
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        for(OutptRegisterDetailDto  outptRegisterDetailDto : outptRegisterDetailDtoList){
            OutptCostDTO outptCostDTO = new OutptCostDTO();
            //主键
            outptCostDTO.setId(SnowflakeUtils.getId());
            //医院编码
            outptCostDTO.setHospCode(outptRegisterDetailDto.getHospCode());
            //就诊ID
            outptCostDTO.setVisitId(outptRegisterDetailDto.getVisitId());
            //费用来源方式代码
            outptCostDTO.setSourceCode(Constants.FYLYFS.GHJZ);
            //费用来源ID
            outptCostDTO.setSourceId(outptRegisterDetailDto.getId());
            //来源科室ID
            outptCostDTO.setSourceDeptId(outptVisitDTO.getDeptId());
            //财务分类ID
            outptCostDTO.setBfcId(outptRegisterDetailDto.getBfcId());
            //项目ID
            outptCostDTO.setItemId(outptRegisterDetailDto.getItemId());
            //项目类型代码
            outptCostDTO.setItemCode(outptRegisterDetailDto.getItemCode());
            //项目名称（药品、项目、材料、医嘱目录）
            outptCostDTO.setItemName(outptRegisterDetailDto.getItemName());
            //单价
            outptCostDTO.setPrice(outptRegisterDetailDto.getPrice());
            //数量
            outptCostDTO.setNum(outptRegisterDetailDto.getNum());
            //规格
            outptCostDTO.setSpec(outptRegisterDetailDto.getSpec());
            //数量单位
            String unitCode = outptRegisterDetailDto.getUnitCode();
            String itemUnitCode = outptRegisterDetailDto.getItemUnitCode();
            outptCostDTO.setNumUnitCode(unitCode == null ? itemUnitCode : unitCode);
            //总数量
            outptCostDTO.setTotalNum(outptRegisterDetailDto.getNum());
            //项目总金额
            outptCostDTO.setTotalPrice(outptRegisterDetailDto.getTotalPrice());
            //优惠总金额
            outptCostDTO.setPreferentialPrice(outptRegisterDetailDto.getPreferentialPrice());
            //优惠后总金额
            outptCostDTO.setRealityPrice(outptRegisterDetailDto.getRealityPrice());
            //状态标志代码
            outptCostDTO.setStatusCode(Constants.ZTBZ.ZC);
            //是否已发药
            outptCostDTO.setIsDist(Constants.SF.F);
            //结算状态代码
            outptCostDTO.setSettleCode(Constants.JSZT.WJS);
            //开方医生ID
            outptCostDTO.setDoctorId(outptVisitDTO.getDoctorId());
            //开方医生名称
            outptCostDTO.setDoctorName(outptVisitDTO.getDoctorName());
            //开方科室ID
            outptCostDTO.setDeptId(outptVisitDTO.getDeptId());
            //执行科室ID
            outptCostDTO.setExecDeptId(outptVisitDTO.getDeptId());
            //创建人ID
            outptCostDTO.setCrteId(outptVisitDTO.getCrteId());
            //创建人姓名
            outptCostDTO.setCrteName(outptVisitDTO.getCrteName());
            //创建时间
            outptCostDTO.setCrteTime(DateUtils.getNow());
            // 处方id置空（处方id为null 医保结算不了） lly 2022-06-01
            outptCostDTO.setOpId("");
            //费用数据
            outptCostDTOList.add(outptCostDTO);
        }
        return outptCostDTOList;
    }

    @Override
    public List<OutptDoctorQueueDto> queryOutptDoctorList(OutptClassifyDTO outptClassifyDTO) {
        // 根据条件查询所有数据
        List<OutptDoctorQueueDto> list =  outptRegisterDAO.queryOutptDoctorPage(outptClassifyDTO);
        if(!ListUtils.isEmpty(list)){
            list.forEach(dto -> {
                dto.setNumOrUseNum(dto.getIsUserRegister() + "/" + dto.getRegisterNum());
            });
        }
        return list;
    }

    @Override
    public List<OutptClassifyCostDTO> queryRegisterCostList(OutptClassifyDTO outptClassifyDTO) {
        OutptClassifyCostDTO outptClassifyCostDTO = new OutptClassifyCostDTO();
        outptClassifyCostDTO.setHospCode(outptClassifyDTO.getHospCode());
        outptClassifyCostDTO.setCyId(outptClassifyDTO.getId());
        List<OutptClassifyCostDTO> claCostList =  outptClassifyDAO.queryClassifyCostList(outptClassifyCostDTO);
        if(ListUtils.isEmpty(claCostList)){
            throw new AppException("该挂号类别没有任何费用");
        }
        Map<String, List<OutptClassifyCostDTO>> itemCodeMap = claCostList.stream().collect(Collectors.groupingBy(OutptClassifyCostDTO::getItemCode));
        List<OutptClassifyCostDTO> itemIdList = MapUtils.get(itemCodeMap, "3");//项目
        List<OutptClassifyCostDTO> materialIdList = MapUtils.get(itemCodeMap, "2");//材料
        List<BaseItemDTO> itemList = new ArrayList<>();
        List<BaseMaterialDTO> materialList = new ArrayList<>();

        if(!ListUtils.isEmpty(itemIdList)){
            Map queryItemMap = new HashMap();
            BaseItemDTO baseItemDTO = new BaseItemDTO();
            baseItemDTO.setHospCode(outptClassifyDTO.getHospCode());
            baseItemDTO.setIds(itemIdList.stream().map(OutptClassifyCostDTO ::getItemId).collect(Collectors.toList()));
            queryItemMap.put("baseItemDTO", baseItemDTO);
            queryItemMap.put("hospCode", outptClassifyDTO.getHospCode());
            WrapperResponse<List<BaseItemDTO>> response = baseItemService.queryAll(queryItemMap);
            itemList = response.getData();

            for (OutptClassifyCostDTO dto:claCostList) {
                for (BaseItemDTO itemDto:itemList) {
                    if(dto.getItemId().equals(itemDto.getId())){
                        dto.setName(itemDto.getName());
                        dto.setItemUnitCode(itemDto.getUnitCode());
                        dto.setItemPrice(itemDto.getPrice());
                        dto.setItemPriceAll(BigDecimalUtils.multiply(null == itemDto.getPrice() ? new BigDecimal(0): itemDto.getPrice(), dto.getNum()));
                        dto.setItemPreferentialPrice(new BigDecimal(0));
                        dto.setItemPreferentialAllPrice(dto.getItemPriceAll());
                    }
                }
            }
        }

        if(!ListUtils.isEmpty(materialIdList)){
            Map queryMarMap = new HashMap();
            queryMarMap.put("idList", materialIdList.stream().map(OutptClassifyCostDTO ::getItemId).collect(Collectors.toList()));
            queryMarMap.put("hospCode", outptClassifyDTO.getHospCode());
            WrapperResponse<List<BaseMaterialDTO>> response = baseMaterialService.queryAll(queryMarMap);
            materialList = response.getData();

            for (OutptClassifyCostDTO dto:claCostList) {
                for (BaseMaterialDTO matDto:materialList) {
                    if(dto.getItemId().equals(matDto.getId())){
                        dto.setName(matDto.getName());
                        dto.setItemUnitCode(matDto.getUnitCode());
                        dto.setItemPrice(matDto.getPrice());
                        dto.setItemPriceAll(BigDecimalUtils.multiply(null == matDto.getPrice() ? new BigDecimal(0) : matDto.getPrice(), dto.getNum()));
                        dto.setItemPreferentialPrice(new BigDecimal(0));
                        dto.setItemPreferentialAllPrice(dto.getItemPriceAll());
                    }
                }
            }
        }
        return claCostList;
    }

    @Override
    public List<BaseDeptDTO> queryOutptDeptClassify(OutptClassifyDTO outptClassifyDTO) {
        Map map = new HashMap();
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(outptClassifyDTO.getHospCode());
        map.put("baseDeptDTO", baseDeptDTO);
        map.put("hospCode" , outptClassifyDTO.getHospCode());

        List<BaseDeptDTO> deptList = outptRegisterDAO.queryDeptAll(baseDeptDTO);
        List<String> deptIds = deptList.stream().map(BaseDeptDTO :: getId).collect(Collectors.toList());
        OutptClassifyDTO outptClassifyDTOTem = new OutptClassifyDTO();
        outptClassifyDTOTem.setHospCode(outptClassifyDTO.getHospCode());
        outptClassifyDTOTem.setDeptIds(deptIds);
        outptClassifyDTOTem.setIsValid("1");
        List<OutptClassifyDTO> classifyList = null;

        Map queueIngUpParams = new HashMap();
        queueIngUpParams.put("hospCode", outptClassifyDTO.getHospCode());
        queueIngUpParams.put("code", "QUEUING_UP_SF");

        // 是否开启排队叫号
        SysParameterDTO queuingUpInfo = sysParameterService.getParameterByCode(queueIngUpParams).getData();
        if(Constants.SF.S.equals(queuingUpInfo.getValue())){
            outptClassifyDTOTem.setQueueDate(DateUtils.format(new Date(),DateUtils.Y_M_D));
            classifyList = outptClassifyDAO.queryAllWithClassQueueId(outptClassifyDTOTem);
            List<BaseDeptDTO> deptListNew = new ArrayList<>(5);
            Map<String, List<OutptClassifyDTO>> deptClassMap = classifyList.stream().collect(Collectors.groupingBy(OutptClassifyDTO::getDeptId));
            for (List<OutptClassifyDTO> classifyDTOList : deptClassMap.values()) {
                BaseDeptDTO baseDeptDTO1 = new BaseDeptDTO();
                baseDeptDTO1.setId(classifyDTOList.get(0).getDeptId());
                baseDeptDTO1.setName(classifyDTOList.get(0).getDeptName());
                baseDeptDTO1.setHospCode(classifyDTOList.get(0).getHospCode());
                baseDeptDTO1.setTypeCode(classifyDTOList.get(0).getDeptTypeCode());
                baseDeptDTO1.setCode(classifyDTOList.get(0).getDeptCode());
                baseDeptDTO1.setUpDeptCode(classifyDTOList.get(0).getUpDeptCode());
                baseDeptDTO1.setChildren(classifyDTOList);
                deptListNew.add(baseDeptDTO1);
            }
            return deptListNew;
        }else {
         classifyList = outptClassifyDAO.queryAllandPage(outptClassifyDTOTem);
         Map<String, List<OutptClassifyDTO>> deptClassMap = classifyList.stream().collect(Collectors.groupingBy(OutptClassifyDTO::getDeptId));


        List<BaseDeptDTO> deptListNew = new ArrayList<>();
        for (BaseDeptDTO deptDto: deptList) {
            deptDto.setChildren(MapUtils.get(deptClassMap, deptDto.getId()));
            if(!ListUtils.isEmpty(deptDto.getChildren())){
                deptListNew.add(deptDto);
            }
        }
        return deptListNew;
        }
    }

    @Override
    public List<OutptClassifyCostDTO> updateCostPreferential(Map map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String pfCode = MapUtils.get(map,"code");
        List<OutptClassifyCostDTO> costList = MapUtils.get(map,"costList");
        return disposeYhcl(costList, pfCode, hospCode);
    }

    @Override
    public List<OutptRegisterDTO> queryRegisterInfoByCertno(Map<String, Object> map) {
        return outptRegisterDAO.queryRegisterInfoByCertno(map);
    }

    @Override
    public  OutptRegisterDTO getOutptRegisterByVisitId(Map<String, Object> map) {
        return outptRegisterDAO.getOutptRegisterByVisitId(map);
    }

    private void disposeMzbr(OutptVisitDTO outptVisitDTO, OutptRegisterDTO outptRegisterDTO, String registerId, String registerNo, String visitId, String hospCode,
                             String docId, String docName, Date sysdate, String profileId) {
        // 组装就诊数据 HIS_MZBR
        outptVisitDTO.setId(visitId);
        outptVisitDTO.setRegisterId(registerId);
        outptVisitDTO.setRegisterNo(registerNo);
        if(StringUtils.isEmpty(outptVisitDTO.getProfileId())){
            outptVisitDTO.setProfileId(profileId);
        }
        outptVisitDTO.setHospCode(hospCode);
        outptVisitDTO.setCrteId(docId);
        outptVisitDTO.setCrteName(docName);
        outptVisitDTO.setCrteTime(sysdate);
        outptVisitDTO.setPreferentialTypeId(outptRegisterDTO.getPreferentialTypeId());
        outptVisitDTO.setTranInCode("0");
        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", "101");
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        outptVisitDTO.setVisitNo(orderNo.getData());//单据生成规则

        outptVisitDTO.setVisitCode("01");
        // 如果是普通病人清楚医保信息不走医保流程，不然还会按医保结算
        if ("BH01".equals(outptVisitDTO.getPatientCode())) {
            outptVisitDTO.setInsureOrgCode("");
            outptVisitDTO.setInsureNo("");
        }

        // 0 初诊，1 复诊
        if (!StringUtils.isEmpty(outptVisitDTO.getIsFirstVisit())) {
            outptVisitDTO.setInsureOrgCode("0");
        }

        // 就诊科室设置
        if (StringUtils.isEmpty(outptVisitDTO.getDeptId())) {
            outptVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
            outptVisitDTO.setDeptName(outptRegisterDTO.getDeptName());
        }
        // 就诊医生为空的情况下
        if (StringUtils.isEmpty(outptVisitDTO.getDoctorId())) {
            outptVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
            outptVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
        }
//        outptRegisterDTO.setJzbz("0");
        outptVisitDAO.insert(outptVisitDTO);
    }

    private void disposeHisGh(OutptRegisterDTO outptRegisterDTO, OutptVisitDTO outptVisitDTO, String registerId, String registerNo, String visitId, Date sysdate,
                              String hospCode, String docId, String docName) {
        // 组装挂号数据 HIS_GH01
        outptRegisterDTO.setId(registerId);
        outptRegisterDTO.setVisitId(visitId);
        outptRegisterDTO.setRegisterNo(registerNo);
        outptRegisterDTO.setHospCode(hospCode);
        outptRegisterDTO.setRegisterTime(sysdate);

        outptRegisterDTO.setName(outptVisitDTO.getName());
        outptRegisterDTO.setGenderCode(outptVisitDTO.getGenderCode());
        outptRegisterDTO.setAge(outptVisitDTO.getAge());
        outptRegisterDTO.setBirthday(outptVisitDTO.getBirthday());
        outptRegisterDTO.setCertCode(outptVisitDTO.getCertCode());
        outptRegisterDTO.setCertNo(outptVisitDTO.getCertNo());
        outptRegisterDTO.setPhone(outptVisitDTO.getPhone());
        outptRegisterDTO.setIsCancel("0");
        outptRegisterDTO.setCrteId(docId);
        outptRegisterDTO.setCrteName(docName);
        outptRegisterDTO.setCrteTime(sysdate);
        outptRegisterDTO.setSourceBzCode("0");
        outptRegisterDTO.setVisitCode(outptVisitDTO.getVisitCode());
        outptRegisterDTO.setSourceTjCode(outptVisitDTO.getSourceTjCode()); // 病人来源途径(LYTJ)
        outptRegisterDTO.setSourceTjRemark(outptVisitDTO.getSourceTjRemark()); // 病人来源途径备注
//        hisGh.setJhxm(hisMzbr.getXm());
//        hisGh.setXm(hisMzbr.getXm());
//        hisGh.setBrlx(hisMzbr.getBrlx());
//        hisGh.setJzlb(hisMzbr.getJzlb());
//        hisGh.setCzr(ysmc);
//        hisGh.setCzrid(ysid);
//        hisGh.setZtbz("0");
//        hisGh.setLybz("0");
//        hisGh.setGhlylx("0");
//        if (CommUtil.isEmpty(hisGh.getJhbz())) {
//            hisGh.setJhbz("1");
//        }
        outptRegisterDAO.insert(outptRegisterDTO);
    }

    private void disposeGhfy(List<OutptRegisterDetailDto> regDetaiolList,OutptRegisterDTO outptRegisterDTO,  String registerId, String visitId, String hospCode, String docId, String docName, Date sysdate, BigDecimal cardPrice, BigDecimal creditPrice) {
        // 处理挂号费用数据
        if (!ListUtils.isEmpty(regDetaiolList)) {
            for (OutptRegisterDetailDto dto : regDetaiolList) {
                dto.setRegisterId(registerId);
                dto.setVisitId(visitId);
                dto.setHospCode(hospCode);
                dto.setItemName(dto.getName());
                dto.setPreferentialId(outptRegisterDTO.getPreferentialTypeId());
                dto.setPrice(dto.getItemPrice());
                dto.setTotalPrice(BigDecimalUtils.multiply(null == dto.getPrice() ? new BigDecimal(0) : dto.getPrice(), dto.getNum()));
                dto.setPreferentialPrice(dto.getItemPreferentialPrice());
                dto.setRealityPrice(dto.getItemPreferentialAllPrice());
                dto.setId(SnowflakeUtils.getId());
                dto.setStatusCode("0");
                dto.setCrteId(docId);
                dto.setCrteName(docName);
                dto.setCrteTime(sysdate);
                dto.setCardPrice(cardPrice);
                dto.setCreditPrice(creditPrice);
            }
            // TODO 插入挂号费用数据
            outptRegisterDAO.insertRegDetail(regDetaiolList);
        }
    }


    private String disposeGhjs(OutptRegisterSettleDto outptRegisterSettleDto, String registerId, String visitId, String hospCode, String docId, String docName, Date sysdate, String profileId, String cardNo, BigDecimal  price,boolean isInvoice) {
        // 获取最新发票号码
        if (outptRegisterSettleDto.getCurrNo() != null && !"".equals(outptRegisterSettleDto.getCurrNo())) {
            outptRegisterSettleDto.setHospCode(hospCode);
            OutinInvoiceDTO invoiceNo = outptRegisterDAO.getMaxInvoiceNo(outptRegisterSettleDto);
            if (invoiceNo != null && !"".equals(invoiceNo.getCurrNo())) {
                outptRegisterSettleDto.setCurrNo(invoiceNo.getCurrNo());
                outptRegisterSettleDto.setBillNo(invoiceNo.getCurrNo());  // 挂号结算表记录的发票号 = 发票记录表最新发票号，不再直接去页面的发票号
            }
        }

        // 处理支付方式数据
        // pos支付
        BigDecimal pos_zf = outptRegisterSettleDto.getPosZf();
        // 微信支付
        BigDecimal wx_zf = outptRegisterSettleDto.getWxZf();
        // 支付宝支付
        BigDecimal zfb_zf = outptRegisterSettleDto.getZfbZf();
        // 实收现金支付
        BigDecimal xjzf = outptRegisterSettleDto.getXjZf();
        // 实收现金支付
        BigDecimal zzzf = outptRegisterSettleDto.getZzZf();
        // 挂账金额
        BigDecimal creditPrice = outptRegisterSettleDto.getCreditPrice();
        // 退款金额
        BigDecimal tsxj = outptRegisterSettleDto.getTsxj();
        List<OutptRegisterPayDto> payList = new ArrayList<>();
        List<OutptRegisterSettleDto> settleList = new ArrayList<>();

        String settleId = SnowflakeUtils.getId();

        //组装挂号结算表settle
        outptRegisterSettleDto.setId(settleId);
        outptRegisterSettleDto.setRegisterId(registerId);
        outptRegisterSettleDto.setHospCode(hospCode);
        outptRegisterSettleDto.setSettleTime(new Date());
        outptRegisterSettleDto.setStatusCode("0");
//            outptRegisterSettleDto.setZffs("BE03");
//            outptRegisterSettleDto.setJyid(jyid);
//            ghjs.setZfje(pos_zf);
//            ghjs.setHm(hisGhjsDto.getPos_pjhm());
        outptRegisterSettleDto.setCrteId(docId);
        outptRegisterSettleDto.setCrteName(docName);
        outptRegisterSettleDto.setCrteTime(sysdate);
//        settleList.add(outptRegisterSettleDto);

        // pos支付
        if (null != pos_zf && !BigDecimalUtils.isZero(pos_zf) && pos_zf.compareTo(BigDecimal.ZERO) == 1) {
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
//            outptRegisterSettleDto.setRealityPrice(pos_zf);
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.SK);
            outptRegisterPayDto.setPrice(pos_zf);
            // TODO: 2020/8/18 手续费怎么算
            outptRegisterPayDto.setServicePrice(pos_zf);
            payList.add(outptRegisterPayDto);
        }
        // 微信支付
        if (null != wx_zf && !BigDecimalUtils.isZero(wx_zf) && wx_zf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(wx_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.WX);
            outptRegisterPayDto.setPrice(wx_zf);
            payList.add(outptRegisterPayDto);
        }
        // 微信支付
        if (null != creditPrice && !BigDecimalUtils.isZero(creditPrice) && creditPrice.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(wx_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.GZ);
            outptRegisterPayDto.setPrice(creditPrice);
            payList.add(outptRegisterPayDto);
        }
        // 支付宝支付
        if (null != zfb_zf && !BigDecimalUtils.isZero(zfb_zf) && zfb_zf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(zfb_zf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.ZFB);
            outptRegisterPayDto.setPrice(zfb_zf);
            payList.add(outptRegisterPayDto);
        }

        // 现金支付
        if (null != xjzf && !BigDecimalUtils.isZero(xjzf) && xjzf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(xjzf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.XJ);
            outptRegisterPayDto.setPrice(BigDecimalUtils.subtract(xjzf, tsxj));
            payList.add(outptRegisterPayDto);
        }
        // 现金支付
        if (null != zzzf && !BigDecimalUtils.isZero(zzzf) && zzzf.compareTo(BigDecimal.ZERO) == 1) {
//            outptRegisterSettleDto.setRealityPrice(xjzf);
            outptRegisterSettleDto.setPayCode("0");  // 0:HIS 1:微信  2：支付宝   3：自助机
            OutptRegisterPayDto outptRegisterPayDto = new OutptRegisterPayDto();
            outptRegisterPayDto.setId(SnowflakeUtils.getId());
            outptRegisterPayDto.setHospCode(hospCode);
            outptRegisterPayDto.setRsId(settleId);
            outptRegisterPayDto.setVisitId(visitId);
            outptRegisterPayDto.setPayCode(Constants.ZFFS.ZZ);
            outptRegisterPayDto.setPrice(zzzf);
            payList.add(outptRegisterPayDto);
        }

        // 使用一卡通  start ========================
        // 校验是否使用一卡通，校验一卡通余额，档案id，卡状态
        if (price == null) {
          price = new BigDecimal(0);
        }
        BigDecimal tempCardPrice = new BigDecimal(0);
        if (cardNo != null) { // 门诊挂号结算收费时，页面传入的一卡通账号为空，没有使用一卡通
            Map<String, Object> map = new HashMap<>();
            map.put("code", "SF_YKTCZ");
            map.put("hospCode", hospCode);
            WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(map);
            if (wr.getData().getValue() != null && ("2".equals(wr.getData().getValue()) || "4".equals(wr.getData().getValue()) || "6".equals(wr.getData().getValue()) || "8".equals(wr.getData().getValue()))) {
                // 1、查询一卡通的余额是否足够，且状态是否为正常
                BaseCardRechargeChangeDTO dto = new BaseCardRechargeChangeDTO();
                dto.setHospCode(hospCode);
                dto.setCardNo(cardNo);
                BaseCardRechargeChangeDTO baseCardRechargeChangeDTO = null;
                if (BigDecimalUtils.greaterZero(price) || (cardNo != null && !cardNo.equals(""))) {
                    baseCardRechargeChangeDTO = baseCardRechargeChangeDAO.getBaseCardRechargeChangeDTO(dto);
                }

                // 2、需要校验当前挂号的档案id与卡的档案id一致，
                if (baseCardRechargeChangeDTO == null && BigDecimalUtils.greaterZero(price)) {
                    throw new AppException("结算支付时查询就诊卡出错，请联系管理员");
                }
                if (baseCardRechargeChangeDTO != null && BigDecimalUtils.greaterZero(price)) {
                    if (baseCardRechargeChangeDTO.getProfileId() == null || !baseCardRechargeChangeDTO.getProfileId().equals(profileId)) {
                        throw new AppException("结算支付时查询就诊卡档案id与当前患者档案id不一致，请联系管理员");
                    }
                    if (baseCardRechargeChangeDTO.getCardStatusCode() == null || !"0".equals(baseCardRechargeChangeDTO.getCardStatusCode())) {
                        throw new AppException("结算支付时查询就诊卡状态非正常状态，不能使用该卡");
                    }
                    if (baseCardRechargeChangeDTO.getAccountBalance() == null || BigDecimalUtils.less(baseCardRechargeChangeDTO.getAccountBalance(), price)){
                        throw new AppException("结算支付时查询就诊卡余额小于需要支付的金额，不能支付");
                    }
                    if (!BigDecimalUtils.equals(baseCardRechargeChangeDTO.getAccountBalance(), baseCardRechargeChangeDTO.getEndBalance())) {
                        throw new AppException("结算支付时查询就诊卡余额与上一次异动后金额不一致，不能支付");
                    }
                    // 更新一卡通充值、消费异动表，更新一卡通主表余额
                    baseCardRechargeChangeDTO.setId(SnowflakeUtils.getId());
                    baseCardRechargeChangeDTO.setStatusCode("8"); // 卡异动状态 8： 消费
                    baseCardRechargeChangeDTO.setPayCode(null);  // 支付方式
                    baseCardRechargeChangeDTO.setPrice(BigDecimalUtils.negate(price));
                    baseCardRechargeChangeDTO.setStartBalance(baseCardRechargeChangeDTO.getEndBalance());
                    baseCardRechargeChangeDTO.setStartBalanceEncryption(baseCardRechargeChangeDTO.getStartBalanceEncryption());
                    baseCardRechargeChangeDTO.setEndBalance(BigDecimalUtils.subtract(baseCardRechargeChangeDTO.getStartBalance(), price));
                    baseCardRechargeChangeDTO.setEndBalanceEncryption(null);
                    baseCardRechargeChangeDTO.setSettleType("01");
                    baseCardRechargeChangeDTO.setSettleId(settleId);
                    baseCardRechargeChangeDTO.setCrteId(docId);
                    baseCardRechargeChangeDTO.setCrteName(docName);
                    baseCardRechargeChangeDTO.setCrteTime(new Date());
                    // 新增一卡通消费异动
                    int temp1 = baseCardRechargeChangeDAO.insertBaseCardRechargeChange(baseCardRechargeChangeDTO);
                    Map<String, Object> baseCardMap = new HashMap<>();
                    baseCardMap.put("hospCode", hospCode);
                    baseCardMap.put("profileId", profileId);
                    baseCardMap.put("cardNo", cardNo);
                    baseCardMap.put("accountBalance", baseCardRechargeChangeDTO.getEndBalance());
                    // 更新一卡通主表余额
                    int temp2 = baseCardRechargeChangeDAO.updateCardAccountBalance(baseCardMap);
                    if (temp1 <= 0 || temp2 <= 0) {
                        throw new AppException("保存一卡通异动异常，未写入数据，请联系管理员");
                    }
                    tempCardPrice = price;

                }
            }
        }
        // 使用一卡通  end ========================

        if (null != outptRegisterSettleDto) {
            outptRegisterSettleDto.setCardPrice(tempCardPrice);
            outptRegisterSettleDto.setRealityPrice(BigDecimalUtils.subtract(outptRegisterSettleDto.getRealityPrice(), tsxj));
            if (!BigDecimalUtils.isZero(price)) {
                outptRegisterSettleDto.setRealityPrice(BigDecimalUtils.subtract(outptRegisterSettleDto.getRealityPrice(), price));
            }
            if(!isInvoice){
                outptRegisterSettleDto.setBillId("");
                outptRegisterSettleDto.setBillNo("");
                outptRegisterSettleDto.setCurrNo("");
            }
            outptRegisterDAO.batchInsertRegisterSettle(outptRegisterSettleDto);
        }
        if (!ListUtils.isEmpty(payList)) {
            outptRegisterDAO.batchInsertOutptRegisterPay(payList);
        }
        return settleId;
    }

    /**
     *  向分诊队列表插入数据
     * @param outptVisitDTO 处理后的门诊病人信息
     * @param outptRegisterDTO   处理后的门诊挂号信息
     * @param ghid 挂号ID
     * @param seqNo  队列排序号
     * @param jzid 就诊ID
     * @param hospCode 医院编码
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/22 19:37
     **/
    private void disposeTriageVisit(OutptVisitDTO outptVisitDTO, OutptRegisterDTO outptRegisterDTO, String ghid, String  seqNo, String jzid, String hospCode) {
        OutptTriageVisitDTO outptTriageVisitDTO = new OutptTriageVisitDTO();
        outptTriageVisitDTO.setId(SnowflakeUtils.getId());
        outptTriageVisitDTO.setHospCode(hospCode);
        outptTriageVisitDTO.setRegisterId(ghid);
        outptTriageVisitDTO.setVisitId(jzid);
        outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId());
        outptTriageVisitDTO.setTriageId(outptRegisterDTO.getTriageId());
        outptTriageVisitDTO.setTriageName(outptRegisterDTO.getTriageName());

        String triageCode = null;


        if(outptRegisterDTO.getDoctorId() != null) {
            // 1.获取医生队列所属诊室信息
            OutptDoctorQueueDto doctorQueueDto = new OutptDoctorQueueDto();
            doctorQueueDto.setId(outptRegisterDTO.getDqId());
            doctorQueueDto.setHospCode(hospCode);
            doctorQueueDto = outptDoctorQueueDao.queryById(doctorQueueDto);

            // 2.获取分诊室信息
            Map mapParam = new HashMap();
            List<String> ids = new ArrayList<>();
            ids.add(doctorQueueDto.getCqId());
            mapParam.put("hospCode",hospCode);
            mapParam.put("ids",ids);
            List<OutptClassesQueueDto> classesQueue = outptClassesQueueDao.queryClassesQueueParam(mapParam);
            if(classesQueue == null || classesQueue.isEmpty()) {
                log.error("排班医生的诊室ID为空或者不存在,执行的SQL语句ID为 'cn.hsa.module.outpt.queue.dao.queryClassesQueueParam',查询的参数为：{}",mapParam);
                return ;
            }

            OutptClassesQueueDto classesQueueDto = classesQueue.get(0);
            // 3.获取班次是否排队叫号信息
            OutptClassifyClassesDTO classifyClassesDTO = new OutptClassifyClassesDTO();
            classifyClassesDTO.setId(classesQueueDto.getCcId());
            classifyClassesDTO.setHospCode(hospCode);
            classifyClassesDTO = outptClassifyClassesDAO.getClassifyClassesById(classifyClassesDTO);

            triageCode = classesQueueDto.getTriageCode();
            outptTriageVisitDTO.setClinicId(doctorQueueDto.getClinicId());

            outptTriageVisitDTO.setTriageId(classesQueueDto.getTriageId());
            outptTriageVisitDTO.setTriageName(classesQueueDto.getTriageName());
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_DIAGNOSED);
            outptTriageVisitDTO.setIsCall(classifyClassesDTO == null? null : classifyClassesDTO.getIsCall());

            outptTriageVisitDTO.setDoctorId(outptRegisterDTO.getDoctorId());
            outptTriageVisitDTO.setDoctorName(outptRegisterDTO.getDoctorName());
            outptTriageVisitDTO.setDqId(outptRegisterDTO.getDqId());

            outptTriageVisitDTO.setSortNo(getSequenceNoByCond(null,outptRegisterDTO.getDoctorId(),hospCode));

            mapParam.put("id",doctorQueueDto.getClinicId());
            Map result = outptTriageVisitDao.getClinicInfoById(mapParam);
            outptTriageVisitDTO.setClinicName(result == null? null:MapUtils.get(result,"name"));
        }
        //  根据分诊方式写入分诊状态
        if(Constants.FZFS.AUTO.equals(triageCode)){
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.HAVE_BEEN_DIAGNOSED);
            // 自动分诊的分诊人ID与姓名默认为创建者ID和姓名
            outptTriageVisitDTO.setTriagePeoId(outptRegisterDTO.getCrteId());
            outptTriageVisitDTO.setTriagePeoName(outptRegisterDTO.getCrteName());
        } else {
            outptTriageVisitDTO.setTriageStartCode(Constants.FZZT.NOT_TRIAGE);
        }

        Map orderMap = new HashMap();
        orderMap.put("hospCode", hospCode);
        orderMap.put("typeCode", triageOrderNoType);
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(orderMap);
        // 分诊单号
        outptTriageVisitDTO.setTriageNo(orderNo.getData());

        outptTriageVisitDTO.setName(outptVisitDTO.getName());
        outptTriageVisitDTO.setIsValid(Constants.SF.S);
        outptTriageVisitDTO.setDeptId(outptRegisterDTO.getDeptId());
        outptTriageVisitDTO.setDeptName(outptRegisterDTO.getDeptName());
        outptTriageVisitDTO.setCrteId(outptRegisterDTO.getCrteId());
        outptTriageVisitDTO.setCrteName(outptRegisterDTO.getCrteName());
        outptTriageVisitDTO.setCqId(outptRegisterDTO.getClassQueueId());
        outptTriageVisitDTO.setCrteTime(DateUtils.getNow());
        outptTriageVisitDTO.setTriagePeoTime(DateUtils.getNow());
        if(outptTriageVisitDTO.getSortNo() == null)
        outptTriageVisitDTO.setSortNo("".equals(seqNo)?getSequenceNoByCond(outptRegisterDTO.getDeptId(),null,hospCode):Integer.valueOf(seqNo));

        outptTriageVisitDao.insertOutptTriageVisit(outptTriageVisitDTO);
        // 挂号到部门的在分诊台分诊时触发
        if (outptTriageVisitDTO.getDoctorId() != null) {
            // 查询系统参数中配置的排队叫号接口
            Map callQueueServerParams = new HashMap();
            callQueueServerParams.put("hospCode", hospCode);
            callQueueServerParams.put("code", "REGISTER_QUEUE_SERVER_URL");

            SysParameterDTO callQueueServerInfo = sysParameterService.getParameterByCode(callQueueServerParams).getData();

            if (callQueueServerInfo == null) {
                log.error("请维护系统参数【 REGISTER_QUEUE_SERVER_URL 】叫号系统叫号面板信息更新接口地址再操作");
                return;
            }
            String queueServerUrl = callQueueServerInfo.getValue();
            Map<String,Object> callParams = new HashMap<>(4);

            callParams.put("url",queueServerUrl);
            JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            String jsonString = JSONObject.toJSONString(outptTriageVisitDTO,SerializerFeature.WriteDateUseDateFormat);
            JSONObject data = new JSONObject();
            JSONObject jsonObject = JSON.parseObject(jsonString);

            jsonObject.put("callContent","请"+outptTriageVisitDTO.getName() +"到"+ outptTriageVisitDTO.getClinicName()+"诊室"
                    +outptTriageVisitDTO.getDoctorName()+"医生处就诊");
            data.put("data",Arrays.asList(jsonObject));

            callParams.put("param",data.toJSONString());
            String result = null;
            //  调用排队叫号接口
            try {
                 result = HttpConnectUtil.doPost(callParams);
            }catch (RuntimeException re){
                log.error("调用排队叫号接口异常，异常信息如下：{}",re);
                return ;
            }
            log.debug("挂号时调用叫号接口的返回结果："+result);
        }
    }

    /** 根据医生id或部门id查询当天病人的排序号 **/
    private Integer getSequenceNoByCond(String deptId, String doctorId,String hospCode){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("registerTime",new Date());
        if(deptId != null) {
            map.put("deptId", deptId);
        }
        if(doctorId != null) {
            map.put("doctorId", doctorId);
        }
        Map<String,Integer> sequenceNoMap = outptTriageVisitDao.getSequenceNoOfDoctorOrDept(map);
        return Integer.valueOf(String.valueOf(sequenceNoMap.get("sequenceNo")));
    }

    private void disposeFpxx(OutptRegisterSettleDto outptRegisterSettleDto, String hospCode){
        // 取最新的发票号码
        OutinInvoiceDTO invoiceNo = outptRegisterDAO.getMaxInvoiceNo(outptRegisterSettleDto);
        Map queryMap = new HashMap();
        OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();
        outinInvoiceDTO.setId(outptRegisterSettleDto.getBillId());
        outinInvoiceDTO.setHospCode(hospCode);
        outinInvoiceDTO.setSettleId(outptRegisterSettleDto.getId());
        outinInvoiceDTO.setPrefix(outptRegisterSettleDto.getPrefix());
        if (invoiceNo != null && !"".equals(invoiceNo.getCurrNo())) {
            outinInvoiceDTO.setCurrNo(invoiceNo.getCurrNo());
            outinInvoiceDTO.setDqCurrNo(invoiceNo.getCurrNo());
        } else {
            outinInvoiceDTO.setCurrNo(outptRegisterSettleDto.getCurrNo());
            outinInvoiceDTO.setDqCurrNo(outptRegisterSettleDto.getCurrNo());
        }

        queryMap.put("hospCode", hospCode);
        queryMap.put("outinInvoiceDTO", outinInvoiceDTO);
        outinInvoiceService.updateInvoiceStatus(queryMap);
//        WrapperResponse<List<OutinInvoiceDTO>> response = outinInvoiceService.getInvoiceList(queryMap);
//        List<OutinInvoiceDTO> invoiceList = response.getData();
//        OutinInvoiceDTO outinInvoiceDTORes = invoiceList.get(0);
//
//        if(outinInvoiceDTORes.getNum() - 1 >= 0){
//            Integer currNo = Integer.parseInt(outinInvoiceDTORes.getCurrNo()) + 1;
//            outinInvoiceDTORes.setCurrNo(String.valueOf(currNo));
//            outinInvoiceDTORes.setNum(outinInvoiceDTORes.getNum() - 1);
//        }else{
//            throw new AppException("当前剩余发票为0");
//        }
//
//        //发票明细
//        OutinInvoiceDetailDO outinInvoiceDetailDO = new OutinInvoiceDetailDO();
//        outinInvoiceDetailDO.setId(SnowflakeUtils.getId());
//        outinInvoiceDetailDO.setHospCode(hospCode);
//        outinInvoiceDetailDO.setInvoiceId(outinInvoiceDTORes.getId());
//        outinInvoiceDetailDO.setInvoiceNo(outinInvoiceDTORes.getPrefix() + outinInvoiceDTORes.getCurrNo());
//        outinInvoiceDetailDO.setStatusCode("0");
//        outinInvoiceDetailDO.setUseTime(new Date());
//        outinInvoiceDetailDO.setSettleId(outptRegisterSettleDto.getId());
//
//        Map updateMap = new HashMap();
//        updateMap.put("hospCode", hospCode);
//        updateMap.put("outinInvoiceDTO", outinInvoiceDTORes);
//
//        Map insertMap = new HashMap();
//        insertMap.put("hospCode", hospCode);
//        insertMap.put("outinInvoiceDetailDO", outinInvoiceDetailDO);
//
//        outinInvoiceService.updateOutinInvoiceStatus(updateMap);
//        outinInvoiceService.insertOutinInvoiceDetail(insertMap);
    }

    private List<OutptClassifyCostDTO> disposeYhcl(List<OutptClassifyCostDTO> costList, String pfCode, String hospCode){
        if(ListUtils.isEmpty(costList)){
            return costList;
        }
        //根据preferentialTypeId查询出base_preferential明细数据
        Map queryMap = new HashMap();
        BasePreferentialDTO basePreferentialDTO = new BasePreferentialDTO();
        basePreferentialDTO.setHospCode(hospCode);
        basePreferentialDTO.setPfTypeCode(pfCode);
        basePreferentialDTO.setIsValid("1");
        queryMap.put("hospCode", hospCode);
        queryMap.put("basePreferentialDTO", basePreferentialDTO);
        WrapperResponse<List<BasePreferentialDTO>> response = basePreferentialService.queryAll(queryMap);
        List<BasePreferentialDTO> preferentialList = response.getData();
        if(ListUtils.isEmpty(preferentialList)){
            return null;
        }
        //把preferentialList按照优惠类型type_code分组 1 财务分类 2药品 3项目
//        Map<String, List<BasePreferentialDTO>> preferentialMap =  preferentialList.stream().collect(Collectors.groupingBy(BasePreferentialDTO::getTypeCode));
//        List<BasePreferentialDTO> list1 = preferentialMap.get("1");
//        List<BasePreferentialDTO> list2 = preferentialMap.get("2");
//        List<BasePreferentialDTO> list3 = preferentialMap.get("3");
        for (OutptClassifyCostDTO outptClassifyCostDTO :costList) {
            //先判断项目和药品
            for (BasePreferentialDTO dto :preferentialList) {
                if(outptClassifyCostDTO.getItemCode().equals(dto.getTypeCode())){
                    if(outptClassifyCostDTO.getItemId().equals(dto.getBizCode())){
                        //判断门诊优惠方式out_code 1: 按比例 2: 按金额
                        if("1".equals(dto.getOutCode())){
                            BigDecimal preferentialPrice =  BigDecimalUtils.multiply((new BigDecimal(1).subtract(dto.getOutScale())), outptClassifyCostDTO.getItemPrice());
                            BigDecimal yhhje = BigDecimalUtils.subtract(outptClassifyCostDTO.getItemPrice(), preferentialPrice);
                            BigDecimal itemPreferentialAllPrice = BigDecimalUtils.multiply(yhhje, outptClassifyCostDTO.getNum());
                            outptClassifyCostDTO.setItemPreferentialPrice(preferentialPrice);
                            outptClassifyCostDTO.setItemPreferentialAllPrice(itemPreferentialAllPrice);
                            break;
                        }else if("2".equals(dto.getOutCode())){
                            outptClassifyCostDTO.setItemPreferentialPrice(dto.getOutScale());
                            BigDecimal yhhje = BigDecimalUtils.subtract(outptClassifyCostDTO.getItemPrice(), dto.getOutScale());
                            outptClassifyCostDTO.setItemPreferentialAllPrice(BigDecimalUtils.multiply(yhhje, outptClassifyCostDTO.getNum()));
                            break;
                        }
                    }
                }
            }
        }
        return costList;
    }


    /**
     * 更新号源表和挂号表信息
     @params [registerId, hospCode, dqId, profileId]
     * @Author:  chenjun
     * @Date   2020/8/19 14:15
     * @return  java.lang.String 当前号源的队列排序号
     **/
    private String updateRegisterDrid(String registerId, String hospCode, String dqId, String profileId){
        Date nowDate = new Date();
        String nowHms = "08:30:00";
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("dqId", dqId);
        map.put("isUse", "0");
        map.put("isLock", "0");
        map.put("isAdd", "0");
        // 号源排序号
        String seqNo = "";

        //按时间排序查询出所有大于当前时间号源
        List<OutptDoctorRegisterDO> list = outptRegisterDAO.queryDoctorRegisterByTime(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("没有号源");
        }
        List<OutptDoctorRegisterDO> listFilter = list.stream().
                filter((OutptDoctorRegisterDO dto) -> DateUtils.dateCompare(dto.getRegisterTime(), DateUtils.format(nowDate, DateUtils.H_M_S), DateUtils.H_M_S) == 1).
                collect(Collectors.toList());
        if(ListUtils.isEmpty(listFilter)){
            throw new AppException("没有可用号源");
        }
        OutptDoctorRegisterDO outptDoctorRegisterDO = listFilter.get(0);
        OutptRegisterDTO outptRegisterDTO = new OutptRegisterDTO();
        outptRegisterDTO.setHospCode(hospCode);
        outptRegisterDTO.setId(registerId);
        outptRegisterDTO.setDrId(outptDoctorRegisterDO.getId());
        seqNo = outptDoctorRegisterDO.getSeqNo();
        //挂号表绑定号源id
        outptRegisterDAO.updateByPrimaryKeySelective(outptRegisterDTO);

        OutptDoctorRegisterDO outptDoctorRegisterDOUpdate = new OutptDoctorRegisterDO();
        outptDoctorRegisterDOUpdate.setId(outptDoctorRegisterDO.getId());
        outptDoctorRegisterDOUpdate.setHospCode(outptDoctorRegisterDO.getHospCode());
        outptDoctorRegisterDOUpdate.setIsLock("1");
        outptDoctorRegisterDOUpdate.setProfileId(profileId);
        //修改号源表状态，已锁定
        outptRegisterDAO.updateDoctorRegisterStatus(outptDoctorRegisterDOUpdate);
        return seqNo;
    }

    private void insertDoctouRegisterIsAdd(String hospCode, String dqId, String profileId, String docId, String docName){
        String hmsDate = "";
        OutptDoctorRegisterDto outptDoctorRegisterDto = new OutptDoctorRegisterDto();
        outptDoctorRegisterDto.setId(SnowflakeUtils.getId());
        outptDoctorRegisterDto.setHospCode(hospCode);
        outptDoctorRegisterDto.setDqId(dqId);
        outptDoctorRegisterDto.setRegisterTime(hmsDate);
        outptDoctorRegisterDto.setRegisterNum(0l);
        outptDoctorRegisterDto.setProfileId(profileId);
        outptDoctorRegisterDto.setIsUse("1");
        outptDoctorRegisterDto.setIsLock("1");
        outptDoctorRegisterDto.setIsAdd("1");
        outptDoctorRegisterDto.setCrteId(docId);
        outptDoctorRegisterDto.setCrteName(docName);
        outptDoctorRegisterDto.setCrteTime(new Date());
        //修改号源表状态，已锁定
        outptRegisterDAO.insertDoctouRegisterIsAdd(outptDoctorRegisterDto);
    }

    /**
     * @Menthod isFlagOutRegister
     * @Desrciption 门诊退号 - 检查是否可以退号
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void isFlagOutRegister(OutptRegisterDO outptRegisterDO) {
        // 判断标准 - 就诊表中是否就诊字段 is_visit。
        OutptVisitDO outptVisit =  outptRegisterDAO.getVisitInfoByVisitId(outptRegisterDO);
        if (outptVisit == null) {
            throw new AppException("未找到就诊信息");
        }

        if (Constants.SF.S.equals(outptVisit.getIsVisit())) {
            throw new AppException("已经就诊，无法退号");
        }
    }

    /**
     * @Menthod updateRegisterState
     * @Desrciption 门诊退号 - 挂号信息表作废(修改作废状态)
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void updateRegisterState(OutptRegisterDO outptRegisterDO) {
        OutptRegisterDTO updateDto = new OutptRegisterDTO();
        updateDto.setId(outptRegisterDO.getId());
        updateDto.setHospCode(outptRegisterDO.getHospCode());
        updateDto.setIsCancel("1");
        outptRegisterDAO.updateByPrimaryKeySelective(updateDto);
    }

    /**
     * @Menthod registerDetailChangeRed
     * @Desrciption 门诊退号 - 支付费用冲红(状态标志:0：正常 1：被冲红 2：冲红)
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 15:40:23
     **/
    private void registerDetailChangeRed(OutptRegisterDO outptRegisterDO) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        selectMap.put("visitId",outptRegisterDO.getVisitId());
        List<OutptRegisterDetailDO> list = outptRegisterDAO.selectDetailByRegisterId(selectMap);
        if (!ListUtils.isEmpty(list)) {
            // 原始数据被冲红
            for (OutptRegisterDetailDO outptRegisterDetailDO : list) {
                outptRegisterDetailDO.setStatusCode(Constants.ZTBZ.BCH);
            }
            outptRegisterDAO.updateDetailById(list);

            // 新增一条冲红数据
            for (OutptRegisterDetailDO outptRegisterDetailDO : list) {
                outptRegisterDetailDO.setOriginId(outptRegisterDetailDO.getId());
                outptRegisterDetailDO.setId(SnowflakeUtils.getId());
                outptRegisterDetailDO.setStatusCode(Constants.ZTBZ.CH);

                // 金额置反
                outptRegisterDetailDO.setTotalPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getTotalPrice()));
                outptRegisterDetailDO.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getPreferentialPrice()));
                outptRegisterDetailDO.setRealityPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getRealityPrice()));
                outptRegisterDetailDO.setNum(BigDecimalUtils.negate(outptRegisterDetailDO.getNum()));
                outptRegisterDetailDO.setCardPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getCardPrice()));
                outptRegisterDetailDO.setCreditPrice(BigDecimalUtils.negate(outptRegisterDetailDO.getCreditPrice()));

                // 创建信息
                outptRegisterDetailDO.setCrteId(outptRegisterDO.getCrteId());
                outptRegisterDetailDO.setCrteName(outptRegisterDO.getCrteName());
                outptRegisterDetailDO.setCrteTime(DateUtils.getNow());
            }
            outptRegisterDAO.insertDetail(list);
        }
    }

    /**
     * @Menthod registerSettleChangeRed
     * @Desrciption 门诊退号 - 挂号结算数据冲红
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/11 11:41:23
     **/
    private void registerSettleChangeRed(OutptRegisterDO outptRegisterDO,String redSettleId) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        OutptRegisterSettleDO outptRegisterSettleDO = outptRegisterDAO.selectSettleByRegisterId(selectMap);
        if (outptRegisterSettleDO != null) {
            // 原始数据被冲红
            outptRegisterSettleDO.setStatusCode(Constants.ZTBZ.BCH);
            outptRegisterDAO.updateSettleByRegisterId(outptRegisterSettleDO);

            // 新增一条冲红数据
            outptRegisterSettleDO.setOriginId(outptRegisterSettleDO.getId());
            outptRegisterSettleDO.setId(redSettleId);
            outptRegisterSettleDO.setStatusCode(Constants.ZTBZ.CH);

            // 金额置反
            outptRegisterSettleDO.setTotalPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getTotalPrice()));
            outptRegisterSettleDO.setPreferentialPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getPreferentialPrice()));
            outptRegisterSettleDO.setRealityPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getRealityPrice()));
            outptRegisterSettleDO.setCardPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getCardPrice()));
            outptRegisterSettleDO.setCreditPrice(BigDecimalUtils.negate(outptRegisterSettleDO.getCreditPrice()));

            // 日结缴款id为空
            outptRegisterSettleDO.setDailySettleId("");
            outptRegisterSettleDO.setSettleTime(DateUtils.getNow());
            outptRegisterSettleDO.setCrteId(outptRegisterDO.getCrteId());
            outptRegisterSettleDO.setCrteName(outptRegisterDO.getCrteName());
            outptRegisterSettleDO.setCrteTime(DateUtils.getNow());
            outptRegisterDAO.insertSettle(outptRegisterSettleDO);

            // 门诊退号退费时需要更新消费异动表
            if (outptRegisterSettleDO.getCardPrice() != null && !BigDecimalUtils.isZero(outptRegisterSettleDO.getCardPrice())) {
                selectMap.put("crteId", outptRegisterDO.getCrteId());
                selectMap.put("crteName", outptRegisterDO.getCrteName());
                selectMap.put("settleId", redSettleId);
                Boolean isTrue = baseCardRechargeChangeService.saveOutptRegisterTuiFei(selectMap);
                if (!isTrue) {
                    throw new AppException("一卡通退费失败，请联系管理员");
                }
            }

            Map <String,Object> invoiceMap = new HashMap<>();
            invoiceMap.put("hospCode",outptRegisterSettleDO.getHospCode());
            invoiceMap.put("invoiceId",outptRegisterSettleDO.getBillId());
            invoiceMap.put("invoiceNo",outptRegisterSettleDO.getBillNo());
            WrapperResponse wr = outinInvoiceService_consumer.getInvoiceDetailByInvoiceInfo(invoiceMap);
            if (wr != null && wr.getData() != null) {
                OutinInvoiceDetailDO outinInvoiceDetailDO = (OutinInvoiceDetailDO) wr.getData();
                outinInvoiceDetailDO.setStatusCode(Constants.PJSYZT.ZF);
                Map <String,Object> detailMap = new HashMap<>();
                detailMap.put("hospCode",outinInvoiceDetailDO.getHospCode());
                detailMap.put("outinInvoiceDetailDO",outinInvoiceDetailDO);
                outinInvoiceService_consumer.updateInvoiceDetailById(detailMap);
            }


        }
    }

    /**
     * @Menthod registerPayChangeRed
     * @Desrciption 门诊退号 - 挂号支付方式冲红
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void registerPayChangeRed(OutptRegisterDO outptRegisterDO,String redSettleId) {
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("registerId",outptRegisterDO.getId());
        selectMap.put("hospCode",outptRegisterDO.getHospCode());
        selectMap.put("visitId",outptRegisterDO.getVisitId());
        List<OutptRegisterPayDO> registerPayList = outptRegisterDAO.selectPayByRegisterId(selectMap);
        if (!ListUtils.isEmpty(registerPayList)) {
            // 批量金额冲红（原路返回原则）
            List<OutptRegisterPayDO> insertList = new ArrayList<>();
            for(int i = 0; i < registerPayList.size(); i ++) {
                OutptRegisterPayDO outptRegisterPayDO = registerPayList.get(i);
                outptRegisterPayDO.setId(SnowflakeUtils.getId());
                outptRegisterPayDO.setRsId(redSettleId);

                // 总金额冲红
                outptRegisterPayDO.setPrice(BigDecimalUtils.negate(outptRegisterPayDO.getPrice()));

                // 手续费（暂不考虑，默认为0）
                outptRegisterPayDO.setServicePrice(outptRegisterPayDO.getServicePrice());
                insertList.add(outptRegisterPayDO);
            }
            // 原始数据无需被冲红，批量冲红数据（金额置反）
            outptRegisterDAO.insertPayList(insertList);
        }
    }

    /**
     * @Menthod updatePatientState
     * @Desrciption 门诊退号 - 就诊记录直接删除
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void updatePatientState(OutptRegisterDO outptRegisterDO) {
        // 根据id删除就诊记录
        outptRegisterDAO.deleteVisitByVisitId(outptRegisterDO);
    }


    /**
     * @Menthod unBlockNumberInfo
     * @Desrciption 门诊退号 - 清除号源信息
     * @Param outptRegisterDO
     * @Author lioajiguang
     * @Date   2020/8/12 09:23:23
     **/
    private void unBlockNumberInfo(OutptRegisterDO outptRegisterDO) {
        // 更新号源使用信息(根据医生号源明细ID数据将此号源信息清空)
        Map<String,Object> updateMap = new HashMap<String,Object>();
        updateMap.put("hospCode",outptRegisterDO.getHospCode());
        updateMap.put("drId",outptRegisterDO.getDrId());
        outptRegisterDAO.updateDoctorRegister(updateMap);
    }

    // 推送挂号消息到医生  2022-01-06 liuliyun
    public void sendMessage(OutptRegisterDO outptRegisterDO){
        if (outptRegisterDO!=null && StringUtils.isNotEmpty(outptRegisterDO.getDoctorId())){
            String hospCode =outptRegisterDO.getHospCode();
            String name = outptRegisterDO.getCrteName();
            String crteId = outptRegisterDO.getCrteId();
            Map openParam = new HashMap();
            openParam.put("hospCode", hospCode);
            openParam.put("code", "MSG_OPEN");
            SysParameterDTO openSysParameterDTO = sysParameterService.getParameterByCode(openParam).getData();
            if (openSysParameterDTO!=null&& "1".equals(openSysParameterDTO.getValue())) {
                Map paramMap = new HashMap();
                paramMap.put("hospCode", hospCode);
                paramMap.put("code", "XXTS_SETTING");
                SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(paramMap).getData();
                ConfigInfoDO configInfoDO = null;
                if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                    configInfoDO = StringUtils.getConfigInfoDOFromSys(sysParameterDTO.getValue(),"outRegisterMsg");
                }
                if (configInfoDO ==null){
                    return;
                }
                if (outptRegisterDO != null) {
                    MessageInfoDTO messageInfoDTO = new MessageInfoDTO();
                    messageInfoDTO.setId(SnowflakeUtils.getId());
                    messageInfoDTO.setHospCode(hospCode);
                    messageInfoDTO.setSourceId("");
                    messageInfoDTO.setVisitId(outptRegisterDO.getVisitId());
                    messageInfoDTO.setDeptId("");
                    messageInfoDTO.setLevel(configInfoDO.getLevel());
                    messageInfoDTO.setReceiverId(outptRegisterDO.getDoctorId());   // 推送至挂号医生
                    messageInfoDTO.setSendCount(configInfoDO.getSendCount());
                    messageInfoDTO.setType(Constants.MSG_TYPE.MSG_YZ);
                    messageInfoDTO.setContent(outptRegisterDO.getName() + "已挂号到当前医生，请及时处理就诊信息");
                    Date startTime = DateUtils.dateAddMinute(new Date(), configInfoDO.getStartTime());
                    Date endTime = DateUtils.dateAddMinute(startTime, configInfoDO.getEndTime());
                    messageInfoDTO.setStartTime(startTime);
                    messageInfoDTO.setEndTime(endTime);
                    messageInfoDTO.setStatusCode(Constants.MSGZT.MSG_WD);
                    messageInfoDTO.setIntervalTime(configInfoDO.getIntervalTime());
                    messageInfoDTO.setUrl(configInfoDO.getUrl());
                    messageInfoDTO.setCrteName(name);
                    messageInfoDTO.setCrteTime(DateUtils.getNow());
                    messageInfoDTO.setCrteId(crteId);
                    List<MessageInfoDTO> messageInfoDTOList =new ArrayList<>();
                    messageInfoDTOList.add(messageInfoDTO);
                    // 获取医院kafka 的IP与端口
                    Map<String, Object> sysMap = new HashMap<>();
                    sysMap.put("hospCode", hospCode);
                    sysMap.put("code", "KAFKA_MSG_IP");
                    SysParameterDTO sys = sysParameterService.getParameterByCode(sysMap).getData();
                    if (sys == null || sys.getValue() == null) {
                        return;
                    }
                    String server = sys.getValue();
                    // 1. 创建一个kafka生产者
                    String producerTopic = Constants.MSG_TOPIC.producerTopicKey;//生产者消息推送Topic
                    KafkaProducer<String, String> kafkaProducer = KafkaUtil.createProducer(server);
                    String message = JSONObject.toJSONString(messageInfoDTOList);
                    KafkaUtil.sendMessage(kafkaProducer,producerTopic,message);
                }
            }
        }

    }

}

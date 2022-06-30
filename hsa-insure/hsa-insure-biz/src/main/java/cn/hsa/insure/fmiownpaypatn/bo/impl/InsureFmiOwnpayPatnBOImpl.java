package cn.hsa.insure.fmiownpaypatn.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.unifiedpay.bo.impl.InsureItfBOImpl;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;

import cn.hsa.insure.util.*;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnDiseListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnFeeListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnMdtrtDDTO;

import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.fmiownpaypatn.bo.InsureFmiOwnpayPatnBO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class InsureFmiOwnpayPatnBOImpl extends HsafBO implements InsureFmiOwnpayPatnBO {

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;


    @Resource
    private SysParameterService sysParameterService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DoctorAdviceService doctorAdviceService_consumer;
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;
    @Resource
    private InptVisitService inptVisitService_consumer;
    @Resource
    private OutptVisitService outptVisitService_consumer;
    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private UnifiedCommon unifiedCommon;

    @Resource
    private OutptVisitDAO outptVisitDAO;

    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Resource
    private OutptCostDAO outptCostDAO;

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureGetInfoDAO.queryVisit(insureSettleInfoDTO);
        // 获取医院医保编码
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        for(InptVisitDTO inptVisitDTO :visitDTOList){
            inptVisitDTO.setOrgCode(sysParameterDTO.getValue());
        }


        return PageDTO.of(visitDTOList);

    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryMatchFeePage(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO.setRegCode(insureSettleInfoDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());

        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的未匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptUnMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutUnMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureFmiOwnPayPatnCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        List<Map<String, Object>> mapList = new ArrayList<>();
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = null;
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = null;

        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = null;
        List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList = null;


        if (insureSettleInfoDTO.getLx().equals("1")) {
            inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            inptVisitDTO.setId(insureSettleInfoDTO.getId());
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            reqMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();

            String insureOrgCode = insureConfigurationDTO.getRegCode();
            inptVisitDTO.setInsureRegCode(insureOrgCode);
            List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
            inptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
            inptVisitDTO.setDiagnoseList(diagnoseList);
            reqMap.put("inptVisitDTO",inptVisitDTO);
            inptDiagnoseDTOList = doctorAdviceService_consumer.getInptDiagnose(reqMap).getData();
            if(ListUtils.isEmpty(inptDiagnoseDTOList)) {
                throw new AppException("该患者没有开诊断信息");
            }
            /**
             * 做医保入院登记办理的时候
             * 1.需要判断是否开了诊断
             * 2.开了的诊断是否已经匹配
             */
            inptMatchDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(reqMap).getData();

            mapList = handlerInptCostFee(insureSettleInfoDTO);
            //修改状态
            inptVisitDTO.setIsUplodDise("1");

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            Map<String, String> visitMap = new HashMap();
            visitMap.put("id",insureSettleInfoDTO.getId());
            visitMap.put("hospCode",insureSettleInfoDTO.getHospCode());

            outptVisitDTO = outptVisitService_consumer.queryByVisitID(visitMap);

            Map<String, Object> map = new HashMap<>();
            map.put("id",insureSettleInfoDTO.getId());
            map.put("hospCode",insureSettleInfoDTO.getHospCode());
            List<String> diagnoseList = Stream.of("101", "102").collect(Collectors.toList());
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            outptDiagnoseDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            outptDiagnoseDTO.setVisitId(insureSettleInfoDTO.getId());
            outptDiagnoseDTO.setInsureRegCode(insureSettleInfoDTO.getInsureRegCode());
            map.put("outptDiagnoseDTO", outptDiagnoseDTO);

            OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
            outptPrescribeDTO.setVisitId(insureSettleInfoDTO.getId());
            outptPrescribeDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            outptPrescribeDTO.setDiagnoseList(diagnoseList);
            map.put("outptPrescribeDTO", outptPrescribeDTO);
            outptDiagnoseDTOList = outptDoctorPrescribeService.getOutptDiagnose(map).getData();
            if (ListUtils.isEmpty(outptDiagnoseDTOList)) {
                throw new AppException("该患者没有开诊断信息");
            }
            outptMatchDiagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnose(map).getData();

            mapList = handlerOutptCostFee(insureSettleInfoDTO);
            //修改状态
            outptVisitDTO.setIsUploadDise("1");
        }


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("inptVisitDTO",inptVisitDTO);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        paramMap.put("inptMatchDiagnoseDTOList",inptMatchDiagnoseDTOList);

        paramMap.put("outptVisitDTO",outptVisitDTO);
        paramMap.put("outptDiagnoseDTOList",outptDiagnoseDTOList);
        paramMap.put("outptMatchDiagnoseDTOList",outptMatchDiagnoseDTOList);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);


        paramMap.put("mapList",mapList);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        // 参保地医保区划
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.F);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setFixmedins_code(insureSettleInfoDTO.getOrgCode());
        interfaceParamDTO.setFixmedins_name(insureConfigurationDTO.getHospName());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_UPLOD, interfaceParamDTO);

        insertHandlerInsureCost(mapList, insureSettleInfoDTO);
        //修改状态
        if(insureSettleInfoDTO.getLx().equals("1")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitService_consumer.updateUplod(updateMap);
        }else if (insureSettleInfoDTO.getLx().equals("0")){
            Map paramMap1 = new HashMap();
            paramMap1.put("hospCode",insureSettleInfoDTO.getHospCode());
            paramMap1.put("outptVisitDTO",outptVisitDTO);
            outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        }
        return true;
    }

    @Override
    public Map<String, Object> queryFmiOwnPayPatnReconciliationInfo(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }

        paramMap.put("fixmedinsCode",sysParameterDTO.getValue());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("certno", insureSettleInfoDTO.getCertNo());
        paramMap.put("pageNum", insureSettleInfoDTO.getPageNo());
        paramMap.put("pageSize", insureSettleInfoDTO.getPageSize());
        // 参保地医保区划
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
//        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
//        paramMap.put("isHospital", Constants.SF.F);
        paramMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode",insureSettleInfoDTO.getInsureRegCode());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_LEDGER_DETAIL.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_LEDGER_DETAIL, interfaceParamDTO);
        return res;
    }

    @Override
    public Map<String, Object> queryFmiOwnPayPatnReconciliation(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }

        paramMap.put("fixmedinsCode",sysParameterDTO.getValue());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("mdtrtId", insureSettleInfoDTO.getId());

        BigDecimal sum = new BigDecimal(0);
        List<InsureUploadCostDTO> costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);
        if(!costDTOList.isEmpty() && costDTOList.size() > 0) {
            for(InsureUploadCostDTO insureUploadCostDTO : costDTOList){
                sum = sum.add(insureUploadCostDTO.getDetItemFeeSumamt());
            }
        }
        paramMap.put("totalFeeSumamt", sum);
        // 参保地医保区划
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureSettleInfoDTO.getOrgCode());
        paramMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode",insureSettleInfoDTO.getInsureRegCode());
//        paramMap.put("isHospital", Constants.SF.F);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_LEDGER.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_LEDGER, interfaceParamDTO);
        return res;
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        if (insureSettleInfoDTO.getLx().equals("1")) {
            mapList = handlerInptCostFee(insureSettleInfoDTO);
        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            mapList = handlerOutptCostFee(insureSettleInfoDTO);
        }
        // 3.组装医保接口参数
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(mapList)) {

            for (Map<String, Object> item : mapList) {
                // 医保上传
                Map feedetail = new HashMap();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");
                feedetail.put("mdtrt_sn", insureSettleInfoDTO.getVisitNo()); // 就医流水号
                feedetail.put("ipt_otp_no", insureSettleInfoDTO.getVisitNo()); // 住院/门诊号
                if ("1".equals(insureSettleInfoDTO.getLx())) {
                    feedetail.put("med_type", "1201"); // 医疗类别
                } else {
                    feedetail.put("med_type", "11"); // 医疗类别
                }
                feedetail.put("chrg_bchno", "0"); // 收费批次号
                feedetail.put("feedetl_sn", MapUtils.get(item, "id")); // 费用明细流水号
                feedetail.put("psn_cert_type", insureSettleInfoDTO.getCertCode()); // 人员证件类型
                feedetail.put("certno", insureSettleInfoDTO.getCertNo()); // 证件号码
                feedetail.put("psn_name", insureSettleInfoDTO.getName()); // 人员姓名
                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.put("cnt", cnt); // 数量
                feedetail.put("pric", price); // 单价
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.put("det_item_fee_sumamt", convertPrice); // 明细项目费用总额
                feedetail.put("med_list_codg", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.put("medins_list_codg", item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.put("medins_list_name", MapUtils.get(item, "insureItemName")); // 医药机构目录名称
                feedetail.put("med_chrgitm_type", MapUtils.get(item, "insureItemType")); // 医疗收费项目类别
                feedetail.put("prodname", MapUtils.get(item, "insureItemName")); // 商品名
                feedetail.put("bilg_dept_codg", MapUtils.get(item, "deptId")); // 开单科室编码
                feedetail.put("bilg_dept_name", MapUtils.get(item, "deptName")); // 开单科室名称
                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.put("bilg_dr_codg", doctorId); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_codg", MapUtils.get(item, "deptId")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.put("bilg_dr_codg", doctorName); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_name", MapUtils.get(item, "doctorName")); // 开单医生姓名
                }
                listMap.add(feedetail);

            }
        }
            /**
             * 公共入参
             */
        Map param = new HashMap();
        param.put("infno", "4201");  //交易编号
        param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        param.put("insuplc_admdvs", ""); //参保地医保区划分
        param.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        param.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        for (Map map : listMap) {
            param.put("input", map);  //交易输入
            String json = JSONObject.toJSONString(param);
            logger.info("自费病人费用明细传输入参:" + json);
            String url = insureConfigurationDTO.getUrl();
            String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
            logger.info("自费病人费用明细传输回参:" + resultJson);
            Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
            if (StringUtils.isEmpty(resultJson)) {
                throw new AppException("访问统一支付平台失败");
            }
            if ("999".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("msg"));
            }
            if (!"0".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("err_msg"));
            }
        }
        insertHandlerInsureCost(listMap, insureSettleInfoDTO);
        return true;
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-17 9:01
     * @Description 查询自费病人门诊就医信息
     * @param outptVisitDTO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryInsureOutptMedicTreatMent(OutptVisitDTO outptVisitDTO) {
        // 设置分页信息
        PageHelper.startPage(outptVisitDTO.getPageNo(),outptVisitDTO.getPageSize());
       /* int pageNo = outptVisitDTO.getPageNo();
        int pageSize = outptVisitDTO.getPageSize();
        int satrtIndex = 1;
        int endIndex = 1;
        if(pageNo==1){
            satrtIndex = 0;
            endIndex = pageSize;
        }else if(pageNo>1){
            satrtIndex = (pageNo-1)*pageSize;
            endIndex = pageNo*pageSize;
        }*/
        Map visitMap = new HashMap();
        visitMap.put("hospCode", outptVisitDTO.getHospCode());
        //查询全自费病人门诊集合信息
        List<OutptVisitDTO> outptVisitDTOS = outptVisitService_consumer.queryOutptVisitSelfFeePatient(visitMap);
        /*//获取门诊就诊id集合
        List<String> ids = outptVisitDTOS.stream().map(OutptVisitDTO::getId).collect(Collectors.toList());
        //查询门诊诊断信息-门诊id在ids中
        List<OutptDiagnoseDTO> outptDiagnoseDTOS = outptDoctorPrescribeDAO.queryOutptDiagnoseByVisitIds(ids);
        //查询门诊费用明细信息-门诊id在ids中
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryOutptCostByvisitIds(ids);

        //组装数据
        List<InsureOutptMedicTreatMentDTO> medicTreatMentDTOList = new ArrayList<>();
        for(OutptVisitDTO visitDTO:outptVisitDTOS){
            List<OutptDiagnoseDTO> diagnoseDTOList = new ArrayList<>();
            List<OutptCostDTO> costDTOList = new ArrayList<>();
            InsureOutptMedicTreatMentDTO medicTreatMentDTO = new InsureOutptMedicTreatMentDTO();
            //匹配诊断信息
            for(OutptDiagnoseDTO diagnoseDTO:outptDiagnoseDTOS){
                if(visitDTO.getId().equals(diagnoseDTO.getVisitId())){
                    diagnoseDTOList.add(diagnoseDTO);
                }
            }
            //匹配门诊费用信息
            for(OutptCostDTO costDTO:outptCostDTOS){
                if(visitDTO.getId().equals(costDTO.getVisitId())){
                    costDTOList.add(costDTO);
                }
            }
            medicTreatMentDTO.setMdtrtinfo(visitDTO);
            medicTreatMentDTO.setDiseinfo(diagnoseDTOList);
            medicTreatMentDTO.setFeedetail(costDTOList);
            medicTreatMentDTOList.add(medicTreatMentDTO);
        }

        if(pageNo*pageSize>medicTreatMentDTOList.size()){
            endIndex = medicTreatMentDTOList.size();
        }
        List<InsureOutptMedicTreatMentDTO> dataList1 = medicTreatMentDTOList.subList(satrtIndex,endIndex);
        PageInfo pageInfo = new PageInfo(medicTreatMentDTOList);
        PageDTO dto = new PageDTO();
        dto = PageDTO.of(dataList1);
        dto.setTotal(pageInfo.getTotal());*/
        return PageDTO.of(outptVisitDTOS);
    }

    @Override
    public Boolean insertOutptMedicTreatMent(InsureSettleInfoDTO insureSettleInfoDTO) {
        if(StringUtils.isEmpty(insureSettleInfoDTO.getId())){
            throw  new AppException("门诊就诊id为空！");
        }
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);

        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList = new ArrayList<>();
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();

        outptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        outptVisitDTO.setId(insureSettleInfoDTO.getId());

        Map map = new HashMap();
        map.put("id",insureSettleInfoDTO.getId());
        map.put("hospCode",insureSettleInfoDTO.getHospCode());
        outptVisitDTO = outptVisitService_consumer.queryByVisitID(map);
        //查询诊断信息
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("outptVisitDTO", outptVisitDTO);
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        outptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        outptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
        outptVisitDTO.setInsureRegCode(insureOrgCode);
        List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
        outptVisitDTO.setDiagnoseList(diagnoseList);
        reqMap.put("outptVisitDTO", outptVisitDTO);
        outptDiagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnoseByVisitIds(reqMap).getData();
        if(ListUtils.isEmpty(outptDiagnoseDTOList)) {
            throw new AppException("该患者没有开诊断信息");
        }
        /**
         * 做医保入院登记办理的时候
         * 1.需要判断是否开了诊断
         * 2.开了的诊断是否已经匹配
         */
        outptMatchDiagnoseDTOList = outptDoctorPrescribeService.queryOutptMatchDiagnose(reqMap).getData();
        mapList = handlerOutptCostFee(insureSettleInfoDTO);
        BigDecimal sum = new BigDecimal(0);
        if(!mapList.isEmpty() && mapList.size() > 0) {
            for(Map<String, Object> item : mapList){
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                sum = sum.add(convertPrice);
            }
        }
        //查询门诊费用信息
        //outptCostDTOList = outptVisitService_consumer.queryOutptCostByvisitIds(reqMap);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("outptVisitDTO",outptVisitDTO);
        paramMap.put("outptDiagnoseDTOList",outptDiagnoseDTOList);
        paramMap.put("outptMatchDiagnoseDTOList",outptMatchDiagnoseDTOList);
        paramMap.put("outptCostDTOList",mapList);
        paramMap.put("medfeeSumamt",sum);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.S);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DISE_FEE_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DISE_FEE_UPLOD, interfaceParamDTO);
        //修改上传状态为已上传
        outptVisitDTO.setIsUploadDise("1");
        outptVisitDTO.setCpltFlag("0");
        Map paramMap1 = new HashMap();
        paramMap1.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap1.put("outptVisitDTO",outptVisitDTO);
        outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        return true;
    }

    @Override
    public Boolean deleteOutptMedicTreatMent(InsureSettleInfoDTO insureSettleInfoDTO) {
        if(StringUtils.isEmpty(insureSettleInfoDTO.getId())){
            throw  new AppException("门诊就诊id为空！");
        }
        String hospCode = insureSettleInfoDTO.getHospCode();
        String insureRegCode = insureSettleInfoDTO.getInsureRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO == null){
            throw new AppException("根据医保机构编码未查找到医保机构配置信息");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_4206);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fixmedins_mdtrt_id",insureSettleInfoDTO.getId()); // 医药机构就诊ID
        paramMap.put("fixmedins_code",insureConfigurationDTO.getOrgCode()); //定点医疗机构编号

        Map<String, Object> inputMap = new HashMap<>();
        //inputMap.put("data", paramMap);
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("自费病人门诊就医信息删除:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        logger.info("自费病人门诊就医信息删除:" + resultJson);

        //修改上传状态
        Map paramMap1 = new HashMap();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setIsUploadDise("0");
        outptVisitDTO.setCpltFlag("0");
        outptVisitDTO.setId(insureSettleInfoDTO.getId());
        outptVisitDTO.setHospCode(hospCode);
        paramMap1.put("outptVisitDTO",outptVisitDTO);
        paramMap1.put("hospCode",hospCode);
        outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        return true;
    }


    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 西藏 -- 4202自费病人住院就诊和诊断信息上传
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureMdtrtAndDiag(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = new ArrayList<>();
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = new ArrayList<>();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getId());
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
        if(inptVisitDTO == null){
            throw new AppException("未查到该患者的就诊信息");
        }
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        inptVisitDTO.setInsureRegCode(insureOrgCode);
        List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
        inptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
        inptVisitDTO.setDiagnoseList(diagnoseList);
        reqMap.put("inptVisitDTO",inptVisitDTO);
        inptDiagnoseDTOList = doctorAdviceService_consumer.getInptDiagnose(reqMap).getData();
        if(ListUtils.isEmpty(inptDiagnoseDTOList)) {
            throw new AppException("该患者没有开诊断信息");
        }
        /**
         * 做医保入院登记办理的时候
         * 1.需要判断是否开了诊断
         * 2.开了的诊断是否已经匹配
         */
        inptMatchDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(reqMap).getData();
        mapList = handlerInptCostFee(insureSettleInfoDTO);
        BigDecimal sum = new BigDecimal(0);
        List<InsureUploadCostDTO> costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);
        if(!mapList.isEmpty() && mapList.size() > 0) {
            for(Map<String, Object> item : mapList){
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                sum = sum.add(convertPrice);
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("inptVisitDTO",inptVisitDTO);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        paramMap.put("inptMatchDiagnoseDTOList",inptMatchDiagnoseDTOList);
        paramMap.put("medfeeSumamt",sum);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.S);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DISE_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DISE_UPLOD, interfaceParamDTO);

        //修改状态
        inptVisitDTO.setIsUplodDise("1");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);
        return true;
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 西藏 -- 4203自费病人就诊以及费用明细上传完成
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureFinish(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isEmpty(insureSettleInfoDTO.getCpltFlag())){
            throw new AppException("请选择完成标志");
        }
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();

        if (insureSettleInfoDTO.getLx().equals("1")) {
            inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            inptVisitDTO.setId(insureSettleInfoDTO.getId());
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            reqMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
            inptVisitDTO.setCpltFlag("1");

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            Map<String, String> visitMap = new HashMap();
            visitMap.put("id",insureSettleInfoDTO.getId());
            visitMap.put("hospCode",insureSettleInfoDTO.getHospCode());

            outptVisitDTO = outptVisitService_consumer.queryByVisitID(visitMap);
            outptVisitDTO.setCpltFlag("1");
        }
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("cpltFlag", insureSettleInfoDTO.getCpltFlag());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureSettleInfoDTO.getLx());
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_FINISH.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_FINISH, interfaceParamDTO);
        //修改状态
        if(insureSettleInfoDTO.getLx().equals("1")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitService_consumer.updateUplod(updateMap);
        }
        if(insureSettleInfoDTO.getLx().equals("0")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("outptVisitDTO",outptVisitDTO);
            outptVisitService_consumer.updateUplod(updateMap);
        }
        return true;
    }

    /**
     * @Method insertInsureFmiOwnPayPatnCost
     * @Desrciption 自费病人费用明细信息删除
     * 1.查询是否有自费就诊记录
     * 2.查询自费上传表是否有数据
     * 3.调用医保接口
     * 4.删除本地数据
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2022-05-17 13:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @Override
    public Boolean deleteInsureFmiOwnPayPatnCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
//        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        //1.查询是否有自费就诊记录
        List<InsureUploadCostDTO> costDTOList = insureIndividualCostDAO.queryFeeInfoDetailPage(insureSettleInfoDTO);
        if(ListUtils.isEmpty(costDTOList)){
            throw new AppException("没有要删除的费用记录!");
        }
        //2.调用接口
        Map paramMap = new HashMap();
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("hospCode", insureConfigurationDTO.getHospCode());
        paramMap.put("configRegCode",insureConfigurationDTO.getRegCode());
        //处理其他入参
        Map<String, Object> feedetailMap = new HashMap<>();
        feedetailMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getVisitId());
        feedetailMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
        paramMap.put("feedetail",feedetailMap);
        //处理流水号
        List<String> feeIdList = insureSettleInfoDTO.getFeeIdList();
        if(feeIdList == null ){
            throw new AppException("没有需要删除的费用！");
        }
        if(feeIdList != null && insureSettleInfoDTO.getFeeIdList().size() != costDTOList.size()){
            List<Map<String,Object>> bkkpSnList = new ArrayList<>();
            for (String s : feeIdList) {
                Map<String,Object> bkkpMap = new HashMap<>();
                bkkpMap.put("bkkp_sn",s);
                bkkpSnList.add(bkkpMap);
            }
            paramMap.put("feedetl",bkkpSnList);
        }
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DELETE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DELETE, interfaceParamDTO);
        //删除本地数据
        insureGetInfoDAO.deleteFmiOwnPayPatnCost(feeIdList,insureSettleInfoDTO.getHospCode());
        //修改自费病人费用上传状态
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getVisitId());
        inptVisitDTO.setIsUplodCost("0");
        inptVisitDTO.setCpltFlag("0");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);

        return true;
    }

    @Override
    public PageDTO queryFeeInfoDetailPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<InsureUploadCostDTO> costDTOList = insureIndividualCostDAO.queryFeeInfoDetailPage(insureSettleInfoDTO);
        return PageDTO.of(costDTOList);
    }


    @Override
    public PageDTO queryFmiOwnPayInfoDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.组装医保交易接口参数
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("参数信息" + insureSettleInfoDTO + paramMap.toString());
        paramMap.put("psn_cert_type", insureSettleInfoDTO.getPsnCertType());
        paramMap.put("certno", insureSettleInfoDTO.getCertNo());
        paramMap.put("psn_name", insureSettleInfoDTO.getPsnName());
        paramMap.put("begntime", insureSettleInfoDTO.getBegntime());
        paramMap.put("endtime", insureSettleInfoDTO.getEndtime());
        paramMap.put("medfee_sumamt", insureSettleInfoDTO.getMedfeeSumamt());
        paramMap.put("elec_billno_code", insureSettleInfoDTO.getElecBillnoCode());
        paramMap.put("cplt_flag", insureSettleInfoDTO.getCpltFlag());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());
        //公共入参，调用医保接口
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4208, paramMap);
        //取出交易输出
        Map<String, Object> resultMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnMdtrtDDTO> resultList = new ArrayList<>();
        if (!MapUtils.isEmpty(resultMap)) {
            resultList = MapUtils.get(resultMap, "data");
        }

        return PageDTO.of(resultList);
    }

    @Override
    public PageDTO queryFmiOwnPayPatnFeeListDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.组装医保接口查询条件参数
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("参数信息" + insureSettleInfoDTO);
        paramMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getFixmedinsMdtrtId());
        paramMap.put("fixmedins_code", insureSettleInfoDTO.getFixmedinsCode());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());

        //公共入参，调用医保接口
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4207, paramMap);
        //取出交易输出
        Map<String, Object> resultMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnFeeListDDTO> resultLists = new ArrayList<>();
        resultLists = MapUtils.get(resultMap, "data");
        return PageDTO.of(resultLists);
    }

    @Override
    public PageDTO queryFmiOwnPayDiseListDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.组装医保接口查询条件参数
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("参数信息" + insureSettleInfoDTO);
        paramMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getFixmedinsMdtrtId());
        paramMap.put("fixmedins_code", insureSettleInfoDTO.getFixmedinsCode());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());

        //公共入参 并调用医保接口
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4209, paramMap);
        //取出交易输出
        Map<String, Object> outputMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnDiseListDDTO> resultList = MapUtils.get(outputMap, "data");
        return PageDTO.of(resultList);
    }

    @Override
    public Boolean insertInsureInputCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        //查询患者信息
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getId());
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
        if(inptVisitDTO == null){
            throw new AppException("未查到该患者的就诊信息");
        }
        //查询费用
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = handlerInptCostFee(insureSettleInfoDTO);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("mapList",mapList);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.F);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_INPUT_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_INPUT_UPLOD, interfaceParamDTO);

        //修改状态
        inptVisitDTO.setIsUplodCost("1");
        inptVisitDTO.setCpltFlag("0");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);
        insertHandlerInsureCost(mapList, insureSettleInfoDTO);
        return true;
    }

    /**
     * @Method insertHandlerInsureCost
     * @Desrciption 处理自费病人明细数据
     * @Param item ：上传参数
     * infoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:57
     * @Return
     **/
    private void insertHandlerInsureCost(List<Map<String, Object>> feeList, InsureSettleInfoDTO infoDTO) {
        List<InsureUploadCostDTO> insureIndividualCostDOList = new ArrayList<>();
        for(Map<String, Object> item : feeList){
            InsureUploadCostDTO insureUploadCostDTO = new InsureUploadCostDTO();
            insureUploadCostDTO.setId(SnowflakeUtils.getId());//id
            insureUploadCostDTO.setHospCode(infoDTO.getHospCode());//医院编码
            insureUploadCostDTO.setVisitId(infoDTO.getId());//患者id

            insureUploadCostDTO.setMdtrtSn("");
            insureUploadCostDTO.setIptOtpNo("");
            insureUploadCostDTO.setMedType("");
            insureUploadCostDTO.setChrgBchno("");
            insureUploadCostDTO.setPsnCertType("");
            insureUploadCostDTO.setCertno("");
            insureUploadCostDTO.setPsnName("");
            insureUploadCostDTO.setOrgCode(infoDTO.getInsureRegCode());
            insureUploadCostDTO.setCostId(MapUtils.get(item, "id"));//费用id

            insureUploadCostDTO.setCrteId(infoDTO.getCrteId());//创建id
            insureUploadCostDTO.setCrteName(infoDTO.getCrteName());//创建人姓名
            insureUploadCostDTO.setCrteTime(new Date());//创建时间

            String doctorId = MapUtils.get(item, "doctorId");
            String doctorName = MapUtils.get(item, "doctorName");
            insureUploadCostDTO.setFeedetlSn(MapUtils.get(item, "id"));
            if (infoDTO.getLx().equals("1")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("costTime")); // 费用发生时间
            } else if (infoDTO.getLx().equals("0")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("crteTime")); // 费用发生时间
            }

            BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
            BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
            insureUploadCostDTO.setCnt(cnt); // 数量
            insureUploadCostDTO.setPric(price); // 单价

            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
            insureUploadCostDTO.setDetItemFeeSumamt(convertPrice); // 明细项目费用总额

            insureUploadCostDTO.setMedListCodg(item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
            insureUploadCostDTO.setMedinsListCodg(item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
            insureUploadCostDTO.setMedinsListName(MapUtils.get(item, "insureItemName")); // 医药机构目录名称
            insureUploadCostDTO.setMedChrgitmType(item.get("insureItemType") == null ? "" : item.get("insureItemType").toString()); // 医疗收费项目类别
            insureUploadCostDTO.setProdname(MapUtils.get(item, "insureItemName")); // 商品名

            insureUploadCostDTO.setBilgDeptCodg(MapUtils.get(item, "deptId")); // 开单科室编码
            insureUploadCostDTO.setBilgDeptName(MapUtils.get(item, "deptName")); // 开单科室名称

            if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                insureUploadCostDTO.setBilgDrCodg( doctorId); // 开单医生编码
            } else {
                insureUploadCostDTO.setBilgDrCodg( MapUtils.get(item, "deptId")); // 开单医生编码
            }
            if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                insureUploadCostDTO.setBilgDrName( doctorName); // 开单医生编码
            } else {
                insureUploadCostDTO.setBilgDrName( MapUtils.get(item, "doctorName")); // 开单医生姓名
            }
            insureIndividualCostDOList.add(insureUploadCostDTO);
        }
        insureGetInfoDAO.deleteCost(infoDTO.getId(),infoDTO.getHospCode());
        insureGetInfoDAO.insertCost(insureIndividualCostDOList);
    }

    /**
     * @Method checkInsureConfig
     * @Desrciption 检查医保机构是否已选择
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:58
     * @Return
     **/
    private InsureConfigurationDTO checkInsureConfig(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        if (insureSettleInfoDTO.getInsureRegCode() == null) {
            throw new AppException("请先选择医保机构");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO.setRegCode(insureSettleInfoDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("医保机构配置信息为空");
        }
        return insureConfigurationDTO;
    }

    /**
     * @Method handlerOutptCostFee
     * @Desrciption 处理门诊患者费用
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 10:54
     * @Return
     **/
    private List<Map<String, Object>> handlerOutptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {

        String insureRegCode = insureSettleInfoDTO.getInsureRegCode();
        //判断是否有传输费用信息
        Map<String, Object> insureCostParam = new HashMap<String, Object>();
        insureCostParam.put("hospCode", insureSettleInfoDTO.getHospCode());//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", insureSettleInfoDTO.getId());//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
//        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital", Constants.SF.F); // 区分门诊还是住院
        insureCostParam.put("settle_code", "2");//费用结算状态
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该病人没有可以上传的费用");
        }
        return insureCostList;
    }

    /**
     * @Method handlerInptCostFee
     * @Desrciption 处理住院病人医保费用数据
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 10:38
     * @Return
     **/
    private List<Map<String, Object>> handlerInptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, String> insureCostParam = new HashMap<String, String>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
//        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureSettleInfoDTO.getOrgCode());// 医保机构编码
        insureCostParam.put("queryBaby", "N");// 医保机构编码
        insureCostParam.put("settle_code", "2");//费用结算状态
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该自费病人没有匹配的费用明细数据");
        }
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        // 查询有退费的数据集合,且未上传的的数据集合
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryBackInptFee(insureIndividualVisitDTO);
        // 查询已经上传的费用数据
        List<Map<String, Object>> individualCostDTOList = insureIndividualCostDAO.queryInsureInptCost(insureIndividualVisitDTO);
        int num = 0;
        /**
         * 本次上传如果没有正常的费用数据,则不上传到医保，直接把退费的数据插入到医保费用表
         */
        if (!ListUtils.isEmpty(insureCostList)) {
            for (Map<String, Object> item : insureCostList) {
                if ("0".equals(MapUtils.get(item, "statusCode"))) {
                    num++;
                } else {
                    continue;
                }
            }
        }
        if (num == 0) {
            // 直接把全开，全退的费用保存到费用表 但是不进行调用医保的操作
            insertNotUpLoadFee(insureCostList, insureSettleInfoDTO);
            return new ArrayList<>();
        } else {
            // 说明有正常的数据 需要调用医保接口
            List<Map<String, Object>> list2 = new ArrayList<>();  // 处理
            List<Map<String, Object>> list1 = new ArrayList<>();
            List<Map<String, Object>> list3 = new ArrayList<>(); // 处理正负直接相抵的集合
            if (!ListUtils.isEmpty(inptCostDTOList)) {
                Map<String, InptCostDTO> collect = inptCostDTOList.stream().collect(Collectors.toMap(InptCostDTO::getOldCostId, Function.identity()));
                // 传正常的数据    假如最原始已经上传 10条  退4条     第二次传输 则  传-10  正6
                for (Map<String, Object> item : insureCostList) {
                    if (!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item, "id"))) {
                        list3.add(item);
                        continue;
                    } else if (!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item, "oldCostId")) &&
                            BigDecimalUtils.less(MapUtils.get(item, "totalNum"), new BigDecimal(0.00))) {
                        list3.add(item);
                        continue;
                    } else {
                        list1.add(item);
                    }
                }
                // 传退费对应的数据
                if (!ListUtils.isEmpty(individualCostDTOList)) {
                    for (Map<String, Object> item : individualCostDTOList) {
                        if (collect.containsKey(MapUtils.get(item, "costId"))) {
                            list2.add(item);
                        }
                    }
                }

                if (!ListUtils.isEmpty(list3)) {
                    insertNotUpLoadFee(list3, insureSettleInfoDTO);
                }
                list2.addAll(list1);
            } else {
                list2.addAll(insureCostList);
            }
            return list2;
        }

    }

    /**
     * @Method
     * @Desrciption 保存未上传的费用数据
     * @Param insureCostList：未上传的费用集合
     * inptVisitDTO ：患者基本信息
     * @Author fuhui
     * @Date 2021/8/13 9:16
     * @Return
     **/
    private void insertNotUpLoadFee(List<Map<String, Object>> insureCostList, InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        String crteId = insureSettleInfoDTO.getCrteId();
        String crteName = insureSettleInfoDTO.getCrteName();
        for (Map<String, Object> item : insureCostList) {
            InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
            insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
            insureIndividualCostDO.setHospCode(hospCode);//医院编码
            insureIndividualCostDO.setVisitId(visitId);//患者id
            insureIndividualCostDO.setCostId((String) item.get("id"));//费用id
            insureIndividualCostDO.setSettleId(null);//结算id
            insureIndividualCostDO.setIsHospital(Constants.SF.S);//是否住院 = 是
            insureIndividualCostDO.setItemType((String) item.get("insureItemType"));//医保项目类别
            insureIndividualCostDO.setItemCode((String) item.get("insureItemCode"));//医保项目编码
            insureIndividualCostDO.setItemName((String) item.get("insureItemName"));//医保项目名称
            insureIndividualCostDO.setGuestRatio((String) item.get("deductible"));//自付比例
            insureIndividualCostDO.setPrimaryPrice((BigDecimal) item.get("realityPrice"));//原费用
            insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
            insureIndividualCostDO.setOrderNo(null);//顺序号
            insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
            insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志 = 已传输
            insureIndividualCostDO.setCrteId(crteId);//创建id
            insureIndividualCostDO.setCrteName(crteName);//创建人姓名
            insureIndividualCostDO.setCrteTime(new Date());//创建时间
            insureIndividualCostDOList.add(insureIndividualCostDO);
        }
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }


    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    public PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InsureUploadCostDTO> costDTOList = new ArrayList();

        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);

        return PageDTO.of(costDTOList);
    }




}

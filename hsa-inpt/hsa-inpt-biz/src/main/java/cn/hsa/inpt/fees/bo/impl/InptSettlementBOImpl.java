package cn.hsa.inpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.advancepay.dao.InptAdvancePayDAO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.compositequery.service.CompositeQueryService;
import cn.hsa.module.inpt.doctor.dao.InptBabyDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.fees.bo.InptSettlementBO;
import cn.hsa.module.inpt.fees.dao.*;
import cn.hsa.module.inpt.fees.entity.*;
import cn.hsa.module.inpt.fees.service.InptSettlementService;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.bo.InsureIndividualCostBO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.module.service.*;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.entity.SysParameterDO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.fees.bo.impl
 * @Class_name: InptSettlementBOImpl
 * @Describe(描述): 住院结算BOImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InptSettlementBOImpl extends HsafBO implements InptSettlementBO {

    @Resource
    private InptVisitDAO inptVisitDAO;

    @Resource
    private InptAdvancePayDAO inptAdvancePayDAO;

    @Resource
    private InptCostDAO inptCostDAO;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InptSettleDAO inptSettleDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InptSettleInvoiceDAO inptSettleInvoiceDAO;

    @Resource
    private InptSettleInvoiceContentDAO inptSettleInvoiceContentDAO;

    @Resource
    private InptPayDAO inptPayDAO;

    @Resource
    private OutinInvoiceService outinInvoiceService_consumer;

    @Resource
    private InptCostSettleDAO inptCostSettleDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    private InptService inptService;

    @Resource
    private InsureIndividualSettleService insureIndividualSettleService;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService;

    @Resource
    private InsureIndividualBasicService insureIndividualBasicService;

    @Resource
    private InsureConfigurationService insureConfigurationService;

    @Resource
    private InptInsurePayDAO inptInsurePayDAO;

    @Resource
    private InsureIndividualCostService insureIndividualCostService;

    @Resource
    InsureUnifiedPayInptService insureUnifiedPayInptService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private InsureUnifiedPayInptService insureUnifiedPayInptService;

    @Resource
    private InptBabyDAO inptBabyDAO;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService_consumer;

    @Resource
    private InsureDetailAuditService insureDetailAuditService;


    @Resource
    private InsureIndividualBasicService InsureIndividualBasicServiceConsumer;

    @Resource
    private CompositeQueryService compositeQueryService;


    /**
     * @param inptVisitDTO 查询条件
     * @Menthod queryInptvisitByPage
     * @Desrciption 查询可结算的患者信息
     * @Author Ou·Mr
     * @Date 2020/9/25 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryInptvisitByPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        if(StringUtils.isEmpty(inptVisitDTO.getAttributionCode())){
          return WrapperResponse.success(PageDTO.of(inptVisitDAO.queryInptVisitList(inptVisitDTO)));
        } else {
          return WrapperResponse.success(PageDTO.of(inptVisitDAO.queryAttributionInptVisitList(inptVisitDTO)));
        }

    }

    /**
     * @param param 请求参数
     * @Menthod queryInptCostByList
     * @Desrciption 查询患者住院费用信息、预交金信息
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryInptCostByList(Map param) {
        //根据财务分类统计费用金额
        List<Map<String, Object>> inptCostList = inptCostDAO.queryInptCostByBfc(param);
        //根据就诊id查询预交金信息
        InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
        inptAdvancePayDTO.setHospCode((String) param.get("hospCode"));
        inptAdvancePayDTO.setVisitId((String) param.get("id"));
        List<InptAdvancePayDTO> inptAdvancePayDTOList = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
        JSONObject result = new JSONObject();
        result.put("inptCost", inptCostList);
        result.put("inptAdvancePay", inptAdvancePayDTOList);
        return WrapperResponse.success(result);
    }

    /**
     * @param inptVisitDTO 请求参数
     * @Menthod saveCostTrial
     * @Desrciption 住院试算
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56 //
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    @Async
    public synchronized WrapperResponse saveCostTrial(InptVisitDTO inptVisitDTO) {
        String id = inptVisitDTO.getId();//就诊id
        String code = inptVisitDTO.getCode();
        String crteId = inptVisitDTO.getCrteId();
        String userName = inptVisitDTO.getCrteName();
        String hospCode = inptVisitDTO.getHospCode();//医院编码
        String treatmentCode = inptVisitDTO.getTreatmentCode();
        String isMidWaySettle = inptVisitDTO.getIsMidWaySettle();  // 医保中途结算标识 1：中途结算 0：出院结算
        String medicalRegNo = inptVisitDTO.getMedicalRegNo();
        InsureIndividualBasicDTO insureBasicDTO = inptVisitDTO.getInsureIndividualBasicDTO();
        // 获取系统参数 是否开启大人婴儿合并结算
        SysParameterDTO mergeParameterDTO =null;
        Map<String, Object> isMergeParam = new HashMap<>();
        isMergeParam.put("hospCode", hospCode);
        isMergeParam.put("code", "BABY_INSURE_FEE");
        mergeParameterDTO = sysParameterService_consumer.getParameterByCode(isMergeParam).getData();
        //《========新生婴儿试算========》
        if(mergeParameterDTO !=null && "1".equals(mergeParameterDTO.getValue())){
            // 开启合并结算
        }else {
            // 大人婴儿费用单独结算
            InptBabyDTO inptBabyDTO=new InptBabyDTO();
            inptBabyDTO.setVisitId(id);
            inptBabyDTO.setHospCode(hospCode);
            //inptBabyDTO.setId(id);
            List<InptBabyDTO> inptBabyDTOS=inptBabyDAO.findByCondition(inptBabyDTO);
            InptBabyDTO babyDTO=null;
            if (inptBabyDTOS!=null&&inptBabyDTOS.size()>0) {
                String babyIds[] = new String[inptBabyDTOS.size()];
                for (int i=0;i<inptBabyDTOS.size();i++){
                    babyIds[i]=inptBabyDTOS.get(i).getId();
                }
                Map<String, Object> costParam = new HashMap<String, Object>();
                costParam.put("hospCode", hospCode);//医院编码
                costParam.put("visitId", id);//就诊id
                costParam.put("babyIds",babyIds);
                //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
                String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
                costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
                //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
                //获取婴儿费用信息
                List<InptCostDO> inptBabyCostDOList = inptCostDAO.queryInptCostList(costParam);
                if (inptBabyCostDOList!=null&&inptBabyCostDOList.size()>0){
                    InptCostDO inptCostDO=inptBabyCostDOList.get(0);
                    if (inptCostDO.getSettleCode().equals("0")){
                        return WrapperResponse.fail("请先结算婴儿费用，再结算大人费用", null);
                    }
                }
            }
        }
        //《========新生婴儿试算========》
        //《========归属结算试算========》
        if("0".equals(inptVisitDTO.getAttributionCode()) || StringUtils.isEmpty(inptVisitDTO.getAttributionCode())) {
          Map<String, Object> costParam = new HashMap<String, Object>();
          costParam.put("hospCode", hospCode);//医院编码
          costParam.put("visitId", id);//就诊id
          String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
          costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
          costParam.put("attributionFlag", "1");//就诊id
          List<InptCostDO> inptAttributionCostDOList = inptCostDAO.queryIsExitAttributionCostList(costParam);
          if (inptAttributionCostDOList!=null && inptAttributionCostDOList.size()>0){
            InptCostDO inptCostDO=inptAttributionCostDOList.get(0);
            if (inptCostDO.getSettleCode().equals("0")){
              return WrapperResponse.fail("请先结算归属费用，再结算正常费用", null);
            }
          }
        }
        //《========归属结算试算========》
        String key = new StringBuilder(hospCode).append(id).append(Constants.INPT_FEES_REDIS_KEY).toString();
        redisUtils.del(key);
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            return WrapperResponse.fail("当前患者正在试算，请稍后再试。", null);
        }
        try {
            redisUtils.set(key, id);//保存值
            //删除试算脏数据（inpt_settle、insure_individual_settle）
            Map<String, String> delParam = new HashMap<String, String>();
            delParam.put("hospCode", hospCode);//医院编码
            delParam.put("visitId", id);//就诊id
            delParam.put("isSettle", Constants.SF.F);//是否结算 = 否
            delParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            inptSettleDAO.deleteByVisitId(delParam);
            String settleId = SnowflakeUtils.getId();//结算id
            //获取个人信息
            InptVisitDTO inptVisitDTO1 = inptVisitDAO.getInptVisitById(inptVisitDTO);
            inptVisitDTO1.setUserCode(inptVisitDTO.getUserCode());
            if (inptVisitDTO1 == null) {
                throw new AppException("未找到该患者。");
            }
            //获取患者所有费用信息
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("hospCode", hospCode);//医院编码
            costParam.put("visitId", id);//就诊id
            //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            if(!StringUtils.isEmpty(inptVisitDTO.getAttributionCode())) {
              costParam.put("attributionCode", inptVisitDTO.getAttributionCode());// 结算类型
            }
            //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = new ArrayList<>();
            // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
            if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
                costParam.put("isMidWaySettle", isMidWaySettle);
                inptCostDOList = inptCostDAO.queryMidWaySettleInptCostList(costParam);
            } else {
                inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            }
            // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
            //if (inptCostDOList.isEmpty()){throw new AppException("该患者没有产生费用信息。");}
            Integer patientValueCode = Integer.parseInt(inptVisitDTO1.getPatientCode());
            if (inptCostDOList.isEmpty() && patientValueCode >1) {
                throw new AppException("该医保病人费用已经正常结算");
            }
            for (InptCostDO dto : inptCostDOList) {
                if (dto.getIsOk().equals("0")) {
                    throw new AppException("该患者有还未确费的费用，包括LIS或PACS检查，请先确费。");
                }
            }

            //校验医保费用是否传输
            List<Map<String, Object>> insureCostList = new ArrayList<>();
            if (patientValueCode > 0) {
                Map<String, String> insureCostParam = new HashMap<String, String>();
                insureCostParam.put("hospCode", hospCode);//医院编码
                insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
                insureCostParam.put("visitId", id);//就诊id
                insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
                insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
                insureCostParam.put("queryBaby","N");
                insureCostList = insureIndividualCostService.queryInsureCostByVisit(insureCostParam);
                if (insureCostList != null && insureCostList.size() > 0) {
                    throw new AppException("住院医保费用需进行费用传输。");
                }
            }
            //计算所有费用金额
            BigDecimal totalPrice = new BigDecimal(0);//总费用
            BigDecimal preferentialPrice = new BigDecimal(0);//单据优惠
            BigDecimal miPrice = new BigDecimal(0);//合同单位金额
            BigDecimal realityPrice = new BigDecimal(0);//单据应缴
            BigDecimal payPrice = new BigDecimal(0);//预交款
            BigDecimal subsequentPrice = new BigDecimal(0);//补收
            BigDecimal refundPrice = new BigDecimal(0);//退款
            for (InptCostDO inptCostDO : inptCostDOList) {
                if (inptCostDO.getTotalPrice() != null) {
                    totalPrice = BigDecimalUtils.add(totalPrice, inptCostDO.getTotalPrice());//总费用
                }
                if (inptCostDO.getPreferentialPrice() != null) {
                    preferentialPrice = BigDecimalUtils.add(preferentialPrice, inptCostDO.getPreferentialPrice());//单据优惠
                }
                if (inptCostDO.getRealityPrice() != null) {
                    realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());//单据应缴
                }
            }
            Map<String, Object> isInsureUnifiedMap = new HashMap<>();
            isInsureUnifiedMap.put("hospCode", hospCode);
            isInsureUnifiedMap.put("code", "UNIFIED_PAY");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
            String settleNo = getOrderNo(hospCode, Constants.ORDERRULE.ZY); // 获取结算单号
            //医保病人
            Map<String, String> inptInsureResult = new HashMap<String, String>();
            BigDecimal singlePrice = null;
            String patientCode = inptVisitDTO1.getPatientCode();
            Integer intPatientCode = Integer.valueOf(patientCode);
            if (intPatientCode > 0) {
                //获取医保就诊信息
                Map<String, Object> insureVisitParam = new HashMap<String, Object>();
                insureVisitParam.put("hospCode", hospCode);//医院编码
                InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
                insureIndividualVisitDTO.setHospCode(hospCode);//医院编码
                insureIndividualVisitDTO.setVisitId(id);//就诊id
                insureIndividualVisitDTO.setMedicalRegNo(medicalRegNo);
                insureVisitParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
                if(sysParameterDTO ==null || !"1".equals(sysParameterDTO.getValue())){
                    List<InsureIndividualVisitDTO> insureIndividualVisitDTOS = insureIndividualVisitService.findByCondition(insureVisitParam);
                    if (insureIndividualVisitDTOS == null || insureIndividualVisitDTOS.size() > 1 || insureIndividualVisitDTOS.size() == 0) {
                        throw new AppException("未获取到医保就诊信息。");
                    }
                    insureIndividualVisitDTO = insureIndividualVisitDTOS.get(0);
                }else{
                    insureIndividualVisitDTO = insureIndividualVisitService.selectInsureInfo(insureVisitParam).getData();
                }

                //读卡信息赋值
                if (ObjectUtil.isNotNull(insureBasicDTO)) {
                    insureIndividualVisitDTO.setHcardBasinfo(insureBasicDTO.getHcardBasinfo());
                    insureIndividualVisitDTO.setHcardChkinfo(insureBasicDTO.getHcardChkinfo());
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

                // 是否使用个人账户
                isInsureUnifiedMap.put("hospCode", hospCode);
                isInsureUnifiedMap.put("code", "INSURE_ACCOUNT");
                SysParameterDTO sysParameter = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
                if (sysParameter != null && Constants.IS_USER_ACCOUNT.S.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(Constants.IS_USER_ACCOUNT.S);
                } else if (Constants.IS_USER_ACCOUNT.F.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(Constants.IS_USER_ACCOUNT.F);
                } else if (Constants.IS_USER_ACCOUNT.T.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(inptVisitDTO.getIsUserInsureAccount());
                }
                inptVisitDTO1.setIsUseAccount(insureIndividualVisitDTO.getInsureAccoutFlag());
                /**
                 * 试算之前  先办理出院登记
                 * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
                 */
//                if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
                if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
                    Map<String, Object> unifiedMap = new HashMap<>();
                    unifiedMap.put("hospCode", hospCode);
                    unifiedMap.put("visitId", id);
                    unifiedMap.put("code", code);
                    unifiedMap.put("crteName",userName);
                    unifiedMap.put("crteId",crteId);
                    unifiedMap.put("userName", userName);
                    unifiedMap.put("inptVisit", inptVisitDTO1);
                    unifiedMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
                    unifiedMap.put("inptVisitDTO",inptVisitDTO1);
                    insureIndividualVisitDTO.setIsOut(Constants.SF.S);
                    unifiedMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
                    //试算不再需要出院才能做
                    /*List<InsureIndividualVisitDTO> byCondition = insureIndividualVisitService.findByCondition(unifiedMap);
                    if(ListUtils.isEmpty(byCondition)){
                        throw new AppException("请先做医保出院登记操作,再进行试算操作");
                    }*/
                    inptInsureResult = insureUnifiedPayInptService.UP_2303(unifiedMap).getData();




                } else {
                    insureIndividualVisitDTO.setCrteName(inptVisitDTO.getCrteName());
                    //TODO 封装医保参数，请求医保试算接口
                    inptVisitDTO1.setTreatmentCode(treatmentCode);
                    Map<String, Object> inptInsureParam = new HashMap<String, Object>();
                    inptInsureParam.put("hospCode", hospCode);//医院编码
                    inptInsureParam.put("insureRegCode", insureIndividualVisitDTO.getInsureRegCode());//医保编码
                    inptInsureParam.put("id", id);//就诊id
                    inptInsureParam.put("inptVisit", inptVisitDTO1);//就诊患者信息
                    inptInsureParam.put("insureIndividualVisit", insureIndividualVisitDTO);//医保就诊信息
                    inptInsureParam.put("isRemote", inptVisitDTO1.getPatientCode());//是否异地
                    inptInsureParam.put("inptVisitDTO", inptVisitDTO);//就诊患者信息
                    inptInsureParam.put("settleNo", settleNo);// 结算单号
                    inptInsureResult = inptService.verifyAndCalculateCost(inptInsureParam);
                }
                //明细审核事前分析
                Map<String, Object> map = new HashMap<>();
                map.put("hospCode",inptVisitDTO.getHospCode());
                map.put("code","DETAILAUDIT_SWITCH");
                WrapperResponse<SysParameterDTO> response = sysParameterService_consumer.getParameterByCode(map);
                if (WrapperResponse.SUCCESS != response.getCode()) {
                    throw new AppException(response.getMessage());
                }
                if (ObjectUtil.isNotEmpty(response.getData())&&"1".equals(response.getData().getValue())) {
                    //查询所有传输费用信息
                    Map<String, String> hashMap = new HashMap<>();
                    hashMap.put("hospCode", hospCode);//医院编码
                    hashMap.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
                    hashMap.put("visitId", id);//就诊id
                    hashMap.put("isMatch", Constants.SF.S);//是否匹配 = 是
                    hashMap.put("transmitCode", Constants.SF.S);//传输标志 = 未传输
                    hashMap.put("insureRegCode", insureIndividualVisitDTO.getInsureRegCode());// 医保机构编码
                    hashMap.put("isHalfSettle", isMidWaySettle);// 是否中途结算
                    List<Map<String, Object>> mapList = insureIndividualCostService.queryInsureCostByVisit(hashMap);
                    inptVisitDTO.setInsureCostList(mapList);
                    AnalysisDTO analysisDTO = this.initAnalysisDTO(inptVisitDTO,insureIndividualVisitDTO);
                    Map<String, Object> inMap = new HashMap<>();
                    inMap.put("hospCode",inptVisitDTO.getHospCode());
                    inMap.put("analysisDTO",analysisDTO);
                    inMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
                    AnaResJudgeDTO anaResJudgeDTO = insureDetailAuditService.upldMidAnalysisDTO(inMap);

                }

                //TODO 保存医保试算接口返回的报销信息

                String akb020 = inptInsureResult.get("akb020");//医疗机构编码
                String aaz217 = inptInsureResult.get("aaz217");//医保登记号
                BigDecimal aka151 = BigDecimalUtils.convert(inptInsureResult.get("aka151"));//起付线费用
                BigDecimal akb066 = BigDecimalUtils.convert(inptInsureResult.get("akb066"));//个人账户支付
                BigDecimal akb067 = BigDecimalUtils.convert(inptInsureResult.get("akb067"));//个人现金支付
                BigDecimal akc264 = BigDecimalUtils.convert(inptInsureResult.get("akc264"));//医疗总费用akc264 = bka831 + bka832+bka842

                /**
                 * 试算的时候如果现金支付 >= 医疗总费用 则不允许走医保
                 */
                if(BigDecimalUtils.equals(akb067,akc264)){
                    isInsureUnifiedMap.put("hospCode",hospCode);
                    isInsureUnifiedMap.put("code","HOSP_APPR_FLAG");
                    String cashPayValue = "";
                    SysParameterDTO parameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
                    if(parameterDTO !=null){
                        String value = parameterDTO.getValue();
                        if(StringUtils.isNotEmpty(value)){
                            Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
                            for (String k : stringObjectMap.keySet()) {
                                if ("cashPay".equals(k)) {
                                    cashPayValue = MapUtils.get(stringObjectMap,k);
                                    break;
                                }
                            }
                            if(!"1".equals(cashPayValue)){
                                throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                            }
                        }else{
                            throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                        }
                    }else {
                        throw new AppException("零费用报销,不能走医保报销流程,请走自费结算流程。");
                    }
                }
                BigDecimal ake026 = BigDecimalUtils.convert(inptInsureResult.get("ake026"));//企业补充医疗保险基金支付
                BigDecimal ake029 = BigDecimalUtils.convert(inptInsureResult.get("ake029"));//大额医疗费用补助基金支付
                BigDecimal ake035 = BigDecimalUtils.convert(inptInsureResult.get("ake035"));//公务员医疗补助基金支付
                BigDecimal ake039 = BigDecimalUtils.convert(inptInsureResult.get("ake039"));//医疗保险统筹基金支付
                BigDecimal bka825 = BigDecimalUtils.convert(inptInsureResult.get("bka825"));//全自费费用
                BigDecimal bka826 = BigDecimalUtils.convert(inptInsureResult.get("bka826"));//部分自费费用
                BigDecimal bka831 = BigDecimalUtils.convert(inptInsureResult.get("bka831"));//个人自付bka831 = akb067
                BigDecimal bka839 = BigDecimalUtils.convert(inptInsureResult.get("bka839"));//其他支付 - 结算总项
                BigDecimal acctInjPay = BigDecimalUtils.convert(inptInsureResult.get("acctInjPay"));//职工意外伤害基金
                BigDecimal retAcctInjPay = BigDecimalUtils.convert(inptInsureResult.get("retAcctInjPay"));//居民意外伤害基金
                BigDecimal governmentPay = BigDecimalUtils.convert(inptInsureResult.get("governmentPay"));//政府兜底
                BigDecimal thbPay = BigDecimalUtils.convert(inptInsureResult.get("thbPay"));//特惠保
                BigDecimal hospPrice = BigDecimalUtils.convert(inptInsureResult.get("hospPrice"));//医院垫付
                BigDecimal carePay = BigDecimalUtils.convert(inptInsureResult.get("carePay"));//优抚对象医疗补助基金
                BigDecimal lowInPay = BigDecimalUtils.convert(inptInsureResult.get("lowInPay"));//农村低收入人口医疗补充保险
                BigDecimal othPay = BigDecimalUtils.convert(inptInsureResult.get("othPay"));//其他基金支付 - 基金单项
                BigDecimal mafPay = BigDecimalUtils.convert(inptInsureResult.get("mafPay"));//民政救助金支付
                BigDecimal hospExemAmount = BigDecimalUtils.convert(inptInsureResult.get("hospExemAmount"));//医院减免
                BigDecimal retiredPay = BigDecimalUtils.convert(inptInsureResult.get("retiredPay"));// 离休基金
                BigDecimal fertilityPay = BigDecimalUtils.convert(inptInsureResult.get("fertilityPay"));// 生育基金
                BigDecimal preselfpayAmt = BigDecimalUtils.convert(inptInsureResult.get("preselfpayAmt"));// 先行自付金额
                BigDecimal inscpScpAmt = BigDecimalUtils.convert(inptInsureResult.get("inscpScpAmt"));// 符合政策范围金额
                BigDecimal poolPropSelfpay = BigDecimalUtils.convert(inptInsureResult.get("poolPropSelfpay"));// 基本医疗保险统筹基金支付比例
                BigDecimal acctMulaidPay = BigDecimalUtils.convert(inptInsureResult.get("acctMulaidPay"));// 个人账户共计支付金额
                BigDecimal soldierPay = BigDecimalUtils.convert(inptInsureResult.get("soldierPay"));// 一至六级残疾军人医疗补助基金
                BigDecimal retiredOutptPay = BigDecimalUtils.convert(inptInsureResult.get("soldierPay"));// 离休老工人门慢保障基金
                BigDecimal injuryPay = BigDecimalUtils.convert(inptInsureResult.get("injuryPay"));// 工伤保险基金
                BigDecimal hallPay = BigDecimalUtils.convert(inptInsureResult.get("hallPay"));// 厅级干部补助基金
                BigDecimal soldierToPay = BigDecimalUtils.convert(inptInsureResult.get("soldierToPay"));// 军转干部医疗补助基金
                BigDecimal welfarePay = BigDecimalUtils.convert(inptInsureResult.get("welfarePay"));// 公益补充保险基金
                BigDecimal COVIDPay = BigDecimalUtils.convert(inptInsureResult.get("COVIDPay"));// 新冠肺炎核酸检测财政补助
                BigDecimal familyPay = BigDecimalUtils.convert(inptInsureResult.get("familyPay"));// 居民家庭账户金
                BigDecimal behalfPay = BigDecimalUtils.convert(inptInsureResult.get("behalfPay"));// 代缴基金（破产改制）
                BigDecimal bka832 = BigDecimalUtils.convert(inptInsureResult.get("bka832"));//医保支付金额
                BigDecimal psnPartAmt = new BigDecimal(0.00); // 个人负担总金额
                if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
                     psnPartAmt = BigDecimalUtils.convert(MapUtils.get(inptInsureResult,"psnPartAmt"));
                }
                //医保支付金额 = 医疗总费用 - 医保个人自付
                miPrice = BigDecimalUtils.subtract(akc264, bka831);

                //获取医保个人信息 insure_individual_basic
                Map<String, Object> insureBasicParam = new HashMap<String, Object>();
                insureBasicParam.put("hospCode", hospCode);//医院编码
                InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
                insureIndividualBasicDTO.setHospCode(hospCode);//医院编码
                insureIndividualBasicDTO.setId(insureIndividualVisitDTO.getMibId());//id
                insureBasicParam.put("basicDTO", insureIndividualBasicDTO);
                insureIndividualBasicDTO = insureIndividualBasicService.getById(insureBasicParam).getData();
                if (insureIndividualBasicDTO == null) {
                    throw new AppException("未获取到医保个人信息。");
                }
                BigDecimal bacu18 = insureIndividualBasicDTO.getAkc252();//账户余额
                bacu18 = bacu18 == null ? new BigDecimal(0) : bacu18;

                //修改医保就诊信息;医保登记号
                if (StringUtils.isNotEmpty(aaz217)) {
                    InsureIndividualVisitDO insureIndividualVisitDO = new InsureIndividualVisitDO();
                    insureIndividualVisitDO.setVisitId(id);//就诊id
                    insureIndividualVisitDO.setMedicalRegNo(aaz217);//医保登记号
                    insureVisitParam.put("insureIndividualVisitDO", insureIndividualVisitDO);
                    insureIndividualVisitService.editInsureIndividualVisit(insureVisitParam);
                }

                //删除结算信息
                Map<String, String> delIndividualSettleParam = new HashMap<String, String>();
                delIndividualSettleParam.put("hospCode", hospCode);//医院编码
                delIndividualSettleParam.put("visitId", id);//就诊id
                delIndividualSettleParam.put("settleState", Constants.YBJSZT.SS);//结算标志 = 试算
                insureIndividualSettleService.delInsureIndividualSettleByVisitId(delIndividualSettleParam);
                //医保结算表 insure_individual_settle
                InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
                insureIndividualSettleDO.setId(SnowflakeUtils.getId());//主键
                insureIndividualSettleDO.setHospCode(hospCode);//医院编码
                insureIndividualSettleDO.setVisitId(id);//就诊id
                insureIndividualSettleDO.setSettleId(settleId);//结算id
                insureIndividualSettleDO.setIsHospital(Constants.SF.S);//是否住院（SF）
                insureIndividualSettleDO.setVisitNo(inptVisitDTO1.getInNo());//住院号/就诊号
                insureIndividualSettleDO.setDischargeDnCode(inptVisitDTO1.getOutDiseaseIcd10());//出院疾病诊断编码
                insureIndividualSettleDO.setInsureOrgCode(insureIndividualVisitDTO.getInsureOrgCode());//医保机构编码
                insureIndividualSettleDO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());//医保注册编码
                insureIndividualSettleDO.setMedicineOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());//医疗机构编码
                insureIndividualSettleDO.setDischargeDnName(inptVisitDTO1.getOutDiseaseName());//出院疾病诊断名称
                insureIndividualSettleDO.setDischargedDate(inptVisitDTO1.getInTime());//出院日期
                insureIndividualSettleDO.setDischargedCase(inptVisitDTO1.getOutSituationCode());//出院情况
                insureIndividualSettleDO.setSettleway(Constants.JSFS.PTJS);//结算方式,01 普通结算,02 包干结算
                insureIndividualSettleDO.setBeforeSettle(bacu18);//结算前账户余额
                insureIndividualSettleDO.setLastSettle(BigDecimalUtils.isZero(bacu18) ? bacu18 : BigDecimalUtils.greater(bka831, bacu18) ? new BigDecimal(0) : BigDecimalUtils.subtract(bacu18, akb066));//结算后账户余额
                insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
                insureIndividualSettleDO.setSettleState(Constants.YBJSZT.SS);//医保结算状态;0试算，1结算
                insureIndividualSettleDO.setAka130(inptVisitDTO1.getInsureBizCode());//业务类型
                insureIndividualSettleDO.setBka006(inptVisitDTO1.getInsureTreatCode());//待遇类型
                insureIndividualSettleDO.setIsAccount(BigDecimalUtils.isZero(akb066) ? Constants.SF.F : Constants.SF.S);//当前结算是否使用个人账户;0是，1否
                insureIndividualSettleDO.setCrteId(inptVisitDTO.getCrteId());//创建人ID
                insureIndividualSettleDO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
                insureIndividualSettleDO.setCrteTime(new Date());//创建时间
                /*
                insureIndividualSettleDO.setAssignPrice(null);//指定账户支付金额
                insureIndividualSettleDO.setTopPrice(null);//超封顶线金额
                insureIndividualSettleDO.setCostbatch(null);//费用批次
                insureIndividualSettleDO.setInjuryBorthSn(null);//业务申请号,门诊特病，工伤，生育
                insureIndividualSettleDO.setRemark(null);//备注*/

                // 处理金额
                insureIndividualSettleDO.setTotalPrice(akc264);// 本次医疗总费用
                insureIndividualSettleDO.setStartingPrice(aka151);//起付线金额
                insureIndividualSettleDO.setAllPortionPrice(bka825);//全自费金额
                insureIndividualSettleDO.setPortionPrice(bka826);//部分自付金额 - 超限价
                insureIndividualSettleDO.setInsurePrice(miPrice);//医保支付
                insureIndividualSettleDO.setPlanPrice(ake039);//统筹基金支付
                insureIndividualSettleDO.setPlanAccountPrice(poolPropSelfpay);//统筹段自负金额***
                insureIndividualSettleDO.setPreselfpayAmt(preselfpayAmt);// 先行自付金额
                insureIndividualSettleDO.setSeriousPrice(ake029);//大病互助支付
                insureIndividualSettleDO.setCivilPrice(ake035);//公务员补助支付
                insureIndividualSettleDO.setRetirePrice(retiredPay);// 离休人员医疗保证基金
                insureIndividualSettleDO.setMafPay(mafPay); // 医疗救助基金
                insureIndividualSettleDO.setHospExemAmount(hospExemAmount); // 医院减免
                insureIndividualSettleDO.setPersonalPrice(akb066);//个人账户支付
                insureIndividualSettleDO.setPersonPrice(akb067);//个人支付
                insureIndividualSettleDO.setRestsPrice(bka839);//其他支付
                insureIndividualSettleDO.setFertilityPay(fertilityPay);// 生育基金
                insureIndividualSettleDO.setComPay(ake026);// 企业补充医疗保险基金
                insureIndividualSettleDO.setHospPrice(hospPrice);//医院垫付
                insureIndividualSettleDO.setAcctInjPay(acctInjPay);
                insureIndividualSettleDO.setRetAcctInjPay(retAcctInjPay);
                insureIndividualSettleDO.setGovernmentPay(governmentPay);
                insureIndividualSettleDO.setThbPay(thbPay);
                insureIndividualSettleDO.setCarePay(carePay);
                insureIndividualSettleDO.setLowInPay(lowInPay);
                insureIndividualSettleDO.setOthPay(othPay);
                insureIndividualSettleDO.setInscpScpAmt(inscpScpAmt);
                insureIndividualSettleDO.setPoolPropSelfpay(poolPropSelfpay);
                insureIndividualSettleDO.setAcctMulaidPay(acctMulaidPay);
                insureIndividualSettleDO.setSoldierPay(soldierPay);
                insureIndividualSettleDO.setRetiredOutptPay(retiredOutptPay);
                insureIndividualSettleDO.setInjuryPay(injuryPay);
                insureIndividualSettleDO.setHallPay(hallPay);
                insureIndividualSettleDO.setSoldierToPay(soldierToPay);
                insureIndividualSettleDO.setWelfarePay(welfarePay);
                insureIndividualSettleDO.setCOVIDPay(COVIDPay);
                insureIndividualSettleDO.setFamilyPay(familyPay);
                insureIndividualSettleDO.setBehalfPay(behalfPay);
                insureIndividualSettleDO.setPsnPartAmt(psnPartAmt); // 个人负担总金额

                Map<String, Object> insureSettleParam = new HashMap<String, Object>();
                insureSettleParam.put("hospCode", hospCode);//医院编码
                insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
                insureIndividualSettleService.insertSelective(insureSettleParam);
            }
            //查询预交款信息
            InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
            inptAdvancePayDTO.setHospCode(hospCode);//医院编码
            inptAdvancePayDTO.setVisitId(id);//就诊id
            inptAdvancePayDTO.setIsSettle(Constants.SF.F);//是否结算 = 否
            inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            List<InptAdvancePayDTO> inptAdvancePayDTOList = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
            String[] payId = null;
            if (inptAdvancePayDTOList != null && !inptAdvancePayDTOList.isEmpty()) {
                int i = 0;
                payId = new String[inptAdvancePayDTOList.size()];
                for (InptAdvancePayDTO inptAdvancePayDTO1 : inptAdvancePayDTOList) {
                    payPrice = BigDecimalUtils.add(payPrice, inptAdvancePayDTO1.getPrice());
                    payId[i++] = inptAdvancePayDTO1.getId();
                }
            }
            //计算补收金额、退款金额
            //补收金额: 优惠总金额 - 合同支付报销金额 - 预交金 = 还需补收的金额
            //如果是医保病人单病种 直接就是 需支付金额 - 预交金；如果不是单病种 需支付金额 - 医保支付金额 - 预交金
            subsequentPrice = singlePrice != null ? BigDecimalUtils.subtract(singlePrice, payPrice) : BigDecimalUtils.subtract(BigDecimalUtils.subtract(realityPrice, miPrice), payPrice);
            //舍入金额
            BigDecimal truncPrice = new BigDecimal(0);
            //退款
            if (BigDecimalUtils.lessZero(subsequentPrice)) {
                refundPrice = subsequentPrice;
                subsequentPrice = new BigDecimal(0);
            } else {
                //根据医院配置舍入金额
                SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
                truncPrice = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舍入费用
            }
            Date now = new Date(); //当前时间
            Date minDate = null;
            Date maxDate = null;
            if (!inptCostDOList.isEmpty()) {
                minDate = inptCostDOList.get(0).getCostTime(); //费用开始时间
                maxDate = inptCostDOList.get(inptCostDOList.size() - 1).getCostTime(); //费用结束时间
            }
            //封装结算信息
            InptSettleDO inptSettleDO = new InptSettleDO();
            inptSettleDO.setId(settleId);//结算id
            inptSettleDO.setHospCode(hospCode);//医院编码
            inptSettleDO.setVisitId(id);//就诊id
            inptSettleDO.setBabyId(null);//婴儿id
            inptSettleDO.setSettleNo(settleNo);//结算单号
            inptSettleDO.setTypeCode(Constants.JSLX.CYJS);//结算类型 = 出院结算
            if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
              inptSettleDO.setTypeCode(Constants.JSLX.ZTJS);//结算类型 = 出院结算
            }
            inptSettleDO.setPatientCode(inptVisitDTO1.getPatientCode());//病人类型
            inptSettleDO.setStartTime(minDate);//开始时间
            inptSettleDO.setEndTime(maxDate);//结束时间
            inptSettleDO.setTotalPrice(totalPrice);//总费用
            inptSettleDO.setRealityPrice(realityPrice);//优惠后总金额
            inptSettleDO.setTruncPrice(truncPrice);//舍入金额
            inptSettleDO.setActualPrice(new BigDecimal(0));//实收金额
            inptSettleDO.setSelfPrice(subsequentPrice);//个人支付
            inptSettleDO.setMiPrice(miPrice);//统筹支付
            inptSettleDO.setApTotalPrice(payPrice);//预交金合计
            inptSettleDO.setApOffsetPrice(payPrice);//预交金冲抵
            inptSettleDO.setSettleTakePrice(subsequentPrice);//结算补收
            inptSettleDO.setSettleBackPrice(refundPrice.negate());//结算退款
            inptSettleDO.setSettleBackCode(Constants.ZFFS.XJ);//退款方式默认现金
            inptSettleDO.setIsSettle(Constants.SF.F);//是否结算 = 否
            if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
                inptSettleDO.setSettleTime(maxDate);// 中途结算的结算时间取最大时间
            } else {
                inptSettleDO.setSettleTime(now);//结算时间
            }
            inptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            inptSettleDO.setRedId(null);//冲红id
            inptSettleDO.setIsPrint(Constants.SF.F);//是否打印 = 否
            inptSettleDO.setHospDfPrice(new BigDecimal(0));//医院垫付金额
            inptSettleDO.setHospJmPrice(new BigDecimal(0));//医院减免金额
            inptSettleDO.setOutSettleCode(null);//TODO 出院结算方式 (待确认)
            inptSettleDO.setDailySettleId(null);//日结缴款id
            inptSettleDO.setSourcePayCode(null);//TODO 支付来源方式（待确认）
            inptSettleDO.setOrderNo(null);//支付订单号
            inptSettleDO.setCrteId(inptVisitDTO.getCrteId());//创建人id
            inptSettleDO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
            inptSettleDO.setCrteTime(now);//创建时间
            inptSettleDAO.insert(inptSettleDO);

            //封装返回前端需要的参数
            JSONObject result = new JSONObject();
            result.put("totalPrice", totalPrice);//总费用
            result.put("preferentialPrice", preferentialPrice);//单据优惠
            result.put("realityPrice", realityPrice);//单据应缴
            result.put("miPrice", miPrice);//合同单位金额
            result.put("payPrice", payPrice);//预交款
            result.put("subsequentPrice", subsequentPrice);//补收
            result.put("refundPrice", refundPrice);//退款
            result.put("truncPrice", truncPrice);//舍入金额
            result.put("settleId", settleId);//结算id
            result.put("payinfo", inptInsureResult);//统筹支付信息
            result.put("settleNo", settleNo); // 结算单号
            return WrapperResponse.success("试算成功。", result);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            redisUtils.del(key);
        }
    }


    /**
     * @param param 请求参数
     * @Menthod saveSettle
     * @Desrciption 住院结算
     * @Author Ou·Mr
     * @Date 2020/9/25 11:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public synchronized WrapperResponse saveSettle(Map param) {
        String treatmentCode = MapUtils.get(param, "treatmentCode");
        String id = (String) param.get("id");//就诊id
        String code = param.get("userCode").toString();
        String userName = param.get("userName").toString();
        String medicalRegNo = MapUtils.get(param,"medicalRegNo");
        String hospCode = (String) param.get("hospCode");//医院编码
        String attributionCode = MapUtils.get(param,"attributionCode");
        String isMidWaySettle = MapUtils.get(param, "isMidWaySettle"); // 是否中途结算，1：是 0：否  中途结算不改变出院状态
        String key = new StringBuilder(hospCode).append(id).append(Constants.INPT_FEES_REDIS_KEY).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key))) return WrapperResponse.fail("当前患者正在结算，请稍后再试。", null);
        try {

            //获取当前患者信息参数
            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setHospCode(hospCode);//医院编码
            inptVisitDTO.setId(id);//就诊id
            inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
            inptVisitDTO.setIsUserInsureAccount(MapUtils.get(param,"isUserInsureAccount"));
            if (inptVisitDTO == null) {
                return WrapperResponse.fail("未找到该患者信息，请刷新。", null);
            }
            if (!Constants.BRZT.YCY.equals(inptVisitDTO.getStatusCode())) {
                throw new AppException("病人状态已变更，非预出院状态，请刷新后重新结算！");
            }
            // 出院结算校验病人状态是不是预出院  lly 2021/11/01
            if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {

            }else {
                if (inptVisitDTO != null && inptVisitDTO.getStatusCode() != null &&!inptVisitDTO.getStatusCode().equals(Constants.YDLX.YCY)){
                    return WrapperResponse.fail("该患者不是预出院状态，请刷新。", null);
                }
            }

            redisUtils.set(key, id);
            Boolean isInvoice = (Boolean) param.get("isInvoice");//是否使用发票
            String userId = (String) param.get("userId");//当前登录用户id
            OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
            //校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
            if (isInvoice) {
                outinInvoiceDTO.setHospCode(hospCode);//医院编码
                outinInvoiceDTO.setUseId(userId);//发票使用人id
                List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
                Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.ZY);
                outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；4、住院
                outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                //校验是否有在用状态的发票段，有就返回在用的发票信outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService_consumer.updateForOutinInvoiceQuery(map).getData();
                if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
                    //没有发票信息
                    return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
                }
                outinInvoiceDTO = outinInvoiceDTOS.get(0);
            }
            String settleId = (String) param.get("settleId");//结算id
            //根据结算id查询本次结算信息
            InptSettleDO inptSettleDO = inptSettleDAO.selectByPrimaryKey(settleId);
            if (inptSettleDO == null) return WrapperResponse.fail("未找到结算记录信息，请重新试算。", null);
            //校验费用信息
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("hospCode", hospCode);//医院编码
            costParam.put("visitId", id);//就诊id
            //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("attributionCode", attributionCode);//结算类型标志
            //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = new ArrayList<>();
            // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
            if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
                costParam.put("isMidWaySettle", isMidWaySettle);
                inptCostDOList = inptCostDAO.queryMidWaySettleInptCostList(costParam);
            } else {
                inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            }
            // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
            // if (inptCostDOList == null || inptCostDOList.isEmpty()){return WrapperResponse.fail("未找到结算费用信息，请刷新。",null);}




            Integer patientValueCode = Integer.parseInt(inptVisitDTO.getPatientCode());
            if (inptCostDOList.isEmpty() && patientValueCode > 0) {
                throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
            }
            //校验预交金
            InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
            inptAdvancePayDTO.setHospCode(hospCode);//医院编码
            inptAdvancePayDTO.setVisitId(id);//就诊id
            inptAdvancePayDTO.setIsSettle(Constants.SF.F);//是否结算 = 否
            inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            List<InptAdvancePayDTO> inptAdvancePayDTOList = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
            BigDecimal payPrice = new BigDecimal(0);
            String[] payIds = null;
            int payIndex = 0;
            if (inptAdvancePayDTOList != null && !inptAdvancePayDTOList.isEmpty()) {
                payIds = new String[inptAdvancePayDTOList.size()];
                for (InptAdvancePayDTO inptAdvancePayDTO1 : inptAdvancePayDTOList) {
                    if (inptAdvancePayDTO1.getPrice() != null) {
                        payPrice = BigDecimalUtils.add(payPrice, inptAdvancePayDTO1.getPrice());
                        payIds[payIndex++] = inptAdvancePayDTO1.getId();
                    }
                }
            }
            if (!BigDecimalUtils.equals(payPrice, inptSettleDO.getApOffsetPrice())) {
                return WrapperResponse.fail("预交金发生改变，请重新试算。", null);
            }
            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            String[] costIds = new String[inptCostDOList.size()];
            List<InptCostSettleDO> inptCostSettleDOList = new ArrayList<InptCostSettleDO>();
            int costIndex = 0;
            for (InptCostDO inptCostDO : inptCostDOList) {
                if (inptCostDO.getRealityPrice() != null) {
                    realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());
                    costIds[costIndex++] = inptCostDO.getId();
                    InptCostSettleDO inptCostSettleDO = new InptCostSettleDO();
                    inptCostSettleDO.setId(SnowflakeUtils.getId());//id
                    inptCostSettleDO.setHospCode(hospCode);//医院编码
                    inptCostSettleDO.setVisitId(id);//就诊id
                    inptCostSettleDO.setBabyId(inptCostDO.getBabyId());//babyID
                    inptCostSettleDO.setCostId(inptCostDO.getId());//费用id
                    inptCostSettleDO.setSettleId(settleId);//结算id
                    inptCostSettleDO.setRealityPrice(inptCostDO.getRealityPrice()); // 优惠后金额
                    inptCostSettleDOList.add(inptCostSettleDO);
                }
            }
            //总费用 - 医保报销 - 预交金 = 需支付/需退还
            /*BigDecimal selfPrice = BigDecimalUtils.subtract(BigDecimalUtils.subtract(realityPrice,inptSettleDO.getMiPrice()),payPrice);
            //校验 需支付金额 是否和 试算的个人支付金额 一致。
            if (BigDecimalUtils.greaterZero(inptSettleDO.getSettleBackPrice())){
                //有退款
                if (!BigDecimalUtils.equals(selfPrice.negate(),inptSettleDO.getSettleBackPrice())){throw new AppException("费用发生改变，请重新试算。");}
            }else{
                //无退款
                if (!BigDecimalUtils.equals(selfPrice,inptSettleDO.getSelfPrice())){throw new AppException("费用发生改变，请重新试算。");}
            }*/

            //校验费用是否一致
            if (!BigDecimalUtils.equals(realityPrice, inptSettleDO.getRealityPrice())) {
                throw new AppException("费用发生改变，请重新试算。");
            }
            BigDecimal selfPrice = inptSettleDO.getSelfPrice();//个人支付金额

            //计算支付金额
            List<InptPayDO> inptPayDOList = (List<InptPayDO>) param.get("inptPayDOList");//支付方式
            BigDecimal actualPrice = new BigDecimal(0);//实收金额
            // 第三方支付金额
            BigDecimal thirdPartyPrice = new BigDecimal(0);
            // 挂账金额
            BigDecimal creditPrice = new BigDecimal(0);
            List<InptPayDO> inptPayParam = new ArrayList<InptPayDO>();
            for (InptPayDO inptPayDO : inptPayDOList) {
                if ("8".equals(inptPayDO.getPayCode())) {
                    creditPrice = inptPayDO.getPrice();
                }
                //TODO 后续考虑支付手续费
                if (StringUtils.isNotEmpty(inptPayDO.getPayCode()) && inptPayDO.getPrice() != null) {
                    // 支付方式中第三方支付费用总和
                    if (!inptPayDO.getPayCode().equals(Constants.ZFFS.XJ)) {
                        thirdPartyPrice = BigDecimalUtils.add(thirdPartyPrice, inptPayDO.getPrice());
                    }
                    actualPrice = BigDecimalUtils.add(actualPrice, inptPayDO.getPrice());
                    inptPayDO.setId(SnowflakeUtils.getId());//id
                    inptPayDO.setHospCode(hospCode);//医院编码
                    inptPayDO.setSettleId(settleId);//结算id
                    inptPayDO.setVisitId(id);//就诊id
                    inptPayDO.setServicePrice(inptPayDO.getServicePrice() == null ? new BigDecimal(0) : inptPayDO.getServicePrice());//手术费
                    inptPayParam.add(inptPayDO);
                }
            }
            //判断支付金额是否小于需支付金额
            int greater = BigDecimalUtils.compareTo(selfPrice, actualPrice);
            if (greater > 0) {
                throw new AppException("实收金额不能小于需支付金额。");
            }
            ;
            // ====================================================================
            // 官红强  2021年1月19日15:16:23 1、如果正常收钱有找零，需要将现金支付金额减去找零金额后再保存，（前提要校验收收费金额还是退费金额）
            // 应收金额大于0，退款金额小于0，说明是收款
            if (BigDecimalUtils.greaterZero(selfPrice) && BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice())) {
                // 应收金额小于实际付款金额，则需要将现金支付金额减去多余的金额
                if (greater < 0) {
                    // 如果第三方支付金额总和大于应收金额，则不允许保存
                    if (BigDecimalUtils.compareTo(thirdPartyPrice, selfPrice) > 0) {
                        throw new AppException("第三方支付金额总和不能大于应收金额。");
                    }
                    BigDecimal dif = BigDecimalUtils.subtract(selfPrice, actualPrice);
                    for (int i = 0; i < inptPayParam.size(); i++) {
                        // 支付方式为现金的支付方式
                        if (inptPayParam.get(i).getPayCode().equals(Constants.ZFFS.XJ)) {
                            // 现金支付金额减去多收的金额
                            inptPayParam.get(i).setPrice(BigDecimalUtils.add(inptPayParam.get(i).getPrice(), dif));
                        }
                    }
                }
            }
            // 官红强  2021年1月19日15:16:23 2、如果预交金额大于实际使用金额，说明有退款，则需要保存退款的负金额记录
            if (BigDecimalUtils.greaterZero(inptSettleDO.getSettleBackPrice())) {
                // 退款支持多路径退，需要校验输入的退款总和不能大于应退金额(即输入金额总和=应退金额)
                if (!BigDecimalUtils.equals(actualPrice, inptSettleDO.getSettleBackPrice())) {
                    throw new AppException("各种支付方式输入的金额总和必须等于应退金额");
                } else {
                    for (int i = 0; i < inptPayParam.size(); i++) {
                        // 将输入的退款金额取反
                        inptPayParam.get(i).setPrice(BigDecimalUtils.negate(inptPayParam.get(i).getPrice()));
                    }
                }
                inptPayDAO.batchInsert(inptPayParam);
            }
            // ====================================================================
//            if (greater < 0){
//                BigDecimal dif = BigDecimalUtils.subtract(selfPrice,actualPrice);
//                InptPayDO inptPayDO = new InptPayDO();
//                inptPayDO.setId(SnowflakeUtils.getId());//id
//                inptPayDO.setHospCode(hospCode);//医院编码
//                inptPayDO.setSettleId(settleId);//结算id
//                inptPayDO.setVisitId(id);//就诊id
//                inptPayDO.setPayCode(Constants.ZFFS.XJ);//支付方式 = 现金
//                inptPayDO.setPrice(dif);//支付金额
//                inptPayDO.setOrderNo(null);//单据号
//                inptPayDO.setServicePrice(new BigDecimal(0));//手续费
//                inptPayDAO.insert(inptPayDO);
//            }
            // =======================官红强 注释=============================================
            //保存结算费用关联信息
            if (!inptCostSettleDOList.isEmpty()) {
                inptCostSettleDAO.batchInsert(inptCostSettleDOList);
            }
            //修改使用预交金状态
            if (payIds != null) {
                Map<String, Object> payParam = new HashMap<String, Object>();
                payParam.put("isSettle", Constants.SF.S);//是否结算 = 是
                payParam.put("settleId", settleId);//结算id
                payParam.put("hospCode", hospCode);//医院编码
                payParam.put("ids", payIds);
                inptAdvancePayDAO.editAdvancePayByIds(payParam);
            }

            //修改费用状态
            if (!inptCostDOList.isEmpty()) {
                Map<String, Object> inptCostParam = new HashMap<String, Object>();
                inptCostParam.put("settleId", settleId);//结算id
                inptCostParam.put("settleCode", Constants.JSZT.YIJS);//结算状态 = 已结算
                inptCostParam.put("hospCode", hospCode);//医院编码
                inptCostParam.put("ids", costIds);//费用id
                inptCostDAO.editInptCostByIds(inptCostParam);
            }

            //修改结算表状态
            InptSettleDO inptSettleDO1 = new InptSettleDO();
            inptSettleDO1.setId(settleId);//结算id
            inptSettleDO1.setIsSettle(Constants.SF.S);//是否结算 = 是
            inptSettleDO1.setSourcePayCode("0"); // 0：his 1：微信 2：支付宝 3：自助机
            inptSettleDO1.setCreditPrice(creditPrice);
            inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO1);

            // 官红强修改， 2021年1月19日14:50:34 如果个人自付为0，意味着有预交金额退款，此时不再写一遍支付信息,如果自付与预交刚好相等，需要写一次记录（即自付为0，退款为0）
            if (!inptPayParam.isEmpty() && (!BigDecimalUtils.isZero(selfPrice) || BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice()))) {
                //保存inpt_pay 住院结算支付方式表
                inptPayDAO.batchInsert(inptPayParam);
            }
            //根据就诊id修改inpt_visit（患者就诊表）当前状态代码 = 出院状态
            InptVisitDTO inptVisitDTO1 = new InptVisitDTO();
            inptVisitDTO1.setId(id);//id
            inptVisitDTO1.setHospCode(hospCode);//医院编码
            if (isMidWaySettle != null && "0".equals(isMidWaySettle) && (StringUtils.isEmpty(attributionCode) || "0".equals(attributionCode))) {
                inptVisitDTO1.setStatusCode(Constants.BRZT.CY);//当前状态 = 出院状态
                inptVisitDTO1.setTreatmentCode(inptVisitDTO.getTreatmentCode());
                inptVisitDAO.updateInptVisit(inptVisitDTO1);
            }
            //医保结算
            String patientCode = inptVisitDTO.getPatientCode();
            Integer intPatientCode = Integer.valueOf(patientCode);
            if (intPatientCode > 0) {
                //查询医保机构信息
                Map<String, Object> configurationParam = new HashMap<String, Object>();
                configurationParam.put("hospCode", hospCode);//医院编码
                InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
                insureConfigurationDTO.setHospCode(hospCode);
                insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureOrgCode());
                configurationParam.put("insureConfigurationDTO", insureConfigurationDTO);
                List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationService.queryAll(configurationParam).getData();
                if (insureConfigurationDTOS == null || insureConfigurationDTOS.isEmpty()) {
                    throw new AppException("未找到医保配置信息。");
                }
                insureConfigurationDTO = insureConfigurationDTOS.get(0);

                // 是否走统一支付平台平台参数 luoyong 2021.08.07
                String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

                Map<String, Object> isInsureUnifiedMap = new HashMap<>();
                isInsureUnifiedMap.put("hospCode", hospCode);
//                isInsureUnifiedMap.put("code", "UNIFIED_PAY");
//                SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
                //获取医保就诊信息
                Map<String, Object> insureVisitParam = new HashMap<String, Object>();
                insureVisitParam.put("hospCode", hospCode);//医院编码
                InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
                insureIndividualVisitDTO.setHospCode(hospCode);//医院编码
                insureIndividualVisitDTO.setVisitId(id);//就诊id
                insureIndividualVisitDTO.setMedicalRegNo(medicalRegNo);
                insureVisitParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
//                if(sysParameterDTO ==null || !"1".equals(sysParameterDTO.getValue())){
                if (StringUtils.isEmpty(isUnifiedPay) || !"1".equals(isUnifiedPay)) {
                    List<InsureIndividualVisitDTO> insureIndividualVisitDTOS = insureIndividualVisitService.findByCondition(insureVisitParam);
                    if (insureIndividualVisitDTOS == null || insureIndividualVisitDTOS.size() > 1 || insureIndividualVisitDTOS.size() == 0) {
                        throw new AppException("未获取到医保就诊信息。");
                    }
                    insureIndividualVisitDTO = insureIndividualVisitDTOS.get(0);
                }else{
                    insureIndividualVisitDTO = insureIndividualVisitService.selectInsureInfo(insureVisitParam).getData();
                }

                //获取医保结算信息
                Map<String, Object> individualSettleParam = new HashMap<String, Object>();
                individualSettleParam.put("hospCode", hospCode);
                InsureIndividualSettleDTO insureIndividualSettleDTO = new InsureIndividualSettleDTO();
                insureIndividualSettleDTO.setHospCode(hospCode);//医院编码
                insureIndividualSettleDTO.setVisitId(id);//就诊id
                insureIndividualSettleDTO.setSettleId(settleId);//结算id
                individualSettleParam.put("insureIndividualSettleDTO", insureIndividualSettleDTO);
                insureIndividualSettleDTO = insureIndividualSettleService.findByCondition(individualSettleParam);
                if (insureIndividualSettleDTO == null) {
                    throw new AppException("未获取到医保结算信息，请联系管理员。");
                }

                //修改结算信息（医保结算状态 = 结算）
                InptVisitDTO inptVisitParam = new InptVisitDTO();
                inptVisitParam.setHospCode(hospCode);
                inptVisitParam.setId(id);
                inptVisitParam = inptVisitDAO.getInptVisitById(inptVisitParam);
                if (inptVisitParam == null) {
                    throw new AppException("未查询到住院患者信息。");
                }
                InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
                insureIndividualSettleDO.setId(insureIndividualSettleDTO.getId());//id
                insureIndividualSettleDO.setSettleState(Constants.YBJSZT.JS);//结算状态 = 结算
                individualSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
                insureIndividualSettleService.updateByPrimaryKeySelective(individualSettleParam);

                //修改费用传输表（赋值结算id） insure_individual_cost
                Map<String, Object> insureCostParam = new HashMap<String, Object>();
                insureCostParam.put("hospCode", hospCode);//医院编码
                InsureIndividualCostDTO insureIndividualCostDTO = new InsureIndividualCostDTO();
                insureIndividualCostDTO.setCostList(inptCostDOList);//费用集合
                insureIndividualCostDTO.setSettleId(settleId);//结算id
                insureCostParam.put("insureIndividualCostDTO", insureIndividualCostDTO);
                insureIndividualCostService.editInsureCostByCostIDS(insureCostParam);

                //保存合同单位明细 inpt_insure_pay
                InptInsurePayDO inptInsurePayDO = new InptInsurePayDO();
                inptInsurePayDO.setId(SnowflakeUtils.getId());//id
                inptInsurePayDO.setHospCode(hospCode);//医院编码
                inptInsurePayDO.setSettleId(settleId);//结算id
                inptInsurePayDO.setVisitId(id);//就诊id
                inptInsurePayDO.setTypeCode(null);//合同单位明细代码（HTDW）
                inptInsurePayDO.setOrgNo(insureConfigurationDTO.getOrgCode());//医保机构编码
                inptInsurePayDO.setOrgName(insureConfigurationDTO.getName());//医保机构名称
                inptInsurePayDO.setTotalPrice(insureIndividualSettleDTO.getInsurePrice());//医保报销总金额
                inptInsurePayDAO.insert(inptInsurePayDO);
                Map<String, String> insureInptResult = null;
                // 是否使用个人账户
                isInsureUnifiedMap.put("hospCode", hospCode);
                isInsureUnifiedMap.put("code", "INSURE_ACCOUNT");
                isInsureUnifiedMap.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
                SysParameterDTO sysParameter = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
                if (sysParameter != null && Constants.IS_USER_ACCOUNT.S.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(Constants.IS_USER_ACCOUNT.S);
                } else if (Constants.IS_USER_ACCOUNT.F.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(Constants.IS_USER_ACCOUNT.F);
                } else if (Constants.IS_USER_ACCOUNT.T.equals(sysParameter.getValue())) {
                    insureIndividualVisitDTO.setInsureAccoutFlag(inptVisitDTO.getIsUserInsureAccount());
                }
                inptVisitDTO.setIsUseAccount(insureIndividualVisitDTO.getInsureAccoutFlag());
                //住院结算增加读卡原始信息
                insureIndividualVisitDTO.setHcardBasinfo(MapUtil.getStr(param,"hcardBasinfo"));
                insureIndividualVisitDTO.setHcardChkinfo(MapUtil.getStr(param,"hcardChkinfo"));


                /**
                 * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
                 */
//                if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
                if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
                    handInsureUnifiedInptSettle(inptVisitDTO, insureIndividualVisitDTO, param, isInsureUnifiedMap, inptCostDOList, settleId);
                }
                else {
                    //TODO 封装医保结算参数；调用医保结算接口
                    Map<String, Object> insureInptParam = new HashMap<String, Object>();
                    //必传值：hospCode:医院编码、visitId:就诊id、insureRegCode:医保编码
                    insureInptParam.put("hospCode", hospCode);//医院编码
                    insureInptParam.put("visitId", id);//就诊id
                    insureInptParam.put("insureRegCode", insureConfigurationDTO.getCode());//医保编码
                    inptVisitParam.setCrteName(String.valueOf(param.get("userName")));
                    inptVisitParam.setUserCode(String.valueOf(param.get("userCode")));
                    inptVisitParam.setTreatmentCode(treatmentCode);
                    insureInptParam.put("inptVisit", inptVisitParam);//住院个人信息
                    insureInptParam.put("insureIndividualVisit", insureIndividualVisitDTO);//医保就诊信息
                    insureInptParam.put("isRemote", inptVisitDTO.getPatientCode());//是否异地
                    insureInptParam.put("settleNo", inptSettleDO.getSettleNo());
                    insureInptResult = inptService.inptSettlement(insureInptParam);
                }

            }
            //判断是否需要发票
            if (isInvoice) { //true:打印发票生成发票信息
                Map<String, Object> map = new HashMap<String, Object>();
                outinInvoiceDTO.setSettleId(settleId);//结算id
                outinInvoiceDTO.setDqCurrNo(outinInvoiceDTO.getCurrNo());
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService_consumer.updateInvoiceStatus(map).getData();
                //保存inpt_settle_invoice住院结算发票情况表
                InptSettleInvoiceDO inptSettleInvoiceDO = new InptSettleInvoiceDO();
                String inptSettleInvoiceId = SnowflakeUtils.getId();
                inptSettleInvoiceDO.setId(inptSettleInvoiceId);//id
                inptSettleInvoiceDO.setHospCode(hospCode);//医院编码
                inptSettleInvoiceDO.setSettleId(settleId);//结算id
                inptSettleInvoiceDO.setVisitId(id);//就诊id
                inptSettleInvoiceDO.setInvoiceId(outinInvoiceDTO.getId());//发票id
                inptSettleInvoiceDO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
                inptSettleInvoiceDO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
                inptSettleInvoiceDO.setTotalPrice(realityPrice);//发票总金额
                inptSettleInvoiceDO.setPrintId(null);//发票打印人id
                inptSettleInvoiceDO.setPrintName(null);//发票打印人姓名
                inptSettleInvoiceDO.setPrintTime(null);//发票打印时间
                inptSettleInvoiceDO.setPrintNum(0);//打印次数
                inptSettleInvoiceDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）
                inptSettleInvoiceDO.setRedId(null);//冲红id
                inptSettleInvoiceDAO.insertSelective(inptSettleInvoiceDO);//保存住院结算发票情况表

                //保存inpt_settle_invoice_content住院结算发票内容表
                InptSettleInvoiceContentDO inptSettleInvoiceContentDO = new InptSettleInvoiceContentDO();
                inptSettleInvoiceContentDO.setId(SnowflakeUtils.getId());//id
                inptSettleInvoiceContentDO.setHospCode(hospCode);//医院编码
                inptSettleInvoiceContentDO.setSettleInvoiceId(inptSettleInvoiceId);//结算发票ID（inpt_settle_invoice）
                inptSettleInvoiceContentDO.setInCode(null);//住院发票代码
                inptSettleInvoiceContentDO.setInName(null);//住院发票名称
                inptSettleInvoiceContentDO.setRealityPrice(realityPrice);//优惠后总金额
                inptSettleInvoiceContentDAO.insertSelective(inptSettleInvoiceContentDO);//保存住院结算发票内容表
            }
            return WrapperResponse.success("支付成功。", null);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            redisUtils.del(key);
        }
    }

    /**
     * @Method insureUnifiedPayInpt
     * @Desrciption 住院预结算
     * @Param
     *
     * @Author 曹亮
     * @Date   2021/7/14 11:17
     * @Return
     **/
    public synchronized WrapperResponse insureUnifiedPayInpt (Map param){
        Map<String, String> inptInsureResult = new HashMap<String, String>();
        Map<String, Object> unifiedMap = new HashMap<>();
        unifiedMap.put("hospCode", param.get("hospCode"));
        unifiedMap.put("inptVisit", param.get("inptVisitDTO"));
        unifiedMap.put("visitId", param.get("visitId"));
        inptInsureResult = insureUnifiedPayInptService.UP_2303(unifiedMap).getData();
        return WrapperResponse.success("试算成功。", inptInsureResult);
    }


    /**
     * @Method handInsureUnifiedInptSettle
     * @Desrciption 医保统一支付平台 ：住院结算业务：以及住院结算以后的反参
     * @Param inptVisitDTO：住院就诊实体信息  insureIndividualVisitDTO：医保就诊实体类对象 param:前台入参 inptCostDOList:费用集合
     * @Author fuhui
     * @Date 2021/5/19 10:44
     * @Return
     **/
    private void handInsureUnifiedInptSettle(InptVisitDTO inptVisitDTO, InsureIndividualVisitDTO insureIndividualVisitDTO, Map param, Map<String, Object> isInsureUnifiedMap, List<InptCostDO> inptCostDOList, String settleId) {
        /**统一支付平台调用出院办理   开始*/
        Map<String, Object> insureUnifiedPayParam = new HashMap<>();
        String hospCode = MapUtils.get(param, "hospCode");
        String code = MapUtils.get(param, "userCode");
        String userName = MapUtils.get(param, "userName");
        String userId = MapUtils.get(param, "userId");
        insureUnifiedPayParam.put("inptVisitDTO", inptVisitDTO);
        insureUnifiedPayParam.put("inptVisit", inptVisitDTO);
        insureUnifiedPayParam.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
        insureUnifiedPayParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        insureUnifiedPayParam.put("hospCode", inptVisitDTO.getHospCode());
        insureUnifiedPayParam.put("visitId", inptVisitDTO.getId());
        insureUnifiedPayParam.put("crteName", userName);
        insureUnifiedPayParam.put("crteId", MapUtils.get(param, "crteId"));
        insureUnifiedPayParam.put("code", code);
        insureUnifiedPayParam.put("userName", userName);
        insureUnifiedPayParam.put("isReadCard", MapUtils.get(param,"isReadCardPay"));
        insureUnifiedPayParam.put("bka895", MapUtils.get(param,"bka895"));
        insureUnifiedPayParam.put("bka896", MapUtils.get(param,"bka896"));
        /**统一支付平台调用出院办理   结束*/

        /**
         * 医保统一支付，结算
         */
        String insureSettleId = "";
        Map<String, String> insureInptResult = new HashMap<>();
        try {
            insureInptResult = insureUnifiedPayInptService.UP_2304(insureUnifiedPayParam).getData();
            /**
             * 如果是结算操作 需要获取结算反参的：结算id 用来做结算取消操作
             */
            insureSettleId = insureInptResult.get("setl_id"); // 结算id
            String medicalRegNo = insureInptResult.get("mdtrt_id"); // 医保返回的就诊id
            String omsgid = MapUtils.get(insureInptResult, "omsgid"); // 交易信息(原交易)中的msgid,发送方报文ID
            String oinfno = MapUtils.get(insureInptResult, "oinfno"); // 交易信息(原交易)中的infno
            String visitId = inptVisitDTO.getId();
            String clrOptins = MapUtils.get(insureInptResult, "clr_optins");
            String clrWay = MapUtils.get(insureInptResult, "clr_way");
            Object balcObject = MapUtils.get(insureInptResult, "balc");// 个人账户余额
            String clrType = MapUtils.get(insureInptResult, "clr_type");
            BigDecimal hospExemAmount = MapUtils.get(insureInptResult, "hospExemAmount");
            Object acctPayObject = MapUtils.get(insureInptResult, "acct_pay");

            String balanceValue = MapUtils.get(insureInptResult,"INSURE_ACCT_PAY_PARAM");  // 海南地区开启个账参数判断
            String acctUsedFlag= MapUtils.get(insureInptResult,"acct_used_flag"); // 是否使用个人账户标志
            BigDecimal personalPrice =
                    BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(insureInptResult,"akb066")));

            BigDecimal acctPay = null;
            if(acctPayObject == null){
                acctPay = new BigDecimal(0.00);
            }else{
                acctPay = BigDecimalUtils.convert(acctPayObject.toString()); // 个人账户支出
            }/**
             * 结算成功以后 更新基金信息
             */
            List<Map<String, Object>> setldetailList = MapUtils.get(insureInptResult, "setldetailList");
            if (!ListUtils.isEmpty(setldetailList)) {
                InsureIndividualFundDTO insureIndividualFundDTO = null;
                List<InsureIndividualFundDTO> fundDTOList = new ArrayList<>();
                for (Map<String, Object> item : setldetailList) {
                    insureIndividualFundDTO = new InsureIndividualFundDTO();
                    insureIndividualFundDTO.setId(SnowflakeUtils.getId());
                    insureIndividualFundDTO.setHospCode(hospCode);
                    insureIndividualFundDTO.setInsureSettleId(insureSettleId);
                    insureIndividualFundDTO.setVisitId(visitId);
                    insureIndividualFundDTO.setCrteName(userName);
                    insureIndividualFundDTO.setCrteId(userId);
                    insureIndividualFundDTO.setCrteTime(DateUtils.getNow());
                    insureIndividualFundDTO.setMibId(null);
                    insureIndividualFundDTO.setFundName(null);
                    insureIndividualFundDTO.setIndiFreezeStatus(null);
                    // 基金支付类型
                    insureIndividualFundDTO.setFundPayType(MapUtils.get(item, "fund_pay_type"));
                    if (MapUtils.isEmpty(item, "inscp_scp_amt")) {
                        insureIndividualFundDTO.setInscpScpAmt(null);
                    } else {
                        insureIndividualFundDTO.setInscpScpAmt(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt").toString()));
                    }
                    // 符合政策范围金额
                    // 本次可支付限额金额
                    if (MapUtils.isEmpty(item, "crt_payb_lmt_amt")) {
                        insureIndividualFundDTO.setCrtPaybLmtAmt(null);
                    } else {
                        insureIndividualFundDTO.setCrtPaybLmtAmt(BigDecimalUtils.convert(MapUtils.get(item, "crt_payb_lmt_amt").toString()));
                    }
                    if (MapUtils.isEmpty(item, "fund_payamt")) {
                        insureIndividualFundDTO.setFundPayamt(null);
                    } else {
                        // 基金支付金额
                        insureIndividualFundDTO.setFundPayamt(BigDecimalUtils.convert(MapUtils.get(item, "fund_payamt").toString()));
                    }
                    // 基金支付类型名称
                    insureIndividualFundDTO.setFundPayTypeName(MapUtils.get(item, "fund_pay_type_name"));
                    //结算过程信息
                    insureIndividualFundDTO.setSetlProcInfo(MapUtils.get(item, "setl_proc_info"));
                    fundDTOList.add(insureIndividualFundDTO);
                }
                isInsureUnifiedMap.put("fundDTOList", fundDTOList);
                System.out.println(isInsureUnifiedMap);
                insureIndividualSettleService.insertBatchFund(isInsureUnifiedMap).getData();
            }

            /**
             * 根据 就诊id， 状态为正常的数据 ，医院编码查询医保结算表的数据
             */
            InsureIndividualSettleDTO settleDTO = new InsureIndividualSettleDTO();
            settleDTO.setVisitId(visitId);
            settleDTO.setHospCode(hospCode);
            settleDTO.setSettleId(settleId);
            settleDTO.setState("0");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("hospCode", hospCode);
            dataMap.put("insureIndividualSettleDTO", settleDTO);
            settleDTO = insureIndividualSettleService.findByCondition(dataMap);
            InsureIndividualSettleDO individualSettleDO = new InsureIndividualSettleDO();
            /**
             * 根据查询回来的医保结算主键id,医院编码  更新医保结算id ，就医登记号
             */
            individualSettleDO.setHospCode(hospCode);
            individualSettleDO.setId(settleDTO.getId());
            individualSettleDO.setInsureSettleId(insureSettleId);
            individualSettleDO.setMedicalRegNo(medicalRegNo);
            individualSettleDO.setVisitId(visitId);
            individualSettleDO.setOinfno(oinfno);
            individualSettleDO.setHospExemAmount(hospExemAmount);
            individualSettleDO.setOmsgid(omsgid);

            Map<String, Object> map = new HashMap<>();
            map.put("hospCode", hospCode);
            map.put("insureIndividualSettleDO", individualSettleDO);
            InsureIndividualCostDTO costDTO = new InsureIndividualCostDTO();
            costDTO.setHospCode(hospCode);
            costDTO.setVisitId(visitId);
            costDTO.setCostList(inptCostDOList);
            costDTO.setInsureSettleId(insureSettleId);
            map.put("insureIndividualCostDTO", costDTO);
            insureIndividualCostService.editInsureCostByCostIDS(map); // 更新费用信息

            BigDecimal balcPrice = new BigDecimal(0.00);
            if(balcObject == null || (balcObject instanceof String && StringUtils.isEmpty(balcObject.toString()))){
                individualSettleDO.setLastSettle(balcPrice);
            }else{
                individualSettleDO.setLastSettle(BigDecimalUtils.convert((balcObject.toString())));
            }

            // 结算前个人账户余额 =  个人账户支出+结算后个人账户余额
            individualSettleDO.setBeforeSettle(BigDecimalUtils.add(acctPay,individualSettleDO.getLastSettle()));
            individualSettleDO.setClrWay(clrWay);
            individualSettleDO.setClrType(clrType);
            individualSettleDO.setClrOptins(clrOptins);
            if(Constants.SF.S.equals(balanceValue) && Constants.SF.S.equals(acctUsedFlag) ){
                individualSettleDO.setPersonalPrice(personalPrice);
            }
            insureIndividualSettleService.updateByPrimaryKeySelective(map); // 更新结算信息
            map.put("medicalRegNo",medicalRegNo);
            map.put("id",visitId);
            map.put("insureSettleId",insureSettleId);
            inptVisitDTO.setHospCode(hospCode);
            inptVisitDTO.setId(visitId);
            inptVisitDTO.setInsureSettleId(insureSettleId);
            inptVisitDTO.setMedicalRegNo(medicalRegNo);
            map.put("inptVisitDTO",inptVisitDTO);
            insureIndividualVisitService.updateInsureInidivdual(map);  // 更新就诊信息

            InptSettleDO inptSettleDO = new InptSettleDO();
            inptSettleDO.setId(settleId);
            inptSettleDO.setAcctPay(acctPay);
            inptSettleDO.setHospCode(hospCode);
            System.out.println("---------------------"+ acctPay);
            inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO);

        } catch (Exception e) {
            //调用结算异常，出院登记取消
            insureUnifiedPayParam.clear();
            insureUnifiedPayParam.put("hospCode", inptVisitDTO.getHospCode());
            insureUnifiedPayParam.put("setl_id", insureSettleId);
            insureUnifiedPayParam.put("medins_code", insureIndividualVisitDTO.getMedicineOrgCode());
            insureUnifiedPayParam.put("serial_no", insureIndividualVisitDTO.getMedicalRegNo());
            insureUnifiedPayParam.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
            insureUnifiedPayParam.put("psn_no", insureIndividualVisitDTO.getAac001());
            insureUnifiedPayParam.put("insuplc_admdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
            insureUnifiedPayParam.put("visitId", inptVisitDTO.getId());
            if (!StringUtils.isEmpty(insureSettleId)) {
                // 出院结算取消
                insureUnifiedPayInptService_consumer.UP_2305(insureUnifiedPayParam);
            }
//            // 出院登记撤销
//            insureUnifiedPayInptService_consumer.UP_2405(insureUnifiedPayParam);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @Method: querySettle
     * @Description: 获取住院发票数据
     * @Param: [params]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/8 14:28
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> querySettle(String settleId, String hospCode, String userName) {
        Map<String, Object> returnMap = new HashMap<>();
        List<Map<String, Object>> list = inptSettleDAO.querySettle(settleId, hospCode);
        if (!ListUtils.isEmpty(list)) {
            returnMap.put("name", list.get(0).get("name"));
            returnMap.put("sex", list.get(0).get("sex"));
            returnMap.put("age",list.get(0).get("age"));
            returnMap.put("ageUnitCode",list.get(0).get("ageUnitCode"));
            returnMap.put("address", list.get(0).get("address"));
            returnMap.put("outDeptName", list.get(0).get("outDeptName"));
            returnMap.put("inNo", list.get(0).get("inNo"));
            returnMap.put("patientCode", list.get(0).get("patientCode"));
            returnMap.put("inYear", list.get(0).get("inYear"));
            returnMap.put("inMonth", list.get(0).get("inMonth"));
            returnMap.put("inDay", list.get(0).get("inDay"));
            returnMap.put("outYear", list.get(0).get("outYear"));
            returnMap.put("outMonth", list.get(0).get("outMonth"));
            returnMap.put("outDay", list.get(0).get("outDay"));
            returnMap.put("skr", list.get(0).get("skr"));
            returnMap.put("kpr", userName);
            returnMap.put("yjxj", list.get(0).get("yjxj"));
            returnMap.put("yjzz", list.get(0).get("yjzz"));
            returnMap.put("tkxj", list.get(0).get("tkxj"));
            returnMap.put("tkzz", list.get(0).get("tkzz"));
            returnMap.put("bsxj", list.get(0).get("bsxj"));
            returnMap.put("bszz", list.get(0).get("bszz"));
            returnMap.put("miPrice", list.get(0).get("miPrice"));
            returnMap.put("selfPrice", list.get(0).get("selfPrice"));
            returnMap.put("settleRealityPrice", list.get(0).get("settleRealityPrice"));  // 优惠后总金额（支付）
            returnMap.put("settleTime", list.get(0).get("settleTime"));  // 结算时间  2021年6月2日11:09:58
            returnMap.put("personalPrice", list.get(0).get("personalPrice"));
            returnMap.put("beforeSettle", list.get(0).get("beforeSettle"));
            returnMap.put("lastSettle", list.get(0).get("lastSettle"));
            returnMap.put("creditPrice", list.get(0).get("creditPrice"));
            returnMap.put("bedName", list.get(0).get("bedName"));
            returnMap.put("seriousPrice",list.get(0).get("seriousPrice"));
            //费用列表 // 暂时保留2021年4月12日11:00:57 官红强
            Map<String, Object> detailMap = new HashMap<>();
            for (Map<String, Object> map : list) {
                detailMap.put(map.get("bfcCode").toString(), map.get("realityPrice"));
            }
            returnMap.put("diff", list.get(0).get("diff"));
            returnMap.put("detail", detailMap);
            // 发票上的计费类别需要根据维护的发票分类关联计费类别统计费用
            Map<String, Object> fpjflbMap = new HashMap<>();
            Map<String, String> bfcName = new HashMap<>();
            // 发票上的计费类别需要根据维护的发票分类关联计费类别统计费用  直接返回名字+金额   材料费：200
            Map<String, Object> fpjflbNameMap = new HashMap<>();
            for (Map<String, Object> map : list) {
                bfcName.put("A" + map.get("inCode"), (String) map.get("fpglName"));
                // 同一个发票分类金额计算在一起
                if (!fpjflbMap.containsKey("A" + map.get("inCode"))) {
                    fpjflbMap.put("A" + map.get("inCode"), map.get("realityPrice"));
                    fpjflbNameMap.put((String) map.get("fpglName"), map.get("realityPrice"));
                } else {
                    fpjflbMap.put("A" + map.get("inCode"), BigDecimalUtils.add((BigDecimal) fpjflbMap.get("A" + map.get("inCode")), (BigDecimal) map.get("realityPrice")));
                    fpjflbNameMap.put((String) map.get("fpglName"), fpjflbMap.get("A" + map.get("inCode")));
                }
            }
            int mark = 1;
            for (Map.Entry<String, String> entry : bfcName.entrySet()) {
                if ("A3".equals(entry.getKey()) || "A4".equals(entry.getKey()) || "A10".equals(entry.getKey()) || "A9".equals(entry.getKey()) || "A15".equals(entry.getKey()) || "A16".equals(entry.getKey())) {
                    returnMap.put("DN" + mark, entry.getValue());
                    returnMap.put("DA" + mark, fpjflbMap.get(entry.getKey()));
                    mark++;
                }
            }
            returnMap.put("fplb", fpjflbMap);
            returnMap.put("fplbName", fpjflbNameMap);
        }
        return returnMap;
    }

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryDiagnose(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptDiagnoseDTO> list = inptSettleDAO.queryDiagnose(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @param map
     * @Method editDischargeInpt
     * @Desrciption 医保出院办理
     *    1.出院办理前 先验证该病人是否已经医保出院登记
     *    2.增加个人累计信息的保存
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    @Override
    public Boolean editDischargeInpt(Map<String, Object> map) {
        String id = MapUtils.get(map,"id");
        String hospCode = MapUtils.get(map,"hospCode");
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        String inHalfSettle = MapUtils.get(map,"inHalfSettle");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(id);
        insureIndividualVisitDTO.setIsHalfSettle(inHalfSettle);
        insureIndividualVisitDTO.setMedicalRegNo(medicalRegNo);
        insureIndividualVisitDTO.setIsOut(Constants.SF.S);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        insureIndividualVisitDTO = insureIndividualVisitService.selectInsureInfo(map).getData();
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        if (insureIndividualVisitDTO == null) {
            throw new RuntimeException("未查询到医保登记信息，请先进行医保登记！");
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

        /**
         * 办理医保预出院之前 需要判断医保上传费用和his费用是不是一致
         */
        map.put("visitId",id);
        map.put("medicalRegNo",medicalRegNo);
        map.put("psnNo",insureIndividualVisitDTO.getAac001());
        map.put("isHalfSettle",insureIndividualVisitDTO.getIsHalfSettle());
        map.put("medisCode",insureIndividualVisitDTO.getMedicineOrgCode());
        checkInsureAndHisFee(map);
        /**
         * 保存个人累计信息
         */
        insertPatientSumInfo(map);
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            insureUnifiedPayInptService_consumer.UP_2402(map);
            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setId(id);
            inptVisitDTO.setHospCode(hospCode);
            inptVisitDTO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());
            inptVisitDTO.setIsOut("1");
            map.put("inptVisitDTO",inptVisitDTO);
            insureIndividualVisitService.updateInsureInidivdual(map).getData();
        }
        return true;
    }

    /**
     * @Method 办理医保预出院之前，需要保存个人累计信息
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/23 14:27
     * @Return
    **/
    private void insertPatientSumInfo(Map<String, Object> map) {
        String crteName = MapUtils.get(map,"crteName"); //  创建人姓名
        String crteId = MapUtils.get(map,"crteId"); // 创建人id
        String visitId = MapUtils.get(map,"visitId"); // 就诊id
        String hospCode = MapUtils.get(map,"hospCode"); // 医院编码
        String psnNo = MapUtils.get(map,"psnNo"); // 个人编号
        String medisCode = MapUtils.get(map,"medisCode"); // 医疗机构编码
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        Map<String, Object> dataMap = insureUnifiedBaseService_consumer.queryPatientSumInfo(map).getData();
        List<Map<String, Object>> resultDataMap = MapUtils.get(dataMap,"resultDataMap");
        if(!ListUtils.isEmpty(resultDataMap)){
            resultDataMap.stream().forEach(item->{
                item.put("id",SnowflakeUtils.getId());
                item.put("crteName",crteName);
                item.put("medicalRegNo",medicalRegNo);
                item.put("crteId",crteId);
                item.put("visitId",visitId);
                item.put("hospCode",hospCode);
                item.put("crteName",crteName);
                item.put("psnNo",psnNo);
                item.put("medisCode",medisCode);
                item.put("insureSettleId",null);
                item.put("crteTime",DateUtils.getNow());
                Object cum = item.get("cum");
                if (cum == null || StringUtils.isEmpty(cum.toString())) {
                    item.put("cum",0);
                }
            });
            inptVisitDAO.deletePatientSumInfo(map);
            inptVisitDAO.insertPatientSumInfo(resultDataMap);
        }
    }

    /**
     * @Method checkInsureAndHisFee
     * @Desrciption  办理医保预出院之前 需要核对his和医保费用
     * 1.费用已产生      项目未生成  基础数据正常
     * 2.费用已产生   项目未生成 但是基础数据已作废
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/29 14:09
     * @Return
    **/
    private void checkInsureAndHisFee(Map<String, Object> map) {
        /**
         * 如果是中途结算，验证费用是否传输完时，需要加上中途结算时间区间
         */
        if("1".equals(MapUtils.get(map,"isHalfSettle"))){
            InsureIndividualCostDTO insureIndividualCostDTO= insureIndividualCostService.selectFeeStartAndEndTime(map).getData();
            map.put("feeStartDate",insureIndividualCostDTO.getFeeStartTime());
            map.put("feeEndDate",insureIndividualCostDTO.getFeeEndTime());
            map.put("medicialRegNo",insureIndividualCostDTO.getInsureRegisterNo());
        }
        List<InptCostDTO> inptCostDTOList  =  inptCostDAO.checkInsureAndHisFee(map);
        if(!ListUtils.isEmpty(inptCostDTOList)){
            List<String> itemNameCollect = inptCostDTOList.stream().map(InptCostDTO::getItemName).collect(Collectors.toList());
            BigDecimal inptSumPrice = inptCostDTOList.get(0).getInptSumPrice(); // his费用
            BigDecimal insureSumPrice = inptCostDTOList.get(0).getInsureSumPrice(); // 医保费用
            if(!ListUtils.isEmpty(itemNameCollect) && !BigDecimalUtils.equalTo(insureSumPrice,inptSumPrice)){
               String[] arrs = new String[itemNameCollect.size()];
                arrs = itemNameCollect.toArray(arrs);
                throw new AppException("HIS总费用和医保上传费用不平:HIS费用为:"+inptSumPrice+ "医保费用为:"+insureSumPrice+ "存在如下未上传的项目数据"+Arrays.toString(arrs)+"" +
                        "请先去生成匹配该项目,然后传输该费用");
            }
        }
    }

    /**
     * @param map
     * @Method editCancelDischargeInpt
     * @Desrciption 医保出院办理撤销
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    @Override
    public Boolean editCancelDischargeInpt(Map<String, Object> map) {
        String id = MapUtils.get(map,"id");
        String hospCode = MapUtils.get(map,"hospCode");
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(id);
        insureIndividualVisitDTO.setMedicalRegNo(medicalRegNo);
        insureIndividualVisitDTO.setIsOut(Constants.SF.S);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        insureIndividualVisitDTO = insureIndividualVisitService.selectInsureInfo(map).getData();
        if (insureIndividualVisitDTO == null) {
            throw new RuntimeException("未查询到医保登记信息，请先进行医保登记！");
        }
//        List<InsureIndividualVisitDTO> byCondition = insureIndividualVisitService.findByCondition(map);
//        if(ListUtils.isEmpty(byCondition)){
//            throw new AppException("该患者医保未做医保出院办理操作,不能进行医保出院取消操作");
//        }

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

        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            // 出院登记撤销
            map.put("visitId",id);
            insureUnifiedPayInptService_consumer.UP_2405(map);
            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setId(id);
            inptVisitDTO.setHospCode(hospCode);
            inptVisitDTO.setIsOut("0");
            inptVisitDTO.setMedicalRegNo(medicalRegNo);
            map.put("inptVisitDTO",inptVisitDTO);
            insureIndividualVisitService.updateInsureInidivdual(map).getData();
        }
        return true;
    }

    /**
     * @param hospCode 医院编码
     * @param code     参数代码
     * @Menthod getSysParameter
     * @Desrciption 查询sys_parameter配置表
     * @Author Ou·Mr
     * @Date 2020/8/28 15:22
     * @Return cn.hsa.module.sys.parameter.entity.SysParameterDO
     */
    private SysParameterDO getSysParameter(String hospCode, String code) {
        SysParameterDTO sysParameterDTO = new SysParameterDTO();//查询条件
        sysParameterDTO.setHospCode(hospCode);//医院编码
        sysParameterDTO.setIsValid(Constants.SF.S);//是否有效；设置：是
        sysParameterDTO.setCode(code);//字典code
        Map param = new HashMap();
        param.put("hospCode", hospCode);
        param.put("sysParameterDTO", sysParameterDTO);
        List<SysParameterDTO> sysParameterDTOS = sysParameterService_consumer.queryAll(param).getData();
        if (sysParameterDTOS == null || sysParameterDTOS.isEmpty()) {
            return new SysParameterDO();
        }
        return sysParameterDTOS.get(0);
    }

    /**
     * @param hospCode 医院编码
     * @param typeCode 规则标志Code
     * @Menthod getOrderNo
     * @Desrciption 生成规则单据号
     * @Author Ou·Mr
     * @Date 2020/8/28 17:26
     * @Return java.lang.String 单据号
     */
    private String getOrderNo(String hospCode, String typeCode) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("hospCode", hospCode);
        param.put("typeCode", typeCode);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        return orderNo.getData();
    }

    @Override
    public WrapperResponse saveBabyCostTrial(InptVisitDTO inptVisitDTO) {
        String id = inptVisitDTO.getId();//就诊id
        InptBabyDTO inptBabyDTO=new InptBabyDTO();
        inptBabyDTO.setVisitId(id);
        inptBabyDTO.setHospCode(inptVisitDTO.getHospCode());
        String babyId =inptVisitDTO.getBabyId();
        String[] babyIds =new String[1];
        babyIds[0] = babyId;
        String code = inptVisitDTO.getCode();
        String hospCode = inptVisitDTO.getHospCode();//医院编码
        String treatmentCode = inptVisitDTO.getTreatmentCode();
        String key = new StringBuilder(hospCode).append(babyIds[0]).append(Constants.INPT_FEES_REDIS_KEY).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key))) {
            return WrapperResponse.fail("当前婴儿正在试算，请稍后再试。", null);
        }
        try {
            redisUtils.set(key, id);//保存值
            //删除试算脏数据（inpt_settle、insure_individual_settle）
            Map<String, String> delParam = new HashMap<String, String>();
            delParam.put("hospCode", hospCode);//医院编码
            delParam.put("visitId", id);//就诊id
            delParam.put("babyIds", babyIds[0]);//婴儿id
            delParam.put("isSettle", Constants.SF.F);//是否结算 = 否
            delParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            inptSettleDAO.deleteByVisitId(delParam);
            String settleId = SnowflakeUtils.getId();//结算id
            //获取个人信息
            InptVisitDTO inptVisitDTO1 = inptVisitDAO.getInptVisitById(inptVisitDTO);
            inptVisitDTO1.setUserCode(inptVisitDTO.getUserCode());
            if (inptVisitDTO1 == null) {
                throw new AppException("未找到该患者。");
            }
            //获取患者所有费用信息
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("hospCode", hospCode);//医院编码
            costParam.put("visitId", id);//就诊id
            costParam.put("babyIds", babyIds);//婴儿Id
            //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            if (inptCostDOList.isEmpty()){
                throw new AppException("该患者没有产生费用信息。");
            }
            Integer patientValueCode = Integer.parseInt(inptVisitDTO1.getPatientCode());
            if (inptCostDOList.isEmpty() && patientValueCode > 0) {
                throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
            }
            for (InptCostDO dto : inptCostDOList) {
                if (dto.getIsOk().equals("0")) {
                    throw new AppException("该患者有还未确费的费用，包括LIS或PACS检查，请先确费。");
                }
            }
            //计算所有费用金额
            BigDecimal totalPrice = new BigDecimal(0);//总费用
            BigDecimal preferentialPrice = new BigDecimal(0);//单据优惠
            BigDecimal miPrice = new BigDecimal(0);//合同单位金额
            BigDecimal realityPrice = new BigDecimal(0);//单据应缴
            BigDecimal payPrice = new BigDecimal(0);//预交款
            BigDecimal subsequentPrice = new BigDecimal(0);//补收
            BigDecimal refundPrice = new BigDecimal(0);//退款
            for (InptCostDO inptCostDO : inptCostDOList) {
                if (inptCostDO.getTotalPrice() != null) {
                    totalPrice = BigDecimalUtils.add(totalPrice, inptCostDO.getTotalPrice());//总费用
                }
                if (inptCostDO.getPreferentialPrice() != null) {
                    preferentialPrice = BigDecimalUtils.add(preferentialPrice, inptCostDO.getPreferentialPrice());//单据优惠
                }
                if (inptCostDO.getRealityPrice() != null) {
                    realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());//单据应缴
                }
            }

            String settleNo = getOrderNo(hospCode, Constants.ORDERRULE.ZY); // 获取结算单号
            //计算补收金额、退款金额
            //补收金额: 优惠总金额 - 合同支付报销金额 - 预交金 = 还需补收的金额
            //如果是医保病人单病种 直接就是 需支付金额 - 预交金；如果不是单病种 需支付金额 - 医保支付金额 - 预交金
            subsequentPrice =BigDecimalUtils.subtract(BigDecimalUtils.subtract(realityPrice, miPrice), payPrice);
            //舍入金额
            BigDecimal truncPrice = new BigDecimal(0);
            //退款
            if (BigDecimalUtils.lessZero(subsequentPrice)) {
                refundPrice = subsequentPrice;
                subsequentPrice = new BigDecimal(0);
            } else {
                //根据医院配置舍入金额
                SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
                truncPrice = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舍入费用
            }
            Date now = new Date(); //当前时间
            Date minDate = null;
            Date maxDate = null;
            if (!inptCostDOList.isEmpty()) {
                minDate = inptCostDOList.get(0).getCostTime(); //费用开始时间
                maxDate = inptCostDOList.get(inptCostDOList.size() - 1).getCostTime(); //费用结束时间
            }
            //封装结算信息
            InptSettleDO inptSettleDO = new InptSettleDO();
            inptSettleDO.setId(settleId);//结算id
            inptSettleDO.setHospCode(hospCode);//医院编码
            inptSettleDO.setVisitId(id);//就诊id
            inptSettleDO.setBabyId(babyIds[0]);//婴儿id
            inptSettleDO.setSettleNo(settleNo);//结算单号
            inptSettleDO.setTypeCode(Constants.JSLX.CYJS);//结算类型 = 出院结算
            inptSettleDO.setPatientCode(inptVisitDTO1.getPatientCode());//病人类型
            inptSettleDO.setStartTime(minDate);//开始时间
            inptSettleDO.setEndTime(maxDate);//结束时间
            inptSettleDO.setTotalPrice(totalPrice);//总费用
            inptSettleDO.setRealityPrice(realityPrice);//优惠后总金额
            inptSettleDO.setTruncPrice(truncPrice);//舍入金额
            inptSettleDO.setActualPrice(new BigDecimal(0));//实收金额
            inptSettleDO.setSelfPrice(subsequentPrice);//个人支付
            inptSettleDO.setMiPrice(miPrice);//统筹支付
            inptSettleDO.setApTotalPrice(payPrice);//预交金合计
            inptSettleDO.setApOffsetPrice(payPrice);//预交金冲抵
            inptSettleDO.setSettleTakePrice(subsequentPrice);//结算补收
            inptSettleDO.setSettleBackPrice(refundPrice.negate());//结算退款
            inptSettleDO.setSettleBackCode(Constants.ZFFS.XJ);//退款方式默认现金
            inptSettleDO.setIsSettle(Constants.SF.F);//是否结算 = 否
            inptSettleDO.setSettleTime(now);//结算时间
            inptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
            inptSettleDO.setRedId(null);//冲红id
            inptSettleDO.setIsPrint(Constants.SF.F);//是否打印 = 否
            inptSettleDO.setHospDfPrice(new BigDecimal(0));//医院垫付金额
            inptSettleDO.setHospJmPrice(new BigDecimal(0));//医院减免金额
            inptSettleDO.setOutSettleCode(null);//TODO 出院结算方式 (待确认)
            inptSettleDO.setDailySettleId(null);//日结缴款id
            inptSettleDO.setSourcePayCode(null);//TODO 支付来源方式（待确认）
            inptSettleDO.setOrderNo(null);//支付订单号
            inptSettleDO.setCrteId(inptVisitDTO.getCrteId());//创建人id
            inptSettleDO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
            inptSettleDO.setCrteTime(now);//创建时间
            inptSettleDAO.insert(inptSettleDO);

            //封装返回前端需要的参数
            JSONObject result = new JSONObject();
            result.put("totalPrice", totalPrice);//总费用
            result.put("preferentialPrice", preferentialPrice);//单据优惠
            result.put("realityPrice", realityPrice);//单据应缴
            result.put("miPrice", miPrice);//合同单位金额
            result.put("payPrice", payPrice);//预交款
            result.put("subsequentPrice", subsequentPrice);//补收
            result.put("refundPrice", refundPrice);//退款
            result.put("truncPrice", truncPrice);//舍入金额
            result.put("settleId", settleId);//结算id
            //result.put("payinfo", inptInsureResult);//统筹支付信息
            return WrapperResponse.success("试算成功。", result);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            redisUtils.del(key);
        }
    }

    @Override
    public WrapperResponse saveBabySettle(Map param) {
        String treatmentCode = MapUtils.get(param, "treatmentCode");
        String id = (String) param.get("id");//就诊id
        String hospCode = (String) param.get("hospCode");//医院编码
        InptBabyDTO inptBabyDTO=new InptBabyDTO();
        inptBabyDTO.setVisitId(id);
        inptBabyDTO.setHospCode(hospCode);
        String babyId =(String) param.get("babyId");
        String[] babyIds =new String[1];
        babyIds[0] = babyId;
//        //inptBabyDTO.setId(id);
//        List<InptBabyDTO> inptBabyDTOS=inptBabyDAO.findByCondition(inptBabyDTO);
//        String babyIds[] = new String[inptBabyDTOS.size()];
//        if (inptBabyDTOS!=null&&inptBabyDTOS.size()>0) {
//            for (int i=0;i<inptBabyDTOS.size();i++){
//                babyIds[i]=inptBabyDTOS.get(i).getId();
//            }
//        }else {
//            return WrapperResponse.fail("没有查找到婴儿信息，不能进行婴儿单独结算", null);
//        }
        String code = param.get("userCode").toString();
        String userName = param.get("userName").toString();
        String key = new StringBuilder(hospCode).append(babyIds).append(Constants.INPT_FEES_REDIS_KEY).toString();
        if (StringUtils.isNotEmpty(redisUtils.get(key))) return WrapperResponse.fail("当前患者正在结算，请稍后再试。", null);
        try {
            redisUtils.set(key, id);
            Boolean isInvoice = (Boolean) param.get("isInvoice");//是否使用发票
            String userId = (String) param.get("userId");//当前登录用户id
            OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
            //校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
            if (isInvoice) {
                outinInvoiceDTO.setHospCode(hospCode);//医院编码
                outinInvoiceDTO.setUseId(userId);//发票使用人id
                List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
                Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.ZY);
                outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；4、住院
                outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                //校验是否有在用状态的发票段，有就返回在用的发票信outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService_consumer.updateForOutinInvoiceQuery(map).getData();
                if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
                    //没有发票信息
                    return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
                }
                outinInvoiceDTO = outinInvoiceDTOS.get(0);
            }
            String settleId = (String) param.get("settleId");//结算id
            //根据结算id查询本次结算信息
            InptSettleDO inptSettleDO = inptSettleDAO.selectByPrimaryKey(settleId);
            if (inptSettleDO == null) return WrapperResponse.fail("未找到结算记录信息，请重新试算。", null);
            //校验费用信息
            Map<String, Object> costParam = new HashMap<String, Object>();
            costParam.put("hospCode", hospCode);//医院编码
            costParam.put("visitId", id);//就诊id
            costParam.put("babyIds",babyIds);// 婴儿id
            //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            // if (inptCostDOList == null || inptCostDOList.isEmpty()){return WrapperResponse.fail("未找到结算费用信息，请刷新。",null);}

            //获取当前患者信息参数
            InptVisitDTO inptVisitDTO = new InptVisitDTO();
            inptVisitDTO.setHospCode(hospCode);//医院编码
            inptVisitDTO.setId(id);//就诊id
            inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
            if (inptVisitDTO == null) {
                return WrapperResponse.fail("未找到该患者信息，请刷新。", null);
            }
            // 出院结算校验病人状态是不是预出院  lly 2021/11/01
            if (inptVisitDTO != null && inptVisitDTO.getStatusCode() != null &&!inptVisitDTO.getStatusCode().equals(Constants.YDLX.YCY)){
                return WrapperResponse.fail("该患者不是预出院状态，请刷新。", null);
            }

            Integer patientValueCode = Integer.parseInt(inptVisitDTO.getPatientCode());
            if (inptCostDOList.isEmpty() && patientValueCode > 0) {
                throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
            }

            BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
            String[] costIds = new String[inptCostDOList.size()];
            List<InptCostSettleDO> inptCostSettleDOList = new ArrayList<InptCostSettleDO>();
            int costIndex = 0;
            for (InptCostDO inptCostDO : inptCostDOList) {
                if (inptCostDO.getRealityPrice() != null) {
                    realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());
                    costIds[costIndex++] = inptCostDO.getId();
                    InptCostSettleDO inptCostSettleDO = new InptCostSettleDO();
                    inptCostSettleDO.setId(SnowflakeUtils.getId());//id
                    inptCostSettleDO.setHospCode(hospCode);//医院编码
                    inptCostSettleDO.setVisitId(id);//就诊id
                    inptCostSettleDO.setBabyId(inptCostDO.getBabyId());//babyID
                    inptCostSettleDO.setCostId(inptCostDO.getId());//费用id
                    inptCostSettleDO.setSettleId(settleId);//结算id
                    inptCostSettleDO.setRealityPrice(inptCostDO.getRealityPrice()); // 优惠后金额
                    inptCostSettleDOList.add(inptCostSettleDO);
                }
            }
            //校验费用是否一致
            if (!BigDecimalUtils.equals(realityPrice, inptSettleDO.getRealityPrice())) {
                throw new AppException("费用发生改变，请重新试算。");
            }
            BigDecimal selfPrice = inptSettleDO.getSelfPrice();//个人支付金额

            //计算支付金额
            List<InptPayDO> inptPayDOList = (List<InptPayDO>) param.get("inptPayDOList");//支付方式
            BigDecimal actualPrice = new BigDecimal(0);//实收金额
            // 第三方支付金额
            BigDecimal thirdPartyPrice = new BigDecimal(0);
            List<InptPayDO> inptPayParam = new ArrayList<InptPayDO>();
            for (InptPayDO inptPayDO : inptPayDOList) {
                //TODO 后续考虑支付手续费
                if (StringUtils.isNotEmpty(inptPayDO.getPayCode()) && inptPayDO.getPrice() != null) {
                    // 支付方式中第三方支付费用总和
                    if (!inptPayDO.getPayCode().equals(Constants.ZFFS.XJ)) {
                        thirdPartyPrice = BigDecimalUtils.add(thirdPartyPrice, inptPayDO.getPrice());
                    }
                    actualPrice = BigDecimalUtils.add(actualPrice, inptPayDO.getPrice());
                    inptPayDO.setId(SnowflakeUtils.getId());//id
                    inptPayDO.setHospCode(hospCode);//医院编码
                    inptPayDO.setSettleId(settleId);//结算id
                    inptPayDO.setVisitId(id);//就诊id
                    inptPayDO.setServicePrice(inptPayDO.getServicePrice() == null ? new BigDecimal(0) : inptPayDO.getServicePrice());//手术费
                    inptPayParam.add(inptPayDO);
                }
            }
            //判断支付金额是否小于需支付金额
            int greater = BigDecimalUtils.compareTo(selfPrice, actualPrice);
            if (greater > 0) {
                throw new AppException("实收金额不能小于需支付金额。");
            }
            ;
            // ====================================================================
            // 官红强  2021年1月19日15:16:23 1、如果正常收钱有找零，需要将现金支付金额减去找零金额后再保存，（前提要校验收收费金额还是退费金额）
            // 应收金额大于0，退款金额小于0，说明是收款
            if (BigDecimalUtils.greaterZero(selfPrice) && BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice())) {
                // 应收金额小于实际付款金额，则需要将现金支付金额减去多余的金额
                if (greater < 0) {
                    // 如果第三方支付金额总和大于应收金额，则不允许保存
                    if (BigDecimalUtils.compareTo(thirdPartyPrice, selfPrice) > 0) {
                        throw new AppException("第三方支付金额总和不能大于应收金额。");
                    }
                    BigDecimal dif = BigDecimalUtils.subtract(selfPrice, actualPrice);
                    for (int i = 0; i < inptPayParam.size(); i++) {
                        // 支付方式为现金的支付方式
                        if (inptPayParam.get(i).getPayCode().equals(Constants.ZFFS.XJ)) {
                            // 现金支付金额减去多收的金额
                            inptPayParam.get(i).setPrice(BigDecimalUtils.add(inptPayParam.get(i).getPrice(), dif));
                        }
                    }
                }
            }
            // 官红强  2021年1月19日15:16:23 2、如果预交金额大于实际使用金额，说明有退款，则需要保存退款的负金额记录
            if (BigDecimalUtils.greaterZero(inptSettleDO.getSettleBackPrice())) {
                // 退款支持多路径退，需要校验输入的退款总和不能大于应退金额(即输入金额总和=应退金额)
                if (!BigDecimalUtils.equals(actualPrice, inptSettleDO.getSettleBackPrice())) {
                    throw new AppException("各种支付方式输入的金额总和必须等于应退金额");
                } else {
                    for (int i = 0; i < inptPayParam.size(); i++) {
                        // 将输入的退款金额取反
                        inptPayParam.get(i).setPrice(BigDecimalUtils.negate(inptPayParam.get(i).getPrice()));
                    }
                }
                inptPayDAO.batchInsert(inptPayParam);
            }
            // =======================官红强 注释=============================================
            //保存结算费用关联信息
            if (!inptCostSettleDOList.isEmpty()) {
                inptCostSettleDAO.batchInsert(inptCostSettleDOList);
            }
            //修改费用状态
            if (!inptCostDOList.isEmpty()) {
                Map<String, Object> inptCostParam = new HashMap<String, Object>();
                inptCostParam.put("settleId", settleId);//结算id
                inptCostParam.put("settleCode", Constants.JSZT.YIJS);//结算状态 = 已结算
                inptCostParam.put("hospCode", hospCode);//医院编码
                inptCostParam.put("ids", costIds);//费用id
                inptCostDAO.editInptCostByIds(inptCostParam);
            }
            //修改结算表状态
            InptSettleDO inptSettleDO1 = new InptSettleDO();
            inptSettleDO1.setId(settleId);//结算id
            inptSettleDO1.setIsSettle(Constants.SF.S);//是否结算 = 是
            inptSettleDO1.setSourcePayCode("0"); // 0：his 1：微信 2：支付宝 3：自助机
            inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO1);

            //判断是否需要发票
            if (isInvoice) { //true:打印发票生成发票信息
                Map<String, Object> map = new HashMap<String, Object>();
                outinInvoiceDTO.setSettleId(settleId);//结算id
                outinInvoiceDTO.setDqCurrNo(outinInvoiceDTO.getCurrNo());
                map.put("hospCode", hospCode);
                map.put("outinInvoiceDTO", outinInvoiceDTO);
                OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService_consumer.updateInvoiceStatus(map).getData();
                //保存inpt_settle_invoice住院结算发票情况表
                InptSettleInvoiceDO inptSettleInvoiceDO = new InptSettleInvoiceDO();
                String inptSettleInvoiceId = SnowflakeUtils.getId();
                inptSettleInvoiceDO.setId(inptSettleInvoiceId);//id
                inptSettleInvoiceDO.setHospCode(hospCode);//医院编码
                inptSettleInvoiceDO.setSettleId(settleId);//结算id
                inptSettleInvoiceDO.setVisitId(id);//就诊id
                inptSettleInvoiceDO.setInvoiceId(outinInvoiceDTO.getId());//发票id
                inptSettleInvoiceDO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
                inptSettleInvoiceDO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
                inptSettleInvoiceDO.setTotalPrice(realityPrice);//发票总金额
                inptSettleInvoiceDO.setPrintId(null);//发票打印人id
                inptSettleInvoiceDO.setPrintName(null);//发票打印人姓名
                inptSettleInvoiceDO.setPrintTime(null);//发票打印时间
                inptSettleInvoiceDO.setPrintNum(0);//打印次数
                inptSettleInvoiceDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）
                inptSettleInvoiceDO.setRedId(null);//冲红id
                inptSettleInvoiceDAO.insertSelective(inptSettleInvoiceDO);//保存住院结算发票情况表

                //保存inpt_settle_invoice_content住院结算发票内容表
                InptSettleInvoiceContentDO inptSettleInvoiceContentDO = new InptSettleInvoiceContentDO();
                inptSettleInvoiceContentDO.setId(SnowflakeUtils.getId());//id
                inptSettleInvoiceContentDO.setHospCode(hospCode);//医院编码
                inptSettleInvoiceContentDO.setSettleInvoiceId(inptSettleInvoiceId);//结算发票ID（inpt_settle_invoice）
                inptSettleInvoiceContentDO.setInCode(null);//住院发票代码
                inptSettleInvoiceContentDO.setInName(null);//住院发票名称
                inptSettleInvoiceContentDO.setRealityPrice(realityPrice);//优惠后总金额
                inptSettleInvoiceContentDAO.insertSelective(inptSettleInvoiceContentDO);//保存住院结算发票内容表
            }
            // 官红强修改， 2021年1月19日14:50:34 如果个人自付为0，意味着有预交金额退款，此时不再写一遍支付信息,如果自付与预交刚好相等，需要写一次记录（即自付为0，退款为0）
            if (!inptPayParam.isEmpty() && (!BigDecimalUtils.isZero(selfPrice) || BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice()))) {
                //保存inpt_pay 住院结算支付方式表
                inptPayDAO.batchInsert(inptPayParam);
            }
            //根据就诊id修改inpt_visit（患者就诊表）当前状态代码 = 出院状态
            InptVisitDTO inptVisitDTO1 = new InptVisitDTO();
            inptVisitDTO1.setId(id);//id
            inptVisitDTO1.setHospCode(hospCode);//医院编码
            // 婴儿单独结算不能算已出院
            inptVisitDTO1.setStatusCode(Constants.BRZT.YCY);//当前状态 = 出院状态
            inptVisitDTO1.setTreatmentCode(inptVisitDTO.getTreatmentCode());
            inptVisitDAO.updateInptVisit(inptVisitDTO1);
            return WrapperResponse.success("支付成功。", null);
        }
        catch (RuntimeException e) {
            throw e;
        } finally {
            redisUtils.del(key);
        }
    }


    /**
    * @Menthod saveAttributionCostTrial
    * @Desrciption 归属结算试算
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 14:32
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    @Override
    @Async
    public synchronized WrapperResponse saveAttributionCostTrial(InptVisitDTO inptVisitDTO) {
      String id = inptVisitDTO.getId();//就诊id
      String code = inptVisitDTO.getCode();
      String hospCode = inptVisitDTO.getHospCode();//医院编码
      String treatmentCode = inptVisitDTO.getTreatmentCode();
      String isMidWaySettle = inptVisitDTO.getIsMidWaySettle();  // 医保中途结算标识 1：中途结算 0：出院结算
      String medicalRegNo = inptVisitDTO.getMedicalRegNo();
      String key = new StringBuilder(hospCode).append(id).append(Constants.INPT_FEES_REDIS_KEY).toString();
      redisUtils.del(key);
      if (StringUtils.isNotEmpty(redisUtils.get(key))) {
        return WrapperResponse.fail("当前患者正在试算，请稍后再试。", null);
      }
      try {
        redisUtils.set(key, id);//保存值
        //删除试算脏数据（inpt_settle、insure_individual_settle）
        Map<String, String> delParam = new HashMap<String, String>();
        delParam.put("hospCode", hospCode);//医院编码
        delParam.put("visitId", id);//就诊id
        delParam.put("isSettle", Constants.SF.F);//是否结算 = 否
        delParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        inptSettleDAO.deleteByVisitId(delParam);
        String settleId = SnowflakeUtils.getId();//结算id
        //获取个人信息
        InptVisitDTO inptVisitDTO1 = inptVisitDAO.getInptVisitById(inptVisitDTO);
        inptVisitDTO1.setUserCode(inptVisitDTO.getUserCode());
        if (inptVisitDTO1 == null) {
          throw new AppException("未找到该患者。");
        }
        //获取患者所有费用信息
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("hospCode", hospCode);//医院编码
        costParam.put("visitId", id);//就诊id
        //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
        costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
        if(!StringUtils.isEmpty(inptVisitDTO.getAttributionCode())) {
          costParam.put("attributionCode", inptVisitDTO.getAttributionCode());// 结算类型
        }
        //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
        List<InptCostDO> inptCostDOList = new ArrayList<>();
        // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
        if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
          costParam.put("isMidWaySettle", isMidWaySettle);
          inptCostDOList = inptCostDAO.queryMidWaySettleInptCostList(costParam);
        } else {
          inptCostDOList = inptCostDAO.queryInptCostList(costParam);
        }
        // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
        //if (inptCostDOList.isEmpty()){throw new AppException("该患者没有产生费用信息。");}
        if (inptCostDOList.isEmpty()) {
          throw new AppException("没有费用需要结算");
        }
        for (InptCostDO dto : inptCostDOList) {
          if (dto.getIsOk().equals("0")) {
            throw new AppException("该患者有还未确费的费用，包括LIS或PACS检查，请先确费。");
          }
        }

        //计算所有费用金额
        BigDecimal totalPrice = new BigDecimal(0);//总费用
        BigDecimal preferentialPrice = new BigDecimal(0);//单据优惠
        BigDecimal miPrice = new BigDecimal(0);//合同单位金额
        BigDecimal realityPrice = new BigDecimal(0);//单据应缴
        BigDecimal payPrice = new BigDecimal(0);//预交款
        BigDecimal subsequentPrice = new BigDecimal(0);//补收
        BigDecimal refundPrice = new BigDecimal(0);//退款
        for (InptCostDO inptCostDO : inptCostDOList) {
          if (inptCostDO.getTotalPrice() != null) {
            totalPrice = BigDecimalUtils.add(totalPrice, inptCostDO.getTotalPrice());//总费用
          }
          if (inptCostDO.getPreferentialPrice() != null) {
            preferentialPrice = BigDecimalUtils.add(preferentialPrice, inptCostDO.getPreferentialPrice());//单据优惠
          }
          if (inptCostDO.getRealityPrice() != null) {
            realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());//单据应缴
          }
        }
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        String settleNo = getOrderNo(hospCode, Constants.ORDERRULE.ZY); // 获取结算单号
        //医保病人
        Map<String, String> inptInsureResult = new HashMap<String, String>();
        String patientCode = inptVisitDTO1.getPatientCode();
        Integer intPatientCode = Integer.valueOf(patientCode);
        //计算补收金额、退款金额
        //归属结算补收金额（不用预交金）: 优惠总金额  = 还需补收的金额
        subsequentPrice =  realityPrice;
        //舍入金额
        BigDecimal truncPrice = new BigDecimal(0);
        //退款
        if (BigDecimalUtils.lessZero(subsequentPrice)) {
          refundPrice = subsequentPrice;
          subsequentPrice = new BigDecimal(0);
        } else {
          //根据医院配置舍入金额
          SysParameterDO sysParameterDO = getSysParameter(hospCode, Constants.HOSPCODE_DISCOUNTS_KEY);//获取当前医院优惠配置
          truncPrice = BigDecimalUtils.rounding(sysParameterDO.getValue(), realityPrice); //舍入费用
        }
        Date now = new Date(); //当前时间
        Date minDate = null;
        Date maxDate = null;
        if (!inptCostDOList.isEmpty()) {
          minDate = inptCostDOList.get(0).getCostTime(); //费用开始时间
          maxDate = inptCostDOList.get(inptCostDOList.size() - 1).getCostTime(); //费用结束时间
        }
        //封装结算信息
        InptSettleDO inptSettleDO = new InptSettleDO();
        inptSettleDO.setId(settleId);//结算id
        inptSettleDO.setHospCode(hospCode);//医院编码
        inptSettleDO.setVisitId(id);//就诊id
        inptSettleDO.setBabyId(null);//婴儿id
        inptSettleDO.setSettleNo(settleNo);//结算单号
        inptSettleDO.setTypeCode(Constants.JSLX.QTJS);//结算类型 = 归属结算（其他结算）
        inptSettleDO.setPatientCode(inptVisitDTO1.getPatientCode());//病人类型
        inptSettleDO.setStartTime(minDate);//开始时间
        inptSettleDO.setEndTime(maxDate);//结束时间
        inptSettleDO.setTotalPrice(totalPrice);//总费用
        inptSettleDO.setRealityPrice(realityPrice);//优惠后总金额
        inptSettleDO.setTruncPrice(truncPrice);//舍入金额
        inptSettleDO.setActualPrice(new BigDecimal(0));//实收金额
        inptSettleDO.setSelfPrice(subsequentPrice);//个人支付
        inptSettleDO.setMiPrice(miPrice);//统筹支付
        inptSettleDO.setApTotalPrice(payPrice);//预交金合计
        inptSettleDO.setApOffsetPrice(payPrice);//预交金冲抵
        inptSettleDO.setSettleTakePrice(subsequentPrice);//结算补收
        inptSettleDO.setSettleBackPrice(refundPrice.negate());//结算退款
        inptSettleDO.setSettleBackCode(Constants.ZFFS.XJ);//退款方式默认现金
        inptSettleDO.setIsSettle(Constants.SF.F);//是否结算 = 否
        if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
          inptSettleDO.setSettleTime(maxDate);// 中途结算的结算时间取最大时间
        } else {
          inptSettleDO.setSettleTime(now);//结算时间
        }
        inptSettleDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志 = 正常
        inptSettleDO.setRedId(null);//冲红id
        inptSettleDO.setIsPrint(Constants.SF.F);//是否打印 = 否
        inptSettleDO.setHospDfPrice(new BigDecimal(0));//医院垫付金额
        inptSettleDO.setHospJmPrice(new BigDecimal(0));//医院减免金额
        inptSettleDO.setOutSettleCode(null);//TODO 出院结算方式 (待确认)
        inptSettleDO.setDailySettleId(null);//日结缴款id
        inptSettleDO.setSourcePayCode(null);//TODO 支付来源方式（待确认）
        inptSettleDO.setOrderNo(null);//支付订单号
        inptSettleDO.setCrteId(inptVisitDTO.getCrteId());//创建人id
        inptSettleDO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
        inptSettleDO.setCrteTime(now);//创建时间
        inptSettleDAO.insert(inptSettleDO);

        //封装返回前端需要的参数
        JSONObject result = new JSONObject();
        result.put("totalPrice", totalPrice);//总费用
        result.put("preferentialPrice", preferentialPrice);//单据优惠
        result.put("realityPrice", realityPrice);//单据应缴
        result.put("miPrice", miPrice);//合同单位金额
        result.put("payPrice", payPrice);//预交款
        result.put("subsequentPrice", subsequentPrice);//补收
        result.put("refundPrice", refundPrice);//退款
        result.put("truncPrice", truncPrice);//舍入金额
        result.put("settleId", settleId);//结算id
        result.put("payinfo", inptInsureResult);//统筹支付信息
        result.put("settleNo", settleNo); // 结算单号
        return WrapperResponse.success("试算成功。", result);
      } catch (RuntimeException e) {
        throw e;
      } finally {
        redisUtils.del(key);
      }
    }


    /**
    * @Menthod saveAttributionSettle
    * @Desrciption 归属结算
    *
    * @Param
    * [param]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 14:32
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    @Override
    public synchronized WrapperResponse saveAttributionSettle(Map param) {
      String treatmentCode = MapUtils.get(param, "treatmentCode");
      String id = (String) param.get("id");//就诊id
      String code = param.get("userCode").toString();
      String userName = param.get("userName").toString();
      String medicalRegNo = MapUtils.get(param,"medicalRegNo");
      String hospCode = (String) param.get("hospCode");//医院编码
      String attributionCode = MapUtils.get(param,"attributionCode");
      String isMidWaySettle = MapUtils.get(param, "isMidWaySettle"); // 是否中途结算，1：是 0：否  中途结算不改变出院状态
      String key = new StringBuilder(hospCode).append(id).append(Constants.INPT_FEES_REDIS_KEY).toString();
      if (StringUtils.isNotEmpty(redisUtils.get(key))) return WrapperResponse.fail("当前患者正在结算，请稍后再试。", null);
      try {
        redisUtils.set(key, id);
        Boolean isInvoice = (Boolean) param.get("isInvoice");//是否使用发票
        String userId = (String) param.get("userId");//当前登录用户id
        OutinInvoiceDTO outinInvoiceDTO = new OutinInvoiceDTO();//发票段信息
        //校验是否使用发票，是否存在发票段（没有返回错误信息，页面给出选择发票段信息）
        if (isInvoice) {
          outinInvoiceDTO.setHospCode(hospCode);//医院编码
          outinInvoiceDTO.setUseId(userId);//发票使用人id
          List<String> typeCode = new ArrayList<String>();//票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
          Collections.addAll(typeCode, Constants.PJLX.TY, Constants.PJLX.ZY);
          outinInvoiceDTO.setTypeCodeList(typeCode);//0、全院通用；4、住院
          outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
          //校验是否有在用状态的发票段，有就返回在用的发票信outinInvoiceDTO.setStatusCode(Constants.PJSYZT.ZY);//使用状态 = 在用状态
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("hospCode", hospCode);
          map.put("outinInvoiceDTO", outinInvoiceDTO);
          List<OutinInvoiceDTO> outinInvoiceDTOS = outinInvoiceService_consumer.updateForOutinInvoiceQuery(map).getData();
          if (outinInvoiceDTOS == null || outinInvoiceDTOS.size() != 1) {
            //没有发票信息
            return WrapperResponse.info(-2, "请选择发票段", outinInvoiceDTOS);
          }
          outinInvoiceDTO = outinInvoiceDTOS.get(0);
        }
        String settleId = (String) param.get("settleId");//结算id
        //根据结算id查询本次结算信息
        InptSettleDO inptSettleDO = inptSettleDAO.selectByPrimaryKey(settleId);
        if (inptSettleDO == null) return WrapperResponse.fail("未找到结算记录信息，请重新试算。", null);
        //校验费用信息
        Map<String, Object> costParam = new HashMap<String, Object>();
        costParam.put("hospCode", hospCode);//医院编码
        costParam.put("visitId", id);//就诊id
        //costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
        costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
        costParam.put("attributionCode", attributionCode);//结算类型标志
        //costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
        List<InptCostDO> inptCostDOList = new ArrayList<>();
        // ==================中途结算，不能查询全部费用，只能查询医保已经上传时间区间的费用  2021年7月28日16:13:29=========================================
        if (isMidWaySettle != null && "1".equals(isMidWaySettle)) {
          costParam.put("isMidWaySettle", isMidWaySettle);
          inptCostDOList = inptCostDAO.queryMidWaySettleInptCostList(costParam);
        } else {
          inptCostDOList = inptCostDAO.queryInptCostList(costParam);
        }
        //获取当前患者信息参数
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);//医院编码
        inptVisitDTO.setId(id);//就诊id
        inptVisitDTO = inptVisitDAO.getInptVisitById(inptVisitDTO);
        if (inptVisitDTO == null) {
          return WrapperResponse.fail("未找到该患者信息，请刷新。", null);
        }
        Integer patientValueCode = Integer.valueOf(inptVisitDTO.getPatientCode());
        if (inptCostDOList.isEmpty() && patientValueCode > 0) {
          throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
        }
        BigDecimal realityPrice = new BigDecimal(0);//优惠后总费用
        String[] costIds = new String[inptCostDOList.size()];
        List<InptCostSettleDO> inptCostSettleDOList = new ArrayList<InptCostSettleDO>();
        int costIndex = 0;
        for (InptCostDO inptCostDO : inptCostDOList) {
          if (inptCostDO.getRealityPrice() != null) {
            realityPrice = BigDecimalUtils.add(realityPrice, inptCostDO.getRealityPrice());
            costIds[costIndex++] = inptCostDO.getId();
            InptCostSettleDO inptCostSettleDO = new InptCostSettleDO();
            inptCostSettleDO.setId(SnowflakeUtils.getId());//id
            inptCostSettleDO.setHospCode(hospCode);//医院编码
            inptCostSettleDO.setVisitId(id);//就诊id
            inptCostSettleDO.setBabyId(inptCostDO.getBabyId());//babyID
            inptCostSettleDO.setCostId(inptCostDO.getId());//费用id
            inptCostSettleDO.setSettleId(settleId);//结算id
            inptCostSettleDO.setRealityPrice(inptCostDO.getRealityPrice()); // 优惠后金额
            inptCostSettleDOList.add(inptCostSettleDO);
          }
        }
        //校验费用是否一致
        if (!BigDecimalUtils.equals(realityPrice, inptSettleDO.getRealityPrice())) {
          throw new AppException("费用发生改变，请重新试算。");
        }
        BigDecimal selfPrice = inptSettleDO.getSelfPrice();//个人支付金额

        //计算支付金额
        List<InptPayDO> inptPayDOList = (List<InptPayDO>) param.get("inptPayDOList");//支付方式
        BigDecimal actualPrice = new BigDecimal(0);//实收金额
        // 第三方支付金额
        BigDecimal thirdPartyPrice = new BigDecimal(0);
        List<InptPayDO> inptPayParam = new ArrayList<InptPayDO>();
        for (InptPayDO inptPayDO : inptPayDOList) {
          //TODO 后续考虑支付手续费
          if (StringUtils.isNotEmpty(inptPayDO.getPayCode()) && inptPayDO.getPrice() != null) {
            // 支付方式中第三方支付费用总和
            if (!inptPayDO.getPayCode().equals(Constants.ZFFS.XJ)) {
              thirdPartyPrice = BigDecimalUtils.add(thirdPartyPrice, inptPayDO.getPrice());
            }
            actualPrice = BigDecimalUtils.add(actualPrice, inptPayDO.getPrice());
            inptPayDO.setId(SnowflakeUtils.getId());//id
            inptPayDO.setHospCode(hospCode);//医院编码
            inptPayDO.setSettleId(settleId);//结算id
            inptPayDO.setVisitId(id);//就诊id
            inptPayDO.setServicePrice(inptPayDO.getServicePrice() == null ? new BigDecimal(0) : inptPayDO.getServicePrice());//手术费
            inptPayParam.add(inptPayDO);
          }
        }
        //判断支付金额是否小于需支付金额
        int greater = BigDecimalUtils.compareTo(selfPrice, actualPrice);
        if (greater > 0) {
          throw new AppException("实收金额不能小于需支付金额。");
        }
        // ====================================================================
        // 官红强  2021年1月19日15:16:23 1、如果正常收钱有找零，需要将现金支付金额减去找零金额后再保存，（前提要校验收收费金额还是退费金额）
        // 应收金额大于0，退款金额小于0，说明是收款
        if (BigDecimalUtils.greaterZero(selfPrice) && BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice())) {
          // 应收金额小于实际付款金额，则需要将现金支付金额减去多余的金额
          if (greater < 0) {
            // 如果第三方支付金额总和大于应收金额，则不允许保存
            if (BigDecimalUtils.compareTo(thirdPartyPrice, selfPrice) > 0) {
              throw new AppException("第三方支付金额总和不能大于应收金额。");
            }
            BigDecimal dif = BigDecimalUtils.subtract(selfPrice, actualPrice);
            for (int i = 0; i < inptPayParam.size(); i++) {
              // 支付方式为现金的支付方式
              if (inptPayParam.get(i).getPayCode().equals(Constants.ZFFS.XJ)) {
                // 现金支付金额减去多收的金额
                inptPayParam.get(i).setPrice(BigDecimalUtils.add(inptPayParam.get(i).getPrice(), dif));
              }
            }
          }
        }
        // 官红强  2021年1月19日15:16:23 2、如果预交金额大于实际使用金额，说明有退款，则需要保存退款的负金额记录
        if (BigDecimalUtils.greaterZero(inptSettleDO.getSettleBackPrice())) {
          // 退款支持多路径退，需要校验输入的退款总和不能大于应退金额(即输入金额总和=应退金额)
          if (!BigDecimalUtils.equals(actualPrice, inptSettleDO.getSettleBackPrice())) {
            throw new AppException("各种支付方式输入的金额总和必须等于应退金额");
          } else {
            for (int i = 0; i < inptPayParam.size(); i++) {
              // 将输入的退款金额取反
              inptPayParam.get(i).setPrice(BigDecimalUtils.negate(inptPayParam.get(i).getPrice()));
            }
          }
          inptPayDAO.batchInsert(inptPayParam);
        }
        // =======================官红强 注释=============================================
        //保存结算费用关联信息
        if (!inptCostSettleDOList.isEmpty()) {
          inptCostSettleDAO.batchInsert(inptCostSettleDOList);
        }
        //修改费用状态
        if (!inptCostDOList.isEmpty()) {
          Map<String, Object> inptCostParam = new HashMap<String, Object>();
          inptCostParam.put("settleId", settleId);//结算id
          inptCostParam.put("settleCode", Constants.JSZT.YIJS);//结算状态 = 已结算
          inptCostParam.put("hospCode", hospCode);//医院编码
          inptCostParam.put("ids", costIds);//费用id
          inptCostDAO.editInptCostByIds(inptCostParam);
        }

        //修改结算表状态
        InptSettleDO inptSettleDO1 = new InptSettleDO();
        inptSettleDO1.setId(settleId);//结算id
        inptSettleDO1.setIsSettle(Constants.SF.S);//是否结算 = 是
        inptSettleDO1.setSourcePayCode("0"); // 0：his 1：微信 2：支付宝 3：自助机
        inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO1);

        //判断是否需要发票
        if (isInvoice) { //true:打印发票生成发票信息
          Map<String, Object> map = new HashMap<String, Object>();
          outinInvoiceDTO.setSettleId(settleId);//结算id
          map.put("hospCode", hospCode);
          map.put("outinInvoiceDTO", outinInvoiceDTO);
          OutinInvoiceDetailDO outinInvoiceDetailDO = outinInvoiceService_consumer.updateInvoiceStatus(map).getData();
          //保存inpt_settle_invoice住院结算发票情况表
          InptSettleInvoiceDO inptSettleInvoiceDO = new InptSettleInvoiceDO();
          String inptSettleInvoiceId = SnowflakeUtils.getId();
          inptSettleInvoiceDO.setId(inptSettleInvoiceId);//id
          inptSettleInvoiceDO.setHospCode(hospCode);//医院编码
          inptSettleInvoiceDO.setSettleId(settleId);//结算id
          inptSettleInvoiceDO.setVisitId(id);//就诊id
          inptSettleInvoiceDO.setInvoiceId(outinInvoiceDTO.getId());//发票id
          inptSettleInvoiceDO.setInvoiceDetailId(outinInvoiceDetailDO.getId());//发票明细id
          inptSettleInvoiceDO.setInvoiceNo(String.valueOf(outinInvoiceDetailDO.getInvoiceNo()));//发票号码
          inptSettleInvoiceDO.setTotalPrice(realityPrice);//发票总金额
          inptSettleInvoiceDO.setPrintId(null);//发票打印人id
          inptSettleInvoiceDO.setPrintName(null);//发票打印人姓名
          inptSettleInvoiceDO.setPrintTime(null);//发票打印时间
          inptSettleInvoiceDO.setPrintNum(0);//打印次数
          inptSettleInvoiceDO.setStatusCode(Constants.ZTBZ.ZC);//状态标志代码（ZTBZ）
          inptSettleInvoiceDO.setRedId(null);//冲红id
          inptSettleInvoiceDAO.insertSelective(inptSettleInvoiceDO);//保存住院结算发票情况表

          //保存inpt_settle_invoice_content住院结算发票内容表
          InptSettleInvoiceContentDO inptSettleInvoiceContentDO = new InptSettleInvoiceContentDO();
          inptSettleInvoiceContentDO.setId(SnowflakeUtils.getId());//id
          inptSettleInvoiceContentDO.setHospCode(hospCode);//医院编码
          inptSettleInvoiceContentDO.setSettleInvoiceId(inptSettleInvoiceId);//结算发票ID（inpt_settle_invoice）
          inptSettleInvoiceContentDO.setInCode(null);//住院发票代码
          inptSettleInvoiceContentDO.setInName(null);//住院发票名称
          inptSettleInvoiceContentDO.setRealityPrice(realityPrice);//优惠后总金额
          inptSettleInvoiceContentDAO.insertSelective(inptSettleInvoiceContentDO);//保存住院结算发票内容表
        }
        // 官红强修改， 2021年1月19日14:50:34 如果个人自付为0，意味着有预交金额退款，此时不再写一遍支付信息,如果自付与预交刚好相等，需要写一次记录（即自付为0，退款为0）
        if (!inptPayParam.isEmpty() && (!BigDecimalUtils.isZero(selfPrice) || BigDecimalUtils.isZero(inptSettleDO.getSettleBackPrice()))) {
          //保存inpt_pay 住院结算支付方式表
          inptPayDAO.batchInsert(inptPayParam);
        }
        return WrapperResponse.success("支付成功。", null);
      } catch (RuntimeException e) {
        throw e;
      } finally {
        redisUtils.del(key);
      }
    }

//    public static void main(String args[]) {
//
//        String resultJson = "{\n" +
//                "    \"output\": {\n" +
//                "        \"setlinfo\": {\n" +
//                "            \"setl_time\": \"2021-10-27 10:59:23\", \n" +
//                "            \"cvlserv_pay\": 0, \n" +
//                "            \"cvlserv_flag\": \"0\", \n" +
//                "            \"med_type\": \"2101\", \n" +
//                "            \"naty\": \"\", \n" +
//                "            \"brdy\": \"1967-11-16\", \n" +
//                "            \"psn_cash_pay\": 2685.99, \n" +
//                "            \"certno\": \"430521196711161676\", \n" +
//                "            \"hifmi_pay\": 0, \n" +
//                "            \"psn_no\": \"43000011300000261123\", \n" +
//                "            \"act_pay_dedc\": 800, \n" +
//                "            \"mdtrt_cert_type\": \"02\", \n" +
//                "            \"balc\": 7748.98, \n" +
//                "            \"medins_setl_id\": \"H43050200156202110271059228684\", \n" +
//                "            \"psn_cert_type\": \"01\", \n" +
//                "            \"hifob_pay\": 0, \n" +
//                "            \"acct_mulaid_pay\": 0, \n" +
//                "            \"clr_way\": \"01\", \n" +
//                "            \"oth_pay\": 0, \n" +
//                "            \"medfee_sumamt\": 5792.53, \n" +
//                "            \"hifes_pay\": 0, \n" +
//                "            \"gend\": \"1\", \n" +
//                "            \"mdtrt_id\": \"17648713\", \n" +
//                "            \"fund_pay_sumamt\": 3106.54, \n" +
//                "            \"acct_pay\": 0, \n" +
//                "            \"fulamt_ownpay_amt\": 296.2, \n" +
//                "            \"setl_id\": \"11007999\", \n" +
//                "            \"hosp_part_amt\": 0, \n" +
//                "            \"psn_name\": \"周跃军\", \n" +
//                "            \"insutype\": \"310\", \n" +
//                "            \"inscp_scp_amt\": 4973.72, \n" +
//                "            \"maf_pay\": 0, \n" +
//                "            \"psn_part_amt\": 2685.99, \n" +
//                "            \"pool_prop_selfpay\": 0, \n" +
//                "            \"clr_optins\": \"430502\", \n" +
//                "            \"psn_type\": \"11\", \n" +
//                "            \"overlmt_selfpay\": 0, \n" +
//                "            \"hifp_pay\": 3106.54, \n" +
//                "            \"preselfpay_amt\": 522.61, \n" +
//                "            \"age\": 53, \n" +
//                "            \"clr_type\": \"9902\"\n" +
//                "        }, \n" +
//                "        \"setldetail\": [\n" +
//                "            {\n" +
//                "                \"fund_pay_type\": \"\", \n" +
//                "                \"fund_payamt\": \"\", \n" +
//                "                \"setl_proc_info\": \"\", \n" +
//                "                \"crt_payb_lmt_amt\": \"\", \n" +
//                "                \"inscp_scp_amt\": 4973.72, \n" +
//                "                \"fund_pay_type_name\": \"\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }, \n" +
//                "    \"infcode\": 0, \n" +
//                "    \"refmsg_time\": \"20211027105922157\", \n" +
//                "    \"message\": \"\", \n" +
//                "    \"respond_time\": \"20211027105924105\", \n" +
//                "    \"inf_refmsgid\": \"430000202110271059240128334095\"\n" +
//                "}";
//        Map<String, Object> item1 = JSONObject.parseObject(resultJson,Map.class);
//        Map<String, Object> item2 = (Map<String, Object>) item1.get("output");
//        //List<Map<String,Object>> setldetailList = MapUtils.get(item2,"setldetail");
//        //MinsureSettleId = item2.get("setl_id"); // 结算id
//        //String medicalRegNo = item2.get("mdtrt_id").toString(); // 医保返回的就诊id
//        String omsgid = MapUtils.get(item2, "omsgid"); // 交易信息(原交易)中的msgid,发送方报文ID
//        String oinfno = MapUtils.get(item2, "oinfno"); // 交易信息(原交易)中的infno
//        //String visitId = item2.getId();
//        String clrOptins = MapUtils.get(item2, "clr_optins");
//        String clrWay = MapUtils.get(item2, "clr_way");
//        String clrType = MapUtils.get(item2, "clr_type");
//        BigDecimal hospExemAmount = MapUtils.get(item2, "hospExemAmount");
//
//        BigDecimal acctPay = MapUtils.get(item2,"acct_pay"); // 个人账户支出
//        /**
//         * 结算成功以后 更新基金信息
//         */
//        List<Map<String, Object>> setldetailList = MapUtils.get(item2, "setldetail");
//        if (!ListUtils.isEmpty(setldetailList)) {
//            InsureIndividualFundDTO insureIndividualFundDTO = null;
//            List<InsureIndividualFundDTO> fundDTOList = new ArrayList<>();
//            for (Map<String, Object> item : setldetailList) {
//                insureIndividualFundDTO = new InsureIndividualFundDTO();
//                insureIndividualFundDTO.setId(SnowflakeUtils.getId());
//                //insureIndividualFundDTO.setHospCode(hospCode);
//                //insureIndividualFundDTO.setInsureSettleId(insureSettleId);
//                //insureIndividualFundDTO.setVisitId(visitId);
//                //insureIndividualFundDTO.setCrteName(userName);
//                //insureIndividualFundDTO.setCrteId(userId);
//                insureIndividualFundDTO.setCrteTime(DateUtils.getNow());
//                insureIndividualFundDTO.setMibId(null);
//                insureIndividualFundDTO.setFundName(null);
//                insureIndividualFundDTO.setIndiFreezeStatus(null);
//                // 基金支付类型
//                insureIndividualFundDTO.setFundPayType(MapUtils.get(item, "fund_pay_type"));
//                if (MapUtils.isEmpty(item, "inscp_scp_amt")) {
//                    insureIndividualFundDTO.setInscpScpAmt(null);
//                } else {
//                    insureIndividualFundDTO.setInscpScpAmt(MapUtils.get(item, "inscp_scp_amt"));
//                }
//                // 符合政策范围金额
//                // 本次可支付限额金额
//                if (MapUtils.isEmpty(item, "crt_payb_lmt_amt")) {
//                    insureIndividualFundDTO.setCrtPaybLmtAmt(null);
//                } else {
//                    insureIndividualFundDTO.setCrtPaybLmtAmt(MapUtils.get(item, "crt_payb_lmt_amt"));
//                }
//                if (MapUtils.isEmpty(item, "fund_payamt")) {
//                    insureIndividualFundDTO.setFundPayamt(null);
//                } else {
//                    // 基金支付金额
//                    insureIndividualFundDTO.setFundPayamt(MapUtils.get(item, "fund_payamt"));
//                }
//                // 基金支付类型名称
//                insureIndividualFundDTO.setFundPayTypeName(MapUtils.get(item, "fund_pay_type_name"));
//                //结算过程信息
//                insureIndividualFundDTO.setSetlProcInfo(MapUtils.get(item, "setl_proc_info"));
//                fundDTOList.add(insureIndividualFundDTO);
//            }
//            //isInsureUnifiedMap.put("fundDTOList", fundDTOList);
//            //System.out.println(isInsureUnifiedMap);
//            //insureIndividualSettleService.insertBatchFund(isInsureUnifiedMap).getData();
//        }
//
//        /**
//         * 根据 就诊id， 状态为正常的数据 ，医院编码查询医保结算表的数据
//         */
//        InsureIndividualSettleDTO settleDTO = new InsureIndividualSettleDTO();
//        //settleDTO.setVisitId(visitId);
//        //settleDTO.setHospCode(hospCode);
//        //settleDTO.setSettleId(settleId);
//        settleDTO.setState("0");
//        Map<String, Object> dataMap = new HashMap<>();
//        //dataMap.put("hospCode", hospCode);
//        //dataMap.put("insureIndividualSettleDTO", settleDTO);
//        //settleDTO = insureIndividualSettleService.findByCondition(dataMap);
//        InsureIndividualSettleDO individualSettleDO = new InsureIndividualSettleDO();
//        /**
//         * 根据查询回来的医保结算主键id,医院编码  更新医保结算id ，就医登记号
//         */
//       // individualSettleDO.setHospCode(hospCode);
//        //individualSettleDO.setId(settleDTO.getId());
//        //individualSettleDO.setInsureSettleId(insureSettleId);
//        //individualSettleDO.setMedicalRegNo(medicalRegNo);
//        //individualSettleDO.setVisitId(visitId);
//        individualSettleDO.setOinfno(oinfno);
//        individualSettleDO.setHospExemAmount(hospExemAmount);
//        individualSettleDO.setOmsgid(omsgid);
//        Map<String, Object> map = new HashMap<>();
//        //map.put("hospCode", hospCode);
//        map.put("insureIndividualSettleDO", individualSettleDO);
//        //InsureIndividualCostDTO costDTO = new InsureIndividualCostDTO();
//        //costDTO.setHospCode(hospCode);
//        //costDTO.setVisitId(visitId);
//        //costDTO.setCostList(inptCostDOList);
//        //costDTO.setInsureSettleId(insureSettleId);
//        //map.put("insureIndividualCostDTO", costDTO);
//        //insureIndividualCostService.editInsureCostByCostIDS(map); // 更新费用信息
//        individualSettleDO.setClrWay(clrWay);
//        individualSettleDO.setClrType(clrType);
//        individualSettleDO.setClrOptins(clrOptins);
//        //insureIndividualSettleService.updateByPrimaryKeySelective(map); // 更新结算信息
//        //map.put("medicalRegNo",medicalRegNo);
//        //map.put("id",visitId);
//        //map.put("insureSettleId",insureSettleId);
//        //inptVisitDTO.setHospCode(hospCode);
//        //inptVisitDTO.setId(visitId);
//        //inptVisitDTO.setInsureSettleId(insureSettleId);
//        //inptVisitDTO.setMedicalRegNo(medicalRegNo);
//        //map.put("inptVisitDTO",inptVisitDTO);
//        //insureIndividualVisitService.updateInsureInidivdual(map);  // 更新就诊信息
//
//        //InptSettleDO inptSettleDO = new InptSettleDO();
//        //inptSettleDO.setId(settleId);
//        //inptSettleDO.setAcctPay(acctPay);
//        //inptSettleDO.setHospCode(hospCode);
//        System.out.println("---------------------"+ acctPay);
//        //inptSettleDAO.updateByPrimaryKeySelective(inptSettleDO);
//
//        System.out.println("ok");
//    }

    /**
     * @Description 拼装明细审核入参
     * @Author 产品三部-郭来
     * @Date 2022-05-09 15:37
     * @return cn.hsa.module.insure.module.dto.AnalysisDTO
     */
    public AnalysisDTO initAnalysisDTO(InptVisitDTO inptVisitDTO,InsureIndividualVisitDTO insureVisitDTO){
        if (ObjectUtil.isEmpty(inptVisitDTO)) {
            throw new AppException("入参不能为空！");
        }

        List<AnaOrderDTO> anaOrderDTOS = new ArrayList<>();
        List<Map<String, Object>> insureCostList = inptVisitDTO.getInsureCostList();
        for (Map<String, Object> map : insureCostList) {
            AnaOrderDTO anaOrderDTO = new AnaOrderDTO();
            //*处方(医嘱)标识
            anaOrderDTO.setRxId(MapUtil.getStr(map,"id"));
            //*处方号
            anaOrderDTO.setRxno(ObjectUtil.isEmpty(MapUtil.getStr(map,"iatId"))?MapUtil.getStr(map,"id"):MapUtil.getStr(map,"iatId"));
            //组编号
            anaOrderDTO.setGrpno(MapUtil.getStr(map,"iatdGroupNo"));
            //*是否为长期医嘱  1:是   0：否
            anaOrderDTO.setLongDrordFlag("0".equals(MapUtil.getStr(map,"isLong"))?"1":"0");
            //*目录类别
            anaOrderDTO.setHilistType(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemType"))?"101":MapUtil.getStr(map,"insureItemType"));
            //*收费类别
            anaOrderDTO.setChrgType(ObjectUtil.isEmpty(MapUtil.getStr(map,"chrgType"))?"1":MapUtil.getStr(map,"chrgType"));
            //*医嘱行为
            anaOrderDTO.setDrordBhvr("-");
            //*医保目录代码
            anaOrderDTO.setHilistCode(MapUtil.getStr(map,"insureItemCode"));
            //*医保目录名称
            anaOrderDTO.setHilistName(MapUtil.getStr(map,"insureItemName"));
            //医保目录(药品)剂型
            anaOrderDTO.setHilistDosform(MapUtil.getStr(map,"insureItemPrepCode"));
            //*医保目录等级
            anaOrderDTO.setHilistLv(ObjectUtil.isEmpty(MapUtil.getStr(map,"hilistLv"))?"1":MapUtil.getStr(map,"hilistLv"));
            //*医保目录价格

            anaOrderDTO.setHilistPric(ObjectUtil.isEmpty(MapUtil.getStr(map,"insureItemPrice"))?BigDecimal.ZERO:new BigDecimal(MapUtil.getStr(map,"insureItemPrice")));
            //医保目录备注
            anaOrderDTO.setHilistMemo("");
            //*医院目录代码
            anaOrderDTO.setHosplistCode(MapUtil.getStr(map,"hospItemCode"));
            //*医院目录名称
            anaOrderDTO.setHosplistName(MapUtil.getStr(map,"hospItemName"));
            //医院目录(药品)剂型
            anaOrderDTO.setHosplistDosform(MapUtil.getStr(map,"hospItemPrepCode"));
            //*数量
            anaOrderDTO.setCnt(BigDecimalUtils.convert(MapUtil.getStr(map,"num")));
            //*单价
            anaOrderDTO.setPric(BigDecimalUtils.convert(MapUtil.getStr(map,"price")));
            //*总费用
            anaOrderDTO.setSumamt(BigDecimalUtils.convert(MapUtil.getStr(map,"totalPrice")));
            //*自费金额
            anaOrderDTO.setOwnpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"fulamtOwnpayAmt")));
            //*自付金额
            anaOrderDTO.setSelfpayAmt(BigDecimalUtils.convert(MapUtil.getStr(map,"preselfpayAmt")));
            //*规格
            anaOrderDTO.setSpec(ObjectUtil.isEmpty(MapUtil.getStr(map,"spec"))?"-":MapUtil.getStr(map,"spec"));
            //*数量单位
            anaOrderDTO.setSpecUnt(ObjectUtil.isEmpty(MapUtil.getStr(map,"numUnitCode"))?"-":MapUtil.getStr(map,"numUnitCode"));
            //*医嘱开始日期
            anaOrderDTO.setDrordBegnDate(ObjectUtil.isEmpty(MapUtil.getDate(map,"longStartTime"))?MapUtil.getDate(map,"costTime"):MapUtil.getDate(map,"longStartTime"));
            //*下达医嘱的科室标识
            anaOrderDTO.setDrordDeptCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptId"))?"-":MapUtil.getStr(map,"execDeptId"));
            //*下达医嘱科室名称
            anaOrderDTO.setDrordDeptName(ObjectUtil.isEmpty(MapUtil.getStr(map,"execDeptName"))?"-":MapUtil.getStr(map,"execDeptName"));
            //*开处方(医嘱)医生标识
            anaOrderDTO.setDrordDrCodg(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteId"))?"-":MapUtil.getStr(map,"crteId"));
            //*开处方(医嘱)医生姓名
            anaOrderDTO.setDrordDrName(ObjectUtil.isEmpty(MapUtil.getStr(map,"crteName"))?"-":MapUtil.getStr(map,"crteName"));
            //*开处方(医嘱)医职称
            anaOrderDTO.setDrordDrProfttl("-");
            //*是否当前处方(医嘱)  1=是,0=否
            anaOrderDTO.setCurrDrordFlag("1");
            anaOrderDTOS.add(anaOrderDTO);
        }
        //诊断信息DTO
        List<AnaDiagnoseDTO> anaDiagnoseDTOS = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("inptVisitDTO",inptVisitDTO);
        map.put("hospCode",inptVisitDTO.getHospCode());
        PageDTO diaginfo = queryDiagnose(inptVisitDTO);
        if (ObjectUtil.isEmpty(diaginfo) || ObjectUtil.isEmpty(diaginfo.getResult())) {
            throw new AppException("未查询到诊断信息！");
        }
        List<InptDiagnoseDTO> inptDiagnoseDTOS = JSON.parseArray(JSON.toJSONString(diaginfo.getResult()), InptDiagnoseDTO.class);
        for (InptDiagnoseDTO inptDiagnoseDTO : inptDiagnoseDTOS) {
            AnaDiagnoseDTO diagnoseDTO = new AnaDiagnoseDTO();
            //*诊断标识
            diagnoseDTO.setDiseId(inptDiagnoseDTO.getId());
            //*出入诊断类别
            diagnoseDTO.setInoutDiseType("1");
            //*主诊断标志
            diagnoseDTO.setMaindiseFlag(inptDiagnoseDTO.getIsMain());
            //*诊断排序号
            diagnoseDTO.setDiasSrtNo(ObjectUtil.isEmpty(inptDiagnoseDTO.getDiagnoseNo())? BigDecimal.ZERO:new BigDecimal(inptDiagnoseDTO.getDiagnoseNo()));
            //*诊断(疾病)编码
            diagnoseDTO.setDiseCodg(inptDiagnoseDTO.getDiseaseCode());
            //*诊断(疾病)名称
            diagnoseDTO.setDiseName(inptDiagnoseDTO.getDiseaseName());
            //*诊断日期
            diagnoseDTO.setDiseDate(inptDiagnoseDTO.getCrteTime());
            //*诊断类目  --海南必传
            diagnoseDTO.setDiseCgy("1");
            anaDiagnoseDTOS.add(diagnoseDTO);
        }
        //就诊信息
        InsureIndividualBasicDTO basicInParm = new InsureIndividualBasicDTO();
        basicInParm.setVisitId(inptVisitDTO.getId());
        basicInParm.setHospCode(inptVisitDTO.getHospCode());
        basicInParm.setMedicalRegNo(inptVisitDTO.getMedicalRegNo());
        Map<String, Object> map2 = new HashMap<>();
        map2.put("insureIndividualBasicDTO",basicInParm);
        map2.put("hospCode",inptVisitDTO.getHospCode());
        WrapperResponse<InsureIndividualBasicDTO> res = InsureIndividualBasicServiceConsumer.getByVisitId(map2);
        if (WrapperResponse.SUCCESS != res.getCode()) {
            throw new AppException("查询医保参保信息失败！"+res.getMessage());
        }
        if (ObjectUtil.isEmpty(res.getData())) {
            throw new AppException("未查询到医保参保信息！");
        }
        InsureIndividualBasicDTO basicDTO = res.getData();
        //查询住院信息
        Map<String, Object> map1 = new HashMap<>();
        map1.put("inptVisitDTO",inptVisitDTO);
        map1.put("hospCode",inptVisitDTO.getHospCode());
        WrapperResponse<InptVisitDTO> response1 = compositeQueryService.queryInptVisit(map1);
        if (WrapperResponse.SUCCESS != response1.getCode()) {
            throw new AppException("查询住院信息失败！"+response1.getMessage());
        }
        if (ObjectUtil.isEmpty(response1.getData())) {
            throw new AppException("未查询到住院信息！");
        }
        InptVisitDTO mdtrtDTO = response1.getData();
        AnaMdtrtDTO anaMdtrtDTO = new AnaMdtrtDTO();
        //*就诊标识
        anaMdtrtDTO.setMdtrtId(mdtrtDTO.getId());
        //*医疗服务机构标识
        anaMdtrtDTO.setMedinsId(mdtrtDTO.getHospCode());
        //*医疗机构名称
        anaMdtrtDTO.setMedinsName("-");
        //*医疗机构行政区划编码
        anaMdtrtDTO.setMedinsAdmdvs(insureVisitDTO.getInsureRegCode());
        //*医疗服务机构类型
        anaMdtrtDTO.setMedinsType("1");
        //*医疗机构等级
        anaMdtrtDTO.setMedinsLv("02");
        //病区标识
        anaMdtrtDTO.setWardareaCodg("");
        //病房号
        anaMdtrtDTO.setWardno("");
        //病床号
        anaMdtrtDTO.setBedno("");
        //*入院日期
        anaMdtrtDTO.setAdmDate(mdtrtDTO.getInTime());
        //*出院日期
        anaMdtrtDTO.setDscgDate(ObjectUtil.isEmpty(mdtrtDTO.getOutTime())?mdtrtDTO.getInTime():mdtrtDTO.getOutTime());
        //*主诊断编码
        anaMdtrtDTO.setDscgMainDiseCodg(insureVisitDTO.getVisitIcdCode());
        //*主诊断名称
        anaMdtrtDTO.setDscgMainDiseName(insureVisitDTO.getVisitIcdName());
        //*医师标识
        anaMdtrtDTO.setDrCodg(mdtrtDTO.getZzDoctorId());
        //*入院科室标识
        anaMdtrtDTO.setAdmDeptCodg(mdtrtDTO.getInDeptId());
        //*入院科室名称
        anaMdtrtDTO.setAdmDeptName(mdtrtDTO.getInDeptName());
        //*出院科室标识
        anaMdtrtDTO.setDscgDeptCodg(ObjectUtil.isEmpty(mdtrtDTO.getOutDeptId())?mdtrtDTO.getInDeptId():mdtrtDTO.getOutDeptId());
        //*出院科室名称
        anaMdtrtDTO.setDscgDeptName(ObjectUtil.isEmpty(mdtrtDTO.getOutDeptName())?mdtrtDTO.getInDeptName():mdtrtDTO.getOutDeptName());
        //*就诊类型
        anaMdtrtDTO.setMedMdtrtType("210");//住院写死210
        //*医疗类别
        anaMdtrtDTO.setMedType("21");
        //*生育状态
        anaMdtrtDTO.setMatnStas("0");
        //*总费用
        anaMdtrtDTO.setMedfeeSumamt(new BigDecimal("0"));
        //*自费金额
        anaMdtrtDTO.setOwnpayAmt(new BigDecimal("0"));
        //*自付金额
        anaMdtrtDTO.setSelfpayAmt(new BigDecimal("0"));
        //个人账户支付金额
        anaMdtrtDTO.setMaAmt(new BigDecimal("0"));
        //救助金支付金额
        anaMdtrtDTO.setAcctPayamt(new BigDecimal("0"));
        //统筹金支付金额
        anaMdtrtDTO.setHifpPayamt(new BigDecimal("0"));
        //*结算总次数
        anaMdtrtDTO.setSetlTotlnum(new BigDecimal("1"));
        //*险种
        anaMdtrtDTO.setInsutype(basicDTO.getAae140());
        //*报销标志
        anaMdtrtDTO.setReimFlag("0");
        //*异地结算标志
        anaMdtrtDTO.setOutSetlFlag(ObjectUtil.isEmpty(inptVisitDTO.getIsOut())?"0":inptVisitDTO.getIsOut());
        //处方(医嘱)信息
        anaMdtrtDTO.setFsiOrderDtos(anaOrderDTOS);
        //诊断信息DTO
        anaMdtrtDTO.setFsiDiagnoseDtos(anaDiagnoseDTOS);
        //公务员标志  --海南必传
        anaMdtrtDTO.setCvlservFlag(ObjectUtil.isNotEmpty(basicDTO.getBac001())?basicDTO.getBac001():"0");
        anaMdtrtDTO.setOrderDtos(anaOrderDTOS);
        anaMdtrtDTO.setDiagnoseDtos(anaDiagnoseDTOS);

        //参保信息
        AnaInsuDTO anaInsuDTO = new AnaInsuDTO();
        //*参保人标识
        anaInsuDTO.setPatnId(basicDTO.getAac001());
        //*姓名
        anaInsuDTO.setPatnName(basicDTO.getAac003());
        //*性别
        anaInsuDTO.setGend(basicDTO.getAac004());
        //*出生日期
        anaInsuDTO.setBrdy(DateUtils.parse(basicDTO.getAac006(), DatePattern.NORM_DATE_PATTERN));
        //*统筹区编码
        anaInsuDTO.setPoolarea(insureVisitDTO.getInsuplcAdmdvs());
        //*当前就诊标识
        anaInsuDTO.setCurrMdtrtId(mdtrtDTO.getId());
        //*就诊信息集合
        anaInsuDTO.setFsiEncounterDtos(anaMdtrtDTO);
        //*就诊信息集合  --海南
        anaInsuDTO.setEncounterDtos(anaMdtrtDTO);

        //入参DTO
        AnalysisDTO analysisDTO = new AnalysisDTO();
        //*系统编码
        analysisDTO.setSyscode("YYDS");
        //任务ID
        analysisDTO.setTaskId("");
        //触发场景
        analysisDTO.setTrigScen("9");
        //规则标识集合
        analysisDTO.setRuleIds("");
        //*参保人信息
        analysisDTO.setPatientDtos(anaInsuDTO);

        return analysisDTO;
    }
}

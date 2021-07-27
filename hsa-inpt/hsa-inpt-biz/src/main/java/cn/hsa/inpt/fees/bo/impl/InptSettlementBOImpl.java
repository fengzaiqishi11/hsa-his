package cn.hsa.inpt.fees.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.advancepay.dao.InptAdvancePayDAO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dao.InptBabyDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.inpt.fees.bo.InptSettlementBO;
import cn.hsa.module.inpt.fees.dao.*;
import cn.hsa.module.inpt.fees.entity.*;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
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
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
    private InsureUnifiedPayInptService insureUnifiedPayInptService;

    @Resource
    private InptBabyDAO inptBabyDAO;

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
        return WrapperResponse.success(PageDTO.of(inptVisitDAO.queryInptVisitList(inptVisitDTO)));
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
        String hospCode = inptVisitDTO.getHospCode();//医院编码
        String treatmentCode = inptVisitDTO.getTreatmentCode();
        //《========新生婴儿试算========》
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
            costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            //获取婴儿费用信息
            List<InptCostDO> inptBabyCostDOList = inptCostDAO.queryInptCostList(costParam);
            if (inptBabyCostDOList!=null&&inptBabyCostDOList.size()>0){
                InptCostDO inptCostDO=inptBabyCostDOList.get(0);
                if (inptCostDO.getSettleCode().equals("0")){
                    return WrapperResponse.fail("请先结算婴儿费用，再结算大人费用", null);
                }
            }
        }
        //《========新生婴儿试算========》

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
            costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            //if (inptCostDOList.isEmpty()){throw new AppException("该患者没有产生费用信息。");}
            if (inptCostDOList.isEmpty() && !Constants.BRLX.PTBR.equals(inptVisitDTO1.getPatientCode())) {
                throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
            }
            for (InptCostDO dto : inptCostDOList) {
                if (dto.getIsOk().equals("0")) {
                    throw new AppException("该患者有还未确费的LIS或PASS检查，请先确费。");
                }
            }

            //校验医保费用是否传输
            if (!Constants.BRLX.PTBR.equals(inptVisitDTO1.getPatientCode())) {
                Map<String, String> insureCostParam = new HashMap<String, String>();
                insureCostParam.put("hospCode", hospCode);//医院编码
                insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
                insureCostParam.put("visitId", id);//就诊id
                insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
                insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
                insureCostParam.put("queryBaby","N");
                List<Map<String, Object>> insureCostList = insureIndividualCostService.queryInsureCostByVisit(insureCostParam);
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
                insureVisitParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
                List<InsureIndividualVisitDTO> insureIndividualVisitDTOS = insureIndividualVisitService.findByCondition(insureVisitParam);
                if (insureIndividualVisitDTOS == null || insureIndividualVisitDTOS.size() > 1 || insureIndividualVisitDTOS.size() == 0) {
                    throw new AppException("未获取到医保就诊信息。");
                }
                insureIndividualVisitDTO = insureIndividualVisitDTOS.get(0);

                // 是否使用个人账户
                Map<String, Object> isInsureUnifiedMap = new HashMap<>();
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
                isInsureUnifiedMap = new HashMap<>();
                isInsureUnifiedMap.put("hospCode", hospCode);
                isInsureUnifiedMap.put("code", "UNIFIED_PAY");
                SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
                /**
                 * 试算之前  先办理出院登记
                 * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
                 */
                if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
                    Map<String, Object> unifiedMap = new HashMap<>();
                    unifiedMap.put("hospCode", hospCode);
                    unifiedMap.put("visitId", id);
                    unifiedMap.put("code", code);
                    unifiedMap.put("userName", inptVisitDTO.getCrteName());
                    unifiedMap.put("inptVisit", inptVisitDTO1);
                    unifiedMap.put("inptVisitDTO",inptVisitDTO1);
                    insureIndividualVisitDTO.setIsOut(Constants.SF.S);
                    unifiedMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
                    List<InsureIndividualVisitDTO> byCondition = insureIndividualVisitService.findByCondition(unifiedMap);
                    if(ListUtils.isEmpty(byCondition)){
                        throw new AppException("请先做医保出院登记操作,再进行试算操作");
                    }
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

                //TODO 保存医保试算接口返回的报销信息
                String akb020 = inptInsureResult.get("akb020");//医疗机构编码
                String aaz217 = inptInsureResult.get("aaz217");//医保登记号
                BigDecimal aka151 = BigDecimalUtils.convert(inptInsureResult.get("aka151"));//起付线费用
                BigDecimal akb066 = BigDecimalUtils.convert(inptInsureResult.get("akb066"));//个人账户支付
                BigDecimal akb067 = BigDecimalUtils.convert(inptInsureResult.get("akb067"));//个人现金支付
                BigDecimal akc264 = BigDecimalUtils.convert(inptInsureResult.get("akc264"));//医疗总费用akc264 = bka831 + bka832+bka842
                BigDecimal ake026 = BigDecimalUtils.convert(inptInsureResult.get("ake026"));//企业补充医疗保险基金支付
                BigDecimal ake029 = BigDecimalUtils.convert(inptInsureResult.get("ake029"));//大额医疗费用补助基金支付
                BigDecimal ake035 = BigDecimalUtils.convert(inptInsureResult.get("ake035"));//公务员医疗补助基金支付
                BigDecimal ake039 = BigDecimalUtils.convert(inptInsureResult.get("ake039"));//医疗保险统筹基金支付
                BigDecimal bka801 = BigDecimalUtils.convert(inptInsureResult.get("bka801"));//床位费超额金额
                BigDecimal bka821 = BigDecimalUtils.convert(inptInsureResult.get("bka821"));//民政救助金支付
                BigDecimal bka825 = BigDecimalUtils.convert(inptInsureResult.get("bka825"));//全自费费用
                BigDecimal bka826 = BigDecimalUtils.convert(inptInsureResult.get("bka826"));//部分自费费用
                BigDecimal bka831 = BigDecimalUtils.convert(inptInsureResult.get("bka831"));//个人自付bka831 = akb067 + akb066
                BigDecimal bka838 = BigDecimalUtils.convert(inptInsureResult.get("bka838"));//超共付段费用个人自付
                BigDecimal bka839 = BigDecimalUtils.convert(inptInsureResult.get("bka839"));//其他支付
                BigDecimal bka840 = BigDecimalUtils.convert(inptInsureResult.get("bka840"));//其他基金支付
                BigDecimal bka841 = BigDecimalUtils.convert(inptInsureResult.get("bka841"));//单位支付
                BigDecimal bka842 = BigDecimalUtils.convert(inptInsureResult.get("bka842"));//医院垫付
                BigDecimal bka843 = BigDecimalUtils.convert(inptInsureResult.get("bka843"));//特惠保
                BigDecimal bka844 = BigDecimalUtils.convert(inptInsureResult.get("bka844"));//医院减免
                BigDecimal bka845 = BigDecimalUtils.convert(inptInsureResult.get("bka845"));//政府兜底
                BigDecimal bka832 = BigDecimalUtils.convert(inptInsureResult.get("bka832"));//医保支付金额
                //医保支付金额 = 医疗总费用 - 医保个人自付
                miPrice = BigDecimalUtils.subtract(akc264, bka831);

                //单病种特殊处理
                if ("121".equals(insureIndividualVisitDTO.getBka006())) {
                    //总费用 - 医保传输过去的总费用 = 医保不报销项目总费用； 医保不报销项目总费用 + 个人现金需支付金额 = 单病种需支付金额
                    singlePrice = BigDecimalUtils.add(BigDecimalUtils.subtract(realityPrice, akc264), akb067);
                }

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
                insureIndividualSettleDO.setTotalPrice(akc264);// 本次医疗总费用
                insureIndividualSettleDO.setInsurePrice(miPrice);//医保支付
                insureIndividualSettleDO.setPlanPrice(ake039);//统筹基金支付
                insureIndividualSettleDO.setSeriousPrice(ake029);//大病互助支付
                insureIndividualSettleDO.setCivilPrice(ake035);//公务员补助支付
                insureIndividualSettleDO.setRetirePrice(ake026);//离休基金支付
                insureIndividualSettleDO.setPersonalPrice(akb066);//个人账户支付
                insureIndividualSettleDO.setPersonPrice(akb067);//个人支付
                insureIndividualSettleDO.setHospPrice(bka842);//医院支付
                insureIndividualSettleDO.setBeforeSettle(bacu18);//结算前账户余额
                insureIndividualSettleDO.setLastSettle(BigDecimalUtils.isZero(bacu18) ? bacu18 : BigDecimalUtils.greater(bka831, bacu18) ? new BigDecimal(0) : BigDecimalUtils.subtract(bacu18, akb066));//结算后账户余额
                insureIndividualSettleDO.setRestsPrice(bka839);//其他支付
                insureIndividualSettleDO.setAssignPrice(null);//指定账户支付金额
                insureIndividualSettleDO.setStartingPrice(aka151);//起付线金额
                insureIndividualSettleDO.setTopPrice(null);//超封顶线金额
                insureIndividualSettleDO.setPlanAccountPrice(bka838);//统筹段自负金额
                insureIndividualSettleDO.setPortionPrice(bka826);//部分自付金额
                insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
                insureIndividualSettleDO.setSettleState(Constants.YBJSZT.SS);//医保结算状态;0试算，1结算
                insureIndividualSettleDO.setCostbatch(null);//费用批次
                insureIndividualSettleDO.setAka130(inptVisitDTO1.getInsureBizCode());//业务类型
                insureIndividualSettleDO.setBka006(inptVisitDTO1.getInsureTreatCode());//待遇类型
                insureIndividualSettleDO.setInjuryBorthSn(null);//业务申请号,门诊特病，工伤，生育
                insureIndividualSettleDO.setIsAccount(BigDecimalUtils.isZero(akb066) ? Constants.SF.F : Constants.SF.S);//当前结算是否使用个人账户;0是，1否
                insureIndividualSettleDO.setRemark(null);//备注
                insureIndividualSettleDO.setCrteId(inptVisitDTO.getCrteId());//创建人ID
                insureIndividualSettleDO.setCrteName(inptVisitDTO.getCrteName());//创建人姓名
                insureIndividualSettleDO.setCrteTime(new Date());//创建时间
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
                minDate = inptCostDOList.get(0).getCrteTime(); //费用开始时间
                maxDate = inptCostDOList.get(inptCostDOList.size() - 1).getCrteTime(); //费用结束时间
            }
            //封装结算信息
            InptSettleDO inptSettleDO = new InptSettleDO();
            inptSettleDO.setId(settleId);//结算id
            inptSettleDO.setHospCode(hospCode);//医院编码
            inptSettleDO.setVisitId(id);//就诊id
            inptSettleDO.setBabyId(null);//婴儿id
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
        String hospCode = (String) param.get("hospCode");//医院编码
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
            costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
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
            if (inptCostDOList.isEmpty() && !Constants.BRLX.PTBR.equals(inptVisitDTO.getPatientCode())) {
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
            //根据就诊id修改inpt_visit（患者就诊表）当前状态代码 = 出院状态
            InptVisitDTO inptVisitDTO1 = new InptVisitDTO();
            inptVisitDTO1.setId(id);//id
            inptVisitDTO1.setHospCode(hospCode);//医院编码
            inptVisitDTO1.setStatusCode(Constants.BRZT.CY);//当前状态 = 出院状态
            inptVisitDTO1.setTreatmentCode(inptVisitDTO.getTreatmentCode());
            inptVisitDAO.updateInptVisit(inptVisitDTO1);

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

                //获取医保就诊信息
                Map<String, Object> insureVisitParam = new HashMap<String, Object>();
                insureVisitParam.put("hospCode", hospCode);//医院编码
                InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
                insureIndividualVisitDTO.setHospCode(hospCode);//医院编码
                insureIndividualVisitDTO.setVisitId(id);//就诊id
                insureVisitParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
                List<InsureIndividualVisitDTO> insureIndividualVisitDTOS = insureIndividualVisitService.findByCondition(insureVisitParam);
                if (insureIndividualVisitDTOS == null || insureIndividualVisitDTOS.size() > 1) {
                    throw new AppException("未获取到医保就诊信息。");
                }
                insureIndividualVisitDTO = insureIndividualVisitDTOS.get(0);

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
                Map<String, Object> isInsureUnifiedMap = new HashMap<>();
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
                inptVisitDTO.setIsUseAccount(insureIndividualVisitDTO.getInsureAccoutFlag());

                isInsureUnifiedMap = new HashMap<>();
                isInsureUnifiedMap.put("hospCode", hospCode);
                isInsureUnifiedMap.put("code", "UNIFIED_PAY");
                SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();

                /**
                 * 通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
                 */
                if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
                    handInsureUnifiedInptSettle(inptVisitDTO, insureIndividualVisitDTO, param, isInsureUnifiedMap, inptCostDOList, settleId);
                } else {
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
        insureUnifiedPayParam.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        insureUnifiedPayParam.put("hospCode", inptVisitDTO.getHospCode());
        insureUnifiedPayParam.put("visitId", inptVisitDTO.getId());
        insureUnifiedPayParam.put("code", code);
        insureUnifiedPayParam.put("userName", userName);
//        insureUnifiedPayInptService_consumer.UP_2402(insureUnifiedPayParam);
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
            String clrType = MapUtils.get(insureInptResult, "clr_type");
            /**
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
                        insureIndividualFundDTO.setInscpScpAmt(MapUtils.get(item, "inscp_scp_amt"));
                    }
                    // 符合政策范围金额
                    // 本次可支付限额金额
                    if (MapUtils.isEmpty(item, "crt_payb_lmt_amt")) {
                        insureIndividualFundDTO.setCrtPaybLmtAmt(null);
                    } else {
                        insureIndividualFundDTO.setCrtPaybLmtAmt(MapUtils.get(item, "crt_payb_lmt_amt"));
                    }
                    if (MapUtils.isEmpty(item, "fund_payamt")) {
                        insureIndividualFundDTO.setFundPayamt(null);
                    } else {
                        // 基金支付金额
                        insureIndividualFundDTO.setFundPayamt(MapUtils.get(item, "fund_payamt"));
                    }
                    // 基金支付类型名称
                    insureIndividualFundDTO.setFundPayTypeName(MapUtils.get(item, "fund_pay_type_name"));
                    //结算过程信息
                    insureIndividualFundDTO.setSetlProcInfo(MapUtils.get(item, "setl_proc_info"));
                    fundDTOList.add(insureIndividualFundDTO);
                }
                isInsureUnifiedMap.put("fundDTOList", fundDTOList);
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
            insureIndividualCostService.editInsureCostByCostIDS(map);
            individualSettleDO.setClrWay(clrWay);
            individualSettleDO.setClrType(clrType);
            individualSettleDO.setClrOptins(clrOptins);
            insureIndividualSettleService.updateByPrimaryKeySelective(map);
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
                insureUnifiedPayInptService_consumer.editCancelInptSettle(insureUnifiedPayParam);
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
            for (Map<String, Object> map : list) {
                bfcName.put("A" + map.get("inCode"), (String) map.get("fpglName"));
                // 同一个发票分类金额计算在一起
                if (!fpjflbMap.containsKey("A" + map.get("inCode"))) {
                    fpjflbMap.put("A" + map.get("inCode"), map.get("realityPrice"));
                } else {
                    fpjflbMap.put("A" + map.get("inCode"), BigDecimalUtils.add((BigDecimal) fpjflbMap.get("A" + map.get("inCode")), (BigDecimal) map.get("realityPrice")));
                }
            }
            int mark = 1;
            for (Map.Entry<String, String> entry : bfcName.entrySet()) {
                if ("A3".equals(entry.getKey()) || "A4".equals(entry.getKey()) || "A10".equals(entry.getKey()) || "A9".equals(entry.getKey())) {
                    returnMap.put("DN" + mark, entry.getValue());
                    returnMap.put("DA" + mark, fpjflbMap.get(entry.getKey()));
                    mark++;
                }
            }
            returnMap.put("fplb", fpjflbMap);
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
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    @Override
    public Boolean editDischargeInpt(Map<String, Object> map) {
        String id = MapUtils.get(map,"id");
        String hospCode = MapUtils.get(map,"hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(id);
        insureIndividualVisitDTO.setIsOut(Constants.SF.S);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);

//        List<InsureIndividualVisitDTO> byCondition = insureIndividualVisitService.findByCondition(map);
//        if(!ListUtils.isEmpty(byCondition)){
//            throw new AppException("该患者已经做了医保出院办理");
//        }
        map.put("visitId",id);
        insureUnifiedPayInptService_consumer.UP_2402(map);
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(id);
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setIsOut("1");
        map.put("inptVisitDTO",inptVisitDTO);
        insureIndividualVisitService.updateInsureInidivdual(map).getData();
        return true;
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(id);
        insureIndividualVisitDTO.setIsOut(Constants.SF.S);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
//        List<InsureIndividualVisitDTO> byCondition = insureIndividualVisitService.findByCondition(map);
//        if(ListUtils.isEmpty(byCondition)){
//            throw new AppException("该患者医保未做医保出院办理操作,不能进行医保出院取消操作");
//        }
        // 出院登记撤销
        map.put("visitId",id);
        insureUnifiedPayInptService_consumer.UP_2405(map);
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(id);
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setIsOut("0");
        map.put("inptVisitDTO",inptVisitDTO);
        insureIndividualVisitService.updateInsureInidivdual(map).getData();
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
            costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
            List<InptCostDO> inptCostDOList = inptCostDAO.queryInptCostList(costParam);
            //if (inptCostDOList.isEmpty()){throw new AppException("该患者没有产生费用信息。");}
            if (inptCostDOList.isEmpty() && !Constants.BRLX.PTBR.equals(inptVisitDTO1.getPatientCode())) {
                throw new AppException("病人没有任何费用，且已经医保登记了，请先取消医保登记再结算。");
            }
            for (InptCostDO dto : inptCostDOList) {
                if (dto.getIsOk().equals("0")) {
                    throw new AppException("该患者有还未确费的LIS或PASS检查，请先确费。");
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
                minDate = inptCostDOList.get(0).getCrteTime(); //费用开始时间
                maxDate = inptCostDOList.get(inptCostDOList.size() - 1).getCrteTime(); //费用结束时间
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
            costParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
            String[] settleCodes = {Constants.JSZT.WJS, Constants.JSZT.YUJS};
            costParam.put("settleCodes", settleCodes);//结算状态 = 未结算、预结算
            costParam.put("backCode", Constants.TYZT.YFY);//退费状态 = 正常
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
            if (inptCostDOList.isEmpty() && !Constants.BRLX.PTBR.equals(inptVisitDTO.getPatientCode())) {
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
}

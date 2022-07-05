package cn.hsa.insure.module.bo.impl;
import java.util.Date;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.insure.enums.TrigScen;
import cn.hsa.module.inpt.compositequery.service.CompositeQueryService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.fees.dao.InptSettleDAO;
import cn.hsa.module.inpt.fees.service.InptSettlementService;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dto.AnaDiagnoseDTO;
import cn.hsa.module.insure.module.dto.AnaInsuDTO;
import cn.hsa.module.insure.module.dto.AnaMdtrtDTO;
import cn.hsa.module.insure.module.dto.AnaOperationDTO;
import cn.hsa.module.insure.module.dto.AnaOrderDTO;
import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.service.InsureDetailAuditService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.insure.util.Transpond;
import cn.hsa.insure.xiangtan.inpt.InptFunction;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.bo.InsureIndividualCostBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Component
@Slf4j
public class InsureIndividualCostBOImpl implements InsureIndividualCostBO {

    @Resource
    private InptFunction inptFunction;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private Transpond transpond;

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InptService inptService;

    @Resource
    private InsureUnifiedPayInptService insureUnifiedPayInptService;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureIndividualSettleDAO insureIndividualSettleDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    //明细审核分析服务
    @Resource
    private InsureDetailAuditService insureDetailAuditService;

    @Resource
    private InptSettlementService inptSettlementServiceProvider;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private CompositeQueryService compositeQueryService;
    /**
     * @return java.lang.Boolean
     * @throws
     * @Method saveCostByRemoteInpt
     * @Param [insureIndividualCostDTO]
     * @description 校验保存住院费用明细信息（Remote_BIZC131252）
     * @author marong
     * @date 2020/11/4 20:06
     */
    @Override
    public Boolean saveCostByRemoteInpt(InsureIndividualCostDTO insureIndividualCostDTO) {
        return inptFunction.remote_bizc131252(insureIndividualCostDTO);
    }


    /**
     * @Method queryPage()
     * @Desrciption 分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @Override
    public PageDTO queryPage(InptVisitDTO inptVisitDTO) {
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", inptVisitDTO.getId());
        insureVisitParam.put("hospCode", inptVisitDTO.getHospCode());
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitDAO.queryInsureIndividualVisitByIds(insureVisitParam);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("未查找到【"+inptVisitDTO.getName()+"】医保个人信息，请做医保登记。");
        }
        inptVisitDTO.setOrgCode(insureIndividualVisitDTO.getInsureOrgCode());
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptCostDTO> costDTOList = insureIndividualCostDAO.queryPage(inptVisitDTO);
        return PageDTO.of(costDTOList);
    }


    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人费用传输
     * 1.先确人改病人是否做了医保登记
     * 2.确人病人的是否有上传的费用
     * 3.判断上传费用数据的条数是否大于100 如果大于100则分批处理
     * 4.判断是否为异地病人
     * 5.判断属于那个医疗机构编码（长沙医保/湘潭医保）
     * 5.调用医保费用传输方法
     * 6.保存费用进inpt_cost
     * @Param
     * @Author fuhui
     * @Date 2020/11/10 15:18
     * @Return
     **/
    @Override
    public Map<String, Object> saveFeeTransmit(InptVisitDTO inptVisitDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        String hospCode = inptVisitDTO.getHospCode();
        String visitId = inptVisitDTO.getId();
        String crteId = inptVisitDTO.getCrteId();
        String code = inptVisitDTO.getCode();
        String isHalfSettle = inptVisitDTO.getIsHalfSettle();
        if (StringUtils.isEmpty(isHalfSettle)) {
            isHalfSettle = "0";
        }
        Date startDate = inptVisitDTO.getFeeStartDate();
        Date endDate = inptVisitDTO.getFeeEndDate();
        String feeStartDate = "";
        String feeEndDate = "";
        if (startDate != null) {
            feeStartDate = DateUtils.format(startDate, DateUtils.Y_M_D);
        }
        if (endDate != null) {
            feeEndDate = DateUtils.format(endDate, DateUtils.Y_M_D);
        }
        String crteName = inptVisitDTO.getCrteName();
        int batchCount = 100; // 定义分批处理的数据
        int count;
        //获取个人信息
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        insureVisitParam.put("medicalRegNo", inptVisitDTO.getMedicalRegNo());
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("【" + inptVisitDTO.getName() + "】未完成医保登记。");
        }
        String insureRegCode = insureIndividualVisitDTO.getInsureOrgCode();
        String cardIden = insureIndividualVisitDTO.getCardIden();

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
        SysParameterDTO sysParameterDTO =null;
        if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
            Map<String, Object> isInsureUnifiedMap = new HashMap<>();
            isInsureUnifiedMap.put("hospCode", hospCode);
            isInsureUnifiedMap.put("code", "BABY_INSURE_FEE");
            sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        }
        Map<String, String> insureCostParam = new HashMap<String, String>();
        if(sysParameterDTO !=null && "1".equals(sysParameterDTO.getValue())){
            insureCostParam.put("queryBaby", "Y");// 医保机构编码
        }else{
            insureCostParam.put("queryBaby", "N");// 医保机构编码
        }
        //判断是否有传输费用信息
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("isHalfSettle", isHalfSettle);// 是否中途结算
        insureCostParam.put("feeStartDate", feeStartDate);
        insureCostParam.put("feeEndDate", feeEndDate);// 是否中途结算
        /**
         *   获取最新的的顺序号
         */
        String orderNo = insureIndividualCostDAO.queryLastestOrderNo(inptVisitDTO);
        if (StringUtils.isEmpty(orderNo)) {
            count = 0;
        } else {
            count = Integer.parseInt(orderNo);
        }
        //保存本次传输费用信息
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
       /*Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();*/
        List<Map<String, Object>> insureCostList = new ArrayList<>();
//        if(sysParameterDTO ==null || !"1".equals(sysParameterDTO.getValue())){
        if(StringUtils.isEmpty(isUnifiedPay) || !"1".equals(isUnifiedPay)){
            //查询未上传的费用集合
            insureCostList = insureIndividualCostDAO.queryInsureCostByVisit(insureCostParam);
            if (insureCostList == null || insureCostList.isEmpty()) {
                resultMap.put("name", inptVisitDTO.getName());
                resultMap.put("num", "0");
                return resultMap;
            }
            for (Map<String,Object> item : insureCostList){
                count++;
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
                insureIndividualCostDO.setGuestRatio("0");//自付比例
                insureIndividualCostDO.setPrimaryPrice((BigDecimal)item.get("realityPrice"));//原费用
                insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
                insureIndividualCostDO.setOrderNo(count+"");//顺序号
                insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
                insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志 = 已传输
                insureIndividualCostDO.setCrteId(crteId);//创建id
                insureIndividualCostDO.setCrteName(crteName);//创建人姓名
                insureIndividualCostDO.setCrteTime(new Date());//创建时间
                insureIndividualCostDOList.add(insureIndividualCostDO);
            }
            insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
        }
        inptVisitDTO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());
        Map<String, Object> resultDataMap = null;
        if (Constants.SF.S.equals(insureIndividualVisitDTO.getIsEcqr())) {
            //电子凭证
            //transpond.to(hospCode,insureRegCode,Constant.FUNCTION.);
//        } else if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
        } else if(StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)){
            //通过获取系统参数来判断 是走医保统一支付平台 还是调用自己的的医保接口
            Map<String, Object> unifiedMap = new HashMap<>();
            unifiedMap.put("code", code);
            unifiedMap.put("crteName", crteName);
            unifiedMap.put("crteId", crteId);
            unifiedMap.put("isHalfSettle", isHalfSettle);// 是否中途结算
            unifiedMap.put("feeStartDate", feeStartDate);
            unifiedMap.put("feeEndDate", feeEndDate);// 是否中途结算
            unifiedMap.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
            unifiedMap.put("inptVisitDTO", inptVisitDTO);
            unifiedMap.put("queryBaby",MapUtils.get(insureCostParam,"queryBaby"));
            unifiedMap.put("count", count);
            unifiedMap.put("queryBaby",MapUtils.get(insureCostParam,"queryBaby"));
            unifiedMap.put("hospCode", hospCode);
            resultDataMap = insureUnifiedPayInptService.UP_2301(unifiedMap).getData();

        }
        else {
            //封装需要的请求医保入参
            inptVisitDTO.setInsureRegCode(insureRegCode);  // 医保注册编码
            inptVisitDTO.setAac001(insureIndividualVisitDTO.getAac001()); // 个人电脑号
            inptVisitDTO.setAka130(insureIndividualVisitDTO.getAka130()); // 业务类型代码----住院
            inptVisitDTO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());//医保登记号
            inptVisitDTO.setMedicineOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());//医疗机构编码
            inptVisitDTO.setCardIden(cardIden); // 卡识别码
            List<Map<String, Object>> costList = new ArrayList<>();

            /**
             * 省内异地 和省外异地
             */
            if (insureCostList.size() >= 100) {
                int leng = 0;
                for (int i = 0; i < insureCostList.size(); i++) {
                    costList.add(insureCostList.get(i));
                    if (batchCount == costList.size() || i == insureCostList.size() - 1) {
                        inptVisitDTO.setInsureCostList(costList);
                        try {
                            if (leng > 0) {
                                Thread.sleep(5000);
                            }
                            if (Constants.BRLX.SNYDBR.equals(insureIndividualVisitDTO.getPatientType()) ||
                                    Constants.BRLX.SWYDBR.equals(insureIndividualVisitDTO.getPatientType())) {
                                transpond.to(hospCode, insureRegCode, Constant.FUNCTION.Remote_BIZC131252, inptVisitDTO);
                            } else {
                                transpond.to(hospCode, insureRegCode, Constant.FUNCTION.BIZH120002, inptVisitDTO);
                            }

                            costList.clear();
                        } catch (Exception e) {
                            if (leng > 0) {
                                Map<String, Object> param = new HashMap<String, Object>();
                                param.put("hospCode", hospCode);
                                param.put("username", crteName);
                                param.put("visitId", visitId);
                                param.put("code", code);
                                param.put("inptVisitDTO", inptVisitDTO);
                                inptService.delInptCostTransmit(param);
                            }
                            throw new AppException(e.getMessage());
                        }
                    }
                }
                leng++;
            }
            else {
                inptVisitDTO.setInsureCostList(insureCostList);
                if (Constants.BRLX.SNYDBR.equals(insureIndividualVisitDTO.getPatientType()) ||
                        Constants.BRLX.SWYDBR.equals(insureIndividualVisitDTO.getPatientType())) {
                    transpond.to(hospCode, insureRegCode, Constant.FUNCTION.Remote_BIZC131252, inptVisitDTO);
                } else {
                    transpond.to(hospCode, insureRegCode, Constant.FUNCTION.BIZH120002, inptVisitDTO);
                }
            }
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
            hashMap.put("visitId", visitId);//就诊id
            hashMap.put("isMatch", Constants.SF.S);//是否匹配 = 是
            hashMap.put("transmitCode", Constants.SF.S);//传输标志 = 未传输
            hashMap.put("insureRegCode", insureRegCode);// 医保机构编码
            hashMap.put("isHalfSettle", isHalfSettle);// 是否中途结算
            List<Map<String, Object>> mapList = insureIndividualCostDAO.queryInsureCostByVisit(hashMap);
            inptVisitDTO.setInsureCostList(mapList);
            AnalysisDTO analysisDTO = this.initAnalysisDTO(inptVisitDTO,insureIndividualVisitDTO);
            Map<String, Object> inMap = new HashMap<>();
            inMap.put("hospCode",inptVisitDTO.getHospCode());
            inMap.put("analysisDTO",analysisDTO);
            inMap.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
            AnaResJudgeDTO anaResJudgeDTO = insureDetailAuditService.upldBeforeAnalysisDTO(inMap);
        }
        resultMap.put("name", inptVisitDTO.getName());
        resultMap.put("num", insureCostList.size());
        return resultMap;
    }

    /**
     * @Method updateUnifiedInptFee
     * @Desrciption  处理医保明细反参
     *
     * @Param resultDataMap：反参map
     *         unifiedPayMap:调用费用传输的入参
     *        inptVisitDTO: 患者信息
     *
     * @Author fuhui
     * @Date   2021/8/11 19:26
     * @Return
    **/
    private void updateUnifiedInptFee(Map<String, Object> resultDataMap, InptVisitDTO inptVisitDTO,Map<String, Object> unifiedPayMap) {
        if(!MapUtils.isEmpty(resultDataMap)){
            List<Map<String,Object>> listAllMap = MapUtils.get(resultDataMap,"listAllMap");
            if(ListUtils.isEmpty(listAllMap)){
                throw new AppException("费用传输时,医保反参费用明细集合为空");
            }else{
                String hospCode  = inptVisitDTO.getHospCode();
                String visitId = inptVisitDTO.getId();
                InsureIndividualCostDTO insureIndividualCostDTO = null;
                BigDecimal sumBigDecimal = MapUtils.get(resultDataMap,"sumBigDecimal");
                List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
                String isHalfSettle = MapUtils.get(resultDataMap,"isHalfSettle");
                Date startDate = MapUtils.get(unifiedPayMap,"startDate"); //计费开始时间
                Date endDate = MapUtils.get(unifiedPayMap,"endDate");  //
                InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(unifiedPayMap,"insureIndividualVisitDTO");
                Integer settleCount = insureIndividualCostDAO.queryLasterCounter(insureIndividualVisitDTO);
                for(Map<String, Object> item :listAllMap){
                    DecimalFormat df1 = new DecimalFormat("0.00");
                    insureIndividualCostDTO = new InsureIndividualCostDTO();
                    insureIndividualCostDTO.setVisitId(visitId);
                    insureIndividualCostDTO.setFeedetlSn(MapUtils.get(item,"feedetl_sn")); // 费用明细流水号
                    insureIndividualCostDTO.setHospCode(hospCode);//医院编码
                    insureIndividualCostDTO.setGuestRatio(MapUtils.get(item, "selfpay_prop") ==null ?"":MapUtils.get(item, "selfpay_prop").toString()); // 自付比例
                    insureIndividualCostDTO.setPricUplmtAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "pric_uplmt_amt")==null ?"": MapUtils.get(item, "pric_uplmt_amt").toString())))); // 定价上限金额
                    insureIndividualCostDTO.setOverlmtSelfpay(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "overlmt_selfpay")==null ?"": MapUtils.get(item, "overlmt_selfpay").toString())))); // 超限价金额
                    insureIndividualCostDTO.setOverlmtAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "overlmt_amt") == null ? "":MapUtils.get(item, "overlmt_amt").toString()))));  // 超限价金额
                    insureIndividualCostDTO.setPreselfpayAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "preselfpay_amt") ==null ? "" :MapUtils.get(item, "preselfpay_amt").toString())))); // 先行自付金额
                    insureIndividualCostDTO.setInscpScpAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt") ==null ? "":MapUtils.get(item, "inscp_scp_amt").toString())))); // 符合政策范围金额
                    insureIndividualCostDTO.setDetItemFeeSumamt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") ==null ? "":MapUtils.get(item, "det_item_fee_sumamt").toString())))); // 明细项目费用总额
                    insureIndividualCostDTO.setFulamtOwnpayAmt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") == null?"":MapUtils.get(item, "fulamt_ownpay_amt").toString())))); // 全自费金额
                    insureIndividualCostDTO.setSumBigDecimalFee(sumBigDecimal); // 本次费用传输总金额
                    insureIndividualCostDTO.setMedChrgitmType(MapUtils.get(item,"med_chrgitm_type")); //医疗收费项目类别
                    insureIndividualCostDTO.setLmtUsedFlag(MapUtils.get(item,"lmt_used_flag")); // 限制使用标志
                    insureIndividualCostDTO.setChrgItemLv(MapUtils.get(item,"chrgitm_lv"));//收费项目等级
                    insureIndividualCostDTO.setInsureRegisterNo(insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
                    insureIndividualCostDTO.setIsHalfSettle(isHalfSettle); // 是否中途结算
                    insureIndividualCostDTO.setFeeStartTime(startDate); //计费开始日期
                    insureIndividualCostDTO.setFeeEndTime(endDate);// 计费截止日期
                    if("0".equals(isHalfSettle)){
                        insureIndividualCostDTO.setSettleCount(0); // 中途结算次数
                    }else{
                        if(settleCount == null){
                            insureIndividualCostDTO.setSettleCount(1); // 中途结算次数
                        }else{
                            insureIndividualCostDTO.setSettleCount(++settleCount); // 中途结算次数
                        }
                    }
                    individualCostDTOList.add(insureIndividualCostDTO);
                }
                if(!ListUtils.isEmpty(individualCostDTOList)){
                    insureIndividualCostDAO.updateFeeUploadResult(individualCostDTOList);
                }
            }
        }
    }

    /**
     * @Method queryInptPatientPage（）
     * @Desrciption 查询医保住院病人的信息
     * @Param
     * @Author fuhui
     * @Date 2020/11/18 11:00
     * @Return
     **/
    @Override
    public PageDTO queryInptPatientPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureIndividualCostDAO.queryInptPatientPage(inptVisitDTO);
        return PageDTO.of(visitDTOList);
    }

    /**
     * @param insureIndividualCostDTO 请求参数
     * @Menthod editInsureCostByCostIDS
     * @Desrciption 根据费用id修改 医保费用信息
     * @Author Ou·Mr
     * @Date 2020/12/3 15:49
     * @Return int 受影响行数
     */
    @Override
    public int editInsureCostByCostIDS(InsureIndividualCostDTO insureIndividualCostDTO) {
        return insureIndividualCostDAO.editInsureCostByCostIDS(insureIndividualCostDTO);
    }


    /**
     * @param param 请求参数
     * @Menthod queryInsureCostByVisit
     * @Desrciption 查询住院费用传输，未传输、已传输费用信息
     * @Author Ou·Mr
     * @Date 2020/12/22 11:40
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> queryInsureCostByVisit(Map<String, String> param) {
        return insureIndividualCostDAO.queryInsureCostByVisit(param);
    }

    /**
     * @param insureCostParam
     * @Method queryOutptInsureCostByVisit
     * @Desrciption 查询门诊未传输费用信息
     * @Param insureCostParam
     * @Author fuhui
     * @Date 2021/3/4 9:32
     * @Return
     */
    @Override
    public List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, Object> insureCostParam) {
        return insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
    }

    /**
     * @Method insertInsureCost()
     * @Desrciption 把住院费用表里面的数据插入到医保就诊费用表里面去
     * @Param
     * @Author fuhui
     * @Date 2020/11/11 11:43
     * @Return
     **/
    @Override
    public void insertInsureCost(List<InsureIndividualCostDO> insureIndividualCostDOList) {
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }

    /**
     * @param insureVisitParam
     * @Method deleteOutptInsureCost
     * @Desrciption 根据就诊id和医院编码删除医保门诊费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:23
     * @Return
     */
    @Override
    public void deleteOutptInsureCost(Map<String, Object> insureVisitParam) {
        insureIndividualCostDAO.deleteOutptInsureCost(insureVisitParam);
    }

    /**
     * @param insureCostParam
     * @Method selectCostIsTransmit
     * @Desrciption 查询门诊医保已经上传的费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:40
     * @Return
     */
    @Override
    public List<InsureIndividualCostDTO> selectCostIsTransmit(Map<String, Object> insureCostParam) {
        return insureIndividualCostDAO.selectCostIsTransmit(insureCostParam);
    }

    /**
     * @param costDTOList
     * @Method updateInsureCost
     * @Desrciption 批量更新费用传输以后，医保统一支付平台的反参
     * @Param data
     * @Author fuhui
     * @Date 2021/3/4 13:49
     * @Return
     */
    @Override
    public Boolean updateInsureCost(List<InsureIndividualCostDTO> costDTOList) {
        return insureIndividualCostDAO.updateInsureCost(costDTOList);
    }

    /**
     * @param insureIndividualCostDTO
     * @Method updateInsureSettleCost
     * @Desrciption 统一支付平台：门诊结算成功以后，更新医保费用的结算id
     * @Param insureIndividualCostDTO
     * @Author fuhui
     * @Date 2021/4/15 15:42
     * @Return
     */
    @Override
    public Boolean updateInsureSettleCost(InsureIndividualCostDTO insureIndividualCostDTO) {
        return insureIndividualCostDAO.updateInsureSettleCost(insureIndividualCostDTO);
    }

    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人批量费用传输
     * @Param inptVisitDTO
     * @Author 廖继广
     * @Date 2021/04/13 19:51
     * @Return
     **/
    @Override
    public Boolean saveFeesTransmit(InptVisitDTO inptVisitDTO) {
        // *********************** 批量查询患者信息，是否均已完成医保登记 ***********************
        /*List<String> ids = inptVisitDTO.getIds();
        Map<String,Object> insureVisitParam = new HashMap<String,Object>();
        insureVisitParam.put("ids",ids);
        insureVisitParam.put("hospCode",inptVisitDTO.getHospCode());
        List<InsureIndividualVisitDTO> insureVisitList = insureIndividualVisitDAO.getInsureIndividualVisitByIds(insureVisitParam);
        if (ListUtils.isEmpty(insureVisitList)) {
            throw new AppException("未查询到医保登记患者");
        }

        if (ids.size() != insureVisitList.size()) {
            for (String id : ids) {
                Boolean isInsure = true;
                for (InsureIndividualVisitDTO insureDTO : insureVisitList) {
                    if (id.equals(insureDTO.getVisitId())) {
                        isInsure = false;
                        break;
                    }
                }
                if (!isInsure) {
                    throw new AppException("患者登记数据异常【就诊ID:" + id +"】，请联系管理员!");
                }
            }
        }

        // *********************** 批量查询患者信息，是否有需要传输的费用 ***********************
        List<Map<String,String>> feesSelectList = new ArrayList<>();
        for (InsureIndividualVisitDTO insureDTO : insureVisitList) {
            String insureRegCode = insureDTO.getInsureOrgCode();
            Map<String,String> insureCostParam = new HashMap<>();
            insureCostParam.put("hospCode",insureDTO.getHospCode());//医院编码
            insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
            insureCostParam.put("visitId",insureDTO.getVisitId());//就诊id
            insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
            insureCostParam.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
            insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
            feesSelectList.add(insureCostParam);
        }
        List<Map<String,Object>> insureCostList = insureIndividualCostDAO.queryInsureCostByList(feesSelectList);
        if(ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该医保病人没有可传输的费用");
        }

        Map<String,String> sysCodeMap = new HashMap<>();
        sysCodeMap.put("hospCode",inptVisitDTO.getHospCode());
        sysCodeMap.put("code","TRANSMIT_MAX");
        WrapperResponse<SysParameterDTO> wr = sysParameterService.getParameterByCode(sysCodeMap);
        if (wr != null && wr.getData() != null) {
            String sysValue = wr.getData().getValue();
           if(Integer.parseInt(sysValue) < insureCostList.size()) {
               throw new AppException("单次批量上传数据超过【" + sysValue + "】，超出系统设置参数最大值(TRANSMIT_MAX)" );
           }
        }*/
        return true;
    }

    /**
     * @param map
     * @Method selectLastCostInfo
     * @Desrciption 查询最新的费用批次号
     * @Param insureIndividualVisitDTO
     * @Author fuhui
     * @Date 2021/5/26 14:39
     * @Return
     */
    @Override
    public String selectLastCostInfo(Map<String, Object> map) {
        return insureIndividualCostDAO.selectLastCostInfo(map);
    }

    /**
     * @param inptVisitDTO
     * @Method queryUnMatchPage
     * @Desrciption 查询没有匹配的费用数据集合
     * @Param
     * @Author fuhui
     * @Date 2021/6/20 9:55
     * @Return
     */
    @Override
    public PageDTO queryUnMatchPage(InptVisitDTO inptVisitDTO) {
        String hospCode = inptVisitDTO.getHospCode();
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", inptVisitDTO.getId());
        insureVisitParam.put("hospCode", inptVisitDTO.getHospCode());
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitDAO.queryInsureIndividualVisitByIds(insureVisitParam);
        if (insureIndividualVisitDTO == null) {
            throw new AppException("未查找到【"+inptVisitDTO.getName()+"】医保个人信息，请做医保登记。");
        }
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        String isUnified = "";
        if (sysParameterDTO != null && "1".equals(sysParameterDTO.getValue())) {
            isUnified = "1";
        } else {
            isUnified = "0";
        }
        inptVisitDTO.setIsUnified(isUnified);
        inptVisitDTO.setOrgCode(insureIndividualVisitDTO.getInsureOrgCode());
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptCostDTO> costDTOList = insureIndividualCostDAO.queryAllQueryUnMatchPage(inptVisitDTO);
        return PageDTO.of(costDTOList);
    }

    /**
     * @Method insurCostTransmissionJob
     * @Desrciption 医保定时费用上传
     * @Param
     * @Author liaojiguang
     * @Date 2021/7/14 14:58
     * @Return
     **/
    @Override
    public Map<String, String> insurCostTransmissionJob(InptVisitDTO inptVisitDTO) {
        String hospCode = inptVisitDTO.getHospCode();
        Map<String, String> resultMap = new HashMap<>();
        Map<String, String> selectTransmissionMap = new HashMap<>();
        selectTransmissionMap.put("hospCode", hospCode);
        selectTransmissionMap.put("code", "IS_TIME_TRANSMISSION");
        SysParameterDTO transmissionDTO = sysParameterService_consumer.getParameterByCode(selectTransmissionMap).getData();

        // 启用定时传输标志 IS_TIME_TRANSMISSION != 1
        if (transmissionDTO == null || !Constants.SF.S.equals(transmissionDTO.getValue())) {
            resultMap.put("code", "-1");
            resultMap.put("info", "【" + hospCode + "】医保费用定时传输未启用！");
            return resultMap;
        }
       /* // KafKa消费者状态
        Boolean networkFlag = true;

        // 启用统一支付平台标志 UNIFIED_PAY == 1
        Map<String, String> UPayMap = new HashMap<>();
        UPayMap.put("hospCode", hospCode);
        UPayMap.put("code", "UNIFIED_PAY");
        SysParameterDTO UPayDTO = sysParameterService_consumer.getParameterByCode(UPayMap).getData();
        if (UPayDTO == null || Constants.SF.F.equals(UPayDTO.getValue())) {
            // Kafka_Ip 获取
            Map<String, Object> sysMap = new HashMap<>();
            sysMap.put("hospCode", inptVisitDTO.getHospCode());
            sysMap.put("code", "KAFKA_IP");
            SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(sysMap).getData();
            if (sys == null || sys.getValue() == null) {
                resultMap.put("code", "-1");
                resultMap.put("info", "【" + hospCode + "】KAFKA_IP为空且未启用统一支付平台！");
                return resultMap;
            }

            // KafKa消费者服务检测
            Map<String, Object> httpParam = new HashMap<String, Object>();
            String activityCode = SnowflakeUtils.getId();
            httpParam.put("activityCode", activityCode);
            httpParam.put("Kafka_Service", "OK");
            httpParam.put("data", "1");
            try {
                Map<String, Object> resultObj = RequestInsure.sendMessage(sys.getValue(), inptVisitDTO.getHospCode(), httpParam, activityCode);
            } catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("info", "【" + hospCode + "】kafka服务检测不通过，医保中间件服务未启动！异常错误：" + e.getMessage());
                return resultMap;
            }
        }*/


        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("hospCode", hospCode);
        List<InsureIndividualVisitDTO> idList = insureIndividualVisitDAO.queryAllInsureRegister(selectMap);
        if (!ListUtils.isEmpty(idList)) {
            for (InsureIndividualVisitDTO visitDTO : idList) {
                inptVisitDTO.setId(visitDTO.getVisitId());
                inptVisitDTO.setMedicalRegNo(visitDTO.getMedicalRegNo());
                try {
                    this.saveFeeTransmit(inptVisitDTO);
                    Thread.sleep(500);
                } catch (Exception e) {
                    resultMap.put("msg", "【" + visitDTO.getAac002() + "】传输服务出现异常！ 异常信息：" + e.getMessage());
                }
            }
        }

        resultMap.put("code", "1");
        resultMap.put("info", "【" + hospCode + "】医保传输服务定时执行完成！");
        return resultMap;
    }

    /**
     * @param map
     * @Method updateLimitUserFlag
     * @Desrciption 1.住院医生站开完医嘱保存，填写报销标识以后。修改这些报销标识
     * 2.修改费用表报销标识
     * 3.已经结算的费用不能修改
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 9:20
     * @Return
     */
    @Override
    public Boolean updateLimitUserFlag(Map<String, Object> map) {
        InptCostDTO inptCostDTO = insureIndividualCostDAO.queryIsSettleFee(map);
        if (inptCostDTO != null && "2".equals(inptCostDTO.getSettleCode())) {
            throw new AppException("已经结算的费用不能修改");
        }
        if (inptCostDTO == null) {
            throw new AppException("费用表里面无该项目信息");
        }
        map.put("1", "isTransmit");
        InsureIndividualCostDTO individualCostDTO = insureIndividualCostDAO.queryIsTransmitFee(map);
        if (individualCostDTO != null) {
            throw new AppException("该费用明细已上传到医保,不能修改报销标识");
        }
        return insureIndividualCostDAO.updateLimitUserFlag(map);
    }

    /**
     * @param inptVisitDTO
     * @Method queryInptCostPage
     * @Desrciption 根据就诊id 查询住院费用明细数据
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 13:49
     * @Return
     */
    @Override
    public PageDTO queryInptCostPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryInptCostPage(inptVisitDTO);
        return PageDTO.of(inptCostDTOList);
    }

    /**
     * @param inptVisitDTO
     * @Method deleteInptHisCost
     * @Desrciption 删除his本地费用
     * 1.如果该患者的存在正常的医保结算记录，则不允许删除his本地费用
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 10:59
     * @Return
     */
    @Override
    public Boolean deleteInptHisCost(InptVisitDTO inptVisitDTO) {
        InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
        individualSettleDTO.setHospCode(inptVisitDTO.getHospCode());
        individualSettleDTO.setVisitId(inptVisitDTO.getId());
        individualSettleDTO.setSettleState(Constants.SF.S);
        individualSettleDTO.setState(Constants.SF.F);
        individualSettleDTO.setMedicalRegNo(inptVisitDTO.getMedicalRegNo());
        individualSettleDTO = insureIndividualSettleDAO.querySettle(individualSettleDTO);
        if (individualSettleDTO != null) {
            throw new AppException("该患者存在正常的医保结算信息,不能删除HIS本地费用");
        }
        return insureIndividualCostDAO.deleteInptHisCost(inptVisitDTO);
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method selectInsureIndividualCost
     * @Desrciption 查询已经保存到医保费用表的数据
     * @Param insureIndividualVisitDTO：医保患者个人就诊信息
     * @Author fuhui
     * @Date 2021/8/16 8:55
     * @Return
     */
    @Override
    public List<InsureIndividualCostDTO> selectInsureIndividualCost(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualCostDAO.selectInsureIndividualCost(insureIndividualVisitDTO);
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method delInsureCost
     * @Desrciption 删除医保本地费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/8/16 9:15
     * @Return
     */
    @Override
    public Integer delInsureCost(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualCostDAO.delInsureCost(insureIndividualVisitDTO);
    }
    /**
     * @param map
     * @Method selectFeeStartAndEndTime
     * @Desrciption 查询中途结算的区间
     * @Param
     * @Author fuhui
     * @Date 2022/1/17 14:39
     * @Return
     */
    @Override
    public InsureIndividualCostDTO selectFeeStartAndEndTime(Map<String, Object> map) {
        return insureIndividualCostDAO.selectFeeStartAndEndTime(map);
    }

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
        WrapperResponse<PageDTO> response = inptSettlementServiceProvider.queryDiagnose(map);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException(response.getMessage());
        }
        if (ObjectUtil.isEmpty(response.getData())) {
            throw new AppException("未查询到诊断信息！");
        }
        List<InptDiagnoseDTO> inptDiagnoseDTOS = JSON.parseArray(JSON.toJSONString(response.getData().getResult()), InptDiagnoseDTO.class);
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
            anaDiagnoseDTOS.add(diagnoseDTO);
        }
        //就诊信息
        InsureIndividualBasicDTO basicInParm = new InsureIndividualBasicDTO();
        basicInParm.setVisitId(inptVisitDTO.getId());
        basicInParm.setHospCode(inptVisitDTO.getHospCode());
        basicInParm.setMedicalRegNo(inptVisitDTO.getMedicalRegNo());
        InsureIndividualBasicDTO basicDTO = insureIndividualBasicDAO.getByVisitId(basicInParm);
        if (ObjectUtil.isEmpty(basicDTO)) {
            throw new AppException("明细审核查询人员医保基本信息为空，请检查！");
        }
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
        anaMdtrtDTO.setMedinsAdmdvs(insureVisitDTO.getMdtrtareaAdmvs());
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

        //入参DTO
        AnalysisDTO analysisDTO = new AnalysisDTO();
        //*系统编码
        analysisDTO.setSyscode("YYDS");
        //任务ID
        analysisDTO.setTaskId("");
        //触发场景
        analysisDTO.setTrigScen(TrigScen.TRIG_SCEN_4.getCode());
        //规则标识集合
        analysisDTO.setRuleIds("");
        //*参保人信息
        analysisDTO.setPatientDtos(anaInsuDTO);

        return analysisDTO;
    }
}

package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedUniversityDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualBasicDO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.module.insure.module.service.InsureIndividualSettleService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedUniversityBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptInsurePayDO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;

import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedUniversityBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/13 16:02
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedUniversityBOImpl extends HsafBO implements InsureUnifiedUniversityBO {

    @Resource
    private OutptVisitService outptVisitService;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureUnifiedPayOutptService insureUnifiedPayOutptService_consumer;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;

    @Resource
    private InsureUnifiedUniversityDAO insureUnifiedUniversityDAO;

    @Resource
    private InsureIndividualCostService insureIndividualCostService_consumer;

    @Resource
    private InsureIndividualSettleService insureIndividualSettleService;


    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    @Resource
    private RedisUtils redisUtils;



    /**
     * @param map
     * @Method insertUniversityInsure
     * @Desrciption 大学生医保单独结算
     * @Param map：封装包含就诊id：visitId  settleId:结算id
     *  1.先验证医保机构信息
     *  2.判断是否存在上次结算时留下的单边账数据
     *  3.先获取到门诊就诊信息
     *  4.验证门诊结算信息
     *  5.验证医保结算信息
     *  6.通过身份证类型，身份证号码 获取人员信息 去做医保挂号登记
     *  7.上传医保就诊信息
     *  8.上传费用信息。
     *  9.试算
     *  10.结算
     *
     * @Author fuhui
     * @Date 2021/12/13 16:13
     * @Return
     */
    @Override
    public Boolean insertUniversityInsure(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        String mdtrtCertType = MapUtils.get(map,"mdtrtCertType");//就诊凭证类型
        String mdtrtCertNo = MapUtils.get(map,"mdtrtCertNo");//就诊凭证号码
        String cardSn = MapUtils.get(map,"cardSn");//卡识别码
        if (Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
            if (StringUtils.isEmpty(cardSn)) {
                throw new AppException("就诊凭证类型为社保卡时，卡识别码【cardSn】不能为空！");
            }
        }
        // 验证医保机构信息
        InsureConfigurationDTO insureInsureConfiguration = insureUnifiedCommonUtil.getInsureInsureConfiguration(hospCode, regCode);
        // 效验是否存在异常结算异常数据（医保单边账）
        handlerSettleError(map,insureInsureConfiguration);

        // 校验就诊信息
        OutptVisitDTO outptVisitDTO =  outptVisitService.selectOutptVisitById(map).getData();
        if(outptVisitDTO == null || StringUtils.isEmpty(outptVisitDTO.getCertNo())){
            return false;
        }
        // 效验门诊结算信息
        OutptSettleDTO outptSettleDTO = outptVisitService.selectOutptSettleById(map).getData();
        if(outptSettleDTO == null){
            return false;
        }
        // 校验医保结算信息
        InsureIndividualSettleDTO individualSettleDTO = insureIndividualSettleService.selectInsureIndividualSettleById(map).getData();
        if(individualSettleDTO !=null){
            return false;
        }
        // 默认通过身份证号  身份证类型进行医保登记
        map.put("certNo",mdtrtCertNo); // 证件号码
        map.put("certCode",mdtrtCertType); // 证件类型
        map.put("visitNo",outptVisitDTO.getVisitNo()); // 就医登记号
        map.put("cardSn",cardSn); // 就医登记号
        // 获取医保人员信息
        Map<String,Object> patientInfoMap =  getInsurePatientInfo(map);
        // 获取人员信息以后需要将数据回写到basic表中
        handlerBasicInfo(map,outptVisitDTO,patientInfoMap);
        // 门诊医保挂号登记
        Map<String,Object> registerDataMap = handlerInsureRegister(map,outptVisitDTO);
        String registerKey = MapUtils.get(registerDataMap,"registerKey");
        // 挂号登记完成需要将数据会写到insure_individual_visit
        InsureIndividualVisitDTO insureIndividualVisitDTO =  handlerRegisterInfo(map,outptVisitDTO,registerDataMap,insureInsureConfiguration);
        // 上传医保就诊信息
        uploadInsureDiagnoseInfo(map,insureIndividualVisitDTO,outptVisitDTO);
        // 上传费用信息
        handlerOutptInsureCostFee(map,insureIndividualVisitDTO,outptVisitDTO);
        String feeKey = MapUtils.get(map,"feeKey");
        // 费用上传之后  结算之前,保存个人累计信息
        map.put("medicalRegNo",insureIndividualVisitDTO.getMedicalRegNo());
        map.put("psnNo",insureIndividualVisitDTO.getAac001());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        // 获取最新的费用批次号
        String batchNo = insureIndividualCostService_consumer.selectLastCostInfo(map).getData();
        map.put("batchNo",batchNo);
        // 获取人员累计信息
        Map<String, Object> patientSumMap = queryInsurePatientSum(map);
        // 查询门诊本次结算的总费用
        BigDecimal sumBigFee = insureUnifiedUniversityDAO.selectSumPrice(map);
        // 调用试算接口
        handlerOutptInsureSettle(map,insureIndividualVisitDTO,Constant.UnifiedPay.OUTPT.UP_2206,"门诊试算",sumBigFee);
        // 调用结算接口
        Map<String, Object> resultDataMap = handlerOutptInsureSettle(map, insureIndividualVisitDTO, Constant.UnifiedPay.OUTPT.UP_2207,"门诊结算",sumBigFee);
        String settleKey = MapUtils.get(resultDataMap,"settleKey");
        // 处理医保接口数据回参 保存医保结算表
        handlerResultData(resultDataMap,map,insureInsureConfiguration);
        // 处理医保接口数据回参 保存医保基金信息表
        handlerResultFundData(resultDataMap,map);
        // 结算完成以后更新医保结算Id 到医保费用表、医保就诊表、更新个人累计信息表
        handlerResultFeeData(resultDataMap,map,patientSumMap);
        //更新门诊结算表  （个人账户支出）
        handlerOutptAcctPay(resultDataMap,map);
        // 更新门诊医保支付明细表 outpt_insure_pay
        handlerOutptInsurePay(map,insureInsureConfiguration);
        // 如果到了最后一步、都没有问题，说明此次患者的医保单独结算没有问题，如果没有问题的话  清空redis  settleKey feeKey  registerKey键
        if(StringUtils.isNotEmpty(settleKey)){
            if(redisUtils.hasKey(settleKey)){
                log.info("删除了结算key"+settleKey);
                redisUtils.del(settleKey);
            }
        }
        if(StringUtils.isNotEmpty(feeKey)){
            if(redisUtils.hasKey(feeKey)){
                log.info("删除了费用key"+feeKey);
                redisUtils.del(feeKey);
            }
        }
        if(StringUtils.isNotEmpty(registerKey)){
            if(redisUtils.hasKey(registerKey)){
                log.info("删除了登记key"+registerKey);
                redisUtils.del(registerKey);
            }
        }

        return true;
    }

    /**
     * @Method handlerSettleError
     * @Desrciption处理医保结算时发生的单边账问题
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 11:27
     * @Return
     **/
    private void handlerSettleError(Map<String,Object> map, InsureConfigurationDTO insureConfigurationDTO) {
        String hospCode = MapUtils.get(map,"hospCode");
        String settleId = MapUtils.get(map,"settleId");
        /**
         * 如果有结算key 医保费用表key 有医保登记号key 说明需要调用 门诊取消结算、费用撤销 门诊挂号撤销接口
         *     String settleKey = stringBuffer.append(hospCode).append(setlId).append(funtionCode).toString();
         *     String settleValue = setlId+"-"+medicalRegNo+"-"+aac001+"-"+batchNo;
         *
         *
         *      String registerKey = stringBuffer.append(hospCode).append(settleId).append(functionCode).toString();
         *     String registerValue =medicalRegNo+"-"+aac001+"-"+iptOtpNo;
         *
         *   String feeKey = stringBuffer.append(hospCode).append(settleId).append("2204").toString();
         *    String feeKeyValue = insureIndividualVisitDTO.getMedicalRegNo()+"-"+batchNo+insureIndividualVisitDTO.getAac001();
         */
        //如果没有结算key 没有费用key 有医保登记号key 说明需要调用门诊挂号撤销接口
        String registerKey = hospCode+settleId+Constant.UnifiedPay.OUTPT.UP_2201;
        String settleKey = hospCode+settleId+Constant.UnifiedPay.OUTPT.UP_2207;
        String feeKey = hospCode+settleId+Constant.UnifiedPay.OUTPT.UP_2204;
        map.put("registerKey",registerKey);
        map.put("settleKey",settleKey);
        map.put("feeKey",feeKey);
        if(redisUtils.hasKey(settleKey) && redisUtils.hasKey(feeKey)  && redisUtils.hasKey(registerKey)){
            handlerOutpt_2208(map, insureConfigurationDTO,settleKey);
            handlerOutpt_2205(map, insureConfigurationDTO,feeKey);
            handlerOutpt_2202(map, insureConfigurationDTO,registerKey);

        }
        //如果没有结算key 有费用key 有医保登记号key 说明需要调用门诊挂号撤销接口  费用撤销
        if(!redisUtils.hasKey(settleKey) && redisUtils.hasKey(feeKey) && redisUtils.hasKey(registerKey)){
            handlerOutpt_2205(map, insureConfigurationDTO,feeKey);
            handlerOutpt_2202(map, insureConfigurationDTO,registerKey);
        }
        if( !redisUtils.hasKey(settleKey) && !redisUtils.hasKey(feeKey) && redisUtils.hasKey(registerKey)){
            handlerOutpt_2202(map, insureConfigurationDTO,registerKey);
        }
    }
    /**
     * @Method handlerOutpt_2202
     * @Desrciption  处理门诊退费的
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 15:03
     * @Return
     **/
    private void handlerOutpt_2208(Map<String, Object> map, InsureConfigurationDTO insureConfigurationDTO,String settleKey) {

        String settleKeyValue = redisUtils.get(settleKey);
        if(StringUtils.isNotEmpty(settleKeyValue)){
            String[] settleKeyValueSplit = settleKeyValue.split("-");
            if(settleKeyValueSplit.length<3){
                return ;
            }
            outpt_2208(map,insureConfigurationDTO,settleKeyValueSplit);
            redisUtils.del(settleKey);
        }
    }

    /**
     * @Method handlerOutpt_2202
     * @Desrciption  处理门诊费用撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 15:03
     * @Return
     **/
    private void handlerOutpt_2205(Map<String, Object> map, InsureConfigurationDTO insureConfigurationDTO,String feeKey) {
        String feeKeyValue = redisUtils.get(feeKey);
        if (StringUtils.isNotEmpty(feeKeyValue)) {
            String[] feeKeyValueSplit = feeKeyValue.split("-");
            if (feeKeyValueSplit.length < 3) {
                return ;
            }
            outpt_2205(map, insureConfigurationDTO, feeKeyValueSplit);
            redisUtils.del(feeKey);
        }
    }

    /**
     * @Method handlerOutpt_2202
     * @Desrciption  处理门诊挂号登记撤销方法的
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 15:03
     * @Return
     **/
    private void handlerOutpt_2202(Map<String, Object> map, InsureConfigurationDTO insureConfigurationDTO,String registerKey) {
        String registerKeyValue = redisUtils.get(registerKey);
        if (StringUtils.isNotEmpty(registerKeyValue)) {
            String[] registerKeyValueSplit = registerKeyValue.split("-");
            if (registerKeyValueSplit.length < 3) {
                return ;
            }
            outpt_2202(map, insureConfigurationDTO, registerKeyValueSplit);
            redisUtils.del(registerKey);
        }
    }

    /**
     * @Method outpt_2205
     * @Desrciption  调用门诊费用撤销
     * @Param         String feeKeyValue = insureIndividualVisitDTO.getMedicalRegNo()+"-"+batchNo+insureIndividualVisitDTO.getAac001();
     *
     * @Author fuhui
     * @Date   2021/12/20 14:50
     * @Return
     **/
    private void outpt_2205(Map<String, Object> map, InsureConfigurationDTO insureConfigurationDTO, String[] feeKeyValueSplit) {
        String hospCode = insureConfigurationDTO.getHospCode();
        String regCode = insureConfigurationDTO.getRegCode();
        Map<String, Object> stringObjectMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>(3);
        dataMap.put("mdtrt_id", feeKeyValueSplit[0]); // 结算ID
        dataMap.put("psn_no", feeKeyValueSplit[2]); // 就诊ID
        dataMap.put("chrg_bchno", feeKeyValueSplit[1]); // 人员编号
        stringObjectMap.put("data", dataMap);
        map.put("msgName","门诊费用明细撤销");
        map.put("isHospital",Constants.SF.F) ;
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,regCode,Constant.UnifiedPay.OUTPT.UP_2205,dataMap,map);
    }

    /**
     * @Method outpt_2202
     * @Desrciption  调用门诊挂号撤销的方法
     * @Param   String registerValue =medicalRegNo+"-"+aac001+"-"+iptOtpNo;
     *
     * @Author fuhui
     * @Date   2021/12/20 13:52
     * @Return
     **/
    private void outpt_2202(Map<String, Object> map, InsureConfigurationDTO insureConfigurationDTO, String[] registerKeyValueSplit) {
        String hospCode = insureConfigurationDTO.getHospCode();
        String regCode = insureConfigurationDTO.getRegCode();
        Map<String, Object> stringObjectMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>(3);
        dataMap.put("mdtrt_id", registerKeyValueSplit[0]); // 结算ID
        dataMap.put("psn_no", registerKeyValueSplit[1]); // 就诊ID
        dataMap.put("ipt_otp_no", registerKeyValueSplit[2]); // 人员编号
        stringObjectMap.put("data", dataMap);
        map.put("msgName","门诊挂号撤销");
        map.put("isHospital",Constants.SF.F) ;
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,regCode,Constant.UnifiedPay.OUTPT.UP_2202,dataMap,map);
    }

    /**
     * @Method outpt_2208
     * @Desrciption  调用门诊取消结算方法
     *  String settleValue = setlId+"-"+medicalRegNo+"-"+aac001+"-"+batchNo;
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/20 13:41
     * @Return
     **/
    private void outpt_2208(Map<String,Object>map, InsureConfigurationDTO insureConfigurationDTO, String[] split) {
        String hospCode = insureConfigurationDTO.getHospCode();
        String regCode = insureConfigurationDTO.getRegCode();
        Map<String, Object> stringObjectMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>(3);
        dataMap.put("setl_id", split[0]); // 结算ID
        dataMap.put("mdtrt_id", split[1]); // 就诊ID
        dataMap.put("psn_no", split[2]); // 人员编号
        stringObjectMap.put("data", dataMap);
        map.put("msgName","医保门诊取消结算");
        map.put("isHospital",Constants.SF.F) ;
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,regCode,Constant.UnifiedPay.OUTPT.UP_2208,dataMap,map);
    }

    /**
     * @Method handlerInsureRegister
     * @Desrciption  门诊医保挂号登记
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 15:27
     * @Return
     **/
    private Map<String, Object> handlerInsureRegister(Map<String, Object> map, OutptVisitDTO outptVisitDTO) {
        Map<String, Object> dataMap = new HashMap<>(11);
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        dataMap.put("psn_no", MapUtils.get(map,"psnNo"));   // 人员编号
        dataMap.put("insutype", MapUtils.get(map,"aae140"));  // 险种类型
        dataMap.put("begntime", outptVisitDTO.getVisitTime()); // 开始时间
        // 就诊凭证类型
        String mdtrtCertType = MapUtil.getStr(map, "certCode");
        if (Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
            dataMap.put("mdtrt_cert_type",Constant.UnifiedPay.CKLX.YDSBK);
        }else {
            dataMap.put("mdtrt_cert_type",mdtrtCertType);
        }
        dataMap.put("mdtrt_cert_no", MapUtil.getStr(map,"certNo"));  // 就诊凭证编号
        dataMap.put("card_sn", MapUtil.getStr(map,"cardSn"));  // 卡识别码
        dataMap.put("ipt_otp_no", outptVisitDTO.getVisitNo()); // 住院/门诊号
        String pracCertiNo = outptVisitDTO.getPracCertiNo();
        String doctorName = outptVisitDTO.getDoctorName();
        if(StringUtils.isEmpty(pracCertiNo)){
            throw  new AppException("该"+doctorName+"医生的医师编码没有维护,请先去用户管理里面维护");
        }
        if(!"D".equals(pracCertiNo.substring(0,1))){
            throw  new AppException("该"+doctorName+"医生的医师编码不是D开头的国家医师代码");
        }
        dataMap.put("atddr_no",pracCertiNo); // 医师编码
        dataMap.put("dr_name",doctorName); // 医师姓名
        dataMap.put("dept_code", outptVisitDTO.getDeptId()); // 科室编码
        dataMap.put("dept_name",outptVisitDTO.getDeptName()); // 科室名称
        dataMap.put("caty", ObjectUtil.isEmpty(outptVisitDTO.getCaty())?"01":outptVisitDTO.getCaty()); // 科别
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", dataMap);
        map.put("isHospital","0");
        map.put("msgName","门诊医保登记");
        String functionCode = Constant.UnifiedPay.OUTPT.UP_2201;
        Map<String, Object> unified = insureUnifiedCommonUtil.commonInsureUnified(hospCode, regCode, functionCode, paramMap, map);
        Map<String, Object> outDataMap = (Map<String, Object>) unified.get("output");
        Map<String, Object> resultDataMap = (Map<String, Object>) outDataMap.get("data");
        String settleId = MapUtils.get(map,"settleId");
        String medicalRegNo = MapUtils.get(resultDataMap,"mdtrt_id");
        String aac001 =  MapUtils.get(resultDataMap,"psn_no");
        String iptOtpNo =  MapUtils.get(resultDataMap,"ipt_otp_no");
        StringBuffer stringBuffer = new StringBuffer();
        // 医保登记的键
        String registerKey = stringBuffer.append(hospCode).append(settleId).append(functionCode).toString();
        String registerValue =medicalRegNo+"-"+aac001+"-"+iptOtpNo;
        redisUtils.set(registerKey,registerValue, 3*24*60*60);
        log.info("门诊挂号登记redis键"+registerKey+"值为:"+registerValue);
        resultDataMap.put("registerKey",registerKey);
        return resultDataMap;
    }

    /**
     * @Method handlerOutptInsurePay
     * @Desrciption  门诊医保结算完成以后更新门诊医保支付明细表
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 14:29
     * @Return
     **/
    private void handlerOutptInsurePay(Map<String, Object> map,InsureConfigurationDTO configurationDTO) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String settleId  = MapUtils.get(map,"settleId");
        //门诊医保明细 outpt_insure_pay
        OutptInsurePayDO outptInsurePayDO = new OutptInsurePayDO();
        outptInsurePayDO.setId(SnowflakeUtils.getId());//id
        outptInsurePayDO.setHospCode(hospCode);//医院编码
        outptInsurePayDO.setSettleId(settleId);//结算id
        outptInsurePayDO.setVisitId(visitId);//就诊id
        outptInsurePayDO.setTypeCode(null);//合同单位明细代码
        outptInsurePayDO.setOrgNo(configurationDTO.getOrgCode());//医保机构编码
        outptInsurePayDO.setOrgName(configurationDTO.getName());//医保机构名称
        outptInsurePayDO.setTotalPrice(MapUtils.get(map, "fundPaySumamt"));//医保报销总金额
        insureUnifiedUniversityDAO.insertOutptInsurePay(outptInsurePayDO);
    }

    /**
     * @Method handlerResultFundData
     * @Desrciption
     *      医保结算以后：处理医保结算基金信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/15 14:03
     * @Return
     **/
    private void handlerResultFundData(Map<String, Object> resultDataMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String crteName = MapUtils.get(map,"crteName");
        String crteId = MapUtils.get(map,"crteId");
        Map<String, Object> outputMap = MapUtils.get(resultDataMap,"output");
        Map<String, Object> setlinfoMap = MapUtils.get(outputMap,"setlinfo");
        String insureSettleId = MapUtils.get(setlinfoMap,"setl_id");
        List<Map<String, Object>> setldetailMapList = MapUtils.get(outputMap,"setldetail");
        if (!ListUtils.isEmpty(setldetailMapList)) {
            InsureIndividualFundDTO insureIndividualFundDTO = null;
            List<InsureIndividualFundDTO> fundDTOList = new ArrayList<>();
            for (Map<String, Object> item : setldetailMapList) {
                insureIndividualFundDTO = new InsureIndividualFundDTO();
                insureIndividualFundDTO.setId(SnowflakeUtils.getId());
                insureIndividualFundDTO.setHospCode(hospCode);
                insureIndividualFundDTO.setVisitId(visitId);
                insureIndividualFundDTO.setInsureSettleId(insureSettleId);
                insureIndividualFundDTO.setCrteName(crteName);
                insureIndividualFundDTO.setCrteId(crteId);
                insureIndividualFundDTO.setCrteTime(DateUtils.getNow());
                insureIndividualFundDTO.setMibId(null);
                insureIndividualFundDTO.setFundName(null);
                insureIndividualFundDTO.setIndiFreezeStatus(null);
                // 基金支付类型
                insureIndividualFundDTO.setFundPayType(MapUtils.get(item, "fund_pay_type"));
                // 符合政策范围金额
                String inscpScpAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "inscp_scp_amt"));
                insureIndividualFundDTO.setInscpScpAmt(BigDecimalUtils.convert(inscpScpAmt));
                // 本次可支付限额金额
                String crtPaybLmtAmt = DataTypeUtils.dataToNumString(MapUtils.get(item, "crt_payb_lmt_amt"));
                insureIndividualFundDTO.setCrtPaybLmtAmt(BigDecimalUtils.convert(crtPaybLmtAmt));
                // 基金支付金额
                String fundPayamt = DataTypeUtils.dataToNumString(MapUtils.get(item, "fund_payamt"));
                insureIndividualFundDTO.setFundPayamt(BigDecimalUtils.convert(fundPayamt));
                // 基金支付类型名称
                insureIndividualFundDTO.setFundPayTypeName(MapUtils.get(item, "fund_pay_type_name"));
                //结算过程信息
                insureIndividualFundDTO.setSetlProcInfo(MapUtils.get(item, "setl_proc_info"));
                fundDTOList.add(insureIndividualFundDTO);
            }
            map.put("fundDTOList",fundDTOList);
            insureIndividualSettleService.insertBatchFund(map).getData();
        }
    }

    /**
     * @Method handlerResultFeeData
     * @Desrciption
     *          医保结算完成以后，更新结算id到医保费用表
     * @Param  map:
     *          settleDataMap： 结算Map
     *
     * @Author fuhui
     * @Date   2021/12/14 16:30
     * @Return
     **/
    private void handlerResultFeeData( Map<String, Object> settleDataMap,Map<String,Object> map,Map<String,Object> patientSumMap) {
        String hospCode = MapUtils.get(map,"hospCode");
        String crteName = MapUtils.get(map,"crteName");
        String crteId = MapUtils.get(map,"crteId");
        Map<String, Object> outputMap = MapUtils.get(settleDataMap,"output");
        Map<String, Object> setlinfoMap = MapUtils.get(outputMap,"setlinfo");
        String insureSettleId = MapUtils.get(setlinfoMap,"setl_id");
        String visitId = MapUtils.get(map,"visitId");
        String batchNo = MapUtils.get(map,"batchNo");
        String aae140 = MapUtils.get(map,"aae140");
        String medisCode = MapUtils.get(setlinfoMap,"fixmedins_code");
        String aac001 =  MapUtils.get(setlinfoMap,"psn_no");
        // 更新医保费用表的结算id
        InsureIndividualCostDTO insureIndividualCostDTO = new InsureIndividualCostDTO();
        insureIndividualCostDTO.setHospCode(hospCode);
        insureIndividualCostDTO.setVisitId(visitId);
        insureIndividualCostDTO.setInsureSettleId(insureSettleId);
        map.put("insureIndividualCostDTO",insureIndividualCostDTO);
        insureIndividualCostService_consumer.updateInsureSettleCost(map).getData();
        // 更新医保就诊表
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String medicalRegNo  = MapUtils.get(setlinfoMap,"mdtrt_id");
        map.put("medicalRegNo",medicalRegNo);
        map.put("insureSettleId",insureSettleId);
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDAO.updateInsureSettleId(map);
        // 更新个人累计信息表
        List<Map<String, Object>> cuminfoMapList = MapUtils.get(patientSumMap, "resultDataMap");
        if(!ListUtils.isEmpty(cuminfoMapList)){
            cuminfoMapList.stream().forEach(item->{
                item.put("id",SnowflakeUtils.getId());
                item.put("crteName",crteName);
                item.put("medicalRegNo",medicalRegNo);
                item.put("crteId",crteId);
                item.put("visitId",visitId);
                item.put("hospCode",hospCode);
                item.put("insutype",aae140);
                item.put("psnNo",aac001);
                item.put("medisCode",medisCode);
                item.put("batchNo",batchNo);
                item.put("insureSettleId",insureSettleId);
                item.put("crteTime",DateUtils.getNow());
            });
            insureIndividualVisitDAO.deletePatientSumInfo(map);
            insureIndividualVisitDAO.insertPatientSumInfo(cuminfoMapList);
        }
    }

    /**
     * @Method queryInsurePatientSum
     * @Desrciption  结算之前,保存个人累计信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 16:13
     * @Return
     **/
    private Map<String,Object> queryInsurePatientSum(Map<String, Object> map) {
        Map<String, Object> patientSumMap = insureUnifiedBaseService.queryPatientSumInfo(map).getData();
        return patientSumMap;
    }

    /**
     * @Method handlerOutptAcctPay
     * @Desrciption  更新门诊结算表的个人账户支出
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 15:57
     * @Return
     **/
    private void handlerOutptAcctPay(Map<String, Object> resultDataMap, Map<String, Object> map) {
        Map<String, Object> outputMap = MapUtils.get(resultDataMap,"output");
        Map<String, Object> settleDataMap = MapUtils.get(outputMap,"setlinfo");
        OutptSettleDO outptSettleDO = new OutptSettleDTO();
        outptSettleDO.setHospCode(MapUtils.get(map,"hospCode"));
        outptSettleDO.setId(MapUtils.get(map,"settleId"));
        outptSettleDO.setAcctPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString( MapUtils.get(settleDataMap, "acct_pay"))));
        map.put("outptSettleDO",outptSettleDO);
        map.put("fundPaySumamt",BigDecimalUtils.convert(DataTypeUtils.dataToNumString( MapUtils.get(settleDataMap, "fund_pay_sumamt"))));
        outptVisitService.updateOutptAcctPay(map).getData();
    }

    /**
     * @Method handlerOutptInsureSettle
     * @Desrciption  门诊试算、结算接口
     *      如果是结算接口则需要回写结算数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 13:45
     * @Return
     **/
    private Map<String,Object> handlerOutptInsureSettle(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO,String funtionCode,String msgName,BigDecimal bigFee) {
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        // 入参，患者信息
        Map<String, Object> patientDataMap = new HashMap<>();
        patientDataMap.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号
        // 就诊凭证类型
        String mdtrtCertType = MapUtil.getStr(map, "certCode");
        if (Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
            patientDataMap.put("mdtrt_cert_type",Constant.UnifiedPay.CKLX.YDSBK);
        }else {
            patientDataMap.put("mdtrt_cert_type",mdtrtCertType);
        }
        patientDataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType()); //  就诊凭证类型
        patientDataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo()); //  就诊凭证编号
        patientDataMap.put("card_sn", MapUtil.getStr(map,"cardSn")); //  卡识别码
        patientDataMap.put("med_type", insureIndividualVisitDTO.getAka130()); //  医疗类别
        patientDataMap.put("medfee_sumamt", bigFee);// 医疗费总额
        patientDataMap.put("psn_setlway", Constants.JSFS.PTJS); //  个人结算方式
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        patientDataMap.put("mdtrt_id", medicalRegNo); // 就诊id
        String batchNo =  MapUtils.get(map,"batchNo");
        patientDataMap.put("chrg_bchno", batchNo); // 收费批次号
        patientDataMap.put("acct_used_flag", Constants.SF.F); // TODO 个人账户使用标志
        patientDataMap.put("insutype", MapUtils.get(map,"aae140")); // 险种类型
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", patientDataMap);
        map.put("msgName",msgName);
        map.put("isHospital",Constants.SF.F);
        map.put("visitId",insureIndividualVisitDTO.getVisitId());
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, regCode, funtionCode, paramMap, map);
        if("2207".equals(funtionCode)){
            Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
            Map<String, Object> settleDataMap = (Map<String, Object>) outputMap.get("setlinfo");
            String setlId = MapUtils.get(settleDataMap,"setl_id");
            String aac001 = MapUtils.get(settleDataMap,"psn_no");
            StringBuffer stringBuffer = new StringBuffer();
            String settleKey = stringBuffer.append(hospCode).append(setlId).append(funtionCode).toString();
            String settleValue = setlId+"-"+medicalRegNo+"-"+aac001+"-"+batchNo;
            redisUtils.set(settleKey,settleValue,3*24*60*60);
            log.info("redis存了结算键"+settleKey);
            resultMap.put("settleKey",settleKey);
        }
        return resultMap;
    }

    /**
     * @Method handlerResultData
     * @Desrciption 处理结算以后的反参数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 14:10
     * @Return
     **/
    private void handlerResultData(Map<String, Object> resultMap, Map<String, Object> map,InsureConfigurationDTO insureConfigurationDTO) {
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> settleDataMap = (Map<String, Object>) outputMap.get("setlinfo");
        String hospCode = MapUtils.get(map,"hospCode");
        Object balcObject = MapUtils.get(settleDataMap, "balc"); // 结算后余额
        Object acctPayObject = MapUtils.get(settleDataMap, "acct_pay"); // 个人账户支出
        Map<String, Object> objectMap = doResultSetdetailList(outputMap);
        // 合并
        Map<String, Object> combineResultMap = new HashMap<>();
        combineResultMap.putAll(settleDataMap);
        combineResultMap.putAll(objectMap);

        //医保结算表 insure_individual_settle
        InsureIndividualSettleDO insureIndividualSettleDO = new InsureIndividualSettleDO();
        insureIndividualSettleDO.setId(SnowflakeUtils.getId());//主键
        insureIndividualSettleDO.setHospCode(hospCode);//医院编码
        insureIndividualSettleDO.setVisitId(MapUtils.get(map,"visitId"));//就诊id
        insureIndividualSettleDO.setSettleId(MapUtils.get(map,"settleId"));//结算id
        insureIndividualSettleDO.setIsHospital(Constants.SF.F);//是否住院（SF）
        insureIndividualSettleDO.setVisitNo(MapUtils.get(map,"visitNo"));//就诊登记号
        insureIndividualSettleDO.setDischargeDnCode(null);//出院疾病诊断编码
        insureIndividualSettleDO.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
        insureIndividualSettleDO.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
        insureIndividualSettleDO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
        insureIndividualSettleDO.setDischargeDnName(null);//出院疾病诊断名称
        insureIndividualSettleDO.setDischargedDate(null);//出院日期
        insureIndividualSettleDO.setDischargedCase(null);//出院情况
        insureIndividualSettleDO.setSettleway(Constants.JSFS.PTJS);//结算方式,01 普通结算,02 包干结算
        insureIndividualSettleDO.setBeforeSettle(BigDecimalUtils.add(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(balcObject)),BigDecimalUtils.convert(DataTypeUtils.dataToNumString(acctPayObject))));//结算前账户余额
        insureIndividualSettleDO.setLastSettle(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(balcObject)));//结算后账户余额
        insureIndividualSettleDO.setState(Constants.ZTBZ.ZC);//状态标志,0正常，2冲红，1，被冲红
        insureIndividualSettleDO.setSettleState(Constants.YBJSZT.JS);//医保结算状态;0试算，1结算
        insureIndividualSettleDO.setCostbatch(null);//费用批次
        insureIndividualSettleDO.setAka130(MapUtils.get(settleDataMap,"med_type"));//业务类型
        insureIndividualSettleDO.setBka006(null);//待遇类型
        insureIndividualSettleDO.setInjuryBorthSn(null);//业务申请号,门诊特病，工伤，生育
        insureIndividualSettleDO.setIsAccount(Constants.SF.S);//当前结算是否使用个人账户;0是，1否
        insureIndividualSettleDO.setRemark(null);//备注
        insureIndividualSettleDO.setCrteId(MapUtils.get(map,"crteId"));//创建人ID
        insureIndividualSettleDO.setCrteName(MapUtils.get(map,"crteName"));//创建人姓名
        insureIndividualSettleDO.setCrteTime(new Date());//创建时间

        insureIndividualSettleDO.setMedicalRegNo(MapUtils.get(settleDataMap,"mdtrt_id"));
        String setlId = MapUtils.get(settleDataMap,"setl_id");
        insureIndividualSettleDO.setInsureSettleId(setlId);
        // 处理金额
        insureIndividualSettleDO.setTotalPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"medfee_sumamt"))));// 本次医疗总费用
        insureIndividualSettleDO.setStartingPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"act_pay_dedc"))));//起付线金额
        insureIndividualSettleDO.setAllPortionPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"fulamt_ownpay_amt"))));//全自费金额
        insureIndividualSettleDO.setPortionPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"overlmt_selfpay"))));//部分自付金额 - 超限价
        BigDecimal insurePrice = BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hifp_pay")));
        insureIndividualSettleDO.setInsurePrice(insurePrice);//医保支付
        insureIndividualSettleDO.setPlanPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"fund_pay_sumamt"))));//统筹基金支付
        insureIndividualSettleDO.setPlanAccountPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"pool_prop_selfpay"))));//统筹段自负金额***
        insureIndividualSettleDO.setPreselfpayAmt(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"preselfpay_amt"))));// 先行自付金额
        insureIndividualSettleDO.setSeriousPrice(BigDecimalUtils.add(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hifmi_pay"))),BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hifob_pay")))));//大病互助支付
        insureIndividualSettleDO.setCivilPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"cvlserv_pay"))));//公务员补助支付
        insureIndividualSettleDO.setRetirePrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"retiredPay"))));// 离休人员医疗保证基金
        insureIndividualSettleDO.setMafPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"maf_pay")))); // 医疗救助基金
        insureIndividualSettleDO.setHospExemAmount(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hospExemAmount")))); // 医院减免
        insureIndividualSettleDO.setPersonalPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"act_pay_dedc"))));//个人账户支付
        insureIndividualSettleDO.setPersonPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"psn_cash_pay"))));//个人支付
        insureIndividualSettleDO.setRestsPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"oth_pay"))));//其他支付
        insureIndividualSettleDO.setFertilityPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"fertilityPay"))));// 生育基金
        insureIndividualSettleDO.setComPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hifes_pay"))));// 企业补充医疗保险基金
        insureIndividualSettleDO.setHospPrice(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hosp_part_amt"))));//医院垫付
        insureIndividualSettleDO.setAcctInjPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"acctInjPay"))));
        insureIndividualSettleDO.setRetAcctInjPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"retAcctInjPay"))));
        insureIndividualSettleDO.setGovernmentPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"governmentPay"))));
        insureIndividualSettleDO.setThbPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"thbPay"))));
        insureIndividualSettleDO.setCarePay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"carePay"))));
        insureIndividualSettleDO.setLowInPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"lowInPay"))));
        insureIndividualSettleDO.setOthPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"othPay"))));
        insureIndividualSettleDO.setInscpScpAmt(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"inscpScpAmt"))));
        insureIndividualSettleDO.setPoolPropSelfpay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"poolPropSelfpay"))));
        insureIndividualSettleDO.setAcctMulaidPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"acctMulaidPay"))));
        insureIndividualSettleDO.setSoldierPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"soldierPay"))));
        insureIndividualSettleDO.setRetiredOutptPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"retiredOutptPay"))));
        insureIndividualSettleDO.setInjuryPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"injuryPay"))));
        insureIndividualSettleDO.setHallPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"hallPay"))));
        insureIndividualSettleDO.setSoldierToPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"soldierToPay"))));
        insureIndividualSettleDO.setWelfarePay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"welfarePay"))));
        insureIndividualSettleDO.setCOVIDPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"COVIDPay"))));
        insureIndividualSettleDO.setFamilyPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"familyPay"))));
        insureIndividualSettleDO.setBehalfPay(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"behalfPay"))));
        insureIndividualSettleDO.setPsnPartAmt(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(combineResultMap,"psn_part_amt"))));
        insureIndividualSettleDO.setClrType(MapUtils.get(combineResultMap,"clr_type"));
        insureIndividualSettleDO.setClrOptins(MapUtils.get(combineResultMap,"clr_optins"));
        insureIndividualSettleDO.setClrWay(MapUtils.get(combineResultMap,"clr_way"));
        Map<String, Object> insureSettleParam = new HashMap<String, Object>();
        insureSettleParam.put("hospCode",hospCode);//医院编码
        insureSettleParam.put("insureIndividualSettleDO", insureIndividualSettleDO);
        insureIndividualSettleService.insertSelective(insureSettleParam);
        map.put("insurePrice",insurePrice);
        map.put("setlId",setlId);
    }

    /**
     * @Method doResultSetdetailList
     * @Desrciption  处理基金信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 15:51
     * @Return
     **/
    private Map<String, Object> doResultSetdetailList(Map<String, Object> outDataMap) {
        Map <String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> setldetailList = MapUtils.get(outDataMap,"setldetailList");
        if (!ListUtils.isEmpty(setldetailList)) {
            for (Map<String,Object> map : setldetailList) {
                String fundPayType = MapUtils.get(map,"fund_pay_type");
                String fundPayamt = MapUtils.get(map,"fund_payamt");
                switch (fundPayType) {
                    case "630100": // 医院减免金额
                        resultMap.put("hospExemAmount",fundPayamt);
                        break;
                    /*case "610100": // 医疗救助基金
                        resultMap.put("mafPay",fundPayamt);
                        break;*/
                    case "330200": // 职工意外伤害基金
                        resultMap.put("acctInjPay",fundPayamt);
                        break;
                    case "390400": // 居民意外伤害基金
                        resultMap.put("retAcctInjPay",fundPayamt);
                        break;
                    case "640100": // 政府兜底基金
                        resultMap.put("governmentPay",fundPayamt);
                        break;
                    case "620100": // 特惠保补偿金
                        resultMap.put("thbPay",fundPayamt);
                        break;
                    case "999996": // 医院垫付基金
                        resultMap.put("hospPrice",fundPayamt);
                        break;
                    case "610200": // 优抚对象医疗补助基金
                        resultMap.put("carePay",fundPayamt);
                        break;
                    case "999109": // 农村低收入人口医疗补充保险
                        resultMap.put("lowInPay",fundPayamt);
                        break;
                    case "999997": // 其他基金
                        resultMap.put("othPay",fundPayamt);
                        break;
                    case "510100": // 生育基金
                        resultMap.put("fertilityPay",fundPayamt);
                        break;
                    case "340100": // 离休人员医疗保障基金
                        resultMap.put("retiredPay",fundPayamt);
                        break;
                    case "350100": // 一至六级残疾军人医疗补助基金
                        resultMap.put("soldierPay",fundPayamt);
                        break;
                    case "340200": // 离休老工人门慢保障基金
                        resultMap.put("retiredOutptPay",fundPayamt);
                        break;
                    case "410100": // 工伤保险基金
                        resultMap.put("injuryPay",fundPayamt);
                        break;
                    case "320200": //  厅级干部补助基金
                        resultMap.put("hallPay",fundPayamt);
                        break;
                    case "310400": //  军转干部医疗补助基金
                        resultMap.put("soldierToPay",fundPayamt);
                        break;
                    case "370200": //  公益补充保险基金
                        resultMap.put("welfarePay",fundPayamt);
                        break;
                    case "99999707": //  新冠肺炎核酸检测财政补助
                        resultMap.put("COVIDPay",fundPayamt);
                        break;
                    case "390500": //  居民家庭账户金
                        resultMap.put("familyPay",fundPayamt);
                        break;
                    case "310500": //  代缴基金（破产改制）
                        resultMap.put("behalfPay",fundPayamt);
                        break;
                    default:
                        break;
                }
            }
        }
        return resultMap;
    }
    /**
     * @Method handlerOutptInsureCostFee
     * @Desrciption  处理门诊医保费用信息
     *   1.先查询出已结算，未上传的医保费用数据
     *   通过结算id、就诊id查询出已经结算的费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 13:39
     * @Return
     **/
    private Map<String,Object> handlerOutptInsureCostFee(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO,OutptVisitDTO outptVisitDTO) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String settleId = MapUtils.get(map,"settleId");
        String insureRegCode = MapUtils.get(map,"regCode");
        // 每次传输之前先删除脏费用数据,没有医保结算id的费用数据  （因为是两个不同的BO实现类,可能存在事务不一致）
        insureUnifiedUniversityDAO.deleteInsureCostFee(map);
        //判断是否有传输费用信息
        Map<String,Object> insureCostParam = new HashMap<String,Object>();
        insureCostParam.put("hospCode",hospCode);//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",visitId);//就诊id
        insureCostParam.put("settleId",settleId); // 结算id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<Map<String,Object>> insureCostList  = insureUnifiedUniversityDAO.selectOutptUniversityFee(insureCostParam);

        // 查询出未匹配的费用项目
        insureCostParam.put("isMatch",Constants.SF.F);//是否匹配 = 是
        List<String> unMatchInsureCostList = insureUnifiedUniversityDAO.selectUnMatchFee(insureCostParam);
        if(!ListUtils.isEmpty(unMatchInsureCostList)){
            throw new AppException("该【"+outptVisitDTO.getName()+"】存在如下没有匹配的项目"+unMatchInsureCostList);
        }
        map.put("insureCostList",insureCostList);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);

        // 获取最新的费用批次号
        String feeBatch = insureIndividualCostService_consumer.selectLastCostInfo(map).getData();
        Integer batchNo = 0;
        if(StringUtils.isNotEmpty(feeBatch)){
            batchNo = Integer.valueOf(feeBatch)+1;
        }
        map.put("batchNo",batchNo);
        map.put("isUniversity","true");
        insureUnifiedPayOutptService_consumer.UP_2204(map).getData();
        StringBuffer stringBuffer = new StringBuffer();
        String feeKey = stringBuffer.append(hospCode).append(settleId).append("2204").toString();
        String feeKeyValue = insureIndividualVisitDTO.getMedicalRegNo()+"-"+batchNo+insureIndividualVisitDTO.getAac001();
        redisUtils.set(feeKey,feeKeyValue,3*24*60*60);
        log.info("redis存了费用键"+feeKey);
        map.put("feeKey",feeKey);
        return map;

    }

    /**
     * @Method uploadInsureDiagnoseInfo
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 11:14
     * @Return
     **/
    private void uploadInsureDiagnoseInfo(Map<String, Object> map,InsureIndividualVisitDTO insureIndividualVisitDTO,OutptVisitDTO outptVisitDTO) {
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        // 封装患者信息
        Map<String, Object> patientInfo = new HashMap<>(13);
        patientInfo.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo()); // 就诊id Y
        patientInfo.put("psn_no", insureIndividualVisitDTO.getAac001()); // 人员编号 Y
        patientInfo.put("med_type", insureIndividualVisitDTO.getAka130()); // TODO 医疗类别 Y
        patientInfo.put("begntime", DateUtils.format(insureIndividualVisitDTO.getVisitTime(), DateUtils.Y_M_DH_M_S)); // 开始时间 Y
        patientInfo.put("main_cond_dscr", ""); // TODO 主要病情
        patientInfo.put("birctrl_type", null); // TODO 计划生育手术类别
        patientInfo.put("birctrl_matn_date", null); // TODO 计划生育手术或生育日期
        patientInfo.put("matn_type", null); // TODO 生育类别
        patientInfo.put("geso_val", "");  // TODO 孕周数
        patientInfo.put("ttp_resp", ""); // TODO 是否第三方责任申请
        patientInfo.put("expi_gestation_nub_of_m", "");  // TODO 是否第三方责任申请
        patientInfo.put("dise_codg", insureIndividualVisitDTO.getBka006()); // add by ljg 病种编号
        patientInfo.put("dise_name", insureIndividualVisitDTO.getBka006Name());  // TODO 病种名称
        List<Map<String,Object>> mapList =  getOutptDiagnose(map,outptVisitDTO);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mdtrtinfo", patientInfo);
        dataMap.put("diseinfo", mapList);
        map.put("msgName","就诊信息上传");
        map.put("isHospital","0");
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,regCode,Constant.UnifiedPay.OUTPT.UP_2203,dataMap,map);
    }

    /**
     * @Method getOutptDiagnose
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/14 11:45
     * @Return
     **/
    private List<Map<String, Object>> getOutptDiagnose(Map<String, Object> map,OutptVisitDTO outptVisitDTO) {
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String regCode = MapUtils.get(map,"regCode");
        List<String> diagnoseList = Stream.of("101","102").collect(Collectors.toList());
        OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
        outptDiagnoseDTO.setHospCode(hospCode);
        outptDiagnoseDTO.setVisitId(visitId);
        outptDiagnoseDTO.setInsureRegCode(regCode);
        map.put("outptDiagnoseDTO", outptDiagnoseDTO);

        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setVisitId(visitId);
        outptPrescribeDTO.setHospCode(hospCode);
        outptPrescribeDTO.setDiagnoseList(diagnoseList);
        map.put("outptPrescribeDTO",outptPrescribeDTO);
        List<OutptDiagnoseDTO> data = outptDoctorPrescribeService.getOutptDiagnose(map).getData();
        if(ListUtils.isEmpty(data)) {
            throw new AppException("没有获取到该【"+outptVisitDTO.getName()+"】患者的诊断信息");
        }

        List<OutptDiagnoseDTO> diagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnose(map).getData();
        List<Map<String, Object>> mapList = new ArrayList<>();
        commonHandlerDisease(diagnoseDTOList,data,outptVisitDTO);
        // 封装诊断信
        for (int i = 0; i < diagnoseDTOList.size(); i++) {
            Map<String, Object> diagnoseMap = new HashMap<>();
            diagnoseMap.put("diag_type", data.get(i).getTypeCode()); // TODO 诊断类别
            diagnoseMap.put("diag_srt_no", i + 1); // TODO 诊断排序号
            diagnoseMap.put("diag_code", diagnoseDTOList.get(i).getInsureInllnessCode()); //  诊断代码
            diagnoseMap.put("diag_name", diagnoseDTOList.get(i).getInsureInllnessName()); //  诊断名称
            diagnoseMap.put("diag_dept", diagnoseDTOList.get(i).getInDeptName()); //  诊断科室
            if ("1".equals(diagnoseDTOList.get(i).getIsMain())) {
                diagnoseMap.put("maindiag_flag", "1");
                diagnoseMap.put("diag_type", "1"); // TODO 诊断类别
            }
            if (StringUtils.isEmpty(diagnoseDTOList.get(i).getPracCertiNo())) {
                diagnoseMap.put("dise_dor_no", outptVisitDTO.getPracCertiNo()); //  诊断医生编码
            } else {
                diagnoseMap.put("dise_dor_no", diagnoseDTOList.get(i).getPracCertiNo()); //  诊断医生编码
            }
            if (StringUtils.isEmpty(diagnoseDTOList.get(i).getZzDoctorName())) {
                diagnoseMap.put("dise_dor_name", outptVisitDTO.getDoctorName()); //  诊断医生编码
            } else {
                diagnoseMap.put("dise_dor_name", diagnoseDTOList.get(i).getZzDoctorName()); // 诊断医生姓名
            }
            diagnoseMap.put("diag_time", DateUtils.format(diagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S)); //  诊断时间
            diagnoseMap.put("vali_flag", Constants.SF.S); // TODO 有效标志
            mapList.add(diagnoseMap);
        }
        return mapList;
    }

    /**
     * @Method commonHandlerDisease
     * @Desrciption  医保入院登记和出院办理时，验证诊断是否匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<OutptDiagnoseDTO> inptDiagnoseDTOList,List<OutptDiagnoseDTO> data,OutptVisitDTO outptVisitDTO) {
        List<OutptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        int size = list.size();
        if(size == 0){
            throw new AppException("该【"+outptVisitDTO.getName()+"】患者没有门诊主诊断");
        }
        if(size >1){
            throw new AppException("该【"+outptVisitDTO.getName()+"】患者门诊主诊断数量大于1");
        }
        if(inptDiagnoseDTOList.size() != data.size()){
            List<String> dataCollect = data.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if(!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该【"+outptVisitDTO.getName()+"】患者开的"+stringBuilder+"还没有进行疾病匹配,请先做好匹配工作");
            }
        }

    }
    /**
     * @Method handlerBasicInfo
     * @Desrciption  获取人员信息以后需要将数据回写到医保信息表当中
     * @Param
     *
     *              map：医院编码，就诊id，创建者等信息
     *              patientInfoMap :人员信息获取回参
     *              outptVisitDTO :门诊就诊信息
     *
     *
     * @Author fuhui
     * @Date   2021/12/14 10:16
     * @Return
     **/
    private void handlerBasicInfo(Map<String, Object> map, OutptVisitDTO outptVisitDTO, Map<String, Object> patientInfoMap) {


        Map<String, Object> tempMap = new HashMap<>();
        Object output = MapUtils.get(patientInfoMap, "output");
        if(output instanceof JSONArray){
            List<Map<String,Object>> list2 = MapUtils.get(patientInfoMap,"output");
            tempMap = list2.get(0);
        }else if(output instanceof  JSONObject){
            tempMap = (Map<String, Object>) patientInfoMap.get("output");
        }
        Map<String, Object> baseInfoMap = MapUtils.get(tempMap,"baseinfo");
        List<Map<String,Object>> insuinfoMapList =   MapUtils.get(tempMap,"insuinfo");
        // 大学生医保默认都是390 居民险种
//        insuinfoMapList = insuinfoMapList.stream().filter(item -> "390".equals(MapUtils.get(item, "insutype"))).collect(Collectors.toList());
        Map<String,Object>  insuinfoMap = new HashMap<>();
        if(!ListUtils.isEmpty(insuinfoMapList)){
            //update by qiang.fan 2022-03-14 获取参保信息，先取有效参保，无有效参保，去第一条参保信息
            //psn_insu_stas 0:未参保,1:正常参保,2:暂停参保,4:终止参保
            for(Map<String,Object> mapList : insuinfoMapList){
                if("1".equals(MapUtils.get(tempMap,"psn_insu_stas"))){
                    insuinfoMap = mapList;
                }else {
                    insuinfoMap = insuinfoMapList.get(0);
                }
            }
        }
        Map<String,Object> idetinfoMap = new HashMap<>();
        List<Map<String,Object>> idetinfoMapList  = MapUtils.get(tempMap,"idetinfo");
        if(!ListUtils.isEmpty(idetinfoMapList)){
            idetinfoMap = idetinfoMapList.get(0);
        }

        //医保个人基本信息
        InsureIndividualBasicDO insureIndividualBasicDO = new InsureIndividualBasicDO();
        String midId = SnowflakeUtils.getId();
        insureIndividualBasicDO.setId(midId);//主键id
        insureIndividualBasicDO.setHospCode(MapUtils.get(map,"hospCode"));//医院编码
        String psnNo = MapUtils.get(baseInfoMap,"psn_no");
        map.put("psnNo",psnNo);
        insureIndividualBasicDO.setAac001(psnNo);//个人电脑号
        insureIndividualBasicDO.setInjuryBorthSn(null);//个人业务号(工伤、生育)
        insureIndividualBasicDO.setAaa027(null);//分级统筹中心编码
        insureIndividualBasicDO.setAaa027Name(null);//分级统筹中心名称
        insureIndividualBasicDO.setAac003(MapUtils.get(baseInfoMap,"psn_name"));//姓名
        insureIndividualBasicDO.setAac004(MapUtils.get(baseInfoMap,"gend"));//性别
        insureIndividualBasicDO.setAac002(MapUtils.get(baseInfoMap,"certno"));//公民身份号码
        insureIndividualBasicDO.setAaz500(null);//社会保障号码
        insureIndividualBasicDO.setAae005(outptVisitDTO.getPhone());//联系电话
        if(MapUtils.get(baseInfoMap,"brdy") ==null){
            insureIndividualBasicDO.setAac006(null);//出生日期
        }else {
            insureIndividualBasicDO.setAac006(DateUtils.format(MapUtils.get(baseInfoMap,"brdy")));//出生日期
        }
        insureIndividualBasicDO.setOrgcode(null);//地区编码
        insureIndividualBasicDO.setAab999(null);//单位管理码
        insureIndividualBasicDO.setAab019(null);//单位类型
        insureIndividualBasicDO.setAab001(null);//单位编码
        insureIndividualBasicDO.setBka008(MapUtils.get(insuinfoMap,"emp_name"));//单位名称
        insureIndividualBasicDO.setBka035(null);//人员类别编码
        insureIndividualBasicDO.setBka035Name(MapUtils.get(insuinfoMap,"psn_type"));//人员类别名称
        insureIndividualBasicDO.setAac008(null);//人员状态编码
        insureIndividualBasicDO.setAac008Name(null);//人员状态名称
        insureIndividualBasicDO.setBac001(MapUtils.get(insuinfoMap,"cvlserv_flag"));//公务员级别编码
        insureIndividualBasicDO.setBac001Name(null);//公务员级别名称
        insureIndividualBasicDO.setAka130("11");//业务类型
        insureIndividualBasicDO.setAka130Name("普通门诊");//业务类型名称
        insureIndividualBasicDO.setBka006(null);//待遇类型
        insureIndividualBasicDO.setBka006Name(null);//待遇类型名称
        insureIndividualBasicDO.setAac148(null);//补助类型
        insureIndividualBasicDO.setAac148Name(null);//补助类型名称
        insureIndividualBasicDO.setAac013(null);//用工形式编码
        insureIndividualBasicDO.setAac013Name(null);//用工形式名称
        String aae140 = MapUtils.get(insuinfoMap,"insutype");
        insureIndividualBasicDO.setAae140(aae140);//险种类型（码表AAE140）
        map.put("aae140",aae140);
        insureIndividualBasicDO.setBka888(null);//基金冻结状态
        insureIndividualBasicDO.setAkc252(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(insuinfoMap,"balc"))));//个人帐户余额
        insureIndividualBasicDO.setAac066(MapUtils.getVS(idetinfoMap,"psn_idet_type",null));//参保身份
        insureIndividualBasicDO.setAab301(null);//所属行政区代码.常住地
        insureIndividualBasicDO.setAac031(null);//人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}
        insureIndividualBasicDO.setAae030Last(null);//上次住院入院日期
        insureIndividualBasicDO.setAae031Last(null);//上次住院出院日期
        insureIndividualBasicDO.setAae030Special(null);//特殊业务申请有效开始时间
        insureIndividualBasicDO.setAae031Special(null);//特殊业务申请有效结束时间
        insureIndividualBasicDO.setAaz267(null);//医疗待遇申请事件ID
        insureIndividualBasicDO.setBaa027(null);//参保地中心编码
        insureIndividualBasicDO.setBaa027Name(null);//参保地中心名称
        insureIndividualBasicDO.setAkc193(null);//疾病ICD编码
        insureIndividualBasicDO.setAkc193Name(null);//疾病ICD名称
        insureIndividualBasicDO.setAac158(null);//低保对象标识
        insureIndividualBasicDO.setAkc026(null);//参加公务员医疗补助标识
        String insuplcAdmdvs = MapUtils.get(insuinfoMap,"insuplc_admdvs");
        map.put("insuplcAdmdvs",insuplcAdmdvs);
        insureIndividualBasicDO.setBaa301(insuplcAdmdvs);//参保地行政区划代码(指参保人所在地的行政区划代码)
        insureIndividualBasicDO.setAab300(null);//参保地社会保险经办机构名称(指参保人所在地的社会保险经办机构名称)
        insureIndividualBasicDO.setAkc009(MapUtils.get(insuinfoMap,"psn_type"));//参保人员类别
        insureIndividualBasicDO.setBka010(null);//本年住院次数
        insureIndividualBasicDO.setBkh015(null);//套餐标识
        insureIndividualBasicDO.setCrteId(MapUtils.get(map,"crteId"));//创建人id
        insureIndividualBasicDO.setCrteName(MapUtils.get(map,"crteName"));//创建人姓名
        insureIndividualBasicDO.setCrteTime(DateUtils.getNow());//创建时间
        insureIndividualBasicDAO.insertSelective(insureIndividualBasicDO);
        // 医保个人信息表主键id  需要赋值给insure_individual_visit的mid_id
        map.put("midId",midId);
    }

    /**
     * @Method handlerRegisterInfo
     * @Desrciption  医保登记以后需要回写insure_individual_visit
     * @Param  map：医院编码，就诊id，创建者等信息
     *         outptVisitDTO :门诊就诊信息
     *         data:医保挂号登记回参
     *         insureConfigurationDTO:医保机构配置信息
     *
     * @Author fuhui
     * @Date   2021/12/14 8:40
     * @Return
     **/
    private InsureIndividualVisitDTO handlerRegisterInfo(Map<String, Object> map, OutptVisitDTO outptVisitDTO, Map<String,Object> registerDataMap,InsureConfigurationDTO insureConfigurationDTO) {
        InsureIndividualVisitDTO data = new InsureIndividualVisitDTO();
        data.setId(SnowflakeUtils.getId());//id
        data.setHospCode(MapUtils.get(map,"hospCode"));//医院编码
        data.setVisitId(MapUtils.get(map,"visitId"));//就诊id
        data.setMibId(MapUtils.get(map,"midId"));//个人基本信息id
        data.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
        data.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
        data.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
        data.setIsHospital(Constants.SF.F);//是否住院（SF）
        data.setVisitNo(outptVisitDTO.getVisitNo());//住院号/就诊号
        data.setAac001(MapUtils.get(registerDataMap,"psn_no")); //人员编号
        data.setMedicalRegNo(MapUtils.get(registerDataMap,"mdtrt_id")); // 就诊ID
        data.setAka130("11");//业务类型
        data.setAka130Name("普通门诊");//业务类型名称
        data.setBka006(null);//待遇类型
        data.setBka006Name(null);//待遇类型名称
        data.setInjuryBorthSn(null);//业务申请号
        /**
         * 需要查询出主诊断
         */
        OutptDiagnoseDTO outptDiagnoseDTO =  insureIndividualVisitDAO.queryOutptDiagnose(map);
        data.setVisitIcdCode(outptDiagnoseDTO.getInsureInllnessCode()); // 就诊疾病编码
        data.setVisitIcdName(outptDiagnoseDTO.getInsureInllnessName()); // 就诊疾病名称
        data.setVisitTime(outptVisitDTO.getVisitTime());//就诊时间
        data.setVisitDrptId(outptVisitDTO.getDeptId());//就诊科室ID
        data.setVisitDrptName(outptVisitDTO.getDeptName());//就诊科室名称
        data.setVisitAreaId(null);//就诊病区ID
        data.setVisitAreaName(null);//就诊病区名称
        data.setVisitBerth(null);//就诊床位
        data.setStartingPrice(null);//起付线金额
        data.setShiftHospCode(null);//转入医院编码
        data.setOutHospCode(null);//转出医院编码
        data.setCause(null);//住院原因
        data.setRemark(null);//备注
        data.setCrteId(MapUtils.get(map,"crteId"));//创建人id
        data.setCrteName(MapUtils.get(map,"crteName"));//创建人姓名
        data.setCrteTime(DateUtils.getNow());//创建时间
        data.setIsEcqr(Constants.SF.S);
        data.setPayOrdId(null);//支付订单号
        data.setPayToken(null);// 支付 token
        data.setAccessoryDiagnosisCode(null); // 副诊断编码
        data.setAccessoryDiagnosisName(null); // 副诊断名称
        data.setInsuplcAdmdvs(MapUtils.get(map,"insuplcAdmdvs")); // 参保地医保区划
        data.setMdtrtCertType(MapUtil.getStr(map,"certCode")); // 就诊凭证类型
        data.setMdtrtCertNo(MapUtil.getStr(map,"certNo")); // 就诊凭证编号
        data.setOmsgid(""); // 交易信息(原交易)中的msgid,发送方报文ID
        data.setOinfno("2201"); // 交易信息(原交易)中的infno
        data.setPretFlag(null);
        data.setFetusCnt(0);
        data.setBirctrlMatnDate(null);
        data.setFetts(0);
        data.setGesoVal(0);
        data.setLatechbFlag(null);
        data.setBirctrlType(null);
        data.setFpscNo(null);
        data.setMatnType(null);
        data.setIsOut("0");
        data.setInsureSettleId(null);
        data.setSettleCount(0);
        data.setIsHalfSettle("0");
        data.setIdetStartDate(null);
        data.setIdetEndDate(null);
        data.setPsnIdetType(null);
        data.setHcardBasinfo(null);
        data.setHcardChkinfo(null);
        insureIndividualVisitDAO.insert(data);
        return data;

    }

    /**
     * @Method getInsurePatientInfo
     * @Desrciption  获取人员信息
     *    大学生医保默认通过身份证证件类型  身份证号码去获取医保人员信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/13 16:48
     * @Return
     **/
    private Map<String, Object> getInsurePatientInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        String regCode = MapUtils.get(map,"regCode");
        String mdtrtCertType = MapUtil.getStr(map, "certCode");
        Map<String, Object> dataMap = new HashMap<>();

        if (Constant.UnifiedPay.CKLX.YDSBK.equals(mdtrtCertType) || Constant.UnifiedPay.CKLX.BDSBK.equals(mdtrtCertType)) {
            dataMap.put("mdtrt_cert_type",Constant.UnifiedPay.CKLX.YDSBK);
        }else {
            dataMap.put("mdtrt_cert_type",mdtrtCertType);
        }
        dataMap.put("mdtrt_cert_no", MapUtils.get(map,"certNo"));
        dataMap.put("card_sn", MapUtil.getStr(map,"cardSn"));
        dataMap.put("psn_cert_type", MapUtil.getStr(map,"psnCertType"));
        dataMap.put("certno", MapUtil.getStr(map,"certno"));
        dataMap.put("psn_name", MapUtil.getStr(map,"psnName"));
        dataMap.put("orgin_card_info", MapUtil.getStr(map,"outInfo"));
        dataMap.put("sign", MapUtil.getStr(map,"sign"));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", dataMap);
        map.put("msgName","人员信息获取");
        map.put("isHospital","0");
        Map<String, Object> unifiedMap = insureUnifiedCommonUtil.commonInsureUnified
                (hospCode, regCode, "1101", paramMap, map);
        return unifiedMap;
    }
}

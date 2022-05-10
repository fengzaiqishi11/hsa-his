package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedOutptBO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: InsureUnifiedPayOutptBoImpl
 * @Describe: 统一支付平台，业务实现层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/10 8:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Component
public class InsureUnifiedOutptBOImpl extends HsafBO implements InsureUnifiedOutptBO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;
    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;
    @Resource
    private SysParameterService sysParameterService_consumer;
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;
    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;
    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;
    @Resource
    private InsureItfBOImpl insureItfBO;
    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private OutptVisitService outptVisitService;

    @Resource
    private InptVisitService inptVisitService;

    @Resource
    DoctorAdviceService doctorAdviceService_consumer;

    @Resource
    private OutptDoctorPrescribeService doctorPrescribeService_consumer;

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);


    /**
     * @Menthod: UP_4301
     * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_4301(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (StringUtils.isEmpty(insureRegCode)) throw new RuntimeException("未传入医保机构编码");
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map, "outptRegisterDTO");
        List<OutptMedicalRecordDTO> blList = MapUtils.get(map, "blList");
        List<OutptDiagnoseDTO> zdList = MapUtils.get(map, "zdList");
        List<OutptPrescribeDetailsExtDTO> cfList = MapUtils.get(map, "cfList");

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setRegCode(insureRegCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

        // 封装挂号信息
        Map<String, Object> rgstinfo = this.handleRegisterData(outptRegisterDTO);
        // 封装病历信息
        List<Map<String, Object>> caseinfo = this.handleBlData(blList);
        // 封装诊断信息
        List<Map<String, Object>> diseinfo = this.handleZdData(zdList);
        // 封装处方信息
        List<Map<String, Object>> rxinfo = this.handleCfData(cfList);

        Map inputMap = new HashMap();
        inputMap.put("rgstinfo", rgstinfo); // 挂号信息dto
        inputMap.put("caseinfo", caseinfo); // 病历信息list
        inputMap.put("diseinfo", diseinfo); // 诊断信息list
        inputMap.put("rxinfo", rxinfo); // 处方信息list

        // 调用统一支付平台接口
        Map httpMap = new HashMap();
        httpMap.put("hospCode",hospCode);
        httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4301); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_DIAGNOSIS_TREATMENT_INFO.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital("0");
        interfaceParamDTO.setVisitId(outptRegisterDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_DIAGNOSIS_TREATMENT_INFO, interfaceParamDTO);

        return resultMap;
    }

    /**
     * @Menthod: UP_4302
     * @Desrciption: 医保统一支付平台：急诊留观手术及抢救信息
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-17 19:45
     * @Return:
     **/
    @Override
    public Map<String, Object> UP_4302(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new RuntimeException("未传入医保机构编码");
        }
        List<OperInfoRecordDTO> ssList = MapUtils.get(map, "ssList");
        List<OutptDiagnoseDTO> qjList = MapUtils.get(map, "qjList");
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setRegCode(insureRegCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

        // 封装手术信息
        List<Map<String, Object>> oprninfo = this.handleSsData(ssList);
        // 封装抢救信息 todo his没有相关数据，暂时写死
        List<Map<String, Object>> rescinfo = this.handleQjData(qjList);

        Map inputMap = new HashMap();
        inputMap.put("oprninfo", oprninfo); // 手术信息list
        inputMap.put("rescinfo", rescinfo); // 抢救信息list

        // 调用统一支付平台接口
        Map httpMap = new HashMap();
        httpMap.put("hospCode",hospCode);
        httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        httpMap.put("infno", Constant.UnifiedPay.OUTPT.UP_4302); // 交易编号
        httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); // 发送方报文
        httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 参保地医保区划
        httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); // 就医地医保区划
        httpMap.put("medins_code", insureConfigurationDTO.getOrgCode()); // 定点医药机构编号
        httpMap.put("insur_code", insureConfigurationDTO.getRegCode()); // 医保中心编码
        httpMap.put("input", inputMap); // 交易输入


        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.OUTPATIENT_EMERGENCY_RESCUE_INFO.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital("0");
        interfaceParamDTO.setVisitId(outptVisitDTO.getVisitId());
        // 调用统一支付平台接口
        Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.OUTPATIENT_EMERGENCY_RESCUE_INFO, interfaceParamDTO);

        return resultMap;
    }

    /**
     * 医保统一支付平台: 线上费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 9:43
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_6201(Map<String, Object> map) {
      String hospCode = MapUtils.get(map, "hospCode");
      String insureRegCode = MapUtils.get(map, "insureRegCode");
      if (StringUtils.isEmpty(insureRegCode)) {
        throw new RuntimeException("未传入医保机构编码");
      }
      //根据医院编码、医保机构编码查询医保配置信息
      InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
      configDTO.setHospCode(hospCode);
      configDTO.setRegCode(insureRegCode);
      InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
      if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");

      //封装onlinePayFeeDTO参数信息
      map.put("insureConfigurationDTO",insureConfigurationDTO);
      OnlinePayFeeDTO onlinePayFeeDTO = handleOnlinePayData(map);
      Map inputMap = new HashMap();
      inputMap.put("data",onlinePayFeeDTO);

      // 调用统一支付平台接口
      Map httpMap = new HashMap();
      httpMap.put("hospCode",hospCode);
      httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
      httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
      // 交易编号
      httpMap.put("infno", Constant.UnifiedPay.REGISTER.UP_6201);
      // 发送方报文
      httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
      // 参保地医保区划
      httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());
      // 就医地医保区划
      httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
      // 定点医药机构编号
      httpMap.put("medins_code", insureConfigurationDTO.getOrgCode());
      // 医保中心编码
      httpMap.put("insur_code", insureConfigurationDTO.getRegCode());
      // 交易输入
      httpMap.put("input", inputMap);

      //参数校验,规则校验和请求初始化
      BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.ONLINE_FEE_PAY.getCode());
      InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
      interfaceParamDTO.setHospCode(hospCode);
      interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
      interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
      // 调用统一支付平台接口
      Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.ONLINE_FEE_PAY, interfaceParamDTO);
      //修改表信息
      /*JSONObject payToken = JSONObject.parseObject(insureIndividualVisitDTO.getPayToken());
      payToken.put("payToken",resultMap.get("payToken"));
      InsureIndividualVisitDO insureIndividualVisitDO = new InsureIndividualVisitDO();
      insureIndividualVisitDO.setVisitId(insureIndividualVisitDTO.getVisitId());
      insureIndividualVisitDO.setHospCode(insureIndividualVisitDTO.getHospCode());
      insureIndividualVisitDO.setPayOrdId((String) resultMap.get("payOrdId"));
      insureIndividualVisitDO.setPayToken(JSONObject.toJSONString(payToken));
      Map<String,Object> insureVisitMap = new HashMap<String,Object>();
      insureVisitMap.put("hospCode",insureIndividualVisitDTO.getHospCode());
      insureVisitMap.put("insureIndividualVisitDO",insureIndividualVisitDO);
      insureIndividualVisitService_consumer.editInsureIndividualVisit(insureVisitMap);*/
      return resultMap;
    }

    /**
     * 6301 医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 16:44
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_6301(Map<String, Object> map) {
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      //入参信息
      SetlResultQueryDTO setlResultQueryDTO = MapUtils.get(map, "setlResultQueryDTO");
      String hospCode = MapUtils.get(map, "hospCode");
      String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
      if (StringUtils.isEmpty(insureRegCode)) {
        throw new RuntimeException("未传入医保机构编码");
      }
      //根据医院编码、医保机构编码查询医保配置信息
      InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
      configDTO.setHospCode(hospCode);
      configDTO.setRegCode(insureRegCode);
      InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
      if (insureConfigurationDTO == null)
        throw new RuntimeException("未发现【" + insureRegCode + "】相关医保配置信息");

      //封装入参数据
      Map map1 = new HashMap();
      Map dataMap = new HashMap();
      SetlResultQueryDTO dto = new SetlResultQueryDTO();
      dto.setPayOrdId(insureIndividualVisitDTO.getPayOrdId());
      dto.setOrgCodg(insureConfigurationDTO.getOrgCode());
      dto.setPayToken(insureIndividualVisitDTO.getPayToken());
      dto.setIdNo(setlResultQueryDTO.getIdNo());
      dto.setIdType(setlResultQueryDTO.getIdType());
      dto.setUserName(setlResultQueryDTO.getUserName());
      // 调用统一支付平台接口
      /*map1.put("payOrdId",insureIndividualVisitDTO.getPayOrdId());
      map1.put("orgCodg",insureConfigurationDTO.getOrgCode());
      map1.put("payToken",insureIndividualVisitDTO.getPayToken());
      map1.put("idNo",setlResultQueryDTO.getIdNo());
      map1.put("idType",setlResultQueryDTO.getIdType());
      map1.put("userName",setlResultQueryDTO.getUserName());
      dataMap.put("data",map1);*/

      Map httpMap = new HashMap();
      httpMap.put("hospCode",hospCode);
      httpMap.put("configRegCode", insureConfigurationDTO.getRegCode());
      httpMap.put("orgCode", insureConfigurationDTO.getOrgCode());
      // 交易编号
      httpMap.put("infno", Constant.UnifiedPay.REGISTER.UP_6301);
      // 发送方报文
      httpMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
      // 参保地医保区划
      httpMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());
      // 就医地医保区划
      httpMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
      // 定点医药机构编号
      httpMap.put("medins_code", insureConfigurationDTO.getOrgCode());
      // 医保中心编码
      httpMap.put("insur_code", insureConfigurationDTO.getRegCode());
      // 交易输入
      httpMap.put("input", dto);

      //参数校验,规则校验和请求初始化
      BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.ORD_QUERY_PAY.getCode());
      InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
      interfaceParamDTO.setHospCode(hospCode);
      interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
      interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
      // 调用统一支付平台接口
      Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.ORD_QUERY_PAY, interfaceParamDTO);
      return resultMap;
    }

  /**
     * 封装onlinePayFeeDTO参数信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 10:11
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    private OnlinePayFeeDTO handleOnlinePayData(Map<String, Object> map) {
      //线上支付费用DTO
      OnlinePayFeeDTO onlinePayFeeDTO = MapUtils.get(map, "onlinePayFeeDTO");
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      //医保配置信息
      InsureConfigurationDTO insureConfigurationDTO = MapUtils.get(map, "insureConfigurationDTO");
      //页面上传费用信息
      List<OutptCostDTO> costList = onlinePayFeeDTO.getCostList();
      //诊断信息
      List<DiseInfoDTO> diseList = onlinePayFeeDTO.getDiseinfoList();
      //费用信息
      List<FeeDetailDTO> feedetailList = onlinePayFeeDTO.getFeedetailList();
      //判断住院还是门诊 1住院
      InptVisitDTO inptVisitDTO = new InptVisitDTO();
      if ("1".equals(insureIndividualVisitDTO.getIsHospital())) {
        inptVisitDTO = inptVisitService.getInptVisitById(map).getData();
        //费用类别 02 住院
        onlinePayFeeDTO.setFeeType("02");
        //封装住院诊断信息
        diseList = handleInptDiseInfo(inptVisitDTO);
      }else{
        OutptVisitDTO outptVisitDTO =  outptVisitService.selectOutptVisitById(map).getData();
        //参数转换
        BeanUtil.copyProperties(outptVisitDTO,inptVisitDTO);
        //费用类别 01 门诊就诊
        onlinePayFeeDTO.setFeeType("01");
        //封装门诊诊断信息
        Map<String, Object> tempMap = new HashMap<>();
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(MapUtils.get(map, "hospCode"));
        outptPrescribeDTO.setVisitId(MapUtils.get(map, "visitId"));
        tempMap.put("outptPrescribeDTO",outptPrescribeDTO);
        diseList = handleOutptDiseInfo(tempMap);
      }
      onlinePayFeeDTO.setDiseinfoList(diseList);
      //险种类型
      onlinePayFeeDTO.setInsutype(insureIndividualVisitDTO.getAae140());
      //医疗机构订单号
      onlinePayFeeDTO.setMedOrgOrd(SnowflakeUtils.getId());
      //电子处方流转标志 1：电子处方 ，0 不是电子处方，默认 0，
      onlinePayFeeDTO.setRxCircFlag("0");
      //就诊时间
      onlinePayFeeDTO.setBegntime(insureIndividualVisitDTO.getVisitTime());
      //证件号码
      onlinePayFeeDTO.setIdNo(insureIndividualVisitDTO.getMdtrtCertNo());
      //参保人姓名
      onlinePayFeeDTO.setUserName(insureIndividualVisitDTO.getAac003());
      //证件类型
      onlinePayFeeDTO.setIdType(inptVisitDTO.getCertCode());
      //住院门诊号
      onlinePayFeeDTO.setIptOtpNo(insureIndividualVisitDTO.getVisitNo());
      //医师编码
      onlinePayFeeDTO.setAtddrNo(insureIndividualVisitDTO.getPracCertiNo());
      //科室编码
      onlinePayFeeDTO.setDeptCode(insureIndividualVisitDTO.getVisitDrptId());
      onlinePayFeeDTO.setDeptName(insureIndividualVisitDTO.getVisitDrptName());
      onlinePayFeeDTO.setCaty(insureIndividualVisitDTO.getVisitDrptName());
      //就诊ID
      onlinePayFeeDTO.setMdtrtId(insureIndividualVisitDTO.getMedicalRegNo());
      //医疗类别
      onlinePayFeeDTO.setMedType(insureIndividualVisitDTO.getAka130());
      //医疗总费用
      BigDecimal totalPrice = costList.stream().map(OutptCostDTO::getPrice).reduce(BigDecimal.ZERO,
          BigDecimal::add);
      onlinePayFeeDTO.setMedfeeSumamt(totalPrice);
      //个人账户使用标志
      onlinePayFeeDTO.setAcctUsedFlag("0");
      //病种编码
      onlinePayFeeDTO.setDiseCodg(insureIndividualVisitDTO.getVisitIcdCode());
      //个人结算方式
      onlinePayFeeDTO.setPsnSetlway("01");
      //收费批次号
      onlinePayFeeDTO.setChrgBchno(SnowflakeUtils.getId());
      //============费用信息===========
      map.put("code", "HOSP_APPR_FLAG");
      // 医院审批标志
      String hospApprFlag = "";
      SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
      if (sysParameterDTO != null && !StringUtils.isEmpty(sysParameterDTO.getValue())) {
        String value = sysParameterDTO.getValue();
        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
        if (stringObjectMap.containsKey(insureConfigurationDTO.getRegCode())) {
          for (String key : stringObjectMap.keySet()) {
            if (key.equals(insureConfigurationDTO.getRegCode())) {
              hospApprFlag = MapUtils.get(stringObjectMap, key);
            }
          }
        }
      }
      for (FeeDetailDTO dto : feedetailList){
        //同一批次号
        dto.setChrgBchno(onlinePayFeeDTO.getChrgBchno());
        //医疗目录编码  医保目录编码
        //医院审批标志
        dto.setHospApprFlag(hospApprFlag);
        //医疗类别
        dto.setMedType(insureIndividualVisitDTO.getAka130());
        //备注
        dto.setMemo(null);
        // 医疗目录名称 医疗目录规格 todo
      }
      return onlinePayFeeDTO;
    }

    /**
     * 封装门诊诊断信息
     * @param tempMap
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 16:57
     * @return java.util.List<cn.hsa.module.outpt.fees.dto.DiseInfoDTO>
     */
    private List<DiseInfoDTO> handleOutptDiseInfo(Map<String, Object> tempMap){
      List<DiseInfoDTO> diseList = new ArrayList<>();
      List<OutptDiagnoseDTO> data = doctorPrescribeService_consumer.getOutptDiagnose(tempMap).getData();
      Integer count =0;
      if (CollectionUtil.isNotEmpty(data)) {
        for (OutptDiagnoseDTO outptDiagnoseDTO : data) {
          DiseInfoDTO dise = new DiseInfoDTO();
          //	诊断代码
          dise.setDiagCode(outptDiagnoseDTO.getDiseaseCode());
          //	诊断名称
          dise.setDiagName(outptDiagnoseDTO.getDiseaseName());
          //	诊断类别
          dise.setDiagType(outptDiagnoseDTO.getTypeCode());
          //	诊断排序号
          dise.setDiagSrtNo(count);
          //诊断时间
          dise.setDiagTime(outptDiagnoseDTO.getCrteTime());
          //诊断医生编码
          dise.setDiseDorNo(outptDiagnoseDTO.getDoctorId());
          //诊断医生名称
          dise.setDiseDorName(outptDiagnoseDTO.getDoctorName());
          //诊断科室
          dise.setDiagDept(outptDiagnoseDTO.getDeptName());
          // 有效值
          dise.setValiFlag("1");
          count++;
          diseList.add(dise);
        }
      }
      return diseList;
    }

    /**
     * 封装住院诊断信息
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 16:54
     * @return cn.hsa.module.outpt.fees.dto.OnlinePayFeeDTO
     */
    private List<DiseInfoDTO> handleInptDiseInfo(InptVisitDTO inptVisitDTO){
      List<DiseInfoDTO> diseList = new ArrayList<>();
      Map<String, Object> tempMap = new HashMap<>();
      tempMap.put("inptVisitDTO",inptVisitDTO);
      List<InptDiagnoseDTO> data = doctorAdviceService_consumer.getInptDiagnose(tempMap).getData();
      Integer count =0;
      if (CollectionUtil.isNotEmpty(data)) {
        for (InptDiagnoseDTO inptDiagnoseDTO : data) {
          DiseInfoDTO dise = new DiseInfoDTO();
          //	诊断代码
          dise.setDiagCode(inptDiagnoseDTO.getDiseaseCode());
          //	诊断名称
          dise.setDiagName(inptDiagnoseDTO.getDiseaseName());
          //	诊断类别
          dise.setDiagType(inptDiagnoseDTO.getTypeCode());
          //	诊断排序号
          dise.setDiagSrtNo(count);
          //诊断时间
          dise.setDiagTime(inptDiagnoseDTO.getCrteTime());
          //诊断医生编码
          dise.setDiseDorNo(inptDiagnoseDTO.getZzDoctorId());
          //诊断医生名称
          dise.setDiseDorName(inptDiagnoseDTO.getZzDoctorName());
          //诊断科室
          dise.setDiagDept(inptDiagnoseDTO.getInDeptName());
          // 有效值
          dise.setValiFlag("1");
          count++;
          diseList.add(dise);
        }
      }
      return diseList;
    }

  // 封装抢救信息
    private List<Map<String, Object>> handleQjData(List<OutptDiagnoseDTO> qjList) {
        List<Map<String, Object>> rescinfo = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("resc_begntime", DateUtils.getNow()); //抢救开始时间
        map.put("resc_endtime", DateUtils.getNow()); //抢救结束时间
        map.put("er_resc_rec", " "); //急诊抢救记录
        map.put("resc_psn_list", "管理员,医生1"); //参加抢救人员名单
        map.put("vali_flag", Constants.SF.S); //有效标志
        rescinfo.add(map);
        return rescinfo;
    }

    // 封装手术信息
    private List<Map<String, Object>> handleSsData(List<OperInfoRecordDTO> ssList) {
        List<Map<String, Object>> oprninfo = new ArrayList<>();
        if (!ListUtils.isEmpty(ssList)) {
            ssList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("mdtrt_sn", dto.getVisitId()); //就医流水号
                if (!Constants.BRLX.PTBR.equals(dto.getPatientCode())) {
                    if (StringUtils.isNotEmpty(dto.getAac001()) && StringUtils.isNotEmpty(dto.getMedicalRegNo())) {
                        map.put("mdtrt_id", dto.getMedicalRegNo()); //就诊ID，医保登记号
                        map.put("psn_no", dto.getAac001()); //人员编号，个人电脑号
                    } else {
                        throw new RuntimeException("【" + dto.getName() + "】为医保病人，请先进行医保登记");
                    }
                }
                map.put("medrcdno", dto.getOutProfile()); //病历号
                for (int i = 1; i <= ssList.size(); i++) {
                    map.put("oprn_oprt_sn", i); //手术操作序号
                }
                map.put("oprn_oprt_code", dto.getOperDiseaseCode()); //手术操作代码
                map.put("oprn_oprt_name", dto.getOperDiseaseName()); //手术操作名称
                map.put("oprn_oprt_tagt_part_name", " "); //手术操作目标部位名称
                map.put("itvt_name", " "); //介入物名称
                map.put("oprn_oprt_mtd_dscr", dto.getContent()); //手术及操作方法描述
                map.put("oprn_oprt_cnt", 1); //手术及操作次数
                if (dto.getOperEndTime() != null) {
                    map.put("oprn_oprt_time", dto.getOperEndTime()); //手术及操作时间
                } else {
                    throw new RuntimeException("患者【" + dto.getName() + "】的【" + dto.getOperDiseaseName() + "】未完成登记，请先完成手术相关信息");
                }
                map.put("vali_flag", Constants.SF.S); //有效标志
                oprninfo.add(map);
            });
        }
        return oprninfo;
    }

    // 封装处方信息
    private List<Map<String, Object>> handleCfData(List<OutptPrescribeDetailsExtDTO> cfList) {
        List<Map<String, Object>> rxinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(cfList)) {
            cfList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("rxno", dto.getOrderNo()); //处方号
                map.put("rx_prsc_time", dto.getCrteTime()); //处方开方时间
                map.put("rx_type_code", dto.getTypeCode()); //处方类别代码
                map.put("rx_item_type_code", dto.getItemCode()); //处方项目分类代码
                map.put("rx_item_type_name", dto.getItemCodeName()); //处方项目分类名称
                map.put("rx_detl_id", dto.getItemId()); //处方明细代码
                map.put("rx_detl_name", dto.getItemName()); //处方明细名称
                map.put("tcmdrug_type_name", null); //中药类别名称
                map.put("tcmdrug_type_code", null); //中药类别代码
                map.put("tcmherb_foote", dto.getHerbNoteName()); //草药脚注
                map.put("medn_type_code", dto.getDrugTypeCode()); //药物类型代码
                map.put("medn_type_name", dto.getDrugTypeName()); //药物类型
                map.put("drug_dosform", dto.getPrepCode()); //药品剂型
                map.put("drug_dosform_name", dto.getPrepName()); //药品剂型名称
                map.put("drug_spec", dto.getSpec()); //药品规格
                map.put("drug_used_frqu", dto.getRateName()); //药物使用-频率
                map.put("drug_used_idose", dto.getDosage()); //药物使用-总剂量
                map.put("drug_used_sdose", dto.getDosage()); //药物使用-次剂量
                map.put("drug_used_dosunt", dto.getDosageUnitName()); //药物使用-剂量单位
                map.put("drug_used_way_code", dto.getUsageCode()); //药物使用-途径代码，即用药方式
                map.put("drug_medc_way", dto.getUsageCode()); //药物使用-途径，即用药方式
                map.put("skintst_dicm", dto.getIsSkin()); //皮试判别
                map.put("medc_begntime", dto.getMinPlanExecDate()); //用药开始时间
                map.put("medc_endtime", dto.getMaxPlanExecDate()); //用药停止日期时间
                map.put("medc_days", dto.getUseDays()); //用药天数
                map.put("main_medc_flag", dto.getUsageName()); //主要用药标志
                map.put("urgt_flag", null); //加急标志
                map.put("unif_purc_drug", null); //统一采购药品
                map.put("drug_purc_code", null); //药品采购代码
                map.put("drug_mgt_plaf_code", null); //药品管理平台代码
                map.put("bas_medn_flag", null); //基本药物标志
                map.put("vali_flag", Constants.SF.S); //有效标志
                rxinfo.add(map);
            });
        }
        return rxinfo;
    }

    // 封装诊断信息
    private List<Map<String, Object>> handleZdData(List<OutptDiagnoseDTO> zdList) {
        List<Map<String, Object>> diseinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(zdList)) {
            zdList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("tcm_diag_flag", null); //中医诊断标志
                map.put("maindiag_flag", dto.getIsMain()); //主诊断标志
                map.put("diag_code", dto.getDiseaseCode()); //诊断代码
                map.put("diag_name", dto.getDiseaseName()); //诊断名称
                map.put("tcm_dise_code", null); //中医病名代码
                map.put("tcm_dise_name", null); //中医病名名称
                map.put("tcmsymp_code", null); //中医证候代码
                map.put("tcmsymp", null); //中医证候
                map.put("vali_flag", Constants.SF.S); //有效标志
                diseinfo.add(map);
            });
        }
        return diseinfo;
    }

    // 封装病历信息
    private List<Map<String, Object>> handleBlData(List<OutptMedicalRecordDTO> blList) {
        List<Map<String, Object>> caseinfo = new ArrayList<>();
        if (!ListUtils.isEmpty(blList)) {
            blList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("mdtrt_date", dto.getVisitTime()); // 就诊日期
                map.put("chfcomp", dto.getChiefComplaint()); // 主诉
                map.put("attk_date_time", dto.getStartDate()); // 发病日期时间
                map.put("mdtrt_rea", null); // 就诊原因
                map.put("illhis", dto.getPresentIllness()); // 病史
                map.put("algs", dto.getAllergyHistory()); // 过敏史
                map.put("aise_code", null); // 过敏源代码
                map.put("phex", null); // 查体
                map.put("disa_info_code", null); // 残疾情况代码
                map.put("symp_name", null); // 症状名称
                map.put("symp_code", null); // 症状代码
                map.put("dspo_opnn", dto.getHandleSuggestion()); // 处置意见
                map.put("dept_code", dto.getDeptCode()); // 科室代码
                map.put("dept_name", dto.getDeptName()); // 科室名称
                map.put("vali_flag", Constants.SF.S); // 有效标志
                caseinfo.add(map);
            });
        }
        return caseinfo;
    }

    // 封装挂号信息
    private Map<String, Object> handleRegisterData(OutptRegisterDTO outptRegisterDTO) {
        Map<String, Object> rgstinfo = new HashMap();
        rgstinfo.put("mdtrt_sn", outptRegisterDTO.getVisitId()); // 就医流水号
        if (!Constants.BRLX.PTBR.equals(outptRegisterDTO.getPatientCode())) {
            if (StringUtils.isNotEmpty(outptRegisterDTO.getAac001()) && StringUtils.isNotEmpty(outptRegisterDTO.getMedicalRegNo())) {
                rgstinfo.put("mdtrt_id", outptRegisterDTO.getMedicalRegNo()); // 就诊ID，医保登记号
                rgstinfo.put("psn_no", outptRegisterDTO.getAac001()); // 人员编号
            } else {
                throw new RuntimeException("【" + outptRegisterDTO.getName() + "】为医保病人，请先进行医保登记");
            }
        }
        rgstinfo.put("rgst_type_code", outptRegisterDTO.getVisitCode()); // 挂号类别代码
        rgstinfo.put("rgst_way_code", outptRegisterDTO.getSourceBzCode()); // 挂号方式代码
        rgstinfo.put("rgst_serv_fee", outptRegisterDTO.getRealityPrice()); // 挂号费
        rgstinfo.put("ordr_way_code", outptRegisterDTO.getSourceTjCode()); // 预约途径代码
        rgstinfo.put("retnr_flag", outptRegisterDTO.getIsCancel()); // 退号标志
        rgstinfo.put("fstdiag_flag", outptRegisterDTO.getIsFirstVisit()); // 初诊标志
        rgstinfo.put("mdtrt_flag", outptRegisterDTO.getIsVisit()); // 就诊标志
        rgstinfo.put("rgst_retnr_time", outptRegisterDTO.getRegisterTime()); // 挂号/退号时间
        rgstinfo.put("medfee_paymtd_code", null); // 医疗费用支付方式代码
        rgstinfo.put("vali_flag", Constants.SF.S); // 有效标志
        return rgstinfo;
    }

}

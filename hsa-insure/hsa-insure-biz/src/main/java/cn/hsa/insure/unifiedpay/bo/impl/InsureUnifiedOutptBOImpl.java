package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dao.BaseDeptDAO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureItemMatchDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedOutptBO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dao.PayOnlineInfoDAO;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.fees.dto.DiseInfoDTO;
import cn.hsa.module.outpt.fees.entity.PayOnlineInfoDO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dao.SysUserDAO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yhtech.nmpay.common.client.YhGatewayClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    @Resource
    private InsureItemMatchDAO insureItemMatchDAO;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    @Resource
    private SysParameterDAO  sysParameterDAO;

    @Resource
    private BaseDeptDAO baseDeptDAO;

    @Resource
    private SysUserDAO sysUserDAO;

    @Resource
    private PayOnlineInfoDAO payOnlineInfoDAO;


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
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      String hospCode = MapUtils.get(map, "hospCode");
      logger.info("UP_6201-页面入参map-{}",  JSON.toJSONString(map));
      //根据医院编码、医保机构编码查询医保配置信息
      InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
      configDTO.setHospCode(hospCode);
      configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
      InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
      if (insureConfigurationDTO == null) throw new RuntimeException("未发现【" + insureIndividualVisitDTO.getInsureRegCode() + "】相关医保配置信息");

      //封装onlinePayFeeDTO参数信息
      map.put("insureConfigurationDTO",insureConfigurationDTO);
      OnlinePayFeeDTO onlinePayFeeDTO = handleOnlinePayData(map);
      logger.info("UP_6201-封装onlinePayFeeDTO参数信息-{}",  JSON.toJSONString(onlinePayFeeDTO));
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
      //设置请求url
      interfaceParamDTO.setUrl("http://10.103.161.181:8082/org/local/api/hos/uldFeeInfo");
      // 调用统一支付平台接口
      Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.ONLINE_FEE_PAY, interfaceParamDTO);
      Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
      Map<String, Object> resultDataMap = MapUtils.get(outptMap, "data");
      //修改表信息
      if (ObjectUtil.isNotEmpty(resultDataMap)){
        //插入医保费用表
        insertInsureCost(onlinePayFeeDTO,insureIndividualVisitDTO,map);
        //完善医保就诊表信息
        updateInsureVisitInfo(resultDataMap,insureIndividualVisitDTO);
      }
      return resultMap;
    }

    private void  updateInsureVisitInfo(Map<String, Object> result, InsureIndividualVisitDTO insureIndividualVisitDTO){
      InsureIndividualVisitDO insureIndividualVisitDO = new InsureIndividualVisitDO();
      insureIndividualVisitDO.setVisitId(insureIndividualVisitDTO.getVisitId());
      insureIndividualVisitDO.setHospCode(insureIndividualVisitDTO.getHospCode());
      insureIndividualVisitDO.setPayOrdId(MapUtils.get(result,"payOrdId"));
      insureIndividualVisitDO.setPayToken(MapUtils.get(result, "payToken"));
      insureIndividualVisitDO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());
      Map<String,Object> insureVisitMap = new HashMap<String,Object>();
      insureVisitMap.put("hospCode",insureIndividualVisitDTO.getHospCode());
      insureVisitMap.put("insureIndividualVisitDO",insureIndividualVisitDO);
      insureIndividualVisitService_consumer.editInsureIndividualVisit(insureVisitMap);
    }

    /**
     * 费用上传成功  插入医保费用表
     * @Author 医保开发二部-湛康
     * @Date 2022-05-18 9:17
     * @return void
     */
    private void insertInsureCost(OnlinePayFeeDTO onlinePayFeeDTO,
                                  InsureIndividualVisitDTO insureIndividualVisitDTO,Map map){
      List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
      List<FeeDetailDTO> feedetailList = onlinePayFeeDTO.getFeedetailList();
      Integer flag = 0;
      for (FeeDetailDTO dto : feedetailList){
        InsureIndividualCostDTO insureIndividualCostDTO = new InsureIndividualCostDTO();
        insureIndividualCostDTO = new InsureIndividualCostDTO();
        insureIndividualCostDTO.setId(SnowflakeUtils.getId());
        insureIndividualCostDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        insureIndividualCostDTO.setVisitId(insureIndividualVisitDTO.getVisitId());//就诊id
        insureIndividualCostDTO.setSettleId(null);
        insureIndividualCostDTO.setBatchNo(onlinePayFeeDTO.getChrgBchno());
        insureIndividualCostDTO.setIsHospital(Constants.SF.F);
        insureIndividualCostDTO.setItemType(dto.getItemtype());
        insureIndividualCostDTO.setItemCode(dto.getMedListCodg());
        insureIndividualCostDTO.setItemName(dto.getMedListName());
        insureIndividualCostDTO.setCostId(dto.getCostId());//费用id
        insureIndividualCostDTO.setFeedetlSn(SnowflakeUtils.getId()); //
        insureIndividualCostDTO.setApplyLastPrice(null);
        insureIndividualCostDTO.setOrderNo(flag.toString()); // 顺序号
        insureIndividualCostDTO.setTransmitCode("1"); // 是否传输标志
        insureIndividualCostDTO.setCrteId(MapUtils.get(map, "crteId"));
        insureIndividualCostDTO.setRxSn(null);
        insureIndividualCostDTO.setInsureSettleId(null);
        insureIndividualCostDTO.setCrteName(MapUtils.get(map, "crteName"));
        insureIndividualCostDTO.setCrteTime(DateUtils.getNow());
        insureIndividualCostDTO.setInsureRegisterNo(insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
        insureIndividualCostDTO.setIsHalfSettle(Constants.SF.F); // 是否中途结算
        insureIndividualCostDTO.setInsureIsTransmit("1");
        insureIndividualCostDTO.setPricUplmtAmt(null);
        insureIndividualCostDTO.setHiLmtpric(null);
        insureIndividualCostDTO.setOverlmtSelfpay(null);
        insureIndividualCostDTO.setPrimaryPrice(null); // 上传到医保的费用
        insureIndividualCostDTO.setGuestRatio(null); // 自付比例
        insureIndividualCostDTO.setSumFee(null); // 本次上传到医保的费用总金额
        insureIndividualCostDTO.setFeeStartTime(null);
        insureIndividualCostDTO.setFeeEndTime(null);
        insureIndividualCostDTO.setSettleCount(0); // 中途结算次数
        individualCostDTOList.add(insureIndividualCostDTO);
        flag ++;
      }
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
      logger.info("UP_6301-页面组合入参map-{}",  JSON.toJSONString(map));
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      //入参信息
      OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
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
      SetlResultQueryDTO dto = new SetlResultQueryDTO();
      dto.setPayOrdId(insureIndividualVisitDTO.getPayOrdId());
      dto.setOrgCodg(insureConfigurationDTO.getOrgCode());
      dto.setPayToken(insureIndividualVisitDTO.getPayToken());
      dto.setIdNo(outptVisitDTO.getCertNo());
      dto.setIdType(outptVisitDTO.getCertCode());
      dto.setUserName(outptVisitDTO.getName());
      map1.put("data",dto);
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
      httpMap.put("input", map1);
      logger.info("UP_6301-医保接口入参map-{}",  JSON.toJSONString(map));
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
     * 费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 14:11
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_6401(Map<String, Object> map) {
      logger.info("UP_6401-页面入参map-{}",  JSON.toJSONString(map));
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
      SetlResultQueryDTO dto = new SetlResultQueryDTO();
      dto.setPayOrdId(insureIndividualVisitDTO.getPayOrdId());
      dto.setOrgCodg(insureConfigurationDTO.getOrgCode());
      dto.setPayToken(insureIndividualVisitDTO.getPayToken());
      dto.setIdNo(setlResultQueryDTO.getIdNo());
      dto.setIdType(setlResultQueryDTO.getIdType());
      dto.setUserName(setlResultQueryDTO.getUserName());
      map1.put("data",dto);
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
      httpMap.put("input", map1);
      logger.info("UP_6401-医保接口入参map-{}",  JSON.toJSONString(map));

      //参数校验,规则校验和请求初始化
      BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.ONLINE_FEE_REVOKE.getCode());
      InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
      interfaceParamDTO.setHospCode(hospCode);
      interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
      interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
      // 调用统一支付平台接口
      Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.ONLINE_FEE_REVOKE, interfaceParamDTO);
      return resultMap;
    }

    /**
     * 医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-17 10:01
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_6203(Map<String, Object> map) {
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      //结算信息
      OutptSettleDTO outptSettleDTO = MapUtils.get(map, "outptSettleDTO");
      //医保结算信息
      InsureIndividualSettleDTO insureIndividualSettleDTO = MapUtils.get(map, "insureIndividualSettleDTO");
      //根据医院编码、医保机构编码查询医保配置信息
      InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
      configDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
      configDTO.setRegCode(insureIndividualSettleDTO.getInsureRegCode());
      InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
      if (insureConfigurationDTO == null)
        throw new RuntimeException("未发现【" + insureIndividualSettleDTO.getInsureRegCode() + "】相关医保配置信息");
      //封装参数信息
      SetlRefundQueryDTO query = new SetlRefundQueryDTO();
      //退费时间
      query.setAppRefdTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
      //应用退费流水号
      query.setAppRefdSn(SnowflakeUtils.getId());
      //现金支付  todo 订单查询接口获取到现金支付的钱  填写到医保结算表的restsPrice字段
      query.setCashRefdAmt(insureIndividualSettleDTO.getRestsPrice());
      //电子凭证授权Token
      query.setEcToken(outptSettleDTO.getEcToken());
      //支付订单号
      query.setPayOrdId(insureIndividualVisitDTO.getPayOrdId());
      //总费用
      query.setTotlRefdAmt(insureIndividualSettleDTO.getTotalPrice());
      //退费类型 全部退 ALL:全部，CASH:只退现金 HI:只退医保
      query.setRefdType("ALL");
      Map map1 = new HashMap();
      map1.put("data",query);
      Map httpMap = new HashMap();
      httpMap.put("hospCode",insureIndividualVisitDTO.getHospCode());
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
      httpMap.put("input", map1);
      logger.info("UP_6203-医保接口入参map-{}",  JSON.toJSONString(httpMap));

      //参数校验,规则校验和请求初始化
      BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INSURE_REFUND.getCode());
      InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(httpMap);
      interfaceParamDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
      interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
      interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
      // 调用统一支付平台接口
      Map<String, Object> resultMap = insureItfBO.executeInsur(FunctionEnum.INSURE_REFUND, interfaceParamDTO);
      return resultMap;
    }

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:38
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> AmpRefund(Map<String, Object> map) {
      //医保信息
      InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
      //页面入参
      SetlRefundQueryDTO setlRefundQueryDTO = MapUtils.get(map, "setlRefundQueryDTO");
      //根据医院编码、医保机构编码查询医保配置信息
      InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
      configDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
      configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
      InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
      if (insureConfigurationDTO == null)
        throw new RuntimeException("未发现【" + insureIndividualVisitDTO.getInsureRegCode() + "】相关医保配置信息");
      Map paramMap = new HashMap();
      paramMap.put("hospCode",insureIndividualVisitDTO.getHospCode());
      // 一、初始化Client
      YhGatewayClient yhGatewayClient = CreateYHGatewayClient(paramMap);
      // 二、入参拼装
      JSONObject param = new JSONObject() {{
        // 区域医保服务平台系统跟踪号
        put("ampTraceId", setlRefundQueryDTO.getVisitId());
        // 平台结算跟踪号
        put("traceId", setlRefundQueryDTO.getSettleId());
        // 机构交易订单号
        put("orgTraceNo", SnowflakeUtils.getId());
        // 机构编号(国标医院编码)
        put("orgCode", insureConfigurationDTO.getOrgCode());
        // 分院编号
        put("subOrgCode", null);
        // 退款原因
        put("refundReason", "退款");
        // 退款类型如果传02，则只支持全额退款（医保和自费全额进行退款） todo 默认全退
        put("refundType", null);
        // 外部退款流水号，到时候查询退款结果根据此订单号可以进行查询，每个机构内保证唯一 todo 系统退款ID
        put("outRefundNo", setlRefundQueryDTO.getRedSettleId());
        // 机构退款流水号  todo 系统退款ID
        put("orgRefundSn", setlRefundQueryDTO.getRedSettleId());
      }};
      //请求方法说明：第一个形参填写接口定义的url，第二个形参填入请求的入参，第三个形参填入出参需要转换成什么类（建议自己定义一个DTO进行接收）
      com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject> gatewayResponse = yhGatewayClient.common(
          "/api/amp/hos/refund", param,
          new TypeReference<com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject>>() {
          }
      );
      boolean requestSuccess = gatewayResponse.isSuccess();
      JSONObject outParam = new JSONObject();
      if (requestSuccess) {
        outParam = gatewayResponse.getParam();
        // 出参接收与处理
        //todo 写表操作
        Map<String, Object> response = new HashMap<>();
        response.put("outParam",outParam);
        response.put("inParam",param);
        return response;
      } else {
        log.warn("请求失败,错误码:{},错误信息{}", gatewayResponse.getRespCode(), gatewayResponse.getRespMsg());
        throw new AppException("请求省医保移动支付官方门户失败,失败编码:"+gatewayResponse.getRespCode()+",失败原因:"+gatewayResponse.getRespMsg());
      }
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method refundInquiry
     * @author wang'qiao
     * @date 2022/6/20 14:55
     * @description 查询退款结果（AMP_HOS_003） 调用AMP_HOS_002平台退款申请接口后，根据此状态来查询对应的退款具体结果
     **/
    @Override
    public Map<String, Object> refundInquiry(Map<String, Object> map) {
        //医保信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        //医保结算信息
        InsureIndividualSettleDTO insureIndividualSettleDTO = MapUtils.get(map, "insureIndividualSettleDTO");
        //页面入参
        SetlRefundQueryDTO setlRefundQueryDTO = MapUtils.get(map, "setlRefundQueryDTO");
        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        configDTO.setRegCode(insureIndividualSettleDTO.getInsureRegCode());
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null)
            throw new RuntimeException("未发现【" + insureIndividualSettleDTO.getInsureRegCode() + "】相关医保配置信息");
        Map paramMap = new HashMap();
        paramMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
        // 一、初始化Client
        YhGatewayClient yhGatewayClient = CreateYHGatewayClient(paramMap);
        // 二、入参拼装
        JSONObject param = new JSONObject() {{
            // 区域医保服务平台系统跟踪号
            put("ampTraceId", insureIndividualVisitDTO.getVisitId());
            // 平台结算跟踪号
            put("traceId", insureIndividualSettleDTO.getId());
            // 机构编号(国标医院编码)
            put("orgCode", insureConfigurationDTO.getOrgCode());
            // 分院编号
            put("subOrgCode", null);
            // 外部退款流水号，到时候查询退款结果根据此订单号可以进行查询，每个机构内保证唯一 todo 系统退款ID
            put("outRefundNo", setlRefundQueryDTO.getRedSettleId());
        }};
        //请求方法说明：第一个形参填写接口定义的url，第二个形参填入请求的入参，第三个形参填入出参需要转换成什么类（建议自己定义一个DTO进行接收）
        com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject> gatewayResponse = yhGatewayClient.common(
                "/api/amp/hos/queryRefundResult", param,
                new TypeReference<com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject>>() {
                }
        );
        boolean requestSuccess = gatewayResponse.isSuccess();
        JSONObject outParam = new JSONObject();
        if (requestSuccess) {
            outParam = gatewayResponse.getParam();
            // 出参接收与处理
            //todo 写表操作
            log.info("响应出参：{}", JSON.toJSONString(outParam));
            RefundResponseDTO dto = FastJsonUtils.fromJson(outParam.toJSONString(), RefundResponseDTO.class);
        } else {
            log.warn("请求失败,错误码:{},错误信息{}", gatewayResponse.getRespCode(), gatewayResponse.getRespMsg());
            throw new AppException("请求省医保移动支付官方门户失败,失败编码:" + gatewayResponse.getRespCode() + ",失败原因:" + gatewayResponse.getRespMsg());
        }
        return outParam;
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method reconciliationDocument
     * @author wang'qiao
     * @date 2022/6/20 19:48
     * @description 对账文件获取  下载后定点医疗机构可自行解析此对账文件并与定点机构的对账文件和医保核心的对账文件进行三方账目的对账
     **/
    @Override
    public Map<String, Object> reconciliationDocument(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode").toString();
        String orgCode = MapUtils.get(map, "orgCode").toString();

        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode);
        configDTO.setOrgCode(orgCode);
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null)
            throw new RuntimeException("未发现【" + orgCode + "】相关医保配置信息");
        Map paramMap = new HashMap();
        paramMap.put("hospCode",hospCode);
        //从系统参数中查找对账秘钥信息
        paramMap.put("codeList", new String[]{"HN_ORG_SECRET"});
        WrapperResponse<Map<String, SysParameterDTO>> wr = sysParameterService_consumer.getParameterByCodeList(paramMap);
        Map<String, SysParameterDTO> sysMap = getData(wr);
        SysParameterDTO orgSecret = MapUtils.get(sysMap, "HN_ORG_SECRET");
        // 一、初始化Client
        YhGatewayClient yhGatewayClient = CreateYHGatewayClient(paramMap);
        // 二、入参拼装
        JSONObject param = new JSONObject() {{
            // 机构编号(国标医院编码)
            put("orgCode", orgCode);
            // 分院编号
            put("subOrgCode", null);
            // 机构获取对账文件秘钥
            put("orgSecret", orgSecret.getValue()); // 系统参数 TODO 关于机构秘钥的发放对接时会进行分配
            // 账单日期，格式yyyy-MM-dd
            put("billDate", MapUtils.get(map,"reconciliationDate"));
            // 账单类型
            put("billType", "ALL");
        }};
        //请求方法说明：第一个形参填写接口定义的url，第二个形参填入请求的入参，第三个形参填入出参需要转换成什么类（建议自己定义一个DTO进行接收）
        com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject> gatewayResponse = yhGatewayClient.common(
                "/api/amp/hos/getCheckFile", param,
                new TypeReference<com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject>>() {
                }
        );
        boolean requestSuccess = gatewayResponse.isSuccess();
        JSONObject outParam = new JSONObject();
        if (requestSuccess) {
            outParam = gatewayResponse.getParam();
            // 出参接收与处理
            log.info("响应出参：{}", JSON.toJSONString(outParam));
        } else {
            log.warn("请求失败,错误码:{},错误信息{}", gatewayResponse.getRespCode(), gatewayResponse.getRespMsg());
            throw new AppException("请求省医保移动支付官方门户失败,失败编码:" + gatewayResponse.getRespCode() + ",失败原因:" + gatewayResponse.getRespMsg());
        }
        return outParam;
    }

    /**
     * 初始化Client
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 10:56
     * @return com.yhtech.nmpay.common.client.YhGatewayClient
     */
    private YhGatewayClient CreateYHGatewayClient(Map paramMap){
      paramMap.put("codeList", new String[]{"HN_URL", "HN_CLIENT_PRV_KEY", "HN_APP_ID", "HN_APP_SECRET", "HN_SERVER_PUB_KEY", "HN_YDZF_FLAG"});
      WrapperResponse<Map<String, SysParameterDTO>> wr = sysParameterService_consumer.getParameterByCodeList(paramMap);
      Map<String, SysParameterDTO> sysMap = getData(wr);
      SysParameterDTO urlPrameter = MapUtils.get(sysMap, "HN_URL");
      SysParameterDTO prvKeyPrameter = MapUtils.get(sysMap, "HN_CLIENT_PRV_KEY");
      SysParameterDTO appIdPrameter = MapUtils.get(sysMap, "HN_APP_ID");
      SysParameterDTO secretPrameter = MapUtils.get(sysMap, "HN_APP_SECRET");
      SysParameterDTO pubKeyPrameter = MapUtils.get(sysMap, "HN_SERVER_PUB_KEY");
      if (ObjectUtil.isEmpty(urlPrameter)){
        throw new RuntimeException("未配置服务网关地址");
      }
      // 一、初始化Client
      YhGatewayClient yhGatewayClient = new YhGatewayClient(urlPrameter.getValue(),
          prvKeyPrameter.getValue(),
          appIdPrameter.getValue(),
          secretPrameter.getValue(),
          pubKeyPrameter.getValue(),
          // 默认加密方式
          "SM4",
          // 默认的签名方式
          "SM3"
      );
      return yhGatewayClient;
    }


    /**
     * @param map
     * @return java.util.Map
     * @method AMP_HOS_001
     * @author wang'qiao
     * @date 2022/6/15 11:08
     * @description 通过区域医保服务平台推送消息（待结算、结算成功、检查报告、挂号通知）等信息给用户,待结算消息推送（必选）
     **/
	@Override
	public boolean AMP_HOS_001(Map<String, Object> map) {
        Map<String, String> params = new HashMap<>();
        //医保信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        //处方信息
        List<OutptCostDTO> outptCostDTOList = MapUtils.get(map, "outptCostDTOList");
        // 从处方信息中总结获得待结算金额  金额
        BigDecimal amount = new BigDecimal(0);
        for (OutptCostDTO costDTO : outptCostDTOList) {
            amount = amount.add(costDTO.getLastRealityPrice());
        }
        //就诊信息
        List outptDiagnoseDTO = MapUtils.get(map, "outptDiagnoseDTOList");
        params.put("id", insureIndividualVisitDTO.getVisitId());
        params.put("hospCode", MapUtils.get(map, "hospCode"));
        //门诊病人信息
        OutptVisitDTO outptVisitDTO = outptVisitService.queryByVisitID(params);
        //根据医院编码、医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        configDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
        InsureConfigurationDTO insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configDTO);
        if (insureConfigurationDTO == null)
            throw new RuntimeException("未发现【" + insureIndividualVisitDTO.getInsureRegCode() + "】相关医保配置信息");
        Map paramMap = new HashMap();
        paramMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
        // 一、初始化Client
        YhGatewayClient yhGatewayClient = CreateYHGatewayClient(paramMap);

        //查找科室位置
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setId(outptVisitDTO.getDeptId());
        BaseDeptDTO deptRes = baseDeptDAO.getById(baseDeptDTO);
        String deptPlace = deptRes.getPlace();
        //查询医生职称
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setId(outptVisitDTO.getDoctorId());
        SysUserDTO sysUserRes = sysUserDAO.getById(sysUserDTO);
        String drLvName = sysUserRes.getDutiesCode();

        PayOnlineInfoDTO payOnlineInfoDTO = new PayOnlineInfoDTO();
        // 二、入参拼装
        // 卡类型
        payOnlineInfoDTO.setHisCustType(outptVisitDTO.getCertCode());
        // 卡号
        payOnlineInfoDTO.setHisCustId(outptVisitDTO.getCertNo());
        // 应用类型
        payOnlineInfoDTO.setAppType("01");
        // 定点机构自己的订单ID，自己定义的id
        String medOrgOrd = SnowflakeUtils.getId();
        payOnlineInfoDTO.setMedOrgOrd(medOrgOrd);
        // 退款相关的推送才需要赋值这个
        /*put("refundReason", "退款原因，退款成功的推送才需要赋值这个~");*/
        // 省医保订单号
        /* put("payOrdId", "ORD324976159723947394273416");*/
        // 分院编号
                /*
                推送类型  因为pushType为支付订单 所以需要填
                MERCHANT_WAIT_PAY 待支付
                MERCHANT_PAID 已支付
                MERCHANT_PART_REFUNDED 已部分退款
                MERCHANT_REFUNDED 已全额退款
                */
        if (MapUtils.get(map, "pushType") != null && MapUtils.get(map, "pushType").equals("HOSPITAL_PAYMENT")) {
            // 前端默认传待支付状态
            payOnlineInfoDTO.setOrderStatus(MapUtils.get(map, "orderStatus"));
        }
        // 机构编号
        payOnlineInfoDTO.setOrgCode(insureConfigurationDTO.getOrgCode());
        //推送类型 默认为  前端传送支付单
        payOnlineInfoDTO.setPushType(MapUtils.get(map, "pushType"));
        // 用户姓名
        payOnlineInfoDTO.setPatientName(outptVisitDTO.getName());
        // 身份类型
        payOnlineInfoDTO.setIdType(outptVisitDTO.getCertCode());
        // 身份证号
        payOnlineInfoDTO.setIdNo(outptVisitDTO.getCertNo());
        // 只有医药单才需要填写
        if (MapUtils.get(map, "pushType") != null && MapUtils.get(map, "pushType").equals("HOSPITAL_MEDICINE")) {
            payOnlineInfoDTO.setTakeMedicineLoc("西药房");
        }
        // 预约时间 yyyy-MM-dd HH:mm:ss
        payOnlineInfoDTO.setScheduledTime(DateUtil.dateToString(new Date(), DateUtils.Y_M_DH_M_S));
        //重定向地址
        payOnlineInfoDTO.setRedirectUrl(null);
        //金额
        payOnlineInfoDTO.setAmount(String.valueOf(amount));
        //当推送类型是支付单或检查单或挂号单类型时候才需要填写
        if ((MapUtils.get(map, "pushType") != null && jugePushType((MapUtils.get(map, "pushType"))))) {
            // 科室名称
            payOnlineInfoDTO.setDeptName(outptVisitDTO.getDeptName());
            // 科室编码
            payOnlineInfoDTO.setDeptCode(outptVisitDTO.getDeptId());
            // 科室位置
            payOnlineInfoDTO.setDeptLoc(deptPlace);
            // 医生姓名
            payOnlineInfoDTO.setDrName(outptVisitDTO.getDoctorName());
            // 医生职级
            payOnlineInfoDTO.setDrLvName(drLvName);
            // 医生编号
            payOnlineInfoDTO.setDrLvNo(outptVisitDTO.getDoctorId());
        }
        //当推送类型是医药单或挂号单时，才需要填写此候诊号，推送提醒用户排队情况
        if (MapUtils.get(map, "pushType") != null && (MapUtils.get(map, "pushType").equals("HOSPITAL_MEDICINE") || MapUtils.get(map, "pushType").equals("HOSPITAL_APPOINTMENT"))) {
            // 排队人数 候诊号
            payOnlineInfoDTO.setWaitingNum("01");
        }
        //当推送类型是检查单 时才需要填写
        if (MapUtils.get(map, "pushType") != null && MapUtils.get(map, "pushType").equals("HOSPITAL_CHECK")) {
            // 检查项目名称
            payOnlineInfoDTO.setCheckItem("核磁共振"); // todo 项目名称
        }
        //当推送的状态为已支付或已退款或已部分退款类型的状态时候才需要填写
        if (MapUtils.get(map, "orderStatus") != null && jugeOrderStatus(MapUtils.get(map, "orderStatus"))) {
            // 订单创建时间
            payOnlineInfoDTO.setCreateTime("2022-03-30 13:15:46"); // todo 订单时间
            // 订单更新时间
            payOnlineInfoDTO.setUpdateTime("2022-03-30 13:15:46"); //  todo 订单时间
        }
        payOnlineInfoDTO.setAmpTraceId("");
        payOnlineInfoDTO.setTraceId("");
        payOnlineInfoDTO.setOrgTraceNo("");
        // bean对象转化为json对象
        JSONObject param = new JSONObject(MapUtils.object2Map(payOnlineInfoDTO));


        PayOnlineInfoDO payOnlineInfoDO = new PayOnlineInfoDO();
        // 属性拷贝
        BeanUtils.copyProperties(payOnlineInfoDTO, payOnlineInfoDO);


        // ！！！请求方法说明：第一个形参填写接口定义的url，第二个形参填入请求的入参，第三个形参填入出参需要转换成什么类（建议自己定义一个DTO进行接收）
        com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject> gatewayResponse = yhGatewayClient.common(
                "/api/amp/hos/pushMsg",
                param,
                new TypeReference<com.yhtech.yhaf.core.dto.WrapperResponse<JSONObject>>() {
                }
        );
        boolean requestSuccess = gatewayResponse.isSuccess();
        if (requestSuccess) {
            JSONObject outParam = gatewayResponse.getParam();
            // 出参接收与处理
            logger.info("响应出参：{}", JSON.toJSONString(outParam));
            // 接收成功后的处理 存入本地信息推送表
            payOnlineInfoDAO.insertPayOnlineInfo(payOnlineInfoDO);

        } else {
            logger.warn("请求失败,错误码:{},错误信息{}", gatewayResponse.getRespCode(), gatewayResponse.getRespMsg());
            throw new AppException("请求远程接口失败："+ gatewayResponse.getRespMsg());
        }

        return requestSuccess;
	}

    /**
      * @method jugePushType
      * @author wang'qiao
      * @date 2022/6/20 8:46
      *	@description 当推送类型是支付单或检查单或挂号单类型时候返回true
      * @param  pushType
      * @return boolean
      *
     **/
    public static boolean jugePushType(String pushType){
        if(pushType == null){
            return  false;
        }
        if(pushType.equals("HOSPITAL_APPOINTMENT") || pushType.equals("HOSPITAL_CHECK") || pushType.equals("HOSPITAL_PAYMENT")){
            return true;
        }
        return false;
    }

    /**
      * @method jugeOrderStatus
      * @author wang'qiao
      * @date 2022/6/20 9:29
      *	@description 	判断支付状态orderStatus不是 待支付
      * @param  orderStatus
      * @return boolean
      *
     **/
    public static boolean jugeOrderStatus(String orderStatus) {
        if (orderStatus == null) {
            return false;
        }
        if (orderStatus.equals("MERCHANT_PAID") || orderStatus.equals("MERCHANT_PART_REFUNDED") || orderStatus.equals("MERCHANT_REFUNDED")) {
            return true;
        }
        return false;
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
      List<OutptCostDTO> outptCostDTOList = onlinePayFeeDTO.getOutptCostDTOList();//患者费用
      //诊断信息
      List<DiseInfoDTO> diseList = onlinePayFeeDTO.getDiseinfoList();
      //费用信息
      List<FeeDetailDTO> feedetailList = onlinePayFeeDTO.getFeedetailList();
      //判断住院还是门诊 1住院
      InptVisitDTO inptVisitDTO = new InptVisitDTO();
      map.put("visitId",insureIndividualVisitDTO.getVisitId());
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
        tempMap.put("hospCode",MapUtils.get(map, "hospCode"));
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
      BigDecimal totalPrice = outptCostDTOList.stream().map(OutptCostDTO::getPrice).reduce(BigDecimal.ZERO,
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
      //费用详情信息
      feedetailList = handleOutFeeInfo(outptCostDTOList,insureIndividualVisitDTO,onlinePayFeeDTO,hospApprFlag);
      onlinePayFeeDTO.setFeedetailList(feedetailList);
      return onlinePayFeeDTO;
    }

    /**
     * 封装费用详情信息
     * @param outptCostDTOList
     * @Author 医保开发二部-湛康
     * @Date 2022-05-17 14:57
     * @return java.util.List<cn.hsa.module.outpt.fees.dto.DiseInfoDTO>
     */
   private List<FeeDetailDTO> handleOutFeeInfo(List<OutptCostDTO> outptCostDTOList,
                                               InsureIndividualVisitDTO insureIndividualVisitDTO,OnlinePayFeeDTO onlinePayFeeDTO, String hospApprFlag ){
     // 批量获取费用匹配信息
     Map<String, Object> itemMatchParam = new HashMap<String, Object>();
     List<String> itemIdList = new ArrayList<>();
     for (OutptCostDTO outptCostDTO : outptCostDTOList) {
       itemIdList.add(outptCostDTO.getItemId());
     }
     itemMatchParam.put("hospCode", insureIndividualVisitDTO.getHospCode());
     itemMatchParam.put("insureRegCode", insureIndividualVisitDTO.getInsureRegCode());
     itemMatchParam.put("xmid", itemIdList);
     List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryInsureMatchItemByItems(itemMatchParam);
     if (ListUtils.isEmpty(insureItemMatchDTOList)) {
       throw new AppException("未找到医保匹配的药品/项目/材料");
     }

     List<FeeDetailDTO> list = new ArrayList<>();
     for (OutptCostDTO outptCostDTO : outptCostDTOList){
       FeeDetailDTO dto = new FeeDetailDTO();
       //费用ID
       dto.setCostId(outptCostDTO.getId());
       //同一批次号
       dto.setChrgBchno(onlinePayFeeDTO.getChrgBchno());
       //处方号
       dto.setRxno(outptCostDTO.getOpId());
       //医保就诊ID
       dto.setMdtrtId(insureIndividualVisitDTO.getMedicalRegNo());
       //人员编号
       dto.setPsnNo(insureIndividualVisitDTO.getAac001());
       //费用总额
       dto.setDetItemFeeSumamt(outptCostDTO.getTotalPrice());
       //数量
       dto.setCnt(outptCostDTO.getNum());
       //单价
       dto.setPric(outptCostDTO.getPrice());
       //开单科室
       dto.setBilgDeptCodg(outptCostDTO.getDeptId());
       //开单科室名称
       dto.setBilgDeptName(outptCostDTO.getDeptName());
       //开单科室医生编号
       dto.setBilgDrCodg(outptCostDTO.getDoctorId());
       //开单科室医生名称
       dto.setBilgDrName(outptCostDTO.getDoctorName());
       //医院审批标志
       dto.setHospApprFlag(hospApprFlag);
       //医疗类别
       dto.setMedType(insureIndividualVisitDTO.getAka130());
       for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchDTOList) {
         if (insureItemMatchDTO.getHospItemId().equals(outptCostDTO.getItemId())) {
           //	医院药品项目编码
           dto.setMedinsListCodg(insureItemMatchDTO.getHospItemCode());
           //医疗目录编码
           dto.setMedListCodg(insureItemMatchDTO.getInsureItemCode());
           //医疗目录名称
           dto.setMedListName(insureItemMatchDTO.getInsureItemName());
           //医疗目录类别
           dto.setItemtype(insureItemMatchDTO.getInsureItemType());
         }
       }
       list.add(dto);
     }
     return list;
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

    protected <T> T getData(WrapperResponse wr) {
      if (wr.getCode() != 0) {
        throw new AppException(wr.getMessage());
      }
      return (T)wr.getData();
    }

}

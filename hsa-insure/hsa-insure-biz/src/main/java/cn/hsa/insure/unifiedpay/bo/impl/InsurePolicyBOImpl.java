package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.dzpz.hainan.SetlInfoDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedBaseBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.insure.other.bo.InsurePolicyBO;
import cn.hsa.module.insure.other.dto.PolicyRequestDTO;
import cn.hsa.module.insure.other.dto.PolicyResponseDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 政策项查询
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 09:38
 */
@Slf4j
@Component
public class InsurePolicyBOImpl  extends HsafBO implements InsurePolicyBO {

  @Resource
  private InsureIndividualVisitService insureIndividualVisitService_consumer;

  @Resource
  private BaseReqUtilFactory baseReqUtilFactory;

  @Resource
  private InsureConfigurationDAO insureConfigurationDAO;

  @Resource
  private InsureItfBOImpl insureItfBO;

  @Resource
  private InsureUnifiedBaseBO insureUnifiedBaseBO;

  /**
   * 政策项查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-07 9:39
   * @return java.util.List<cn.hsa.module.insure.other.dto.PolicyResponseDTO>
   */
  @Override
  public List<PolicyResponseDTO> queryInsurePolicy(Map map) {
    //参数获取
    PolicyRequestDTO policy = MapUtils.get(map,"policyRequestDTO");
    //参数校验
    if (ObjectUtil.isEmpty(policy.getCertno())){
      throw new AppException("证件号码必传！");
    }
    if (ObjectUtil.isEmpty(policy.getMdtrtId())){
      throw new AppException("医保就诊ID必传！");
    }
    if (ObjectUtil.isEmpty(policy.getSetlId())){
      throw new AppException("人员结算ID必传！");
    }
    /*if (ObjectUtil.isEmpty(policy.getVisitId())){
      throw new AppException("his就诊ID必传！");
    }*/
    Map<String, Object> insureVisitParam = new HashMap<String, Object>();
    insureVisitParam.put("medicalRegNo", policy.getMdtrtId());
    insureVisitParam.put("hospCode", MapUtils.get(map,"hospCode"));
    InsureIndividualVisitDTO insureIndividualVisitDTO =
        insureIndividualVisitService_consumer.getInsureIndividualVisitByMedRegNo(insureVisitParam);
    if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
      throw new AppException("未查找到医保就诊信息，请做医保登记。");
    }
    //获取医保配置
    InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
    insureConfigurationDTO.setHospCode(MapUtils.get(map,"hospCode"));
    // 医疗机构编码
    insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
    insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureOrgCode());
    insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    if (insureConfigurationDTO == null) {
      throw new AppException("根据" + insureIndividualVisitDTO.getInsureOrgCode() + "医保机构编码获取医保机构配置信息为空");
    }
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("setl_Id", policy.getSetlId());
    dataMap.put("psn_No", insureIndividualVisitDTO.getAac001());
    dataMap.put("mdtrt_Id", policy.getMdtrtId());
    //参数校验,规则校验和请求初始化
    BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.POLICY_ITEM_INFO.getCode());
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("hospCode",MapUtils.get(map,"hospCode"));
    paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
    paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
    paramMap.put("infno", Constant.UnifiedPay.REGISTER.UP_100001);
    //paramMap.put("input", JSONUtil.toJsonStr(policy));
    paramMap.put("input", dataMap);
    InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
    interfaceParamDTO.setHospCode(MapUtils.get(map,"hospCode"));
    interfaceParamDTO.setIsHospital(insureIndividualVisitDTO.getIsHospital());
    interfaceParamDTO.setVisitId(policy.getVisitId());
    // 调用统一支付平台接口
    Map<String, Object> result = insureItfBO.executeInsur(FunctionEnum.POLICY_ITEM_INFO, interfaceParamDTO);
    PolicyResponseDTO response = MapUtils.get(result,"output");
    List<PolicyResponseDTO> list = new ArrayList<>();
    list.add(response);
    return list;
  }
}

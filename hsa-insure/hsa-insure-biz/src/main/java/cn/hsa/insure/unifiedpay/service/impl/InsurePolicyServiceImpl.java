package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.other.bo.InsurePolicyBO;
import cn.hsa.module.insure.other.dto.PolicyResponseDTO;
import cn.hsa.module.insure.other.service.InsurePolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 政策项查询Service
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 09:04
 */
@Slf4j
@HsafRestPath("/service/insure/policy")
@Service("insurePolicyService_provider")
public class InsurePolicyServiceImpl  implements InsurePolicyService {

  @Resource
  private InsurePolicyBO insurePolicyBO;

  /**
   * 政策项查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-07 9:05
   * @return java.util.List<cn.hsa.module.insure.other.PolicyResponseDTO>
   */
  @Override
  public WrapperResponse<PageDTO>  queryInsurePolicy(Map map){
    return WrapperResponse.success(insurePolicyBO.queryInsurePolicy(map));
  }
}

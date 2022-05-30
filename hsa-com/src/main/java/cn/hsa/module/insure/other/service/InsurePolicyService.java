package cn.hsa.module.insure.other.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.other.dto.PolicyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Description: 政策项查询Service
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 08:50
 */
@FeignClient(value = "hsa-insure")
public interface InsurePolicyService {

  /**
   * 政策项查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-07 8:52
   * @return List<>
   */
  WrapperResponse<PageDTO> queryInsurePolicy(Map map);
}

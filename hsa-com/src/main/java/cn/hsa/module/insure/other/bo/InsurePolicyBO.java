package cn.hsa.module.insure.other.bo;


import cn.hsa.module.insure.other.dto.PolicyResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * @Description: 政策项查询
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 09:08
 */
public interface InsurePolicyBO {

  /**
   * 政策项查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-07 9:10
   * @return java.util.List<cn.hsa.module.insure.other.PolicyResponseDTO>
   */
  List<PolicyResponseDTO> queryInsurePolicy(Map map);
}

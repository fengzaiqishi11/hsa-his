package cn.hsa.module.inpt.costclassification.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.costclassification.service
 * @Class_name: CostClassificationService
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 14:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface CostClassificationService {

  /**
  * @Menthod queryCostByAdviceId
  * @Desrciption 根据患者查询和类型该医嘱，补记帐产生的所有费用
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
  **/
  WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostByAdviceId(Map map);

  /**
  * @Menthod updateCostAttributionCode
  * @Desrciption  修改费用的类型
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateCostAttributionCode(Map map);

}

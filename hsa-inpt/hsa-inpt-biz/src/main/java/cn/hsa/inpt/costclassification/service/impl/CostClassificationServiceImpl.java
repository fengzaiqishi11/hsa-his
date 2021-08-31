package cn.hsa.inpt.costclassification.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.costclassification.bo.CostClassificationBO;
import cn.hsa.module.inpt.costclassification.service.CostClassificationService;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.costclassification.bo.impl
 * @Class_name: CostClassificationServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 14:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/inpt/costclassification")
@Service("costClassificationService_provider")
public class CostClassificationServiceImpl extends HsafService implements CostClassificationService {

  @Resource
  private CostClassificationBO costClassificationBO;

  /**
  * @Menthod queryCostByAdviceId
  * @Desrciption 根据患者查询和类型该医嘱，补记帐产生的所有费用
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
  **/
  @Override
  public WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostByAdviceId(Map map) {
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = costClassificationBO.queryCostByAdviceId(MapUtils.get(map, "patientCompreHensiveQueryDTO"));
    return WrapperResponse.success(patientCompreHensiveQueryDTOS);
  }

  /**
  * @Menthod updateCostAttributionCode
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateCostAttributionCode(Map map) {
    return WrapperResponse.success(costClassificationBO.updateCostAttributionCode(MapUtils.get(map, "inptCostDTO")));
  }
}

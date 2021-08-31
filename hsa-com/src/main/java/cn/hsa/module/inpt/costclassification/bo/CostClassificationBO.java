package cn.hsa.module.inpt.costclassification.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.costclassification.bo
 * @Class_name: CostClassificationBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 14:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CostClassificationBO {

  /**
  * @Menthod queryCostByAdviceId
  * @Desrciption 根据患者查询和类型该医嘱，补记帐产生的所有费用
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:37
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
  **/
  List<PatientCompreHensiveQueryDTO> queryCostByAdviceId(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
  * @Menthod updateCostAttributionCode
  * @Desrciption 修改费用的类型
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:37
  * @Return java.lang.Boolean
  **/
  Boolean updateCostAttributionCode(InptCostDTO inptCostDTO);

}

package cn.hsa.module.inpt.costclassification.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.costclassification.dao
 * @Class_name: CostClassificationDAO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 14:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CostClassificationDAO {

  /**
  * @Menthod updateCostAttributionCode
  * @Desrciption  修改费用的类型
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 14:39
  * @Return java.lang.Boolean
  **/
  int updateCostAttributionCode(InptCostDTO inptCostDTO);

  /**
  * @Menthod queryItemAndDrugAndMaterialAndAdvice
  * @Desrciption 查询项目汇总
  *
  * @Param
  * [patientCompreHensiveQueryDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/31 16:49
  * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
  **/
  List<PatientCompreHensiveQueryDTO> queryItemAndDrugAndMaterialAndAdvice(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

  /**
  * @Menthod queryItemAndDrugAndMaterialDetail
  * @Desrciption 查询项目费用明细
  *
  * @Param
  * [patientCompreHensiveQueryDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/31 16:50
  * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
  **/
  List<PatientCompreHensiveQueryDTO> queryItemAndDrugAndMaterialDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO);

}

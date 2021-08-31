package cn.hsa.inpt.costclassification.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.costclassification.bo.CostClassificationBO;
import cn.hsa.module.inpt.costclassification.dao.CostClassificationDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dao.PatientComprehensiveQueryDAO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.costclassification.bo.impl
 * @Class_name: CostClassificationBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 15:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class CostClassificationBOImpl extends HsafBO implements CostClassificationBO {

  @Resource
  private CostClassificationDAO costClassificationDAO;

  @Resource
  private PatientComprehensiveQueryDAO patientComprehensiveQueryDAO;

  /**
  * @Menthod queryCostByAdviceId
  * @Desrciption 根据患者查询和类型该医嘱，补记帐产生的所有费用
  *
  * @Param
  * [inptAdviceDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 15:04
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
  **/
  @Override
  public List<PatientCompreHensiveQueryDTO> queryCostByAdviceId(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO) {
    List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS = patientComprehensiveQueryDAO.queryItemAndDrugAndMaterialAndAdvice(patientCompreHensiveQueryDTO);
    // 查询项目的明细费用
    for (PatientCompreHensiveQueryDTO item: patientCompreHensiveQueryDTOS){
      PatientCompreHensiveQueryDTO detail = new PatientCompreHensiveQueryDTO();
      detail.setVisitId(patientCompreHensiveQueryDTO.getVisitId());
      detail.setItemId(item.getItemId());
      detail.setItemCode(item.getItemCode());
      detail.setHospCode(patientCompreHensiveQueryDTO.getHospCode());
      detail.setSourceDeptId(patientCompreHensiveQueryDTO.getSourceDeptId());
      detail.setIsAdviceItem(patientCompreHensiveQueryDTO.getIsAdviceItem());
      detail.setAttributionCode(patientCompreHensiveQueryDTO.getAttributionCode());
      List<PatientCompreHensiveQueryDTO> patientCompreHensiveQueryDTOS1 = patientComprehensiveQueryDAO.queryItemAndDrugAndMaterialDetail(detail);
      item.setPatientCompreHensiveQueryDTOList(patientCompreHensiveQueryDTOS1);
    }
    return patientCompreHensiveQueryDTOS;
  }

  /**
  * @Menthod updateCostAttributionCode
  * @Desrciption 修改费用的类型
  *
  * @Param
  * [inptCostDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/8/19 15:04
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateCostAttributionCode(InptCostDTO inptCostDTO) {
    if(ListUtils.isEmpty(inptCostDTO.getIds())) {
      throw new AppException("未选择费用");
    }
    if(StringUtils.isEmpty(inptCostDTO.getAttributionCode())) {
      throw new AppException("费用需要转化的类型为空");
    }
    return costClassificationDAO.updateCostAttributionCode(inptCostDTO) > 0;
  }
}

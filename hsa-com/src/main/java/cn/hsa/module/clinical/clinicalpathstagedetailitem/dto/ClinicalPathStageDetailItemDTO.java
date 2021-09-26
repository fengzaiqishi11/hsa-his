package cn.hsa.module.clinical.clinicalpathstagedetailitem.dto;

import cn.hsa.module.clinical.clinicalpathstagedetailitem.entity.ClinicalPathStageDetailItemDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetailitem.dto
 * @Class_name: ClinicalPathStageDetailItemDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicalPathStageDetailItemDTO extends ClinicalPathStageDetailItemDO implements Serializable {
  private static final long serialVersionUID = 4652859477510050483L;
  private String type;
  private List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS;
  private List<String> ids;
}

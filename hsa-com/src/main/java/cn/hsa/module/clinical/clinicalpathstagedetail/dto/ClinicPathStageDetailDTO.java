package cn.hsa.module.clinical.clinicalpathstagedetail.dto;

import cn.hsa.module.clinical.clinicalpathstagedetail.entity.ClinicPathStageDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetail.dto
 * @Class_name: ClinicPathStageDetailDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/9 19:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClinicPathStageDetailDTO extends ClinicPathStageDetailDO implements Serializable {
  private static final long serialVersionUID = -3896859390097085660L;
  private String clinicalPathStageCode; // 路径编码
  private String itemName;
  private List<String> ids;
  private String flag;
  private String describe;
  private String listName;
  private String clinicalPathStageId; // 入径状态表id
  private String isExec;   // 是否执行
  private String printClunm;
}

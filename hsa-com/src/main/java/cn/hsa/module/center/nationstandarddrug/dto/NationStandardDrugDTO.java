package cn.hsa.module.center.nationstandarddrug.dto;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.dto
 * @Class_name: NationStandardDrugDTO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NationStandardDrugDTO extends NationStandardDrugDO implements Serializable {
  private static final long serialVersionUID = -9021175792107344203L;
  private String flag;
  private String keyword;
  private String usageDosage;
}

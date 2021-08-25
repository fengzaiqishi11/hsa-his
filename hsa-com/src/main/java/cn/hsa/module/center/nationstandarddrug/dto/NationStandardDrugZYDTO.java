package cn.hsa.module.center.nationstandarddrug.dto;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.dto
 * @Class_name: NationStandardDrugDTO
 * @Describe: 国脚标准药品信息（中药）
 * @Author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/1/26 9:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NationStandardDrugZYDTO extends NationStandardDrugZYDO implements Serializable {

  private static final long serialVersionUID = -1939235269101925674L;

  private String keyword;
  private String usageDosage;
}

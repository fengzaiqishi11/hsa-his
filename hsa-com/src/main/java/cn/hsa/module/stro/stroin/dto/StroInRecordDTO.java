package cn.hsa.module.stro.stroin.dto;

import cn.hsa.module.stro.stroin.entity.StroInRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.stro.stroin.dto
 * @Class_name: StroInRecordDtO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/12 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroInRecordDTO extends StroInRecordDO implements Serializable {
  private static final long serialVersionUID = -1209333320908543450L;
  private String keyword;
  // 平均购进价
  private BigDecimal avgBuyPrice;
}

package cn.hsa.module.outpt.outptclassify.entity;

import cn.hsa.base.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.entity
 * @Class_name: OutptClassifyCostDO
 * @Describe:   挂号类别费用数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/11 13:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutptClassifyCostDO extends PageDTO implements Serializable {
  private static final long serialVersionUID = 568394639280356026L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 挂号类别ID
   */
  private String cyId;
  /**
   * 项目类型代码（XMLB）
   */
  private String itemCode;
  /**
   * 项目ID（项目/材料）
   */
  private String itemId;
  /**
   * 项目数量
   */
  private BigDecimal num;
  /**
   * 用药性质代码
   */
  private String useCode;
}

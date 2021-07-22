package cn.hsa.module.outpt.outptclassify.dto;

import cn.hsa.module.outpt.outptclassify.entity.OutptClassifyCostDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.dto
 * @Class_name: OutptClassifyCostDTO
 * @Describe:  挂号类别费用数据传输对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/11 13:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptClassifyCostDTO extends OutptClassifyCostDO implements Serializable {
  private String itemUnitCode; //项目单位
  private String name;  //项目名称
  private BigDecimal itemPrice; //项目单价
  private BigDecimal itemPriceAll; //项目总价格
  private BigDecimal itemPreferentialPrice; //优惠金额
  private BigDecimal itemPreferentialAllPrice; //优惠后金额
  /**
   * @Description: 用于微信小程序接口
   * @Author: luoyong
   * @Email: luoyong@powersi.com.cn
   * @Date 2021-06-22 16:35
   */
  // 财务分类id
  private String bfcId;
  // 财务分类编码
  private String bfcCode;
  // 财务分类名称
  private String bfcName;
  // 规格
  private String spec;
}

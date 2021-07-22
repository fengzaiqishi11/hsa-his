package cn.hsa.module.stro.outinstro.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.stro.outinstro.entity
 * @Class_name: StroOutin
 * @Describe:  出入库映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/25 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroOutinDO extends PageDO implements Serializable {
  private static final long serialVersionUID = -68033529157226776L;
  /**
   * 主键
   */
  private String id;
  /**
   * 医院编码
   */
  private String hospCode;
  /**
   * 出入方式代码
   */
  private String outinCode;
  /**
   * 单据号
   */
  private String orderNo;
  /**
   * 入库业务ID
   */
  private String inBizId;
  /**
   * 出库业务ID
   */
  private String outBizId;
  /**
   * 购进总金额
   */
  private BigDecimal buyPriceAll;
  /**
   * 零售总金额
   */
  private BigDecimal sellPriceAll;
  /**
   * 备注
   */
  private String remark;
  /**
   * 入库审核代码
   */
  private String inAuditCode;
  /**
   * 入库审核人ID
   */
  private String inAuditId;
  /**
   * 入库审核人姓名
   */
  private String inAuditName;
  /**
   * 入库审核时间
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date inAuditTime;
  /**
   * 出库审核代码
   */
  private String outAuditCode;
  /**
   * 出库审核人ID
   */
  private String outAuditId;
  /**
   * 出库审核人姓名
   */
  private String outAuditName;
  /**
   * 出库审核时间
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date outAuditTime;
  /**
   * 创建人ID
   */
  private String crteId;
  /**
   * 创建人姓名
   */
  private String crteName;
  /**
   * 创建时间
   */
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date crteTime;
}

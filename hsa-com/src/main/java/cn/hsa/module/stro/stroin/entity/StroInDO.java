package cn.hsa.module.stro.stroin.entity;

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
public class StroInDO extends PageDO implements Serializable {
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
   * 入库方式代码（CRFS）
   */
  private String inCode;
  /**
   * 单据号
   */
  private String orderNo;
  /**
   * 库位ID
   */
  private String stockId;
  /**
   * 供应商ID
   */
  private String supplierId;
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
   * 审核状态代码（SHZT）
   */
  private String auditCode;
  /**
   * 审核人ID
   */
  private String auditId;
  /**
   * 审核人姓名
   */
  private String auditName;
  /**
   * 审核时间
   */
  private Date auditTime;
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

  // 整单出库--入库单号
  private String inOrderNo;


  private String fkrId   ;//	付款人ID
  private String fkrName;//		付款人名称
  private String fkStatusCode	;//				付款状态（0未付款，1已付款）
  private String fkRemark	;//				付款备注
  private String fkdid	;//			付款单ID
  // 时间戳转换为标准时间格式
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date fkTime ;

  /**
   * 验收状态
   */
  private String acceptanceStatus;
  /**
   * 验收时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date acceptanceTime;
  /**
   * 验收人id
   */
  private String acceptanceId;
  /**
   * 验收人名称
   */
  private String acceptanceName;
  /**
   * 质量情况
   */
  private String qualifiedStatus;
  /**
   * 验收结果
   */
  private String acceptanceResult;
}

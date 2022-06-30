package cn.hsa.module.outpt.fees.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 医保订单结算结果查询dto
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-09 14:51
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetlRefundQueryDTO implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;

  /**
   * 医保注册编码
   */
  private String insureRegCode;

  /**
   * 就诊号
   */
  private String visitId;

  /**
   * 结算NO
   */
  private String settleNo;

  /**
   * 结算ID
   */
  private String settleId;

  /**
   * 退款结算ID
   */
  private String redSettleId;

  /**
   * 支付订单号
   */
  private String payOrdId;

  /**
   * 应用退款流水号
   */
  private String appRefdSn;

  /**
   * 应用退费时间 yyyyMMddHHmmss
   */
  private String appRefdTime;

  /**
   * 总退费金额
   */
  private BigDecimal totlRefdAmt;

  /**
   * 医保个人账户支付
   */
  private BigDecimal psnAcctRefdAmt;

  /**
   * 基金支付
   */
  private BigDecimal fundRefdAmt;

  /**
   * 现金退费金额
   */
  private BigDecimal cashRefdAmt;

  /**
   * 电子凭证授权Token
   */
  private String ecToken;

  /**
   * 退费类型
   */
  private String refdType;


}

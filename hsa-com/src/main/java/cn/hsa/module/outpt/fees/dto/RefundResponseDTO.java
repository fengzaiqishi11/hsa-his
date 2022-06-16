package cn.hsa.module.outpt.fees.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-06-15 14:45
 */
@Data
public class RefundResponseDTO implements Serializable {

  /**
   * 序列号
   */
  private static final long serialVersionUID = -8142117462251296112L;

  /**
   * 平台退款流水号
   */
  private String refundTraceId;

  /**
   * 区域平台退款流水号
   */
  private String ampRefundId;

  /**
   * 支付渠道退款流水号
   */
  private String payChnlRefundSn;

  /**
   * 平台缴费单流水号
   */
  private String traceId;

  /**
   * 支付渠道流水号
   */
  private String payChnlOrdSn;

  /**
   * 省医保平台订单号
   */
  private String payOrdId;

  /**
   * 交易状态
   */
  private String tradeStatus;

}

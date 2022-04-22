package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Description: 医保电子凭证回调入参DTO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-20 16:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeltSucCallbackDTO {

  /**
   * 回调类型
   */
  String callType;

  /**
   * 支付订单号
   */
  String payOrdId;

  /**
   * 医院订单号
   */
  String medOrgOrd;

  /**
   * 交易时间
   */
  String traceTime;

  /**
   * 费用总金额
   */
  BigDecimal feeSumamt;

  /**
   * 个人现金自付
   */
  BigDecimal ownpayAmt;

  /**
   * 个人账户支付
   */
  BigDecimal psnAcctPay;

  /**
   * 其中基金支付
   */
  BigDecimal fundPay;

  /**
   * 医保收费时间
   */
  String hiChrgTime;

  /**
   * 医保交易流水号
   */
  String hiDocSn;

  /**
   * 医保挂号流水号
   */
  String hiRgstSn;

  /**
   * 机构名称
   */
  String orgName;

  /**
   * 机构编号
   */
  String orgCode;

  /**
   * 电子凭证码值
   */
  String ecCode;

  /**
   * 结算类型
   */
  String setlType;

  /**
   * 于院内结算失败对医保的冲正授权
   */
  String revsToken;
  /**
   * 额外数据
   */
  ExtDataDTO extData;

}

package cn.hsa.module.insure.other.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 政策项查询返回参数
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 08:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyResponseDTO implements Serializable {

  /**
   * 结算ID
   */
  private String setlId;
  /**
   * 就诊ID
   */
  private String mdtrtId;
  /**
   * 人员编号
   */
  private String psnNo;
  /**
   * 政策项编码
   */
  private String polItemCode;
  /**
   * 政策项名称
   */
  private String polItemName;
  /**
   * 自付金额
   */
  private BigDecimal selfPayAmt;
  /**
   * 自付比例
   */
  private BigDecimal selfPayProp;
  /**
   * 基金支付金额
   */
  private BigDecimal fundPayAmt;
  /**
   * 基金支付比例
   */
  private BigDecimal fundPayProp;
  /**
   * 政策项支付合计
   */
  private BigDecimal polItemPaySum;

}

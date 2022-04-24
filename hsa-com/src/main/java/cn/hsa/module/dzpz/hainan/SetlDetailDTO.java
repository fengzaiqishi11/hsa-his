package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Description: 结算基金分项信息
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-20 16:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetlDetailDTO {

  /**
   * 基金支付类型
   */
  private String fundpaytype;

  /**
   * 符合政策范围金额
   */
  private BigDecimal inscpscpamt;

  /**
   * 本次可支付限额金额
   */
  private BigDecimal crtpayblmtamt;

  /**
   * 基金支付金额
   */
  private BigDecimal fundpayamt;

  /**
   * 基金支付类型名称
   */
  private String fundpaytypename;

  /**
   * 结算过程信息
   */
  private String setlprocinfo;
}

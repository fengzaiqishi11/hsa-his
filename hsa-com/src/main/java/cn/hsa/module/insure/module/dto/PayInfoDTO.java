package cn.hsa.module.insure.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PayInfoDTO implements Serializable {

    private String fundPayType; //基金支付类型

    private BigDecimal fundPayamt; // 基金支付金额

    private BigDecimal planPrice; // 统筹基金支付

    private String aka130; // 基金支付类型

    private BigDecimal totalPrice; // 总费用

    private BigDecimal insurePrice; // 医保支付

    private BigDecimal seriousPrice; // 大病互助支付

    private BigDecimal civilPrice; // 公务员补助支付

    private BigDecimal retirePrice; // 离休基金支付

    private BigDecimal personalPrice; // 个人账户支付

    private BigDecimal personPrice; // 个人支付

    private BigDecimal restsPrice; // 其他支付

    private String settleId;

    private String insureSettleId;

    private String medicalRegNo; //医保登记号

    private String psnNo; //人员编号

}

package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptTrialSettleFundOutDO
 * @Description: 住院结算：预结算返回参数信息（基金信息）
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 17:11
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptTrialSettleFundOutDO {
    // 基金支付类型
    private String fundPayType;
    // 符合政策范围金
    private BigDecimal inscpScpAmt;
    // 本次可支付限额金额
    private BigDecimal crtPaybLmtAmt;
    // 基金支付金额
    private BigDecimal fundPayamt;
    // 基金支付类型名称
    private String fundPayTypeName;
    // 结算过程信息
    private String setlProcInfo;
}

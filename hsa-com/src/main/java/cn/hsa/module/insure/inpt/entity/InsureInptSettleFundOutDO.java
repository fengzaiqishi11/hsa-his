package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptSettleFundOutDO
 * @Description: 住院结算：住院结算反参（第二部分） 基金信息
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 17:02
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptSettleFundOutDO implements Serializable {
    // 基金支付类型
    private String fundPayType;
    // 符合政策范围金
    private BigDecimal inscpScpAmt;
    // 本次可支付限额金额
    private BigDecimal crtPaybLmtAmt;
    // 基金支付金额
    private String fundPayamt;
    // 基金支付类型名称
    private String fundPayTypeName;
    // 结算过程信息
    private String setlProcInfo;
}

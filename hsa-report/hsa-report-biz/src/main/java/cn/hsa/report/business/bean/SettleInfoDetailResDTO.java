package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleInfoResDTO
 * @Deacription 结算信息查询响应dto层
 * @Author liuzhuoting
 * @Date 2021-08-16
 * @Version 1.0
 **/
@Data
public class SettleInfoDetailResDTO implements Serializable {

    /**
     * 基金支付类型
     */
    private String fundPayType;

    /**
     * 符合政策范围金额
     */
    private BigDecimal inscpScpAmt;

    /**
     * 本次可支付限额金额
     */
    private BigDecimal crtPaybLmtAmt;

    /**
     * 基金支付金额
     */
    private BigDecimal fundPayamt;

    /**
     * 基金支付类型名称
     */
    private String fundPayTypeName;

    /**
     * 结算过程信息
     */
    private String setlProcInfo;

}

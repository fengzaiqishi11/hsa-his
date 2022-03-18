package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartFourDTO
 * @Deacription 结算单政策支付项
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartFourDTO implements Serializable {

    @ApiModelProperty(name = "setlId", value = "结算ID")
    private String setlId;

    @ApiModelProperty(name = "mdtrtId", value = "就诊ID")
    private String mdtrtId;

    @ApiModelProperty(name = "psnNo", value = "人员编号")
    private String psnNo;

    @ApiModelProperty(name = "polItemCode", value = "政策项编码")
    private String polItemCode;

    @ApiModelProperty(name = "polItemName", value = "政策项名称")
    private String polItemName;

    @ApiModelProperty(name = "selfPayAmt", value = "自付金额")
    private BigDecimal selfPayAmt;

    @ApiModelProperty(name = "selfPayProp", value = "自付比例")
    private String selfPayProp;

    @ApiModelProperty(name = "fundPayAmt", value = "基金支付金额")
    private BigDecimal fundPayAmt;

    @ApiModelProperty(name = "fundPayProp", value = "基金支付比例")
    private String fundPayProp;

    @ApiModelProperty(name = "polItemPaySum", value = "政策项支付合计")
    private BigDecimal polItemPaySum;

    public void setScale() {
        this.selfPayAmt = roundHalfUp(this.selfPayAmt);
        this.fundPayAmt = roundHalfUp(this.fundPayAmt);
        this.polItemPaySum = roundHalfUp(this.polItemPaySum);
    }

    private static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

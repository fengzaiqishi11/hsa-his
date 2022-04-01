package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartFiveDTO
 * @Deacription 结算单支付信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartFiveDTO implements Serializable {

    @ApiModelProperty(name = "medfeeSumamt", value = "本次医疗费总额")
    private BigDecimal medfeeSumamt;

    @ApiModelProperty(name = "hifpPay", value = "统筹基金支付")
    private BigDecimal hifpPay;

    @ApiModelProperty(name = "cvlservPay", value = "公务员补助支付")
    private BigDecimal cvlservPay;

    @ApiModelProperty(name = "hifesPay", value = "企业补充医疗保险基金支出")
    private BigDecimal hifesPay;

    @ApiModelProperty(name = "hifobPay", value = "大额基金支付")
    private BigDecimal hifobPay;

    @ApiModelProperty(name = "hifmipPay", value = "大病保险支付")
    private BigDecimal hifmipPay;

    @ApiModelProperty(name = "balc", value = "个人账户余额")
    private BigDecimal balc;

    @ApiModelProperty(name = "acctPay", value = "个人账户支付金额")
    private BigDecimal acctPay;

    @ApiModelProperty(name = "psnCashPay", value = "现金支付金额")
    private BigDecimal psnCashPay;

    @ApiModelProperty(name = "actPayDedc", value = "实际支付起付线")
    private BigDecimal actPayDedc;

    @ApiModelProperty(name = "mafPay", value = "医疗救助支付")
    private BigDecimal mafPay;

    @ApiModelProperty(name = "hospPrice", value = "医院支付")
    private BigDecimal hospPrice;

    @ApiModelProperty(name = "othPay", value = "其他基金支付")
    private BigDecimal othPay;

    @ApiModelProperty(name = "fundPaySumamt", value = "基金支付总额")
    private BigDecimal fundPaySumamt;

    @ApiModelProperty(name = "fundSumAmt", value = "基金支付总额")
    private BigDecimal fundSumAmt;

    public void setScale() {
        this.medfeeSumamt = roundHalfUp(this.medfeeSumamt);
        this.hifpPay = roundHalfUp(this.hifpPay);
        this.cvlservPay = roundHalfUp(this.cvlservPay);
        this.hifesPay = roundHalfUp(this.hifesPay);
        this.hifobPay = roundHalfUp(this.hifobPay);
        this.hifmipPay = roundHalfUp(this.hifmipPay);
        this.balc = roundHalfUp(this.balc);
        this.acctPay = roundHalfUp(this.acctPay);
        this.psnCashPay = roundHalfUp(this.psnCashPay);
        this.actPayDedc = roundHalfUp(this.actPayDedc);
        this.mafPay = roundHalfUp(this.mafPay);
        this.othPay = roundHalfUp(this.othPay);
        this.fundPaySumamt = roundHalfUp(this.fundPaySumamt);
        this.fundSumAmt = roundHalfUp(this.fundSumAmt);
    }

    public static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

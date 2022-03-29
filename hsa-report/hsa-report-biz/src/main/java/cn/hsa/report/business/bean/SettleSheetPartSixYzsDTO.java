package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartSixYzsDTO
 * @Deacription 一站式特殊累计信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartSixYzsDTO implements Serializable {

    @ApiModelProperty(name = "hifpPay", value = "基本医疗保险统筹基金支出")
    private BigDecimal hifpPay;
    @ApiModelProperty(name = "psnPartAmt", value = "个人负担总金额")
    private BigDecimal psnPartAmt;
    @ApiModelProperty(name = "fundPaySumamt", value = "基金总金额")
    private BigDecimal fundPaySumamt;
    @ApiModelProperty(name = "s2", value = "大病保险报销金额")
    private BigDecimal s2;
    @ApiModelProperty(name = "s3", value = "意外伤害")
    private BigDecimal s3;
    @ApiModelProperty(name = "s4", value = "大病补充特惠保")
    private BigDecimal s4;
    @ApiModelProperty(name = "s5", value = "医疗救助金额")
    private BigDecimal s5;
    @ApiModelProperty(name = "s6", value = "医院减免金额")
    private BigDecimal s6;
    @ApiModelProperty(name = "s7", value = "财政兜底报销金额")
    private BigDecimal s7;
    @ApiModelProperty(name = "s9", value = "其它报销金额")
    private BigDecimal s9;
    @ApiModelProperty(name = "sumBxFeeNumber", value = "合计报销金额")
    private BigDecimal sumBxFeeNumber;
    @ApiModelProperty(name = "sumBxFee", value = "合计报销金额中文")
    private String sumBxFee;

    public void setScale() {
        this.hifpPay = roundHalfUp(this.hifpPay);
        this.psnPartAmt = roundHalfUp(this.psnPartAmt);
        this.fundPaySumamt = roundHalfUp(this.fundPaySumamt);
        this.s2 = roundHalfUp(this.s2);
        this.s3 = roundHalfUp(this.s3);
        this.s4 = roundHalfUp(this.s4);
        this.s5 = roundHalfUp(this.s5);
        this.s6 = roundHalfUp(this.s6);
        this.s7 = roundHalfUp(this.s7);
        this.s9 = roundHalfUp(this.s9);
        this.sumBxFeeNumber = roundHalfUp(this.sumBxFeeNumber);
    }

    public static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

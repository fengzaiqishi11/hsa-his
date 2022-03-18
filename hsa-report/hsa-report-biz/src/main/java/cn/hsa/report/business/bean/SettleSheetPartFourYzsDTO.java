package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartFourYzsDTO
 * @Deacription 一站式结算单政策支付项
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartFourYzsDTO implements Serializable {

    @ApiModelProperty(name = "s1", value = "完全政策自付费用/全自费/超限额自付")
    private BigDecimal s1;
    @ApiModelProperty(name = "s2", value = "部分政策自付费用/乙类先自付")
    private BigDecimal s2;
    @ApiModelProperty(name = "s3", value = "县外就医转外自付费用")
    private BigDecimal s3;
    @ApiModelProperty(name = "s4", value = "大病起付线")
    private BigDecimal s4;
    @ApiModelProperty(name = "hospName", value = "核算机构名称 核算人")
    private String hospName;
    @ApiModelProperty(name = "hifpPayNumber", value = "基本医保报销金额")
    private BigDecimal hifpPayNumber;
    @ApiModelProperty(name = "hifpPay", value = "基本医保报销金额（大写）")
    private String hifpPay;
    @ApiModelProperty(name = "divideFormat", value = "百分比")
    private String divideFormat;
    @ApiModelProperty(name = "sumCum", value = "累计起付线(不含本次)")
    private BigDecimal sumCum;
    @ApiModelProperty(name = "s5", value = "提高10%的报销金额")
    private BigDecimal s5;
    @ApiModelProperty(name = "actPayDedc", value = "本次起付线")
    private BigDecimal actPayDedc;

    public void setScale() {
        this.s1 = roundHalfUp(this.s1);
        this.s2 = roundHalfUp(this.s2);
        this.s3 = roundHalfUp(this.s3);
        this.s4 = roundHalfUp(this.s4);
        this.hifpPayNumber = roundHalfUp(this.hifpPayNumber);
        this.sumCum = roundHalfUp(this.sumCum);
        this.s5 = roundHalfUp(this.s5);
        this.actPayDedc = roundHalfUp(this.actPayDedc);
    }

    private static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartTwoZyDTO
 * @Deacription 住院结算单累计信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartTwoYzsDTO extends SettleSheetPartTwoDTO {

    @ApiModelProperty(name = "s4", value = "累计扶贫特惠保报销金额")
    private BigDecimal s4;
    @ApiModelProperty(name = "s5", value = "累计医疗救助金额")
    private BigDecimal s5;
    @ApiModelProperty(name = "s6", value = "累计医院减免金额")
    private BigDecimal s6;
    @ApiModelProperty(name = "s7", value = "累计财政兜底报销金额")
    private BigDecimal s7;
    @ApiModelProperty(name = "s8", value = "累计其它报销金额")
    private BigDecimal s8;
    @ApiModelProperty(name = "s9", value = "累计政策范围内费用")
    private BigDecimal s9;
    @ApiModelProperty(name = "sumBxFeeNumber", value = "合计报销金额")
    private BigDecimal sumBxFeeNumber;
    @ApiModelProperty(name = "sumBxFee", value = "合计报销金额中文")
    private String sumBxFee;

    @Override
    public void setScale() {
        super.setScale();
        this.s4 = roundHalfUp(this.s4);
        this.s5 = roundHalfUp(this.s5);
        this.s6 = roundHalfUp(this.s6);
        this.s7 = roundHalfUp(this.s7);
        this.s8 = roundHalfUp(this.s8);
        this.s9 = roundHalfUp(this.s9);
        this.sumBxFeeNumber = roundHalfUp(this.sumBxFeeNumber);
    }

}

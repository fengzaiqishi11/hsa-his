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
public class SettleSheetPartTwoZyDTO extends SettleSheetPartTwoDTO {

    @ApiModelProperty(name = "inptCount", value = "本年住院次数")
    private Integer inptCount;
    @ApiModelProperty(name = "s4", value = "统筹支付")
    private BigDecimal s4;
    @ApiModelProperty(name = "s5", value = "政策自费")
    private BigDecimal s5;
    @ApiModelProperty(name = "s6", value = "政策自付")
    private BigDecimal s6;
    @ApiModelProperty(name = "s7", value = "大病保险合规费用")
    private BigDecimal s7;
    @ApiModelProperty(name = "s8", value = "大病保险合规费用")
    private BigDecimal s8;
    @ApiModelProperty(name = "s9", value = "大病保险支付")
    private BigDecimal s9;
    @ApiModelProperty(name = "s10", value = "医疗救助支付")
    private BigDecimal s10;

    @Override
    public void setScale() {
        super.setScale();
        this.inptCount = this.inptCount == null ? 0 : this.inptCount;
        this.s4 = roundHalfUp(this.s4);
        this.s5 = roundHalfUp(this.s5);
        this.s6 = roundHalfUp(this.s6);
        this.s7 = roundHalfUp(this.s7);
        this.s8 = roundHalfUp(this.s8);
        this.s9 = roundHalfUp(this.s9);
        this.s10 = roundHalfUp(this.s10);
    }

}

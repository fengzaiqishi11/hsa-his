package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartTwoMzDTO
 * @Deacription 门诊结算单累计信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartTwoMzDTO extends SettleSheetPartTwoDTO {

    @ApiModelProperty(name = "outptCount", value = "门诊次数")
    private Integer outptCount;
    @ApiModelProperty(name = "s4", value = "公务员补助支付")
    private BigDecimal s4;
    @ApiModelProperty(name = "s5", value = "大病保险支付")
    private BigDecimal s5;
    @ApiModelProperty(name = "s6", value = "个人账户支付")
    private BigDecimal s6;
    @ApiModelProperty(name = "s7", value = "医疗救助支付")
    private BigDecimal s7;


    @Override
    public void setScale() {
        super.setScale();
        this.outptCount = this.outptCount == null ? 0 : this.outptCount;
        this.s4 = roundHalfUp(this.s4);
        this.s5 = roundHalfUp(this.s5);
        this.s6 = roundHalfUp(this.s6);
        this.s7 = roundHalfUp(this.s7);
    }

}

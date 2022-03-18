package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartTwoDTO
 * @Deacription 结算单累计信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartTwoDTO implements Serializable {

    @ApiModelProperty(name = "s1", value = "医疗费用合计")
    private BigDecimal s1;
    @ApiModelProperty(name = "s2", value = "统筹基金支付")
    private BigDecimal s2;
    @ApiModelProperty(name = "s3", value = "大额基金支付")
    private BigDecimal s3;

    public void setScale() {
        this.s1 = roundHalfUp(this.s1);
        this.s2 = roundHalfUp(this.s2);
        this.s3 = roundHalfUp(this.s3);
    }

    public static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}

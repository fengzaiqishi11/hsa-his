package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName SettleSheetPartThreeDTO
 * @Deacription 结算单费用分类汇总
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartThreeDTO implements Serializable, Comparable<SettleSheetPartThreeDTO> {

    @ApiModelProperty(name = "sumDetItemFeeSumamt", value = "总费用")
    private BigDecimal sumDetItemFeeSumamt;

    @ApiModelProperty(name = "aClassFee", value = "甲类费用")
    private BigDecimal aClassFee;

    @ApiModelProperty(name = "bClassFee", value = "乙类费用")
    private BigDecimal bClassFee;

    @ApiModelProperty(name = "cClassFee", value = "丙类费用")
    private BigDecimal cClassFee;

    @ApiModelProperty(name = "dClassFee", value = "政策范围内金额")
    private BigDecimal dClassFee;

    @ApiModelProperty(name = "medChrgitmType", value = "费用类型")
    private String medChrgitmType;

    @ApiModelProperty(name = "medChrgitmTypeName", value = "费用类型名称")
    private String medChrgitmTypeName;

    @ApiModelProperty(name = "isort", value = "费用排序")
    private Integer isort;

    public void setScale() {
        this.sumDetItemFeeSumamt = roundHalfUp(this.sumDetItemFeeSumamt);
        this.aClassFee = roundHalfUp(this.aClassFee);
        this.bClassFee = roundHalfUp(this.bClassFee);
        this.cClassFee = roundHalfUp(this.cClassFee);
        this.dClassFee = roundHalfUp(this.dClassFee);
    }

    private static BigDecimal roundHalfUp(BigDecimal val) {
        if (null == val) {
            return BigDecimal.ZERO;
        }
        return val.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public int compareTo(SettleSheetPartThreeDTO o) {
        return (this.isort < o.isort ? -1 : (this.isort.equals(o.isort) ? 0 : 1));
    }

}

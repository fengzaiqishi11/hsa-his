package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SettleSheetPartOneYdDTO
 * @Deacription 异地结算单基本信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartOneYdDTO extends SettleSheetPartOneDTO {

    @ApiModelProperty(name = "outDiseaseName", value = "入院第一诊断")
    private String inDiseaseName;

    @ApiModelProperty(name = "outDiseaseName", value = "出院第一诊断")
    private String outDiseaseName;

    @ApiModelProperty(name = "bka006Name", value = "待遇类别")
    private String bka006Name;

    @ApiModelProperty(name = "visitNationCode", value = "科别（暂时取出院科室）")
    private String visitNationCode;

}

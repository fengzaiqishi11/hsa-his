package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SettleSheetPartOneYzsDTO
 * @Deacription 一站式结算单基本信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartOneYzsDTO extends SettleSheetPartOneDTO {

    @ApiModelProperty(name = "outDiseaseName", value = "出院诊断")
    private String outDiseaseName;

    @ApiModelProperty(name = "address", value = "家庭地址")
    private String address;

}

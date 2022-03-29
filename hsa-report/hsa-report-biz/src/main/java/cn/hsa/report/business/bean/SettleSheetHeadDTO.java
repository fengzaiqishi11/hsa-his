package cn.hsa.report.business.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SettleSheetPartOneDTO
 * @Deacription 结算单头部信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetHeadDTO implements Serializable {

    @ApiModelProperty(name = "settleTitle", value = "结算单标题")
    private String settleTitle;

    @ApiModelProperty(name = "hospLv", value = "医院等级")
    private String hospLv;

    @ApiModelProperty(name = "hospLvName", value = "医院等级")
    private String hospLvName;

    @ApiModelProperty(name = "hospName", value = "医疗机构名称")
    private String hospName;

    @ApiModelProperty(name = "printDate", value = "打印日期")
    @JSONField(format = "yyyy年MM月dd日")
    private Date printDate;

    @ApiModelProperty(name = "unit", value = "金额单位")
    private String unit;

    @ApiModelProperty(name = "opterName", value = "经办人姓名")
    private String opterName;

}

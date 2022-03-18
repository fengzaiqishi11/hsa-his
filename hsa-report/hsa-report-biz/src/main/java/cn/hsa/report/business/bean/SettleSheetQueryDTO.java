package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SettleSheetQueryDTO
 * @Deacription 结算单查询条件
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetQueryDTO implements Serializable {

    @ApiModelProperty(value = "挂号流水号")
    private String mdtrtSn;

    @ApiModelProperty(value = "门诊号/住院号")
    private String iptOtpNo;

    @ApiModelProperty(value = "HIS就诊ID")
    private String visitId;

    @ApiModelProperty(value = "业务类型")
    private String businessSources;

}

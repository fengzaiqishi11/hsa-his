package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SettleSheetInfoZyDTO
 * @Deacription 住院结算单信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 */
@Data
public class SettleSheetInfoZyDTO implements Serializable {

    @ApiModelProperty(name = "sheetCode", value = "模板编码")
    private String sheetCode;

    @ApiModelProperty(name = "medisnInfMap", value = "结算单头部信息")
    private SettleSheetHeadDTO medisnInfMap;

    @ApiModelProperty(name = "baseInfoMap", value = "结算单基本信息")
    private SettleSheetPartOneDTO baseInfoMap;

    @ApiModelProperty(name = "fourPartMap", value = "结算单支付信息")
    private SettleSheetPartFiveDTO fourPartMap;

    @ApiModelProperty(name = "pastFeeMap", value = "结算单累计信息")
    private SettleSheetPartTwoZyDTO pastFeeMap;

    @ApiModelProperty(name = "feeMapList", value = "结算单费用分类汇总")
    private List<SettleSheetPartThreeDTO> feeMapList;

    @ApiModelProperty(name = "policyMapList", value = "结算单政策支付项")
    private List<SettleSheetPartFourDTO> policyMapList;

}

package cn.hsa.report.business.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SettleSheetPartFiveDTO
 * @Deacription 结算单支付信息
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 3.0
 */
@Data
public class SettleSheetPartFiveYdDTO extends SettleSheetPartFiveDTO {

    @ApiModelProperty(name = "fundPaySumamt", value = "基金支付总额中文")
    private String fundPaySumamtCN;

    @ApiModelProperty(name = "medfeeSumamtCN", value = "本次医疗费总额中文")
    private String medfeeSumamtCN;

    @ApiModelProperty(name = "hifpPayCN", value = "统筹基金支付中文")
    private String hifpPayCN;

    @ApiModelProperty(name = "cvlservPayCN", value = "公务员补助支付中文")
    private String cvlservPayCN;

    @ApiModelProperty(name = "hifesPayCN", value = "企业补充医疗保险基金支出中文")
    private String hifesPayCN;

    @ApiModelProperty(name = "hifobPayCN", value = "大额基金支付中文")
    private String hifobPayCN;

    @ApiModelProperty(name = "hifmipPayCN", value = "大病保险支付中文")
    private String hifmipPayCN;

    @ApiModelProperty(name = "acctPayCN", value = "个人账户支付金额中文")
    private String acctPayCN;

    @ApiModelProperty(name = "actPayDedcCN", value = "实际支付起付线中文")
    private String actPayDedcCN;

    @ApiModelProperty(name = "othPayCN", value = "其他基金支付中文")
    private String othPayCN;

    @ApiModelProperty(name = "hospPriceCN", value = "医院支付中文")
    private String hospPriceCN;

    @ApiModelProperty(name = "mafPayCN", value = "医疗救助支付中文")
    private String mafPayCN;

    @ApiModelProperty(name = "psnCashPayCN", value = "现金支付金额中文")
    private String psnCashPayCN;

    @ApiModelProperty(name = "balcCN", value = "个人账户余额中文")
    private String balcCN;

    @ApiModelProperty(name = "fundSumAmtCN", value = "基金支付总额中文")
    private String fundSumAmtCN;

}

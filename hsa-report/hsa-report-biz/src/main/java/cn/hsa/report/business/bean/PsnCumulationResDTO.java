package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName PsnCumulationResDTO
 * @Deacription 人人员累计信息查询响应dto层
 * @Author liuzhuoting
 * @Date 2021-08-16
 * @Version 1.0
 **/
@Data
public class PsnCumulationResDTO implements Serializable {

    /**
     * 险种类型
     */
    private String insutype;

    /**
     * 年度
     */
    private String year;

    /**
     * 累计年月
     */
    private String cumYm;

    /**
     * 累计类别代码
     */
    private String cumTypeCode;

    /**
     * 累计值
     */
    private BigDecimal cum;

    /**
     * 住院次数
     */
    private BigDecimal iptNum;

    /**
     * 本年度分段计算费用累计
     */
    private BigDecimal subCalcFee;

    /**
     * 医疗费用合计
     */
    private BigDecimal MedFeeTotal;

    /**
     * 已付起付线
     */
    private BigDecimal payDedc;

    /**
     * 统筹支付
     */
    private BigDecimal hifpPay;

    /**
     * 政策自费
     */
    private BigDecimal policyFee;

    /**
     * 大额基金支付
     */
    private BigDecimal hifobPay;

    /**
     * 大病保险合规费用
     */
    private BigDecimal hifmipPay;

    /**
     * 医疗救助支付
     */
    private BigDecimal mafPay;

}

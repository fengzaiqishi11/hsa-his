package cn.hsa.report.business.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName DiseInfoReqDTO
 * @Deacription 人员累计信息查询请求dto层
 * @Author liuzhuoting
 * @Date 2021-08-16
 * @Version 1.0
 **/
@Data
public class PsnPolicyResDTO implements Serializable {

    /**
     * 结算ID
     */
    @JSONField(name = "setlId")
    private String setlId;

    /**
     * 就诊ID
     */
    @JSONField(name = "mdtrtId")
    private String mdtrtId;

    /**
     * 人员编号
     */
    @JSONField(name = "psnNo")
    private String psnNo;

    /**
     * 政策项编码
     */
    @JSONField(name = "polItemCode")
    private String polItemCode;

    /**
     * 政策项名称
     */
    @JSONField(name = "polItemName")
    private String polItemName;

    /**
     * 自付金额
     */
    @JSONField(name = "selfPayAmt")
    private BigDecimal selfPayAmt;

    /**
     * 自付比例
     */
    @JSONField(name = "selfPayProp")
    private String selfPayProp;

    /**
     * 基金支付金额
     */
    @JSONField(name = "fundPayAmt")
    private BigDecimal fundPayAmt;

    /**
     * 基金支付比例
     */
    @JSONField(name = "fundPayProp")
    private String fundPayProp;

    /**
     * 政策项支付合计
     */
    @JSONField(name = "polItemPaySum")
    private BigDecimal polItemPaySum;

}

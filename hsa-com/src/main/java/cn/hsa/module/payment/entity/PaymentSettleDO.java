package cn.hsa.module.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 诊间支付结算表(PaymentSettle)实体类
 *
 * @author makejava
 * @since 2022-09-01 11:19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentSettleDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -72675184541908659L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 结算id
     */
    private String settleId;
    /**
     * 平台支付结算id
     */
    private String paymentSettleId;
    /**
     * 就诊号
     */
    private String visitNo;
    /**
     * 总费用
     */
    private BigDecimal totalPrice;
    /**
     * 平台支付费用
     */
    private BigDecimal paymentPrice;
    /**
     * 支付方式（ZFFS）
     */
    private String payCode;
    /**
     * 状态标志,0正常，2冲红，1，被冲红
     */
    private String statusCode;

    /**
     * 结算状态（0：未结算 1预结算 2已结算）
     */
    private String settleCode;

    /**
     * 交易信息(原交易)中的msgid,发送方报文ID
     */
    private String omsgid;
    /**
     * 交易信息(原交易)中的infno
     */
    private String oinfno;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;

    /**
     * 第一次结算的id
     */
    private String oneSettleId;

    /**
     * 冲红id
     */
    private String redId;
    /**
     * 原结算id
     */
    private String oldSettleId;

    /**
     * 结算时间
     */
    private Date settleTime;

    /**
     * 是否结算（SF）
     */
    private String isSettle;

}

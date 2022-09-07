package cn.hsa.module.payment.entity;

import java.util.Date;

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
public class PaymentSettleDO implements Serializable {
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
    private Double totalPrice;
    /**
     * 平台支付费用
     */
    private Double paymentPrice;
    /**
     * 支付方式（ZFFS）
     */
    private String payCode;
    /**
     * 状态标志,0正常，2冲红，1，被冲红
     */
    private String stateCode;

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


}

package cn.hsa.module.inpt.advancepay.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (InptAdvancePay)实体类
 *
 * @author xingyu.xie
 * @since 2020-09-08 14:25:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdvancePayDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 552748717104693042L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 预交单号
     */
    private String apOrderNo;
    /**
     * 预交金额
     */
    private BigDecimal price;
    /**
     * 是否结算（SF）
     */
    private String isSettle;
    /**
     * 结算id
     */
    private String settleId;
    /**
     * 冲红id
     */
    private String redId;
    /**
     * 状态标志代码（ZTBZ）
     */
    private String statusCode;
    /**
     * 日结缴款ID
     */
    private String dailySettleId;
    /**
     * 支付来源代码（ZFLY，第三方对接）
     */
    private String sourcePayCode;
    /**
     * 支付方式代码（ZFFS）
     */
    private String payCode;
    /**
     * 支票号码（支付方式为支票时：显示必填）
     */
    private String chequeNo;
    /**
     * 手续费（支付方式为POS时：显示选填，默认0）
     */
    private BigDecimal servicePrice;
    /**
     * 支付订单号（第三方订单号）
     */
    private String orderNo;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crteTime;
}
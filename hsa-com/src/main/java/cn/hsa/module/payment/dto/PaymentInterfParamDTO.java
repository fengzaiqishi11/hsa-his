package cn.hsa.module.payment.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name:
 * @class_name: PaymentInterfParamDTO
 * @Description: 调用支付平台参数封装DTO
 * @Author: yuelong.chen
 * @Date: 2022/8/30 14:22
 * @Company: 创智和宇
 **/
@Data
public class PaymentInterfParamDTO implements Serializable {
    /**
     * 交易编号
     */
    private String infno;

    /**
     * 医药机构编号
     */
    private String orgId;

    /**
     * 机构订单号
     */
    private String orgOrderId;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 订单金额
     */
    private BigDecimal totalFee;
    /**
     * 付款码
     */
    private String authCode;
    /**
     * 付款码类型 1：wx，2：alipay
     */
    private String payType;

    /**
     * 发送方报文
     */
    private String msgid;

    /**
     * 交易输入
     */
    private String input;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 操作人id
     */
    private String opter;

    /**
     *  操作人姓名
     */
    private String opterName;
    /**
     *  账单开始日期
     */
    private String billBeginDate;
    /**
     *  账单结束日期
     */
    private String billEndDate;

    /**
     *  页码
     */
    private int pageNum;
    /**
     *  每页显示数量
     */
    private int pageSize;

    /**
     *  退款订单号
     */
    private String orgRefundId;

    /**
     *  退款金额
     */
    private BigDecimal refundFee;
    /**
     *  退款理由
     */
    private String refundReason;

}

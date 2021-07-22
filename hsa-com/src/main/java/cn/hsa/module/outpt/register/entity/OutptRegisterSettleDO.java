package cn.hsa.module.outpt.register.entity;/*

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
* @Package_name: 
* @Class_name: OutptRegisterSettleDO
* @Describe: 挂号结算实体类
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/8/10 17:50
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class OutptRegisterSettleDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4419950015733596882L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 挂号ID */
    private String registerId;

    /** 挂号总金额 */
    private BigDecimal totalPrice;

    /** 优惠总金额 */
    private BigDecimal preferentialPrice;

    /** 实收总金额 */
    private BigDecimal realityPrice;

    /** 结算时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

    /** 日结缴款ID */
    private String dailySettleId;

    /** 状态标志代码（ZTBZ） */
    private String statusCode;

    /** 原结算ID */
    private String originId;

    /** 发票段ID */
    private String billId;

    /** 票据号码 */
    private String billNo;

    /** 支付方式代码（ZFFS，第三方对接） */
    private String payCode;

    /** 支付订单号（第三方订单号） */
    private String orderNo;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    /**
     * 获取主键
     * 
     * @return 主键
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置主键
     * 
     * @param id
     *          主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取医院编码
     * 
     * @return 医院编码
     */
    public String getHospCode() {
        return this.hospCode;
    }

    /**
     * 设置医院编码
     * 
     * @param hospCode
     *          医院编码
     */
    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    /**
     * 获取挂号ID
     * 
     * @return 挂号ID
     */
    public String getRegisterId() {
        return this.registerId;
    }

    /**
     * 设置挂号ID
     * 
     * @param registerId
     *          挂号ID
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * 获取挂号总金额
     * 
     * @return 挂号总金额
     */
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * 设置挂号总金额
     * 
     * @param totalPrice
     *          挂号总金额
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取优惠总金额
     * 
     * @return 优惠总金额
     */
    public BigDecimal getPreferentialPrice() {
        return this.preferentialPrice;
    }

    /**
     * 设置优惠总金额
     * 
     * @param preferentialPrice
     *          优惠总金额
     */
    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    /**
     * 获取实收总金额
     * 
     * @return 实收总金额
     */
    public BigDecimal getRealityPrice() {
        return this.realityPrice;
    }

    /**
     * 设置实收总金额
     * 
     * @param realityPrice
     *          实收总金额
     */
    public void setRealityPrice(BigDecimal realityPrice) {
        this.realityPrice = realityPrice;
    }

    /**
     * 获取结算时间
     * 
     * @return 结算时间
     */
    public Date getSettleTime() {
        return this.settleTime;
    }

    /**
     * 设置结算时间
     * 
     * @param settleTime
     *          结算时间
     */
    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    /**
     * 获取日结缴款ID
     * 
     * @return 日结缴款ID
     */
    public String getDailySettleId() {
        return this.dailySettleId;
    }

    /**
     * 设置日结缴款ID
     * 
     * @param dailySettleId
     *          日结缴款ID
     */
    public void setDailySettleId(String dailySettleId) {
        this.dailySettleId = dailySettleId;
    }

    /**
     * 获取状态标志代码（ZTBZ）
     * 
     * @return 状态标志代码（ZTBZ）
     */
    public String getStatusCode() {
        return this.statusCode;
    }

    /**
     * 设置状态标志代码（ZTBZ）
     * 
     * @param statusCode
     *          状态标志代码（ZTBZ）
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 获取原结算ID
     * 
     * @return 原结算ID
     */
    public String getOriginId() {
        return this.originId;
    }

    /**
     * 设置原结算ID
     * 
     * @param originId
     *          原结算ID
     */
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    /**
     * 获取发票段ID
     * 
     * @return 发票段ID
     */
    public String getBillId() {
        return this.billId;
    }

    /**
     * 设置发票段ID
     * 
     * @param billId
     *          发票段ID
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }

    /**
     * 获取票据号码
     * 
     * @return 票据号码
     */
    public String getBillNo() {
        return this.billNo;
    }

    /**
     * 设置票据号码
     * 
     * @param billNo
     *          票据号码
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 获取支付方式代码（ZFFS，第三方对接）
     * 
     * @return 支付方式代码（ZFFS
     */
    public String getPayCode() {
        return this.payCode;
    }

    /**
     * 设置支付方式代码（ZFFS，第三方对接）
     * 
     * @param payCode
     *          支付方式代码（ZFFS
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * 获取支付订单号（第三方订单号）
     * 
     * @return 支付订单号（第三方订单号）
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 设置支付订单号（第三方订单号）
     * 
     * @param orderNo
     *          支付订单号（第三方订单号）
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取创建人ID
     * 
     * @return 创建人ID
     */
    public String getCrteId() {
        return this.crteId;
    }

    /**
     * 设置创建人ID
     * 
     * @param crteId
     *          创建人ID
     */
    public void setCrteId(String crteId) {
        this.crteId = crteId;
    }

    /**
     * 获取创建人姓名
     * 
     * @return 创建人姓名
     */
    public String getCrteName() {
        return this.crteName;
    }

    /**
     * 设置创建人姓名
     * 
     * @param crteName
     *          创建人姓名
     */
    public void setCrteName(String crteName) {
        this.crteName = crteName;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCrteTime() {
        return this.crteTime;
    }

    /**
     * 设置创建时间
     * 
     * @param crteTime
     *          创建时间
     */
    public void setCrteTime(Date crteTime) {
        this.crteTime = crteTime;
    }

}
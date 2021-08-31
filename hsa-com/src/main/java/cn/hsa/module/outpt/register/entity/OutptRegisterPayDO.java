package cn.hsa.module.outpt.register.entity;/*
import cn.hsa.base.PageDO;

import java.math.BigDecimal;

/**
* @Package_name: cn.hsa.module.outpt.register.entity
* @Class_name: OutptRegisterPayDO
* @Describe: 挂号支付方式表
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/8/10 17:50
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;

import java.math.BigDecimal;

public class OutptRegisterPayDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7959551814727169674L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 挂号结算ID */
    private String rsId;

    /** 就诊ID */
    private String visitId;

    /** 支付方式代码（ZFFS） */
    private String payCode;

    /** 票据号 */
    private String orderNo;

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /** 支付金额 */
    private BigDecimal price;

    /** 手续费（针对POS机） */
    private BigDecimal servicePrice;

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
     * 获取挂号结算ID
     * 
     * @return 挂号结算ID
     */
    public String getRsId() {
        return this.rsId;
    }

    /**
     * 设置挂号结算ID
     * 
     * @param rsId
     *          挂号结算ID
     */
    public void setRsId(String rsId) {
        this.rsId = rsId;
    }

    /**
     * 获取就诊ID
     * 
     * @return 就诊ID
     */
    public String getVisitId() {
        return this.visitId;
    }

    /**
     * 设置就诊ID
     * 
     * @param visitId
     *          就诊ID
     */
    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    /**
     * 获取支付方式代码（ZFFS）
     * 
     * @return 支付方式代码（ZFFS）
     */
    public String getPayCode() {
        return this.payCode;
    }

    /**
     * 设置支付方式代码（ZFFS）
     * 
     * @param payCode
     *          支付方式代码（ZFFS）
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * 获取支付金额
     * 
     * @return 支付金额
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置支付金额
     * 
     * @param price
     *          支付金额
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取手续费（针对POS机）
     * 
     * @return 手续费（针对POS机）
     */
    public BigDecimal getServicePrice() {
        return this.servicePrice;
    }

    /**
     * 设置手续费（针对POS机）
     * 
     * @param servicePrice
     *          手续费（针对POS机）
     */
    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

}
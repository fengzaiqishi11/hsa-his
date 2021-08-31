package cn.hsa.module.outpt.outinInvoice.entity;/*
import java.util.Date;

/**
* @Package_name: n.hsa.module.outpt.invoice.entity
* @Class_name: OutinInvoiceDetailDO
* @Describe: 发票管理明细表实体
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/8/24 15:10
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OutinInvoiceDetailDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9106696510521337237L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 发票ID（outin_invoice） */
    private String invoiceId;

    /** 发票号码 */
    private String invoiceNo;

    /** 使用状态代码 */
    private String statusCode;

    /** 使用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useTime;

    /** 结算ID（outpt_settle） */
    private String settleId;

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
     * 获取发票ID（outin_invoice）
     * 
     * @return 发票ID（outin_invoice）
     */
    public String getInvoiceId() {
        return this.invoiceId;
    }

    /**
     * 设置发票ID（outin_invoice）
     * 
     * @param invoiceId
     *          发票ID（outin_invoice）
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 获取发票号码
     * 
     * @return 发票号码
     */
    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    /**
     * 设置发票号码
     * 
     * @param invoiceNo
     *          发票号码
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 获取使用状态代码
     * 
     * @return 使用状态代码
     */
    public String getStatusCode() {
        return this.statusCode;
    }

    /**
     * 设置使用状态代码
     * 
     * @param statusCode
     *          使用状态代码
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 获取使用时间
     * 
     * @return 使用时间
     */
    public Date getUseTime() {
        return this.useTime;
    }

    /**
     * 设置使用时间
     * 
     * @param useTime
     *          使用时间
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * 获取结算ID（outpt_settle）
     * 
     * @return 结算ID（outpt_settle）
     */
    public String getSettleId() {
        return this.settleId;
    }

    /**
     * 设置结算ID（outpt_settle）
     * 
     * @param settleId
     *          结算ID（outpt_settle）
     */
    public void setSettleId(String settleId) {
        this.settleId = settleId;
    }
}
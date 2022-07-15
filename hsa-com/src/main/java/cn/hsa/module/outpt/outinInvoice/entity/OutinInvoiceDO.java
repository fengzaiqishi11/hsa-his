package cn.hsa.module.outpt.outinInvoice.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.invoice.entity
 * @Class_name: OutinInvoiceDO
 * @Describe: 发票管理主表实体
 * @Author: liaojiguang
 * @Email: liaojiguang@powersi.com.cn
 * @Date: 2020/8/24 15:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutinInvoiceDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1465644747030956055L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 领用人ID */
    private String receiveId;

    /** 领用人姓名 */
    private String receiveName;

    /** 领用时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date receiveTime;

    /** 使用人ID */
    private String useId;

    /** 使用人姓名 */
    private String useName;

    /** 票据类型代码 */
    private String typeCode;

    /** 票据号前缀 */
    private String prefix;

    /** 起始号码 */
    private String startNo;

    /** 终止号码 */
    private String endNo;

    /** 当前号码 */
    private String currNo;

    /** 使用状态代码 */
    private String statusCode;

    /** 发票剩余数量 */
    private Integer num;

    /** 备注 */
    private String remark;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date crteTime;

    /**
     * 发票的票据代码
     */
    private String invoiceCode;

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
     * 获取领用人ID
     * 
     * @return 领用人ID
     */
    public String getReceiveId() {
        return this.receiveId;
    }

    /**
     * 设置领用人ID
     * 
     * @param receiveId
     *          领用人ID
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    /**
     * 获取领用人姓名
     * 
     * @return 领用人姓名
     */
    public String getReceiveName() {
        return this.receiveName;
    }

    /**
     * 设置领用人姓名
     * 
     * @param receiveName
     *          领用人姓名
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 获取领用时间
     * 
     * @return 领用时间
     */
    public Date getReceiveTime() {
        return this.receiveTime;
    }

    /**
     * 设置领用时间
     * 
     * @param receiveTime
     *          领用时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取使用人ID
     * 
     * @return 使用人ID
     */
    public String getUseId() {
        return this.useId;
    }

    /**
     * 设置使用人ID
     * 
     * @param useId
     *          使用人ID
     */
    public void setUseId(String useId) {
        this.useId = useId;
    }

    /**
     * 获取使用人姓名
     * 
     * @return 使用人姓名
     */
    public String getUseName() {
        return this.useName;
    }

    /**
     * 设置使用人姓名
     * 
     * @param useName
     *          使用人姓名
     */
    public void setUseName(String useName) {
        this.useName = useName;
    }

    /**
     * 获取票据类型代码
     * 
     * @return 票据类型代码
     */
    public String getTypeCode() {
        return this.typeCode;
    }

    /**
     * 设置票据类型代码
     * 
     * @param typeCode
     *          票据类型代码
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取票据号前缀
     * 
     * @return 票据号前缀
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * 设置票据号前缀
     * 
     * @param prefix
     *          票据号前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取起始号码
     * 
     * @return 起始号码
     */
    public String getStartNo() {
        return this.startNo;
    }

    /**
     * 设置起始号码
     * 
     * @param startNo
     *          起始号码
     */
    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    /**
     * 获取终止号码
     * 
     * @return 终止号码
     */
    public String getEndNo() {
        return this.endNo;
    }

    /**
     * 设置终止号码
     * 
     * @param endNo
     *          终止号码
     */
    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    /**
     * 获取当前号码
     * 
     * @return 当前号码
     */
    public String getCurrNo() {
        return this.currNo;
    }

    /**
     * 设置当前号码
     * 
     * @param currNo
     *          当前号码
     */
    public void setCurrNo(String currNo) {
        this.currNo = currNo;
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
     * 获取发票剩余数量
     * 
     * @return 发票剩余数量
     */
    public Integer getNum() {
        return this.num;
    }

    /**
     * 设置发票剩余数量
     * 
     * @param num
     *          发票剩余数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     * 
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
}
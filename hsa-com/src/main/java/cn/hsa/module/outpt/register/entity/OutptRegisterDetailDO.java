package cn.hsa.module.outpt.register.entity;/*

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
* @Package_name: 
* @Class_name: OutptRegisterDetailDO
* @Describe:  挂号费用明细表信息
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/8/11 16:01
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
public class OutptRegisterDetailDO extends PageDO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5737759054665677309L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 挂号ID */
    private String registerId;

    /** 就诊ID */
    private String visitId;

    /** 项目类型代码（XMLB） */
    private String itemCode;

    /** 项目ID（项目/材料） */
    private String itemId;

    /** 项目名称（项目/材料） */
    private String itemName;

    /** 财务分类编码（表base_finance_classify） */
    private String bfcId;

    /** 财务分类编码（表base_finance_classify） */
    private String bfcCode;

    /** 项目单价 */
    private BigDecimal price;

    /** 项目数量 */
    private BigDecimal num;

    /** 用药性质代码（YYXZ） */
    private String useCode;

    /** 规格 */
    private String spec;

    /** 优惠配置ID */
    private String preferentialId;

    /** 项目总金额 */
    private BigDecimal totalPrice;

    /** 优惠总金额 */
    private BigDecimal preferentialPrice;

    /** 实收总金额 */
    private BigDecimal realityPrice;

    /** 单位代码（DW） */
    private String unitCode;

    /** 状态标志代码（ZTBZ） */
    private String statusCode;

    /** 原费用明细ID */
    private String originId;

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
     * 获取项目类型代码（XMLB）
     * 
     * @return 项目类型代码（XMLB）
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 设置项目类型代码（XMLB）
     * 
     * @param itemCode
     *          项目类型代码（XMLB）
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 获取项目ID（项目/材料）
     * 
     * @return 项目ID（项目/材料）
     */
    public String getItemId() {
        return this.itemId;
    }

    /**
     * 设置项目ID（项目/材料）
     * 
     * @param itemId
     *          项目ID（项目/材料）
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取项目名称（项目/材料）
     * 
     * @return 项目名称（项目/材料）
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 设置项目名称（项目/材料）
     * 
     * @param itemName
     *          项目名称（项目/材料）
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 获取财务分类编码（表base_finance_classify）
     * 
     * @return 财务分类编码（表base_finance_classify）
     */
    public String getBfcCode() {
        return this.bfcCode;
    }

    /**
     * 设置财务分类编码（表base_finance_classify）
     * 
     * @param bfcCode
     *          财务分类编码（表base_finance_classify）
     */
    public void setBfcCode(String bfcCode) {
        this.bfcCode = bfcCode;
    }

    /**
     * 获取项目单价
     * 
     * @return 项目单价
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置项目单价
     * 
     * @param price
     *          项目单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取项目数量
     * 
     * @return 项目数量
     */
    public BigDecimal getNum() {
        return this.num;
    }

    /**
     * 设置项目数量
     * 
     * @param num
     *          项目数量
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * 获取用药性质代码（YYXZ）
     * 
     * @return 用药性质代码（YYXZ）
     */
    public String getUseCode() {
        return this.useCode;
    }

    /**
     * 设置用药性质代码（YYXZ）
     * 
     * @param useCode
     *          用药性质代码（YYXZ）
     */
    public void setUseCode(String useCode) {
        this.useCode = useCode;
    }

    /**
     * 获取规格
     * 
     * @return 规格
     */
    public String getSpec() {
        return this.spec;
    }

    /**
     * 设置规格
     * 
     * @param spec
     *          规格
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 获取优惠配置ID
     * 
     * @return 优惠配置ID
     */
    public String getPreferentialId() {
        return this.preferentialId;
    }

    /**
     * 设置优惠配置ID
     * 
     * @param preferentialId
     *          优惠配置ID
     */
    public void setPreferentialId(String preferentialId) {
        this.preferentialId = preferentialId;
    }

    /**
     * 获取项目总金额
     * 
     * @return 项目总金额
     */
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * 设置项目总金额
     * 
     * @param totalPrice
     *          项目总金额
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
     * 获取单位代码（DW）
     * 
     * @return 单位代码（DW）
     */
    public String getUnitCode() {
        return this.unitCode;
    }

    /**
     * 设置单位代码（DW）
     * 
     * @param unitCode
     *          单位代码（DW）
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
     * 获取原费用明细ID
     * 
     * @return 原费用明细ID
     */
    public String getOriginId() {
        return this.originId;
    }

    /**
     * 设置原费用明细ID
     * 
     * @param originId
     *          原费用明细ID
     */
    public void setOriginId(String originId) {
        this.originId = originId;
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

    public String getBfcId() {
        return bfcId;
    }

    public void setBfcId(String bfcId) {
        this.bfcId = bfcId;
    }
}
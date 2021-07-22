package cn.hsa.module.sync.syncdrug.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.center.drug.entity
 * @Class_name:: CenterDrugDO
 * @Description: 药品管理数据库映射对象
 * @Author: liaojunjie
 * @Date: 2020/8/5 17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncDrugDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -24481128139354269L;
    /**
     * 主键
     */
    private String id;
    /**
     * 药品分类代码（YPFL）
     */
    private String typeCode;
    /**
     * 药品大类代码（YPDL）
     */
    private String bigTypeCode;
    /**
     * 财务分类编码（表base_finance_classify）
     */
    private String bfcCode;
    /**
     * 药品编码
     */
    private String code;
    /**
     * 通用名
     */
    private String usualName;
    /**
     * 商品名
     */
    private String goodName;
    /**
     * 规格
     */
    private String spec;
    /**
     * 剂型代码（JXFL）
     */
    private String prepCode;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 门诊单位代码（DW）
     */
    private String outUnitCode;
    /**
     * 住院单位代码（DW）
     */
    private String inUnitCode;
    /**
     * 频率编码（表base_rate）
     */
    private String rateCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 最近购进单价
     */
    private BigDecimal lastBuyPrice;
    /**
     * 最近拆零购进单价
     */
    private BigDecimal lastSplitBuyPrice;
    /**
     * 平均购进单价
     */
    private BigDecimal avgBuyPrice;
    /**
     * 平均零售单价
     */
    private BigDecimal avgSellPrice;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 是否门诊可用：0否、1是（SF）
     */
    private String isOut;
    /**
     * 是否住院可用：0否、1是（SF）
     */
    private String isIn;
    /**
     * 是否大输液：0否、1是（SF）
     */
    private String isLvp;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 医保本位码编码
     */
    private String insureCode;
    /**
     * 国家卫健委编码
     */
    private String nationCode;
    /**
     * 药品说明书
     */
    private String drugRemark;
    /**
     * 药品说明书图片路径
     */
    private String drugImgPath;
    /**
     * 最大剂量
     */
    private BigDecimal maxDosage;
    /**
     * 最小剂量
     */
    private BigDecimal minDosage;
    /**
     * 性别限制代码
     */
    private String genderCode;
    /**
     * 最小年龄
     */
    private Integer minAge;
    /**
     * 最大年龄
     */
    private Integer maxAge;
    /**
     * DDD值（限定日剂量）
     */
    private String ddd;
    /**
     * 药品级别代码（YPJB）
     */
    private String durgCode;
    /**
     * 毒麻药品级别代码（DMYPJB）
     */
    private String phCode;
    /**
     * 抗菌药品级别代码（KJYPJB）
     */
    private String antibacterialCode;
    /**
     * 是否皮试：0否、1是（SF）
     */
    private String isSkin;
    /**
     * 是否基药：0否、1是（SF）
     */
    private String isBasic;
    /**
     * 基药代码（JY）
     */
    private String basicCode;
    /**
     * 批准文号
     */
    private String dan;
    /**
     * 国药准字号
     */
    private String ndan;
    /**
     * 生产厂家编码
     */
    private String prodCode;
    /**
     * 通用名拼音码
     */
    private String usualPym;
    /**
     * 通用名五笔码
     */
    private String usualWbm;
    /**
     * 商品名拼音码
     */
    private String goodPym;
    /**
     * 商品名五笔码
     */
    private String goodWbm;
    /**
     * 是否有效：0否、1是（SF）
     */
    private String isValid;
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
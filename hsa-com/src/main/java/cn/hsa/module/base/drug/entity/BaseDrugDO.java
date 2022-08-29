package cn.hsa.module.base.drug.entity;

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
 * @Package_name: cn.hsa.module.base.drug.entity
 * @Class_name:: BaseDrugDO
 * @Description: 药品管理数据库映射对象
 * @Author: liaojunjie
 * @Date: 2020/7/16 16:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDrugDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -49437964728620024L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 财务分类编码（表base_finance_classify）
     */
    private String bfcCode;
    /**
     * 药品分类代码（YPFL）
     */
    private String typeCode;
    /**
     * 药品大类代码（YPDL）
     */
    private String bigTypeCode;
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
    private BigDecimal price = BigDecimal.valueOf(0);
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
     * 平均购进价
     */
    private BigDecimal avgBuyPrice;
    /**
     * 平均零售价
     */
    private BigDecimal avgSellPrice;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio = BigDecimal.valueOf(1);
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice = BigDecimal.valueOf(0);
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
     * 国家卫健委编码名称
     */
    private String nationName;
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
     * 是否处方药：0否、1是（SF）
     */
    private String isPrescription;
    /**
     * 基药代码（JY）
     */
    private String basicCode;
    /**
     * 质子泵
     */
    private String protonPump;
    /**
     * 国药准字号
     */
    private String ndan;
    /**
     * 生产厂家编码（表base_product）
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
    /**
     * 批准文号
     */
    private String dan;

    // 报销比例
    private String reimbursementRatio;
    /**
     * 开药周期（几天内）
     */
    private String prescribingCycle;
    /**
     * 允许开药天数（几天内允许开药次数）
     */
    private String numOfPrescAllowed;

}

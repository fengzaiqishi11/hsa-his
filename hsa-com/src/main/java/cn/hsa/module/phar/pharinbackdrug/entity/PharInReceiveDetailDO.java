package cn.hsa.module.phar.pharinbackdrug.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.entity
 *@Class_name: PharInWaitReceiveDO
 *@Describe: 住院领药申请详情
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 16:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharInReceiveDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 920809994166504821L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 待领ID
     */
    private String wrId;
    /**
     * 领药申请ID
     */
    private String receiveId;
    /**
     * 医嘱ID
     */
    private String adviceId;
    /**
     * 医嘱组号
     */
    private String groupNo;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品/材料）
     */
    private String itemId;
    /**
     * 项目名称（药品/材料）
     */
    private String itemName;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 规格
     */
    private String spec;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum;
    /**
     * 用法代码（YF）
     */
    private String usageCode;

    //开单单位
    private String currUnitCode;
}
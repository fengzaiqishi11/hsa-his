package cn.hsa.module.phar.pharoutreceive.entity;/*

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
* @Package_name: 
* @Class_name: DO
* @Describe: 
* @Author: liaojiguang
* @Email: liaojiguang@powersi.com.cn
* @Date: 2020/9/07 13:50
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDetailDO extends PageDO implements java.io.Serializable {

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 领药申请ID */
    private String orId;

    /** 处方ID */
    private String opId;

    /** 处方明细ID */
    private String opdId;

    /** 费用ID */
    private String costId;

    /** 项目类型代码（XMLB） */
    private String itemCode;

    /** 项目ID（药品/材料） */
    private String itemId;

    /** 项目名称（药品/材料） */
    private String itemName;

    /** 规格 */
    private String spec;

    /** 剂量 */
    private BigDecimal dosage;

    /** 剂量单位代码（JLDW） */
    private String dosageUnitCode;

    /** 数量 */
    private BigDecimal num;

    /** 单位代码（DW） */
    private String unitCode;

    /** 单价 */
    private BigDecimal price;

    /** 拆分比 */
    private BigDecimal splitRatio;

    /** 拆零单位代码（DW） */
    private String splitUnitCode;

    /** 拆零数量 */
    private BigDecimal splitNum;

    /** 拆零单价 */
    private BigDecimal splitPrice;

    /** 总金额 */
    private BigDecimal totalPrice;

    /** 中药付数 */
    private BigDecimal chineseDrugNum;

    /** 用法代码（YF） */
    private String usageCode;

    /** 用药性质代码（YYXZ） */
    private String useCode;

    /** 开单单位 */
    private String currUnitCode;
}
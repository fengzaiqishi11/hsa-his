package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceDTO
 *@Describe: 住院医嘱明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceDetailTempDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -66173986604315233L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 模板明细ID
     */
    private String iatId;

    /**
     * 医嘱组号
     */
    private Integer groupNo;
    /**
     * 医嘱组内序号
     */
    private Integer groupSeqNo;
    /**
     * 医嘱分类代码（YZFL）
     */
    private String typeCode;

    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品、项目、材料、医嘱目录）
     */
    private String itemId;
    /**
     * 项目名称（药品、项目、材料、医嘱目录）
     */
    private String itemName;

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
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 频率ID
     */
    private String rateId;
    /**
     * 速度代码（SD）
     */
    private String speedCode;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 数量单位（DW）
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 用药天数
     */
    private Integer useDays;

    /**
     * 医嘱内容
     */
    private String content;

    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;

    private String execDeptId;
    private String herbNoteCode;
    private String isSkin;
    private String isGive;
    private String isLong;

    //类型
    private String type;
    //类型
    private String totalNumUnitCode;
    /**
     * 医嘱前缀
     */
    private String advicePrefix;
    /**
     * 医嘱后缀
     */
    private String adviceSuffix;

}

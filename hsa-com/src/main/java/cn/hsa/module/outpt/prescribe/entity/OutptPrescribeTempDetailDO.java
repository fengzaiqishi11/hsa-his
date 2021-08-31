package cn.hsa.module.outpt.prescribe.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.entity
 *@Class_name: OutptPrescribeTempDetailDO
 *@Describe: 处方模板明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 15:54
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeTempDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -72550744738640186L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 处方模板ID
     */
    private String optId;
    /**
     * 处方模板组号
     */
    private Integer groupNo;
    /**
     * 处方模板组内序号
     */
    private Integer groupSeqNo;
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
    private Double dosage;
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
    private Double num;
    /**
     * 数量单位（DW）
     */
    private String unitCode;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private Double totalNum;
    /**
     * 单价
     */
    private Double price;
    /**
     * 总金额
     */
    private Double totalPrice;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 用药天数
     */
    private Integer useDays;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 中草药脚注代码（ZYJZ）（中药调剂方法）
     */
    private String herbNoteCode;
    /**
     * 是否皮试：0否、1是（SF）
     */
    private String isSkin;
    /**
     * 处方内容
     */
    private String content;
    /**
     * 处方类别代码
     */
    private String typeCode;
    /**
     * 处方类型代码
     */
    private String prescribeTypeCode = "1";

}
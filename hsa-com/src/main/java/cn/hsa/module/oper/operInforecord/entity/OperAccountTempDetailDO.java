package cn.hsa.module.oper.operInforecord.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.inpt.operInfoRecord.entity
 *@Class_name: OperAccountTempDetailDO
 *@Describe: 手术补记账模板明细(OperAccountTempDetail)实体类
 *@Author: liuqi1
 *@Date: 2020-12-04 17:13:31
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperAccountTempDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 625232435456379376L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 模板id
     */
    private String tempId;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 财务分类名称
     */
    private String bfcName;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品、项目、材料、医嘱目录）
     */
    private String itemId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 数量单位（DW）
     */
    private String numUnitCode;
    /**
     * 规格
     */
    private String spec;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
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
     * 频率id（表base_rate）
     */
    private String rateId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
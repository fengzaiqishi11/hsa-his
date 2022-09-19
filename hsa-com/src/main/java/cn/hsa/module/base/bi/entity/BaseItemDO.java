package cn.hsa.module.base.bi.entity;

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
 * @Package_name: cn.hsa.module.base.bi.entity
 * @Class_name:: BaseItemDO
 * @Description: 项目管理数据库映射对象
 * @Author: liaojunjie
 * @Date: 2020/7/14 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseItemDO extends PageDO implements Serializable  {
    private static final long serialVersionUID = -43484976415870903L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 国家编码
     */
    private String nationCode;
    /**
     * 国家编码
     */
    private String nationName;
    /**
     * 项目分类代码（XMFL）
     */
    private String typeCode;
    /**
     * 财务分类编码（表base_finance_classify）
     */
    private String bfcCode;
    /**
     * 项目编码
     */
    private String code;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目别名
     */
    private String otherName;
    /**
     * 项目简称
     */
    private String abbr;
    /**
     * 项目规格
     */
    private String spec;
    /**
     * 项目单价
     */
    private BigDecimal price;
    /**
     * 一级价格
     */
    private BigDecimal onePrice;
    /**
     * 二级价格
     */
    private BigDecimal twoPrice;
    /**
     * 三级价格
     */
    private BigDecimal threePrice;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 项目内涵
     */
    private String intension;
    /**
     * 项目提示
     */
    private String prompt;
    /**
     * 除外内容
     */
    private String except;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否门诊可用：0否、1是（SF）
     */
    private String isOut;
    /**
     * 是否住院可用：0否、1是（SF）
     */
    private String isIn;
    /**
     * 是否可改价0否、1是（SF）（门诊直接划价收费用，如果直接划价收费选择的项目是可改价的，表格中价格一列是可以修改的）
     */
    private String isCg;
    /**
     * 是否医技可用：0否、1是（SF）
     */
    private String isMs;
    /**
     * 是否补记帐：0否、1是（SF）
     */
    private String isSuppCurtain;
    /**
     * 门诊执行科室编码（表base_dept）
     */
    private String outDeptCode;
    /**
     * 住院执行科室编码（表base_dept）
     */
    private String inDeptCode;
    /**
     * 名称拼音码
     */
    private String namePym;
    /**
     * 名称五笔码
     */
    private String nameWbm;
    /**
     * 简称拼音码
     */
    private String abbrPym;
    /**
     * 简称五笔码
     */
    private String abbrWbm;
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

    // 报销比例
    private String reimbursementRatio;
    /**
     * 是否生成医嘱
     */
    private String isGenerateAdvice;
}

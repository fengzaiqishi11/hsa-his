package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptAdviceDetailDO
 *@Describe: 住院医嘱明细表
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptAdviceDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 457969190719606833L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 住院医嘱ID（inpt_advice.id）
     */
    private String iaId;
    /**
     * 医嘱目录ID（base_advice.id）
     */
    private String baId;
    /**
     * 医嘱组号
     */
    private Integer iaGroupNo;
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
     * 项目数量
     */
    private BigDecimal num;
    /**
     * 项目数量单位（DW）
     */
    private String unitCode;
    /**
     * 项目单价
     */
    private BigDecimal price;
    /**
     * 项目总金额
     */
    private BigDecimal totalPrice;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 来源方式代码（FYLYFS）
     */
    private String sourceCode;
    /**
     * 拒收备注
     */
    private String rejectRemark;
    /**
     * 是否拒收
     */
    private String is_reject;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 限制使用标志
     */
    private String lmtUserFlag;
    /**
     * 限制使用说明
     */
    private String limUserExplain;
}
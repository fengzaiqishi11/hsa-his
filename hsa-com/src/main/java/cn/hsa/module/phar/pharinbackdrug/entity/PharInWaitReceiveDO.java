package cn.hsa.module.phar.pharinbackdrug.entity;

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
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.entity
 *@Class_name: PharInWaitReceiveDO
 *@Describe: 住院待领
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 15:26
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharInWaitReceiveDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -84642796208039374L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
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
     * 规格
     */
    private String spec;
    /**
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price;
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
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum;
    /**
     * 发药状态代码（LYZT）
     */
    private String statusCode;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 配药人ID
     */
    private String assignUserId;
    /**
     * 配药人姓名
     */
    private String assignUserName;
    /**
     * 配药时间
     */
    private Date assignTime;
    /**
     * 发药人ID
     */
    private String distUserId;
    /**
     * 发药人姓名
     */
    private String distUserName;
    /**
     * 发药时间
     */
    private Date distTime;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 申请科室ID
     */
    private String deptId;
    /**
     * 是否紧急（SF）
     */
    private String isEmergency;
    /**
     * 是否退药（SF）
     */
    private String isBack;
    /**
     * 费用明细ID
     */
    private String costId;
    /**
     * 原待领ID
     */
    private String oldWrId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    /** 开单单位 */
    private String currUnitCode;
    /**
     * 提前领药ID
     */
    private String advanceId;
}
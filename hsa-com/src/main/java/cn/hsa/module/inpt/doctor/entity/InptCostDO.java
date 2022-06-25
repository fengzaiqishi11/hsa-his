package cn.hsa.module.inpt.doctor.entity;

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
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptCostDO
 *@Describe: 住院费用表
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
public class InptCostDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -98267781150663620L;
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
     * 婴儿ID
     */
    private String babyId;
    /**
     * 医嘱ID
     */
    private String iatId;
    /**
     * 医嘱组号
     */
    private Integer iatdGroupNo;
    /**
     * 医嘱组内序号
     */
    private Integer iatdSeqNo;
    /**
     * 医嘱执行签名ID
     */
    private String adviceExecId;
    /**
     * 费用来源方式代码（FYLYFS）
     */
    private String sourceCode;
    /**
     * 费用来源ID
     */
    private String sourceId;
    /**
     * 原费用ID
     */
    private String oldCostId;
    /**
     * 来源科室ID
     */
    private String sourceDeptId;
    /**
     * 就诊科室ID
     */
    private String inDeptId;
    /**
     * 财务分类ID
     */
    private String bfcId;
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
     * 用药天数
     */
    private Integer useDays;
    /**
     * 数量单位（DW）
     */
    private String numUnitCode;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum;
    /**
     * 总数量单位（DW）
     */
    private String totalNumUnitCode;
    /**
     * 中草药脚注代码（ZYJZ）（中药调剂方法）
     */
    private String herbNoteCode;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 中草药付（剂）数
     */
    private BigDecimal herbNum;
    /**
     * 项目总金额
     */
    private BigDecimal totalPrice;
    /**
     * 优惠总金额
     */
    private BigDecimal preferentialPrice = BigDecimal.valueOf(0);
    /**
     * 优惠后总金额
     */
    private BigDecimal realityPrice;
    /**
     * 退药数量
     */
    private BigDecimal backNum;
    /**
     * 开嘱医生ID
     */
    private String doctorId;
    /**
     * 开嘱医生姓名
     */
    private String doctorName;
    /**
     * 开嘱科室ID
     */
    private String deptId;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 是否已发药（SF）
     */
    private String isDist;
    /**
     * 是否交病人（SF）：临时医嘱
     */
    private String isGive;

    /**
     * 退药状态代码（TYZT）:0、正常，1、已退费未退药，2、已退费已退药
     */
    private String backCode;
    /**
     *是否确费：0、未确认，1、已确认
     */
    private String isOk;
    /**
     *确费人ID
     */
    private String okId;
    /**
     *确费人姓名
     */
    private String okName;
    /**
     *确费时间
     */
    private Date okTime;
    /**
     * 结算状态代码： 0未结算，1预结算，2已结算
     */
    private String settleCode;
    /**
     * 结算ID（outpt_settle）
     */
    private String settleId;
    /**
     * 是否核对（SF）
     */
    private String isCheck;
    /**
     * 费用核对人ID
     */
    private String checkId;
    /**
     * 费用核对人姓名
     */
    private String checkName;
    /**
     * 费用核对时间
     */
    private Date checkTime;
    /**
     * 主治医生ID
     */
    private String zzDoctorId;
    /**
     * 主治医生名称
     */
    private String zzDoctorName;
    /**
     * 经治医生ID
     */
    private String jzDoctorId;
    /**
     * 经治医生名称
     */
    private String jzDoctorName;
    /**
     * 主管医生ID
     */
    private String zgDoctorId;
    /**
     * 主管医生名称
     */
    private String zgDoctorName;
    /**
     * 执行人ID
     */
    private String execId;
    /**
     * 执行人姓名
     */
    private String execName;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date execTime;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 计划执行时间
     */
    private Date planExecTime;
    /**
     * 状态标志代码（ZTBZ）
     */
    private String statusCode;
    /**
     * 是否已记账（SF）
     */
    private String isCost;
    /**
     * 计费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date costTime;
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

    /**
     * 是否生成待领数据
     */
    private String isWait;

    private String distributeAllDetailId;

    private String feedetlSn; //医保费用明细流水号

    /**
     * 限制使用标志
     */
    private String lmtUserFlag;
    /**
     * 限制使用说明
     */
    private String limUserExplain;
    /**
     * 是否报销
     */
    private String isReimburse;

    /**
     * 费用类型标识
     */
    private String attributionCode;

    /**
     * 提前领药ID
     */
    private String advanceId;
}

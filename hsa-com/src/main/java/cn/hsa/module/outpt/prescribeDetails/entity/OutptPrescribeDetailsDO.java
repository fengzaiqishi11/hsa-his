package cn.hsa.module.outpt.prescribeDetails.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.entity
 * @Class_name: OutptPrescribeDetailsDO
 * @Describe: 门诊处方明细entity
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeDetailsDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 8336494033412956753L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 处方ID
     */
    private String opId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 处方模板ID
     */
    private String optId;
    /**
     * 处方模板内组号
     */
    private Integer optdGroupNo;
    /**
     * 处方模板组内序号
     */
    private Integer optdGroupSeqNo;
    /**
     * 处方模板明细ID
     */
    private String optdId;
    /**
     * 处方组号
     */
    private Integer groupNo;
    /**
     * 处方组内序号
     */
    private Integer groupSeqNo;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（项目/材料）
     */
    private String itemId;
    /**
     * 项目名称（项目/材料）
     */
    private String itemName;
    /**
     * 单价
     */
    private BigDecimal price = BigDecimal.valueOf(0);
    /**
     * 总金额
     */
    private BigDecimal totalPrice = BigDecimal.valueOf(0);
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
     * 数量
     */
    private BigDecimal num = BigDecimal.valueOf(0);
    /**
     * 数量单位（DW）
     */
    private String numUnitCode;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum = BigDecimal.valueOf(0);
    /**
     * 总数量单位
     */
    private String totalNumUnitCode;
    /**
     * 中草药脚注代码（ZYJZ）（中药调剂方法）
     */
    private String herbNoteCode;
    /**
     * 是否皮试（SF）
     */
    private String isSkin;
    /**
     * 是否阳性（SF）
     */
    private String isPositive;
    /**
     * 处方内容
     */
    private String content;
    /**
     * 领药药房ID
     */
    private String pharId;
    /**
     * 财务分类ID
     */
    private String bfcId;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 执行科室ID
     */
    private String execDeptId;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date execDate;
    /**
     * 执行人ID
     */
    private String execId;
    /**
     * 执行人姓名
     */
    private String execName;
    /**
     * 本院执行次数
     */
    private Integer execNum;
    /**
     * 医技申请单号
     */
    private String technologyNo;
    /**
     * 皮试换药药品ID
     */
    private String skinDurgId;
    /**
     * 皮试换药药品药房ID
     */
    private String skinPharId;
    /**
     * 皮试换药药品单位
     */
    private String skinUnitCode;
    /**
     * 处方前缀
     */
    private String prescribePrefix;
    /**
     * 处方后缀
     */
    private String prescribeSuffix;
    /**
     * 备注
     */
    private String remark;
    /**
     * 煎药方式
     */
    private String decoctionMethod;
}

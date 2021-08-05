package cn.hsa.module.outpt.prescribeDetails.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.entity
 * @Class_name: OutptPrescribeDetailsDO
 * @Describe: 门诊处方明细执行
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com.cn
 * @Date: 2020/9/8 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeDetailsExtDO extends PageDO implements Serializable {
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
    private String opdId;
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
     * 医嘱目录ID
     */
    private String adviceId;
    /**
     * 医保自费比例
     */
    private String selfRatio;
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
    private BigDecimal price;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
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
     * 用药天数
     */
    private Integer useDays;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 总数量（数量*频率*用药天数）
     */
    private BigDecimal totalNum;
    /**
     * 处方内容
     */
    private String content;
    /**
     * 数量单位
     */
    private String numUnitCode;
    /**
     * 领药药房ID
     */
    private String pharId;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
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
}

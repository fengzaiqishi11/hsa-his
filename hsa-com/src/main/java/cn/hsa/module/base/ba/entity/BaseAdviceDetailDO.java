package cn.hsa.module.base.ba.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 表名含义：
 * base：基础模块
 * advice：医嘱
 * detail：明细
 * (BaseAdviceDetail)实体类
 *
 * @author xingyu.xie
 * @since 2020-09-04 08:43:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseAdviceDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -93268675945729633L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 医嘱编码
     */
    private String adviceCode;
    /**
     * 项目类别代码（XMLB）
     */
    private String typeCode;
    /**
     * 项目编码
     */
    private String itemCode;
    /**
     * 项目名称
     */
    private String itemName;
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
     * 规格
     */
    private String spec;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 是否数量独立计费（SF）
     */
    private String isAloneCost;
    /**
     * 是否仅首次计费（SF）
     */
    private String isFristCost;
    /**
     * 是否指定频率（SF）
     */
    private String isAppointRate;
    /**
     * 医嘱频率编码（表base_rate）
     */
    private String rateCode;
    /**
     * 医保目录标识代码（YBMLBS）
     */
    private String insureListCode;


}
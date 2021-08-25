package cn.hsa.module.sync.advice.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.base.data.entity
 * @Class_name: BaseMaterialDO
 * @Describe:  医嘱详情管理数据库映射对象
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/13 15:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SyncAdviceDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 234447821465644075L;
    /**
     * 主键
     */
    private String id;
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
package cn.hsa.module.stro.adjust.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@Package_name: cn.hsa.module.stro.adjust.entity
 *@Class_name: StroAdjustDetailDO
 *@Describe: 调价明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/7/31 17:26
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StroAdjustDetailDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 119254746722289565L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 调价ID（表stro_adjust）
     */
    private String adjustId;
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
     * 调前零售单价
     */
    private BigDecimal beforePrice;
    /**
     * 调后零售单价
     */
    private BigDecimal afterPrice;
    /**
     * 购进单价
     */
    private BigDecimal buyPrice;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 单位
     */
    private String unitCode;
    /**
     * 拆分比
     */
    private Integer splitRatio;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 拆零单位
     */
    private String splitUnitCode;
    /**
     * 拆零购进单价
     */
    private BigDecimal splitBuyPrice;
    /**
     * 调前拆零零售单价
     */
    private BigDecimal splitBeforePrice;
    /**
     * 调后拆零零售单价
     */
    private BigDecimal splitAfterPrice;
    /**
     * 调价的科室
     */
    private String bizId;

}

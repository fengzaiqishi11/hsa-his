package cn.hsa.module.phar.pharoutdistribute.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 表名含义：
 * phar：药房模块缩写，pharmacy
 * out：门诊
 * distribu(PharOutDistributeDetail)实体类
 *
 * @author makejava
 * @since 2020-08-26 11:09:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutDistributeDetailDO implements Serializable {
    private static final long serialVersionUID = -36465487422228438L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 发药ID
     */
    private String distributeId;
    /**
     * 处方ID
     */
    private String opId;
    /**
     * 处方明细ID
     */
    private String opdId;
    /**
     * 原费用ID
     */
    private String oldCostId;
    /**
     * 费用ID
     */
    private String costId;
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
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 库存明细ID
     */
    private String stockDetailId;
    /**
     * 批号
     */
    private String batchNo;
    /**
     * 当前退药数量
     */
    private BigDecimal backNum;
    /**
     * 累计退药数量
     */
    private BigDecimal totalBackNum;
    /**
     * 上期批号结余数量
     */
    private BigDecimal upBatchSurplusNum;
    /**
     * 本期批号结余数量
     */
    private BigDecimal batchSurplusNum;
    /**
     * 上期购进总金额
     */
    private BigDecimal upBuyPriceAll;
    /**
     * 本期购进总金额
     */
    private BigDecimal buyPriceAll;
    /**
     * 上期零售总金额
     */
    private BigDecimal upSellPriceAll;
    /**
     * 本期零售总金额
     */
    private BigDecimal sellPriceAll;
    /**
     * 原发药明细ID
     */
    private String oldDistId;
    /**
     * 退药状态代码（TYZT）
     */
    private String statusCode;
    /**
     * 开单单位
     */
    private String currUnitCode;

    /**
     * 发药明细汇总id
     */
    private String distributeAllDetailId;
}

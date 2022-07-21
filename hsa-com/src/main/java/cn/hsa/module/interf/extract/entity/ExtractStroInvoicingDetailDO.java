package cn.hsa.module.interf.extract.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import cn.hsa.base.PageDO;
/**
 * 同步药房实时进销存报表(ExtractStroInvoicingDetail)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractStroInvoicingDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 869890362720398685L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 科室id
     */
    private String bizId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品/材料）
     */
    private String itemId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 大单位数量（入库为正，出库为负）
     */
    private BigDecimal num;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 当前单位代码（DW）
     */
    private String currUnitCode;
    /**
     * 零售单价
     */
    private BigDecimal sellPrice;
    /**
     * 购进单价
     */
    private BigDecimal buyPrice;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 本日期初数量
     */
    private BigDecimal upSurplusNum;
    /**
     * 本日期末数量
     */
    private BigDecimal surplusNum;
    /**
     * 本日期初购进总金额
     */
    private BigDecimal upBuyPriceAll;
    /**
     * 本日期末购进总金额
     */
    private BigDecimal buyPriceAll;
    /**
     * 本日期初零售总金额
     */
    private BigDecimal upSellPriceAll;
    /**
     * 本日期末零售总金额
     */
    private BigDecimal sellPriceAll;
    /**
     * 创建时间（操作）
     */
    private Date crteTime;
    /**
     * 新价格
     */
    private BigDecimal newPrice;
    /**
     * 新拆零单价
     */
    private BigDecimal newSplitPrice;
    /**
     * 药库入库数量
     */
    private BigDecimal stroInNum;
    /**
     * 药库入库零售总金额
     */
    private BigDecimal stroInNumPriceAll;
    /**
     * 药库退供应商数量
     */
    private BigDecimal returnSuplNum;
    /**
     * 药库退供应商零售总金额
     */
    private BigDecimal returnSuplPriceAll;
    /**
     * 药库出库到科室数量
     */
    private BigDecimal stroToDeptNum;
    /**
     * 药库出库到科室零售总金额
     */
    private BigDecimal stroToDeptPriceAll;
    /**
     * 药库出库到药房数量
     */
    private BigDecimal stroToPharNum;
    /**
     * 药库出库到药房零售总金额
     */
    private BigDecimal stroToPharPriceAll;
    /**
     * 报损消耗数量
     */
    private BigDecimal reportLossesNum;
    /**
     * 报损消耗数量零售总金额
     */
    private BigDecimal reportLossesPriceAll;
    /**
     * 药房退库数量
     */
    private BigDecimal pharReturnStroNum;
    /**
     * 药房退库零售总金额
     */
    private BigDecimal pharReturnStroPriceAll;
    /**
     * 调价调盈零售总金额
     */
    private BigDecimal adjustProfitPriceAll;
    /**
     * 调价亏损零售总金额
     */
    private BigDecimal adjustLossPriceAll;
    /**
     * 盘亏数量
     */
    private BigDecimal takeStrockSubtractNum;
    /**
     * 盘亏零售总金额
     */
    private BigDecimal takeStrockSubtractPriceAll;
    /**
     * 盘盈总数量
     */
    private BigDecimal takeStrockAddNum;
    /**
     * 盘盈零售总金额
     */
    private BigDecimal takeStrockAddPriceAll;
    /**
     * 药库入库购进总金额
     */
    private BigDecimal stroInNumBuyPriceAll;
    /**
     * 药库退供应商购进总金额
     */
    private BigDecimal returnSuplBuyPriceAll;
    /**
     * 药库出库到科室购进总金额
     */
    private BigDecimal stroToDeptBuyPriceAll;
    /**
     * 药库出库到药房购进总金额
     */
    private BigDecimal stroToPharBuyPriceAll;
    /**
     * 报损消耗数量购进总金额
     */
    private BigDecimal reportLossesBuyPriceAll;
    /**
     * 药房退库购进总金额
     */
    private BigDecimal pharReturnStroBuyPriceAll;
    /**
     * 调价调盈购进总金额
     */
    private BigDecimal adjustProfitBuyPriceAll;
    /**
     * 调价亏损购进总金额
     */
    private BigDecimal adjustLossBuyPriceAll;
    /**
     * 盘亏购进总金额
     */
    private BigDecimal takeStrockSubtractBuyPriceAll;
    /**
     * 盘盈购进总金额
     */
    private BigDecimal takeStrockAddBuyPriceAll;
    /**
     * 门诊销售数量
     */
    private BigDecimal outSalesNum;
    /**
     * 门诊销售零售总金额
     */
    private BigDecimal outSalesPriceAll;
    /**
     * 门诊销售购进总金额
     */
    private BigDecimal outSalesBuyPriceAll;
    /**
     * 住院销售购进总金额
     */
    private BigDecimal inSalesBuyPriceAll;
    /**
     * 住院销售零售总金额
     */
    private BigDecimal inSalesPriceAll;
    /**
     * 住院销售数量
     */
    private BigDecimal inSalesNum;
    /**
     * 月度进销存主表id
     */
    private String extractId;
    /**
     * 规格
     */
    private String spec;
    /**
     * 项目编码
     */
    private String code;
    /**
     * 同步抽取时间
     */
    private Date extractTime;

    /**
     * 项目分类代码
     */
    private String itemType;
    /**
     * 项目分类代码名称
     */
    private String itemTypeName;

    /**
     * 药房退库销售总金额
     */
    private BigDecimal pharReturnStroSellPriAll;

    /**
     * 药房退库购进总金额
     */
    private BigDecimal pharReturnStroBuyPriAll;

    /**
     * 药房退库总数量
     */
    private BigDecimal pharReturnStroTotalNum;

    /**
     * 药房入库确认零售总金额
     */
    private BigDecimal pharInNumSellPriceAll;

    /**
     * 药房入库确认购进总金额
     */
    private BigDecimal pharInNumBuyPriceAll;

    /**
     * 药房入库确认总数量
     */
    private BigDecimal pharInNum;



}


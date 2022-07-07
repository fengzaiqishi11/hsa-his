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
 * 药房消耗表(ExtractConsumptionDetail)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractConsumptionDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 912236569112049503L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 汇总类型(1:含盘点 2:不含盘点)
     */
    private String summaryType;
    /**
     * 统计选项（1:仅统计消耗，2:按先进先出算成本,3:按加权平均值算成本）
     */
    private String statisticsOptions;
    /**
     * 项目id
     */
    private String itemId;
    /**
     * 项目类别
     */
    private String itemCode;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 项目编码(基础数据的code)
     */
    private String code;
    /**
     * 药品分为：西药、中药等
     */
    private String typeCode;
    /**
     * 规格
     */
    private String spec;
    /**
     * 单位
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 拆零单位
     */
    private String splitUnitCode;
    /**
     * 库房科室id
     */
    private String bizId;
    /**
     * 消耗数量
     */
    private BigDecimal consumNum;
    /**
     * 平均售价
     */
    private BigDecimal avgSellPrice;
    /**
     * 零售总金额
     */
    private BigDecimal sellPriceAll;
    /**
     * 平均成本价成本价
     */
    private BigDecimal avgBuyPrice;
    /**
     * 成本金额
     */
    private BigDecimal buyPriceAll;
    /**
     * 盈利
     */
    private BigDecimal profitPrice;
    /**
     * 1:定时器自动同步，2:手动同步
     */
    private String extractType;
    /**
     * 同步抽取时间
     */
    private Date extractTime;
    /**
     * 更新时间
     */
    private Date renewalTime;
    /**
     * 抽取主表id
     */
    private String extractId;


}


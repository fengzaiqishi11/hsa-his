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
 * 药房药库xxxx报表类型 按xxx/单据汇总方式(ExtractBusinessBills)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractBusinessBillsDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -53182968744032115L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 报表类型
     */
    private String reportType;
    /**
     * 筛选科室的id
     */
    private String bizId;
    /**
     * 目标科室id
     */
    private String targetBizId;
    /**
     * 目标名称
     */
    private String targetBizName;
    /**
     * 单据号
     */
    private String orderNo;
    /**
     * 时间
     */
    private Date crteTime;
    /**
     * 明细笔数
     */
    private Integer detailNum;
    /**
     * 品种总数
     */
    private Integer itemNum;
    /**
     * 批次总数
     */
    private Integer batchNoNum;
    /**
     * 品种平均购进金额
     */
    private BigDecimal avgItemBuyPrice;
    /**
     * 购进金额
     */
    private BigDecimal buyPriceAll;
    /**
     * 零售金额
     */
    private BigDecimal sellPriceAll;
    /**
     * 购零差
     */
    private BigDecimal priceSpread;
    /**
     * 同步抽取时间
     */
    private Date extractTime;


}


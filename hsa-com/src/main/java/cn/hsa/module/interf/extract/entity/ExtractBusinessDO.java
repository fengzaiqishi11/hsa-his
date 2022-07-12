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
 * 药房药库xxxx报表类型 按xxx汇总方式(ExtractBusiness)实体类
 *
 * @author gory
 * @since 2022-07-06 10:15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractBusinessDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -13639574467184853L;
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
     * 单据张数
     */
    private Integer mainNum;
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
     * 最后操作时间
     */
    private Date lastTime;
    /**
     * 单据平均购进金额
     */
    private BigDecimal avgMainBuyPrice;
    /**
     * 品种平均购进金额
     */
    private BigDecimal avgItemBuyPrice;
    /**
     * 购进金额
     */
    private BigDecimal buyPriceAll;
    /**
     * 购零差
     */
    private BigDecimal priceSpread;
    /**
     * 零售金额
     */
    private BigDecimal sellPriceAll;
    /**
     * 同步抽取时间
     */
    private Date extractTime;


}


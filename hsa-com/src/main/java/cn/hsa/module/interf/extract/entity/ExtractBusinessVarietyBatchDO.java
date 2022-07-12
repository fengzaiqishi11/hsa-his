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
 * 药房药库xxxx报表类型 按xxx/品种批次汇总方式(ExtractBusinessVarietyBatch)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractBusinessVarietyBatchDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 541203173308657533L;
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
     * 品名
     */
    private String itemName;
    /**
     * 编码
     */
    private String code;
    /**
     * 规格
     */
    private String spec;
    /**
     * 类别
     */
    private String typeCode;
    /**
     * 单位
     */
    private String unitCode;
    /**
     * 生产企业
     */
    private String prodName;
    /**
     * 生产企业id
     */
    private String prodId;
    /**
     * 批号
     */
    private String batchNo;
    /**
     * 有效期
     */
    private Date expiryDate;
    /**
     * 购进价
     */
    private BigDecimal buyPrice;
    /**
     * 零售价
     */
    private BigDecimal sellPrice;
    /**
     * 入库数量
     */
    private BigDecimal num;
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


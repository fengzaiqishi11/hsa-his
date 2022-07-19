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
 * 药房药库盘点报表类型(ExtractBusinessInventory)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractBusinessInventoryDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 260495239415173741L;
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
     * 单据
     */
    private String orderNo;
    /**
     * 盘点人
     */
    private String crteName;
    /**
     * 审核人
     */
    private String checkName;
    /**
     * 项目编码
     */
    private String itemCode;
    /**
     * 品名
     */
    private String itemName;
    /**
     * 规格
     */
    private String spec;
    /**
     * 类别
     */
    private String bigTypeCode;
    /**
     * 单位
     */
    private String unitCode;
    /**
     * 购进价
     */
    private BigDecimal buyPrice;
    /**
     * 零售价
     */
    private BigDecimal sellPrice;
    /**
     * 账存数
     */
    private BigDecimal upSurplusNum;
    /**
     * 实盘数
     */
    private BigDecimal surplusNum;
    /**
     * 盘亏数
     */
    private BigDecimal numSpread;
    /**
     * 损益金额
     */
    private BigDecimal priceSpread;
    /**
     * 有效期
     */
    private Date expiryDate;
    /**
     * 批号
     */
    private String batchNo;
    /**
     * 生产企业
     */
    private String prodName;
    /**
     * 生产企业id
     */
    private String prodId;
    /**
     * 时间
     */
    private Date crteTime;
    /**
     * 同步抽取时间
     */
    private Date extractTime;


}


package entity;

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
 * 药房药库盘点报表类型(ExtractBusinessAdjust)实体类
 *
 * @author gory
 * @since 2022-07-06 10:13:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ExtractBusinessAdjustDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 206829296345151137L;
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
     * 调价时间
     */
    private Date crteTime;
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
     * 原零售价
     */
    private BigDecimal upSellPrice;
    /**
     * 新零售价
     */
    private BigDecimal sellPrice;
    /**
     * 差价
     */
    private BigDecimal priceSpread;
    /**
     * 差率
     */
    private BigDecimal poorRate;
    /**
     * 库位名称
     */
    private String bizName;
    /**
     * 账存数
     */
    private BigDecimal upSurplusNum;
    /**
     * 损益金额
     */
    private BigDecimal priceSpreadAll;
    /**
     * 抽取同步时间
     */
    private Date extractTime;


}


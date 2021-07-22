package cn.hsa.module.stro.inventory.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ljh
 * @date 2020/07/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StroInventoryDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -25487061965971397L;
    //主键
    private String id;
    //医院编码
    private String hospCode;
    //盘点单ID（表stro_inventory）
    private String inventoryId;
    //项目类型代码（药品/材料）（YWLB过滤项目）
    private String itemCode;
    //项目ID（药品/材料）（表base_drug\表base_material）
    private String itemId;
    //项目名称（药品/材料）
    private String itemName;
    //批号
    private String batchNo;
    //有效期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    //盘点结论代码（PDJL）
    private String resultCode;
    //盘点前总数量
    private BigDecimal beforeNum;
    //盘点前拆零总数量
    private BigDecimal beforeSplitNum;
    //实盘总数量
    private BigDecimal finalNum;
    //损益总数量
    private BigDecimal incdecNum;
    //实盘拆零总数量
    private BigDecimal finalSplitNum;
    //单位
    private String unitCode;
    //购进单价
    private BigDecimal buyPrice;
    //零售单价
    private BigDecimal sellPrice;
    //拆零单位
    private String splitUnitCode;
    //拆零购进单价
    private BigDecimal splitBuyPrice;
    //拆零零售单价
    private BigDecimal splitSellPrice;
    //盘点损益购进总金额
    private BigDecimal incdecBuyPrice;
    //盘点损益零售总金额
    private BigDecimal incdecSellPrice;
    //货架号
    private String locationNo;
}

package cn.hsa.module.stro.inventory.dto;

import cn.hsa.module.stro.inventory.entity.StroInventoryDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class StroInventoryDetailDTO extends StroInventoryDetailDO implements Serializable {
    private static final long serialVersionUID = -25487061965973397L;
    //拆分比
    private BigDecimal splitRatio;
    //单据号
    private String orderno;
    // 材料/药品的编码
    private String code;
    // 材料/药品的规格
    private String spec;
    // 材料/药品的生产企业名
    private String prodName;
    private String model; // 材料型号
    // 进销存目标名称
    private String invoicingTargetName;
    // 进销存目标ID
    private String invoicingTargetId;
    // 盘前购进总金额
    private BigDecimal beforBuyPriceAll;
    // 盘后购进总金额
    private BigDecimal afterBuyPriceAll;
    // 盘前零售总金额
    private BigDecimal beforSellPriceAll;
    // 盘后零售总金额
    private BigDecimal afterSellPriceAll;
    //分类
    private String typeCode;
    public StroInventoryDetailDTO() {

    }
}

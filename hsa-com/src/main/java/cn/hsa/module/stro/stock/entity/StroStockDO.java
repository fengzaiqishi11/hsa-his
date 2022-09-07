package cn.hsa.module.stro.stock.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ljh
 * @date 2020/07/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroStockDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -41065628269023745L;

    //主键
    private String id;
    //医院编码
    private String hospCode;
    //库房ID（药库/药房）
    private String bizId;
    //库位码（货架号）
    private String locationNo;
    //项目类型代码（药品/材料）
    private String itemCode;
    //项目ID（药品/材料）
    private String itemId;
    //项目名称（药品/材料）
    private String itemName;
    //规格
    private String spec;
    //剂型代码
    private String prepCode;
    //库存总数量
    private BigDecimal num = BigDecimal.valueOf(0);
    //单位代码
    private String unitCode;
    //购进总金额
    private BigDecimal buyPriceAll;
    //零售总金额
    private BigDecimal sellPriceAll;
    //拆零总数量
    private BigDecimal splitNum;
    //拆零单价
    private BigDecimal splitPrice;
    //拆零单位代码
    private String splitUnitCode;
    //库存上限
    private BigDecimal stockMax;
    //库存下限
    private BigDecimal stockMin;
    //占用库存
    private BigDecimal stockOccupy;
    //拆分比
    private BigDecimal splitRatio;
    // 是否上传到医保
    private String isUploadToInsure;
    // 过期占存数量
    private BigDecimal expireStockOccupy;
}

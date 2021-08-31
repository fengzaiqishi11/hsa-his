package cn.hsa.module.stro.purchase.entity;

import cn.hsa.base.PageDO;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @Package_name: cn.hsa.module.stro.purchase.entity
 * @Class_name: StroPurchaseDetailDO
 * @Describe: 药品采购明细 Model对象
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@ToString
public class StroPurchaseDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 890629419097358533L;
    //主键
    private String id;
    //医院编码
    private String hospCode;
    //采购ID
    private String purchaseId;
    //项目类型代码（药品/材料）
    private String itemCode;
    //项目ID（药品/材料）
    private String itemId;
    //项目名称（药品/材料）
    private String itemName;
    //规格
    private String spec;
    //数量
    private Integer num;
    //单位代码
    private String unitCode;
    //购进单价
    private BigDecimal buyPrice;
    //零售单价
    private BigDecimal sellPrice;
    //购进总金额
    private BigDecimal buyPriceAll;
    //零售总金额
    private BigDecimal sellPriceAll;
    //顺序号
    private Integer seqNo;
    //当时库存数量
    private Integer stockNum;
    //生产厂家ID
    private String productId;
}
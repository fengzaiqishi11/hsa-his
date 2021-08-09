package cn.hsa.module.stro.stroinvoicing.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * @Package_name: cn.hsa.module.stro.stroinvoicing.entity
 * @Class_name:: StroInvoicingDO
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/25 9:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class StroInvoicingDO  extends PageDO implements Serializable {
    private static final long serialVersionUID = -25487061965971097L;
    private String id;
    //医院编码
    private String hospCode;
    //出入方式代码（CRFS）
    private String outinCode;
    //单据号（表stro_outin_detail、stro_inventory_detail、stro_incdec_detail）
    private String orderNo;
    //业务ID（记录科室ID和供应商ID）
    private String bizId;
    //项目类型代码（药品/材料）（YWLB过滤项目）
    private String itemCode;
    //项目ID（药品/材料）（表base_drug\表base_material）
    private String itemId;
    //项目名称（药品/材料）
    private String itemName;
    //大单位数量（入库为正，出库为负）
    private BigDecimal num;
    //单位
    private String unitCode;
    //当前单位代码（DW）
    private String currUnitCode;
    //零售单价
    private BigDecimal sellPrice;
    //购进单价
    private BigDecimal buyPrice;
    //拆分比
    private BigDecimal splitRatio;
    //拆零单价
    private BigDecimal splitPrice;
    //拆零数量
    private BigDecimal splitNum;
    //拆零单位
    private String splitUnitCode;
    //批号
    private String batchNo;
    //批号结余数量
    private BigDecimal batchSurplusNum;
    //上期/期初数量
    private BigDecimal upSurplusNum = BigDecimal.valueOf(0);
    //本期/期末数量
    private BigDecimal surplusNum = BigDecimal.valueOf(0);
    //上期/期初购进总金额
    private BigDecimal upBuyPriceAll = BigDecimal.valueOf(0);
    //本期/期末购进总金额
    private BigDecimal buyPriceAll;
    //上期/期初零售总金额
    private BigDecimal upSellPriceAll;
    //本期/期末零售总金额
    private BigDecimal sellPriceAll;
    //有效期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryDate;
    //创建人ID（操作）
    private String crteId;
    //创建人姓名（操作）
    private String crteName;
    //创建时间（操作）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date crteTime;
    //时间戳
    private Long crteTimeStamp;

    // 进销存目标名称
    private String invoicingTargetName;
    // 进销存目标ID
    private String invoicingTargetId;
    // 新价格
    private BigDecimal newPrice;
    // 新拆零价格
    private BigDecimal newSplitPrice;
}

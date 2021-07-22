package cn.hsa.module.stro.stock.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.stro.stock.entity
 * @Class_name:: StroStockDetailDO
 * @Description: 进销存do
 * @Author: ljh
 * @Date: 2020/8/25 9:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroStockDetailDO extends PageDO implements Serializable {


    private static final long serialVersionUID = -41065628269073745L;


    //主键
    private String id;
    //医院编码
    private String hospCode;
    //库房ID（药库/药房）
    private String bizId;
    //项目类型代码（药品/材料）
    private String itemCode;
    //项目ID（药品/材料）
    private String itemId;
    //项目名称（药品/材料）
    private String itemName;
    //单位代码
    private String unitCode;
    //数量
    private BigDecimal num = BigDecimal.valueOf(0);
    //购进单价
    private BigDecimal buyPrice = BigDecimal.valueOf(0);
    //零售单价
    private BigDecimal sellPrice = BigDecimal.valueOf(0);
    //购进总金额
    private BigDecimal buyPriceAll = BigDecimal.valueOf(0);
    //零售总金额
    private BigDecimal sellPriceAll = BigDecimal.valueOf(0);
    //批号
    private String batchNo;
    //有效期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    //拆零数量
    private BigDecimal splitNum = BigDecimal.valueOf(0);
    //拆零单价
    private BigDecimal splitPrice = BigDecimal.valueOf(0);
    //拆零单位代码
    private String splitUnitCode;
    //拆分比
    private BigDecimal splitRatio;
}

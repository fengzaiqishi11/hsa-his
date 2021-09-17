package cn.hsa.module.phar.pharapply.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 表名含义：
stro：库房模块缩写，store仓库、room房间
out：出库
de(StroOutDetail)实体类
 *
 * @author makejava
 * @since 2020-08-20 17:35:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroOutDetail extends PageDO implements Serializable {
    private static final long serialVersionUID = 892806116633514561L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 出库ID
    */
    private String outId;
    /**
    * 项目类型代码（XMLB）
    */
    private String itemCode;
    /**
    * 项目ID（药品/材料）
    */
    private String itemId;
    /**
    * 项目名称（药品/材料）
    */
    private String itemName;
    /**
    * 规格
    */
    private String spec;
    /**
    * 数量
    */
    private BigDecimal num;
    /**
    * 单位代码（DW）
    */
    private String unitCode;
    /**
    * 剂量
    */
    private BigDecimal dosage;
    /**
    * 剂量单位代码（JLDW）
    */
    private String dosageUnitCode;
    /**
    * 零售单价
    */
    private BigDecimal buyPrice;
    /**
    * 购进单价
    */
    private BigDecimal sellPrice;
    /**
    * 零售总金额
    */
    private BigDecimal buyPriceAll;
    /**
    * 购进总金额
    */
    private BigDecimal sellPriceAll;
    /**
    * 拆分比
    */
    private BigDecimal splitRatio;
    /**
    * 拆零数量
    */
    private BigDecimal splitNum;
    /**
    * 拆零单价
    */
    private BigDecimal splitPrice;
    /**
    * 拆零单位代码（DW）
    */
    private String splitUnitCode;
    /**
    * 批号
    */
    private String batchNo;
    /**
    * 有效期
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date expiryDate;
    /**
     * 生产厂家名称
     */
    private String productName;

}
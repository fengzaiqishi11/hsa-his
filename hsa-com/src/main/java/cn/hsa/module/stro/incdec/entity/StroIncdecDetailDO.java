package cn.hsa.module.stro.incdec.entity;

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


/**
 *@Package_name: ccn.hsa.module.stro.incdec.entity
 *@Class_name: StroIncdecDetailDO
 *@Describe: 药品损益明细表
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-08-11 09:17:28
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StroIncdecDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -58675880762781370L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 损益ID（表stro_incdec）
     */
    private String adjustId;
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
     * 零售单价
     */
    private BigDecimal sellPrice;
    /**
     * 购进单价
     */
    private BigDecimal buyPrice;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 损益前零售总金额(损益前库存数量 * 零售单价)
     */
    private BigDecimal sellBeforePrice;
    /**
     * 损益后零售总金额((损益前库存数量 - 损益数量) * 零售单价)
     */
    private BigDecimal sellAfterPrice;
    /**
     * 损益前购进总金额(损益前库存数量 * 购进单价)
     */
    private BigDecimal buyBeforePrice;
    /**
     * 损益后购进总金额((损益前库存数量 - 损益数量) * 购进单价)
     */
    private BigDecimal buyAfterPrice;
    /**
     * 批号
     */
    private String batchNo;
    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    /**
     * 损益前库存数量
     */
    private BigDecimal beforeNum;
    /**
     * 损益数量
     */
    private BigDecimal num;
    /**
     * 单位
     */
    private String unitCode;
    /**
     * 盘点结论代码（PDJL）
     */
    private String resultCode;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零数量(损益前库存数量 * 拆分比)
     */
    private BigDecimal splitNum;
    /**
     * 拆零单位
     */
    private String splitUnitCode;
    /**
     * 损益类型
     */
    private String profitLossType;

}

package cn.hsa.module.phar.pharapply.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @PackageName: cn.hsa.module.phar.pharapply.entity
 * @Class_name: PharApplyDetailDO
 * @Description: 药房领药明细申请数据库映射对象
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/7 21:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharApplyDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -59785796953010054L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 项目类型代码（药品/材料）
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
    * 申领单号ID
    */
    private String applyId;
    /**
     * 规格
     */
    private String spec;
    /**
     * 剂量
     */
    private BigDecimal dosage = BigDecimal.valueOf(0);

    /**
     * 剂量单位代码
     */
    private String dosageUnitCode;

    /**
     * 拆分比
     */
    private BigDecimal splitRatio = BigDecimal.valueOf(0);
    /**
     * 拆零单位代码
     */
    private String splitUnitCode;
    /**
     * 拆零数量
     */

    private BigDecimal splitNum = BigDecimal.valueOf(0);
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice = BigDecimal.valueOf(0);
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum =BigDecimal.valueOf(0);
    /**
     * 用法
     */
    private String usageCode;
    /**
    * 购进单价
    */
    private BigDecimal buyPrice = BigDecimal.valueOf(0);
    /**
    * 零售单价
    */
    private BigDecimal sellPrice =BigDecimal.valueOf(0);
    /**
    * 申领数量
    */
    private BigDecimal num = BigDecimal.valueOf(0);
    /**
    * 单位代码
    */
    private String unitCode;
    /**
    * 购进总金额
    */
    private BigDecimal buyPriceAll =  BigDecimal.valueOf(0);
    /**
    * 零售总金额
    */
    private BigDecimal sellPriceAll = BigDecimal.valueOf(0);
    /**
    * 生产企业ID
    */
    private String productId;

}
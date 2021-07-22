package cn.hsa.module.phar.pharoutdistributedrug.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请明细表-Y
 * 表名含义：
 * phar：药房模块缩写，pharmacy
 * out：门诊
 * receive：领药
 * detail：明细
 *
 * 表说明：
 * 1、门诊为交完费（划价收费）才产生数据，划价收费对应处方明细表中药品信息。
 *
 * 业务逻辑：
 * 1、处方ID：直接划价收费无处方ID、处方明细ID。
 * 2、中药付数：默认为1，发药数量计算方式：发药数量 = 数量 * 中药付数。
 * 3、单位代码（DW）：为发药单位代码，可能存的是大单位（盒），也可能存小单位（片/粒）。
 * 4、用药性质代码（YYXZ）：项目类别代码为材料时用。
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutReceiveDetailDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -36465487422228438L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 领药申请ID
     */
    private String orId;
    /**
     * 处方ID
     */
    private String opId;
    /**
     * 处方明细ID
     */
    private String opdId;
    /**
     * 费用ID
     */
    private String costId;
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
     * 剂量
     */
    private BigDecimal dosage;
    /**
     * 剂量单位代码（JLDW）
     */
    private String dosageUnitCode;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 用药性质代码（YYXZ）
     */
    private String useCode;
    /**
     * 开单单位
     */
    private String currUnitCode;

    /**
     * 就诊id
     */
    private String visitId;

    /**
     * 进销存目标名称
     */
    private String invoicingTargetName;

    /**
     * 发药明细汇总id
     */
    private String distributeAllDetailId;
}

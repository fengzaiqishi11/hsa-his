package cn.hsa.module.phar.pharinbackdrug.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.phar.pharinbackdrug.entity
 * @Class_name: PharInDistributeBatchDetailDO
 * @Describe: 药房住院分批发药明细数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/5/20 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharInDistributeAllDetailDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -53129214138237966L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 发药ID
     */
    private String distributeId;
    /**
     * 领药申请ID
     */
    private String irId;
    /**
     * 领药申请明细ID
     */
    private String irdId;
    /**
     * 医嘱ID
     */
    private String adviceId;
    /**
     * 医嘱组号
     */
    private String groupNo;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 项目类型代码（XMLB）
     */
    private String itemCode;
    /**
     * 项目ID（药品/材料）
     */
    private String itemId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 数量
     */
    private BigDecimal num;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 单位代码（DW）
     */
    private String unitCode;
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
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 拆分比
     */
    private BigDecimal splitRatio;
    /**
     * 拆零单位代码（DW）
     */
    private String splitUnitCode;
    /**
     * 拆零数量
     */
    private BigDecimal splitNum;
    /**
     * 拆零单价
     */
    private BigDecimal splitPrice;
    /**
     * 中药付数
     */
    private BigDecimal chineseDrugNum;
    /**
     * 用法代码（YF）
     */
    private String usageCode;
    /**
     * 开单单位
     */
    private String currUnitCode;

    /**
     * 创建人id
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;
    /**
     * 原发药明细id
     */
    private String oldDistId;
}

package cn.hsa.module.phar.pharoutdistribute.entity;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.phar.pharoutdistribute.entity
 * @Class_name: PharOutDistributeBatchDetailDO
 * @Describe: 药房门诊分批发药明细数据映射对象
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/5/20 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutDistributeAllDetailDO extends PageDO implements Serializable {
      private static final long serialVersionUID = -25124412958476727L;
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
       * 处方ID
       */
      private String opId;
      /**
       * 处方明细ID
       */
      private String opdId;
      /**
       * 原费用ID
       */
      private String oldCostId;
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
       * 项目名称
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
       * 当前退药数量
       */
      private BigDecimal backNum;
      /**
       * 累计退药数量
       */
      private BigDecimal totalBackNum;
      /**
       * 原发药明细ID
       */
      private String oldDistId;
      /**
       * 退药状态代码（TYZT）
       */
      private String statusCode;
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
}

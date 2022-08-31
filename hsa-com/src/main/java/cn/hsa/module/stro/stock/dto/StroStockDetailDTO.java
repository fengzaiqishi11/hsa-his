package cn.hsa.module.stro.stock.dto;


import cn.hsa.module.stro.stock.entity.StroStockDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroStockDetailDTO extends StroStockDetailDO implements Serializable {
    private static final long serialVersionUID = -41075628269023745L;

    //是否为零
    private Boolean isMun;
    // 库位码（货架号）
    private String locationNo;
    //规格
    private String spec;
    //拆零单位
    private String splitUnitCode;
    //拆零购进价
    private BigDecimal splitBuyPrice = BigDecimal.valueOf(0);
    //拆零零售单价
    private BigDecimal splitSellPrice = BigDecimal.valueOf(0);
    // 拆零新单价
    private BigDecimal newSplitPrice = BigDecimal.valueOf(0);
    // 新单价
    private BigDecimal newPrice = BigDecimal.valueOf(0);
    //剂型代码
    private String prepCode;
    //单据号
    private String orderNo;
    //创建人
    private String crteId;
    //创建时间
    private String crteName;
    //是否按批号
    private String sfBatchNo;
    //当前单位代码（出入库具体单位）
    private String currUnitCode;
    //关键字
    private String keyword;
    //剂量单位代码
    private String dosageUnitCode;
    //剂量
    private BigDecimal dosage;
    //处方ID
    private String opId;
    //处方明细ID
    private String opdId;
    //费用ID
    private String costId;
    //中药付数
    private BigDecimal chineseDrugNum;
    //用法代码
    private String usageCode;
    //用药性质
    private String useCode;
    //领药申请明细ID
    private String irdId;
    //医嘱ID
    private String adviceId;
    //医嘱组号
    private String groupNo;
    //就诊ID
    private String visitId;
    //婴儿ID
    private String babyId;
    //科室性质
    private String loginDeptType;
    //科室类别
    private String loginTypeIdentity;
    //类型(1:中药,2:中成药,3:中草药,4:西药)
    private String typeIdentity;
    //药品大类
    private String bigTypeCode;
    private String typeCode;
    private List<String> types;
    // 生产企业名称
    private String prodName;
    //项目编码
    private String code;
    //时间戳
    private Long crteTimeStamp;
    //平均购进价
    private BigDecimal avgBuyPrice;
    // 盘点/报损查询的标志
    private String queryFlag;
    //生产厂家编码
    private String prodCode;
    // 批准文号
    private String ndan;
    //材料型号
    private String model;
    // 损益类型
    private String profitLossType;
    // 项目id集合
    private List<String> itemIds;
    // 进销存目标名称
    private String invoicingTargetName;
    // 进销存目标ID
    private String invoicingTargetId;

   // 发药明细汇总id
    private String distributeAllDetailId;

    private List<StroStockDetailDTO> stroStockDetailDTOS;
    /**
     * 判断查询库存明细的时候是否需要加上有效期，默认为空是加上，不为空是不加上
     */
    private String  isExpiryDate;
    // 是否为报损单查询数据 0： 不是 ，1：是
    private String sLossList;
    // 是否过滤科室库存
    private String sfdeptFilter;
    private String nationCode;
    private String nationName;
    /**
     * 总库存数
     */
    private BigDecimal totalNum;
    /**
     * 总库存拆零数量
     */
    private BigDecimal totalSplitNum;
    /**
     * 失效数量
     */
    private BigDecimal expiryNum;

    /**
     * 失效拆零数量
     */
    private BigDecimal expirySplitNum;

    private BigDecimal stock_occupy;

}

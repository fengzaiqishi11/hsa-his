package cn.hsa.module.phar.pharwaitreceive.entity;

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
phar：药房模块缩写，pharmacy
wait：等待
receive：门诊/住院申领表（药品/材(PharWaitReceive)实体类
 *
 * @author makejava
 * @since 2020-07-29 16:36:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharWaitReceiveDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 229634136749469664L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 业务类型代码（YWLX）
    */
    private String bizTypeCode;
    /**
    * 医嘱ID
    */
    private String bizId;
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
    * 规格
    */
    private String spec;
    /**
    * 剂量
    */
    private BigDecimal dosage;
    /**
    * 剂量单位代码
    */
    private String dosageUnitCode;
    /**
    * 数量
    */
    private Integer num;
    /**
     * 退药数量
     */
    private Integer backNum;
    /**
    * 单位代码
    */
    private String unitCode;
    /**
    * 单价
    */
    private BigDecimal price;
    /**
    * 拆分比
    */
    private Integer splitRatio;
    /**
    * 拆零单位代码
    */
    private String splitUnitCode;
    /**
    * 拆零单价
    */
    private BigDecimal splitPrice;
    /**
    * 总金额
    */
    private BigDecimal totalPrice;
    /**
    * 中药付数
    */
    private Integer chineseDrugNum;
    /**
    * 费用明细ID
    */
    private String costId;
    /**
    * 原费用明细ID
    */
    private String oldCostId;
    /**
    * 状态代码（LYZT）
    */
    private String statusCode;
    /**
    * 发药药房ID
    */
    private String distPharId;
    /**
    * 发药人ID
    */
    private String distUserId;
    /**
    * 发药时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distTime;
    /**
    * 用法代码
    */
    private String usageCode;
    /**
    * 用药性质代码（YYXZ）
    */
    private String useCode;
    /**
    * 申请科室ID
    */
    private String applyDeptId;
    /**
     * 发药状态代码
     */
    private String distributeCode;
    /**
    * 创建人ID
    */
    private String crteId;
    /**
    * 创建人姓名
    */
    private String crteName;
    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;



}
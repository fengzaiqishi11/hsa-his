package cn.hsa.module.phar.pharapply.entity;

import cn.hsa.base.PageDO;
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

(StroOut)实体类
 *
 * @author makejava
 * @since 2020-08-19 19:30:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StroOut extends PageDO implements Serializable {
    private static final long serialVersionUID = -62355342220265598L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 出库方式代码（CRFS）
    */
    private String outCode;
    /**
    * 单据号
    */
    private String orderNo;
    /**
    * 出库库位ID
    */
    private String outStockId;
    /**
    * 入库库位ID
    */
    private String inStockId;
    /**
    * 购进总金额
    */
    private BigDecimal  buyPriceAll;
    /**
    * 零售总金额
    */
    private BigDecimal sellPriceAll;
    /**
    * 备注
    */
    private String remark;
    /**
    * 审核状态代码（SHZT）
    */
    private String auditCode;
    /**
    * 审核人ID
    */
    private String auditId;
    /**
    * 审核人姓名
    */
    private String auditName;
    /**
    * 审核时间
    */
    private Date auditTime;
    /**
    * 确认审核状态代码（SHZT）
    */
    private String okAuditCode;
    /**
    * 确认审核人ID
    */
    private String okAuditId;
    /**
    * 确认审核人姓名
    */
    private String okAuditName;
    /**
    * 确认审核时间
    */
    private Date okAuditTime;
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
    private Date crteTime;


}
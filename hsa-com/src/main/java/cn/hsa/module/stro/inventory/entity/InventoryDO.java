package cn.hsa.module.stro.inventory.entity;

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
 * @author ljh
 * @date 2020/07/21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -36541473748702454L;
    /**
     * 主键
     */

    private String id;
    /**
     * 医院编码
     */

    private String hospCode;
    /**
     * 库房ID（药库/药房）
     */

    private String bizId;
    /**
     * 单据号
     */

    private String orderNo;
    /**
     * 盘前总金额
     */

    private BigDecimal beforePrice;
    /**
     * 盘后总金额
     */

    private BigDecimal afterPrice;
    /**
     * 损益总金额
     */

    private BigDecimal incdecPrice;
    /**
     * 备注
     */

    private String remark;
    /**
     * 审核人ID
     */

    private String auditId;
    /**
     * 审核人姓名
     */

    private String auditName;
    /**
     * 审核代码
     */

    private String auditCode;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}

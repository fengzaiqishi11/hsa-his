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
 * 表名含义：
 * phar：药房模块缩写，pharmacy
 * out：门诊
 * distribu(PharOutDistribute)实体类
 *
 * @author makejava
 * @since 2020-08-26 11:07:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharOutDistributeDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 402755114841777914L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 结算ID
     */
    private String settleId;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 发药药房ID
     */
    private String pharId;
    /**
     * 发药窗口ID
     */
    private String windowId;
    /**
     * 单据号
     */
    private String orderNo;
    /**
     * 配药人ID
     */
    private String assignUserId;
    /**
     * 配药人姓名
     */
    private String assignUserName;
    /**
     * 配药时间
     */
    private Date assignTime;
    /**
     * 发药人ID
     */
    private String distUserId;
    /**
     * 发药人姓名
     */
    private String distUserName;
    /**
     * 发药时间
     */
    private Date distTime;
    /**
     * 申请科室ID
     */
    private String deptId;
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
    /**
     * 冲红状态
     */
    private String statusCode;

}
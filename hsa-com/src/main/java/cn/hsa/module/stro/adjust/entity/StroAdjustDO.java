package cn.hsa.module.stro.adjust.entity;

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
 *@Package_name: cn.hsa.module.stro.adjust.entity
 *@Class_name: StroAdjustDo
 *@Describe: 调价单据
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/7/31 17:26
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StroAdjustDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 572798954788087714L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 库房ID（药库/药房）（表base_dept）
     */
    private String bizId;
    /**
     * 单据号（表base_order_rule）
     */
    private String orderNo;
    /**
     * 调前总金额
     */
    private BigDecimal beforePrice;
    /**
     * 调后总金额
     */
    private BigDecimal afterPrice;
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
     * 审核代码（SHDM）
     */
    private String auditCode;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    private Date crteTime;
    /**
     * 调价来源方式
     */
    private String adjustCode;
    /**
     * 来源ID
     */
    private String sourceId;
}
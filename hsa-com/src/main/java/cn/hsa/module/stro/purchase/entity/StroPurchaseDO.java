package cn.hsa.module.stro.purchase.entity;

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
 * @Package_name: cn.hsa.module.stro.purchase.entity
 * @Class_name: StroPurchaseDO
 * @Describe: 药品采购 Model对象
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class StroPurchaseDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 890629419097358533L;
    //主键
    private String id;
    //医院编码
    private String hospCode;
    //库房ID（药库/药房）
    private String bizId;
    //单据号
    private String orderNo;
    //供应商ID
    private String supplierId;
    //购进总金额
    private BigDecimal buyPriceAll;
    //零售总金额
    private BigDecimal sellPriceAll;
    //审核代码
    private String auditCode;
    //审核人ID
    private String auditId;
    //审核人姓名
    private String auditName;
    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    //创建人ID
    private String crteId;
    //创建人姓名
    private String crteName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;
}
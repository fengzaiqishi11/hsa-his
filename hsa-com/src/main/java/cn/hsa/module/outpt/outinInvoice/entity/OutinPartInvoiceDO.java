package cn.hsa.module.outpt.outinInvoice.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.invoice.entity
 * @Class_name: OutinPartInvoiceDO
 * @Describe:  发票分单实体类
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/16 15:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutinPartInvoiceDO extends PageDO implements java.io.Serializable {

    private static final long serialVersionUID = 8514868709667273145L;
    private String invoiceNo;
    private String id;
    private BigDecimal invoiceTotalPrice;
    private BigDecimal realityPrice;
    private String bfcCode;
    private String bfcName;
    private String crteName;
    /** 创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date crteTime;

    // 发票领用人
    private String receiveName;
    // 医保个人账户支付金额
    private BigDecimal personalPrice;
    // 统筹支付金额
    private BigDecimal miPrice;
}

package cn.hsa.module.outpt.daily.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (OutinDailyInvoice)实体类
 *
 * @author zhongming
 * @since 2020-09-24 14:25:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutinDailyInvoiceDO implements Serializable {
    private static final long serialVersionUID = -22547750272258151L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 日结ID
    */
    private String dailyId;
    /**
     * 日结单号
     */
    private String dailyNo;
    /**
    * 缴款类型代码（JKLX）
    */
    private String typeCode;
    /**
    * 发票张数
    */
    private Integer num;
    /**
    * 发票起始号码
    */
    private String startNo;
    /**
    * 发票结束号码
    */
    private String endNo;
    /**
    * 发票费用合计
    */
    private BigDecimal totalPrice;
}
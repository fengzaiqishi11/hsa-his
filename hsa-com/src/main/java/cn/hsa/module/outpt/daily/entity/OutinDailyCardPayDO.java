package cn.hsa.module.outpt.daily.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (OutinDailyCardPayDO)实体类
 *
 * @author zhongming
 * @since 2020-09-24 14:25:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutinDailyCardPayDO implements Serializable {
    private static final long serialVersionUID = 956573493555723435L;
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
    * 支付方式代码（ZFFS）
    */
    private String payCode;
    /**
    * 支付总金额
    */
    private BigDecimal totalPrice;
}
package cn.hsa.module.outpt.daily.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (OutinDailyFinclassify)实体类
 *
 * @author zhongming
 * @since 2020-09-24 14:25:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutinDailyFinclassifyDO implements Serializable {
    private static final long serialVersionUID = 941805487400904068L;
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
    * 财务分类ID
    */
    private String bfcId;
    /**
    * 总费用合计
    */
    private BigDecimal totalPrice;
    /**
    * 优惠总金额
    */
    private BigDecimal preferentialTotalPrice;
    /**
    * 优惠后总金额
    */
    private BigDecimal realityTotalPrice;
}
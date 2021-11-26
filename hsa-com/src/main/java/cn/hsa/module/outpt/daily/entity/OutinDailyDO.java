package cn.hsa.module.outpt.daily.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.daily.entity
 * @Class_name: OutinDaliyDO
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 11:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutinDailyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -64473821911858039L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 日结单号
     */
    private String dailyNo;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 缴款类型代码（JKLX）
     */
    private String typeCode;
    /**
     * 总费用合计
     */
    private BigDecimal totalPrice;
    /**
     * 合同单位总金额
     */
    private BigDecimal insureTotalPrice;
    /**
     * 预交金实收总金额
     */
    private BigDecimal yjssTotalPrice;
    /**
     * 预交金退款总金额
     */
    private BigDecimal yjtkTotalPrice;
    /**
     * 预交金结算总金额
     */
    private BigDecimal yjjsTotalPrice;
    /**
     * 预交金冲抵总金额
     */
    private BigDecimal yjcdTotalPrice;
    /**
     * 退款总金额（减）
     */
    private BigDecimal backTotalPrice;
    /**
     * 优惠总金额（减）
     */
    private BigDecimal preferentialTotalPrice;
    /**
     * 舍入总金额（减）
     */
    private BigDecimal roundTotalPrice;
    /**
     * 实缴总金额
     */
    private BigDecimal realityTotalPrice;
    /**
     * 是否缴款确认（SF）
     */
    private String isOk;
    /**
     * 确认缴款人ID
     */
    private String okId;
    /**
     * 确认缴款人姓名
     */
    private String okName;
    /**
     * 确认缴款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date okTime;
    /**
     * 创建人/缴款人ID
     */
    private String crteId;
    /**
     * 创建人/缴款人姓名
     */
    private String crteName;
    /**
     * 创建/缴款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    // 一卡通支付金额
    private BigDecimal cardTotalPrice;

    // 一卡通充值总金额
    private BigDecimal yktczTotalPrice;

    // 一卡通退款总金额
    private BigDecimal ykttkTotalPrice;

    // 挂账总金额
    private BigDecimal creditTotalPrice;

}

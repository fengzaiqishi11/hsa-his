package cn.hsa.module.outpt.outinInvoice.dto;

import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpo.register.dto
 * @class_name: OutptRegisterDTO
 * @Description:
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/24 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutinInvoiceDTO extends OutinInvoiceDO implements Serializable {

	private static final long serialVersionUID = 3785314863351737839L;
	/**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endTime;

    /**
     * 作废/退领开始号码
     */
    private String invalidStartNo;

    /**
     * 作废/退领结束号码
     */
    private String invalidEndNo;

    /**
     * 票据类型
     */
    private String  typeCode;

    /**
     * 查询条件
     */
    private String keyword;

    /**
     * 结算单号
     */
    private String settleNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 发票id
     */
    private String invoiceId;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 结算类型代码
     */
    private String settleType;

    /**
     * 发票总金额
     */
    private BigDecimal invoiceTotalPrice;

    /**
     * 结算总金额
     */
    private BigDecimal settleTotalPrice;

    /**
     * 结算时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date settleTime;

	/**
     * 优惠后金额
     */
    private BigDecimal realityPrice;

    /**
     * 舍入金额
     */
    private BigDecimal truncPrice;

    /**
     * 实收金额
     */
    private BigDecimal actualPrice;

    /**
     * 个人支付金额
     */
    private BigDecimal selfPrice;

    /**
     * 统筹支付金额
     */
    private BigDecimal miPrice;

    // 结算时费用合计后的优惠后总金额
    private BigDecimal settleRealityPrice;

    /**
     * 支付订单号
     */
    private String orderNo;

    /**
     * 支付来源
     */
    private String sourcePayCode;

    /**
     * 是否打印
     */
    private String isPrint;

    /**
     * 是否打印清单
     */
    private String isPrintList;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 挂号id
     */
    private String registerId;

    /**
     * 挂号单号
     */
    private String registerNo;

    /**
     * 打印类型
     */
    private String printType;

    private List<String> typeCodeList;

    /**
     * 结算ID
     */
    private String settleId;

    /**
     * FPHB_FS
     */
    private String type;

    private String visitId;

    private String invoiceDetailId;

    private String visitNo;

    private String systemCode;  // 当前登录子系统编码

    private String outptSettleInvoiceId; // 发票补打重打

	private String genderCode;
	private String age;
	private String ageUnitCode;

	// 个人账户支付
	private BigDecimal personalPrice;

    /**
     * 就诊日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String visitTime;

    //就诊科别
    private String deptName;

    // 就诊类别  JZLB
    private String visitCode;

    // 个人编号
    private String personalNO;

    // 待遇类型名称
    private String bka006Name;

    // 传入当前使用票号
    private String dqCurrNo;
    // 收费员id
    private String chargeId;

    // 余额
    private String balanceSettle;

    // 国家编码
    private String nationCode;
    // 国家名称
    private String nationName;

    /**
     * 费用发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date costTime;
}

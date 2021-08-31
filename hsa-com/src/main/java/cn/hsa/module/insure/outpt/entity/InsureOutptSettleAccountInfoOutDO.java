package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptSettleAccountInfoOut
 * @Describe: 门诊预结算 结算信息输出
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 15:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleAccountInfoOutDO implements Serializable {
	private static final long serialVersionUID = -816880993380626257L;

	// 就诊ID
	private String mdtrtId;

	// 人员编号
	private String psnNo;

	// 人员姓名
	private String psnName;

	// 人员证件类型
	private String psnCertType;

	// 证件号码
	private String certno;

	// 性别
	private String gend;

	// 民族
	private String naty;

	// 出生日期
	private Date brdy;

	// 年龄
	private BigDecimal age;

	// 险种类型
	private String insutype;

	// 人员类别
	private String psnType;

	// 公务员标志
	private String cvlservFlag;

	// 结算时间
	private Date setlTime;

	// 就诊凭证类型
	private String mdtrtCertType;

	// 医疗类别
	private String medType;

	// 医疗费总额
	private BigDecimal medfeeSumamt;

	// 全自费金额
	private BigDecimal fulamtOwnpayAmt;

	// 超限价自费费用
	private BigDecimal overlmtSelfpay;

	// 先行自付金额
	private BigDecimal preselfpayAmt;

	// 符合政策范围金额
	private BigDecimal inscpScpAmt;

	// 实际支付起付线
	private BigDecimal actPayDedc;

	// 基本医疗保险统筹基金支出
	private BigDecimal hifpPay;

	// 基本医疗保险统筹基金支付比例
	private BigDecimal poolPropSelfpay;

	// 公务员医疗补助资金支出
	private BigDecimal cvlservPay;

	// 企业补充医疗保险基金支出
	private BigDecimal hifesPay;

	// 居民大病保险资金支出
	private BigDecimal hifmiPay;

	// 职工大额医疗费用补助基金支出
	private BigDecimal hifobPay;

	// 医疗救助基金支出
	private BigDecimal mafPay;

	// 其他支出
	private BigDecimal othPay;

	// 基金支付总额
	private BigDecimal fundPaySumamt;

	// 个人负担总金额
	private BigDecimal psnPartAmt;

	// 个人账户支出
	private BigDecimal acctPay;

	// 个人现金支出
	private BigDecimal psnCashPay;

	// 医院负担金额
	private BigDecimal hospPartAmt;

	// 余额
	private BigDecimal balc;

	// 个人账户共济支付金额
	private BigDecimal acctMulaidPay;

	// 医药机构结算ID
	private String medinsSetlId;

	// 清算经办机构
	private String clrOptins;

	// 清算方式
	private String clrWay;

	// 清算类别
	private String clrType;

	// 备注
	private String memo;
}

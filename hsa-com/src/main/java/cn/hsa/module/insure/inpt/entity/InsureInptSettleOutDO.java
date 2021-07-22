package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptSettleOutDO
 * @Description: 住院结算：住院结算反参（第一部分）
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 16:55
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptSettleOutDO {
    private String	mdtrtId	; //	就诊ID
    private String	setlId	; //	结算ID
    private String	psnNo	; //	人员编号
    private String	psnName	; //	人员姓名
    private String	psnCertType	; //	人员证件类型
    private String	certno	; //	证件号码
    private String	gend	; //	性别
    private String	naty	; //	民族
    private Date brdy	; //	出生日期
    private Integer	age	; //	年龄
    private String	insutype	; //	险种类型
    private String	psnType	; //	人员类别
    private String	cvlservFlag	; //	公务员标志
    private Date	setlTime	; //	结算时间
    private String	mdtrtCertType	; //	就诊凭证类型
    private String	medType	; //	医疗类别
    private BigDecimal medfeeSumamt	; //	医疗费总额
    private BigDecimal	fulamtOwnpayAmt	; //	全自费金额
    private BigDecimal	overlmtSelfpay	; //	超限价自费费用
    private BigDecimal	preselfpayAmt	; //	先行自付金额
    private BigDecimal	inscpScpAmt	; //	符合政策范围金额
    private BigDecimal	actPayDedc	; //	实际支付起付线
    private BigDecimal	hifpPay	; //	基本医疗保险统筹基金支出
    private BigDecimal	poolPropSelfpay	; //	基本医疗保险统筹基金支付比例
    private BigDecimal	cvlservPay	; //	公务员医疗补助资金支出
    private BigDecimal	hifesPay	; //	企业补充医疗保险基金支出
    private BigDecimal	hifmiPay	; //	居民大病保险资金支出
    private BigDecimal	hifobPay	; //	职工大额医疗费用补助基金支出
    private BigDecimal	mafPay	; //	医疗救助基金支出
    private BigDecimal	hospPartAmt	; //	医院负担金额
    private BigDecimal	othPay	; //	其他支出
    private BigDecimal	fundPaySumamt	; //	基金支付总额
    private BigDecimal	psnPartAmt	; //	个人负担总金额
    private BigDecimal	acctPay	; //	个人账户支出
    private BigDecimal	psnCashPay	; //	个人现金支出
    private BigDecimal	balc	; //	余额
    private BigDecimal	acctMulaidPay	; //	个人账户共济支付金额
    private String	medinsSetlId	; //	医药机构结算ID
    private String	clrOptins	; //	清算经办机构
    private String	clrWay	; //	清算方式
    private String	clrType	; //	清算类别
    private String	psnSetlway	; //	个人结算方式
    private String	BigDecimal	; //	医保认可费用总额

}

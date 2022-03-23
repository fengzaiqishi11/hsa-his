package cn.hsa.report.business.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName SettleInfoResDTO
 * @Deacription 结算信息查询响应dto层
 * @Author liuzhuoting
 * @Date 2021-08-16
 * @Version 1.0
 **/
@Data
public class SettleInfoResDTO implements Serializable {

    /**
     * 结算ID
     */
    private String setlId;

    /**
     * 就诊ID
     */
    private String mdtrtId;

    /**
     * 人员编号
     */
    private String psnNo;

    /**
     * 人员姓名
     */
    private String psnName;

    /**
     * 人员证件类型
     */
    private String psnCertType;

    /**
     * 证件号码
     */
    private String certno;

    /**
     * 性别
     */
    private String gend;

    /**
     * 民族
     */
    private String naty;

    /**
     * 出生日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date brdy;

    /**
     * 年龄
     */
    private BigDecimal age;

    /**
     * 险种类型
     */
    private String insutype;

    /**
     * 人员类别
     */
    private String psnType;

    /**
     * 公务员标志
     */
    private String cvlservFlag;

    /**
     * 灵活就业标志
     */
    private String flxempeFlag;

    /**
     * 新生儿标志
     */
    private String nwbFlag;

    /**
     * 参保机构医保区划
     */
    private String insuOptins;

    /**
     * 单位名称
     */
    private String empName;

    /**
     * 支付地点类别
     */
    private String payLoc;

    /**
     * 定点医药机构编号
     */
    private String fixmedinsCode;

    /**
     * 定点医药机构名称
     */
    private String fixmedinsName;

    /**
     * 医院等级
     */
    private String hospLv;

    /**
     * 定点归属机构
     */
    private String fixmedinsPoolarea;

    /**
     * 限价医院等级
     */
    private String lmtpricHospLv;

    /**
     * 起付线医院等级
     */
    private String dedcHospLv;

    /**
     * 开始日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date begndate;

    /**
     * 结束日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date enddate;

    /**
     * 结算时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date setlTime;

    /**
     * 就诊凭证类型
     */
    private String mdtrtCertType;

    /**
     * 医疗类别
     */
    private String medType;

    /**
     * 清算类别
     */
    private String clrType;

    /**
     * 清算方式
     */
    private String clrWay;

    /**
     * 清算经办机构
     */
    private String clrOptins;

    /**
     * 医疗费总额
     */
    private BigDecimal medfeeSumamt;

    /**
     * 全自费金额
     */
    private BigDecimal fulamtOwnpayAmt;

    /**
     * 超限价自费费用
     */
    private BigDecimal overlmtSelfpay;

    /**
     * 先行自付金额
     */
    private BigDecimal preselfpayAmt;

    /**
     * 符合政策范围金额
     */
    private BigDecimal inscpScpAmt;

    /**
     * 实际支付起付线
     */
    private BigDecimal actPayDedc;

    /**
     * 基本医疗保险统筹基金支出
     */
    private BigDecimal hifpPay;

    /**
     * 基本医疗保险统筹基金支付比例
     */
    private BigDecimal poolPropSelfpay;

    /**
     * 公务员医疗补助资金支出
     */
    private BigDecimal cvlservPay;

    /**
     * 企业补充医疗保险基金支出
     */
    private BigDecimal hifesPay;

    /**
     * 居民大病保险资金支出
     */
    private BigDecimal hifmiPay;

    /**
     * 职工大额医疗费用补助基金支出
     */
    private BigDecimal hifobPay;

    /**
     * 医疗救助基金支出
     */
    private BigDecimal mafPay;

    /**
     * 其他支出
     */
    private BigDecimal othPay;

    /**
     * 基金支付总额
     */
    private BigDecimal fundPaySumamt;

    /**
     * 个人支付金额
     */
    private BigDecimal psnPay;

    /**
     * 个人账户支出
     */
    private BigDecimal acctPay;

    /**
     * 现金支付金额
     */
    private BigDecimal cashPayamt;

    /**
     * 余额
     */
    private BigDecimal balc;

    /**
     * 个人账户共济支付金额
     */
    private BigDecimal acctMulaidPay;

    /**
     * 医药机构结算ID
     */
    private String medinsSetlId;

    /**
     * 退费结算标志
     */
    private String refdSetlFlag;

    /**
     * 年度
     */
    private String year;

    /**
     * 诊断代码
     */
    private String diseCodg;

    /**
     * 诊断名称
     */
    private String diagName;

    /**
     * 发票号
     */
    private String invono;

    /**
     * 经办人ID
     */
    private String opterId;

    /**
     * 经办人姓名
     */
    private String opterName;

    /**
     * 经办时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date optTime;


}

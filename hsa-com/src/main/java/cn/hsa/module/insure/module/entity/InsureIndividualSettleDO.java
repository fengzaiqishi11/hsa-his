package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
* @Package_name: 
* @Class_name: DO
* @Describe: 表含义： insure：医保 Individual：就诊 settle：结算                                              -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualSettleDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 3836953671759402601L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //结算id
        private String settleId;
        //是否住院（SF）
        private String isHospital;
        //就诊登记号
        private String visitNo;
        //出院疾病诊断编码
        private String dischargeDnCode;
        //医保机构编码
        private String insureOrgCode;
        //医保注册编码
        private String insureRegCode;
        //医疗机构编码
        private String medicineOrgCode;
        //出院疾病诊断名称
        private String dischargeDnName;
        //出院日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date dischargedDate;
        //出院情况
        private String dischargedCase;
        //结算方式,01 普通结算,02 包干结算
        private String settleway;
        //总费用
        private BigDecimal totalPrice;
        //医保支付
        private BigDecimal insurePrice;
        //统筹基金支付
        private BigDecimal planPrice;
        //大病互助支付
        private BigDecimal seriousPrice;
        //公务员补助支付
        private BigDecimal civilPrice;
        //离休基金支付
        private BigDecimal retirePrice;
        //个人账户支付
        private BigDecimal personalPrice;
        //个人支付
        private BigDecimal personPrice;
        //医院支付
        private BigDecimal hospPrice;
        //结算前账户余额
        private BigDecimal beforeSettle;
        //结算后账户余额
        private BigDecimal lastSettle;
        //其他支付
        private BigDecimal restsPrice;
        //指定账户支付金额
        private BigDecimal assignPrice;
        //起付线金额
        private BigDecimal startingPrice;
        //超封顶线金额
        private BigDecimal topPrice;
        //统筹段自负金额
        private BigDecimal planAccountPrice;
        //部分自付金额
        private BigDecimal portionPrice;
        //状态标志,0正常，2冲红，1，被冲红
        private String state;
        //医保结算状态;0试算，1结算
        private String settleState;
        //费用批次
        private String costbatch;
        //业务类型
        private String aka130;
        //待遇类型
        private String bka006;
        //业务申请号,门诊特病，工伤，生育
        private String injuryBorthSn;
        //当前结算是否使用个人账户;0是，1否
        private String isAccount;
        //备注
        private String remark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        private String insureSettleId; // 医保结算返回的结算id
        private String medicalRegNo;  //入院登记唯一返回的就诊登记号
        //自定义字段4
        private String ext03;
        //自定义字段5
        private String ext04;
        //自定义字段6
        private String ext05;
        //自定义字段3
        private String ext06;
        //自定义字段7
        private String ext07;
        //自定义字段8
        private String ext08;
        //自定义字段9
        private String ext09;
        //自定义字段10
        private String ext10;

        private String deptName; // 科室名称

        private String medType; //医疗类型
        // 交易信息(原交易)中的msgid,发送方报文ID
        private String omsgid;
        // 交易信息(原交易)中的infno
        private String oinfno;
        private String clrWay;
        private String clrType;
        private String clrOptins;
        private BigDecimal mafPay; // 医疗救助金额
        private BigDecimal hospExemAmount; //医院减免金额（一站式紧急基金）

        private BigDecimal comPay; // 企业支付
        private BigDecimal allPortionPrice;// 全自费金额
        private BigDecimal fertilityPay;// 生育基金
        private BigDecimal overSelfPrice; // 超限价
        private BigDecimal preselfpayAmt; // 先行自付金额
        private BigDecimal inscpScpAmt; // 符合政策范围金额
        private BigDecimal poolPropSelfpay;// 本医疗保险统筹基金支付比例
        private BigDecimal acctMulaidPay;// 个人账户共计支付金额
        private BigDecimal acctInjPay;// 职工意外伤害基金
        private BigDecimal retAcctInjPay;// 居民意外伤害基金
        private BigDecimal governmentPay;// 政府兜底基金
        private BigDecimal thbPay;// 特惠保补偿金
        private BigDecimal carePay;// 优抚对象医疗补助基金
        private BigDecimal lowInPay;// 农村低收入人口医疗补充保险
        private BigDecimal othPay;// 其他基金
        private BigDecimal retiredPay;// 离休人员医疗保障基金
        private BigDecimal soldierPay;// 一至六级残疾军人医疗补助基金
        private BigDecimal retiredOutptPay;// 离休老工人门慢保障基金
        private BigDecimal injuryPay;// 工伤保险基金
        private BigDecimal hallPay;// 厅级干部补助基金
        private BigDecimal soldierToPay;// 军转干部医疗补助基金
        private BigDecimal welfarePay;// 公益补充保险基金
        private BigDecimal COVIDPay;// 新冠肺炎核酸检测财政补助
        private BigDecimal familyPay;// 居民家庭账户金
        private BigDecimal behalfPay;// 代缴基金（破产改制）
        private BigDecimal psnPartAmt; // 个人负担总金额
        private String isCancel;
        //是否是移动支付结算
        //private String isMobilePayment;
}
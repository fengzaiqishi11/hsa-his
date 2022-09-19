package cn.hsa.module.outpt.fees.entity;

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
* @Package_name: cn.hsa.module.outpt.fees.entity
* @Class_name: OutptSettleDO
* @Describe: 门诊结算Model； 舍入方式： 0、不处理 1、按角4舍5入：1.15块=1.2块 2、按角舍 ：1.1
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptSettleDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 1876950112602064498L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊ID
        private String visitId;
        //结算单号
        private String settleNo;
        //病人类型代码（BRLX）
        private String patientCode;
        //结算时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date settleTime;
        //项目金额
        private BigDecimal totalPrice;
        //优惠后总金额
        private BigDecimal realityPrice;
        //舍入金额（存在正负金额）
        private BigDecimal truncPrice;
        //实收金额
        private BigDecimal actualPrice;
        //个人支付金额
        private BigDecimal selfPrice;
        //统筹支付金额
        private BigDecimal miPrice;
        //是否结算（SF）
        private String isSettle;
        //日结缴款ID
        private String dailySettleId;
        //状态标志代码（ZTBZ）
        private String statusCode;
        //冲红ID
        private String redId;
        //是否打印（SF）
        private String isPrint;
        //原结算ID
        private String oldSettleId;
        //是否打印清单（SF）
        private String isPrintList;
        //清单打印时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date printListTime;
        //支付来源代码（ZFLY，第三方对接）
        private String sourcePayCode;
        //支付订单号（第三方订单号）
        private String orderNo;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;
        // 第一次结算id
        private String oneSettleId;

        // 一卡通支付金额
        private BigDecimal cardPrice;
        // 个人账户支付
        private BigDecimal acctPay ;

        // 挂账金额
        private BigDecimal creditPrice;
        private String creditIsPay; // 是否补缴
        //补缴时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date backPayTime;
        // 补缴人id
        private String backPayId;
        // 补缴人姓名
        private String backPayName;
        // 是否在线支付（SF）0: 线下支付 1：线上支付
        private String onlinePay;

}
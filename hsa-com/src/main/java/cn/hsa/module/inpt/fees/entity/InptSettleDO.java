package cn.hsa.module.inpt.fees.entity;

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
* @Package_name: cn.hsa.module.inpt.fees.entity
* @Class_name: InptSettleDO
* @Describe: 住院结算model；结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算 出院结算方式代码：0、正常结算，1
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptSettleDO extends PageDO implements Serializable{
        //序列号
        private static final long serialVersionUID = 5905204405117152464L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊ID
        private String visitId;
        //婴儿ID
        private String babyId;
        //结算单号
        private String settleNo;
        //结算类型代码（JSLX）
        private String typeCode;
        //病人类型代码（BRLX）
        private String patientCode;
        //开始日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;
        //结束日期
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;
        //总金额
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
        //预交金合计
        private BigDecimal apTotalPrice;
        //预交金冲抵
        private BigDecimal apOffsetPrice;
        //结算补收
        private BigDecimal settleTakePrice;
        //结算退款
        private BigDecimal settleBackPrice;
        //结算退款支付方式代码（ZFFS）
        private String settleBackCode;
        //是否结算（SF）
        private String isSettle;
        //结算时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date settleTime;
        //状态标志代码（ZTBZ）
        private String statusCode;
        //冲红ID
        private String redId;
        //是否打印（SF）
        private String isPrint;
        //医院垫付金额
        private BigDecimal hospDfPrice;
        //医院减免金额
        private BigDecimal hospJmPrice;
        //出院结算方式代码（CYJSFS）
        private String outSettleCode;
        //日结缴款ID
        private String dailySettleId;
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
        //病人状态
        private String brztStatusCode;
        private BigDecimal acctPay;
}
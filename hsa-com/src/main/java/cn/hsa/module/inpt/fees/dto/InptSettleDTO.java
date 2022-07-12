package cn.hsa.module.inpt.fees.dto;

import cn.hsa.module.inpt.fees.entity.InptSettleDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Package_name: cn.hsa.module.inpt.fees.dto
* @Class_name: InptSettleDTO
* @Describe: 住院结算TDO； 结算类型代码：0：正常结算 1：中途结算 2：新生儿结算 3：其它结算 出院结算方式代码：0、正常结算，1、
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptSettleDTO extends InptSettleDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 5905204405117152464L;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date startDate; //开始时间

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date endDate; //结束时间

        //查询条件
        private String keyword;

        //发票号码
        private String invoiceNo;

        //结算状态
        private String settleCode;

        //优惠后金额
        private BigDecimal preferentialPrice;

        //单据优惠
        private BigDecimal costRealityPrice;

        //发票金额
        private BigDecimal invoiceTotalPrice;
        
        //名字
        private String name;

        //住院号
        private String inNo;
        //开始时间
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String startTimeYMD;
        //结束时间
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String endTimeYMD;
        //年龄
        private String age;
        //年龄单位
        private String ageUnitCode;
        //性别代码
        private String genderCode;
        //结算发票id
        private String settleInvoiceId;
        //医保报销总金额
        private BigDecimal insurePayTotalPrice;

        /**
         * 现金
         */
        private BigDecimal xjzf = BigDecimal.valueOf(0);
        /**
         * 刷卡
         */
        private BigDecimal skzf = BigDecimal.valueOf(0);
        /**
         * 微信
         */
        private BigDecimal wxzf = BigDecimal.valueOf(0);
        /**
         * 支付宝
         */
        private BigDecimal zfbzf = BigDecimal.valueOf(0);
        /**
         * 转帐
         */
        private BigDecimal zzzf = BigDecimal.valueOf(0);
        /**
         * 支票
         */
        private BigDecimal zpzf = BigDecimal.valueOf(0);
        //病人类型
        private String patientCode;
        //患者信息 姓名/性别/年龄
        private String patientInfo;

        /**
         * 医院支付
         */
        private BigDecimal hospPrice = BigDecimal.valueOf(0);

        private String queryBaby;

        private String babyName;
        /**
         * 门诊医生
         * */
        private String outptDoctorName;
        /**
         * 经治医生
         */
        private String jzDoctorName;
        /**
         * 主管医生
         */
        private String zgDoctorName;

        // 医院减免金额
        private BigDecimal hospExemAmount;
        private String ywlx;
        /**
         * 出院时间
         */
        private String outTime;

        /**
         * 身份证号码
         */
        private String certNo;
        //补退
        private BigDecimal backTake;
        //个账余额
        private BigDecimal lastSettle;
}
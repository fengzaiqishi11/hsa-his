package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @Package_name: cn.hsa.module.outpt.fees.dto
* @Class_name: OutptSettleDTO
* @Describe:  门诊结算ModelDTO; 舍入方式： 0、不处理 1、按角4舍5入：1.15块=1.2块 2、按角舍 ：1.1
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptSettleDTO extends OutptSettleDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 1876950112602064498L;

        /**
         * 患者姓名/ji结算单号
         */
        private String keyword;
        /**
         * 收费开始日期
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private String startTime;
        /**
         * 收费结束日期
         */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private String endTime;
        /**
         * 收费员id
         */
        private String chargeId;
        /**
         * 患者姓名
         */
        private String name;
        /**
         * 性别
         */
        private String genderCode;
        /**
         * 年龄
         */
        private Integer age;
        /**
         * 年龄单位
         */
        private String ageUnitCode;
        /**
         * 就诊科室名称
         */
        private String deptName;
        /**
         * 就诊医生名称
         */
        private String doctorName;
        /**
         * 发票号码
         */
        private String invoiceNo;
        /**
         * 发票金额
         */
        private BigDecimal invoicePrice;

        //是否打印发票
        private Boolean isInvoice;

        /**
         * 医生ID
         */
        private String doctorId;

        /**
         * 科室ID
         */
        private String deptId;

        /**
         * 优惠类别名称
         */
        private String preferential_type_name;

        /**
         * 诊断名称
         */
        private String disease_name;

        /**
         * 身份证号
         */
        private String certNo;

        /**
         * 诊断信息
         */
        private String diseaseName;

        /**
         * 优惠类型名称
         */
        private String preferentialTypeName;

        /**
         * 就诊号
         */
        private String visitNo;
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

        private String status; // 门诊退费申请状态

        private String personInfo;  //个人信息

        /**
         * 病人类型（BRLX）
         */
        private String patientCode;
        /**
         * 结算id
         */
        private String settleId;
        /**
         * 处方id
         */
        private String opId;

        // 一卡通账号
        private String cardNo;

        // 档案id
        private String profileId;

        // 优惠类别名称
        private String preferentialName;

        // 患者是否为体检患者
        private String isPhys;
        // 退费费用id
        private List<String> costIdList;

}

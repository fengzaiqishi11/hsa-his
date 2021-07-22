package cn.hsa.module.insure.module.entity;

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
* @Describe: 表含义： insure：医保 Individual：个人 fund：基金                                             -&#&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualStatsDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -2049602729228008772L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊id
        private String visitId;
        //个人基本信息id
        private String mibId;
        //本年业务总次数
        private Integer bizYear;
        //本年购药次数
        private Integer drugYear;
        //本年门诊次数
        private Integer diagYear;
        //本年住院次数
        private Integer inhospYear;
        //本年门诊特殊病次数
        private Integer specialYear;
        //本年总费用
        private BigDecimal feeYear;
        //本年统筹基金累计支出
        private BigDecimal fundYear;
        //本年个人帐户累计支出
        private BigDecimal acctYear;
        //本年大病互助金累计支出
        private BigDecimal additionalYear;
        //本年离休基金累计支出
        private BigDecimal retireYear;
        //本年公务员补助累计支出
        private BigDecimal officialYear;
        //本年住院起付线支出
        private BigDecimal qfxYear;
        //本年申报费用累计
        private BigDecimal declareYear;
        //本年个人自付
        private BigDecimal grzfYear;
        //本年居民意外伤害基金
        private BigDecimal jmywYear;
        //本年企业补充支付
        private BigDecimal corpAddYear;
        //当前月份门诊公务员支付累积
        private BigDecimal monthDiagYear;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

}
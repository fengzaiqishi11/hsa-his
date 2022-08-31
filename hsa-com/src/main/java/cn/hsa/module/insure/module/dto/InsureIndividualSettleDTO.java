package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 Individual：就诊 settle：结算                                              -&
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualSettleDTO extends InsureIndividualSettleDO implements Serializable {
        private String keyWord;//医保结算查询关键字
        private String businessType; // 业务名称
        private String diseaseName; //疾病名称
        private String wageType; //待遇类型名称
        private String insureOrgCode; // 医保机构编码
        private String aac003;  // 医保个人基本信息表：姓名
        private String aac002;   // 医保个人基本信息表：公民身份号码
        private String aac004;  // 医保个人基本信息表：性别
        private String aae005; // 医保个人基本信息表：联系电话
        private String mibId;
        private String bka006Name; // 待遇类型名称
        private String visitIcdCode; // 就诊疾病编码
        private String visitIcdName; //就诊疾病名称
        private String aac001; // 个人电脑号
        private String aka130Name; // 业务类型名称
        private String patientCode; // 病人类型
        private BigDecimal akc252; // 医保个人账户余额
        private String name; // 姓名
        private String mdtrtCertNo;
        private String remoteMsgId;
        @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date time;
        private String visitDrptName;
        private String isZC;//是否正常结算单
        private String isUpload;// 上传标志
        //统计时间段开始时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        //统计时间段结束时间
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        //是否住院（SF）
        private String isHospital;
        //入院时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date inTime;
        //出院时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date outTime;
        private String bka035; // 人员类别编码
        private String bka035Name; // 人员类别名称
        private BigDecimal psnPartAmt ; // 个人负担总金额
        private BigDecimal hifdmPay;  // 伤残人员医疗保障基金（珠海医保新增回参字段）
        private BigDecimal totalAdvance; // 预交金
        private BigDecimal settleTakePrice; // 结算补收
        private BigDecimal settleBackPrice; // 结算退款
        private BigDecimal insuplcAdmdvs; // 参保地区划
}
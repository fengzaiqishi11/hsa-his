package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.Date;

/**
* @Package_name: 
* @Class_name: DTO
* @Describe: 表含义： insure：医保 Individual：个人 visit：就诊                                             -&#
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualVisitDTO extends InsureIndividualVisitDO implements Serializable {
        private static final long serialVersionUID = -4344033250932437962L;
        // 医保机构编码
        private String insureRegCode;
        private String itemName; // 费用明细项目名称
        /**
         * 医保机构编码
         */
        private String insureOrgCode;
        private String adviceDoctorName ; // 开嘱医生姓名
        private String adviceId; // 医嘱号
        private String code; // 开嘱医生工号
        private BigDecimal bkr264; // 总费用
        private String type ; // 1 住院号  2:参保人电脑号 3:参保人的姓名 4: 参保人的公民身份号码 5:参保人的 IC 卡号
        private String aac003; // 姓名
        private String aac002; // 身份证
        private String aac004; // 性别
        private String aaz500; // IC卡号
        private String aaa027; //分级统筹中心编码
        private String keyWord ;// 查询关键字
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate; // 开始时间
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate ; // 结束时间
        private String patientCode; // 病人类型
        private String isAdviceEntry ;// 是否医嘱录入上传
        private String isMrisEntry; //处方录入上传
        private String inptVisitNo;  // 住院号
        private String outptVisitNo ; // 就诊号
        private String patientType ; // 病人类型
        private String registerCrteName; // 挂号经办人
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date registerTime ; // 挂号日期
        private String doctorName;  //挂号医生姓名
        private String doctorId;   // 挂号医生id
        private String aae140 ; // 险种类型
        private String baa301; // 参保地行政区划代码
        private String insureSettleId;
        private String zzDoctorId;
        private String zzDoctorName;

        private String akc252; // 个人账户余额
        private String insureAccoutFlag;// 是否使用个人账户
        private String cardIden; // 卡识别码
        private String settleId; // 医保返回的结算id
        private String keyword;
        private Boolean isSettle;
        private Integer age;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date doctorVisitTime;
        private String inProfile;
        private Boolean isRemote; // 是否异地
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date outTime; // 出院时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date inTime; //入院时间
        private String outPhone;  // 门诊登时的电话
        private String inptPhone; // 住院登时的电话
        private String inDiseaseName;  // 入院主诊断
        private String outDiseaseName; // 出院主诊断
        private Integer hospitalDay; // 累计住院天数
        private String outDeptId; // 出院科室id
        private String outDeptName; // 出院科室编码
        private String insureIsTransmit; // 是否上传到医保
        private String ageUnitCode;
        private String genderCode;
        private String isOut; // 病人是否做了医保出院登记
        private String outDiseaseIcd10; // 出院诊断ICD
        private String bka008; //单位名称
        private String bka035; // 人员类别
        private String isHospital; // 是否住院
        private String bac001; // 公务员级别编码'
        @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date aac006;
        private String gend;
        private String outModeCode;
        private int settleCount; // 中途结算次数
        private String  isHalfSettle; //是否中途结算
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date settleTime; // 结算时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date maxRegister; // 最晚登记时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date minRegister;// 最早登记时间
        private String pracCertiNo;  // 医师编码
        // 床位号
        private String bedName;
        // 入院科室
        private String indeptName;
        // 性别
        private String gendCode;
        // 姓名
        private String name;
        // 状态
        private String statusCode;
        // 医疗机构名称
        private String fixmedinsName;
        // 医疗机构名称
        private String fixmedinsCode;
        // 医院等级
        private String hospLv;
        // 住院天数
        private Integer  totalInDays;
        // 就医地区划
        private String mdtrtareaAdmvs;
        private String address ; // 地址
        private String phone; // 联系电话
        private String visitNationCode;  // 就诊科别
        private BigDecimal  totalAdvance; // 累计预交金
        private String aab001;// 单位ID
        private String iptPsnSpFlagType;// 单位ID
        private String iptPsnSpFlag;// 单位ID
        private String iptPsnSpFlagDetlId;// 单位ID
        private String inNo;//住院号
        //重复住院标志   1：是重复住院   0：不是重复住院
        private String repeatIptFlag;
}
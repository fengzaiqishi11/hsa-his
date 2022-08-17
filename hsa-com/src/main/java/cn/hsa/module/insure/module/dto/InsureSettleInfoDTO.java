package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureSettleInfoDTO extends InsureIndividualSettleDO {
    private String keywords;

    private String mdtrtId; // 就诊id *

    private String visitId; // his就诊id *

    private String setlId; // 结算id *

    private String fixmedinsName; // 定点医疗机构名称 *

    private String fixmedinsCode; // 定点医疗机构编号 *

    private String hiSetlLv; // 医保结算等级

    private String hiNo; // 医保编号

    private String medcasno; // 病案号 *

    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dclaTime; // 申报时间

    private String psnName; // 人员姓名 *
    private String psnCertType;

    private String gend; // 性别 *

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date brdy; // 出生日期 *

    private int age; // 年龄

    private String babyAgeMonth; // 年龄(不足一岁)

    private String ntly; // 国籍 *

    private int nebAge; // （年龄不足一周岁 ）年龄*/

    private String naty; // 民族 *

    private String patnCertType; // 患者证件类别 *

    private String prfs; // 职业 *

    private String address; // 现住址

    private String work; // 单位名称

    private String workAddress; // 单位地址

    private String workPhone; // 单位电话

    private String workPostCode; // 邮编

    private String conerName; // 联系人姓名 *

    private String patnRlts; // 与患者关系 *

    private String conerAddr; // 联系人地址 *

    private String conerTel; // 联系人电话 *

    private String hiType; // 医保类型 *

    private String insuplc; // 参保地 *

    private String spPsnType; // 特殊人员类型

    private String nebAdmType; // 新生儿入院类型

    private String babyBirthWeight; // 新生儿出生体重

    private String babyInWeight; // 新生儿入院体重

    private String visitDrptName; // 门诊慢特病诊断科别

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitTime; // 门诊慢特病就诊日期

    private String iptMedType; // 住院医疗类型 *

    private String inWay; // 入院途径

    private String trtType; // 治疗类别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime; // 入院时间

    private String admCaty; // 入院科别 *

    private String refldeptDept; // 转科科别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime; // 出院时间

    private String dscgCaty; // 出院科别 *

    private String actIptDays; // 实际住院天数


    private String optWmDise; // 门（急）诊西医诊断
    private String wmDiswCode; // 西医诊断疾病代码
    private String optTcmDise ; // 门（急）诊西医诊断
    private String tcmDiseCode; // 中医诊断代码

    private String optTcmDiseCode; // 门（急）诊中医诊断代码
    private String optWmDiseCode; // 门（急）诊西医诊断代码
    private String inSituationName; // 入院病情

    private String accessoryDiagnosisName;  //其他诊断

    private String bldCat; // 输血品种 *
    private String  bldUnt; // 输血计量单位
    private BigDecimal bldAmt; // 输血量
    private String pwcryAfadmComaDura; // 颅脑损伤患者入院后昏迷时长
    private String pwcryBfadmComaDura; // 颅脑损伤患者入院前昏迷时长
    private String ventUsedDura; // 呼吸机使用时长
    private BigDecimal oprnOprtCodeCnt; // 手术操作代码计数
    private BigDecimal diagCodeCnt; // 诊断代码计数
    private String outModeName; // 离院方式名称 *

    private String dscgWay; // 离院方式 *

    private String billCode; // 票据代码 *

    private String billNo; // 票据号码 *

    private String bizSn; // 业务流水号 *

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setlBegnDate; // 结算开始日期 *

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setlEnDate; // 结算结束日期 *

    private BigDecimal psnSelfpay; // 个人自付 *

    private BigDecimal psnOwnpay; // 个人自费 *

    private BigDecimal acctPay; // 个人账户支出 *

    private BigDecimal psnCashpay; //个人现金支付 *

    private String hiPaymtd; // 医保支付方式 *

    private String hsorg; // 医保机构 *

    private String hosrgOpter; // 医保机构经办人 *

    private String medinsFillDept; // 医保机构填报部门 *

    private String medinsFillPsn; // 医疗机构填报人 *

    private String inProfile; // 病案号

    private String name; // 姓名

    private String genderCode; // 性别

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday; // 出生日期

    private String nationalityCation; // 国籍

    private String nationCode; // 民族

    private String certCode; // 患者证件类别

    private String certNo; // 证件号码

    private String occupationCode; // 职业代码

    private String contactName; // 联系人姓名

    private String contactRelaCode; // 与患者关系

    private String contactAddress; // 联系人地址

    private String contactPhone; // 联系人电话

    private String insureCode; // 医保类型

    private String patientCode; // 病人类型

    private String inDeptId; // 入院科别

    private String outDeptId; // 出院科别

    private String outModerCode; // 离园方式

    private String insureSettleId; // 业务流水号

    /*private Date startTime; // 结算开始日期

    private Date endTime; // 结算结束日期*/

    private BigDecimal selfPrice; // 个人自付

    private String lx;// 类型（‘1’ 住院  ‘2’ 门诊）

    private String medicineOrgCode; // 医保机构编码

    private String hospName; // 医院名称

    private String inDeptCode; // 入院科别

    private String outDeptCode; // 出院科别

    private String outProfile; //门诊号

    private String baa027; // 参保地

    private String bka035_name; // 特殊人员类型

    private String aac001; // 个人电脑号

    private String medicalRegNo; // 医保登记号

    private String mibId; // 个人基本信息id

    private String isUpload;// 上传标志
    private String insuplcAdmdvs; //参保地局域区划代码

    private String visitDrptId;//门诊就诊科室id

    private String upload;//打印、上传标志

    private String settleNo;//医保结算清单流水号

    private String inptBeforeDay;//入院前昏迷天
    private String inptBeforeHour;//入院前昏迷时
    private String inptBeforeMinute;//入院前昏迷分
    private String inptLastDay;//入院后昏迷天
    private String inptLastHour;//入院后昏迷时
    private String inptLastMinute;//入院后昏迷分
    private String isInpt;//是否有出院31天再住院计划
    private String chfpdrName;//主诊医生姓名
    private String chfpdrCode;//主诊医生代码
    private String zrNurseName;//责任护士姓名
    private String zrNurseId;//责任护士代码
    private String omsgid;//业务流水号
    private String invoiceDetailId;//票据代码
    private String invoiceNo;//票据号码
    /**
     * 特级护理天数
     */
    private Integer spgaNurscareDays;

    private Integer lv1NurscareDays;

    private Integer scdNurscareDays;

    private Integer lv3NursecareDays;

    private String iptOptNo;
    private String isTransmit ;
    private String isMatch;
    private String orgCode;
    private String aae140;
    private String doctorPracCertiNo;
    private String nursePracCertiNo;
    private String turnOrgName;
    private String bka035;
    private String inModeCode;
    private String phone;
    private Date admTime;
    private String acpMedinsName; // 拟接收机构名称
    private String acpMedinsCode; // 拟接收机构编码
    private String daysRinpFlag31; // 出院31天内再住院计划标志
    private String daysRinpPup31; // 出院31天内再住院目的
    private String zrNurseCode; // 责任护士代码
    private String currAddr; // 先住址
    private String empName;
    private String empAddr;
    private String empTel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opspMdtrtDate; // 门诊慢特病就诊日期
    private String admWay; // 入院途径
    private String opspDiagCaty; // 门诊慢特病诊断科别
    private BigDecimal nwbAdmWt; // 新生儿入院体重
    private String nwbAdmType;// 新生儿入院类型
    private String nwbBirWt; // 新生儿出生体重
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dscgTime;
    // 单位邮编
    private String poscode;
    private List<String> feeIdList; // 自费上传明细id集合
    //修改状态
    private String stasType;
    //完成标志
    private String cpltFlag;
    //就医信息是否上传
    private String isUplodDise;

    private String fixmedinsMdtrtId;//医药机构就诊ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String begntime; //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endtime; //结束时间
    private String medfeeSumamt; //医疗总费用
    private String medType; //医疗类型
    private String elecBillnoCode; //电子票据号码
    /**
     * 开始时间
     */
    private String startTime;


    private Date begndate; //开始日期
    private String certno; //证件号码
    private String insutype; //险种类型
    private String ipt_otp_no;//住院/门诊号	字符型
    private String mdtrt_id; // 就诊id *
    private String med_type;//医疗类别
    private String out_flag; //异地标志
    private String psn_no; //人员编号

}

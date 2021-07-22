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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureSettleInfoDTO extends InsureIndividualSettleDO {
    private String keywords;

    private String mdtrtId; // 就诊id *

    private String setlId; // 结算id *

    private String fixmedinsName; // 定点医疗机构名称 *

    private String fixmedinsCode; // 定点医疗机构编号 *

    private String hiSetlLv; // 医保结算等级

    private String hiNo; // 医保编号

    private String medcasno; // 病案号 *

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dclaTime; // 申报时间

    private String psnName; // 人员姓名 *

    private String gend; // 性别 *

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date brdy; // 出生日期 *

    private int age; // 年龄

    private String ntly; // 国籍 *

    private int nwbAge; // （年龄不足一周岁 ）年龄

    private String naty; // 民族 *

    private String patnCertType; // 患者证件类别 *

    private String prfs; // 职业 *

    private String cuurAddr; // 现住址

    private String empName; // 单位名称

    private String empAddr; // 单位地址

    private String empTel; // 单位电话

    private String poscode; // 邮编

    private String conerName; // 联系人姓名 *

    private String patnRlts; // 与患者关系 *

    private String conerAddr; // 联系人地址 *

    private String conerTel; // 联系人电话 *

    private String hiType; // 医保类型 *

    private String insuplc; // 参保地 *

    private String spPsnType; // 特殊人员类型

    private String nebAdmType; // 新生儿入院类型

    private Double nwbBirWt; // 新生儿出生体重

    private Double nwbAdmWt; // 新生儿入院体重

    private String opspDiagCaty; // 门诊慢特病诊断科别

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date opspMdtrtDate; // 门诊慢特病就诊日期

    private String iptMedType; // 住院医疗类型 *

    private String admWay; // 入院途径

    private String trtType; // 治疗类别

    @JsonFormat(pattern = "yyyy-MM-dd hh-MM-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh-MM-ss")
    private Date admTime; // 入院时间

    private String admCaty; // 入院科别 *

    private String refldeptDept; // 转科科别

    @JsonFormat(pattern = "yyyy-MM-dd hh-MM-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh-MM-ss")
    private Date dscgTime; // 出院时间

    private String dscgCaty; // 出院科别 *

    private int actIptDays; // 实际住院天数

    private String otpWmDise; // 门（急）诊西医诊断

    private String wmDiseCode; // 西医诊断疾病代码

    private String bldCat; // 输血品种 *

    private String dscgWay; // 离院方式 *

    private String billCode; // 票据代码 *

    private String billNo; // 票据号码 *

    private String bizSn; // 业务流水号 *

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setlBegnDate; // 结算开始日期 *

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setlEndnDate; // 结算结束日期 *

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

    @JsonFormat(pattern = "yyyy-MM-dd")
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

    private String patientCode; // 住院医疗类型

    private String inDeptId; // 入院科别

    private String outDeptId; // 出院科别

    private String outModerCode; // 离园方式

    private String insureSettleId; // 业务流水号

    private Date startTime; // 结算开始日期

    private Date endTime; // 结算结束日期

    private BigDecimal selfPrice; // 个人自付

    private String lx;// 类型（‘1’ 住院  ‘2’ 门诊）

    private String medicineOrgCode; // 医保机构编码

    private String hospName; // 医院名称

    private String inDeptCode; // 入院科别

    private String outDeptCode; // 出院科别

    private String outProfile; //门诊号

    private String baa027; // 参保地

    private String aac001; // 个人电脑号

    private String medicalRegNo; // 医保登记号

    private String mibId; // 个人基本信息id

    private String isUpload;// 上传标志
    private String insuplcAdmdvs; //参保地局域区划代码

    private String visitDrptId;//门诊就诊科室id

    private String upload;//打印、上传标志


}

package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisBaseInfoDO
 * @Describe: 病案患者基础信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisBaseInfoDO implements Serializable {
    private static final long serialVersionUID = 135432866105204219L;

    /** 主键 */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 就诊ID */
    private String visitId;

    /** 病案号 */
    private String inProfile;

    /** 病理号 */
    private String inBlh;

    /** 住院号 */
    private String inNo;

    /** 姓名 */
    private String name;

    /** 性别代码（XB） */
    private String genderCode;

    /** 性别名称 */
    private String genderName;

    /** 年龄 */
    private String age;

    /** 年龄单位代码（NLDW） */
    private String ageUnitCode;

    /** 年龄单位名称 */
    private String ageUnitName;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    /** 证件类型代码（ZJLX） */
    private String certCode;

    /** 证件类型名称 */
    private String certName;

    /** 证件号码 */
    private String certNo;

    /** 国籍代码（GJZD） */
    private String nationalityCation;

    /** 国籍名称 */
    private String nationalityName;

    /** 民族代码（MZ） */
    private String nationCode;

    /** 民族名称 */
    private String nationName;

    /** 籍贯 */
    private String nativePlace;

    /** 职业代码（ZY） */
    private String occupationCode;

    /** 职业名称 */
    private String occupationName;

    /** 婚姻状况代码（HYZK） */
    private String marryCode;

    /** 婚姻状况名称 */
    private String marryName;

    /** 工作单位 */
    private String work;

    /** 单位电话 */
    private String workPhone;

    /** 单位邮编 */
    private String workPostCode;

    /** 单位地址 */
    private String workAddress;

    /** 联系人姓名 */
    private String contactName;

    /** 联系人关系（RYGX） */
    private String contactRelaCode;

    /** 联系人关系名称 */
    private String contactRelaName;

    /** 联系人电话 */
    private String contactPhone;

    /** 联系人邮编 */
    private String contactPostCode;

    /** 联系人地址 */
    private String contactAddress;

    /** 居住地（省）编码 */
    private String nowProv;

    /** 居住地（省）名称 */
    private String nowProvName;

    /** 居住地（市）编码 */
    private String nowCity;

    /** 居住地（市）名称 */
    private String nowCityName;

    /** 居住地（区、县）编码 */
    private String nowArea;

    /** 居住地（区、县）名称 */
    private String nowAreaName;

    /** 居住地邮编 */
    private String nowPostCode;

    /** 户口地（省）编码 */
    private String nativeProv;

    /** 户口地（省）名称 */
    private String nativeProvName;

    /** 户口地（市）编码 */
    private String nativeCity;

    /** 户口地（市）名称 */
    private String nativeCityName;

    /** 户口地（区、县）编码 */
    private String nativeArea;

    /** 户口地（区、县）名称 */
    private String nativeAreaName;

    /** 户口地邮编 */
    private String nativePostCode;

    /** 住院次数 */
    private Integer inCnt;

    /** 入院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;

    /** 入院科室ID */
    private String inDeptId;

    /** 入院科室名称 */
    private String inDeptName;

    /** 入院床位ID */
    private String inBedId;

    /** 入院床位名称 */
    private String inBedName;

    /** 出院时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /** 出院科室ID */
    private String outDeptId;

    /** 出院科室名称 */
    private String outDeptName;

    /** 出院床位ID */
    private String outBedId;

    /** 出院床位名称 */
    private String outBedName;

    /** 住院天数 */
    private Integer inDays;

    /** 疾病版本（疾病分类名称） */
    private String icdVersion;

    /** 疾病诊断ICD */
    private String diseaseIcd10;

    /** 疾病诊断名称 */
    private String diseaseIcd10Name;

    /** 病理诊断ICD */
    private String blIcd10;

    /** 病理诊断名称 */
    private String blIcd10Name;

    /** 科室主任ID */
    private String directorId1;

    /** 科室主任姓名 */
    private String directorName1;

    /** 科室副主任ID */
    private String directorId2;

    /** 科室副主任姓名 */
    private String directorName2;

    /** 主治医生ID */
    private String zzDoctorId;

    /** 主治医生姓名 */
    private String zzDoctorName;

    /** 主管医生ID */
    private String zgDoctorId;

    /** 主管医生姓名 */
    private String zgDoctorName;

    /** 责任护士ID */
    private String zrNurseId;

    /** 责任护士姓名 */
    private String zrNurseName;

    /** 病历质量代码 */
    private String emrQualityCode;

    /** 病历质量 */
    private String emrQualityName;

    /** 质控医生ID */
    private String zkDoctorId;

    /** 质控医生姓名 */
    private String zkDoctorName;

    /** 质控护士ID */
    private String zkNurseId;

    /** 质控护士姓名 */
    private String zkNurseName;

    /** 质控时间 */
    private Date zkTime;

    /** 是否尸检（SF）编码 */
    private String isAutopsy;

    /** 是否尸检（SF）名称 */
    private String isAutopsyName;

    /** 血型代码（XX）编码 */
    private String bloodCode;

    /** 血型名称 */
    private String bloodName;

    /** RH代码 */
    private String rhCode;

    /** RH名称 */
    private String rhName;

    /** 新生儿体重 */
    private String babyWeight;

    /** 是否药物过敏（SF） */
    private String isAllergy;

    /** 过敏药物合集 */
    private String allergyList;

    /** 离院方式代码（CYFS） */
    private String outModeCode;

    /** 离院方式名称 */
    private String outModeName;

    /** 转院机构名称 */
    private String turnOrgName;

    /** 入院病情代码（RYQK） */
    private String inSituationCode;

    /** 入院病情名称 */
    private String inSituationName;

    /** 出院病情代码CYBQ） */
    private String outSituationCode;

    /** 出院病情名称 */
    private String outSituationName;

    /** 创建人ID */
    private String crteId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    /** 医疗机构名称 */
    private String hospName;

    /** 医疗支付方式类型(ZFFS) */
    private String payWayCode;

    /** 医疗支付方式名称 */
    private String payWayName;

    /** 健康卡号 */
    private String healthCard;

    /** 新生儿出生体重 */
    private String babyBirthWeight;

    /** 新生儿入院体重 */
    private String babyInWeight;

    /** 户口地址 */
    private String nativeAdress;

    /** 居住地址 */
    private String nowAdress;

    /** 年龄（月）不足一周岁 */
    private String babyAgeMonth;

    /** 出生地址 */
    private String birthAdress;

    /** 身份证号 */
    private String idCard;

    /** 电话号码 */
    private String phone;

    /** 单位名称及地址 */
    private String workInfo;

    /** 入院途径 */
    private String inWay;

    /** 病房 */
    private String inWard;

    /** 损伤中毒的外部原因 */
    private String damageReason;

    /** 进修医师id */
    private String jxDoctorId;

    /** 实习医师id */
    private String sxDoctorId;

    /** 进修医师名称 */
    private String jxDoctorName;

    /** 实习医师名称 */
    private String sxDoctorName;

    /** 编码员id */
    private String doctorCoderId;

    /** 编码员名称 */
    private String doctorCoderName;

    /** 病房2 */
    private String inWard2;

    /** 入院科室id */
    private String inDeptCode;

    /** 疾病编码（损伤原因） */
    private String diseaseIcd10Other;

    /** 转科科室 */
    private String turnDeptIds;

    /** 病例分析等级（A/B/C/D） */
    private String caseClassification;

    /** 是否实施重症监控 */
    private String isMonitor;

    /** 重症监控天数 */
    private String monitorDay;

    /** 重症监控小时 */
    private String monitorHour;

    /** 是否单种病管理 */
    private String isDzb;

    /** 临床路径管理状态 */
    private String lcljStatus;

    /** DGR管理状态 */
    private String drgStatus;

    /** 是否使用抗生素 */
    private String isKss;

    /** 是否细菌送检 */
    private String isXjsj;

    /** 法定传染源类型 */
    private String isCry;

    /** 肿瘤分期 */
    private String zlLevel;

    /** 新生儿Apgar评分 */
    private String apgarNum;

    /** 目的 */
    private String aim;

    private String inptBeforeDay;

    private String inptBeforehour;

    private String inptBeforeMinute;

    private String inptLastDay;

    private String inptLastHour;

    private String inptLastMinute;

    private String isInpt;

    private String yljgCode; // 医疗机构编码

    /** 转科时间1 */
    private Date turnTime1;
    /** 转科时间2 */
    private Date turnTime2;
    /** 转科时间3 */
    private Date turnTime3;

    /** 转科3 */
    private String turnDept1;
    /** 转科3 */
    private String turnDept2;
    /** 转科3 */
    private String turnDept3;

    /** 门急诊医生Id */
    private String outptDoctorId;
    /** 门急诊医生名称 */
    private String outptDoctorName;
    /** 病例分型 */
    private String caseClassify;
    /** 临床路径病例 */
    private String clinicalPathCase;
    /** 抢救次数 */
    private String rescueCount;
    /** 成功次数 */
    private String rescueSuccessCount;

}
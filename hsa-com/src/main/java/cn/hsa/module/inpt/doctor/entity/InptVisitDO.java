package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@Package_name: cn.hsa.module.inpt.entity
 *@Class_name: InptVisitDO
 *@Describe: 住院就诊表
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-04 9:52:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptVisitDO extends PageDO implements Serializable {

    private static final long serialVersionUID = -31648186911883178L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 个人档案ID
     */
    private String profileId;
    /**
     * 病案号/住院档案号（入院登记回写）
     */
    private String inProfile;
    /**
     * 住院号
     */
    private String inNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别代码（XB）
     */
    private String genderCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 年龄单位代码（NLDW）
     */
    private String ageUnitCode;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 国籍代码（GJZD）
     */
    private String nationalityCation;
    /**
     * 职业代码（ZY）
     */
    private String occupationCode;
    /**
     * 学历代码（XL）
     */
    private String educationCode;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人关系（RYGX）
     */
    private String contactRelaCode;
    /**
     * 联系人地址
     */
    private String contactAddress;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 民族代码（MZ）
     */
    private String nationCode;
    /**
     * 证件类型代码（ZJLX）
     */
    private String certCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 婚姻状况代码（HYZK）
     */
    private String marryCode;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 现住址
     */
    private String address;
    /**
     * 优惠类别ID
     */
    private String preferentialTypeId;
    /**
     * 病人类型代码（BRLX）
     */
    private String patientCode;
    /**
     * 接收医院名称
     */
    private String receiveHospName;
    /**
     * 当前床位ID
     */
    private String bedId;
    /**
     * 当前床位名称
     */
    private String bedName;
    /**
     * 护理级别（医嘱回写）
     */
    private String nursingCode;
    /**
     * 膳食类型（医嘱回写）
     */
    private String dietType;
    /**
     * 病情标识（医嘱回写）
     */
    private String illnessCode;
    /**
     * 当前状态代码（BRZT）
     */
    private String statusCode;
    /**
     * 入院病区ID
     */
    private String inWardId;
    /**
     * 入院科室ID
     */
    private String inDeptId;
    /**
     * 入院科室名称
     */
    private String inDeptName;
    /**
     * 入院时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;
    /**
     * 主治医生ID
     */
    private String zzDoctorId;
    /**
     * 主治医生名称
     */
    private String zzDoctorName;
    /**
     * 经治医生ID
     */
    private String jzDoctorId;
    /**
     * 经治医生名称
     */
    private String jzDoctorName;
    /**
     * 主管医生ID
     */
    private String zgDoctorId;
    /**
     * 主管医生名称
     */
    private String zgDoctorName;
    /**
     * 入院备注
     */
    private String inRemark;
    /**
     * 入院方式代码（RYFS）
     */
    private String inModeCode;
    /**
     * 入院主诊断ID（base_disease）
     */
    private String inDiseaseId;
    /**
     * 入院主诊断名称（base_disease）
     */
    private String inDiseaseName;
    /**
     * 入院主诊断ICD-10码（base_disease）
     */
    private String inDiseaseIcd10;
    /**
     * 入院情况代码（RYQK）
     */
    private String inSituationCode;
    /**
     * 门诊就诊号
     */
    private String outptVisitNo;
    /**
     * 门诊医生ID
     */
    private String outptDoctorId;
    /**
     * 门诊医生姓名
     */
    private String outptDoctorName;
    /**
     * 门诊主诊断ID
     */
    private String outptDiseaseId;
    /**
     * 门诊主诊断名称
     */
    private String outptDiseaseName;
    /**
     * 门诊主诊断ICD-10码
     */
    private String outptDiseaseIcd10;
    /**
     * 出院病区ID
     */
    private String outWardId;
    /**
     * 出院科室ID
     */
    private String outDeptId;
    /**
     * 出院科室名称
     */
    private String outDeptName;
    /**
     * 出院时间
     */
    private Date outTime;
    /**
     * 出院主诊断ID
     */
    private String outDiseaseId;
    /**
     * 出院主诊断名称
     */
    private String outDiseaseName;
    /**
     * 出院主诊断ICD-10码
     */
    private String outDiseaseIcd10;
    /**
     * 出院操作人ID
     */
    private String outOperId;
    /**
     * 出院操作人姓名
     */
    private String outOperName;
    /**
     * 出院操作时间
     */
    private Date outOperTime;
    /**
     * 出院情况代码（CYQK）
     */
    private String outSituationCode;
    /**
     * 出院备注
     */
    private String outRemark;
    /**
     * 出院方式代码（CYFS）
     */
    private String outModeCode;
    /**
     * 是否归档（SF）
     */
    private String isArchive;
    /**
     * 归档时间
     */
    private Date archiveTime;
    /**
     * 归档人ID
     */
    private String archiveId;
    /**
     * 归档人姓名
     */
    private String archiveName;
    /**
     * 参保类型代码（CBLX）
     */
    private String insureCode;
    /**
     * 参保机构编码
     */
    private String insureOrgCode;
    /**
     * 参保号
     */
    private String insureNo;
    /**
     * 医保业务类型编码
     */
    private String insureBizCode;
    /**
     * 医保待遇类型编码
     */
    private String insureTreatCode;
    /**
     * 医保病人ID
     */
    private String insurePatientId;
    /**
     * 医保合同单位备注
     */
    private String insureRemark;
    /**
     * 累计预交金
     */
    private BigDecimal totalAdvance;
    /**
     * 累计费用
     */
    private BigDecimal totalCost;
    /**
     * 累计余额
     */
    private BigDecimal totalBalance;
    /**
     * 责任护士id
     */
    private String respNurseId;
    /**
     * 责任护士名称
     */
    private String respNurseName;

    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;
    /**
     * 危急值状态代码：0 正常，1、有危急值，2、已处理危急值
     */
    private String criticalValueCode;
    /**
     * 担保金额
     */
    private BigDecimal guaranteeBalance;
}

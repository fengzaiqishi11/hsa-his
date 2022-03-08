package cn.hsa.module.outpt.medictocare.entity;

import cn.hsa.base.PageDO;
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
 * outpt：门诊
 medical_care：医养
 apply：申请(OutptMedicalCareApply)实体类
 *
 * @author makejava
 * @since 2022-03-01 08:53:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptMedicalCareApplyDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -60513630296950945L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 档案id
     */
    private String profileId;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 申请人姓名
     */
    private String name;
    /**
     * 性别（XB）
     */
    private String genderCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 年龄单位（NLDW）
     */
    private String ageUnitCode;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 转诊类别（ZZLB：1医转养、2养转医）
     */
    private String changeType;
    /**
     * 养转医申请id
     */
    private String careToMedicId;
    /**
     * 患者类别（1门诊、2住院）
     */
    private String visitType;
    /**
     * 申请科室ID
     */
    private String applyDeptId;
    /**
     * 申请入住机构编码
     */
    private String applyCompanyCode;
    /**
     * 申请入住机构名称
     */
    private String applyCompanyName;
    /**
     * 期望入住时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hopeInTime;
    /**
     * 申请人ID
     */
    private String applyId;
    /**
     * 申请人姓名
     */
    private String applyName;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    /**
     * 是否入住（SF）
     */
    private String isHouse;
    /**
     * 入住床号
     */
    private String houseBed;
    /**
     * 转诊主诉
     */
    private String referralMainSuit;
    /**
     * 护理级别（HLJB）
     */
    private String nusreTypeCode;
    /**
     * 主诊断疾病id
     */
    private String diseaseId;
    /**
     * 主诊断疾病名
     */
    private String diseaseName;
    /**
     * 实际入住时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realityInTime;
    /**
     * 医养申请状态（YYSQZT：0待处理、1已接收、2已拒绝）
     */
    private String statusCode;
    /**
     * 就诊科室id
     */
    private String visitDeptId;
    /**
     * 就诊医生id
     */
    private String visitDoctorId;
    /**
     * 就诊医生姓名
     */
    private String visitDoctorName;
    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;
    /**
     * 备注
     */
    private String remark;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
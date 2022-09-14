package cn.hsa.module.outpt.visit.entity;

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
 * @Package_name: cn.hsa.module.outpt.visit.entity
 * @Class_name: OutptVisitDO
 * @Describe: 门诊就诊对象(门诊病人)
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptVisitDO extends PageDO implements Serializable {

    private static final long serialVersionUID = 3358648859964807185L;

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
     * 挂号ID
     */
    private String registerId;
    /**
     * 挂号单号
     */
    private String registerNo;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 婚姻状况代码（HYZK）
     */
    private String marryCode;
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
     * 电话号码
     */
    private String phone;
    /**
     * 就诊号（根据单据规则生成）
     */
    private String visitNo;
    /**
     * 就诊类别代码（JZLB）
     */
    private String visitCode;
    /**
     * 病人类型代码（BRLX）
     */
    private String patientCode;
    /**
     * 优惠类别ID
     */
    private String preferentialTypeId;
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
     * 就诊医生ID
     */
    private String doctorId;
    /**
     * 就诊医生名称
     */
    private String doctorName;
    /**
     * 就诊科室ID
     */
    private String deptId;
    /**
     * 就诊科室名称
     */
    private String deptName;
    /**
     * 就诊时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 拼音码
     */
    private String pym;
    /**
     * 五笔码
     */
    private String wbm;
    /**
     * 是否就诊（SF）
     */
    private String isVisit;
    /**
     * 转入院代码（ZRY）：0、未开住院证，1、已开住院证、2、已入院登记
     */
    private String tranInCode;
    /**
     * 是否复诊（SF）
     */
    private String isFirstVisit;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String outProfile;
    //建议入院科室ID
    private String inDeptId;
    //建议入院诊断ID
    private String inDiseaseId;
    //建议入院备注
    private String inRemark;
    //建议预交金
    private BigDecimal advancePayment = BigDecimal.valueOf(0);
    /**
     * 开住院证时间
     */
    private Date inCertTime;
    /**
     * 是否食源性
     */
    private String isFoodBorne;
    /**
     * 是否为体检登记
     */
    private String isPhys;
    /**
     * 居住地地址
     */
    private String nowAddress;
    /**
     * 职业代码（ZY）
     */
    private String occupationCode;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 自费病人上传完成标志
     */
    private String cpltFlag;
    /**
     * 0：无处方  1：存在处方未提交  2：全部提交
     */
    private String isSubmit;
}

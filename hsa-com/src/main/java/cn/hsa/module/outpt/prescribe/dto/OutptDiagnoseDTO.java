package cn.hsa.module.outpt.prescribe.dto;

import cn.hsa.module.outpt.prescribe.entity.OutptDiagnoseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *@Package_name: cn.hsa.module.outpt.prescribe.entity
 *@Class_name: OutptPrescribeTempDO
 *@Describe: 门诊诊断
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020/9/3 14:00
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptDiagnoseDTO extends OutptDiagnoseDO implements Serializable {
    private static final long serialVersionUID = 2681588503570906404L;
    private String keyword;
    private String diseaseName;
    private String diseaseIds;
    //诊断信息
    private List<OutptDiagnoseDTO> outptDiagnoseDOList;
    private String diseaseCode;
    private String doctorId;
    private String doctorName;
    private String deptName;
    private String insureRegCode; // 医保注册编码
    private String insureInllnessCode;
    private String insureInllnessName;
    // 主治医生ID
    private String zzDoctorId;
    // 主治医生姓名
    private String zzDoctorName;
    // 入院科室名称
    private String inDeptName;
    // 入院科室id
    private String inDeptId;
    private String pracCertiNo; // 医师执业证书编码
}

package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptDiagnoseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptDiagnoseDTO extends InptDiagnoseDO implements Serializable {

    // 疾病编码
    private String diseaseCode;

    // 疾病名称
    private String diseaseName;

    // 疾病ICD名称
    private String Icd10Name;

    // 疾病ICD码
    private String Icd10;

    // icd版本
    private String icdVersion;
    // 诊断次序
    private String diagnoseNo ;

    private List<InptDiagnoseDTO> inptDiagnoseDTOList;
    private String jzDoctorName; //经治医生名称

    //疾病分类代码
    private String diseaseTypeCode;
    //附加编码
    private String attachCode;
    //国家编码
    private String nationCode;
    // 疾病国家名称
    private String nationName;
    // 入院科室名称
    private String inDeptName;
   // 入院科室id
    private String inDeptId;
    // 主治医生ID
    private String zzDoctorId;
    // 主治医生姓名
    private String zzDoctorName;
    private String insureInllnessCode;
    private String insureInllnessName;
    private String pracCertiNo; // 医师执业证书编码
    private String medicalRegNo ; // 就医登记号
    private String insureSettleId; // 医保结算id
    private String admCondType; // 入院病情标识
    private String customDisease; // 自定义诊断
    private String tcmSyndromesId; // 中医症候id
    private String tcmSyndromesName; // 中医症候名称

}
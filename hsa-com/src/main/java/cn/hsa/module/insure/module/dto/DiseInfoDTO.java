package cn.hsa.module.insure.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class DiseInfoDTO {
    private String diagType; // 诊断类别
    private String diagCode; // 诊断代码
    private String diagName; // 诊断名称
    private String inDiseaseName; // 诊断类别
    private String inDiseaseIcd10; // 诊断代码
    private String typeCode; // 诊断类型
    private String visitIcdName;
    private String visitIcdCode;
    private String diseaseId; // 诊断代码
    private String isMain; // 是否主诊断
    private String name; // 诊断名称
}

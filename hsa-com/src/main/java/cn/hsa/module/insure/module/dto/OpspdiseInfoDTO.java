package cn.hsa.module.insure.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OpspdiseInfoDTO {
    private String diagName;  // 诊断名称
    private String diagCode;  // 诊断代码
    private String operDiseaseName;  // 手术操作名称
    private String operDiseaseIcd9;  // 手术操作代码

    private String visitIcdCode;
    private String visitIcdName;

    private String diseaseId; // 诊断代码
    private String name; // 诊断名称


//    private String diagName;  // 诊断名称
//    private String diagCode;  // 诊断代码
//    private String oprnOprtName;  // 手术操作名称
//    private String oprnOprtCode;  // 手术操作代码

}

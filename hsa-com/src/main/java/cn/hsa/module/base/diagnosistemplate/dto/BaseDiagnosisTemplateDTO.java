package cn.hsa.module.base.diagnosistemplate.dto;

import cn.hsa.module.base.diagnosistemplate.entity.BaseDiagnosisTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseDiagnosisTemplateDTO extends BaseDiagnosisTemplateDO  implements Serializable {

    private String keyword;

    private String checkFlag;

}

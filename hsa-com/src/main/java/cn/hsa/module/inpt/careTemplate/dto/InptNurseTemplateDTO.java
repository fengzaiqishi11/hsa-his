package cn.hsa.module.inpt.careTemplate.dto;

import cn.hsa.module.inpt.careTemplate.entity.InptNurseTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class InptNurseTemplateDTO extends InptNurseTemplateDO implements Serializable {

    private static final long serialVersionUID = -2122671344739328200L;
    private List<String> templateIdList;
}

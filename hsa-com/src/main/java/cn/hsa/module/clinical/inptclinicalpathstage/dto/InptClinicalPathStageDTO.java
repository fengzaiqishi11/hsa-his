package cn.hsa.module.clinical.inptclinicalpathstage.dto;

import cn.hsa.module.clinical.inptclinicalpathstage.entity.InptClinicalPathStageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptClinicalPathStageDTO extends InptClinicalPathStageDO implements Serializable {
    private static final long serialVersionUID = 421152518083858898L;

    private String keyword;
}

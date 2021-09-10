package cn.hsa.module.clinical.clinicalpathstage.dto;


import cn.hsa.module.clinical.clinicalpathstage.entity.ClinicalPathStageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ClinicalPathStageDTO extends ClinicalPathStageDO implements Serializable {

    /**
     * 关键字
     */
    private String keyword;


}

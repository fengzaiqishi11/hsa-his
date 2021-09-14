package cn.hsa.module.clinical.clinicalpathstage.dto;


import cn.hsa.module.clinical.clinicalpathstage.entity.ClinicalPathStageDO;
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
public class ClinicalPathStageDTO extends ClinicalPathStageDO implements Serializable {

    /**
     * 关键字
     */
    private String keyword;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 是否查询 顺序号
     */
    private String isFlag;

    /**
     * 删除的id集合
     */
    private List<String> ids;

}

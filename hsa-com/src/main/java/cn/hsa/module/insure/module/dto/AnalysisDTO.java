package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 明细审核分析信息
 */
@Data
public class AnalysisDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 系统编码
     */
//    @JSONField(name = "syscode")
    private String syscode;
    /**
     * 任务ID
     */
//    @JSONField(name = "task_id")
    private String taskId;
    /**
     * 触发场景
     */
//    @JSONField(name = "trig_scen")
    private String trigScen;
    /**
     * 规则标识集合
     */
//    @JSONField(name = "rule_ids")
    private String ruleIds;
    /**
     * 参保人信息
     */
//    @JSONField(name = "patient_dtos")
    private AnaInsuDTO patientDtos;

//    @JSONField(serialize = false)
    private String mdtrtAdmdvs;

}

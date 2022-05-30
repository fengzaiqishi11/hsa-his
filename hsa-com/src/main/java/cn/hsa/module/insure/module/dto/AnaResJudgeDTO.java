package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 违规信息
 */
@Data
public class AnaResJudgeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 违规标识
     */
    @JSONField(name = "jr_id")
    private String jrId;
    /**
     * 规则ID
     */
    @JSONField(name = "rule_id")
    private String ruleId;
    /**
     * 规则名称
     */
    @JSONField(name = "rule_name")
    private String ruleName;
    /**
     * 违规内容
     */
    @JSONField(name = "vola_cont")
    private String volaCont;
    /**
     * 参保人ID
     */
    @JSONField(name = "patn_id")
    private String patnId;
    /**
     * 就诊ID
     */
    @JSONField(name = "mdtrt_id")
    private String mdtrtId;
    /**
     * 违规金额
     */
    @JSONField(name = "vola_amt")
    private BigDecimal volaAmt;
    /**
     * 违规金额计算状态
     */
    @JSONField(name = "vola_amt_stas")
    private String volaAmtStas;
    /**
     * 违规金额计算状态
     */
    private String volaAmtStasName;
    /**
     * 严重程度
     */
    @JSONField(name = "sev_deg")
    private String sevDeg;
    /**
     * 严重程度
     */
    private String sevDegName;
    /**
     * 违规依据
     */
    @JSONField(name = "vola_evid")
    private String volaEvid;
    /**
     * 违规行为分类
     */
    @JSONField(name = "vola_bhvr_type")
    private String volaBhvrType;
    /**
     * 违规行为分类
     */
    private String volaBhvrTypeName;
    /**
     * 任务ID
     */
    @JSONField(name = "task_id")
    private String taskId;

    /**
     * 违规明细
     */
    @JSONField(name = "judge_result_detail_dtos")
    private List<AnaResJudgeDetlDTO> judgeResultDetailDtos;

}

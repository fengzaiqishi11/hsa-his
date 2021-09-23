package cn.hsa.module.clinical.inptclinicalpathstage.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 病人阶段病情记录表(InptClinicalPathStage)实体类
 *
 * @author makejava
 * @since 2021-09-22 14:16:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptClinicalPathStageDO extends PageDO implements Serializable  {
    private static final long serialVersionUID = 421152518083858888L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 婴儿ID
     */
    private String babyId;
    /**
     * 临床路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 临床路径阶段ID(clinic_path_stage.id)
     */
    private String stageId;
    /**
     * 记录名称
     */
    private String name;
    /**
     * 病情状态(BQZT)（0:正常 1正变异 2 负变异）
     */
    private String illnessState;
    /**
     * 变异原因分类(BYYY)
     */
    private String variationReason;
    /**
     * 变异原因补充
     */
    private String variationRemarke;
    /**
     * 阶段开始时间
     */
    private Date beginTime;
    /**
     * 阶段结束时间
     */
    private Date endTime;
    /**
     * 备注
     */
    private String remarke;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    private Date crteTime;

}

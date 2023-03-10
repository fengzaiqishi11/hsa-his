package cn.hsa.module.clinical.inptclinicalpathstate.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 临床路径病人记录表(InptClinicalPathState)实体类
 *
 * @author makejava
 * @since 2021-09-22 14:47:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InptClinicalPathStateDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -29683798207572817L;
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
     * 临床路径状态(LJZT) 1路径内；2退出路径, 3取消入径
     */
    private String pathState;
    /**
     * 入径开始ID(clinic_path_list.id)
     */
    private String startStageId;
    /**
     * 入径开始阶段名称
     */
    private String startStageName;
    /**
     * 当前路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 入径创建人ID
     */
    private String pathCrteId;
    /**
     * 入径创建人姓名
     */
    private String pathCrteName;
    /**
     * 入径创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pathCrteTime;
    /**
     * 当前阶段ID(clinic_path_stage.id)
     */
    private String stageId;
    /**
     * 当前阶段名称
     */
    private String stageName;
    /**
     * 出径阶段ID(clinic_path_stage.id)
     */
    private String endStageId;
    /**
     * 出径阶段名称
     */
    private String endStageName;
    /**
     * 出径人ID
     */
    private String endCrteId;
    /**
     * 出径人姓名
     */
    private String endCrteName;
    /**
     * 出径时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCrteTime;
    /**
     * 出径方式(CJFS)0出院；1变异
     */
    private String endPathType;
    /**
     * 出径备注
     */
    private String endPathRemarke;
    /**
     * 路径内发生费用
     */
    private BigDecimal totalPrice;

}

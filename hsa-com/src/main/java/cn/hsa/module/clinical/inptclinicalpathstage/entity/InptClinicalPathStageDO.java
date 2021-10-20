package cn.hsa.module.clinical.inptclinicalpathstage.entity;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录表
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
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
     * 入径状态表id
     */
    private String clinicalPathStageId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     * 阶段结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}

package cn.hsa.module.clinical.clinicalpathstage.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：临床路径阶段描述(ClinicalPathStage)实体类
 *
 * @author makejava
 * @since 2021-09-10 16:34:57
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicalPathStageDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 225523167703485723L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 临床路径目录ID(clinic_path_list.id)
     */
    private String listId;
    /**
     * 阶段编号 路径编号+2顺序号
     */
    private String code;
    /**
     * 阶段名称
     */
    private String name;
    /**
     * 阶段描述
     */
    private String describe;
    /**
     * 最小天数
     */
    private String minTime;
    /**
     * 最大天数
     */
    private String maxTime;
    /**
     * 时间单位(SJDW):0天,1小时
     */
    private String timeUnit;
    /**
     * 排序编号
     */
    private String sortNo;
    /**
     * 备注
     */
    private String remarke;
    /**
     * 是否手术日(预留)
     */
    private String isOperationDay;
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

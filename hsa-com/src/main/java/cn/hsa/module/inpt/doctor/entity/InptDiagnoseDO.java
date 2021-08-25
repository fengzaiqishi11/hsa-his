package cn.hsa.module.inpt.doctor.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 住院诊断表(InptDiagnose)实体类
 *
 * @author makejava
 * @since 2020-09-24 14:19:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptDiagnoseDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -53239864316387997L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 疾病ID
     */
    private String diseaseId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 诊断类型代码（ZDLX）
     */
    private String typeCode;
    /**
     * 是否主诊断（SF）
     */
    private String isMain;
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
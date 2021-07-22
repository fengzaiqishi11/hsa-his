package cn.hsa.module.base.diagnosistemplate.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
 * base：基础模块
 * advice：诊断模板
 * <p>
 * 表说明：
 * -&#(BaseDiagnosisTemplate)实体类
 *
 * @author pengbo
 * @since 2021-03-27 08:43:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDiagnosisTemplateDO  extends PageDO implements Serializable {
    private String id;// 主键
    private String hospCode;// 医院编码
    private String bizType;// 业务类别 （门诊，住院）
    private String typeCode;// 疾病分类
    private String diseaseId;// 疾病ID
    private String diseaseName;// 疾病名称
    private String diseaseIcd10;// 疾病ICD-10码
    private String useScopeCode;// 使用范围（SYFW）
    private String pym;// 拼音码
    private String wbm;// 五笔码
    private String deptId;// 科室ID
    private String deptName;// 科室名称
    private String doctorId;// 医生ID
    private String doctorName;// 医生名称
    private String isCheck;// 是否审核
    private String checkId;// 审核人ID
    private String checkName;// 审核人姓名
    private Date checkTime;// 审核时间
    private String isValid;// 是否有效：0否、1是（SF）
    private String crteId;// 创建人ID
    private String crteName;// 创建人姓名
    private Date crteTime;// 创建时间
}

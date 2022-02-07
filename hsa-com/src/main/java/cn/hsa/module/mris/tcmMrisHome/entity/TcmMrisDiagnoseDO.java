package cn.hsa.module.mris.tcmMrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.entity
 * @Class_name: TcmMrisDiagnoseDO
 * @Describe: 中医病案诊断信息表（西医诊断）
 * @author liyun.liu
 * @since 2022-01-17 09:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TcmMrisDiagnoseDO implements Serializable {
    private static final long serialVersionUID = 7436958421162141574L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案ID（tcm_mris_base_info.id）
     */
    private String mbiId;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 诊断类型代码（ZDLX）
     */
    private String diseaseCode;
    /**
     * 诊断类型名称
     */
    private String diseaseName;
    /**
     * 疾病ICD版本
     */
    private String icdVersion;
    /**
     * 疾病ICD编码
     */
    private String diseaseIcd10;
    /**
     * 疾病名称
     */
    private String diseaseIcd10Name;
    /**
     *入院病情
     */
    private String inSituationCode;

}
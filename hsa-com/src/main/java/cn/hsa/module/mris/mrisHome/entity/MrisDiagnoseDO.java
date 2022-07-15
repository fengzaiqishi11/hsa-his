package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisControlDO
 * @Describe: 病案诊断信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisDiagnoseDO implements Serializable {
    private static final long serialVersionUID = -18335570905025477L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 病案ID（mris_base_info.id）
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

    private String diseaseIcd101;

    private String diseaseIcd102;

    private String diseaseIcd10Name1;

    private String diseaseIcd10Name2;

    private String inSituationCode1;

    private String columnsNum;

    private String inSituationCode2;
    private String inBlh; // 病理号
    /**
     * 诊断类型代码2（ZDLX） liuliyun 2021/7/13
     */
    private String diseaseCode2;
    /**
     * 诊断类型名称
     */
    private String diseaseName2;
    /**
     * 疾病分类
     */
    private String typeCode;
    /**
     * 诊断序号
     */
    private int columnIndex;
}
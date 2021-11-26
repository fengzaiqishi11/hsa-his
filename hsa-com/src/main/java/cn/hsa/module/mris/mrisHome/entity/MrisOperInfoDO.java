package cn.hsa.module.mris.mrisHome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.mris.entity
 * @Class_name: MrisOperInfoDO
 * @Describe: 病案手术信息表
 * @author LiaoJiGuang
 * @since 2020-09-22 15:14:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MrisOperInfoDO implements Serializable {
    private static final long serialVersionUID = 516030351959338720L;
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
     * 手术疾病ID（base_disease）
     */
    private String operDiseaseId;
    /**
     * 手术疾病编码ICD-9（base_disease）
     */
    private String operDiseaseIcd9;
    /**
     * 手术疾病名称（base_disease）
     */
    private String operDiseaseName;
    /**
     * 切口代码（QK）
     */
    private String notchedCode;
    /**
     * 切口名称
     */
    private String notchedName;
    /**
     * 愈合代码（YH）
     */
    private String healCode;
    /**
     * 愈合名称
     */
    private String healName;
    /**
     * 麻醉方式代码（MZFS）
     */
    private String anaCode;
    /**
     * 麻醉方式名称
     */
    private String anaName;
    /**
     * 手术医生ID
     */
    private String operDoctorId;
    /**
     * 手术医生姓名
     */
    private String operDoctorName;
    /**
     * 手术日期
     */
    private String operTime;
    /**
     * 手术一助ID
     */
    private String assistantId4;
    /**
     * 手术一助姓名
     */
    private String assistantName1;
    /**
     * 手术二助ID
     */
    private String assistantId2;
    /**
     * 手术二助姓名
     */
    private String assistantName2;
    /**
     * 手术三助ID
     */
    private String assistantId3;
    /**
     * 手术三助姓名
     */
    private String assistantName3;
    /**
     * 第一麻醉医生ID
     */
    private String anaId1;
    /**
     * 第一麻醉医生姓名
     */
    private String anaName1;
    /**
     * 第二麻醉医生ID
     */
    private String anaId2;
    /**
     * 第二麻醉医生姓名
     */
    private String anaName2;
    /**
     * 第三麻醉医生ID
     */
    private String anaId3;
    /**
     * 第三麻醉医生姓名
     */
    private String anaName3;
    /**
     * 手术级别代码（SSJB）
     */
    private String operCode;
    /**
     * 手术级别名称
     */
    private String operName;
    /**
     * 手术部位代码（SSBW）
     */
    private String operPosition;
    /**
     * 手术部位名称
     */
    private String operPositionName;

    private String columnsNum;

    private String otherOperTime; //择期手术时间

}
package cn.hsa.module.emr.emrpatientrecord.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名含义：
 * emr:电子病历缩写
 * patient：病人
 * (EmrPatientRecord)实体类
 *
 * @author
 * @since 2020-09-21 19:47:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrPatientRecordDO implements Serializable {
	private static final long serialVersionUID = -30192909285560078L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 医院编码
	 */
	private String hospCode;
	/**
	 * 就诊id
	 */
	private String visitId;
	/**
	 * 病历病人ID（emr_patient.id）
	 */
	private String patientId;
	/**
	 * 病历模板ID（emr_classify_template.id）
	 */
	private String classifyTemplateId;
	/**
	 * 病历内容ID（emr_patient_html.id）
	 */
	private String patientHtmlId;
	/**
	 * 创建人id
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
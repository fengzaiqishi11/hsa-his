package cn.hsa.module.emr.emrpatienthtml.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.emrpatienthtml.entity
 * @Class_name: EmrPatientHtmlDO
 * @Describe: 电子病历存储病人已经编写好的病历，以html格式存储
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/23 19:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrPatientHtmlDO implements Serializable {
	private static final long serialVersionUID = 8858657092633447653L;

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
	 * 病历记录ID（emr_patient_record.id）
	 */
	private String patientRecordId;

	/**
	 * 病历内容
	 */
	private byte[] html;

	/**
	 * 送审状态代码（SHZT）
	 */
	private String auditCode;

	/**
	 * 送审人id
	 */
	private String reviewId;

	/**
	 * 送审人姓名
	 */
	private String reviewName;

	/**
	 * 送审时间
	 */
	private Date reviewTime;

	/**
	 * 是否指定审核人（SF）
	 */
	private String isSpecify;

	/**
	 * 指定审核人id
	 */
	private String specifyId;

	/**
	 * 指定审核人姓名
	 */
	private String specifyName;

	/**
	 * 实际审核人id
	 */
	private String auditId;

	/**
	 * 实际审核人姓名
	 */
	private String auditName;

	/**
	 * 实际审核时间
	 */
	private Date auditTime;

	/**
	 * 审核意见
	 */
	private String auditOption;

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

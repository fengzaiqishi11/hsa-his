package cn.hsa.module.emr.emrpatient.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.emr.patient.entity
 * @Class_name: EmrPatientDO
 * @Describe: 电子病历病人已经拥有的病历记录
 * @Author: guanhongqiang
 * @Email: hongqiang.guan@powersi.com
 * @Date: 2020-09-22 10:52:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrPatientDO extends PageDO implements Serializable {
	private static final long serialVersionUID = -42264464129879721L;
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
	 * 病历模板ID（emr_classify_template.id）
	 */
	private String classifyTemplateId;
	/**
	 * 病历记录ID（emr_patient_record.id）
	 */
	private String patientRecordId;
	/**
	 * 病历内容ID（emr_patient_html.id）
	 */
	private String patientHtmlId;
	/**
	 * 上次病历记录ID（emr_patient_record.id）
	 */
	private String scPatientRecordId;
	/**
	 * 上次病历内容ID（emr_patient_html.id）
	 */
	private String scPatientHtmlId;
	/**
	 * 使用科室id
	 */
	private String deptId;
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
	/**
	 * 时间记录项
	 */
	private Date recordTime;
	/**
	 * 病历打印次数
	 */
	private int printNum;
}
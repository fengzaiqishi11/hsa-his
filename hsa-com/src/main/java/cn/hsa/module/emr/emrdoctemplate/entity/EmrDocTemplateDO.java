package cn.hsa.module.emr.emrdoctemplate.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (EmrDocTemplate)实体类
 *
 * @author makejava
 * @since 2020-12-08 15:30:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrDocTemplateDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 654592832969912564L;
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 医院编码
	 */
	private String hospCode;
	/**
	 * 医生另存模板编码
	 */
	private String templateCode;
	/**
	 * 医生另存模板名字
	 */
	private String templateName;
	/**
	 * 共享范围（取码表BLGXFW）
	 */
	private String gxfw;
	/**
	 * 科室id
	 */
	private String deptId;
	/**
	 * 病历模板id
	 */
	private String classifyCode;
	/**
	 * 医生另存模板（json格式内容）
	 */
	private Object emrJson;
	/**
	 * 医生另存模板blob格式内容
	 */
	private Object nrHtml;
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
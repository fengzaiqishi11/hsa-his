package cn.hsa.module.emr.emrelementtemplate.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (EmrElementTemplate)实体类
 *
 * @author makejava
 * @since 2020-12-08 15:24:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmrElementTemplateDO extends PageDO implements Serializable {
	private static final long serialVersionUID = 507842761412406892L;
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 医院编码
	 */
	private String hospCode;
	/**
	 * 科室id
	 */
	private String deptId;
	/**
	 * 单项模板名称
	 */
	private String mbName;
	/**
	 * 共享范围（取码表BLGXFW）
	 */
	private String gxfw;
	/**
	 * 元素编码
	 */
	private String elementCode;
	/**
	 * 单项模板内容
	 */
	private String mbnr;
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
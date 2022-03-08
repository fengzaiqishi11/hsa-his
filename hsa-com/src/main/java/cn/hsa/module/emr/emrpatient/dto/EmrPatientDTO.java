package cn.hsa.module.emr.emrpatient.dto;

import cn.hsa.module.emr.emrpatient.entity.EmrPatientDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrpatient.dto
 * @Class_name: EmrPatientDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 11:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrPatientDTO extends EmrPatientDO implements Serializable {
	private static final long serialVersionUID = -1991180396605059238L;

	private Map<String, Object> nrMap;

	// html格式的病历内容
	private String stringHtml;

	private byte[] html;

	// 病历编码
	private String classifyCode;

	// 病历分类名称
	private String classifyName;

	// 是否送审
	private String isAudit;

	// 是否拥有历史记录
	private String haveHistory;

	// 是否常用病历
	private String isCommon;

	// 病历关联的元素集合中元素性别约束与当前患者性别相反的元素集合
	private Map<String, Object> oppositeSexMap;

	// 是否换页打印
	private String isPagePrint;

	private String deptType;  // 当前病人是门诊病人or住院病人， 1：门诊 2：住院
	// liuliyun 20210623
	private Integer validTime; // 电子病历创建时效性

	private String allowEdit; // 编辑 ：1 允许同级编辑；2 只能上级编辑
}

package cn.hsa.module.emr.emrdoctemplate.dto;

import cn.hsa.module.emr.emrdoctemplate.entity.EmrDocTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrdoctemplate.dto
 * @Class_name: EmrDocTemplateDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EmrDocTemplateDTO extends EmrDocTemplateDO implements Serializable {
	private static final long serialVersionUID = -1991180396605069259L;

	// html格式的病历内容
	private String stringHtml;

	private byte[] html;

	private Map<String, Object> nrMap;

	private String emrPatientId;

	private String label;

	// 病历分类编码
	private String classifyCode;
}

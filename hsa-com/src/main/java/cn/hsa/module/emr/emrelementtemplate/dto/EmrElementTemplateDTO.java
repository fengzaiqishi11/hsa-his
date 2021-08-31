package cn.hsa.module.emr.emrelementtemplate.dto;

import cn.hsa.module.emr.emrelementtemplate.entity.EmrElementTemplateDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelementtemplate.dto
 * @Class_name: EmrElementTemplateDTO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 15:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class EmrElementTemplateDTO extends EmrElementTemplateDO implements Serializable {
	private static final long serialVersionUID = -1991180396605069238L;

	private List checkSingleTemplate;

	private String emrPatientId;

	private String elementName;

	private Map<String, Object> nrMap;

	private String emrClassifyTemplateId;
}

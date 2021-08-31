package cn.hsa.module.emr.emrelementtemplate.bo;

import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelementtemplate.bo
 * @Class_name: EmrElementTemplateBO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrElementTemplateBO {

	boolean saveEmrElementTemplate(Map map);

	List<EmrElementTemplateDTO> getEmrElementTemplates(Map map);

	List<EmrElementTemplateDTO> filterNrMap(Map map);
}

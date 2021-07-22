package cn.hsa.module.emr.emrelementtemplate.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelementtemplate.service
 * @Class_name: EmrElementTemplateService
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 15:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(name = "hsa-emr")
public interface EmrElementTemplateService  {

	/**
	 * @Description: 保存医生自定义的单项模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 16:57
	 * @Return
	 */
	@PostMapping("/service/emr/emrElementTemplate/saveEmrElementTemplate")
	WrapperResponse<Boolean> saveEmrElementTemplate(Map map);

	/**
	 * @Description: 根据元素编码与共享范围，查询单项模板的值
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 15:25
	 * @Return
	 */
	@GetMapping("/service/emr/emrElementTemplate/getEmrElementTemplates")
	WrapperResponse<List<EmrElementTemplateDTO>> getEmrElementTemplates(Map map);

	/**
	 * @Description: 需要将当前病历元素抽取到后台，再根据元素的是否单项模板属性过滤元素，再将元素返回给前端
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/14 9:49
	 * @Return
	 */
	@GetMapping("/service/emr/emrElementTemplate/filterNrMap")
	WrapperResponse<List<EmrElementTemplateDTO>> filterNrMap(Map map);
}

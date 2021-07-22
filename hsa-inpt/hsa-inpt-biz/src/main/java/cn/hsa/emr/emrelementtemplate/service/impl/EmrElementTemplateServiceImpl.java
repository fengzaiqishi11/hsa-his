package cn.hsa.emr.emrelementtemplate.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelementtemplate.bo.EmrElementTemplateBO;
import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import cn.hsa.module.emr.emrelementtemplate.service.EmrElementTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrelementtemplate.bo.impl
 * @Class_name: EmrElementTemplateServiceImpl
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 16:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrelementtemplate")
@Slf4j
@Service("emrElementTemplateService_provider")
public class EmrElementTemplateServiceImpl extends HsafService implements EmrElementTemplateService {

	@Resource
	private EmrElementTemplateBO emrElementTemplateBO;

	/**
	 * @Description: 保存医生自定义的单项病历模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 17:06
	 * @Return
	 * @return
	 */
	@Override
	public WrapperResponse<Boolean> saveEmrElementTemplate(Map map) {
		return WrapperResponse.success(emrElementTemplateBO.saveEmrElementTemplate(map));
	}

	/**
	 * @Description: 根据元素编码与共享范围，查询单项模板的值
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 15:25
	 * @Return 
	 */
	@Override
	public WrapperResponse<List<EmrElementTemplateDTO>> getEmrElementTemplates(Map map) {
		return WrapperResponse.success(emrElementTemplateBO.getEmrElementTemplates(map));
	}

	/**
	 * @Description: 需要将当前病历元素抽取到后台，再根据元素的是否单项模板属性过滤元素，再将元素返回给前端
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/14 9:49
	 * @Return
	 */
	@Override
	public WrapperResponse<List<EmrElementTemplateDTO>> filterNrMap(Map map) {
		return WrapperResponse.success(emrElementTemplateBO.filterNrMap(map));
	}
}

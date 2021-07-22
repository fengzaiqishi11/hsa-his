package cn.hsa.module.emr.emrdoctemplate.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrdoctemplate.service
 * @Class_name: EmrDocTemplateService
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/9 8:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(name = "hsa-emr")
public interface EmrDocTemplateService {

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 8:56
	 * @Return
	 */
	@PostMapping("/service/emr/emrDocTemplate/saveEmrDocTemplate")
	WrapperResponse<Boolean> saveEmrDocTemplate(Map map);

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:01
	 * @Return
	 */
	@GetMapping("/service/emr/emrDocTemplate/getEmrDocTemplates")
	WrapperResponse<List<EmrDocTemplateDTO>> getEmrDocTemplates(Map map);

	/**
	 * @Description: 根据主键查询医生自定义另存的病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:45
	 * @Return
	 */
	@GetMapping("/service/emr/emrDocTemplate/selectEmrDocTemplate")
	WrapperResponse<EmrDocTemplateDTO> selectEmrDocTemplate(Map map);


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
	/**
	 * @Description: 查询当前科室可用病历模板，仅根据科室id过滤，用于自定义病历模板维护
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 9:26
	 * @Return
	 */
	@GetMapping("/service/emr/emrDocTemplate/selectClassifyTemplate")
	WrapperResponse<List<TreeMenuNode>> selectClassifyTemplate(Map map);

	/**
	 * @Description: 自定义病历模板新增模板时根据病历模板id查询病历模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 11:45
	 * @Return
	 */
	@PostMapping("/service/emr/emrDocTemplate/getEmrClassifyTemplate")
	WrapperResponse<EmrPatientDTO> getEmrClassifyTemplate(Map map);

	/**
	 * @Description: 根据共享范围查询自定义病历模板集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 15:44
	 * @Return
	 */
	@GetMapping("/service/emr/emrDocTemplate/getCustomClassifyTemplates")
	WrapperResponse<List<TreeMenuNode>> getCustomClassifyTemplates(Map map);

	/**
	 * @Description: 根据自定义模板id查询模板详细信息
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:34
	 * @Return 
	 */
	@GetMapping("/service/emr/emrDocTemplate/getCustomClassifyTemplate")
	WrapperResponse<EmrDocTemplateDTO> getCustomClassifyTemplate(Map map);

	/**
	 * @Description: 根据自定义病历模板id删除自定义模板
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 11:31
	 * @Return 
	 */
	@GetMapping("/service/emr/emrDocTemplate/deleteCustomClassifyTemplate")
	WrapperResponse<Boolean> deleteCustomClassifyTemplate(Map map);

	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能


}

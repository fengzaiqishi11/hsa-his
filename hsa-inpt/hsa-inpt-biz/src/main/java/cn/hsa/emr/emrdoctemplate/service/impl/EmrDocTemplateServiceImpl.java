package cn.hsa.emr.emrdoctemplate.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrdoctemplate.bo.EmrDocTemplateBO;
import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;
import cn.hsa.module.emr.emrdoctemplate.service.EmrDocTemplateService;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrdoctemplate.service.impl
 * @Class_name: EmrDocTemplateServiceImpl
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/9 8:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrdoctemplate")
@Slf4j
@Service("emrDocTemplateService_provider")
public class EmrDocTemplateServiceImpl extends HsafService implements EmrDocTemplateService {

	@Resource
	private EmrDocTemplateBO emrDocTemplateBO;

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 9:05
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveEmrDocTemplate(Map map) {
		EmrDocTemplateDTO dto = MapUtils.get(map, "dto");
		return WrapperResponse.success(emrDocTemplateBO.saveEmrDocTemplate(dto));
	}

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:04
	 * @Return
	 */
	@Override
	public WrapperResponse<List<EmrDocTemplateDTO>> getEmrDocTemplates(Map map) {
		return WrapperResponse.success(emrDocTemplateBO.getEmrDocTemplates(map));
	}

	/**
	 * @Description: 根据主键查询医生自定义另存的病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:46
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrDocTemplateDTO> selectEmrDocTemplate(Map map) {
		return WrapperResponse.success(emrDocTemplateBO.selectEmrDocTemplate(map));
	}

	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
	/**
	 * @Description: 查询当前科室可用病历模板，仅根据科室id过滤，用于自定义病历模板维护
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 9:28
	 * @Return
	 */
	@Override
	public WrapperResponse<List<TreeMenuNode>> selectClassifyTemplate(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrDocTemplateBO.selectClassifyTemplate(emrPatientDTO));
	}

	/**
	 * @Description: 自定义病历模板新增模板时根据病历模板id查询病历模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 11:46
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrPatientDTO> getEmrClassifyTemplate(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrDocTemplateBO.getEmrClassifyTemplate(emrPatientDTO));
	}

	/**
	 * @Description: 根据共享范围查询自定义病历模板集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 15:54
	 * @Return
	 */
	@Override
	public WrapperResponse<List<TreeMenuNode>> getCustomClassifyTemplates(Map map) {
		return WrapperResponse.success(emrDocTemplateBO.getCustomClassifyTemplates(map));
	}

	/**
	 * @Description: 根据id查询自定义模板详细信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:37
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrDocTemplateDTO> getCustomClassifyTemplate(Map map) {
		return WrapperResponse.success(emrDocTemplateBO.getCustomClassifyTemplate(map));
	}

	/**
	 * @Description: 根据自定义病历模板id删除自定义模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 11:32
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> deleteCustomClassifyTemplate(Map map) {
		return WrapperResponse.success(emrDocTemplateBO.deleteCustomClassifyTemplate(map));
	}


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能

}

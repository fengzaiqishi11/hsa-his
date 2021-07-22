package cn.hsa.module.emr.emrdoctemplate.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrdoctemplate.bo
 * @Class_name: EmrDocTemplateBO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 16:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrDocTemplateBO {

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 9:10
	 * @Return
	 */
	boolean saveEmrDocTemplate(EmrDocTemplateDTO dto);

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:05
	 * @Return
	 */
	List<EmrDocTemplateDTO> getEmrDocTemplates(Map map);

	/**
	 * @Description: 根据医生自定义模板的id查询自定义病历文档模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:48
	 * @Return
	 */
	EmrDocTemplateDTO selectEmrDocTemplate(Map map);

	/**
	 * @Description: 查询当前科室可用的病历模板，不需要根据病历分类的属性过滤模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:39
	 * @Return
	 */
	List<TreeMenuNode> selectClassifyTemplate(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询具体的模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:39
	 * @Return
	 */
	EmrPatientDTO getEmrClassifyTemplate(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据共享范围查询已经创建的模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:39
	 * @Return
	 */
	List<TreeMenuNode> getCustomClassifyTemplates(Map map);

	/**
	 * @Description: 根据id查询自定义模板详细信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:38
	 * @Return
	 */
	EmrDocTemplateDTO getCustomClassifyTemplate(Map map);

	/**
	 * @Description: 根据自定义病历模板id删除自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 11:33
	 * @Return
	 */
	boolean deleteCustomClassifyTemplate(Map map);
}

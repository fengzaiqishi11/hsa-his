package cn.hsa.module.emr.emrdoctemplate.dao;

import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrdoctemplate.dao
 * @Class_name: EmrDocTemplateDAO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 16:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrDocTemplateDAO {

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 9:36
	 * @Return
	 */
	boolean saveEmrDocTemplate(EmrDocTemplateDTO emrDocTemplateDTO);

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:06
	 * @Return
	 */
	List<EmrDocTemplateDTO> getEmrDocTemplates(Map map);

	/**
	 * @Description: 根据主键查询医生自定义的病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 13:50
	 * @Return
	 */
	EmrDocTemplateDTO selectEmrDocTemplate(Map map);

	/**
	 * @Description: 新建病历时，通过emrPatinetId查询不到病历编码，此时页面可以直接获取病历文档编码
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/28 20:34
	 * @Return
	 */
	List<EmrDocTemplateDTO> getEmrDocTemplatesForXJ(Map map);

	/**
	 * @Description: 根据主键更新自定义模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 10:34
	 * @Return
	 */
	int updateEmrDocTemplate(EmrDocTemplateDTO dto);

	int deleteEmrDocTemplate(Map map);

}

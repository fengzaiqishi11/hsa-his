package cn.hsa.module.emr.emrelementtemplate.dao;

import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelementtemplate.dao
 * @Class_name: EmrElementTemplateDAO
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrElementTemplateDAO {

	/**
	 * @Description: 保存医生自定义的单项模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 19:14
	 * @Return
	 */
	int saveEmrElementTemplate(@Param("list") List<EmrElementTemplateDTO> list);

	/**
	 * @Description: 查询单项模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 19:17
	 * @Return
	 */
	List<EmrElementTemplateDTO> getEmrElementTemplates(Map map);

	void deleteEmrElementTemplates(Map map);

	/**
	 * @Description: 根据患者病历id，查询当前病历在模板中定义的元素信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/14 9:59
	 * @Return
	 */
	List<EmrElementTemplateDTO> getBlmbUseElements(Map map);

	/**
	 * @Description: 新建的病历还未保存的情况下查询病历使用到的元素
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/20 12:23
	 * @Return
	 */
	List<EmrElementTemplateDTO> getBlmbUseElementsByTemplateId(Map map);
}

package cn.hsa.emr.emrelementtemplate.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.emr.emrelementtemplate.bo.EmrElementTemplateBO;
import cn.hsa.module.emr.emrelementtemplate.dao.EmrElementTemplateDAO;
import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrelementtemplate.bo.impl
 * @Class_name: EmrElementTemplateBOImpl
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 16:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrElementTemplateBOImpl extends HsafBO implements EmrElementTemplateBO {

	@Resource
	private EmrElementTemplateDAO emrElementTemplateDAO;

	/**
	 * @Description: 保存医生自定义的单项模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 17:01
	 * @Return
	 */
	@Override
	public boolean saveEmrElementTemplate(Map map) {
		List<EmrElementTemplateDTO> list = MapUtils.get(map, "date");
		String hosp_code = MapUtils.get(map, "hospCode");
		String deptId = MapUtils.get(map, "deptId");
		String userId = MapUtils.get(map, "userId");
		String userName = MapUtils.get(map, "userName");
		for (EmrElementTemplateDTO dto : list) {
			dto.setId(SnowflakeUtils.getId());
			dto.setHospCode(hosp_code);
			dto.setDeptId(deptId);
			dto.setCrteId(userId);
			dto.setCrteName(userName);
			dto.setCrteTime(DateUtils.getNow());
		}
		return emrElementTemplateDAO.saveEmrElementTemplate(list) > 0;
	}

	/**
	 * @Description: 根据元素编码与共享范围，查询单项模板的值
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 15:28
	 * @Return
	 */
	@Override
	public List<EmrElementTemplateDTO> getEmrElementTemplates(Map map) {
		return emrElementTemplateDAO.getEmrElementTemplates(map);
	}

	/**
	 * @Description: 根据患者病历id查询病历类型，并查询出当前病历使用的元素，根据病历元素的【是否单项模板】属性来过滤页面传入的元素集合，即：
	 * 页面元素集合只保留元素【是否单项模板】属性为是的元素
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/14 9:50
	 * @Return
	 */
	@Override
	public List<EmrElementTemplateDTO> filterNrMap(Map map) {
		List<EmrElementTemplateDTO> list = new ArrayList<>();
		// 1、根据病历编码，查询到当前病历模板使用到的元素集合
		EmrElementTemplateDTO emrElementTemplateDTO = MapUtils.get(map,"dto");
		if (emrElementTemplateDTO != null && emrElementTemplateDTO.getEmrPatientId() != null) {
			map.put("emrPatientId", emrElementTemplateDTO.getEmrPatientId());
			List<EmrElementTemplateDTO> dtoList = emrElementTemplateDAO.getBlmbUseElements(map);
			if (dtoList == null || dtoList.size() == 0) {
				dtoList = emrElementTemplateDAO.getBlmbUseElementsByTemplateId(map);
			}
			// 将dtoList转换为map集合
			Map<String, Object> mbMap = new HashMap<>();
			for (EmrElementTemplateDTO templateDTO : dtoList) {
				mbMap.put(templateDTO.getElementCode(), templateDTO.getElementName());
			}
			// 2、遍历获取到的当前病历的元素集合，如果匹配到病历模板使用到的元素的【是否单项模板】为否则舍弃当前元素。
			if (mbMap != null) {
				Map<String, Object> nrMap = emrElementTemplateDTO.getNrMap();
				for (Map.Entry<String, Object> entry : nrMap.entrySet()) {
					String key = entry.getKey();
					if (mbMap.containsKey(key)) {
						EmrElementTemplateDTO dto = new EmrElementTemplateDTO();
						dto.setElementName((String) mbMap.get(key));
						dto.setElementCode(key);
						dto.setMbnr((String) nrMap.get(key));
						list.add(dto);
					}
				}
			}

		}
		return list;
	}
}

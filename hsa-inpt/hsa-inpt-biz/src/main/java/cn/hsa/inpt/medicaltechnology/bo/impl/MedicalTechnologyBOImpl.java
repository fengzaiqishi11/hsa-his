package cn.hsa.inpt.medicaltechnology.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.medicaltechnology.bo.MedicalTechnologyBO;
import cn.hsa.module.inpt.medicaltechnology.dao.MedicalTechnologyDAO;
import cn.hsa.module.inpt.medicaltechnology.dto.MedicalTechnologyDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.medicaltechnology.bo.impl
 * @Class_name: MedicalTechnologyBOImpl
 * @Describe: 住院医技确费实现层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class MedicalTechnologyBOImpl extends HsafBO implements MedicalTechnologyBO {

	@Resource
	private MedicalTechnologyDAO medicalTechnologyDAO;
	/**
	 * @Description: 查询住院医技需要确费信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/12 11:39
	 * @Return
	 */
	@Override
	public PageDTO getLISorPASSNeedConfirmCost(Map<String, Object> map) {
		MedicalTechnologyDTO medicalTechnologyDTO = MapUtils.get(map, "medicalTechnologyDTO");
		if ("".equals(medicalTechnologyDTO.getBabyId())) {
			medicalTechnologyDTO.setBabyId(null);
		} else if(medicalTechnologyDTO.getBabyId() != null ) {
			medicalTechnologyDTO.setKeyword(null);
		}
		PageHelper.startPage(medicalTechnologyDTO.getPageNo(),medicalTechnologyDTO.getPageSize());

		return PageDTO.of(medicalTechnologyDAO.getLISorPASSNeedConfirmCost(medicalTechnologyDTO));
	}

	/**
	 * @Description: 确认医技收费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 22:42
	 * @Return
	 */
	@Override
	public boolean saveMwdicalTechnologyConfirmCost(Map<String, Object> map) {
		map.put("crteTime", DateUtils.getNow());
		List<String> list = MapUtils.get(map, "ids");
		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			ids.append("'");
			ids.append(list.get(i));
			ids.append("'");
			if ((i+1) != list.size()) {
				ids.append(",");
			}
		}
		map.put("costIds", ids.toString());
		return medicalTechnologyDAO.saveMwdicalTechnologyConfirmCost(map) > 0;
	}

	/**
	 * @Description: 取消医技确费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 9:57
	 * @Return
	 */
	@Override
	public boolean updateMedicalTechnologyConfirmCost(Map<String, Object> map) {
		List<String> list = MapUtils.get(map, "ids");
		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			ids.append("'");
			ids.append(list.get(i));
			ids.append("'");
			if ((i+1) != list.size()) {
				ids.append(",");
			}
		}
		map.put("costIds", ids.toString());
		return medicalTechnologyDAO.updateMedicalTechnologyConfirmCost(map) > 0;
	}
}

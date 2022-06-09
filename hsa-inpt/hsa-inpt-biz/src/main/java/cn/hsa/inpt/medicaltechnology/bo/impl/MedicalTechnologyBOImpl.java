package cn.hsa.inpt.medicaltechnology.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.medicaltechnology.bo.MedicalTechnologyBO;
import cn.hsa.module.inpt.medicaltechnology.dao.MedicalTechnologyDAO;
import cn.hsa.module.inpt.medicaltechnology.dto.MedicalTechnologyDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
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
		//"1"为门诊查询医技
		if("1".equals(medicalTechnologyDTO.getClinicType())){
			return PageDTO.of(medicalTechnologyDAO.getOutPutLISorPASSNeedConfirmCost(medicalTechnologyDTO));
		}
		return PageDTO.of(medicalTechnologyDAO.getLISorPASSNeedConfirmCost(medicalTechnologyDTO));
	}
	/**
	 * @Description: 查询康复理疗需要确费的数据
	 * @Param:
	 * @Author: yuelong.chen
	 * @Email: yuelong.chen@powersi.com.cn
	 * @Date 2022/4/25 16:54
	 * @Return
	 */
	@Override
	public PageDTO getRecoveryConfirmCost(Map<String, Object> map) {
		MedicalTechnologyDTO medicalTechnologyDTO = MapUtils.get(map, "medicalTechnologyDTO");
		if ("".equals(medicalTechnologyDTO.getBabyId())) {
			medicalTechnologyDTO.setBabyId(null);
		} else if(medicalTechnologyDTO.getBabyId() != null ) {
			medicalTechnologyDTO.setKeyword(null);
		}
		PageHelper.startPage(medicalTechnologyDTO.getPageNo(),medicalTechnologyDTO.getPageSize());
		return PageDTO.of(medicalTechnologyDAO.getRecoveryConfirmCost(medicalTechnologyDTO));
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
		if (ListUtils.isEmpty(list)){
			throw new AppException("费用id不能为空");
		}
		// 后端对确费状态再做一次校验，根据费用id查询确费状态，查看是否都是未确费状态
		int alreadyCount = medicalTechnologyDAO.getAlreadyCostByCostIds((String) map.get("hospCode"),list);
		if (alreadyCount > 0){
			throw new AppException("只能选未确费数据");
		}
		//"1"为门诊确费
		if(!MapUtils.isEmpty(map,"clinicType") &&"1".equals(MapUtils.get(map, "clinicType").toString())){
			return medicalTechnologyDAO.saveOutPtMwdicalTechnologyConfirmCost(map) > 0;
		}
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
		if (ListUtils.isEmpty(list)){
			throw new AppException("费用id不能为空");
		}
		//"1"为门诊取消
		if(!MapUtils.isEmpty(map,"clinicType")&&"1".equals(MapUtils.get(map, "clinicType").toString())){
			return medicalTechnologyDAO.updateOutPtMedicalTechnologyConfirmCost(map) > 0;
		}
		return medicalTechnologyDAO.updateMedicalTechnologyConfirmCost(map) > 0;
	}
}

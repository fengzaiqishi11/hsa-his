package cn.hsa.inpt.medicaltechnology.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.medicaltechnology.bo.MedicalTechnologyBO;
import cn.hsa.module.inpt.medicaltechnology.service.MedicalTechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.medicaltechnology.service.impl
 * @Class_name: MedicalTechnologyServiceImpl
 * @Describe: 医技确费服务层
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Service("medicalTechnologyService_provider")
@Slf4j
@HsafRestPath("/service/inpt/medicaltechnology")
public class MedicalTechnologyServiceImpl extends HsafService implements MedicalTechnologyService {

	@Resource
	private MedicalTechnologyBO medicalTechnologyBO;

	/**
	 * @Description: 查询住院医技需要确费的信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/12 11:32
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> getLISorPASSNeedConfirmCost(Map<String, Object> map) {
		return WrapperResponse.success(medicalTechnologyBO.getLISorPASSNeedConfirmCost(map));
	}

	/**
	 * @Description: 保存医技确费数据，确认需要收费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 22:40
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveMwdicalTechnologyConfirmCost(Map<String, Object> map) {
		return WrapperResponse.success(medicalTechnologyBO.saveMwdicalTechnologyConfirmCost(map));
	}

	/**
	 * @Description: 取消医技确费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 9:56
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateMedicalTechnologyConfirmCost(Map<String, Object> map) {
		return WrapperResponse.success(medicalTechnologyBO.updateMedicalTechnologyConfirmCost(map));
	}
}

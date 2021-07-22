package cn.hsa.module.inpt.medicaltechnology.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.medicaltechnology.service
 * @Class_name: MedicalTechnologyService
 * @Describe: 住院医技确费
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-inpt")
public interface MedicalTechnologyService {

	/**
	 * @Description: 查询lis或pass需要确费信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/12 11:27
	 * @Return
	 */
	@GetMapping("/service/inpt/medicaltechnology/getLISorPASSNeedConfirmCost")
	WrapperResponse<PageDTO> getLISorPASSNeedConfirmCost(Map<String, Object> map);

	/**
	 * @Description: 保存医技确费数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 22:39
	 * @Return
	 */
	@PostMapping("/service/inpt/medicaltechnology/saveMwdicalTechnologyConfirmCost")
	WrapperResponse<Boolean> saveMwdicalTechnologyConfirmCost(Map<String, Object> map);

	/**
	 * @Description: 取消医技确费
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 9:53
	 * @Return 
	 */
	@PostMapping("/service/inpt/medicaltechnology/updateMedicalTechnologyConfirmCost")
	WrapperResponse<Boolean> updateMedicalTechnologyConfirmCost(Map<String, Object> map);
}

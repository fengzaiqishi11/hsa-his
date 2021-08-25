package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.medicaltechnology.dto.MedicalTechnologyDTO;
import cn.hsa.module.inpt.medicaltechnology.service.MedicalTechnologyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: MedicalTechnologyController
 * @Describe: 住院医技确费
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/12 11:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RequestMapping("/web/inpt/medicaltechnology")
@Slf4j
@RestController
public class MedicalTechnologyController extends BaseController {

	@Resource
	private MedicalTechnologyService medicalTechnologyService_consumer;
	/**
	 * @Description: 查询lis或pass需要确费信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/12 11:24
	 * @Return
	 */
	@GetMapping("/getLISorPASSNeedConfirmCost")
	public WrapperResponse getLISorPASSNeedConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO, HttpServletRequest req, HttpServletResponse res){
		SysUserDTO sysUserDTO = getSession(req, res);
		Map<String, Object> map = new HashMap<>();
		medicalTechnologyDTO.setHospCode(sysUserDTO.getHospCode());
		medicalTechnologyDTO.setExecDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		medicalTechnologyDTO.setTypeCode("12");
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("medicalTechnologyDTO", medicalTechnologyDTO);
		return medicalTechnologyService_consumer.getLISorPASSNeedConfirmCost(map);
	}

	/**
	 * @Description: 查询lis需要确费的数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 23:54
	 * @Return
	 */
	@GetMapping("/getLISNeedConfirmCost")
	public WrapperResponse getLISNeedConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO, HttpServletRequest req, HttpServletResponse res){
		SysUserDTO sysUserDTO = getSession(req, res);
		Map<String, Object> map = new HashMap<>();
		medicalTechnologyDTO.setHospCode(sysUserDTO.getHospCode());
		medicalTechnologyDTO.setExecDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		medicalTechnologyDTO.setTypeCode("4");
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("medicalTechnologyDTO", medicalTechnologyDTO);
		return medicalTechnologyService_consumer.getLISorPASSNeedConfirmCost(map);
	}

	@GetMapping("/getPACSNeedConfirmCost")
	public WrapperResponse getPACSNeedConfirmCost(MedicalTechnologyDTO medicalTechnologyDTO, HttpServletRequest req, HttpServletResponse res){
		SysUserDTO sysUserDTO = getSession(req, res);
		Map<String, Object> map = new HashMap<>();
		medicalTechnologyDTO.setHospCode(sysUserDTO.getHospCode());
		medicalTechnologyDTO.setExecDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		medicalTechnologyDTO.setTypeCode("5");
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("medicalTechnologyDTO", medicalTechnologyDTO);
		return medicalTechnologyService_consumer.getLISorPASSNeedConfirmCost(map);
	}

	/**
	 * @Description: 保存医技确费
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/13 21:10
	 * @Return
	 */
	@PostMapping("/saveMwdicalTechnologyConfirmCost")
	public WrapperResponse<Boolean> saveMwdicalTechnologyConfirmCost(@RequestBody MedicalTechnologyDTO medicalTechnologyDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map<String, Object> map = new HashMap<>();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("crteId", sysUserDTO.getId());
		map.put("crteName", sysUserDTO.getName());
		map.put("ids", medicalTechnologyDTO.getIds());
		return medicalTechnologyService_consumer.saveMwdicalTechnologyConfirmCost(map);
	}

	/**
	 * @Description: 取消医技确费
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 9:47
	 * @Return 
	 */
	@PostMapping("/updateMedicalTechnologyConfirmCost")
	public WrapperResponse<Boolean> updateMedicalTechnologyConfirmCost(@RequestBody MedicalTechnologyDTO medicalTechnologyDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map<String, Object> map = new HashMap<>();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("ids", medicalTechnologyDTO.getIds());
		return medicalTechnologyService_consumer.updateMedicalTechnologyConfirmCost(map);
	}

}

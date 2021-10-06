package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import cn.hsa.module.emr.emrquality.service.EmrQualityDataRulesService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrQualityDataRulesController
 * @Describe: TODO
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/9/27 10:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrQualityDataRules")
@Slf4j
public class EmrQualityDataRulesController extends BaseController {

	@Resource
	private EmrQualityDataRulesService emrQualityDataRulesService_consumer;

	/**
	 * @Description: 新增病历数据控制规则记录
	 * @param  emrQualityDataRulesDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/26 20:50
	 * @Return WrapperResponse<Boolean>
	 */
	@PostMapping("/insertEmrQualityDataRules")
	public WrapperResponse<Boolean> insertEmrQualityDataRules(@RequestBody EmrQualityDataRulesDTO emrQualityDataRulesDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityDataRulesDTO.setCrteId(sysUserDTO.getId());
		emrQualityDataRulesDTO.setCrteName(sysUserDTO.getName());
		emrQualityDataRulesDTO.setCrteTime(DateUtils.getNow());
		emrQualityDataRulesDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityDataRulesDTO", emrQualityDataRulesDTO);
		return emrQualityDataRulesService_consumer.insertEmrQualityDataRules(map);
	}

	/**
	 * @Description: 更新病历数据控制规则
	 * @param  emrQualityDataRulesDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/26 20:50
	 * @Return WrapperResponse<Boolean>
	 */
	@PostMapping("/updateEmrQualityDataRules")
	public WrapperResponse<Boolean> updateEmrQualityDataRules(@RequestBody EmrQualityDataRulesDTO emrQualityDataRulesDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrQualityDataRulesDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityDataRulesDTO", emrQualityDataRulesDTO);
		return emrQualityDataRulesService_consumer.updateEmrQualityDataRules(map);
	}

	/**
	 * @Description: 病历数据控制规则作废
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/27 8:55
	 * @Return
	 */
	@GetMapping("/updateEmrQualityDataInvalid")
	public WrapperResponse<Boolean> updateEmrQualityDataInvalid(String id, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("id",id);
		return emrQualityDataRulesService_consumer.updateEmrQualityDataInvalid(map);
	}

	/**
	 * @Description: 查询病历数据控制规则
	 * @param emrQualityDataRulesDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/27 9:01
	 * @Return WrapperResponse<PageDTO>
	 */
	@GetMapping("/queryEmrQualityDataRulesList")
	public WrapperResponse<List<EmrQualityDataRulesDTO>> queryEmrQualityDataRulesList(EmrQualityDataRulesDTO emrQualityDataRulesDTO , HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityDataRulesDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityDataRulesDTO",emrQualityDataRulesDTO);
		return emrQualityDataRulesService_consumer.queryEmrQualityDataRulesList(map);
	}

	/**
	 * @Description: 根据id查询病历数据控制规则
	 * @param emrQualityDataRulesDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/27 10:04
	 * @Return WrapperResponse<EmrQualityDataRulesDTO>
	 */
	@GetMapping("/queryEmrQualityDataRulesById")
	public WrapperResponse<EmrQualityDataRulesDTO> queryEmrQualityDataRulesById(EmrQualityDataRulesDTO emrQualityDataRulesDTO , HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityDataRulesDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityDataRulesDTO",emrQualityDataRulesDTO);
		return emrQualityDataRulesService_consumer.queryEmrQualityDataRulesById(map);
	}

}

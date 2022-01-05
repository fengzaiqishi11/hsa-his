package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import cn.hsa.module.emr.emrquality.service.EmrQualityAgingService;
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
 * @Class_name: EmrArchiveLoggingController
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/27 20:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrQualityAging")
@Slf4j
public class EmrQualityAgingController extends BaseController {

	@Resource
	private EmrQualityAgingService emrQualityAgingService_consumer;

	/**
	 * @Description: 新增病历时效提醒记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/24 10:56
	 * @Return
	 */
	@PostMapping("/insertEmrQualityAging")
	public WrapperResponse<Boolean> insertEmrQualityAging(@RequestBody EmrQualityAgingDTO emrQualityAgingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityAgingDTO.setCrteId(sysUserDTO.getId());
		emrQualityAgingDTO.setCrteName(sysUserDTO.getName());
		emrQualityAgingDTO.setCrteTime(DateUtils.getNow());
		emrQualityAgingDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityAgingDTO", emrQualityAgingDTO);
		return emrQualityAgingService_consumer.insertEmrQualityAging(map);
	}

	/**
	 * @Description: 更新病历时效提醒记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/24 10:56
	 * @Return
	 */
	@PostMapping("/updateEmrQualityAging")
	public WrapperResponse<Boolean> updateEmrQualityAging (@RequestBody EmrQualityAgingDTO emrQualityAgingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrQualityAgingDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityAgingDTO", emrQualityAgingDTO);
		return emrQualityAgingService_consumer.updateEmrQualityAging(map);
	}

	/**
	 * @Description: 删除时效质控记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/24 11:34
	 * @Return
	 */
	@PostMapping("/deleteEmrQualityAging")
	public WrapperResponse<Boolean> deleteEmrQualityAging(@RequestBody List<String> ids, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("ids",ids);
		return emrQualityAgingService_consumer.deleteEmrQualityAging(map);
	}

	/**
	 * @Description: 查询病历模板列表
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/24 11:34
	 * @Return
	 */
	@GetMapping("/queryEmrTemplateList")
	public WrapperResponse<List<Map>> queryEmrTemplateList(HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		return emrQualityAgingService_consumer.queryEmrTemplateList(map);
	}

	/**
	 * @Description: 查询病历时效质控list
	 * @param emrQualityAgingDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/24 16:37
	 * @Return
	 */
	@GetMapping("/queryEmrQualityList")
	public WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityList(EmrQualityAgingDTO emrQualityAgingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityAgingDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityAgingDTO",emrQualityAgingDTO);
		return emrQualityAgingService_consumer.queryEmrQualityList(map);
	}

	/**
	 * @Description: 通过id查询病历时效质控list
	 * @param emrQualityAgingDTO
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/09/24 16:37
	 * @Return
	 */
	@GetMapping("/queryEmrQualityListById")
	public WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityListById(EmrQualityAgingDTO emrQualityAgingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrQualityAgingDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrQualityAgingDTO",emrQualityAgingDTO);
		return emrQualityAgingService_consumer.queryEmrQualityListById(map);
	}


	/**
	 * @Description: 查询病历未书写提醒信息
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/11/29 9:17
	 * @Return WrapperResponse<Boolean>
	 */
	@PostMapping("/queryUnwriteEmrList")
	public WrapperResponse<Boolean> queryUnwriteEmrList(HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map=new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("crteName",sysUserDTO.getName());
		map.put("crteId",sysUserDTO.getId());
		return emrQualityAgingService_consumer.queryUnwriteEmrList(map);
	}

}

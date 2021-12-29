package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;
import cn.hsa.module.emr.emrarchivelogging.service.EmrArchiveLoggingService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
@RequestMapping("/web/emr/emrArchiveLogging")
@Slf4j
public class EmrArchiveLoggingController extends BaseController {

	@Resource
	private EmrArchiveLoggingService emrArchiveLoggingService_consumer;

	/**
	 * @Description: 新增病人归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 21:07
	 * @Return
	 */
	@PostMapping("/insertEmrArchiveLogging")
	public WrapperResponse<Boolean> insertEmrArchiveLogging(@RequestBody EmrArchiveLoggingDTO emrArchiveLoggingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrArchiveLoggingDTO.setArchiveUserId(sysUserDTO.getId());
		emrArchiveLoggingDTO.setArchiveName(sysUserDTO.getName());
		emrArchiveLoggingDTO.setArchiveTime(DateUtils.getNow());
		emrArchiveLoggingDTO.setHospCode(sysUserDTO.getHospCode());
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrArchiveLoggingDTO", emrArchiveLoggingDTO);
		return emrArchiveLoggingService_consumer.insertEmrArchiveLogging(map);
	}

	/**
	 * @Description: 更新病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 21:10
	 * @Return
	 */
	@PostMapping("/updateEmrArchiveLogging")
	public WrapperResponse<Boolean> updateEmrArchiveLogging (@RequestBody EmrArchiveLoggingDTO emrArchiveLoggingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrArchiveLoggingDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrArchiveLoggingDTO", emrArchiveLoggingDTO);
		return emrArchiveLoggingService_consumer.updateEmrArchiveLogging(map);
	}

	/**
	 * @Description: 查询所有住院病人信息，带出归档状态
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 20:37
	 * @Return
	 */
	@GetMapping("/getZYEmrFilePatients")
	public WrapperResponse<PageDTO> getZYEmrFilePatients(EmrArchiveLoggingDTO emrArchiveLoggingDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrArchiveLoggingDTO.setHospCode(sysUserDTO.getHospCode());
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			emrArchiveLoggingDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("emrArchiveLoggingDTO", emrArchiveLoggingDTO);
		return emrArchiveLoggingService_consumer.getZYEmrFilePatients(map);
	}

	/**
	 * @Description: 病人出院7天自动归档记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/06/25 11:37
	 * @Return
	 */
	@PostMapping("/outHospInsertEmrArchiveLogging")
	public WrapperResponse<Boolean> outHospInsertEmrArchiveLogging(@RequestBody Map map, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		map.put("hospCode",sysUserDTO.getHospCode());
		return emrArchiveLoggingService_consumer.outHospInsertEmrArchiveLogging(map);
	}

	/**
	 * @Description: 查询病人出院7天未归档信息
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/11/25 9:12
	 * @Return
	 */
	@PostMapping("/insertOutHospEmrArchiveLogging")
	public WrapperResponse<Boolean> insertOutHospEmrArchiveLogging(HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map=new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		map.put("crteName",sysUserDTO.getName());
		map.put("crteId",sysUserDTO.getId());
		return emrArchiveLoggingService_consumer.insertOutHospEmrArchiveLogging(map);
	}

}

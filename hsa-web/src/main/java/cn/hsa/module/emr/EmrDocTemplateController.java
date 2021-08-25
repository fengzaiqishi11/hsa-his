package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;
import cn.hsa.module.emr.emrdoctemplate.service.EmrDocTemplateService;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
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
 * @Class_name: EmrDocTemplateController
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/9 10:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrDocTemplate")
@Slf4j
public class EmrDocTemplateController extends BaseController {

	@Resource
	private EmrDocTemplateService emrDocTemplateService_consumer;

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 10:44
	 * @Return
	 */
	@PostMapping("/saveEmrDocTemplate")
	public WrapperResponse<Boolean> saveEmrDocTemplate(@RequestBody EmrDocTemplateDTO emrDocTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		emrDocTemplateDTO.setHospCode(sysUserDTO.getHospCode());
		emrDocTemplateDTO.setCrteId(sysUserDTO.getId());
		emrDocTemplateDTO.setCrteName(sysUserDTO.getName());
		emrDocTemplateDTO.setCrteTime(DateUtils.getNow());
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			emrDocTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("dto", emrDocTemplateDTO);
		return emrDocTemplateService_consumer.saveEmrDocTemplate(map);
	}

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 10:52
	 * @Return
	 */
	@GetMapping("/getEmrDocTemplates")
	public WrapperResponse<List<EmrDocTemplateDTO>> getEmrDocTemplates(String emrPatientId, String gxfw, String emrClassifyCode, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("crteId", sysUserDTO.getId());
		map.put("emrPatientId", emrPatientId);
		map.put("gxfw", gxfw);
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		  map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		map.put("emrClassifyCode", emrClassifyCode);
		return emrDocTemplateService_consumer.getEmrDocTemplates(map);
	}

	/**
	 * @Description: 根据医生自定义模板的id查询自定义病历文档模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:31
	 * @Return
	 */
	@GetMapping("/selectEmrDocTemplate")
	public WrapperResponse<EmrDocTemplateDTO> selectEmrDocTemplate (String id, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("id", id);
		return emrDocTemplateService_consumer.selectEmrDocTemplate(map);
	}



	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
	/**
	 * @Description: 查询当前科室可用病历模板，仅根据科室id过滤，用于自定义病历模板维护
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 9:37
	 * @Return
	 */
	@GetMapping("/selectClassifyTemplate")
	public WrapperResponse<List<TreeMenuNode>> selectClassifyTemplate(HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		EmrPatientDTO emrPatientDTO = new EmrPatientDTO();
	     if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			 emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		 }
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setCrteId(sysUserDTO.getId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrDocTemplateService_consumer.selectClassifyTemplate(map);
	}

	/**
	 * @Description: 自定义病历模板新增模板时根据病历模板id查询病历模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 10:55
	 * @Return
	 */
	@PostMapping("/getEmrClassifyTemplate")
	public WrapperResponse<EmrPatientDTO> getEmrClassifyTemplate(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		return emrDocTemplateService_consumer.getEmrClassifyTemplate(map);
	}

	/**
	 * @Description: 根据共享范围查询自定义模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 15:10
	 * @Return
	 */
	@GetMapping("/getCustomClassifyTemplates")
	public WrapperResponse<List<TreeMenuNode>> getCustomClassifyTemplates(String gxfw, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("gxfw", gxfw);
		map.put("userId", sysUserDTO.getId());
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		return emrDocTemplateService_consumer.getCustomClassifyTemplates(map);
	}

	/**
	 * @Description: 根据自定义病历模板id查询病历模板具体信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:25
	 * @Return
	 */
	@GetMapping("/getCustomClassifyTemplate")
	public WrapperResponse<EmrDocTemplateDTO> getCustomClassifyTemplate(String id, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("id", id);
		return emrDocTemplateService_consumer.getCustomClassifyTemplate(map);
	}

	/**
	 * @Description: 根据自定义病历模板id删除自定义模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 11:31
	 * @Return
	 */
	@GetMapping("/deleteCustomClassifyTemplate")
	public WrapperResponse<Boolean> deleteCustomClassifyTemplate(String id, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("id", id);
		map.put("crteId", sysUserDTO.getId());
		return emrDocTemplateService_consumer.deleteCustomClassifyTemplate(map);
	}
	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
}

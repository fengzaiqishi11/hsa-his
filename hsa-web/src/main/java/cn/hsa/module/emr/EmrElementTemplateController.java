package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import cn.hsa.module.emr.emrelementtemplate.service.EmrElementTemplateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrElementTemplateController
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/8 16:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrElementTemplate")
public class EmrElementTemplateController extends BaseController {

	@Resource
	private EmrElementTemplateService emrElementTemplateService_consumer;

	/**
	 * @Description: 保存医生自定义的单项模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/8 16:53
	 * @Return
	 */
	@PostMapping("/saveEmrElementTemplate")
	public WrapperResponse<Boolean> saveEmrElementTemplate(@RequestBody List<EmrElementTemplateDTO> list, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode",sysUserDTO.getHospCode());
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		map.put("userId", sysUserDTO.getId());
		map.put("userName", sysUserDTO.getName());
		map.put("date", list);
		return emrElementTemplateService_consumer.saveEmrElementTemplate(map);
	}

	/**
	 * @Description: 根据元素编码与共享范围查询单项模板值
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 15:14
	 * @Return
	 */
	@GetMapping("/getEmrElementTemplates")
	public WrapperResponse<List<EmrElementTemplateDTO>> getEmrElementTemplates(String elementCode, String gxfw, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("elementCode", elementCode);
		map.put("gxfw", gxfw);
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		map.put("userId", sysUserDTO.getId());
		return emrElementTemplateService_consumer.getEmrElementTemplates(map);
	}

	/**
	 * @Description: 另存单项模板时，另存页面的数据需要交由后台过滤，因为某个元素修改了【是否单项模板】属性后不会自动更新病历模板文件
	 * 从而导致另存时不能及时展现效果，即：需要将当前病历元素抽取到后台，再根据元素的是否单项模板属性过滤元素，再将元素返回给前端
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/14 9:16
	 * @Return 
	 */
	@PostMapping("/filterNrMap")
	public WrapperResponse<List<EmrElementTemplateDTO>> filterNrMap(@RequestBody EmrElementTemplateDTO emrElementTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		map.put("dto", emrElementTemplateDTO);
		return emrElementTemplateService_consumer.filterNrMap(map);
	}
}

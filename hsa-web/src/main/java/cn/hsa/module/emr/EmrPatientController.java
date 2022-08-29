package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientReportFormDTO;
import cn.hsa.module.emr.emrpatient.entity.EmrPatientPrintDO;
import cn.hsa.module.emr.emrpatient.service.EmrPatientService;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
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
 * @Class_name: EmrPatientController
 * @Describe: 电子病历病人所拥有的病历，用于查看当前病人已经创建了哪些病历，减少查询病人病历结构化数据表次数，提高速度
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 14:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrPatient")
@Slf4j
public class EmrPatientController extends BaseController {

	@Resource
	private EmrPatientService emrPatientService_consumer;

	@Resource
	private OutptProfileFileService OutptProfileFileService_consumer;

	/**
	 * @Description: 1、查询可以新建的病历列表
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 14:54
	 * @Return
	 */
	@GetMapping("/getEmrClassifyTemplateDTO")
	public WrapperResponse<List<TreeMenuNode>> getEmrClassifyTemplateDTO(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());

		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
			emrPatientDTO.setDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());  // 当前登录科室是门诊还是住院  1：门诊  2 住院
		}
		// 职工类型为护士
	    if (StringUtils.isNotEmpty(sysUserDTO.getWorkTypeCode())
		     && (Constants.RYZW_NURSE.RYZW_NURSE_01.equals(sysUserDTO.getWorkTypeCode())
	         || Constants.RYZW_NURSE.RYZW_NURSE_02.equals(sysUserDTO.getWorkTypeCode())
		     ||Constants.RYZW_NURSE.RYZW_NURSE_03.equals(sysUserDTO.getWorkTypeCode())
		     ||Constants.RYZW_NURSE.RYZW_NURSE_04.equals(sysUserDTO.getWorkTypeCode())
		     ||Constants.RYZW_NURSE.RYZW_NURSE_05.equals(sysUserDTO.getWorkTypeCode()))){
	    	emrPatientDTO.setIsNurse("1");
	    }else {
			emrPatientDTO.setIsNurse("0");
		}
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getEmrClassifyTemplateDTO(map);
	}

	/**
	 * @Description: 2、新增界面点击确认新增时调用，此时病人病历记录只在emr_patient表中写一条数据，其他表不添加数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 19:21
	 * @Return
	 */
	@PostMapping("/insertPatient")
	public WrapperResponse<String> insertPatient(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		// 新增病历,设置创建人信息，当前登录科室信息
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
	    if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		    emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
	    }
		emrPatientDTO.setCrteId(sysUserDTO.getId());
		emrPatientDTO.setCrteName(sysUserDTO.getName());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.insertEmrPatient(map);
	}

	/**
	 * @Description: 新建病历文档，获取模板与病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/18 15:51
	 * @Return
	 */
	@PostMapping("/newDZBLWord")
	public WrapperResponse<EmrPatientDTO> newDZBLWord(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("hospName", sysUserDTO.getHospName());
		map.put("emrPatientDTO", emrPatientDTO);
		map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
		return emrPatientService_consumer.newDZBLWord(map);
	}

	/**
	 * @Description: 3、查询当前病人已经拥有的病历树;   入参需要  医院编码，就诊ID  两个字段信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 20:41
	 * @Return
	 */
	@GetMapping("/getPatientTree")
	public WrapperResponse<List<TreeMenuNode>> getPatientTree(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
	    if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		    emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getEmrPatientsTreeByVisitId(map);
	}

	/**
	 * @Description: 4、查看病人病历具体内容，注意：当查询不到记录时，需要加载模板病历
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 20:49
	 * @Return
	 */
	@GetMapping("/getPatientHtml")
	public WrapperResponse<EmrPatientDTO> getPatientHtml(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("hospName", sysUserDTO.getHospName());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getEmrPatientDTO(map);
	}

	/**
	 * @Description: 查询病人基础信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/28 21:51
	 * @Return
	 */
	@GetMapping("/getPatientNrInfo")
	public WrapperResponse<EmrPatientDTO> getPatientNrInfo(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
	   if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		   emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
	   }
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("hospName", sysUserDTO.getHospName());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getPatientNrInfo(map);
	}

	/**
	 * @Description: 在保存病历前执行，校验病历必填元素是否已经填值
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/16 14:52
	 * @Return
	 */
	@PostMapping("/checkRequired")
	public WrapperResponse<String> checkRequired(@RequestBody EmrElementDTO emrElementDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("noValue", emrElementDTO.getNoValue());
		return emrPatientService_consumer.checkRequired(map);
	}

	/**
	 * @Description: 5、书写，修改病历内容后保存病历 需要操作三张表，emr_patient,emr_patient_record,emr_patient_html
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 17:26
	 * @Return
	 */
	@PostMapping("/saveEmrPatientRecordHtml")
	public WrapperResponse<Boolean> saveEmrPatientRecordHtml(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
	    if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		    emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
	    }
		emrPatientDTO.setCrteId(sysUserDTO.getId());
		emrPatientDTO.setCrteName(sysUserDTO.getName());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.saveEmrPatientRecordHtml(map);
	}

	/**
	 * @Description: 6、查询出同一份病历最近一次相比于上次有修改的地方
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:33
	 * @Return
	 */
	@GetMapping("/getEmrPatientDifferen")
	public WrapperResponse<Map> getEmrPatientDifferen(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getEmrPatientDTOHistory(map);
	}

	/**
	 * @Description: 7、更新病历送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:55
	 * @Return
	 */
	@PostMapping("/updateEmrPatientSpecify")
	public WrapperResponse<Boolean> updateEmrPatientSpecify(@RequestBody EmrPatientHtmlDTO emrPatientHtmlDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		//BeanUtils.copyProperties(emrPatientDTO,EmrPatientHtmlDTO);
		emrPatientHtmlDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientHtmlDTO", emrPatientHtmlDTO);
		return emrPatientService_consumer.updateEmrPatientSpecify(map);
	}

	/**
	 * @Description: 8、查询当前登录科室的病人数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 21:23
	 * @Return
	 */
	@GetMapping("/getInptVisits")
	public WrapperResponse<PageDTO> getInptVisits(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		if (sysUserDTO.getLoginBaseDeptDTO().getTypeCode() != null && sysUserDTO.getLoginBaseDeptDTO().getTypeCode().equals("1")) { // 门诊科室获取门诊病人
			OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
			outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
			outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
	/*		//新增当前登录医生过滤条件-caoliang 2021/6/17
			outptVisitDTO.setDoctorName(userName);*/
			outptVisitDTO.setName(inptVisitDTO.getName());
			outptVisitDTO.setAge(inptVisitDTO.getAge());
			outptVisitDTO.setPageNo(inptVisitDTO.getPageNo());
			outptVisitDTO.setPageSize(inptVisitDTO.getPageSize());
			outptVisitDTO.setRyEndTime(inptVisitDTO.getRyEndTime());
			outptVisitDTO.setRyStartTime(inptVisitDTO.getRyStartTime());
			Map map = new HashMap();
			map.put("hospCode", sysUserDTO.getHospCode());
			map.put("outptVisitDTO", outptVisitDTO);
			return emrPatientService_consumer.getOutptVisit(map);
		} else { // 住院科室获取住院病人
			inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
			inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
			inptVisitDTO.setZgDoctorId(sysUserDTO.getId()); // liuliyun 20210629 住院病历添加主管医生过滤
			if (StringUtils.isEmpty(inptVisitDTO.getColumnName())) {
				inptVisitDTO.setColumnName("outTime");
			}
			if (StringUtils.isEmpty(inptVisitDTO.getSortName())) {
				inptVisitDTO.setSortName("asc");
			}
/*			//新增当前登录医生过滤条件 -caoliang 2021/6/17
			inptVisitDTO.setDoctorName(userName);*/
			Map map = new HashMap();
			map.put("hospCode", sysUserDTO.getHospCode());
			map.put("inptVisitDTO", inptVisitDTO);
			return emrPatientService_consumer.getInptVisit(map);
		}
	}

	/**
	 * @Description: 8.1、查询当医院的病人数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020年12月30日21:11:39
	 * @Return
	 */
	@GetMapping("/getAllHosptallInptVisits")
	public WrapperResponse<PageDTO> getAllHosptallInptVisits(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
		inptVisitDTO.setZgDoctorId(sysUserDTO.getId());
		if (StringUtils.isEmpty(inptVisitDTO.getColumnName())) {
			inptVisitDTO.setColumnName("outTime");
		}
		if (StringUtils.isEmpty(inptVisitDTO.getSortName())) {
			inptVisitDTO.setSortName("asc");
		}
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("inptVisitDTO", inptVisitDTO);
		return emrPatientService_consumer.getInptVisit(map);
	}


	/**
	 * @Description: 8、查询当前登录科室的病人数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 21:23
	 * @Return
	 */
	@GetMapping("/getInptOpreationVisits")
	public WrapperResponse<PageDTO> getInptOpreationVisits(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
	   if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		   inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
	   }
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("inptVisitDTO", inptVisitDTO);
		return emrPatientService_consumer.getInptOpreationVisits(map);
	}

	/**
	 * @Description: 检查病人病历是否可以编辑
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 16:21
	 * @Return
	 */
	@GetMapping("/checkPatientIsEdit")
	public WrapperResponse<Boolean> checkPatientIsEdit(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
	    if (sysUserDTO.getLoginBaseDeptDTO() != null) {
		emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		// 职工类型为护士
		if (StringUtils.isNotEmpty(sysUserDTO.getWorkTypeCode())
				&& (Constants.RYZW_NURSE.RYZW_NURSE_01.equals(sysUserDTO.getWorkTypeCode())
				|| Constants.RYZW_NURSE.RYZW_NURSE_02.equals(sysUserDTO.getWorkTypeCode())
				||Constants.RYZW_NURSE.RYZW_NURSE_03.equals(sysUserDTO.getWorkTypeCode())
				||Constants.RYZW_NURSE.RYZW_NURSE_04.equals(sysUserDTO.getWorkTypeCode())
				||Constants.RYZW_NURSE.RYZW_NURSE_05.equals(sysUserDTO.getWorkTypeCode()))){
			emrPatientDTO.setIsNurse("1");
		}else {
			emrPatientDTO.setIsNurse("0");
		}
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("userId", sysUserDTO.getId());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.checkPatientIsEdit(map);
	}

	/**
	 * @Description: 根据病历的创建人id，查询与创建人在同一个部门的上级人员名单
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:20
	 * @Return
	 */
	@GetMapping("/getDeptSupperUsers")
	public WrapperResponse<List<SysUserDTO>> getDeptSupperUsers(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setDeptId(sysUserDTO.getBaseDeptDTO().getId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.getDeptSupperUsers(map);
	}

	/**
	 * @Description: 保存送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:31
	 * @Return
	 */
	@PostMapping("/updatePatientAndRecord")
	public WrapperResponse<Boolean> updatePatientAndRecord(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setReviewId(sysUserDTO.getId()); // 送审人id
		emrPatientDTO.setReviewName(sysUserDTO.getName());  // 送审人姓名
		emrPatientDTO.setReviewTime(DateUtils.getNow());  // 送审时间
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.updatePatientAndRecord(map);
	}

	/**
	 * @Description: 保存审核记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 10:09
	 * @Return
	 */
	@PostMapping("/saveExamine")
	public WrapperResponse<Boolean> saveExamine(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setAuditId(sysUserDTO.getId());
		emrPatientDTO.setAuditName(sysUserDTO.getName());
		emrPatientDTO.setAuditTime(DateUtils.getNow());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.saveExamine(map);
	}

	/**
	 * @Description: 检查当前用户对当前病历是否有权限审核
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/19 14:49
	 * @Return
	 */
	@GetMapping("checkUserIsAudit")
	public WrapperResponse<Boolean> checkUserIsAudit(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		map.put("userId", sysUserDTO.getId());
		return emrPatientService_consumer.checkUserIsAudit(map);
	}

	/**
	 * @Description: 匹配登录用户账号密码
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/21 10:41
	 * @Return
	 */
	@PostMapping("/matchUserPassword")
	public WrapperResponse<Boolean> matchUserPassword(@RequestBody String passWord, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("userId", sysUserDTO.getId());
		map.put("passWord", passWord);
		return emrPatientService_consumer.matchUserPassword(map);
	}

	/**
	 * @Description: 检查当前病人是否已经创建了日常病程记录，供页面判断“病程”按钮是否可用
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 11:29
	 * @Return
	 */
	@GetMapping("/checkDailyCourseOfDisease")
	public WrapperResponse<Boolean> checkDailyCourseOfDisease(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		map.put("userId", sysUserDTO.getId());
		return emrPatientService_consumer.checkDailyCourseOfDisease(map);
	}

	/**
	 * @Description: 查询当前病人的所有“日常病程记录”
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 14:10
	 * @Return
	 */
	@GetMapping("/selectEmrPatientDTOs")
	public WrapperResponse<List<EmrPatientDTO>> selectEmrPatientDTOs(EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.selectEmrPatientDTOs(map);
	}

	/**
	 * @Description: 根据病人档案id查询病人住院次数
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/11/2 10:45
	 * @Return
	 */
	@GetMapping("/getOutptProfileFile")
	public WrapperResponse<OutptProfileFileExtendDTO> getOutptProfileFile(OutptProfileFileExtendDTO extendDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		extendDTO.setHospCode(sysUserDTO.getHospCode());
		return OutptProfileFileService_consumer.getExtendByProfileId(extendDTO);
	}

	/**
	 * @Description: 根据病历主键删除病历文档，已归档病人、审核完成病历不能删除，且只有创建者才能删除
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/21 8:33
	 * @Return
	 */
	@PostMapping("/deleteEmrPatient")
	public WrapperResponse<Boolean> deleteEmrPatient(@RequestBody EmrPatientDTO emrPatientDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			emrPatientDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		emrPatientDTO.setHospCode(sysUserDTO.getHospCode());
		emrPatientDTO.setCrteId(sysUserDTO.getId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientDTO", emrPatientDTO);
		return emrPatientService_consumer.deleteEmrPatient(map);
	}

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	@GetMapping("/getPatientInHospVisitId")
	public WrapperResponse<List<InptVisitDTO>> getPatientInHospVisitId(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("inptVisitDTO", inptVisitDTO);
		return emrPatientService_consumer.getPatientInHospVisitId(map);
	}

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	@GetMapping("/getPatientOutHospVisitId")
	public WrapperResponse<List<OutptVisitDTO>> getPatientOutHospVisitId(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("outptVisitDTO", outptVisitDTO);
		return emrPatientService_consumer.getPatientOutHospVisitId(map);
	}

	/**
	 * @Description:  电子病历上传医保
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	@GetMapping("/uploadEmr")
	public WrapperResponse<Boolean> uploadEmr(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		if (StringUtils.isEmpty(inptVisitDTO.getVisitId())){
			throw new AppException("请选择要上传病历的病人");
		}
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    inptVisitDTO.setId(inptVisitDTO.getVisitId());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("inptVisitDTO", inptVisitDTO);
		return emrPatientService_consumer.uploadEmr(map);
	}

	/**
	 * @Description: 电子病历数据抓取
	 * @Param:
	 * @Author: 廖继广
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2022/01/06 14:32
	 * @Return
	 */
	@GetMapping("/updateHisEmrJosnInfo")
		public WrapperResponse updateHisEmrJosnInfo(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		if (StringUtils.isEmpty(inptVisitDTO.getVisitId())){
			throw new AppException("请选择要上传病历的病人");
		}
		if (sysUserDTO.getLoginBaseDeptDTO() != null) {
			inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
		}
		inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("inptVisitDTO", inptVisitDTO);
		return WrapperResponse.success(emrPatientService_consumer.updateHisEmrJosnInfo(map));
	}



	/**
	 * @Description: 记录病历打印次数
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/10 11：03
	 * @Return
	 */
	@PostMapping("/insertEmrPrint")
	public WrapperResponse<Boolean> insertEmrPrint(@RequestBody EmrPatientPrintDO emrPatientPrintDO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		emrPatientPrintDO.setHospCode(sysUserDTO.getHospCode());
		emrPatientPrintDO.setPrintId(sysUserDTO.getId());
		emrPatientPrintDO.setPrintName(sysUserDTO.getName());
		Map map = new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("emrPatientPrintDO", emrPatientPrintDO);
		return emrPatientService_consumer.insertEmrPrint(map);
	}

	/**
	 * @Description: 查询病历报表
	 * @Param: map
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/13 15：15
	 * @Return
	 */
	@GetMapping("/queryPatientEmrReportForm")
	public WrapperResponse<PageDTO> queryPatientEmrReportForm(EmrPatientReportFormDTO reportFormDTO, HttpServletRequest req, HttpServletResponse res) {
		SysUserDTO sysUserDTO = getSession(req, res);
		reportFormDTO.setHospCode(sysUserDTO.getHospCode());
		Map map= new HashMap();
		map.put("hospCode", sysUserDTO.getHospCode());
		map.put("reportFormDTO",reportFormDTO);
		return emrPatientService_consumer.queryPatientEmrReportForm(map);
	}
}

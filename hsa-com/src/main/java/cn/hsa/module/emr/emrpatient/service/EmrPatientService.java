package cn.hsa.module.emr.emrpatient.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import com.github.pagehelper.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrpatient.service
 * @Class_name: EmrPatientService
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 13:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(name = "hsa-emr")
public interface EmrPatientService {

	/**
	 * @Description: 1、新建病人病历时，加载当前病人可以创建的病历文档分类
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 14:30
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getEmrClassifyTemplateDTO")
	WrapperResponse<List<TreeMenuNode>> getEmrClassifyTemplateDTO(Map map);

	/**
	 * @Description: 2、保存新增的病人病历记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 20:07
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/insertEmrPatient")
	WrapperResponse<String> insertEmrPatient(Map map);

	/**
	 * @Description: 3、根据就诊ID查询当前就诊ID拥有的病历文档
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 21:07
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getEmrPatientsTreeByVisitId")
	WrapperResponse<List<TreeMenuNode>> getEmrPatientsTreeByVisitId(Map map);

	/**
	 * @Description:  4、查询单个病历信息,包含完整的html片段
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 20:27
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getEmrPatientDTO")
	WrapperResponse<EmrPatientDTO> getEmrPatientDTO(Map map);

	/**
	 * @Description: 5、书写，修改病历内容后保存病历
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 17:13
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/saveEmrPatientRecordHtml")
	WrapperResponse<Boolean> saveEmrPatientRecordHtml(Map map);


	/**
	 * @Description: 6、查看最近两次修改记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 15:24
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getEmrPatientDTOHistory")
	WrapperResponse<Map> getEmrPatientDTOHistory(Map map);

	/**
	 * @Description: 7、更新病历送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:54
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/updateEmrPatientSpecify")
	WrapperResponse<Boolean> updateEmrPatientSpecify(Map map);

	@GetMapping("/service/emr/emrPatient/getInptVisit")
	WrapperResponse<PageDTO> getInptVisit(Map map);

	/**
	 * @Description: 根据门诊科室id查询门诊病人信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/2 14:51
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getOutptVisit")
	WrapperResponse<PageDTO> getOutptVisit(Map map);

	/**
	 * @Description: 检查病历是否可以编辑
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 16:22
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/checkPatientIsEdit")
	WrapperResponse<Boolean> checkPatientIsEdit(Map map);

	/**
	 * @Description: 根据病历的创建人，查询与创建人同部门的上一级领导
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:14
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getDeptSupperUsers")
	WrapperResponse<List<SysUserDTO>> getDeptSupperUsers(Map map);

	/**
	 * @Description: 保存送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:27
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/updatePatientAndRecord")
	WrapperResponse<Boolean> updatePatientAndRecord(Map map);

	/**
	 * @Description: 保存审核记录，审核意见
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 10:24
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/saveExamine")
	WrapperResponse<Boolean> saveExamine(Map map);

	/**
	 * @Description: 检查当前用户对当前病历是否有权限审核
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/19 14:51
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/checkUserIsAudit")
	WrapperResponse<Boolean> checkUserIsAudit(Map map);

	/**
	 * @Description: 匹配登录用户账号密码
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 11:42
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/matchUserPassword")
	WrapperResponse<Boolean> matchUserPassword(Map map);

	/**
	 * @Description: 查询当前病人是否已经创建了首次日常病程，只有创建了首次日常病程才能创建日常病程，且查看完整病程时需要有首次日常病程
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 11:43
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/checkDailyCourseOfDisease")
	WrapperResponse<Boolean> checkDailyCourseOfDisease(Map map);

	/**
	 * @Description: 查询当前病人所有的日常病程记录集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 14:12
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/selectEmrPatientDTOs")
	WrapperResponse<List<EmrPatientDTO>> selectEmrPatientDTOs(Map map);

	/**
	 * @Description: 校验病历中保存时没有值的元素是否有必填元素
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/16 15:04
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/checkRequired")
	WrapperResponse<String> checkRequired(Map map);

	/**
	 * @Description: 新建病历，获取病历模板与病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/18 15:52
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/newDZBLWord")
	WrapperResponse<EmrPatientDTO> newDZBLWord(Map map);

	/**
	 * @Description: 查看有手术申请的患者的病历信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/20 16:38
	 * @Return
	 */
	@GetMapping("/service/emr/emrPatient/getInptOpreationVisits")
	WrapperResponse<PageDTO> getInptOpreationVisits(Map map);

	/**
	 * @Description: 根据病历主键删除病历文档，已归档病人、审核完成病历不能删除，且只有创建者才能删除
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/21 8:48
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/deleteEmrPatient")
	WrapperResponse<Boolean> deleteEmrPatient(Map map);

	/**
	 * @Description: 查询患者基础信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/28 21:52
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/getPatientNrInfo")
	WrapperResponse<EmrPatientDTO> getPatientNrInfo(Map map);


	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/getPatientInHospVisitId")
	WrapperResponse<List<InptVisitDTO>> getPatientInHospVisitId(Map map);

	/**
	 * @Description: 查询患者就诊次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/getPatientOutHospVisitId")
	WrapperResponse<List<OutptVisitDTO>> getPatientOutHospVisitId(Map map);

	/**
	 * @Description: 上传电子病历
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/8/22 9:27
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/uploadEmr")
	WrapperResponse<Boolean> uploadEmr(Map map);


	/**
	 * @Description: 记录病人病历打印次数
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/10 9:10
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/insertEmrPrint")
	WrapperResponse<Boolean> insertEmrPrint(Map map);

	/**
	 * @Description: 查询病历报表
	 * @Param: map
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/13 15：07
	 * @Return
	 */
	@PostMapping("/service/emr/emrPatient/queryPatientEmrReportForm")
	WrapperResponse<PageDTO> queryPatientEmrReportForm(Map map);

	/**
	 * @Description: 电子病历数据抓取
	 * @Param:
	 * @Author: 廖继广
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2022/01/06 14:32
	 * @Return
	 */
	Map<String, Object> updateHisEmrJosnInfo(Map map);

	/**
	 * 根据模板id获取电子病历模板
	 * @param map
	 * @Author 医保开发二部-湛康
	 * @Date 2022-09-07 20:03
	 * @return cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO
	 */
  EmrPatientDTO getEmrTemplateHtmlByTemplateId(Map map);
}

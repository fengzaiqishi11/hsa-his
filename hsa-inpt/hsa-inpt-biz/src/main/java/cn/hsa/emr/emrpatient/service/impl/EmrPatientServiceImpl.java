package cn.hsa.emr.emrpatient.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrpatient.bo.EmrPatientBO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.entity.EmrPatientPrintDO;
import cn.hsa.module.emr.emrpatient.service.EmrPatientService;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrpatient.service.impl
 * @Class_name: EmrPatientServiceImpl
 * @Describe: 电子病历病人所拥有的病历记录
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 14:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrpatient")
@Slf4j
@Service("emrPatientService_provider")
public class EmrPatientServiceImpl extends HsafService implements EmrPatientService {

	@Resource
	private EmrPatientBO emrPatientBO;

	/**
	 * @Description: 1、新建病人病历时，加载当前病人可以创建的病历文档分类
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 14:30
	 * @Return
	 */
	@Override
	public WrapperResponse<List<TreeMenuNode>> getEmrClassifyTemplateDTO(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.getEmrClassifyTemplateDTO(emrPatientDTO));
	}

	/**
	 * @Description: 2、保存新增病人病历数据,需要校验病人的病历是否已经归档
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 21:20
	 * @Return
	 */
	@Override
	public WrapperResponse<String> insertEmrPatient(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.insertEmrPatient(emrPatientDTO));
	}

	/**
	 * @Description: 3、根据就诊ID查询当前就诊ID所拥有的所有病历，并以树形结构展示
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 11:48
	 * @Return
	 */
	@Override
	public WrapperResponse<List<TreeMenuNode>> getEmrPatientsTreeByVisitId(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		List<TreeMenuNode> list = emrPatientBO.getEmrPatientsTreeByVisitId(emrPatientDTO);
		return WrapperResponse.success(list);
	}

	/**
	 * @Description: 4、查询单个病人病历，展示病历详细信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/23 8:58
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrPatientDTO> getEmrPatientDTO(Map map) {
		EmrPatientDTO emrPatientDTO = emrPatientBO.getEmrPatientDTO(map);
		return WrapperResponse.success(emrPatientDTO);
	}

	/**
	 * @Description: 5、书写，修改病历内容后保存病历
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 17:21
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveEmrPatientRecordHtml(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.saveEmrPatientRecordHtml(emrPatientDTO));
	}

	/**
	 * @Description: 6、查询出同一份病历最近两次有差别的地方
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:30
	 * @Return
	 */
	@Override
	public WrapperResponse<Map> getEmrPatientDTOHistory(Map map) {
		EmrPatientDTO emrPatientDTO  = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.getEmrPatientDTOHistory(emrPatientDTO));
	}

	/**
	 * @Description: 7、更新病历送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:55
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateEmrPatientSpecify(Map map) {
		EmrPatientHtmlDTO emrPatientHtmlDTO = MapUtils.get(map, "emrPatientHtmlDTO");
		return WrapperResponse.success(emrPatientBO.updateEmrPatientSpecify(emrPatientHtmlDTO));
	}

	/**
	 * @Description: 查询当前科室的所有住院病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/28 15:05
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> getInptVisit(Map map) {
		InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
		return WrapperResponse.success(emrPatientBO.getInptVisit(inptVisitDTO));
	}

	/**
	 * @Description: 查询当前科室的所有门诊病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/2 14:53
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> getOutptVisit(Map map) {
		OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
		return WrapperResponse.success(emrPatientBO.getOuptVisit(outptVisitDTO));
	}

	/**
	 * @Description: 检查病人病历是否可以编辑
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 16:25
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> checkPatientIsEdit(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		String userId = MapUtils.get(map, "userId");
		return WrapperResponse.success(emrPatientBO.checkPatientIsEdit(emrPatientDTO, userId));
	}

	/**
	 * @Description: 根据病历的创建人，查询与创建人同部门的上一级人员名单
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:15
	 * @Return 
	 */
	@Override
	public WrapperResponse<List<SysUserDTO>> getDeptSupperUsers(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.getDeptSupperUsers(emrPatientDTO));
	}

	/**
	 * @Description: 保存送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:30
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updatePatientAndRecord(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.updatePatientAndRecord(emrPatientDTO));
	}

	/**
	 * @Description: 保存审核记录，意见
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 10:25
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveExamine(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.saveExamine(emrPatientDTO));
	}

	/**
	 * @Description: 检查当前用户对当前病历是否有审核权限
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/19 14:53
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> checkUserIsAudit(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		String userId = MapUtils.get(map, "userId");
		return WrapperResponse.success(emrPatientBO.checkUserIsAudit(emrPatientDTO, userId));
	}

	/**
	 * @Description: 匹配当前登录用户的密码
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/21 10:42
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> matchUserPassword(Map map) {
		return WrapperResponse.success(emrPatientBO.matchUserPassword(map));
	}

	/**
	 * @Description: 查询当前病人是否创建了首次病程记录文档
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 11:48
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> checkDailyCourseOfDisease(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.checkDailyCourseOfDisease(emrPatientDTO));
	}

	/**
	 * @Description: 查询当前病人所有日常病程记录集合
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 14:20
	 * @Return 
	 */
	@Override
	public WrapperResponse<List<EmrPatientDTO>> selectEmrPatientDTOs(Map map) {
		EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
		return WrapperResponse.success(emrPatientBO.selectEmrPatientDTOs(emrPatientDTO));
	}

	/**
	 * @Description: 校验病历中保存时没有值的元素是否有必填元素
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/16 15:08
	 * @Return
	 */
	@Override
	public WrapperResponse<String> checkRequired(Map map) {
		return WrapperResponse.success(emrPatientBO.checkRequired(map));
	}

	/**
	 * @Description: 新建病历，获取病历模板与病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/18 15:53
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrPatientDTO> newDZBLWord(Map map) {
		return WrapperResponse.success(emrPatientBO.newDZBLWord(map));
	}

	/**
	 * @Description: 查看有手术申请的患者的病历信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/20 16:38
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> getInptOpreationVisits(Map map) {
		InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
		return WrapperResponse.success(emrPatientBO.getInptOpreationVisits(inptVisitDTO));
	}

	/**
	 * @Description: 根据病历主键删除病历文档，已归档病人、审核完成病历不能删除，且只有创建者才能删除
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/21 8:52
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> deleteEmrPatient(Map map) {
		return WrapperResponse.success(emrPatientBO.deleteEmrPatient(map));
	}

	/**
	 * @Description: 查询患者基础信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/28 21:53
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrPatientDTO> getPatientNrInfo(Map map) {
		return WrapperResponse.success(emrPatientBO.getPatientNrInfo(map));
	}

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	@Override
	public WrapperResponse<List<InptVisitDTO>> getPatientInHospVisitId(Map map) {
		InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
		return WrapperResponse.success(emrPatientBO.getPatientInHospVisitId(inptVisitDTO));
	}

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	@Override
	public WrapperResponse<List<OutptVisitDTO>> getPatientOutHospVisitId(Map map) {
		OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
		return WrapperResponse.success(emrPatientBO.getPatientOutHospVisitId(outptVisitDTO));
	}

	/**
	 * @Description:  电子病历上传医保
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> uploadEmr(Map map) {
		InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
		return WrapperResponse.success(emrPatientBO.uploadEmrInfo(inptVisitDTO));
	}

	/**
	 * @Description: 记录病人病历打印次数
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/10 11:08
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> insertEmrPrint(Map map) {
		EmrPatientPrintDO emrPatientPrintDO = MapUtils.get(map, "emrPatientPrintDO");
		return WrapperResponse.success(emrPatientBO.insertEmrPrint(emrPatientPrintDO));
	}

	/**
	 * @Description: 查询病历报表
	 * @Param: map
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/9/13 15：15
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> queryPatientEmrReportForm(Map map) {
		return WrapperResponse.success(emrPatientBO.queryPatientEmrReportForm(map));
	}

	/**
	 * @param map
	 * @Description: 电子病历数据抓取
	 * @Param:
	 * @Author: 廖继广
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2022/01/06 14:32
	 * @Return
	 */
	@Override
	public Map<String, Object> updateHisEmrJosnInfo(Map map) {
		InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
		return emrPatientBO.updateHisEmrJosnInfo(inptVisitDTO);
	}

	/**
	 * 根据模板id获取电子病历模板
	 * @param map
	 * @Author 医保开发二部-湛康
	 * @Date 2022-09-08 8:36
	 * @return cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO
	 */
  @Override
  public EmrPatientDTO getEmrTemplateHtmlByTemplateId(Map map) {
    EmrPatientDTO emrPatientDTO = MapUtils.get(map, "emrPatientDTO");
    return emrPatientBO.getEmrTemplateHtmlByTemplateId(emrPatientDTO);
  }
}

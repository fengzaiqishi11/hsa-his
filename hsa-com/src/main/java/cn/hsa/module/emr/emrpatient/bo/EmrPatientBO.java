package cn.hsa.module.emr.emrpatient.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.entity.EmrPatientPrintDO;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrpatient.bo
 * @Class_name: EmrPatientBO
 * @Describe: 电子病历病人当前就诊时已经创建的病历，针对病人病历结构化数据存储表的抽取，加快页面展示时数据加载
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 11:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrPatientBO {

	/**
	 * @Description: 1、新建病人病历，获取能够使用的病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 10:47
	 * @Return
	 */
	List<TreeMenuNode> getEmrClassifyTemplateDTO(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description:  2、保存新增的病人病历记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:52
	 * @Return
	 */
	String insertEmrPatient(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 3、根据病人就诊ID查询当前就诊ID所有已经创建的病历，即病人已经创建的病历，多次修改的病历只有一条数据且为最新的数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:23
	 * @Return
	 */
	List<TreeMenuNode> getEmrPatientsTreeByVisitId(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 4、查询病人某份病历记录，对象中包含map形式的病历数据与html格式的病历数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:50
	 * @Return
	 */
	EmrPatientDTO getEmrPatientDTO(Map map);


	/**
	 * @Description: 5、书写，修改病历内容后保存病历
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 17:13
	 * @Return
	 */
	boolean saveEmrPatientRecordHtml(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 6、查看最近两次修改记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 15:24
	 * @Return
	 */
	Map getEmrPatientDTOHistory(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 7、更新病历送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 19:58
	 * @Return
	 */
	boolean updateEmrPatientSpecify(EmrPatientHtmlDTO emrPatientHtmlDTO);

	/**
	 * @Description: 根据科室查询当前科室的病人信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/28 8:53
	 * @Return
	 */
	PageDTO getInptVisit(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 检查病历是否可以编辑
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 16:23
	 * @Return
	 */
	boolean checkPatientIsEdit(EmrPatientDTO emrPatientDTO, String userId);

	/**
	 * @Description: 根据病历的创建人，查询与创建人同部门的上一级领导
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:07
	 * @Return
	 */
	List<SysUserDTO> getDeptSupperUsers(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 保存送审记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 9:29
	 * @Return
	 */
	boolean updatePatientAndRecord (EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 保存审核记录，审核意见
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/16 10:33
	 * @Return
	 */
	boolean saveExamine(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 检查当前用户对当前病历是否有审核权限
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/19 14:56
	 * @Return
	 */
	boolean checkUserIsAudit(EmrPatientDTO emrPatientDTO, String userId);

	/**
	 * @Description: 匹配当前登录用户的密码
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/21 10:43
	 * @Return
	 */
	boolean matchUserPassword(Map map);

	/**
	 * @Description: 查询当前病人是否创建了首次日常病程记录文档
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 11:51
	 * @Return 
	 */
	boolean checkDailyCourseOfDisease(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询当前病人的日常病程记录集合
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 14:23
	 * @Return 
	 */
	List<EmrPatientDTO> selectEmrPatientDTOs(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 校验病历中保存时没有值的元素是否有必填元素
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/16 15:12
	 * @Return
	 */
	String checkRequired(Map map);

	/**
	 * @Description: 新建病历，获取病历模板与病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/18 15:56
	 * @Return
	 */
	EmrPatientDTO newDZBLWord(Map map);

	/**
	 * @Description: 查询有手术申请的患者
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/20 16:40
	 * @Return
	 */
	PageDTO getInptOpreationVisits(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 根据病历主键删除病历文档，已归档病人、审核完成病历不能删除，且只有创建者才能删除
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/21 8:53
	 * @Return
	 */
	boolean deleteEmrPatient(Map map);

	/**
	 * @Description: 查询患者基础信息
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/28 21:55
	 * @Return 
	 */
	EmrPatientDTO getPatientNrInfo(Map map);

	/**
	 * @Description: 查询当前科室门诊病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/2 14:55
	 * @Return
	 */
	PageDTO getOuptVisit(OutptVisitDTO outptVisitDTO);

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	List<InptVisitDTO> getPatientInHospVisitId(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	List<OutptVisitDTO> getPatientOutHospVisitId(OutptVisitDTO outptVisitDTO);

	/**
	 * @Description:  电子病历上传医保
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/6 15:27
	 * @Return
	 */
	Boolean uploadEmrInfo(InptVisitDTO inptVisitDTO);

	Boolean insertEmrPrint(EmrPatientPrintDO emrPatientPrintDO);

	PageDTO queryPatientEmrReportForm(Map map);

	/**
	 * @param inptVisitDTO
	 * @Description: 电子病历数据抓取
	 * @Param:
	 * @Author: 廖继广
	 * @Email: jiguang.liao@powersi.com.cn
	 * @Date 2022/01/06 14:32
	 * @Return
	 */
	Map<String, Object> updateHisEmrJosnInfo(InptVisitDTO inptVisitDTO);

	/**
	 * 根据模板id获取电子病历模板
	 * @param emrPatientDTO
	 * @Author 医保开发二部-湛康
	 * @Date 2022-09-08 8:42
	 * @return cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO
	 */
  EmrPatientDTO getEmrTemplateHtmlByTemplateId(EmrPatientDTO emrPatientDTO);
}

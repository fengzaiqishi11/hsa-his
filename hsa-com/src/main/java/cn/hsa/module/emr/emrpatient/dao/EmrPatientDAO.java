package cn.hsa.module.emr.emrpatient.dao;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelementtemplate.dto.EmrElementTemplateDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientReportFormDTO;
import cn.hsa.module.emr.emrpatient.entity.EmrPatientPrintDO;
import cn.hsa.module.emr.emrpatienthtml.dto.EmrPatientHtmlDTO;
import cn.hsa.module.emr.emrpatientrecord.dto.EmrPatientRecordDTO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrpatient.dao
 * @Class_name: EmrPatientDAO
 * @Describe: 电子病历模块病人当前就诊ID所拥有的的病历数据
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/22 13:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrPatientDAO {

	/**
	 * @Description: 病人新建病历时，加载可创建的病历，性别限制、是否唯一过滤
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/25 13:55
	 * @Return
	 */
	List<TreeMenuNode> getEmrClassifyTemplate(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 新增病人病历记录，新增时需要校验病历是否归档了
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:52
	 * @Return
	 */
	int insertEmrPatient(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 更新病人某份病历记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:48
	 * @Return
	 */
	int updateEmrPatient(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据病人就诊ID查询当前就诊ID所有已经创建的病历，即病人已经创建的病历，多次修改的病历只有一条数据且为最新的数据
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:23
	 * @Return 
	 */
	List<TreeMenuNode> getEmrPatientsTreeByVisitId(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询病人某份病历记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/22 15:50
	 * @Return
	 */
	EmrPatientDTO getEmrPatientDTO(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询指定病历关联的元素信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 11:41
	 * @Return
	 */
	List<EmrElementDTO> getEmrElementDTO(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据就诊ID查询住院病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 13:44
	 * @Return
	 */
	Map getInptVisit(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据就诊ID查询门诊病人基本信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 13:45
	 * @Return
	 */
	Map getOutptVisit(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 更新emr_patient表的审核记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/27 20:15
	 * @Return
	 */
	int updateEmrPatientSpecify(EmrPatientHtmlDTO emrPatientHtmlDTO);

	/**
	 * @Description: 根据就诊id，查询当前就诊id下未审核完成文件数量
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/28 9:01
	 * @Return
	 */
	int selectNoAudit(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询当前科室的所有病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/28 15:10
	 * @Return
	 */
	List<InptVisitDTO> selectInptVisits(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 查询当前科室门诊患者信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/2 15:00
	 * @Return
	 */
	List<OutptVisitDTO> selectOutptVisits(OutptVisitDTO outptVisitDTO);

	/**
	 * @Description: 查询当前医院住院病人中有手术申请的所有病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/28 15:10
	 * @Return
	 */
	List<InptVisitDTO> selectInptOpreationVisits(InptVisitDTO inptVisitDTO);

	/**
	 * @Description: 根据病人病历记录的病历模板id查询模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/12 15:18
	 * @Return
	 */
	EmrClassifyTemplateDTO getEmrClassifyTemplateByTemplateId(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询当前医生同部门的上级医生
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 18:18
	 * @Return
	 */
	List<SysUserDTO> getDeptUsers(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询用户信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 13:50
	 * @Return
	 */
	SysUserDTO getSysUserDTO(Map map);

	/**
	 * @Description: 查询当前病人是否已经创建了首次日常病程记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 13:50
	 * @Return
	 */
	int selectDailyCourseOfDisease(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询当前病人的日常病程记录集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/26 14:26
	 * @Return
	 */
	List<EmrPatientDTO> selectEmrPatientDTOs(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据元素编码，医院编码，元素关联的码表key，查询码表name
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/11/2 20:43
	 * @Return 
	 */
	String selectElementValue(String key, String value, String hospCode);

	/**
	 * @Description: 查询指定元素编码集合中必填属性为是的元素名称
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/16 15:42
	 * @Return
	 */
	List<EmrElementDTO> checkRequired(Map map);

	/**
	 * @Description: 根据病历分类编码与部门编码，获取病历模板信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/18 15:40
	 * @Return
	 */
	EmrPatientDTO getTemplateByCodeAndDept(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据病历主键删除病历
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/21 9:04
	 * @Return
	 */
	int deleteEmrPatient(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据科室id，医院编码，病历分类编码查询病历分类的时间记录项的值
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/22 16:05
	 * @Return
	 */
	String getEmrClassifyRecordTime(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据就诊id查询患者性别
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/23 19:36
	 * @Return
	 */
	String getInptVisitSex(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 患者性别为男性，查出当前病历使用到的元素中性别约束为女性的元素集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/23 19:23
	 * @Return
	 */
	List<EmrElementDTO> selectGirlElementMap(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 患者性别为女性，查出当前病历使用到的元素中性别约束为男性的元素集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/23 19:23
	 * @Return
	 */
	List<EmrElementDTO> selectBoyElementMap(EmrPatientDTO emrPatientDTO);


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能

	/**
	 * @Description: 查询当前科室可用病历模板，仅根据科室id过滤，用于自定义病历模板维护
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 9:49
	 * @Return
	 */
	List<TreeMenuNode> selectClassifyTemplate(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 根据共享范围查询自定义病历模板集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 15:58
	 * @Return
	 */
	List<TreeMenuNode> getCustomClassifyTemplates(Map map);

	/**
	 * @Description: 根据当前登录的科室判断科室是门诊or住院，再查出可用病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/15 21:39
	 * @Return
	 */
	List<TreeMenuNode> getEmrClassifyTemplateMZorZY(EmrPatientDTO emrPatientDTO);


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
	/**
	 * @Description: 查询患者住院次数及visitId
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/7/3 15:27
	 * @Return
	 */
	List<InptVisitDTO> getPatientInHospVisitId(InptVisitDTO inptVisitDTO);

	List<OutptVisitDTO> getPatientOutHospVisitId(OutptVisitDTO outptVisitDTO);

	/**
	 * @Method queryEmrPatientDiagnose
	 * @Desrciption  查询出住院诊断信息
	 * @Param
	 *
	 * @Author fuhui
	 * @Date   2022/2/23 10:21
	 * @Return
	**/
	List<InptDiagnoseDTO> queryEmrPatientDiagnose(Map map);

	/**
	 * @Method queryEmrOperRecordInfo
	 * @Desrciption  查询出住院手术信息
	 * @Param
	 *
	 * @Author fuhui
	 * @Date   2022/2/23 10:22
	 * @Return
	**/
	List<OperInfoRecordDTO> queryEmrOperRecordInfo(Map map);

	EmrPatientRecordDTO queryEmrCourseInfo(Map map);


	EmrPatientRecordDTO queryEmrOutInfo(Map map);

	List<EmrPatientDTO> queryEmrPaitentInfo(Map map);

	int insertEmrPirnt(EmrPatientPrintDO emrPatientPrintDO);

	int updatePatientEmrPrintNum(EmrPatientDTO emrPatientDTO);

	Integer getPrintNum(EmrPatientPrintDO emrPatientPrintDO);

	List<EmrPatientReportFormDTO> queryPatientEmrReportForm(EmrPatientReportFormDTO reportFormDTO);

	String queryDataIsValid(@Param("sql") String sql);

	String queryDataIsExist(@Param("sql") String sql);

	List<EmrQualityDataRulesDTO> queryEmrQualityDataRulesByCode(EmrPatientDTO emrPatientDTO);

	/**
	 * 获取此病人的所有病历JSON串
	 * @param selectMap
	 * @return
	 */
    List<Map<String,String>> queryHisEmrJosnInfo(Map<String, String> selectMap);

	/**
	 * @Description: 查询不同部门得同级别人员信息
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liuliyun@powersi.com
	 * @Date 2021/3/14 11:57
	 * @Return
	 */
	List<SysUserDTO> getDiffDeptUsers(EmrPatientDTO emrPatientDTO);

	/**
	 * @Description: 查询病人的会诊医生
	 * @Param: emrPatientDTO
	 * @Author: liuliyun
	 * @Email: liuliyun@powersi.com
	 * @Date 2022/4/12 09:35
	 * @Return
	 */
	String getConsultationId(EmrPatientDTO emrPatientDTO);

	/**
	 * 根据模板ID获取模板信息
	 * @param selectMap
	 * @Author 医保开发二部-湛康
	 * @Date 2022-08-18 15:12
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
  Map<String,Object> queryHisEmrClassifyInfo(Map<String, String> selectMap);

  /**
   * 查找医保就诊信息
   * @param selectMap
   * @Author 医保开发二部-湛康
   * @Date 2022-08-23 17:27
   * @return java.util.List<cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO>
   */
  InsureEmrUnifiedDTO queryInsureVisitEmrInfo(Map<String, String> selectMap);
}

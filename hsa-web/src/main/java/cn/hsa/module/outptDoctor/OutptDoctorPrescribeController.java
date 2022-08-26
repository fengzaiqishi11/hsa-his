package cn.hsa.module.outptDoctor;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import cn.hsa.module.base.baseoutptexec.service.BaseOutptExecService;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import cn.hsa.module.center.profilefile.service.CenterProfileFileService;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.triage.service.OutptTriageVisitService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Supplier;

/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.service
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块视图控制层
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/outptDoctorPrescribe")
@Slf4j
public class OutptDoctorPrescribeController extends BaseController {


  /**
   * 处方管理dubbo消费者接口
   */
  @Resource
  private OutptDoctorPrescribeService outptDoctorPrescribeService_consumer;
  /** 排队叫号 **/
  @Resource
  private OutptTriageVisitService outptTriageVisitService_consumer;

  /**
   * 个人档案dubbo消费者接口
   */
  @Resource
  private CenterProfileFileService centerProfileFileService_consumer;

  /**
   * 执行科室dubbo消费者接口
   */
  @Resource
  private BaseOutptExecService baseOutptExecService_consumer;

  /**
   * 系统参数服务
   */
  @Resource
  private SysParameterService sysParameterService_consumer;


  /**
   * @Menthod queryPatientByOperType
   * @Desrciption  查询未就诊、已就诊数据
   * @param outptVisitDTO operType:查询类型 1：未就诊 2：就诊
   * @param outptVisitDTO startDate : 开始时间
   * @param outptVisitDTO endDate : 结束时间
   * @param outptVisitDTO keyword : 查询内容
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/queryPatientByOperType")
  public WrapperResponse<List<Map<String, Object>>> queryPatientByOperType(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    //查询类型
    if(StringUtils.isEmpty(outptVisitDTO.getOperType())){
      throw new RuntimeException("查询类型不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //医生
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //就诊科室
    outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //类型 1：未就诊 2：已就诊
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.queryPatientByOperType(paramMap);
  }

  /**
   * @Menthod queryVisit
   * @Desrciption  处方查询
   * @param outptVisitDTO operType:查询类型 3：处方查询
   * @param outptVisitDTO startDate : 开始时间
   * @param outptVisitDTO endDate : 结束时间
   * @param outptVisitDTO keyword : 查询内容 deptId：就诊科室
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return PageDTO
   **/
  @GetMapping("/queryVisit")
  public WrapperResponse<PageDTO> queryVisit(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    //查询类型
    if(StringUtils.isEmpty(outptVisitDTO.getOperType())){
      throw new RuntimeException("查询类型不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    if(StringUtils.isEmpty(outptVisitDTO.getDeptId())){
      outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    }
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //类型 1：未就诊 2：已就诊 3：
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.queryVisit(paramMap);
  }

  /**
   * @Menthod queryMedicalRecord
   * @Desrciption  获取历史病历记录
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/queryMedicalRecord")
  public WrapperResponse<List<Map<String, Object>>> queryMedicalRecord(HttpServletRequest req, HttpServletResponse res){
    //个人档案ID
    String profileId = getParameterError(req,"profileId", "个人档案ID不能为空");
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //个人档案ID
    paramMap.put("profileId", profileId);
    return outptDoctorPrescribeService_consumer.queryMedicalRecord(paramMap);
  }

  /**
   * @Menthod getMedicalRecord
   * @Desrciption  获取病历记录
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getMedicalRecord")
  public WrapperResponse<List<Map<String, Object>>> getMedicalRecord(String visitId, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //就诊ID
    paramMap.put("visitId", visitId);
    return outptDoctorPrescribeService_consumer.getMedicalRecord(paramMap);
  }

  /**
   * @Menthod saveMedicalRecord
   * @Desrciption  保存病历记录
   * @param outptMedicalRecordDTO 病历信息
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/saveMedicalRecord")
  public WrapperResponse<Boolean> saveMedicalRecord(@RequestBody OutptMedicalRecordDTO outptMedicalRecordDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //赋值医院编码
    outptMedicalRecordDTO.setHospCode(sysUserDTO.getHospCode());
    //操作人ID
    outptMedicalRecordDTO.setCrteId(sysUserDTO.getId());
    //操作人姓名
    outptMedicalRecordDTO.setCrteName(sysUserDTO.getName());
    //病历信息
    paramMap.put("outptMedicalRecordDTO", outptMedicalRecordDTO);
    return outptDoctorPrescribeService_consumer.saveMedicalRecord(paramMap);
  }

  /**
   * @Menthod updateVisit
   * @Desrciption  修改个人信息
   * @param outptVisitDTO 就诊信息
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/updateVisit")
  public WrapperResponse<Boolean> updateVisit(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("就诊ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //赋值医院编码
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //就诊信息
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateVisit(paramMap);
  }

  /**
   * @Menthod updateVisitInHospital
   * @Desrciption  保存住院证信息
   * @param outptVisitDTO 就诊信息
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/updateVisitInHospital")
  public WrapperResponse<Boolean> updateVisitInHospital(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("就诊ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //赋值医院编码
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //已开住院证
    outptVisitDTO.setTranInCode("1");
    // 开住院证时间
    outptVisitDTO.setInCertTime(new Date());
    // 就诊医生id
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    // 就诊医生姓名
    outptVisitDTO.setDoctorName(sysUserDTO.getName());
    //就诊信息
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateVisitInHospital(paramMap);
  }

  /**
   * @Menthod updateIsVisit
   * @Desrciption  接诊接口
   * @param outptVisitDTO 就诊信息
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @PostMapping("/updateIsVisit")
  public WrapperResponse<Boolean> updateIsVisit(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("就诊ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //赋值医院编码
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //就诊科室
    outptVisitDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //就诊科室ID
    outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //就诊医生
    outptVisitDTO.setDoctorName(sysUserDTO.getName());
    //就诊医生ID
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //就诊信息
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateIsVisit(paramMap);
  }

  /**
   * @Menthod getPrescribeAllDetail
   * @Desrciption  查询处方信息
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeAllDetail")
  public WrapperResponse<PageDTO> getPrescribeAllDetail(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    Map<String,Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);

    WrapperResponse<PageDTO> pageDTOWrapperResponse = outptDoctorPrescribeService_consumer.getPrescribeAllDetail(map);
    return pageDTOWrapperResponse;
  }

  /**
   * @Method: getPrescribeAllDetail
   * @Description:
   * @Param: [outptPrescribeDTO]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2021/1/14 14:29
   * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/getPrescribeAllDetailBySettleNo")
  public WrapperResponse<List<OutptPrescribeDTO>> getPrescribeAllDetailBySettleNo(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    Map<String,Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.getPrescribeAllDetailBySettleNo(map);
  }

  /**
   * @Menthod getPrescribe
   * @Desrciption  查询处方信息
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribe")
  public WrapperResponse<List<Map<String, Object>>> getPrescribe(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //就诊ID
    paramMap.put("visitId", req.getParameter("visitId"));
    return outptDoctorPrescribeService_consumer.getPrescribe(paramMap);
  }

  /**
   * @Menthod getPrescribeDetail
   * @Desrciption  查询处方明细信息
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeDetail")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetail( HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //处方ID
    paramMap.put("opId", req.getParameter("opId"));
    return outptDoctorPrescribeService_consumer.getPrescribeDetail(paramMap);
  }

  /**
   * @Menthod getPrescribeDetailExt
   * @Desrciption  查询处方明细明细信息
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeDetailExt")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailExt(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //处方明细ID
    paramMap.put("opdId", req.getParameter("opdId"));
    // 处方ID
    paramMap.put("opId",req.getParameter("opId"));
    // 就诊Id
    paramMap.put("visitId",req.getParameter("visitId"));
    return outptDoctorPrescribeService_consumer.getPrescribeDetailExt(paramMap);
  }

  /**
   * @Menthod getPrescribeDetailAll
   * @Desrciption  获取处方明细记录（处方查询使用）
   * @param  outptPrescribeDTO  就诊ID  hospCode ：医院编码
   * @Author zengfeng
   * @Date   2020/10/20 19:46:53
   * @Return cn.hsa.base.PageDTO
   **/
  @GetMapping("/getPrescribeDetailAll")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailAll(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //处方数据
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.getPrescribeDetailAll(paramMap);
  }

  /**
   * @Menthod getVisitById
   * @Desrciption  获取就诊信息（visitId：根据就诊ID获取单条  profileId：根据档案ID获取多条）
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getVisitById")
  public WrapperResponse<List<OutptVisitDTO>> getVisitById(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //就诊ID
    paramMap.put("visitId", req.getParameter("visitId"));
    //档案ID
    paramMap.put("profileId", req.getParameter("profileId"));
    return outptDoctorPrescribeService_consumer.getVisitById(paramMap);
  }

  /**
   * @Menthod getPrescribeCost
   * @Desrciption  opId：处方ID  hospCode ：医院编码
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeCost")
  public WrapperResponse<List<OutptCostDTO>> getPrescribeCost(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //处方ID
    paramMap.put("opId", req.getParameter("opId"));
    //就诊ID
    paramMap.put("visitId", req.getParameter("visitId"));
    //来源ID
    paramMap.put("sourceCode", req.getParameter("sourceCode"));
    paramMap.put("settleCode", req.getParameter("settleStatus"));
    return outptDoctorPrescribeService_consumer.getPrescribeCost(paramMap);
  }

  /**
   * @Menthod queryProfileFile
   * @Desrciption  查询档案信息
   * @param centerProfileFileDTO: keyword 查询条件
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/queryProfileFile")
  public WrapperResponse<PageDTO> queryProfileFile(CenterProfileFileDTO centerProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
    return centerProfileFileService_consumer.queryPage(centerProfileFileDTO);
  }

  /**
   * @Menthod queryClassifyCostList
   * @Desrciption  查询挂号费用信息
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getOutptRegisterDetailDO")
  public WrapperResponse<List<OutptRegisterDetailDto>> getOutptRegisterDetailDO(OutptRegisterDTO outptRegisterDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptRegisterDTO.getCfId())){
      throw new RuntimeException("挂号类别ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptRegisterDTO.setHospCode(sysUserDTO.getHospCode());
    //医院级别
    outptRegisterDTO.setLevelCode(sysUserDTO.getLevelCode());
    //挂号类别费用
    paramMap.put("outptRegisterDTO", outptRegisterDTO);
    return outptDoctorPrescribeService_consumer.getOutptRegisterDetailDO(paramMap);
  }


  /**
   * @Menthod OutptRegisterDTO
   * @Desrciption  直接就诊
   * 1. 直接就诊创建患者档案
   * 2. 新增门诊挂号信息
   * 3. 新增门诊就诊患者信息
   * 4. 新增挂号费用到门诊费用表
   *    1：获取挂号费用明细
   *    2：赋值门诊挂号费用表
   * @param outptRegisterDTO 直接就诊（挂号信息）
   * @Author zengfeng
   * @Date   2020/9/7 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/saveDirectVisit")
  public WrapperResponse<OutptVisitDTO> saveDirectVisit(@RequestBody OutptRegisterDTO outptRegisterDTO, HttpServletRequest req, HttpServletResponse res) {
    if(StringUtils.isEmpty(outptRegisterDTO.getPreferentialTypeId())){
      throw new RuntimeException("优惠类别不能为空");
    }
    if(StringUtils.isEmpty(outptRegisterDTO.getPatientCode())){
      throw new RuntimeException("病人类型ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptRegisterDTO.setHospCode(sysUserDTO.getHospCode());
    //医院级别
    outptRegisterDTO.setLevelCode(sysUserDTO.getLevelCode());
    //登录部门ID
    outptRegisterDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //登录部门
    outptRegisterDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //医生ID
    outptRegisterDTO.setDoctorId(sysUserDTO.getId());
    //医生
    outptRegisterDTO.setDoctorName(sysUserDTO.getName());
    //医生ID
    outptRegisterDTO.setCrteName(sysUserDTO.getName());
    //医生
    outptRegisterDTO.setCrteId(sysUserDTO.getId());
    //挂号信息
    paramMap.put("outptRegisterDTO", outptRegisterDTO);
    // 直接就诊
    return outptDoctorPrescribeService_consumer.saveDirectVisit(paramMap);
  } // End saveDirectVisit

  /**
   * @Menthod getCfData
   * @Desrciption  获取开处方药品、材料医嘱数据
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getCfData")
  public WrapperResponse<PageDTO> getCfData(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //登录科室
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // 是否只能开检查项目
    baseDrugDTO.setOnlyOpenItem(sysUserDTO.getOnlyOpenItem());
    //查询数据
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getCfData(paramMap);
  }
  @PostMapping("/getCfData2")
  public WrapperResponse<PageDTO> getCfData2(@RequestBody BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //登录科室
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // 是否只能开检查项目
    baseDrugDTO.setOnlyOpenItem(sysUserDTO.getOnlyOpenItem());
    //查询数据
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getCfData2(paramMap);
  }
  /**
   * @Menthod OutptRegisterDTO
   * @Desrciption  保存处方信息
   * @param outptPrescribeDTO 处方明细ID
   * @Author zengfeng
   * @Date   2020/9/7 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/savePrescribe")
  public WrapperResponse<Boolean> savePrescribe(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outptPrescribeDTO.getVisitId())) {
      throw new AppException("未获取到患者就诊ID");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map sysParamMap = new HashMap();
    sysParamMap.put("hospCode", sysUserDTO.getHospCode());
    sysParamMap.put("code", "IS_OPEN_MZZY"); // 是否使用门诊中医症候
    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
    if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
      // 中医和西医诊断同时为空
      if (StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())&&StringUtils.isEmpty(outptPrescribeDTO.getTcmDiseaseId())) {
        throw new AppException("诊断不能为空");
      }
    }else {
      if (StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())) {
        throw new AppException("诊断不能为空");
      }
    }
    if (ListUtils.isEmpty(outptPrescribeDTO.getOutptPrescribeDetailsDTOList())) {
      throw new AppException("未获取到处方明细信息");
    }
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    // 医院编码
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    // 医生ID
    outptPrescribeDTO.setDoctorId(sysUserDTO.getId());
    // 医生姓名
    outptPrescribeDTO.setDoctorName(sysUserDTO.getName());
    // 操作科室Id
    outptPrescribeDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // 操作科室
    outptPrescribeDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //处方信息
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    // 保存处方信息
    return outptDoctorPrescribeService_consumer.savePrescribe(paramMap);
  }
  /**
   * @param outptPrescribeDTO
   * @Menthod: queryDrugCount
   * @Desrciption: 获取精麻药品允许时间内开药次数
   * @Author: yuelong.chen
   * @Email: yuelong.chen@powersi.com.cn
   * @Date: 2022-08-23 19:51
   * @Return: List<Map>
   */
  @PostMapping("/queryDrugCount")
  public WrapperResponse<List<Map>> queryDrugCount(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //查询数据
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.queryDrugCount(paramMap);
  }
  /**
   * @Menthod queryOutptPrescribeTempDTO
   * @Desrciption  查询处方模板信息
   * @param outptPrescribeTempDTO 处方模板：operType ：2 个人 1：科室 0 或者 空：全院
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<OutptPrescribeTempDTO>
   **/
  @GetMapping("/queryOutptPrescribeTempDTO")
  public WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeTempDTO.setHospCode(sysUserDTO.getHospCode());
    //操作科室
    outptPrescribeTempDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //操作人
    outptPrescribeTempDTO.setDoctorId(sysUserDTO.getId());
    //查询数据
    paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
    return outptDoctorPrescribeService_consumer.queryOutptPrescribeTempDTO(paramMap);
  }

  /**
   * @Menthod queryOutptPrescribeTempDetails
   * @Desrciption  获取处方模板明细信息
   * @param outptPrescribeTempDTO : 处方模块
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<OutptPrescribeTempDetailDTO>
   **/
  @GetMapping("/queryOutptPrescribeTempDetails")
  public WrapperResponse<List<OutptPrescribeTempDetailDTO>> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeTempDTO.setHospCode(sysUserDTO.getHospCode());
    //登录科室
    outptPrescribeTempDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //查询数据
    paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
    return outptDoctorPrescribeService_consumer.queryOutptPrescribeTempDetails(paramMap);
  }

  /**
   * @Menthod getByCode
   * @Desrciption  获取开处方药品、材料医嘱数据 type=0 没有第二级目录
   * @param  sysCodeDetailDTO
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<SysCodeDetailDTO>
   **/
  @GetMapping("/getByCode")
  public WrapperResponse<List<SysCodeDetailDTO>> getByCode(SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
    //查询数据
    paramMap.put("sysCodeDetailDTO", sysCodeDetailDTO);
    return outptDoctorPrescribeService_consumer.getByCode(paramMap);
  }

  /**
   * @Menthod getByCodeDetail
   * @Desrciption  获取药品、医嘱第二级目录
   * @param sysCodeDetailDTO ： code：查询项目类型  type：类型 upValue:查询值
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<SysCodeDetailDTO>
   **/
  @GetMapping("/getByCodeDetail")
  public WrapperResponse<List<SysCodeDetailDTO>> getByCodeDetail(SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
    //查询数据
    paramMap.put("sysCodeDetailDTO", sysCodeDetailDTO);
    return outptDoctorPrescribeService_consumer.getByCodeDetail(paramMap);
  }

  /**
   * @Menthod getDrugData
   * @Desrciption  获取药品信息
   * @param baseDrugDTO typeCode：查询的具体value， type：0：医嘱 1：药品 2：医技 5:皮试换药
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/getDrugData")
  public WrapperResponse<PageDTO> getDrugData(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //登录科室
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //查询数据
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getDrugData(paramMap);
  }

  /**
   * @Menthod calculateYp
   * @Desrciption  计算剂量、用量、频率、总数量、用药天数
   * @param outptPrescribeDetailsDTO: itemId ：药品ID RateId：频率ID  Dosage：剂量 num：用量
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return Map : jl:剂量  yl:用量 zsl:总数量 yyts:用药天数
   **/
  @GetMapping("/calculateYp")
  public WrapperResponse<Map> calculateYp(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptPrescribeDetailsDTO.getItemId())){
      throw new RuntimeException("项目不能为空");
    }
    if(StringUtils.isEmpty(outptPrescribeDetailsDTO.getNumUnitCode())){
      throw new RuntimeException("使用单位不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeDetailsDTO.setHospCode(sysUserDTO.getHospCode());
    //登录科室
    outptPrescribeDetailsDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //查询数据
    paramMap.put("outptPrescribeDetailsDTO", outptPrescribeDetailsDTO);
    return outptDoctorPrescribeService_consumer.calculateYp(paramMap);
  }

  /**
   * @Menthod deleteOutptPrescribe
   * @Desrciption  删除处方信息
   * @param  outptPrescribeDTO: id ：处方ID
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/deleteOutptPrescribe")
  public WrapperResponse<Boolean> deleteOutptPrescribe(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //查询数据
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    //校验删除权限
    if(!StringUtils.isEmpty(outptPrescribeDTO.getDoctorId()) && !outptPrescribeDTO.getDoctorId().equals(sysUserDTO.getId())){
          return WrapperResponse.error(200,"只能由开方医生删除！",null);
    }
    return outptDoctorPrescribeService_consumer.deleteOutptPrescribe(paramMap);
  }

  /**
   * @Menthod updatePrescribeIsCancel
   * @Desrciption  作废处方信息
   * @param  outptPrescribeDTO: id ：处方ID
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/updatePrescribeIsCancel")
  public WrapperResponse<Boolean> updatePrescribeIsCancel(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //作废
    outptPrescribeDTO.setIsCancel(Constants.SF.S);
    //作废人ID
    outptPrescribeDTO.setCancelId(sysUserDTO.getId());
    //作废人
    outptPrescribeDTO.setCancelName(sysUserDTO.getName());
    //查询数据
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.updatePrescribeIsCancel(paramMap);
  }

  /**
   * @Menthod getById
   * @Desrciption  根据id查询门诊执行科室配置信息
   *
   * @Param
   * [baseOutptExecDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/10/15 10:56
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>
   **/
  @GetMapping("/getExecDept")
  public WrapperResponse<BaseOutptExecDTO> getExecDept(BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    baseOutptExecDTO.setDeptIds(sysUserDTO.getLoginBaseDeptDTO().getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.getExecDept(map);
  }

  /**
   * @Menthod getOutptDiagnose
   * @Desrciption  获取诊断信息
   * @param  outptPrescribeDTO: visitId ：就诊ID
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return List<OutptDiagnoseDTO>
   **/
  @GetMapping("/getOutptDiagnose")
  public WrapperResponse<List<OutptDiagnoseDTO>> getOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.getOutptDiagnose(map);
  }

  /**
   * @Menthod saveOutptDiagnose
   * @Desrciption  保存诊断信息
   * @param outptDiagnoseDTO: visitId:就诊ID  outptDiagnoseDOList：诊断明细
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/saveOutptDiagnose")
  public WrapperResponse<Boolean> saveOutptDiagnose(@RequestBody OutptDiagnoseDTO outptDiagnoseDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptDiagnoseDTO.setHospCode(sysUserDTO.getHospCode());
    outptDiagnoseDTO.setCrteName(sysUserDTO.getName());
    outptDiagnoseDTO.setCrteId(sysUserDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptDiagnoseDTO",outptDiagnoseDTO);
    return outptDoctorPrescribeService_consumer.saveOutptDiagnose(map);
  }

  /**
   * @Menthod deleteDiagnoseById
   * @Desrciption  根据诊断ID删除处方诊断信息
   * @param outptDiagnoseDTO: 诊断信息 ：visitId:就诊ID  diseaseId：诊断ID
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/deleteDiagnoseById")
  public WrapperResponse<Boolean> deleteDiagnoseById(@RequestBody OutptDiagnoseDTO outptDiagnoseDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptDiagnoseDTO.setHospCode(sysUserDTO.getHospCode());
    outptDiagnoseDTO.setCrteName(sysUserDTO.getName());
    outptDiagnoseDTO.setCrteId(sysUserDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptDiagnoseDTO",outptDiagnoseDTO);
    return outptDoctorPrescribeService_consumer.deleteDiagnoseById(map);
  }

  /**
   * @Desrciption 获取药品单位
   * @param baseDrugDTO
   * @Author zengfeng
   * @Date   2020/10/26 14:44
   * @Return List<Map<String, Object>>
   */
  @GetMapping("/getDrugUnitCode")
  public WrapperResponse<List<Map<String, Object>>> getDrugUnitCode(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    //查询类型
    if(StringUtils.isEmpty(baseDrugDTO.getItemId())){
      throw new RuntimeException("药品ID不能为空");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //医院编码
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //医院编码
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //药品信息
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getDrugUnitCode(paramMap);
  }

  /**
   * @Menthod updatePrescribeSubmit
   * @Desrciption  根据诊断IDs提交处方信息
   * @param outptPrescribeDTO: 处方ID串 ：ids
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/updatePrescribeSubmit")
  public WrapperResponse<Boolean> updatePrescribeSubmit(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    outptPrescribeDTO.setSubmitName(sysUserDTO.getName());
    outptPrescribeDTO.setSubmitId(sysUserDTO.getId());

    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.updatePrescribeSubmit(map);
  }

  /**
   * @Menthod updateIsCanPrescribeSubmit
   * @Desrciption  根据诊断IDs取消提交处方信息
   * @param outptPrescribeDTO: 处方ID串 ：ids
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return 是否成功
   **/
  @NoRepeatSubmit
  @PostMapping("/updateIsCanPrescribeSubmit")
  public WrapperResponse<Boolean> updateIsCanPrescribeSubmit(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    outptPrescribeDTO.setSubmitName(sysUserDTO.getName());
    outptPrescribeDTO.setSubmitId(sysUserDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.updateIsCanPrescribeSubmit(map);
  }

  /**
   * @Menthod getOperInfoRecord
   * @Desrciption  获取手术申请单
   * @param  operInfoRecordDTO: 手术申请 adviceId：处方明细iD 或者医嘱ID
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return OperInfoRecordDTO
   **/
  @GetMapping("/getOperInfoRecord")
  public WrapperResponse<OperInfoRecordDTO> getOperInfoRecord(OperInfoRecordDTO operInfoRecordDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("operInfoRecordDTO",operInfoRecordDTO);
    return outptDoctorPrescribeService_consumer.getOperInfoRecord(map);
  }




  /**
   * @Menthod findVisitListById
   * @Desrciption  根据此次的就只能ID 查询此人的就诊记录
   * @param  outptPrescribeDTO
   * @Author pengbo
   * @Date   2020/10/26 10:24
   * @Return outptPrescribeDTO
   **/
  @GetMapping("/findVisitListById")
  public WrapperResponse<PageDTO> findVisitListById(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("outptPrescribeDTO",outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.findVisitListById(map);
  }

  /**
   * @Menthod isNeedReportInfectious
   * @Desrciption  根据疾病id判断是否是传染病
   * @param  diseaseId
   * @Author liuliyun
   * @Date   2021/04/21
   * @Return Boolean
   **/
  @GetMapping("/isNeedReportInfectious")
  public WrapperResponse<BaseDiseaseDTO> isNeedReportInfectious(String diseaseId, String profileId, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
    Map diseaseMap = new HashMap();
    baseDiseaseDTO.setId(diseaseId);
    String ids[]=diseaseId.split(",");
    List<String> diseaseIds=new ArrayList<>();
    if (ids!=null&&ids.length>0) {
      for (String id: ids){
        diseaseIds.add(id);
      }
    }
    baseDiseaseDTO.setIds(diseaseIds);
    baseDiseaseDTO.setHospCode(sysUserDTO.getHospCode());
    diseaseMap.put("hospCode", sysUserDTO.getHospCode());
    if(StringUtils.isNotEmpty(profileId)) {
      diseaseMap.put("profileId", profileId);
    }
    diseaseMap.put("baseDiseaseDTO", baseDiseaseDTO);
    return outptDoctorPrescribeService_consumer.isNeedReportInfectiousDisease(diseaseMap);
  }

  /***
     * 调用排队叫号-叫号接口
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/7/1 14:08
  **/
  @PostMapping("/callNumberInTheQueue")
  public WrapperResponse<Map<String,Object>> callNumberInTheQueue(@RequestBody  Map<String,Object> parameter, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    parameter.put("hospCode",sysUserDTO.getHospCode());
    parameter.put("doctorId",sysUserDTO.getId());
    return outptTriageVisitService_consumer.updateCallNumberInTheQueue(parameter);
  }
  /**
     *  调用排队叫号-过号接口
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/7/1 14:12
  **/
  @PostMapping("/numberMiss")
  public WrapperResponse<Boolean> numberMiss(@RequestBody  Map<String,Object> parameter, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    parameter.put("hospCode",sysUserDTO.getHospCode());
    return outptTriageVisitService_consumer.updateNumberMiss(parameter);
  }

  /**
   * @Menthod: queryLimitDrugList
   * @Desrciption: 查询医保限制级用药列表
   * @Param: outptVisitDTO
   * @Author: luoyong
   * @Email: luoyong@powersi.com.cn
   * @Date: 2021-07-19 11:42
   * @Return:
   **/
  @GetMapping("/queryLimitDrugList")
  public WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
      outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    Map paramMap = new HashMap();
    paramMap.put("hospCode", sysUserDTO.getHospCode());
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.queryLimitDrugList(paramMap);
  }

  /**
   * @Menthod: updateOuptCostAndPreDetailExt
   * @Desrciption: 更新费用表以及处方明细表副表限制用药相关字段()
   * @Param: insureItemMatchDTOS
   * @Author: luoyong
   * @Email: luoyong@powersi.com.cn
   * @Date: 2021-07-19 19:51
   * @Return:
   **/
  @PostMapping("/updateOuptCostAndPreDetailExt")
  public WrapperResponse<Boolean> updateOuptCostAndPreDetailExt(@RequestBody List<InsureItemMatchDTO> insureItemMatchDTOS, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    insureItemMatchDTOS.forEach(insureItemMatchDTO -> insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode()));
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("insureItemMatchDTOS", insureItemMatchDTOS);
    return outptDoctorPrescribeService_consumer.updateOuptCostAndPreDetailExt(map);
  }
}

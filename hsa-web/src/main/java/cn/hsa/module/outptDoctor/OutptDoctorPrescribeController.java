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
 * @Describe: ?????????????????????????????????
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
   * ????????????dubbo???????????????
   */
  @Resource
  private OutptDoctorPrescribeService outptDoctorPrescribeService_consumer;
  /** ???????????? **/
  @Resource
  private OutptTriageVisitService outptTriageVisitService_consumer;

  /**
   * ????????????dubbo???????????????
   */
  @Resource
  private CenterProfileFileService centerProfileFileService_consumer;

  /**
   * ????????????dubbo???????????????
   */
  @Resource
  private BaseOutptExecService baseOutptExecService_consumer;

  /**
   * ??????????????????
   */
  @Resource
  private SysParameterService sysParameterService_consumer;


  /**
   * @Menthod queryPatientByOperType
   * @Desrciption  ?????????????????????????????????
   * @param outptVisitDTO operType:???????????? 1???????????? 2?????????
   * @param outptVisitDTO startDate : ????????????
   * @param outptVisitDTO endDate : ????????????
   * @param outptVisitDTO keyword : ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/queryPatientByOperType")
  public WrapperResponse<List<Map<String, Object>>> queryPatientByOperType(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    //????????????
    if(StringUtils.isEmpty(outptVisitDTO.getOperType())){
      throw new RuntimeException("????????????????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //??????
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //????????????
    outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //?????? 1???????????? 2????????????
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.queryPatientByOperType(paramMap);
  }

  /**
   * @Menthod queryVisit
   * @Desrciption  ????????????
   * @param outptVisitDTO operType:???????????? 3???????????????
   * @param outptVisitDTO startDate : ????????????
   * @param outptVisitDTO endDate : ????????????
   * @param outptVisitDTO keyword : ???????????? deptId???????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return PageDTO
   **/
  @GetMapping("/queryVisit")
  public WrapperResponse<PageDTO> queryVisit(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    //????????????
    if(StringUtils.isEmpty(outptVisitDTO.getOperType())){
      throw new RuntimeException("????????????????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    if(StringUtils.isEmpty(outptVisitDTO.getDeptId())){
      outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    }
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //?????? 1???????????? 2???????????? 3???
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.queryVisit(paramMap);
  }

  /**
   * @Menthod queryMedicalRecord
   * @Desrciption  ????????????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/queryMedicalRecord")
  public WrapperResponse<List<Map<String, Object>>> queryMedicalRecord(HttpServletRequest req, HttpServletResponse res){
    //????????????ID
    String profileId = getParameterError(req,"profileId", "????????????ID????????????");
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????ID
    paramMap.put("profileId", profileId);
    return outptDoctorPrescribeService_consumer.queryMedicalRecord(paramMap);
  }

  /**
   * @Menthod getMedicalRecord
   * @Desrciption  ??????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getMedicalRecord")
  public WrapperResponse<List<Map<String, Object>>> getMedicalRecord(String visitId, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("visitId", visitId);
    return outptDoctorPrescribeService_consumer.getMedicalRecord(paramMap);
  }

  /**
   * @Menthod saveMedicalRecord
   * @Desrciption  ??????????????????
   * @param outptMedicalRecordDTO ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/saveMedicalRecord")
  public WrapperResponse<Boolean> saveMedicalRecord(@RequestBody OutptMedicalRecordDTO outptMedicalRecordDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????????????????
    outptMedicalRecordDTO.setHospCode(sysUserDTO.getHospCode());
    //?????????ID
    outptMedicalRecordDTO.setCrteId(sysUserDTO.getId());
    //???????????????
    outptMedicalRecordDTO.setCrteName(sysUserDTO.getName());
    //????????????
    paramMap.put("outptMedicalRecordDTO", outptMedicalRecordDTO);
    return outptDoctorPrescribeService_consumer.saveMedicalRecord(paramMap);
  }

  /**
   * @Menthod updateVisit
   * @Desrciption  ??????????????????
   * @param outptVisitDTO ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/updateVisit")
  public WrapperResponse<Boolean> updateVisit(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("??????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????????????????
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateVisit(paramMap);
  }

  /**
   * @Menthod updateVisitInHospital
   * @Desrciption  ?????????????????????
   * @param outptVisitDTO ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/updateVisitInHospital")
  public WrapperResponse<Boolean> updateVisitInHospital(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("??????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????????????????
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //???????????????
    outptVisitDTO.setTranInCode("1");
    // ??????????????????
    outptVisitDTO.setInCertTime(new Date());
    // ????????????id
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    // ??????????????????
    outptVisitDTO.setDoctorName(sysUserDTO.getName());
    //????????????
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateVisitInHospital(paramMap);
  }

  /**
   * @Menthod updateIsVisit
   * @Desrciption  ????????????
   * @param outptVisitDTO ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return Boolean
   **/
  @PostMapping("/updateIsVisit")
  public WrapperResponse<Boolean> updateIsVisit(@RequestBody OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptVisitDTO.getId())){
      throw new RuntimeException("??????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????????????????
    outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptVisitDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //????????????ID
    outptVisitDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //????????????
    outptVisitDTO.setDoctorName(sysUserDTO.getName());
    //????????????ID
    outptVisitDTO.setDoctorId(sysUserDTO.getId());
    //????????????
    paramMap.put("outptVisitDTO", outptVisitDTO);
    return outptDoctorPrescribeService_consumer.updateIsVisit(paramMap);
  }

  /**
   * @Menthod getPrescribeAllDetail
   * @Desrciption  ??????????????????
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
   * @Desrciption  ??????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribe")
  public WrapperResponse<List<Map<String, Object>>> getPrescribe(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("visitId", req.getParameter("visitId"));
    return outptDoctorPrescribeService_consumer.getPrescribe(paramMap);
  }

  /**
   * @Menthod getPrescribeDetail
   * @Desrciption  ????????????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeDetail")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetail( HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("opId", req.getParameter("opId"));
    return outptDoctorPrescribeService_consumer.getPrescribeDetail(paramMap);
  }

  /**
   * @Menthod getPrescribeDetailExt
   * @Desrciption  ??????????????????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeDetailExt")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailExt(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????ID
    paramMap.put("opdId", req.getParameter("opdId"));
    // ??????ID
    paramMap.put("opId",req.getParameter("opId"));
    // ??????Id
    paramMap.put("visitId",req.getParameter("visitId"));
    return outptDoctorPrescribeService_consumer.getPrescribeDetailExt(paramMap);
  }

  /**
   * @Menthod getPrescribeDetailAll
   * @Desrciption  ????????????????????????????????????????????????
   * @param  outptPrescribeDTO  ??????ID  hospCode ???????????????
   * @Author zengfeng
   * @Date   2020/10/20 19:46:53
   * @Return cn.hsa.base.PageDTO
   **/
  @GetMapping("/getPrescribeDetailAll")
  public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailAll(OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.getPrescribeDetailAll(paramMap);
  }

  /**
   * @Menthod getVisitById
   * @Desrciption  ?????????????????????visitId???????????????ID????????????  profileId???????????????ID???????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getVisitById")
  public WrapperResponse<List<OutptVisitDTO>> getVisitById(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("visitId", req.getParameter("visitId"));
    //??????ID
    paramMap.put("profileId", req.getParameter("profileId"));
    return outptDoctorPrescribeService_consumer.getVisitById(paramMap);
  }

  /**
   * @Menthod getPrescribeCost
   * @Desrciption  opId?????????ID  hospCode ???????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getPrescribeCost")
  public WrapperResponse<List<OutptCostDTO>> getPrescribeCost(HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("opId", req.getParameter("opId"));
    //??????ID
    paramMap.put("visitId", req.getParameter("visitId"));
    //??????ID
    paramMap.put("sourceCode", req.getParameter("sourceCode"));
    paramMap.put("settleCode", req.getParameter("settleStatus"));
    return outptDoctorPrescribeService_consumer.getPrescribeCost(paramMap);
  }

  /**
   * @Menthod queryProfileFile
   * @Desrciption  ??????????????????
   * @param centerProfileFileDTO: keyword ????????????
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
   * @Desrciption  ????????????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getOutptRegisterDetailDO")
  public WrapperResponse<List<OutptRegisterDetailDto>> getOutptRegisterDetailDO(OutptRegisterDTO outptRegisterDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptRegisterDTO.getCfId())){
      throw new RuntimeException("????????????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptRegisterDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptRegisterDTO.setLevelCode(sysUserDTO.getLevelCode());
    //??????????????????
    paramMap.put("outptRegisterDTO", outptRegisterDTO);
    return outptDoctorPrescribeService_consumer.getOutptRegisterDetailDO(paramMap);
  }


  /**
   * @Menthod OutptRegisterDTO
   * @Desrciption  ????????????
   * 1. ??????????????????????????????
   * 2. ????????????????????????
   * 3. ??????????????????????????????
   * 4. ????????????????????????????????????
   *    1???????????????????????????
   *    2??????????????????????????????
   * @param outptRegisterDTO ??????????????????????????????
   * @Author zengfeng
   * @Date   2020/9/7 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/saveDirectVisit")
  public WrapperResponse<OutptVisitDTO> saveDirectVisit(@RequestBody OutptRegisterDTO outptRegisterDTO, HttpServletRequest req, HttpServletResponse res) {
    if(StringUtils.isEmpty(outptRegisterDTO.getPreferentialTypeId())){
      throw new RuntimeException("????????????????????????");
    }
    if(StringUtils.isEmpty(outptRegisterDTO.getPatientCode())){
      throw new RuntimeException("????????????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptRegisterDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptRegisterDTO.setLevelCode(sysUserDTO.getLevelCode());
    //????????????ID
    outptRegisterDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //????????????
    outptRegisterDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //??????ID
    outptRegisterDTO.setDoctorId(sysUserDTO.getId());
    //??????
    outptRegisterDTO.setDoctorName(sysUserDTO.getName());
    //??????ID
    outptRegisterDTO.setCrteName(sysUserDTO.getName());
    //??????
    outptRegisterDTO.setCrteId(sysUserDTO.getId());
    //????????????
    paramMap.put("outptRegisterDTO", outptRegisterDTO);
    // ????????????
    return outptDoctorPrescribeService_consumer.saveDirectVisit(paramMap);
  } // End saveDirectVisit

  /**
   * @Menthod getCfData
   * @Desrciption  ??????????????????????????????????????????
   * @param
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<Map<String, Object>>
   **/
  @GetMapping("/getCfData")
  public WrapperResponse<PageDTO> getCfData(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // ???????????????????????????
    baseDrugDTO.setOnlyOpenItem(sysUserDTO.getOnlyOpenItem());
    //????????????
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getCfData(paramMap);
  }
  @PostMapping("/getCfData2")
  public WrapperResponse<PageDTO> getCfData2(@RequestBody BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // ???????????????????????????
    baseDrugDTO.setOnlyOpenItem(sysUserDTO.getOnlyOpenItem());
    //????????????
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getCfData2(paramMap);
  }
  /**
   * @Menthod OutptRegisterDTO
   * @Desrciption  ??????????????????
   * @param outptPrescribeDTO ????????????ID
   * @Author zengfeng
   * @Date   2020/9/7 10:24
   * @Return Boolean
   **/
  @NoRepeatSubmit
  @PostMapping("/savePrescribe")
  public WrapperResponse<Boolean> savePrescribe(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outptPrescribeDTO.getVisitId())) {
      throw new AppException("????????????????????????ID");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map sysParamMap = new HashMap();
    sysParamMap.put("hospCode", sysUserDTO.getHospCode());
    sysParamMap.put("code", "IS_OPEN_MZZY"); // ??????????????????????????????
    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParamMap).getData();
    if (sysParameterDTO!=null && "1".equals(sysParameterDTO.getValue())) {
      // ?????????????????????????????????
      if (StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())&&StringUtils.isEmpty(outptPrescribeDTO.getTcmDiseaseId())) {
        throw new AppException("??????????????????");
      }
    }else {
      if (StringUtils.isEmpty(outptPrescribeDTO.getDiagnoseIds())) {
        throw new AppException("??????????????????");
      }
    }
    if (ListUtils.isEmpty(outptPrescribeDTO.getOutptPrescribeDetailsDTOList())) {
      throw new AppException("??????????????????????????????");
    }
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    // ????????????
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    // ??????ID
    outptPrescribeDTO.setDoctorId(sysUserDTO.getId());
    // ????????????
    outptPrescribeDTO.setDoctorName(sysUserDTO.getName());
    // ????????????Id
    outptPrescribeDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    // ????????????
    outptPrescribeDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
    //????????????
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    // ??????????????????
    return outptDoctorPrescribeService_consumer.savePrescribe(paramMap);
  }
  /**
   * @param outptPrescribeDTO
   * @Menthod: queryDrugCount
   * @Desrciption: ?????????????????????????????????????????????
   * @Author: yuelong.chen
   * @Email: yuelong.chen@powersi.com.cn
   * @Date: 2022-08-23 19:51
   * @Return: List<Map>
   */
  @PostMapping("/queryDrugCount")
  public WrapperResponse<List<Map>> queryDrugCount(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.queryDrugCount(paramMap);
  }
  /**
   * @Menthod queryOutptPrescribeTempDTO
   * @Desrciption  ????????????????????????
   * @param outptPrescribeTempDTO ???????????????operType ???2 ?????? 1????????? 0 ?????? ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<OutptPrescribeTempDTO>
   **/
  @GetMapping("/queryOutptPrescribeTempDTO")
  public WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeTempDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptPrescribeTempDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //?????????
    outptPrescribeTempDTO.setDoctorId(sysUserDTO.getId());
    //????????????
    paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
    return outptDoctorPrescribeService_consumer.queryOutptPrescribeTempDTO(paramMap);
  }

  /**
   * @Menthod queryOutptPrescribeTempDetails
   * @Desrciption  ??????????????????????????????
   * @param outptPrescribeTempDTO : ????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<OutptPrescribeTempDetailDTO>
   **/
  @GetMapping("/queryOutptPrescribeTempDetails")
  public WrapperResponse<List<OutptPrescribeTempDetailDTO>> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeTempDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptPrescribeTempDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //????????????
    paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
    return outptDoctorPrescribeService_consumer.queryOutptPrescribeTempDetails(paramMap);
  }

  /**
   * @Menthod getByCode
   * @Desrciption  ?????????????????????????????????????????? type=0 ?????????????????????
   * @param  sysCodeDetailDTO
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<SysCodeDetailDTO>
   **/
  @GetMapping("/getByCode")
  public WrapperResponse<List<SysCodeDetailDTO>> getByCode(SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("sysCodeDetailDTO", sysCodeDetailDTO);
    return outptDoctorPrescribeService_consumer.getByCode(paramMap);
  }

  /**
   * @Menthod getByCodeDetail
   * @Desrciption  ????????????????????????????????????
   * @param sysCodeDetailDTO ??? code?????????????????????  type????????? upValue:?????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return List<SysCodeDetailDTO>
   **/
  @GetMapping("/getByCodeDetail")
  public WrapperResponse<List<SysCodeDetailDTO>> getByCodeDetail(SysCodeDetailDTO sysCodeDetailDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    sysCodeDetailDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("sysCodeDetailDTO", sysCodeDetailDTO);
    return outptDoctorPrescribeService_consumer.getByCodeDetail(paramMap);
  }

  /**
   * @Menthod getDrugData
   * @Desrciption  ??????????????????
   * @param baseDrugDTO typeCode??????????????????value??? type???0????????? 1????????? 2????????? 5:????????????
   * @Author zengfeng
   * @Date   2020/9/2 10:24
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/getDrugData")
  public WrapperResponse<PageDTO> getDrugData(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //????????????
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getDrugData(paramMap);
  }

  /**
   * @Menthod calculateYp
   * @Desrciption  ?????????????????????????????????????????????????????????
   * @param outptPrescribeDetailsDTO: itemId ?????????ID RateId?????????ID  Dosage????????? num?????????
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return Map : jl:??????  yl:?????? zsl:????????? yyts:????????????
   **/
  @GetMapping("/calculateYp")
  public WrapperResponse<Map> calculateYp(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO, HttpServletRequest req, HttpServletResponse res){
    if(StringUtils.isEmpty(outptPrescribeDetailsDTO.getItemId())){
      throw new RuntimeException("??????????????????");
    }
    if(StringUtils.isEmpty(outptPrescribeDetailsDTO.getNumUnitCode())){
      throw new RuntimeException("????????????????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDetailsDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDetailsDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    //????????????
    paramMap.put("outptPrescribeDetailsDTO", outptPrescribeDetailsDTO);
    return outptDoctorPrescribeService_consumer.calculateYp(paramMap);
  }

  /**
   * @Menthod deleteOutptPrescribe
   * @Desrciption  ??????????????????
   * @param  outptPrescribeDTO: id ?????????ID
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return ????????????
   **/
  @NoRepeatSubmit
  @PostMapping("/deleteOutptPrescribe")
  public WrapperResponse<Boolean> deleteOutptPrescribe(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    //??????????????????
    if(!StringUtils.isEmpty(outptPrescribeDTO.getDoctorId()) && !outptPrescribeDTO.getDoctorId().equals(sysUserDTO.getId())){
          return WrapperResponse.error(200,"??????????????????????????????",null);
    }
    return outptDoctorPrescribeService_consumer.deleteOutptPrescribe(paramMap);
  }

  /**
   * @Menthod updatePrescribeIsCancel
   * @Desrciption  ??????????????????
   * @param  outptPrescribeDTO: id ?????????ID
   * @Author zengfeng
   * @Date   2020/9/19 10:24
   * @Return ????????????
   **/
  @NoRepeatSubmit
  @PostMapping("/updatePrescribeIsCancel")
  public WrapperResponse<Boolean> updatePrescribeIsCancel(@RequestBody OutptPrescribeDTO outptPrescribeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    outptPrescribeDTO.setHospCode(sysUserDTO.getHospCode());
    //??????
    outptPrescribeDTO.setIsCancel(Constants.SF.S);
    //?????????ID
    outptPrescribeDTO.setCancelId(sysUserDTO.getId());
    //?????????
    outptPrescribeDTO.setCancelName(sysUserDTO.getName());
    //????????????
    paramMap.put("outptPrescribeDTO", outptPrescribeDTO);
    return outptDoctorPrescribeService_consumer.updatePrescribeIsCancel(paramMap);
  }

  /**
   * @Menthod getById
   * @Desrciption  ??????id????????????????????????????????????
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
   * @Desrciption  ??????????????????
   * @param  outptPrescribeDTO: visitId ?????????ID
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
   * @Desrciption  ??????????????????
   * @param outptDiagnoseDTO: visitId:??????ID  outptDiagnoseDOList???????????????
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return ????????????
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
   * @Desrciption  ????????????ID????????????????????????
   * @param outptDiagnoseDTO: ???????????? ???visitId:??????ID  diseaseId?????????ID
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return ????????????
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
   * @Desrciption ??????????????????
   * @param baseDrugDTO
   * @Author zengfeng
   * @Date   2020/10/26 14:44
   * @Return List<Map<String, Object>>
   */
  @GetMapping("/getDrugUnitCode")
  public WrapperResponse<List<Map<String, Object>>> getDrugUnitCode(BaseDrugDTO baseDrugDTO, HttpServletRequest req, HttpServletResponse res){
    //????????????
    if(StringUtils.isEmpty(baseDrugDTO.getItemId())){
      throw new RuntimeException("??????ID????????????");
    }
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //????????????
    baseDrugDTO.setHospCode(sysUserDTO.getHospCode());
    //????????????
    paramMap.put("baseDrugDTO", baseDrugDTO);
    return outptDoctorPrescribeService_consumer.getDrugUnitCode(paramMap);
  }

  /**
   * @Menthod updatePrescribeSubmit
   * @Desrciption  ????????????IDs??????????????????
   * @param outptPrescribeDTO: ??????ID??? ???ids
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return ????????????
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
   * @Desrciption  ????????????IDs????????????????????????
   * @param outptPrescribeDTO: ??????ID??? ???ids
   * @Author zengfeng
   * @Date   2020/10/26 10:24
   * @Return ????????????
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
   * @Desrciption  ?????????????????????
   * @param  operInfoRecordDTO: ???????????? adviceId???????????????iD ????????????ID
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
   * @Desrciption  ????????????????????????ID ???????????????????????????
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
   * @Desrciption  ????????????id????????????????????????
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
     * ??????????????????-????????????
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
     *  ??????????????????-????????????
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
   * @Desrciption: ?????????????????????????????????
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
   * @Desrciption: ??????????????????????????????????????????????????????????????????()
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

  /**
   * @Menthod getPrescribeDetailForEncode
   * @Desrciption  ????????????????????????(????????????)
   * @param map opId?????????ID  hospCode ??????????????? visitId?????????id
   * @Author liuliyun
   * @Date   2022/10/09 14:24
   * @Return Map<String, Object>
   **/
  @GetMapping("/getPrescribeDetailForEncode")
  public WrapperResponse<Map<String, Object>> getPrescribeDetailForEncode( HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map paramMap = new HashMap();
    //????????????
    paramMap.put("hospCode",sysUserDTO.getHospCode());
    //??????ID
    paramMap.put("opId", req.getParameter("opId"));
    //??????ID
    paramMap.put("visitId", req.getParameter("visitId"));
    return outptDoctorPrescribeService_consumer.getPrescribeDetailForEncode(paramMap);
  }
}

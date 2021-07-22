package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.service.PatientComprehensiveQueryService;
import cn.hsa.module.mris.mrisHome.dto.InptBedChangeInfoDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name:: PatientComprehensiveQueryController
 * @Description: 病人综合查询控制层
 * @Author: fuhui
 * @Date: 2020/10/12 9:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/patientComprehensiveQuery")
@Slf4j
public class PatientComprehensiveQueryController extends BaseController {

  @Resource
  private PatientComprehensiveQueryService comprehensiveQueryService_consumer;


    /**
     * @Method queryPatientDiagnosePage()
     * @Desrciption  病人诊断信息查询
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/10/12 15:55
     * @Return 病人诊断分页信息
    **/
    @GetMapping("/queryPatientDiagnosePage")
    public WrapperResponse<PageDTO> queryPatientDiagnosePage(InptDiagnoseDTO inptDiagnoseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptDiagnoseDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptDiagnoseDTO",inptDiagnoseDTO);
        return comprehensiveQueryService_consumer.queryPatientDiagnosePage(map);
    }

  /**
  * @Menthod queryAllAdvice
  * @Desrciption 查询医嘱汇总
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/12 10:41
  * @Return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
  **/
  @GetMapping("queryAdviceSummary")
  public WrapperResponse<PageDTO> queryAdviceSummary(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("loginDeptId", sysUserDTO.getLoginBaseDeptDTO().getId());
    map.put("hospCode", sysUserDTO.getHospCode());
    inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("inptAdviceDTO",inptAdviceDTO);
    return comprehensiveQueryService_consumer.queryAdviceSummary(map);
  }
  /**
   * @Method querySettleInfo
   * @Desrciption  查询结算详细信息
   * @Param
   * map [ 就诊号，当前科室，医院编码 ]
   * @Author zhangxuan
   * @Date   2020-10-14 14:48
   * @Return map
  **/
  @GetMapping("querySettleInfo")
  public WrapperResponse<PageDTO> querySettleInfo(InptSettleDTO inptSettleDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    inptSettleDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("deptId", sysUserDTO.getBaseDeptDTO().getId());
    map.put("inptSettleDTO",inptSettleDTO);
    return comprehensiveQueryService_consumer.querySettleInfo(map);
  }

  /**
   * @Description: 查询所有住院病人数据
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/4/23 16:07
   * @Return
   */
  @GetMapping("queryAllInptVisitInfo")
  public WrapperResponse<PageDTO> queryAllInptVisitInfo(InptSettleDTO inptSettleDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      inptSettleDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("deptId", sysUserDTO.getBaseDeptDTO().getId());
      map.put("inptSettleDTO",inptSettleDTO);
      return comprehensiveQueryService_consumer.queryAllInptVisitInfo(map);
  }

    /**
   * @Method queryBedChangeInfo
   * @Desrciption  查询床位异动信息
   * @Param
   * map [就诊号,医院编码]
   * @Author zhangxuan
   * @Date   2020-10-15 14:03
   * @Return map
  **/
  @GetMapping("queryBedChangeInfo")
  public WrapperResponse<PageDTO> queryBedChangeInfo(InptBedChangeInfoDTO inptBedChangeInfoDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    inptBedChangeInfoDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptBedChangeInfoDTO",inptBedChangeInfoDTO);
    return comprehensiveQueryService_consumer.queryBedChangeInfo(map);
  }
  /**
   * @Method querySettleInvoice
   * @Desrciption  结算票据信息
   * @Param
   * map [就诊号，医院编码]
   * @Author zhangxuan
   * @Date   2020-10-15 14:25
   * @Return map
  **/
  @GetMapping("querySettleInvoice")
  public WrapperResponse<PageDTO> querySettleInvoice(InptSettleDTO inptSettleDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    inptSettleDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("inptSettleDTO",inptSettleDTO);
    return comprehensiveQueryService_consumer.querySettleInvoice(map);
  }

  /**
  * @Menthod queryAdviceSummaryDetail
  * @Desrciption 查询医嘱明细
  *
  * @Param
  * [inptAdviceDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/16 10:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("queryAdviceSummaryDetail")
  public WrapperResponse<PageDTO> queryAdviceSummaryDetail(InptAdviceDetailDTO inptAdviceDetailDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    inptAdviceDetailDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("inptAdviceDetailDTO",inptAdviceDetailDTO);
    return comprehensiveQueryService_consumer.queryAdviceSummaryDetail(map);
  }

  /**
  * @Menthod queryAdviceLongOrShort
  * @Desrciption 病人综合查询--长期/短期医嘱查询
  *
  * @Param
  * [inptAdviceDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/16 16:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("queryAdviceLongOrShort")
  public WrapperResponse<PageDTO> queryAdviceLongOrShort(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("inptAdviceDTO",inptAdviceDTO);
    return comprehensiveQueryService_consumer.queryAdviceLongOrShort(map);
  }


  /**
   * @Menthod queryItemAndDrugAndMaterialAndAdvice
   * @Desrciption  项目汇总查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode）
   *                                     选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("queryItemAndDrugAndMaterialAndAdvice")
  public WrapperResponse<PageDTO> queryItemAndDrugAndMaterialAndAdvice(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      if ("Y".equals(patientCompreHensiveQueryDTO.getSourceDeptId())){
          patientCompreHensiveQueryDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      }else {
          patientCompreHensiveQueryDTO.setSourceDeptId(null);
      }
      map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
      return comprehensiveQueryService_consumer.queryItemAndDrugAndMaterialAndAdvice(map);
  }


    /**
     * @Menthod queryItemAndDrugAndMaterialDetail
     * @Desrciption  根据项目汇总查询其中明细
     * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode） 选填:仅当前科室（pharId）,医嘱项目（isAdviceItem=Y）,非医嘱项目（isAdviceItem=N）
     *                                     项目id(item_id),项目类型(item_code)
     * @Author xingyu.xie
     * @Date   2020/12/22 9:22
     * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
     **/
    @GetMapping("queryItemAndDrugAndMaterialDetail")
    public WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryItemAndDrugAndMaterialDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
        if ("Y".equals(patientCompreHensiveQueryDTO.getSourceDeptId())){
            patientCompreHensiveQueryDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }else {
            patientCompreHensiveQueryDTO.setSourceDeptId(null);
        }
        if(StringUtils.isEmpty(patientCompreHensiveQueryDTO.getItemId())){
            throw new AppException("项目ID不能为空");
        }
        map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
        return comprehensiveQueryService_consumer.queryItemAndDrugAndMaterialDetail(map);
    }

  /**
   * @Menthod queryCostClassify
   * @Desrciption  计费类别查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("queryCostClassify")
  public WrapperResponse<PageDTO> queryCostClassify(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
      return comprehensiveQueryService_consumer.queryCostClassify(map);
  }

  /**
   * @Menthod queryCostClassifyDetail
   * @Desrciption  计费类别明细查询
   * @param patientCompreHensiveQueryDTO 必填:就诊id(visitId), 医院编码（hospCode）,财务类别id（原费用类别）（bfcId）
   *                                     选填:仅当前科室（pharId）,项目名称keyword(keyword)
   * @Author xingyu.xie
   * @Date   2020/10/13 13:49
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("queryCostClassifyDetail")
  public WrapperResponse<PageDTO> queryCostClassifyDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      if ("Y".equals(patientCompreHensiveQueryDTO.getSourceDeptId())){
          patientCompreHensiveQueryDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      }else {
          patientCompreHensiveQueryDTO.setSourceDeptId(null);
      }
      map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
      return comprehensiveQueryService_consumer.queryCostClassifyDetail(map);
  }

  /**
   * @Menthod queryCostAll
   * @Desrciption  费用汇总查询
   * @param patientCompreHensiveQueryDTO 必填：就诊id（VisitId）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("queryCostAll")
  public WrapperResponse<PageDTO> queryCostAll(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
      return comprehensiveQueryService_consumer.queryCostAll(map);
  }

  /**
   * @Menthod queryCostAllDetail
   * @Desrciption  费用汇总明细查询
   * @param patientCompreHensiveQueryDTO  必填：就诊id（VisitId）,财务分类id（原费用类别）（bfcId）,计费时间（costTime）
   * @Author xingyu.xie
   * @Date   2020/10/13 13:50
   * @Return java.util.List<cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO>
   **/
  @GetMapping("queryCostAllDetail")
  public WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostAllDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
      return comprehensiveQueryService_consumer.queryCostAllDetail(map);
  }
    /**
     * @Method queryAdvice()
     * @Desrciption  查询临时医嘱和长期医嘱的信息
     * @Param inptVisitDTO：visitId：就诊id isLong：是否长期医嘱
     *
     * @Author fuhui
     * @Date   2020/10/12 17:20
     * @Return 医嘱信息
     **/
    @GetMapping("/queryAdvice")
    public WrapperResponse<List<InptAdviceDTO >> queryAdvice(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptAdviceDTO",inptAdviceDTO);
        return comprehensiveQueryService_consumer.queryAdvice(map);
    }

    /**
     * @Method queryCostClassifyDetail
     * @Desrciption 费用详情查询
     * @Param
     * [patientCompreHensiveQueryDTO]
     * @Author liaojunjie
     * @Date   2020/10/22 21:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryCostDetail")
    public WrapperResponse<PageDTO> queryCostDetail(PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
        if ("Y".equals(patientCompreHensiveQueryDTO.getSourceDeptId())){
            patientCompreHensiveQueryDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }else {
            patientCompreHensiveQueryDTO.setSourceDeptId(null);
        }
        map.put("patientCompreHensiveQueryDTO",patientCompreHensiveQueryDTO);
        return comprehensiveQueryService_consumer.queryCostDetail(map);
    }

}

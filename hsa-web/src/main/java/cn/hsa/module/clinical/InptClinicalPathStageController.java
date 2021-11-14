package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.service.InptClinicalPathStageService;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径病人记录控制层
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/9/22 14:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/clinical/inptClinicalPathStageController")
@Slf4j
public class InptClinicalPathStageController extends BaseController {

  @Resource
  private InptClinicalPathStageService inptClinicalPathStageService_consumer;
  /**
   * @Menthod getInptClinicalPathStageById
   * @Desrciption 查询单个入径阶段病情
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
   **/
  @RequestMapping("/getInptClinicalPathStageById")
  public WrapperResponse<InptClinicalPathStageDTO> getInptClinicalPathStageById(InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.getInptClinicalPathStageById(map);
  }

  /**
   * @Menthod queryInptClinicalPathStagePage
   * @Desrciption 查询入径阶段病情
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @RequestMapping("/queryInptClinicalPathStagePage")
  public WrapperResponse<PageDTO> queryInptClinicalPathStagePage(InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.queryInptClinicalPathStagePage(map);
  }

  /**
   * @Menthod queryInptClinicalPathStageAll
   * @Desrciption
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>>
   **/
  @RequestMapping("/queryInptClinicalPathStageAll")
  public WrapperResponse<List<InptClinicalPathStageDTO>> queryInptClinicalPathStageAll(InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.queryInptClinicalPathStageAll(map);
  }

  /**
   * @Menthod saveInptClinicalPathStage
   * @Desrciption  保存入径阶段病情
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/saveInptClinicalPathStage")
  public WrapperResponse<Boolean> saveInptClinicalPathStage(@RequestBody InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    inptClinicalPathStageDTO.setCrteId(sysUserDTO.getId());
    inptClinicalPathStageDTO.setCrteTime(DateUtils.getNow());
    inptClinicalPathStageDTO.setCrteName(sysUserDTO.getName());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.saveInptClinicalPathStage(map);
  }



  /**
  * @Menthod queryNotExecItem
  * @Desrciption  查询阶段未执行项目
  *
  * @Param
  * [inptClinicalPathStateDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/26 10:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/queryNotExecItem")
  public WrapperResponse<List<ClinicPathStageDetailDTO>> queryNotExecItem(@RequestBody InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.queryNotExecItem(map);
  }

  /**
   * @Menthod deleteInptClinicalPathStage
   * @Desrciption
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/deleteInptClinicalPathStage")
  public WrapperResponse<Boolean> deleteInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO sysUserDTO = getSession(req, res);
    inptClinicalPathStageDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("inptClinicalPathStageDTO",inptClinicalPathStageDTO);
    return inptClinicalPathStageService_consumer.deleteInptClinicalPathStage(map);
  }
}

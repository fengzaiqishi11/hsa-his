package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO;
import cn.hsa.module.outpt.outptmedicaltemplate.service.OutptMedicalTemplateService;
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
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptMedicalTemplateController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/3/9 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@RestController
@RequestMapping("/web/outpt/outptMedicalTemplate")
public class OutptMedicalTemplateController extends BaseController {

  @Resource
  private OutptMedicalTemplateService outptMedicalTemplateService_consumer;

  /**
  * @Menthod getById
  * @Desrciption 根据主键查询门诊电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 14:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptmedicaltemplate.dto.OutptMedicalTemplateDTO>
  **/
  @RequestMapping(value = "/getById")
  public WrapperResponse<OutptMedicalTemplateDTO> getById(OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    outptMedicalTemplateDTO.setHospCode(userDTO.getHospCode());
    return outptMedicalTemplateService_consumer.getById(map);
  }

  /**
  * @Menthod queryMedicalTemplatePage
  * @Desrciption 分页查询门诊病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @RequestMapping(value = "/queryMedicalTemplatePage")
  public WrapperResponse<PageDTO> queryMedicalTemplatePage(OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    outptMedicalTemplateDTO.setDoctorId(userDTO.getId());
    outptMedicalTemplateDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getId());
    outptMedicalTemplateDTO.setHospCode(userDTO.getHospCode());
    return outptMedicalTemplateService_consumer.queryMedicalTemplatePage(map);
  }

  /**
  * @Menthod saveMedicalTemplate
  * @Desrciption 保存电子病历模板
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:03
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping(value = "/insertMedicalTemplate")
  public WrapperResponse<Boolean> insertMedicalTemplate(@RequestBody OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    outptMedicalTemplateDTO.setCrteId(userDTO.getId());
    outptMedicalTemplateDTO.setHospCode(userDTO.getHospCode());
    outptMedicalTemplateDTO.setCrteName(userDTO.getName());
    outptMedicalTemplateDTO.setCrteTime(DateUtils.getNow());
    outptMedicalTemplateDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getId());
    outptMedicalTemplateDTO.setDeptName(userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getName());
    outptMedicalTemplateDTO.setDoctorId(userDTO.getId());
    outptMedicalTemplateDTO.setDoctorName(userDTO.getName());
    outptMedicalTemplateDTO.setAuditId(userDTO.getId());
    outptMedicalTemplateDTO.setAuditName(userDTO.getName());
    outptMedicalTemplateDTO.setAuditTime(DateUtils.getNow());
    return outptMedicalTemplateService_consumer.insertMedicalTemplate(map);
  }

  /**
  * @Menthod updateMedicalTemplate
  * @Desrciption
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 19:31
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping(value = "/updateMedicalTemplate")
  public WrapperResponse<Boolean> updateMedicalTemplate(@RequestBody OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    map.put("loginDeptId",userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getId());
    map.put("loginDeptName",userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getName());
    map.put("userId",userDTO.getId());
    map.put("userName",userDTO.getName());
    return outptMedicalTemplateService_consumer.updateMedicalTemplate(map);
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 修改电子病历模板状态
  *
  * @Param
  * [outptMedicalTemplateDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/9 15:05
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping(value = "/updateStatus")
  public WrapperResponse<Boolean> updateStatus(@RequestBody OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    outptMedicalTemplateDTO.setHospCode(userDTO.getHospCode());
    return outptMedicalTemplateService_consumer.updateStatus(map);
  }

  /**
   * @Menthod queryAllMedicalTemplate
   * @Desrciption 查询所有门诊病历模板
   * @Param [outptMedicalTemplateDTO]
   * @Author liuliyun
   * @Date   2021/10/21 15:27
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @RequestMapping(value = "/queryAllMedicalTemplate")
  public WrapperResponse<PageDTO> queryAllMedicalTemplate(OutptMedicalTemplateDTO outptMedicalTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptMedicalTemplateDTO",outptMedicalTemplateDTO);
    outptMedicalTemplateDTO.setDoctorId(userDTO.getId());
    outptMedicalTemplateDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null? null:
            userDTO.getLoginBaseDeptDTO().getId());
    outptMedicalTemplateDTO.setHospCode(userDTO.getHospCode());
    return outptMedicalTemplateService_consumer.queryAllMedicalTemplate(map);
  }

}

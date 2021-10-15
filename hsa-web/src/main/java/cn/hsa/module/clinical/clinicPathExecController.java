package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;
import cn.hsa.module.clinical.clinicpathexec.service.ClinicPathExecService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @Class_name: clinicPathExecController
 * @Describe: 临床路径执行情况控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/10/14 16:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/clinicPathExecController")
@Slf4j
public class clinicPathExecController extends BaseController {
  @Resource
  private ClinicPathExecService clinicPathExecService_consumer;

  /**
  * @Menthod queryClinicPathExecById
  * @Desrciption  根据id查询临床路径执行
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:34
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>
  **/
  @RequestMapping("/queryClinicPathExecById")
  public WrapperResponse<ClinicPathExecDTO> queryClinicPathExecById(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.queryClinicPathExecById(map);
  }

  /**
  * @Menthod queryClinicPathExecAll
  * @Desrciption  根据条件查询全部临床路径执行
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO>>
  **/
  @RequestMapping("/queryClinicPathExecAll")
  public WrapperResponse<List<ClinicPathExecDTO>> queryClinicPathExecAll(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.queryClinicPathExecAll(map);
  }


  /**
  * @Menthod queryClinicPathExecPage
  * @Desrciption  根据条件分页查询临床路径执行
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @RequestMapping("/queryClinicPathExecPage")
  public WrapperResponse<PageDTO> queryClinicPathExecPage(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.queryClinicPathExecPage(map);
  }

  /**
  * @Menthod insertClinicPathExec
  * @Desrciption  新增临床路径执行记录
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/insertClinicPathExec")
  public WrapperResponse<Boolean> insertClinicPathExec(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    clinicPathExec.setCrteId(sysUserDTO.getId());
    clinicPathExec.setCrteName(sysUserDTO.getName());
    clinicPathExec.setCrteTime(DateUtils.getNow());
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.insertClinicPathExec(map);
  }

  /**
  * @Menthod updateClinicPathExec
  * @Desrciption 编辑临床路径执行记录
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/updateClinicPathExec")
  public WrapperResponse<Boolean> updateClinicPathExec(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.updateClinicPathExec(map);
  }

  /**
  * @Menthod deleteClinicPathExecById
  * @Desrciption  删除临床路径执行记录
  *
  * @Param
  * [clinicPathExec, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/10/14 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteClinicPathExecById")
  public WrapperResponse<Boolean> deleteClinicPathExecById(ClinicPathExecDTO clinicPathExec, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req,res);
    String hospCode = sysUserDTO.getHospCode();
    clinicPathExec.setHospCode(hospCode);
    Map map = new HashMap<>();
    map.put("hospCode",hospCode);
    map.put("clinicPathExec",clinicPathExec);
    return clinicPathExecService_consumer.deleteClinicPathExecById(map);
  }
}

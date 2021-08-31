package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO;
import cn.hsa.module.emr.emrprintsetting.service.EmrPrintSettingService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrPrintSettingController
 * @Describe: 电子病历打印设置控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrPrintSetting")
@Slf4j
public class EmrPrintSettingController extends BaseController {

  /**
   * 电子病历打印设置消费者接口
   */
  @Resource
  private EmrPrintSettingService emrPrintSettingService_consumer;

  /**
   * @Menthod getById()
   * @Desrciption  根据主键id查询供应商信息
   *
   * @Param
   * 1。 [id] 供应商主键id
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:27
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.EmrPrintSettingDTO>
   **/
  @GetMapping("/getById")
  public WrapperResponse<EmrPrintSettingDTO> getById(EmrPrintSettingDTO emrPrintSettingDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("emrPrintSettingDTO",emrPrintSettingDTO);
    map.put("hospCode",sysUserDTO.getHospCode());
    return emrPrintSettingService_consumer.getById(map);
  }

  /**
   * @Menthod queryPage()
   * @Desrciption   根据条件分页查询供应商信息
   *
   * @Param
   * 1. [emrPrintSettingDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:30
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(EmrPrintSettingDTO emrPrintSettingDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrPrintSettingDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("emrPrintSettingDTO",emrPrintSettingDTO);
    return emrPrintSettingService_consumer.queryPage(map);
  }

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有供应商信息
   *
   * @Param
   * [1. emrPrintSettingDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/18 14:45
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<EmrPrintSettingDTO>> queryAll(EmrPrintSettingDTO emrPrintSettingDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrPrintSettingDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("emrPrintSettingDTO",emrPrintSettingDTO);
    return this.emrPrintSettingService_consumer.queryAll(map);
  }

  /**
   * @Menthod insert()
   * @Desrciption 新增供应商
   *
   * @Param
   * 1.[emrPrintSettingDTO] 供应商数据传输DTO对象
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspl.dto.EmrPrintSettingDTO>
   **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> insert(@RequestBody EmrPrintSettingDTO emrPrintSettingDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrPrintSettingDTO.setHospCode(sysUserDTO.getHospCode());
    emrPrintSettingDTO.setCrteId(sysUserDTO.getId());
    emrPrintSettingDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("emrPrintSettingDTO",emrPrintSettingDTO);
    return emrPrintSettingService_consumer.save(map);
  }


  /**
   * @Menthod updateStatus()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   * 1. [ids] 主键id
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/updateStatus")
  public WrapperResponse<Boolean> updateStatus(@RequestBody EmrPrintSettingDTO emrPrintSettingDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    emrPrintSettingDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("emrPrintSettingDTO",emrPrintSettingDTO);
    return emrPrintSettingService_consumer.updateStatus(map);
  }
}

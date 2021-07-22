package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO;
import cn.hsa.module.base.baseoutptexec.service.BaseOutptExecService;
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
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseOutptExecController
 * @Describe: 门诊执行科室配置信息维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/10/14 14:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseOutptExec")
@Slf4j
public class BaseOutptExecController extends BaseController {
  /**
   * 供应商信息维护dubbo消费者接口
   */
  @Resource
  private BaseOutptExecService baseOutptExecService_consumer;


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
  @GetMapping("/getById")
  public WrapperResponse<BaseOutptExecDTO> getById(BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    map.put("id",baseOutptExecDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.getById(map);
  }

  /**
  * @Menthod queryPage
  * @Desrciption 根据条件分页查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 10:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.queryPage(map);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 10:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.baseoutptexec.dto.BaseOutptExecDTO>>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<BaseOutptExecDTO>> queryAll(BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.queryAll(map);
  }

  /**
  * @Menthod insert
  * @Desrciption 新增/编辑门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 10:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> insert(@RequestBody BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    baseOutptExecDTO.setCrteId(sysUserDTO.getId());
    baseOutptExecDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.save(map);
  }

  /**
  * @Menthod updateStatus
  * @Desrciption 作废/启用门诊执行科室配置信息
  *
  * @Param
  * [baseOutptExecDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/10/15 10:57
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/updateStatus")
  public WrapperResponse<Boolean> updateStatus(@RequestBody BaseOutptExecDTO baseOutptExecDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    baseOutptExecDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("baseOutptExecDTO",baseOutptExecDTO);
    return baseOutptExecService_consumer.updateStatus(map);
  }
}


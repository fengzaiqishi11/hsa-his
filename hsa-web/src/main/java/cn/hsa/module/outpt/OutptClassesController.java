package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.module.outpt.classes.service.OutptClassesService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptClassesController
 * @Description: 班次信息维护控制层
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:14 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/classes")
@Slf4j
public class OutptClassesController extends BaseController {
  /**
   * 班次信息维护dubbo消费者接口
   */
    @Resource
    private OutptClassesService outptClassesService_consumer;
  /**
   * @Menthod queryPage()
   * @Desrciption   根据条件分页查询班次信息
   * @Param
   * 1. [outptClassesDTO] 班次数据传输DTO对象
   * @Author zhangxuan
   * @Date   2020/7/28 16:30
   * @Return map
   **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(OutptClassesDTO outptClassesDTO, HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.queryPage(map);
  }
  /**
   * @Menthod queryPage()
   * @Desrciption   根据条件分页查询班次信息
   * @Param
   * 1. [outptClassesDTO] 班次数据传输DTO对象
   * @Author zhangxuan
   * @Date   2020/7/28 16:30
   * @Return map
   **/
  @GetMapping("/getById")
  public WrapperResponse<OutptClassesDTO> getById(OutptClassesDTO outptClassesDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.getById(map);
  }
  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有班次信息
   * @Param
   * [1. outptClassesDTO]
   * @Author zhangxuan
   * @Date   2020/7/28 14:45
   * @Return map
   **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<OutptClassesDTO>> queryAll(OutptClassesDTO outptClassesDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.queryAll(map);
  }

  /**
   * @Menthod insert()
   * @Desrciption 新增班次
   * @Param
   * 1.[outptClassesDTO] 班次数据传输DTO对象
   * @Author zhangxuan
   * @Date   2020/7/28 16:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.parameter.dto.OutptClassesDTO>
   **/
  @PostMapping("/insert")
  public WrapperResponse<Boolean> insert(@RequestBody OutptClassesDTO outptClassesDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    outptClassesDTO.setCrteId(userDTO.getId());
    outptClassesDTO.setCrteName(userDTO.getName());
    outptClassesDTO.setCrteTime(DateUtils.getNow());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.insert(map);
  }


  /**
   * @Menthod delete()
   * @Desrciption 删除班次
   * @Param
   * 1. [ids] 主键id
   * @Author zhangxuan
   * @Date   2020/7/28 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/delete")
  public WrapperResponse<Boolean> delete(@RequestBody OutptClassesDTO outptClassesDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.delete(map);
  }

  /**
   * @Menthod update()
   * @Desrciption  修改班次信息
   * @Param
   *  1. outptClassesDTO  班次数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.bspl.dto.outptClassesDTO>
   **/
  @PostMapping("/update")
  public WrapperResponse<Boolean> update(@RequestBody OutptClassesDTO outptClassesDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outptClassesDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outptClassesDTO",outptClassesDTO);
    return outptClassesService_consumer.update(map);
  }





}

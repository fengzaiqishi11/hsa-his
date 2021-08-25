package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.SnowflakeUtils;
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
 * @Class_name: BasePreferentialController
 * @Describe:   优惠配置信息维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 10:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/basePreferential")
@Slf4j
public class BasePreferentialController extends BaseController {

  /**
   * 优惠配置信息维护dubbo消费者接口
   */
  @Resource
  private BasePreferentialService basePreferentialService_consumer;

  /**
  * @Menthod getById()
  * @Desrciption 根据id查询优惠配置信息
  *
  * @Param
  * [1. BasePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/16 14:14
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
  **/
  @GetMapping("/getById")
  public WrapperResponse<BasePreferentialDTO> getById(BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("id",basePreferentialDTO.getId());
    map.put("hospCode",sysUserDTO.getHospCode());
    return basePreferentialService_consumer.getById(map);
  }

  /**
  * @Menthod queryPage()
  * @Desrciption 按条件分页查询优惠配置信息
  *
  * @Param
  * [
   * 1.BasePreferentialDTO 优惠配置数据传输DTO对象
   * 2.pageNo 分页页数
   * 3.pageSize  每页显示条数
   * ]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 14:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryPage")
  public WrapperResponse<PageDTO> queryPage(BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      basePreferentialDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("basePreferentialDTO",basePreferentialDTO);
      return basePreferentialService_consumer.queryPage(map);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询全部优惠配置信息
  *
  * @Param
  * [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/19 17:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryAll")
  public WrapperResponse<List<BasePreferentialDTO>> queryAll(BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialDTO",basePreferentialDTO);
    return basePreferentialService_consumer.queryAll(map);
  }

  /**
  * @Menthod insert()
  * @Desrciption 新增一条优惠配置信息
  *
  * @Param
  * [1. BasePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 16:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/insert")
  public WrapperResponse<Boolean> insert(@RequestBody BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
    basePreferentialDTO.setId(SnowflakeUtils.getId());
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialDTO.setHospCode(sysUserDTO.getHospCode());
    basePreferentialDTO.setCrteId(sysUserDTO.getId());
    basePreferentialDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialDTO",basePreferentialDTO);
    return basePreferentialService_consumer.insert(map);
  }

  /**
  * @Menthod delete()
  * @Desrciption 通过id删除优惠配置信息
  *
  * @Param
  * [1. ids]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 16:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/delete")
  public WrapperResponse<Boolean> delete(@RequestBody BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialDTO",basePreferentialDTO);
    return basePreferentialService_consumer.delete(map);
  }

  /**
  * @Menthod update()
  * @Desrciption  修改优惠配置信息
  *
  * @Param
  * [1. basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 16:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/update")
  public WrapperResponse<Boolean> update(@RequestBody BasePreferentialDTO basePreferentialDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialDTO",basePreferentialDTO);
    return basePreferentialService_consumer.update(map);
  }

  /**
  * @Menthod queryBsplTypePage
  * @Desrciption  分页查询优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/5 17:14
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryBsplTypePage")
  public WrapperResponse<PageDTO> queryBsplTypePage(BasePreferentialTypeDTO basePreferentialTypeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialTypeDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialTypeDTO",basePreferentialTypeDTO);
    return basePreferentialService_consumer.queryBsplTypePage(map);
  }

  /**
  * @Menthod queryBsplTypeAll()
  * @Desrciption 查询所有的优惠类型填充下拉框
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 15:09
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryBsplTypeAll")
  public WrapperResponse<List<BasePreferentialTypeDTO>> queryBsplTypeAll(BasePreferentialTypeDTO basePreferentialTypeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialTypeDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialTypeDTO",basePreferentialTypeDTO);
    return basePreferentialService_consumer.queryBsplTypeAll(map);
  }


  /**
  * @Menthod save()
  * @Desrciption 修改和增加
  *
  * @Param
  * [basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 10:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/saveBsplType")
  public WrapperResponse<Boolean> saveBsplType(@RequestBody BasePreferentialTypeDTO basePreferentialTypeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialTypeDTO.setHospCode(sysUserDTO.getHospCode());
    basePreferentialTypeDTO.setCrteId(sysUserDTO.getId());
    basePreferentialTypeDTO.setCrteName(sysUserDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialTypeDTO",basePreferentialTypeDTO);
    return basePreferentialService_consumer.saveBsplType(map);
  }

  /**
  * @Menthod updateBsplTypeStatus()
  * @Desrciption 批量删除优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 16:41
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/updateBsplTypeStatus")
  public WrapperResponse<Boolean> updateBsplTypeStatus(@RequestBody BasePreferentialTypeDTO basePreferentialTypeDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    basePreferentialTypeDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("basePreferentialTypeDTO",basePreferentialTypeDTO);
    return basePreferentialService_consumer.updateBsplTypeStatus(map);
  }
}

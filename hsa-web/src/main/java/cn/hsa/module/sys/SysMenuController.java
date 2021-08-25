package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
import cn.hsa.module.sys.menu.service.SysMenuService;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
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
 * @Package_name: cn.hsa.module.sys
 * @Class_name: SysMenuController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 13:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/sys/sysMenu")
@Slf4j
public class SysMenuController extends BaseController {

  /**
   * 菜单管理维护dubbo消费者接口
   */
  @Resource
  private SysMenuService sysMenuService_consumer;

  /**
   * 单据消费者
   */
  @Resource
  private BaseOrderRuleService baseOrderRuleService_consumer;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.menu.dto.SysMenuDTO>
  **/
  @GetMapping("getMenuById")
  public WrapperResponse<SysMenuDTO> getMenuById(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.getMenuById(map);
  }
  /**
  * @Menthod queryMenu()
  * @Desrciption 按条件查询系统菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.menu.dto.SysMenuDTO>
  *
   * @return*/
  @GetMapping("queryMenu")
  public WrapperResponse<List<SysMenuDTO>> queryMenu(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.queryMenu(map);
  }

  /**
  * @Menthod getMenuTree()
  * @Desrciption 获取菜单树
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 10:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getMenuTree")
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.getMenuTree(map);
  }

  /**
  * @Menthod getMenuAndBtn()
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 20:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getMenuAndBtn")
  public WrapperResponse<List<TreeMenuNode>> getMenuAndBtn(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.getMenuAndBtn(map);
  }
  /**
  * @Menthod save()
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [1.sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> save(@RequestBody SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("typeCode", "10");
    String orderNo = baseOrderRuleService_consumer.getOrderNo(map).getData();

    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    sysMenuDTO.setCrteId(sysUserDTO.getId());
    sysMenuDTO.setCrteName(sysUserDTO.getName());
    map.put("code", orderNo);
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.save(map);
  }

  /**
  * @Menthod deleteMenu()
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteMenu")
  public WrapperResponse<Boolean> deleteMenu(@RequestBody SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.deleteMenu(map);
  }

  /**
  * @Menthod queryMenuButton()
  * @Desrciption 根据条件查询系统按钮
  *
  * @Param
  * [1. sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 16:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>>
  **/
  @GetMapping("/queryMenuButton")
  public WrapperResponse<List<SysMenuButtonDTO>> queryMenuButton(SysMenuButtonDTO sysMenuButtonDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuButtonDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuButtonDTO",sysMenuButtonDTO);
    return sysMenuService_consumer.queryMenuButton(map);
  }

  /**
  * @Menthod saveBtn()
  * @Desrciption 保存按钮信息（增加和修改）
  *
  * @Param
  * [sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/4 15:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/saveBtn")
  public WrapperResponse<Boolean> saveBtn(@RequestBody SysMenuButtonDTO sysMenuButtonDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("typeCode", "10");
    String orderNo = baseOrderRuleService_consumer.getOrderNo(map).getData();
    map.remove("typeCode");

    sysMenuButtonDTO.setHospCode(sysUserDTO.getHospCode());
    sysMenuButtonDTO.setCrteId(sysUserDTO.getId());
    sysMenuButtonDTO.setCrteName(sysUserDTO.getName());

    map.put("code", orderNo);
    map.put("sysMenuButtonDTO",sysMenuButtonDTO);
    return sysMenuService_consumer.saveBtn(map);
  }

  /**
  * @Menthod deleteMenuButton（）
  * @Desrciption 删除按钮信息
  *
  * @Param
  * [sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/4 15:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteMenuButton")
  public WrapperResponse<Boolean> deleteMenuButton(@RequestBody SysMenuButtonDTO sysMenuButtonDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuButtonDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuButtonDTO",sysMenuButtonDTO);
    return sysMenuService_consumer.deleteMenuButton(map);
  }

  /**
  * @Menthod querySystem
  * @Desrciption
  *
  * @Param
  * [sysSystemDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 17:00
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.dto.SysSystemDTO>>
  **/
  @GetMapping("querySystem")
  public WrapperResponse<List<SysSystemDTO>> querySystem(SysSystemDTO sysSystemDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysSystemDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysSystemDTO",sysSystemDTO);
    return sysMenuService_consumer.querySystem(map);
  }

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 10:56
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  @GetMapping("queryMenuSeqNo")
  public WrapperResponse<Integer> queryMenuSeqNo(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.queryMenuSeqNo(map);
  }

  /**
   * @Menthod getMenuTreeNoBtn()
   * @Desrciption  获取菜单树
   *
   * @Param
   * [map]
   *
   * @Author liuliyun
   * @Date   2021/15/25 15:25
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("getMenuTreeNoBtn")
  public WrapperResponse<List<TreeMenuNode>> getMenuTreeNoBtn(SysMenuDTO sysMenuDTO, HttpServletRequest req, HttpServletResponse res){
    SysUserDTO sysUserDTO = getSession(req, res);
    sysMenuDTO.setHospCode(sysUserDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",sysUserDTO.getHospCode());
    map.put("sysMenuDTO",sysMenuDTO);
    return sysMenuService_consumer.getMenuTreeNoBtn(map);
  }


}

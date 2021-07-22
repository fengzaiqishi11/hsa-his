package cn.hsa.sync.syncmenu.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuDTO;
import cn.hsa.module.sync.syncmenu.service.SyncMenuService;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/center/sync/syncMenu")
@Slf4j
public class SyncMenuController extends CenterBaseController {

  /**
   * 菜单管理维护dubbo消费者接口
   */
  @Resource
  private SyncMenuService syncMenuService;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [1. centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.SyncMenuDTO>
  **/
  @GetMapping("getMenuById")
  public WrapperResponse<SyncMenuDTO> getMenuById(SyncMenuDTO centerMenuDTO){
    return syncMenuService.getMenuById(centerMenuDTO);
  }
  /**
  * @Menthod queryMenu()
  * @Desrciption 按条件查询系统菜单
  *
  * @Param
  * [1. centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.SyncMenuDTO>
  *
   * @return*/
  @GetMapping("queryMenu")
  public WrapperResponse<List<SyncMenuDTO>> queryMenu(SyncMenuDTO centerMenuDTO){
    return syncMenuService.queryMenu(centerMenuDTO);
  }

  /**
  * @Menthod getMenuTree()
  * @Desrciption 获取菜单树
  *
  * @Param
  * [1. centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 10:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getMenuTree")
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(SyncMenuDTO centerMenuDTO){
    return syncMenuService.getMenuTree(centerMenuDTO);
  }

  /**
  * @Menthod getMenuAndBtn()
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1. centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 20:48
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("getMenuAndBtn")
  public WrapperResponse<List<TreeMenuNode>> getMenuAndBtn(SyncMenuDTO syncMenuDTO){
    return syncMenuService.getMenuAndBtn(syncMenuDTO);
  }

  /**
  * @Menthod save()
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [1.centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> save(@RequestBody SyncMenuDTO syncMenuDTO){
    Map map = new HashMap();
    syncMenuDTO.setCrteId(userId);
    syncMenuDTO.setCrteName(userName);
    map.put("syncMenuDTO",syncMenuDTO);
    return syncMenuService.save(map);
  }

  /**
  * @Menthod deleteMenu()
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [1. centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteMenu")
  public WrapperResponse<Boolean> deleteMenu(@RequestBody SyncMenuDTO centerMenuDTO){
    return syncMenuService.deleteMenu(centerMenuDTO);
  }

  /**
  * @Menthod queryMenuButton()
  * @Desrciption 根据条件查询系统按钮
  *
  * @Param
  * [1. syncMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 16:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.menu.dto.SyncMenuButtonDTO>>
  **/
  @GetMapping("/queryMenuButton")
  public WrapperResponse<List<SyncMenuButtonDTO>> queryMenuButton(SyncMenuButtonDTO syncMenuButtonDTO){
    return syncMenuService.queryMenuButton(syncMenuButtonDTO);
  }

  /**
  * @Menthod saveBtn()
  * @Desrciption 保存按钮信息（增加和修改）
  *
  * @Param
  * [syncMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/4 15:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/saveBtn")
  public WrapperResponse<Boolean> saveBtn(@RequestBody SyncMenuButtonDTO syncMenuButtonDTO){
    Map map = new HashMap();
    syncMenuButtonDTO.setCrteId(userId);
    syncMenuButtonDTO.setCrteName(userName);
    map.put("syncMenuButtonDTO",syncMenuButtonDTO);
    return syncMenuService.saveBtn(map);
  }

  /**
  * @Menthod deleteMenuButton（）
  * @Desrciption 删除按钮信息
  *
  * @Param
  * [syncMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/4 15:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/deleteMenuButton")
  public WrapperResponse<Boolean> deleteMenuButton(@RequestBody SyncMenuButtonDTO syncMenuButtonDTO){
    return syncMenuService.deleteMenuButton(syncMenuButtonDTO);
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
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.system.dto.SysSystemDTO>>
  **/
  @GetMapping("querySystem")
  public WrapperResponse<List<SysSystemDTO>> querySystem(SysSystemDTO sysSystemDTO){
    return syncMenuService.querySystem(sysSystemDTO);
  }

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 15:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  @GetMapping("queryMenuSeqNo")
  public WrapperResponse<Integer> queryMenuSeqNo(SyncMenuDTO syncMenuDTO){
    return syncMenuService.queryMenuSeqNo(syncMenuDTO);
  }
}

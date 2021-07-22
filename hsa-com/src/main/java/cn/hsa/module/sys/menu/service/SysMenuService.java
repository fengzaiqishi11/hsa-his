package cn.hsa.module.sys.menu.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.menu.service
 * @Class_name: SysMenuApi
 * @Describe: 系统菜单API接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-sys")
public interface SysMenuService {

  /**
  * @Menthod getMenuById()
  * @Desrciption  按主键获取系统菜单
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.menu.dto.SysMenuDTO>
  **/
  @GetMapping("/service/sys/menu/getMenuById")
  WrapperResponse<SysMenuDTO> getMenuById(Map map);

  /**
  * @Menthod queryMenu（）
  * @Desrciption 安条件查询系统菜单
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>>
  **/
  @GetMapping("/service/sys/menu/queryMenu")
  WrapperResponse<List<SysMenuDTO>> queryMenu(Map map);

  /**
  * @Menthod getMenuTree()
  * @Desrciption 获取左侧按钮树
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 9:15
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("/service/sys/menu/getMenuTree")
  WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map);

  /**
  * @Menthod save
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sys/menu/save")
  WrapperResponse<Boolean> save(Map map);

  /**
  * @Menthod deleteMenu（）
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [1。 sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:22
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sys/menu/deleteMenu")
  WrapperResponse<Boolean> deleteMenu(Map map);

  /**
  * @Menthod queyrMenuButton
  * @Desrciption 查询按钮
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 15:20
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>>
  **/
  @GetMapping("/service/sys/menu/queryMenuButton")
  WrapperResponse<List<SysMenuButtonDTO>> queryMenuButton(Map map);

  /**
  * @Menthod saveBtn（）
  * @Desrciption 保存菜单按钮 可编辑修改
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:08
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sys/menu/saveBtn")
  WrapperResponse<Boolean> saveBtn(Map map);

  /**
  * @Menthod deleteMenuButton()
  * @Desrciption 删除按钮菜单
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/sys/menu/deleteMenuButton")
  WrapperResponse<Boolean> deleteMenuButton(Map map);

  /**
  * @Menthod querySystem()
  * @Desrciption  查询子系统填充下拉框
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 16:56
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.dto.SysSystemDTO>>
  **/
  @GetMapping("/service/sys/menu/querySystem")
  WrapperResponse<List<SysSystemDTO>> querySystem(Map map);

  /**
  * @Menthod getMenuAndBtn
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 20:54
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("/service/sys/menu/getMenuAndBtn")
  WrapperResponse<List<TreeMenuNode>> getMenuAndBtn(Map map);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 10:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  WrapperResponse<Integer> queryMenuSeqNo(Map map);

  /**
   * @Menthod getMenuTreeNoBtn()
   * @Desrciption 获取菜单树(不带按钮)
   *
   * @Param
   * [1. map]
   *
   * @Author liuliyun
   * @Date   2021/05/10 15:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("/service/sys/menu/getMenuTreeNoBtn")
  WrapperResponse<List<TreeMenuNode>> getMenuTreeNoBtn(Map map);


}

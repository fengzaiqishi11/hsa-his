package cn.hsa.module.sync.syncmenu.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.menu.bo
 * @Class_name: SyncMenuBO
 * @Describe:  系统菜单BO接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncMenuBO {

  /**
   * @Menthod getMenuById()
   * @Desrciption 按主键获取系统菜单
   *
   * @Param
   * [1. sysMenu]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:15
   * @Return cn.hsa.module.sys.menu.dto.SysMenuDTO
   **/
  SyncMenuDTO getMenuById(SyncMenuDTO sysMenu);

  /**
   * @Menthod queryMenu（）
   * @Desrciption  安条件查询系统菜单
   *
   * @Param
   * [1. sysMenu]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:15
   * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>
   **/
  List<SyncMenuDTO> queryMenu(SyncMenuDTO sysMenu);

  /**
  * @Menthod getMenuTree()
  * @Desrciption 获取菜单树
  *
  * @Param
  * [1. hospCode]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 9:18
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getMenuTree(SyncMenuDTO sysMenu);

  /**
   * @Menthod save()
   * @Desrciption  保存系统菜单按钮（可以修改和增加）
   *
   * @Param
   * [sysMenu]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:16
   * @Return java.lang.Boolean
   **/
  Boolean save(Map map);

  /**
   * @Menthod deleteMenu（）
   * @Desrciption  删除菜单按钮
   *
   * @Param
   * [1. sysMenu]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:18
   * @Return java.lang.Boolean
   **/
  Boolean deleteMenu(SyncMenuDTO sysMenu);

  /**
   * @Menthod queyrMenuButton
   * @Desrciption 按条件查询系统按钮
   *
   * @Param
   * [1. sysMenuButtonDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/31 15:22
   * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>
   **/
  List<SyncMenuButtonDTO> queryMenuButton(SyncMenuButtonDTO sysMenuButtonDTO);

  /**
  * @Menthod saveBtn()
  * @Desrciption 保存菜单按钮 可编辑修改
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:08
  * @Return java.lang.Boolean
  **/
  Boolean saveBtn(Map map);

  /**
  * @Menthod deleteMenuButton()
  * @Desrciption 删除按钮菜单
  *
  * @Param
  * [1. sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:30
  * @Return java.lang.Boolean
  **/
  Boolean deleteMenuButton(SyncMenuButtonDTO sysMenuButtonDTO);
  /**
  * @Menthod querySystem()
  * @Desrciption 查询子系统填充下拉框
  *
  * @Param
  * [sysSystemDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 17:00
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SyncMenuButtonDTO>
  **/
  List<SysSystemDTO> querySystem(SysSystemDTO sysSystemDTO);

  /**
  * @Menthod getMenuAndBtn（）
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1.map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 21:01
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getMenuAndBtn(SyncMenuDTO sysMenu);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 15:40
  * @Return java.lang.Integer
  **/
  Integer queryMenuSeqNo(SyncMenuDTO syncMenuDTO);

}

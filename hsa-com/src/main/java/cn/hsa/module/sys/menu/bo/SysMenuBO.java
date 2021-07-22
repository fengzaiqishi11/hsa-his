package cn.hsa.module.sys.menu.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.menu.bo
 * @Class_name: SysMenuBO
 * @Describe:  系统菜单BO接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysMenuBO {

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
  SysMenuDTO getMenuById(SysMenuDTO sysMenu);

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
  List<SysMenuDTO> queryMenu(SysMenuDTO sysMenu);

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
  List<TreeMenuNode> getMenuTree(SysMenuDTO sysMenu);

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
  Boolean deleteMenu(SysMenuDTO sysMenu);

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
  List<SysMenuButtonDTO> queryMenuButton(SysMenuButtonDTO sysMenuButtonDTO);

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
  Boolean deleteMenuButton(SysMenuButtonDTO sysMenuButtonDTO);
  /**
  * @Menthod querySystem()
  * @Desrciption 查询子系统填充下拉框
  *
  * @Param
  * [sysSystemDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 17:00
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>
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
  List<TreeMenuNode> getMenuAndBtn(SysMenuDTO sysMenu);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 10:37
  * @Return java.lang.Integer
  **/
  Integer queryMenuSeqNo(SysMenuDTO sysMenuDTO);

  /**
   * @Menthod getMenuNoBtn（）
   * @Desrciption 获取菜单(不带按钮)
   *
   * @Param
   * [1.map]
   *
   * @Author liuliyun
   * @Date   2021/5/10 14:51
   * @Return java.util.List<cn.hsa.base.TreeMenuNode>
   **/
  List<TreeMenuNode> getMenuNoBtn(SysMenuDTO sysMenu);

}

package cn.hsa.module.center.centermenu.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.centermenu.dao
 * @Class_name: CenterMenuDAO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 19:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterMenuDAO {
  /**
   * @Menthod getMenuById()
   * @Desrciption  根据主键获取单个菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:10
   * @Return cn.hsa.module.sys.menu.dto.SysMenuDTO
   **/
  CenterMenuDTO getMenuById(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod queryMenu()
   * @Desrciption  按条件查询菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:12
   * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>
   **/
  List<CenterMenuDTO> queryMenu(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod getMenuTree()
   * @Desrciption 获取菜单和按钮树
   *
   * @Param
   * [1. hospCode]
   *
   * @Author jiahong.yang
   * @Date   2020/7/31 9:18
   * @Return java.util.List<cn.hsa.base.TreeMenuNode>
   **/
  List<TreeMenuNode> getMenuTree(CenterMenuDTO centerMenuDTO);


  /**
   * @Menthod insertMenu()
   * @Desrciption 新增一条菜单信息
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:13
   * @Return int
   **/
  int insertMenu(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod updateMenu
   * @Desrciption 更新一条菜单信息
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:13
   * @Return int
   **/
  int updateMenu(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod deleteMenu（）
   * @Desrciption  删除菜单信息
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:13
   * @Return int
   **/
  int deleteMenu(CenterMenuDTO centerMenuDTO);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [syncMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 16:52
  * @Return java.lang.Integer
  **/
  Integer queryMenuSeqNo(CenterMenuDTO centerMenuDTO);

}

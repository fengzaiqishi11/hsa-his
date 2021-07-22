package cn.hsa.module.center.centermenu.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.centermenu.bo
 * @Class_name: CenterMenuBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 19:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface CenterMenuBO {
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
  CenterMenuDTO getMenuById(CenterMenuDTO centerMenuDTO);

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
  List<CenterMenuDTO> queryMenu(CenterMenuDTO centerMenuDTO);

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
  List<TreeMenuNode> getMenuTree(CenterMenuDTO centerMenuDTO);

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
  Boolean deleteMenu(CenterMenuDTO centerMenuDTO);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 16:53
  * @Return java.lang.Integer
  **/
  Integer queryMenuSeqNo(CenterMenuDTO centerMenuDTO);

}

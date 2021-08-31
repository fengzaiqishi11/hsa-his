package cn.hsa.module.sync.syncmenu.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.menu.dao
 * @Class_name:SyncMenuDAO
 * @Describe:  系统菜单数据访问层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncMenuDAO {
    /**
    * @Menthod getMenuById()
    * @Desrciption  根据主键获取单个菜单
    *
    * @Param
    * [1. syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:10
    * @Return cn.hsa.module.sys.menu.dto.SysMenuDTO
    **/
   SyncMenuDTO getMenuById(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod queryMenu()
    * @Desrciption  按条件查询菜单
    *
    * @Param
    * [1. syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:12
    * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>
    **/
    List<SyncMenuDTO> queryMenu(SyncMenuDTO syncMenuDTO);

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
    List<TreeMenuNode> getMenuTree(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod getOnlyMenuTree（）
    * @Desrciption  只查找菜单树提供给动态菜单
    *
    * @Param
    * [syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/6 20:56
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> getOnlyMenuTree(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod insertMenu()
    * @Desrciption 新增一条菜单信息
    *
    * @Param
    * [syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int insertMenu(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod updateMenu
    * @Desrciption 更新一条菜单信息
    *
    * @Param
    * [1. syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int updateMenu(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod deleteMenu（）
    * @Desrciption  删除菜单信息
    *
    * @Param
    * [syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int deleteMenu(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod getMenuButton()
    * @Desrciption  通过主键获取按钮
    *
    * @Param
    * [syncMenuDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:45
    * @Return cn.hsa.module.sys.menu.dto.SyncMenuButtonDTO
    **/
   SyncMenuButtonDTO getMenuButton(SyncMenuDTO syncMenuDTO);

    /**
    * @Menthod queyrMenuButton
    * @Desrciption 按条件查询系统按钮
    *
    * @Param
    * [1. syncMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/7/31 15:22
    * @Return java.util.List<cn.hsa.module.sys.menu.dto.SyncMenuButtonDTO>
    **/
    List<SyncMenuButtonDTO> queryMenuButton(SyncMenuButtonDTO syncMenuButtonDTO);

     /**
     * @Menthod insertMenuButton()
     * @Desrciption 新增菜单按钮
     *
     * @Param
     * [1. syncMenuButtonDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/8/3 16:05
     * @Return int
     **/
    int insertMenuButton(SyncMenuButtonDTO syncMenuButtonDTO);

    /**
    * @Menthod updateMenuButton
    * @Desrciption 修改菜单按钮
    *
    * @Param
    * [syncMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:06
    * @Return int
    **/
    int updateMenuButton(SyncMenuButtonDTO syncMenuButtonDTO);

    /**
    * @Menthod deleteMenuButton（）
    * @Desrciption 删除菜单按钮
    *
    * @Param
    * [1. syncMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:06
    * @Return int
    **/
    int deleteMenuButton(SyncMenuButtonDTO syncMenuButtonDTO);

    /**
    * @Menthod queryIsExitCode
    * @Desrciption 查询统一系统下按钮编码是否存在
    *
    * @Param
    * [syncMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/31 10:41
    * @Return int
    **/
    int queryIsExitCode(SyncMenuButtonDTO syncMenuButtonDTO);

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
    * @Menthod getUsById
    * @Desrciption 根据用户系统id获取用户系统关联代码
    *
    * @Param
    * [sysUserSystemDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/31 10:54
    * @Return cn.hsa.module.sys.user.dto.SysUserSystemDTO
    **/
    SysUserSystemDTO getUsById(SysUserSystemDTO sysUserSystemDTO);

  /**
   * @Menthod queryMenuSeqNo
   * @Desrciption 查询菜单序号自动填充前端
   *
   * @Param
   * [syncMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/9/4 10:37
   * @Return java.lang.Integer
   **/
  Integer queryMenuSeqNo(SyncMenuDTO syncMenuDTO);

  /**
   * @Menthod queryBtnSeqNo
   * @Desrciption 查询按钮序号自动填充前端
   *
   * @Param
   * [syncMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/9/4 11:02
   * @Return java.lang.Integer
   **/
  Integer queryBtnSeqNo(SyncMenuDTO syncMenuDTO);

}

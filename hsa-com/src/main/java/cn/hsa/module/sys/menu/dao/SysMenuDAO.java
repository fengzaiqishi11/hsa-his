package cn.hsa.module.sys.menu.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.sys.menu.dao
 * @Class_name: SysMenuDao
 * @Describe:  系统菜单数据访问层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysMenuDAO {
    /**
    * @Menthod getMenuById()
    * @Desrciption  根据主键获取单个菜单
    *
    * @Param
    * [1. sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:10
    * @Return cn.hsa.module.sys.menu.dto.SysMenuDTO
    **/
    SysMenuDTO getMenuById(SysMenuDTO sysMenu);

    /**
    * @Menthod queryMenu()
    * @Desrciption  按条件查询菜单
    *
    * @Param
    * [1. sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:12
    * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>
    **/
    List<SysMenuDTO> queryMenu(SysMenuDTO sysMenu);

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
    List<TreeMenuNode> getMenuTree(SysMenuDTO sysMenu);

    /**
    * @Menthod getOnlyMenuTree（）
    * @Desrciption  只查找菜单树提供给动态菜单
    *
    * @Param
    * [sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/8/6 20:56
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    List<TreeMenuNode> getOnlyMenuTree(SysMenuDTO sysMenu);

    /**
    * @Menthod insertMenu()
    * @Desrciption 新增一条菜单信息
    *
    * @Param
    * [sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int insertMenu(SysMenuDTO sysMenu);

    /**
    * @Menthod updateMenu
    * @Desrciption 更新一条菜单信息
    *
    * @Param
    * [1. sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int updateMenu(SysMenuDTO sysMenu);

    /**
    * @Menthod deleteMenu（）
    * @Desrciption  删除菜单信息
    *
    * @Param
    * [sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/7/30 11:13
    * @Return int
    **/
    int deleteMenu(SysMenuDTO sysMenu);

    /**
    * @Menthod getMenuButton()
    * @Desrciption  通过主键获取按钮
    *
    * @Param
    * [sysMenu]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:45
    * @Return cn.hsa.module.sys.menu.dto.SysMenuButtonDTO
    **/
    SysMenuButtonDTO getMenuButton(SysMenuDTO sysMenu);

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
     * @Menthod insertMenuButton()
     * @Desrciption 新增菜单按钮
     *
     * @Param
     * [1. sysMenuButtonDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/8/3 16:05
     * @Return int
     **/
    int insertMenuButton(SysMenuButtonDTO sysMenuButtonDTO);

    /**
    * @Menthod updateMenuButton
    * @Desrciption 修改菜单按钮
    *
    * @Param
    * [sysMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:06
    * @Return int
    **/
    int updateMenuButton(SysMenuButtonDTO sysMenuButtonDTO);

    /**
    * @Menthod deleteMenuButton（）
    * @Desrciption 删除菜单按钮
    *
    * @Param
    * [1. sysMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/3 16:06
    * @Return int
    **/
    int deleteMenuButton(SysMenuButtonDTO sysMenuButtonDTO);

    int delectMenuButtonByMenuCode(SysMenuButtonDTO sysMenuButtonDTO);

    /**
    * @Menthod queryIsExitCode
    * @Desrciption 判断模块内编码是否重复
    *
    * @Param
    * [sysMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/24 16:03
    * @Return int
    **/
    int queryIsExitCode(SysMenuButtonDTO sysMenuButtonDTO);

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
    * @Menthod getUsById()
    * @Desrciption 根据用户系统id获取用户系统关联代码
    *
    * @Param
    * [sysUserSystemDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/8/20 15:47
    * @Return cn.hsa.module.sys.user.dto.SysUserSystemDTO
    **/
    SysUserSystemDTO getUsById(SysUserSystemDTO sysUserSystemDTO);

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
    * @Menthod queryBtnSeqNo
    * @Desrciption
    *
    * @Param
    * [sysMenuButtonDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/9/4 11:02
    * @Return java.lang.Integer
    **/
    Integer queryBtnSeqNo(SysMenuDTO sysMenuDTO);

    /**
     * @Menthod getMenuTreeNoBtn()
     * @Desrciption 获取菜单树
     *
     * @Param
     * [1. hospCode]
     *
     * @Author liuliyun
     * @Date   2021/05/10 15:23
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getMenuTreeNoBtn(SysMenuDTO sysMenu);

}

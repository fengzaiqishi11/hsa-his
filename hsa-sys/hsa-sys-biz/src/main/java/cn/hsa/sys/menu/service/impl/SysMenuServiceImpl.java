package cn.hsa.sys.menu.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.menu.bo.SysMenuBO;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
import cn.hsa.module.sys.menu.service.SysMenuService;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.menu.service.impl
 * @Class_name: SysMenuApiImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sys/sysMenu")
@Slf4j
@Service("sysMenuService_provider")
public class SysMenuServiceImpl extends HsafService implements SysMenuService {
  @Resource
  private SysMenuBO sysMenuBO;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 14:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.menu.dto.SysMenuDTO>
  **/
  @Override
  public WrapperResponse<SysMenuDTO> getMenuById(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    return WrapperResponse.success(sysMenuBO.getMenuById(sysMenuDTO));
  }

  /**
  * @Menthod queryMenu()
  * @Desrciption 按条件查询系统菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 14:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>>
  **/
  @Override
  public WrapperResponse<List<SysMenuDTO>> queryMenu(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    return WrapperResponse.success(sysMenuBO.queryMenu(sysMenuDTO));
  }

  /**
  * @Menthod getMenuTree()
  * @Desrciption  获取菜单树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 10:03
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(sysMenuBO.getMenuTree(sysMenuDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
  * @Menthod save()
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 14:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> save(Map map) {
    try {
      SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
      if(sysMenuDTO == null){
        return WrapperResponse.error(400,"参数不能为空",null);
      }
      if (StringUtils.isEmpty(sysMenuDTO.getUrl()) || StringUtils.isEmpty(sysMenuDTO.getName())) {
        return WrapperResponse.error(400,"必填地址不能为空",null);
      }
    }catch (Exception e){
      e.printStackTrace();
      return WrapperResponse.error(500,e.getMessage(),null);
    }
    return WrapperResponse.success(sysMenuBO.save(map));
  }

  /**
  * @Menthod deleteMenu()
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 14:37
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteMenu(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    return WrapperResponse.success(sysMenuBO.deleteMenu(sysMenuDTO));
  }

  /**
  * @Menthod queyrMenuButton
  * @Desrciption 按条件查询系统菜单按钮
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 15:25
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>>
  **/
  @Override
  public WrapperResponse<List<SysMenuButtonDTO>> queryMenuButton(Map map) {
    SysMenuButtonDTO sysMenuButtonDTO = MapUtils.get(map,"sysMenuButtonDTO");
    return WrapperResponse.success(sysMenuBO.queryMenuButton(sysMenuButtonDTO));
  }

  /**
  * @Menthod saveBtn
  * @Desrciption 保存菜单按钮 可编辑修改
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:09
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> saveBtn(Map map) {
    try {
      SysMenuButtonDTO sysMenuButtonDTO = MapUtils.get(map,"sysMenuButtonDTO");
      if(sysMenuButtonDTO == null){
        return WrapperResponse.error(400,"参数不能为空",null);
      }
      if (StringUtils.isEmpty(sysMenuButtonDTO.getCode())) {
        return WrapperResponse.error(400,"编码不能为空",null);
      }
    }catch (Exception e){
      e.printStackTrace();
      return WrapperResponse.error(500,e.getMessage(),null);
    }
    return WrapperResponse.success(sysMenuBO.saveBtn(map));
  }

  /**
  * @Menthod deleteMenuButton()
  * @Desrciption 删除按钮菜单
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:31
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteMenuButton(Map map) {
    SysMenuButtonDTO sysMenuButtonDTO = MapUtils.get(map,"sysMenuButtonDTO");
    return WrapperResponse.success(sysMenuBO.deleteMenuButton(sysMenuButtonDTO));
  }

  /**
  * @Menthod querySystem()
  * @Desrciption 查询子系统填充下拉框
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 17:07
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.dto.SysSystemDTO>>
  **/
  @Override
  public WrapperResponse<List<SysSystemDTO>> querySystem(Map map) {
    SysSystemDTO sysSystemDTO = MapUtils.get(map,"sysSystemDTO");
    return WrapperResponse.success(sysMenuBO.querySystem(sysSystemDTO));
  }

  /**
  * @Menthod getMenuAndBtn（）
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 21:02
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getMenuAndBtn(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(sysMenuBO.getMenuAndBtn(sysMenuDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 10:38
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  @Override
  public WrapperResponse<Integer> queryMenuSeqNo(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    if(StringUtils.isEmpty(sysMenuDTO.getIsMenuOrBtn()) ){
      throw new AppException("参数不能为空");
    }
    return WrapperResponse.success(sysMenuBO.queryMenuSeqNo(sysMenuDTO));
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
  @Override
  public WrapperResponse<List<TreeMenuNode>> getMenuTreeNoBtn(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(sysMenuBO.getMenuNoBtn(sysMenuDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }
}

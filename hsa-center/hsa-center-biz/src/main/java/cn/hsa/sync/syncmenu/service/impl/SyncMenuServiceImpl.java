package cn.hsa.sync.syncmenu.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.syncmenu.bo.SyncMenuBO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuDTO;
import cn.hsa.module.sync.syncmenu.service.SyncMenuService;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
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
@HsafRestPath("/service/sync/syncmenu")
@Slf4j
@Service("syncMenuService_provider")
public class SyncMenuServiceImpl extends HsafService implements SyncMenuService {

  @Resource
  private SyncMenuBO syncMenuBO;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [1. sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 14:36
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.SyncMenuDTO>
  **/
  @Override
  public WrapperResponse<SyncMenuDTO> getMenuById(SyncMenuDTO sysMenuDTO) {
    return WrapperResponse.success(syncMenuBO.getMenuById(sysMenuDTO));
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
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.menu.dto.SyncMenuDTO>>
  **/
  @Override
  public WrapperResponse<List<SyncMenuDTO>> queryMenu(SyncMenuDTO sysMenuDTO) {
    return WrapperResponse.success(syncMenuBO.queryMenu(sysMenuDTO));
  }

  /**
  * @Menthod getMenuTree()
  * @Desrciption  获取菜单树
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 10:03
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(SyncMenuDTO sysMenuDTO) {
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(syncMenuBO.getMenuTree(sysMenuDTO), "-1");
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
    return WrapperResponse.success(syncMenuBO.save(map));
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
  public WrapperResponse<Boolean> deleteMenu(SyncMenuDTO sysMenuDTO) {
    return WrapperResponse.success(syncMenuBO.deleteMenu(sysMenuDTO));
  }

  /**
  * @Menthod queyrMenuButton
  * @Desrciption 按条件查询系统菜单按钮
  *
  * @Param
  * [sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 15:25
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.menu.dto.SyncMenuButtonDTO>>
  **/
  @Override
  public WrapperResponse<List<SyncMenuButtonDTO>> queryMenuButton(SyncMenuButtonDTO sysMenuButtonDTO) {
    return WrapperResponse.success(syncMenuBO.queryMenuButton(sysMenuButtonDTO));
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
    return WrapperResponse.success(syncMenuBO.saveBtn(map));
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
  public WrapperResponse<Boolean> deleteMenuButton(SyncMenuButtonDTO sysMenuButtonDTO) {
    return WrapperResponse.success(syncMenuBO.deleteMenuButton(sysMenuButtonDTO));
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
  public WrapperResponse<List<SysSystemDTO>> querySystem(SysSystemDTO sysSystemDTO) {
    return WrapperResponse.success(syncMenuBO.querySystem(sysSystemDTO));
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
  public WrapperResponse<List<TreeMenuNode>> getMenuAndBtn(SyncMenuDTO syncMenuDTO) {
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(syncMenuBO.getMenuAndBtn(syncMenuDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  @Override
  public WrapperResponse<Integer> queryMenuSeqNo(SyncMenuDTO syncMenuDTO) {
    if(StringUtils.isEmpty(syncMenuDTO.getIsMenuOrBtn()) ){
      throw new AppException("参数不能为空");
    }
    return WrapperResponse.success(syncMenuBO.queryMenuSeqNo(syncMenuDTO));
  }
}

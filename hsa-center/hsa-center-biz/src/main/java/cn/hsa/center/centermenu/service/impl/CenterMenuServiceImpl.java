package cn.hsa.center.centermenu.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.centermenu.bo.CenterMenuBO;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;
import cn.hsa.module.center.centermenu.service.CenterMenuService;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.centermenu.service.impl
 * @Class_name: CenterMenuServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/menu")
@Slf4j
@Service("centerMenuService_provider")
public class CenterMenuServiceImpl implements CenterMenuService {

  @Resource
  private CenterMenuBO centerMenuBO;
  /**
   * @Menthod getMenuById()
   * @Desrciption 按主键获取系统菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 14:36
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.CenterMenuDTO>
   **/
  @Override
  public WrapperResponse<CenterMenuDTO> getMenuById(CenterMenuDTO centerMenuDTO) {
    return WrapperResponse.success(centerMenuBO.getMenuById(centerMenuDTO));
  }

  /**
   * @Menthod queryMenu()
   * @Desrciption 按条件查询系统菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 14:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.menu.dto.CenterMenuDTO>>
   **/
  @Override
  public WrapperResponse<List<CenterMenuDTO>> queryMenu(CenterMenuDTO centerMenuDTO) {
    return WrapperResponse.success(centerMenuBO.queryMenu(centerMenuDTO));
  }

  /**
   * @Menthod getMenuTree()
   * @Desrciption  获取菜单树
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/31 10:03
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(CenterMenuDTO centerMenuDTO) {
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(centerMenuBO.getMenuTree(centerMenuDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
   * @Menthod save()
   * @Desrciption 保存系统菜单按钮（可以修改和增加）
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 14:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> save(Map map) {
    return WrapperResponse.success(centerMenuBO.save(map));
  }

  /**
   * @Menthod deleteMenu()
   * @Desrciption 删除菜单按钮
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 14:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> deleteMenu(CenterMenuDTO centerMenuDTO) {
    return WrapperResponse.success(centerMenuBO.deleteMenu(centerMenuDTO));
  }

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 16:54
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  @Override
  public WrapperResponse<Integer> queryMenuSeqNo(CenterMenuDTO centerMenuDTO) {
    return WrapperResponse.success(centerMenuBO.queryMenuSeqNo(centerMenuDTO));
  }

}

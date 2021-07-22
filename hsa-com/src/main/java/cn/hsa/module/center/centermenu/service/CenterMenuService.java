package cn.hsa.module.center.centermenu.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.centermenu.service
 * @Class_name: CenterMenuService
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 19:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-center")
public interface CenterMenuService {
  /**
   * @Menthod getMenuById()
   * @Desrciption  按主键获取系统菜单
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.menu.dto.SysMenuDTO>
   **/
  @GetMapping("/service/center/menu/getMenuById")
  WrapperResponse<CenterMenuDTO> getMenuById(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod queryMenu（）
   * @Desrciption 安条件查询系统菜单
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>>
   **/
  @GetMapping("/service/center/menu/queryMenu")
  WrapperResponse<List<CenterMenuDTO>> queryMenu(CenterMenuDTO centerMenuDTO);

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
  @GetMapping("/service/center/menu/getMenuTree")
  WrapperResponse<List<TreeMenuNode>> getMenuTree(CenterMenuDTO centerMenuDTO);

  /**
   * @Menthod save
   * @Desrciption 保存系统菜单按钮（可以修改和增加）
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/service/center/menu/save")
  WrapperResponse<Boolean> save(Map map);

  /**
   * @Menthod deleteMenu（）
   * @Desrciption 删除菜单按钮
   *
   * @Param
   * [1。 centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/service/center/menu/deleteMenu")
  WrapperResponse<Boolean> deleteMenu(CenterMenuDTO centerMenuDTO);

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 16:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
  **/
  @GetMapping("/service/center/menu/queryMenuSeqNo")
  WrapperResponse<Integer> queryMenuSeqNo(CenterMenuDTO centerMenuDTO);
}

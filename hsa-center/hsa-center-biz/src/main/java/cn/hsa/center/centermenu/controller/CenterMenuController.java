package cn.hsa.center.centermenu.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;
import cn.hsa.module.center.centermenu.service.CenterMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.centermenu.controller
 * @Class_name: CenterMenuController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("center/center/centerMenu")
@Slf4j
public class CenterMenuController extends CenterBaseController {
  /**
   * 菜单管理维护dubbo消费者接口
   */
  @Resource
  private CenterMenuService centerMenuService;



  /**
   * @Menthod getMenuById()
   * @Desrciption 按主键获取系统菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 13:52
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.CenterMenuDTO>
   **/
  @GetMapping("getMenuById")
  public WrapperResponse<CenterMenuDTO> getMenuById(CenterMenuDTO centerMenuDTO){
    return centerMenuService.getMenuById(centerMenuDTO);
  }
  /**
   * @Menthod queryMenu()
   * @Desrciption 按条件查询系统菜单
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 13:52
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.CenterMenuDTO>
   *
   * @return*/
  @GetMapping("queryMenu")
  public WrapperResponse<List<CenterMenuDTO>> queryMenu(CenterMenuDTO centerMenuDTO){
    return centerMenuService.queryMenu(centerMenuDTO);
  }

  /**
   * @Menthod getMenuTree()
   * @Desrciption 获取菜单树
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/31 10:12
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("getMenuTree")
  public WrapperResponse<List<TreeMenuNode>> getMenuTree(CenterMenuDTO centerMenuDTO){
    return centerMenuService.getMenuTree(centerMenuDTO);
  }

  /**
   * @Menthod save()
   * @Desrciption 保存系统菜单按钮（可以修改和增加）
   *
   * @Param
   * [1.centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 13:52
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/save")
  public WrapperResponse<Boolean> save(@RequestBody CenterMenuDTO centerMenuDTO){
    Map map = new HashMap();
    centerMenuDTO.setCrteId(userId);
    centerMenuDTO.setCrteName(userName);
    map.put("centerMenuDTO",centerMenuDTO);
    return centerMenuService.save(map);
  }

  /**
   * @Menthod deleteMenu()
   * @Desrciption 删除菜单按钮
   *
   * @Param
   * [1. centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 13:52
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/deleteMenu")
  public WrapperResponse<Boolean> deleteMenu(@RequestBody CenterMenuDTO centerMenuDTO){
    return centerMenuService.deleteMenu(centerMenuDTO);
  }

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
  @GetMapping("queryMenuSeqNo")
  public WrapperResponse<Integer> queryMenuSeqNo(CenterMenuDTO centerMenuDTO){
    return centerMenuService.queryMenuSeqNo(centerMenuDTO);
  }
}

package cn.hsa.sync.syncmenu.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.syncmenu.bo.SyncMenuBO;
import cn.hsa.module.sync.syncmenu.dao.SyncMenuDAO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuButtonDTO;
import cn.hsa.module.sync.syncmenu.dto.SyncMenuDTO;
import cn.hsa.module.sys.system.dto.SysSystemDTO;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.menu.bo.impl
 * @Class_name: SysMenuBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SyncMenuBOImpl extends HsafBO implements SyncMenuBO {

  @Resource
  private SyncMenuDAO syncMenuDAO;

  /**
   * 单据消费者
   */
  @Resource
  private SyncOrderRuleService syncOrderRuleService;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [SyncMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return cn.hsa.module.center.menu.dto.SyncMenuButtonDTO
  **/
  @Override
  public SyncMenuDTO getMenuById(SyncMenuDTO syncMenuDTO) {
    return syncMenuDAO.getMenuById(syncMenuDTO);
  }

  /**
  * @Menthod queryMenu()
  * @Desrciption 按条件查询系统菜单
  *
  * @Param
  * [1. SyncMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return java.util.List<cn.hsa.module.center.menu.dto.SyncMenuButtonDTO>
  **/
  @Override
  public List<SyncMenuDTO> queryMenu(SyncMenuDTO syncMenuDTO) {
    return syncMenuDAO.queryMenu(syncMenuDTO);
  }

  /**
  * @Menthod getMenuTree()
  * @Desrciption 获取菜单按钮
  *
  * @Param
  * [hospCode]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 9:20
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  @Override
  public List<TreeMenuNode> getMenuTree(SyncMenuDTO syncMenuDTO) {
    return syncMenuDAO.getMenuTree(syncMenuDTO);
  }

  /**
  * @Menthod save()
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [centerMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean save(Map map) {
    SyncMenuDTO syncMenuDTO = MapUtils.get(map,"syncMenuDTO");
    //新建一个为上级菜单
    SyncMenuDTO syncMenuDTO1 = new SyncMenuDTO();

    if(StringUtils.isEmpty(syncMenuDTO.getId())){

      String orderNo = syncOrderRuleService.getOrderNo("10").getData();

      syncMenuDTO.setId(SnowflakeUtils.getId());
      syncMenuDTO.setCode(orderNo);
      //新增状态为菜单
      syncMenuDTO.setTypeCode("1");

      //如果有上级菜单,则修改上级菜单为目录，不能再新增按钮
      if(!StringUtils.isEmpty(syncMenuDTO.getUpCode())){
        //当在目录菜单下新增菜单时修改该菜单为根菜单
        syncMenuDTO1.setTypeCode("0");
        //给上级菜单添加编码
        syncMenuDTO1.setCode(syncMenuDTO.getUpCode());
        //跟新上级菜单状态
        syncMenuDAO.updateMenu(syncMenuDTO1);
      }
      syncMenuDTO.setCrteTime(DateUtils.getNow());
      syncMenuDAO.insertMenu(syncMenuDTO);
    }else{
      syncMenuDAO.updateMenu(syncMenuDTO);
    }
    return true;
  }

  /**
  * @Menthod deleteMenu()
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [syncMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:31
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteMenu(SyncMenuDTO syncMenuDTO) {
    SyncMenuDTO syncMenuDTO1 = new SyncMenuDTO();
    syncMenuDTO1.setUpCode(syncMenuDTO.getUpCode());
    syncMenuDTO1.setIsValid("1");
    //判断删除该节点后该菜单是否为最末节点
    List<SyncMenuDTO> syncMenuDTOS = syncMenuDAO.queryMenu(syncMenuDTO1);
    if(syncMenuDTOS.size() <= 1){
      //如果该节点的父节点只有一个孩子,删除后让该节点为最末级
      SyncMenuDTO update = new SyncMenuDTO();
      update.setTypeCode("1");
      update.setCode(syncMenuDTO.getUpCode());
      syncMenuDAO.updateMenu(update);
    }
    return syncMenuDAO.deleteMenu(syncMenuDTO) > 0;
  }

  /**
  * @Menthod queyrMenuButton()
  * @Desrciption 按条件查询按钮
  *
  * @Param
  * [1。 syncMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 15:24
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SyncMenuButtonDTO>
  **/
  @Override
  public List<SyncMenuButtonDTO> queryMenuButton(SyncMenuButtonDTO syncMenuButtonDTO) {
    return syncMenuDAO.queryMenuButton(syncMenuButtonDTO);
  }

  /**
  * @Menthod saveBtn
  * @Desrciption 保存菜单按钮 可编辑修改
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:10
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean saveBtn(Map map) {
    SyncMenuButtonDTO syncMenuButtonDTO = MapUtils.get(map,"syncMenuButtonDTO");
    int i = syncMenuDAO.queryIsExitCode(syncMenuButtonDTO);
    if(i > 0){
      throw new AppException("编码重复");
    }
    if(StringUtils.isEmpty(syncMenuButtonDTO.getId())){
      syncMenuButtonDTO.setId(SnowflakeUtils.getId());
      syncMenuButtonDTO.setCrteTime(DateUtils.getNow());
      syncMenuDAO.insertMenuButton(syncMenuButtonDTO);
    }else{
      syncMenuDAO.updateMenuButton(syncMenuButtonDTO);
    }
    return true;
  }

  /**
  * @Menthod deleteMenuButton()
  * @Desrciption 删除按钮菜单
  *
  * @Param
  * [1. syncMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:32
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteMenuButton(SyncMenuButtonDTO syncMenuButtonDTO) {
    return syncMenuDAO.deleteMenuButton(syncMenuButtonDTO) > 0;
  }

  /**
  * @Menthod querySystem
  * @Desrciption 查询子系统填充下拉框
  *
  * @Param
  * [sysSystemDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 17:04
  * @Return java.util.List<cn.hsa.module.center.menu.dto.SyncMenuButtonDTO>
  **/
  @Override
  public List<SysSystemDTO> querySystem(SysSystemDTO sysSystemDTO) {
    return syncMenuDAO.querySystem(sysSystemDTO);
  }

  /**
  * @Menthod getMenuAndBtn
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *
  * @Param
  * [1. syncMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 21:03
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  @Override
  public List<TreeMenuNode> getMenuAndBtn(SyncMenuDTO syncMenuDTO) {

    if(StringUtils.isEmpty(syncMenuDTO.getUsID())){
      throw new AppException("登录异常");
    }
    //新建用户系统关系实例
    SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();

    //添加usCODE:用户系统关系ID
    sysUserSystemDTO.setId(syncMenuDTO.getUsID());

    //根据用户系统id获取用户系统关联代码
    SysUserSystemDTO usById = syncMenuDAO.getUsById(sysUserSystemDTO);

    SyncMenuButtonDTO syncMenuButtonDTO = new SyncMenuButtonDTO();

    //添加usCODE:用户系统关系代码
    syncMenuButtonDTO.setUsCode(usById.getUsCode());

    syncMenuDTO.setUsCode(usById.getUsCode());

    //查出角色所拥有的菜单
    List<TreeMenuNode> onlyMenuTree = syncMenuDAO.getOnlyMenuTree(syncMenuDTO);
    if(ListUtils.isEmpty(onlyMenuTree)){
      throw new AppException("获取菜单为空");
    }

    //查询角色下所有用的按钮
    List<SyncMenuButtonDTO> syncMenuButtonDTOS1 = syncMenuDAO.queryMenuButton(syncMenuButtonDTO);
    //如果为空则返回所有菜单
    if(ListUtils.isEmpty(syncMenuButtonDTOS1)){
      return onlyMenuTree;
    }

    for (int i = 0; i < onlyMenuTree.size(); i++) {
      List<SyncMenuButtonDTO> syncMenuButtonDTOS = new ArrayList<>();
      for (int j = 0; j < syncMenuButtonDTOS1.size(); j++) {
        //如果该按钮为菜单的子节点，则加入该菜单的按钮列表
        if(onlyMenuTree.get(i).getId().equals(syncMenuButtonDTOS1.get(j).getMenuCode())){
          syncMenuButtonDTOS.add(syncMenuButtonDTOS1.get(j));
        }
      }
      onlyMenuTree.get(i).setSyncMenuButtonDTOS(syncMenuButtonDTOS);
    }
    return onlyMenuTree;
  }

  @Override
  public Integer queryMenuSeqNo(SyncMenuDTO syncMenuDTO) {
    if("1".equals(syncMenuDTO.getIsMenuOrBtn())){
      return syncMenuDAO.queryMenuSeqNo(syncMenuDTO);
    }else{
      return syncMenuDAO.queryBtnSeqNo(syncMenuDTO);
    }
  }
}

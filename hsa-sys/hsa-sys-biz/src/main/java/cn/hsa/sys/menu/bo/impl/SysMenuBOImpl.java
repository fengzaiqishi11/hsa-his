package cn.hsa.sys.menu.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.menu.bo.SysMenuBO;
import cn.hsa.module.sys.menu.dao.SysMenuDAO;
import cn.hsa.module.sys.menu.dto.SysMenuButtonDTO;
import cn.hsa.module.sys.menu.dto.SysMenuDTO;
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
 * @Package_name: cn.hsa.sys.menu.bo.impl
 * @Class_name: SysMenuBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/30 10:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SysMenuBOImpl extends HsafBO implements SysMenuBO {

  @Resource
  private SysMenuDAO sysMenuDAO;

  /**
  * @Menthod getMenuById()
  * @Desrciption 按主键获取系统菜单
  *
  * @Param
  * [sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return cn.hsa.module.sys.menu.dto.SysMenuDTO
  **/
  @Override
  public SysMenuDTO getMenuById(SysMenuDTO sysMenu) {
    return sysMenuDAO.getMenuById(sysMenu);
  }

  /**
  * @Menthod queryMenu()
  * @Desrciption 按条件查询系统菜单
  *
  * @Param
  * [1. sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuDTO>
  **/
  @Override
  public List<SysMenuDTO> queryMenu(SysMenuDTO sysMenu) {
    return sysMenuDAO.queryMenu(sysMenu);
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
  public List<TreeMenuNode> getMenuTree(SysMenuDTO sysMenu) {

    //判断有没有搜索关键字
    if(!StringUtils.isEmpty(sysMenu.getKeyword())){

      //用来接受筛选后的菜单和按钮
      List<TreeMenuNode> resultMenuNodeList = new ArrayList<>();
      SysMenuDTO sysMenuDTO = new SysMenuDTO();
      sysMenuDTO.setHospCode(sysMenu.getHospCode());

      //查出所有的按钮和菜单的树形
      List<TreeMenuNode> allMenuAndBtn = sysMenuDAO.getMenuTree(sysMenuDTO);

      //查出按菜单名称全部查出的菜单
      List<TreeMenuNode> selectThemenu = sysMenuDAO.getOnlyMenuTree(sysMenu);
      resultMenuNodeList.addAll(selectThemenu);
      if(selectThemenu.size() > 0){
        for (int i = 0; i < selectThemenu.size(); i++) {
          //循环递归找出
          resultMenuNodeList = getMenuTreeByName(allMenuAndBtn, selectThemenu.get(i),resultMenuNodeList);
        }
      }

      return resultMenuNodeList;
    }
    return sysMenuDAO.getMenuTree(sysMenu);
  }

  /**
  * @Menthod getMenuTree
  * @Desrciption  递归找出该菜单的所有按钮和上级菜单
  *
  * @Param
  * [sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/8/7 17:09
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  public List<TreeMenuNode> getMenuTreeByName( List<TreeMenuNode> allMenuAndBtn,TreeMenuNode selectThemenu,List<TreeMenuNode> resultMenuNodeList) {

      for (int j = 0; j < allMenuAndBtn.size(); j++) {
        //查出该菜单按钮和上级菜单
        if(selectThemenu.getParentId().equals(allMenuAndBtn.get(j).getId())){
          resultMenuNodeList.add(allMenuAndBtn.get(j));
          getMenuTreeByName(allMenuAndBtn,allMenuAndBtn.get(j),resultMenuNodeList);
        }
        //只查他所带的下级按钮
        else if(allMenuAndBtn.get(j).getIsMenuOrBtn().equals("2") && selectThemenu.getId().equals(allMenuAndBtn.get(j).getParentId())){
          resultMenuNodeList.add(allMenuAndBtn.get(j));
        }
    }
    return resultMenuNodeList;
  }

  /**
  * @Menthod save()
  * @Desrciption 保存系统菜单按钮（可以修改和增加）
  *
  * @Param
  * [sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:30
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean save(Map map) {
    SysMenuDTO sysMenuDTO = MapUtils.get(map,"sysMenuDTO");
    //新建一个为上级菜单
    SysMenuDTO sysMenuDTO1 = new SysMenuDTO();

    if(StringUtils.isEmpty(sysMenuDTO.getId())){

      sysMenuDTO.setId(SnowflakeUtils.getId());
      sysMenuDTO.setCode(MapUtils.get(map,"code"));
      //新增状态为菜单
      sysMenuDTO.setTypeCode("1");

      //如果有上级菜单,则修改上级菜单为目录，不能再新增按钮
      if(!StringUtils.isEmpty(sysMenuDTO.getUpCode())){
        //设置医院编码
        sysMenuDTO1.setHospCode(sysMenuDTO.getHospCode());
        //当在目录菜单下新增菜单时修改该菜单为根菜单
        sysMenuDTO1.setTypeCode("0");
        //给上级菜单添加编码
        sysMenuDTO1.setCode(sysMenuDTO.getUpCode());
        //跟新上级菜单状态
        sysMenuDAO.updateMenu(sysMenuDTO1);
      }
      sysMenuDTO.setCrteTime(DateUtils.getNow());
      sysMenuDAO.insertMenu(sysMenuDTO);

    }else{

      sysMenuDAO.updateMenu(sysMenuDTO);
    }
    return true;
  }

  /**
  * @Menthod deleteMenu()
  * @Desrciption 删除菜单按钮
  *
  * @Param
  * [sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/7/30 11:31
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteMenu(SysMenuDTO sysMenu) {
    SysMenuDTO sysMenu1 = new SysMenuDTO();
    sysMenu1.setHospCode(sysMenu.getHospCode());
    sysMenu1.setUpCode(sysMenu.getUpCode());
    sysMenu1.setIsValid("1");
    //判断删除该节点后，他的上级菜单是否为最末节点
    List<SysMenuDTO> sysMenuDTOS = sysMenuDAO.queryMenu(sysMenu1);
    if(sysMenuDTOS.size() <= 1){
      //如果该节点的父节点只有一个孩子,删除后让该节点为最末级
      SysMenuDTO update = new SysMenuDTO();
      update.setTypeCode("1");
      update.setHospCode(sysMenu.getHospCode());
      update.setCode(sysMenu.getUpCode());
      sysMenuDAO.updateMenu(update);
    }
    // 使该菜单的状态为无效
    sysMenuDAO.deleteMenu(sysMenu);
    SysMenuButtonDTO sysMenuButtonDTO = new SysMenuButtonDTO();
    sysMenuButtonDTO.setHospCode(sysMenu.getHospCode());
    sysMenuButtonDTO.setMenuCode(sysMenu.getCode());
    // 删除该菜单下的按钮
    sysMenuDAO.deleteMenuButton(sysMenuButtonDTO);
    return true;
  }

  /**
  * @Menthod queyrMenuButton()
  * @Desrciption 按条件查询按钮
  *
  * @Param
  * [1。 sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/31 15:24
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>
  **/
  @Override
  public List<SysMenuButtonDTO> queryMenuButton(SysMenuButtonDTO sysMenuButtonDTO) {
    return sysMenuDAO.queryMenuButton(sysMenuButtonDTO);
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
    SysMenuButtonDTO sysMenuButtonDTO = MapUtils.get(map,"sysMenuButtonDTO");
    int i = sysMenuDAO.queryIsExitCode(sysMenuButtonDTO);
    if(i > 0){
      throw new AppException("编码重复");
    }
    if(StringUtils.isEmpty(sysMenuButtonDTO.getId())){
      sysMenuButtonDTO.setId(SnowflakeUtils.getId());
      sysMenuButtonDTO.setCrteTime(DateUtils.getNow());
      sysMenuDAO.insertMenuButton(sysMenuButtonDTO);
    }else{
      sysMenuDAO.updateMenuButton(sysMenuButtonDTO);
    }
    return true;
  }

  /**
  * @Menthod deleteMenuButton()
  * @Desrciption 删除按钮菜单
  *
  * @Param
  * [1. sysMenuButtonDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/3 16:32
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteMenuButton(SysMenuButtonDTO sysMenuButtonDTO) {
    return sysMenuDAO.deleteMenuButton(sysMenuButtonDTO) > 0;
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
  * @Return java.util.List<cn.hsa.module.sys.menu.dto.SysMenuButtonDTO>
  **/
  @Override
  public List<SysSystemDTO> querySystem(SysSystemDTO sysSystemDTO) {
    return sysMenuDAO.querySystem(sysSystemDTO);
  }

  /**
  * @Menthod getMenuAndBtn
  * @Desrciption 获取菜单和按钮提供给动态菜单
  *    <p>
  *      1.先获取角色用户关系id
  *      2.根据id获取用户角色关系代码
  *      3.通过用户角色关系代码获取该角色下的所有菜单和按钮
  *      4.循环让按钮成为菜单的子集
  *    </>
  * @Param
  * [1. sysMenu]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 21:03
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  @Override
  public List<TreeMenuNode> getMenuAndBtn(SysMenuDTO sysMenu) {

    if(StringUtils.isEmpty(sysMenu.getUsID())){
      throw new AppException("登录异常");
    }
    //新建用户系统关系实例
    SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();

    //添加usCODE:用户系统关系ID
    sysUserSystemDTO.setId(sysMenu.getUsID());

    sysUserSystemDTO.setHospCode(sysMenu.getHospCode());

    //根据用户系统id获取用户系统关联代码
    SysUserSystemDTO usById = sysMenuDAO.getUsById(sysUserSystemDTO);

    SysMenuButtonDTO sysMenuButtonDTO = new SysMenuButtonDTO();

    //添加usCODE:用户系统关系代码
    sysMenuButtonDTO.setUsCode(usById.getUsCode());

    //添加医院编码
    sysMenuButtonDTO.setHospCode(sysMenu.getHospCode());
    sysMenu.setUsCode(usById.getUsCode());

    //查出角色所拥有的菜单
    List<TreeMenuNode> onlyMenuTree = sysMenuDAO.getOnlyMenuTree(sysMenu);
    if(ListUtils.isEmpty(onlyMenuTree)){
       throw new AppException("没有该权限，请联系管理员授予权限");
    }

    //查询角色下所拥有的按钮
    List<SysMenuButtonDTO> sysMenuButtonDTOS1 = sysMenuDAO.queryMenuButton(sysMenuButtonDTO);
    //如果为空则返回所有菜单
    if(ListUtils.isEmpty(sysMenuButtonDTOS1)){
      return onlyMenuTree;
    }

    for (int i = 0; i < onlyMenuTree.size(); i++) {
      List<SysMenuButtonDTO> sysMenuButtonDTOS = new ArrayList<>();
      for (int j = 0; j < sysMenuButtonDTOS1.size(); j++) {
        //如果该按钮为菜单的子节点，则加入该菜单的按钮列表
        if(onlyMenuTree.get(i).getId().equals(sysMenuButtonDTOS1.get(j).getMenuCode())){
          sysMenuButtonDTOS.add(sysMenuButtonDTOS1.get(j));
        }
      }
      onlyMenuTree.get(i).setSysMenuButtonDTOS(sysMenuButtonDTOS);
    }
    return onlyMenuTree;
  }

  /**
  * @Menthod queryMenuSeqNo
  * @Desrciption 查询菜单序号自动填充前端
  *
  * @Param
  * [sysMenuDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/4 10:56
  * @Return java.lang.Integer
  **/
  @Override
  public Integer queryMenuSeqNo(SysMenuDTO sysMenuDTO) {
    if(StringUtils.isEmpty(sysMenuDTO.getUpCode())){
      sysMenuDTO.setUpCode("-1");
    }
    if("1".equals(sysMenuDTO.getIsMenuOrBtn())){
      return sysMenuDAO.queryMenuSeqNo(sysMenuDTO);
    }else{
      return sysMenuDAO.queryBtnSeqNo(sysMenuDTO);
    }
  }

  @Override
  public List<TreeMenuNode> getMenuNoBtn(SysMenuDTO sysMenu) {
    return sysMenuDAO.getMenuTreeNoBtn(sysMenu);
  }
}

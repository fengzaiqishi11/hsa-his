package cn.hsa.center.centermenu.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.centermenu.bo.CenterMenuBO;
import cn.hsa.module.center.centermenu.dao.CenterMenuDAO;
import cn.hsa.module.center.centermenu.dto.CenterMenuDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.centermenu.bo.impl
 * @Class_name: CenterMenuBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/1 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class CenterMenuBOImpl extends HsafBO implements CenterMenuBO {

  @Resource
  private CenterMenuDAO centerMenuDAO;

  /**
   * @Menthod getMenuById()
   * @Desrciption 按主键获取系统菜单
   *
   * @Param
   * [CenterMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:30
   * @Return cn.hsa.module.center.menu.dto.SyncMenuButtonDTO
   **/
  @Override
  public CenterMenuDTO getMenuById(CenterMenuDTO centerMenuDTO) {
    return centerMenuDAO.getMenuById(centerMenuDTO);
  }

  /**
   * @Menthod queryMenu()
   * @Desrciption 按条件查询系统菜单
   *
   * @Param
   * [1. CenterMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:30
   * @Return java.util.List<cn.hsa.module.center.menu.dto.SyncMenuButtonDTO>
   **/
  @Override
  public List<CenterMenuDTO> queryMenu(CenterMenuDTO centerMenuDTO) {
    return centerMenuDAO.queryMenu(centerMenuDTO);
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
  public List<TreeMenuNode> getMenuTree(CenterMenuDTO centerMenuDTO) {
    return centerMenuDAO.getMenuTree(centerMenuDTO);
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
    CenterMenuDTO centerMenuDTO = MapUtils.get(map,"centerMenuDTO");
    if(StringUtils.isEmpty(centerMenuDTO.getId())){
//      String orderNo = syncOrderRuleService.getOrderNo("10").getData();
      centerMenuDTO.setCode("CD" + SnowflakeUtils.getId());
      centerMenuDTO.setId(SnowflakeUtils.getId());
      centerMenuDTO.setCrteTime(DateUtils.getNow());
      centerMenuDAO.insertMenu(centerMenuDTO);
    }else{
      centerMenuDAO.updateMenu(centerMenuDTO);
    }
    return true;
  }

  /**
   * @Menthod deleteMenu()
   * @Desrciption 删除菜单按钮
   *
   * @Param
   * [centerMenuDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/30 11:31
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean deleteMenu(CenterMenuDTO centerMenuDTO) {
    return centerMenuDAO.deleteMenu(centerMenuDTO) > 0;
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
  * @Return java.lang.Integer
  **/
  @Override
  public Integer queryMenuSeqNo(CenterMenuDTO centerMenuDTO) {
    return centerMenuDAO.queryMenuSeqNo(centerMenuDTO);
  }
}

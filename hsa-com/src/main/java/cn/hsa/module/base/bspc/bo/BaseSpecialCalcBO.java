package cn.hsa.module.base.bspc.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bspc.bo
 * @Class_name: BaseSpecialCalcDao
 * @Describe: 特殊药品管理业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 15:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseSpecialCalcBO {
  /**
   * @Menthod queryById()
   * @Desrciption 根据id和医院编码查询特殊药品计费
   *
   * @Param
   * [1. id    特殊药品主键id]
   *
   * @Author jiahong.yang
   * @Date   2020/7/15 15:35
   * @Return cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO
   **/
  BaseSpecialCalcDTO getById(Map<String, Object> map);

  /**
   * @Menthod queryPage()
   * @Desrciption 根据条件分页查询特殊药品首次计费
   *
   * @Param
   * [1. baseSpecialCalcDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/15 15:37
   * @Return java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
   *
   * @return*/
   PageDTO queryPage(BaseSpecialCalcDTO baseSpecialCalcDTO);

   /**
   * @Menthod queryAll
   * @Desrciption 根据条件查询特殊药品首次计费
   *
   * @Param
   * [baseSpecialCalcDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/9/16 9:40
   * @Return java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
   **/
   List<BaseSpecialCalcDTO> queryAll(BaseSpecialCalcDTO baseSpecialCalcDTO);

    /**
     * @Method: querySpecialCalcs
     * @Description: 根据参数获取特殊辅助计费
     * @Param: [baseSpecialCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/21 16:27
     * @Return: java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
     **/
   List<BaseSpecialCalcDTO> querySpecialCalcs(BaseSpecialCalcDTO baseSpecialCalcDTO);

  /**
  * @Menthod update()
  * @Desrciption 保存特殊药品计费可编辑表格
  *
  * @Param
  * [1. BaseSpecialCalcDTOs  特殊药品计费列表]
  *
  * @Author jiahong.yang
  * @Date   2020/7/17 11:58
  * @Return boolean
  **/
  boolean update(Map map);


  /**
   * @Menthod delete()
   * @Desrciption 根据主键id删除特殊药品首次计费（可批量删除）
   *
   * @Param
   * [1. baseSpecialCalcDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/15 15:39
   * @Return int
   **/
  boolean delete(BaseSpecialCalcDTO baseSpecialCalcDTO);

  /**
  * @Menthod getDeptTree()
  * @Desrciption 获取科室树
  *
  * @Param
  * [1.code 科室编码, 2. hospCode 医院编码]
  *
  * @Author jiahong.yang
  * @Date   2020/7/21 10:33
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getDeptTree(Map map);

}

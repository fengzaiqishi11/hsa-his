package cn.hsa.module.base.bpft.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bpft.bo
 * @Class_name: BasePreferentialBo
 * @Describe: 优惠配置业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 10:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BasePreferentialBO {

  /**
   * @Menthod getById()
   * @Desrciption 根据id获取优惠配置信息
   *
   * @Param
   * 1. [map]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:00
   * @Return cn.hsa.module.base.bpft.dto.BasePreferentialDTO
   **/
  BasePreferentialDTO getById(Map<String, Object> map);

  /**
   * @Menthod queryPage()
   * @Desrciption 根据条件分页获取优惠配置信息
   *
   * @Param
   * 1.[basePreferentialDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:02
   * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
   **/
  PageDTO queryPage(BasePreferentialDTO basePreferentialDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询全部优惠配置信息
  *
  * @Param
  * [1. basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/19 17:24
  * @Return cn.hsa.base.PageDTO
  **/
  List<BasePreferentialDTO> queryAll(BasePreferentialDTO basePreferentialDTO);

  /**
   * @Method: queryPreferentials
   * @Description: 根据主表获取记录
   * @Param: [basePreferentialTypeDTO]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/7 12:02
   * @Return: java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
   **/
  List<BasePreferentialDTO> queryPreferentials(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
   * @Menthod insert()
   * @Desrciption 新增优惠配置信息
   *
   * @Param
   * 1. [basePreferentialDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:03
   * @Return boolean
   **/
  boolean insert(BasePreferentialDTO basePreferentialDTO);

  /**
   * @Menthod delete()
   * @Desrciption 删除优惠配置信息
   *
   * @Param
   * 1.[map]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:04
   * @Return boolean
   **/
  boolean delete(BasePreferentialDTO basePreferentialDTO);

  /**
   * @Menthod update
   * @Desrciption  修改优惠配置信息
   *
   * @Param
   * [basePreferentialDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:05
   * @Return boolean
   **/
  boolean update(BasePreferentialDTO basePreferentialDTO);

  /**
  * @Menthod queryBsplTypePage()
  * @Desrciption 分页查询优惠类型
  *
  * @Param
  * [basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/5 17:19
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryBsplTypePage(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod queryBsplTypeAll()
  * @Desrciption 查询所有的优惠类型填充下拉框
  *
  * @Param
  * [1.basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 15:12
  * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO>
  **/
  List<BasePreferentialTypeDTO> queryBsplTypeAll(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod update
  * @Desrciption 编辑和新增优惠类型
  *
  * @Param
  * [basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 10:55
  * @Return boolean
  **/
  boolean saveBsplType(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod updateBsplTypeStatus
  * @Desrciption 批量删除优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 16:45
  * @Return boolean
  **/
  boolean updateBsplTypeStatus(BasePreferentialTypeDTO basePreferentialTypeDTO);


  List<Map<String, Object>> calculatPreferential(List<Map<String, Object>> costList);

}

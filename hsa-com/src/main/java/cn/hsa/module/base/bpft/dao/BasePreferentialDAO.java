package cn.hsa.module.base.bpft.dao;

import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bpft.dao
 * @Class_name: BasePreferentialDao
 * @Describe: 优惠配置数据访问层接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 9:57
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BasePreferentialDAO {

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
  List<BasePreferentialDTO> queryPage(BasePreferentialDTO basePreferentialDTO);

  /**
  * @Menthod insert()
  * @Desrciption 新增优惠配置信息
  *
  * @Param
  * 1. [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:03
  * @Return int
  **/
  int insert(BasePreferentialDTO basePreferentialDTO);

  /**
  * @Menthod delete()
  * @Desrciption 删除优惠配置信息
  *
  * @Param
  * 1.[map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:04
  * @Return int
  **/
  int delete(BasePreferentialDTO basePreferentialDTO);

   /**
   * @Menthod update
   * @Desrciption  修改优惠配置信息
   *
   * @Param
   * [basePreferentialDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/7/13 10:05
   * @Return int
   **/
  int update(BasePreferentialDTO basePreferentialDTO);

  /**
  * @Menthod updateAll
  * @Desrciption 批量修改优惠配置
  *
  * @Param
  * []
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 11:31
  * @Return int
  **/
  int updateAll(@Param("list") List<BasePreferentialDTO> basePreferentialDTOS);

  /**
  * @Menthod queryBsplTypePage()
  * @Desrciption 分页查询优惠类型
  *
  * @Param
  * [basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/5 17:18
  * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO>
  **/
  List<BasePreferentialTypeDTO> queryBsplTypePage(BasePreferentialTypeDTO basePreferentialTypeDTO);

  List<BasePreferentialTypeDTO> queryBsplTypeAll(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod updateBsplType
  * @Desrciption 修改优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 10:56
  * @Return int
  **/
  int updateBsplType(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod insertBsplType()
  * @Desrciption 新增优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 10:57
  * @Return int
  **/
  int insertBsplType(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod updateBsplTypeStatus
  * @Desrciption 批量删除优惠类型
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 16:44
  * @Return int
  **/
  int updateBsplTypeStatus(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod queryCodeIsExist()
  * @Desrciption 判断编码是否存在
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 19:14
  * @Return int
  **/
  int queryCodeIsExist(BasePreferentialTypeDTO basePreferentialTypeDTO);

  /**
  * @Menthod queryNameIsExist()
  * @Desrciption 判断优惠类型名称是否存在
  *
  * @Param
  * [1. basePreferentialTypeDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/19 15:57
  * @Return int
  **/
  int queryNameIsExist(BasePreferentialTypeDTO basePreferentialTypeDTO);

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
}

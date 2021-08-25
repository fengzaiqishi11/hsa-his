package cn.hsa.module.outpt.outptclassify.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.bo
 * @Class_name: OutptClassifyServiceBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 16:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptClassifyBO {

  /**
   * @Menthod getByID()
   * @Desrciption 根据id获取挂号类别设置
   *
   * @Param
   * [id]
   *
   * @Author jiahong.yang
   * @Date   2020/8/10 16:36
   * @Return cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO
   **/
  OutptClassifyDTO getById(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod queryPage()
  * @Desrciption 分页查询挂号类别设置
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:52
  * @Return java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  PageDTO queryPage(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod queryAll（）
  * @Desrciption 查询全部挂号类别设置
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:53
  * @Return java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  List<OutptClassifyDTO> queryAll(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod save()
  * @Desrciption 新增挂号类别
  *
  * @Param
  * [1. outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:54
  * @Return java.lang.Boolean
  **/
  Boolean insert(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod update()
  * @Desrciption 修改挂号类别
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 14:54
  * @Return java.lang.Boolean
  **/
  Boolean update(OutptClassifyDTO outptClassifyDTO);

  /**
  * @Menthod deleteById()
  * @Desrciption  根据id删除挂号别设置
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:55
  * @Return java.lang.Boolean
  **/
  Boolean deleteById(OutptClassifyDTO outptClassifyDTO);

  List<OutptClassifyCostDTO> queryClassifyCostList(OutptClassifyCostDTO outptClassifyCostDTO);
}

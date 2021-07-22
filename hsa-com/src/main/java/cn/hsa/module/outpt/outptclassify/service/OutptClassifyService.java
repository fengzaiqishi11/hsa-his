package cn.hsa.module.outpt.outptclassify.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassify.service
 * @Class_name: OutptClassifyService
 * @Describe:  挂号类别设置接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 15:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface OutptClassifyService {

  /**
  * @Menthod getById()
  * @Desrciption 根据id获取挂号类别设置
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 15:59
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  @GetMapping("/service/outpt/outptclassify/getById")
  WrapperResponse<OutptClassifyDTO> getById(Map map);

  /**
  * @Menthod queryPage（）
  * @Desrciption 根据条件分页查询挂号类别设置
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:00
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>>
  **/
  @GetMapping("/service/outpt/outptclassify/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有挂号类别
  *
  * @Param
  * [1.map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/13 13:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/outpt/outptclassify/queryAll")
  WrapperResponse<List<OutptClassifyDTO>> queryAll(Map map);

  /**
  * @Menthod save()
  * @Desrciption 新增修改挂号类别
  *
  * @Param
  * [1 .map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/outpt/outptclassify/insert")
  WrapperResponse<Boolean> insert(Map map);


  /**
  * @Menthod update()
  * @Desrciption 修改修改挂号类别
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 14:53
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/outpt/outptclassify/update")
  WrapperResponse<Boolean> update(Map map);

  /**
  * @Menthod deleteById()
  * @Desrciption  根据id删除挂号别设置
  *
  * @Param
  * [1.map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/outpt/outptclassify/deleteById")
  WrapperResponse<Boolean> deleteById(Map map);


}

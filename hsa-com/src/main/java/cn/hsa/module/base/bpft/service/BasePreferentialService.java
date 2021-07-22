package cn.hsa.module.base.bpft.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bpft.service
 * @Class_name: BasePreferentialService
 * @Describe: 优惠配置信息维护API接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 10:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BasePreferentialService {

  /**
  * @Menthod getById()
  * @Desrciption 通过主键id查询优惠配置信息
  *
  * @Param
  * 1. [map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
  **/
  @GetMapping("/service/base/bpft/getById")
  WrapperResponse<BasePreferentialDTO> getById(Map<String, Object> map);

  /**
  * @Menthod queryPage()
  * @Desrciption 通过条件分页查询优惠配置信息
  *
  * @Param
  * [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/base/bpft/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 查询全部优惠配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/19 17:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/base/bpft/queryAll")
  WrapperResponse<List<BasePreferentialDTO>> queryAll(Map map);

  /**
  * @Menthod insert()
  * @Desrciption 添加优惠配置信息
  *
  * @Param
  * 1. [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bpft/insert")
  WrapperResponse<Boolean> insert(Map map);

  /**
  * @Menthod delete()
  * @Desrciption 删除优惠配信息
  *
  * @Param
  * 1.[map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bpft/delete")
  WrapperResponse<Boolean> delete(Map map);


  /**
  * @Menthod update()
  * @Desrciption  更新优惠配置信息
  *
  * @Param
  * 1. [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/13 10:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bpft/update")
  WrapperResponse<Boolean> update(Map map);

  /**
  * @Menthod queryBsplTypePage（）
  * @Desrciption 分页查询优惠类型
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/5 17:17
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/service/base/bpft/queryBsplTypePage")
  WrapperResponse<PageDTO> queryBsplTypePage(Map map);

  /**
   * @Method: queryPreferentials
   * @Description: 根据主表获取记录
   * @Param: [basePreferentialTypeDTO]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/7 12:02
   * @Return: java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
   **/
  @GetMapping("/service/base/bpft/queryPreferentials")
  WrapperResponse<List<BasePreferentialDTO>> queryPreferentials(Map map);

  /**
  * @Menthod queryBsplTypeAll()
  * @Desrciption 查询所有的优惠类型填充下拉框
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 15:11
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO>>
  **/
  @GetMapping("/service/base/bpft/queryBsplTypeAll")
  WrapperResponse<List<BasePreferentialTypeDTO>> queryBsplTypeAll(Map map);

  /**
  * @Menthod saveBsplType（）
  * @Desrciption 修改和新增优惠类型
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 11:02
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @GetMapping("/service/base/bpft/saveBsplType")
  WrapperResponse<Boolean> saveBsplType(Map map);

  /**
  * @Menthod updateBsplTypeStatus
  * @Desrciption 批量删除优惠类型
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 16:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateBsplTypeStatus(Map map);

  /**
   * @Method: calculatPreferential
   * @Description: 优惠处理
   * @Param: [map]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/10 10:39
   * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  WrapperResponse<List<Map<String, Object>>> calculatPreferential(Map map);
}

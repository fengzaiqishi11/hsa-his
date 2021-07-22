package cn.hsa.module.base.bspc.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.bspc.service
 * @Class_name: BaseSpecialCalcService
 * @Describe:  特殊药品管理维护API接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 15:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseSpecialCalcService {
  /**
  * @Menthod getById()
  * @Desrciption 根据id和医院编码查询特殊药品计费信息
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
  **/
  @PostMapping("/service/base/bspc/getById")
  WrapperResponse<BaseSpecialCalcDTO> getById(Map<String, Object> map);

  /**
  * @Menthod queryPage（）
  * @Desrciption 根据条件分页查询特殊药品计费信息
  *
  * @Param
  * [1. baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:02
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/base/bspc/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询所有特殊药品计费信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/16 9:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/base/bspc/queryPage")
  WrapperResponse<List<BaseSpecialCalcDTO>> queryAll(Map map);

  /**
   * @Method: querySpecialCalcs
   * @Description: 根据参数获取特殊辅助计费
   * @Param: [map]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/10/21 16:26
   * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>>
   **/
  @PostMapping("/service/base/bspc/querySpecialCalcs")
  WrapperResponse<List<BaseSpecialCalcDTO>> querySpecialCalcs(Map map);

  /**
  * @Menthod update()
  * @Desrciption 保存特殊药品计费可编辑表格
  *
  * @Param
  * [1. BaseSpecialCalcDTOs 特殊药品计费列表]
  *
  * @Author jiahong.yang
  * @Date   2020/7/17 12:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bspc/update")
  WrapperResponse<Boolean> update(Map map);

  /**
  * @Menthod delete（）
  * @Desrciption 删除特殊药品计费信息
  *
  * @Param
  * [1. baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:04
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bspc/delete")
  WrapperResponse<Boolean> delete(Map map);

  /**
  * @Menthod getDeptTree()
  * @Desrciption 获取科室树
  *
  * @Param
  * [1. hospCode 医院编码]
  *
  * @Author jiahong.yang
  * @Date   2020/7/21 13:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  *
   * @return*/
  @GetMapping("/service/base/bspc/getDeptTree")
  WrapperResponse<? extends Object> getDeptTree(Map map);

}

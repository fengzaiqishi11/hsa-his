package cn.hsa.module.base.bspl.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.data.service
 * @Class_name: BaseSupplierService
 * @Describe:  供应商信息维护API接口层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/7 16:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseSupplierService {

  /**
   * @Menthod getById()
   * @Desrciption  通过主键id查询供应商信息
   *
   * @Param
   * 1. map  主键id和医院编码
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 15:45
   * @Return cn.hsa.module.base.data.dao.BaseSupplierDao
   **/
   @PostMapping("/service/base/bspl/getById")
   WrapperResponse<BaseSupplierDTO> getById(Map<String, Object> map);


  /**
  * @Menthod queryPage()
  * @Desrciption   根据条件分页查询供应商信息
  *
  * @Param
  * 1. baseSupplierDTO  供应商数据对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/7 16:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @PostMapping("/service/base/bspl/queryPage")
  WrapperResponse<PageDTO> queryPage(Map map);

  /**
  * @Menthod queryAll()
  * @Desrciption  查询所有供应商信息
  *
  * @Param
  * [1. baseSupplierDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/18 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>>
  *
   * @return*/
  @PostMapping("/service/base/bspl/queryAll")
  WrapperResponse<List<BaseSupplierDTO>> queryAll(Map map);

  /**
  * @Menthod insert()
  * @Desrciption 新增供应商
  *
  * @Param
  * 1. baseSupplierDTO  供应商数据对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/7 16:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.data.dto.BaseSupplierDTO>
  **/
  @PostMapping("/service/base/bspl/insert")
  WrapperResponse<Boolean> insert(Map map);

   /**
   * @Menthod delete()
   * @Desrciption 启用禁用供应商
   *
   * @Param
   * 1.map
   *
   * @Author jiahong.yang
   * @Date   2020/7/7 16:29
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @PostMapping("/service/base/bspl/delete")
  WrapperResponse<Boolean> updateStatus(Map map);


  /**
  * @Menthod update()
  * @Desrciption  修改供应商信息
  *
  * @Param
  *  1. baseSupplierDTO  供应商数据对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/7 16:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>
  **/
  @PostMapping("/service/base/bspl/update")
  WrapperResponse<Boolean> update(Map map);


  /**
  * @Menthod insertUpLoad
  * @Desrciption  数据导入
   * @param map
  * @Author xingyu.xie
  * @Date   2021/1/9 13:00
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/base/bspl/upLoad")
  WrapperResponse<Boolean> insertUpLoad(Map map);

}

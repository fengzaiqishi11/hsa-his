package cn.hsa.base.bspl.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspl.bo.BaseSupplierBO;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.bspl.service.BaseSupplierService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.data.service.impl
 * @Class_name: BaseSupplierApiImpl
 * @Describe:  供应商API接口实现层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/8 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/bspl")
@Slf4j
@Service("baseSupplierService_provider")
public class BaseSupplierServiceImpl extends HsafService implements BaseSupplierService {

  /**
   * 供应商业务逻辑接口
   */
  @Resource
  private BaseSupplierBO baseSupplierBO;

  /**
  * @Menthod getById
  * @Desrciption  通过主键id查询供应商信息
  *
  * @Param
  * 1. map
  *
  * @Author jiahong.yang
  * @Date   2020/7/8 9:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.data.dto.BaseSupplierDTO>
  **/
  @Override
  public WrapperResponse<BaseSupplierDTO> getById(Map<String, Object> map) {
    BaseSupplierDTO baseSupplierDTO = baseSupplierBO.getById(map);
    return WrapperResponse.success(baseSupplierDTO);
  }


  /**
  * @Menthod queryPage()
  * @Desrciption 根据条件查询供应商信息
  *
  * @Param
  * 1. baseSupplierDTO  供应商数据传输对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/8 9:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(Map map) {
    BaseSupplierDTO baseSupplierDTO = MapUtils.get(map,"baseSupplierDTO");
    PageDTO pageDTO = baseSupplierBO.queryPage(baseSupplierDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
  * @Menthod queryAll()
  * @Desrciption  查询所有供应商接口
  *
  * @Param
  * [1. baseSupplierDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/18 14:54
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspl.dto.BaseSupplierDTO>>
  **/
  @Override
  public WrapperResponse<List<BaseSupplierDTO>> queryAll(Map map) {
    BaseSupplierDTO baseSupplierDTO = MapUtils.get(map,"baseSupplierDTO");
    List<BaseSupplierDTO> baseSupplierDTOS = baseSupplierBO.queryAll(baseSupplierDTO);
    return WrapperResponse.success(baseSupplierDTOS);
  }


  /**
  * @Menthod insert()
  * @Desrciption 新增供应商
  *
  * @Param
  * 1. baseSupplierDTO  供应商数据传输对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/8 9:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insert(Map map) {
    BaseSupplierDTO baseSupplierDTO = MapUtils.get(map,"baseSupplierDTO");
    return WrapperResponse.success(baseSupplierBO.insert(baseSupplierDTO));
  }


  /**
  * @Menthod updateBsplTypeStatus()
  * @Desrciption 启用禁用供应商根据主键id
  *
  * @Param
  * 1.map
  *
  * @Author jiahong.yang
  * @Date   2020/7/8 9:26
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateStatus(Map map) {
    BaseSupplierDTO baseSupplierDTO = MapUtils.get(map,"baseSupplierDTO");
    return WrapperResponse.success(baseSupplierBO.updateStatus(baseSupplierDTO));
  }


  /**
  * @Menthod update()
  * @Desrciption 修改供应商信息
  *
  * @Param
  * 1. baseSupplierDTO  供应商数据传输对象
  *
  * @Author jiahong.yang
  * @Date   2020/7/8 9:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> update(Map map) {
    BaseSupplierDTO baseSupplierDTO = MapUtils.get(map,"baseSupplierDTO");
    return WrapperResponse.success(baseSupplierBO.update(baseSupplierDTO));
  }

  /**
   * @Menthod insertUpLoad
   * @Desrciption   数据导入
   * @param map 参数
   * @Author xingyu.xie
   * @Date   2020/7/8 15:40
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> insertUpLoad(Map map) {
    return WrapperResponse.success(baseSupplierBO.insertUpLoad(map));
  }
}

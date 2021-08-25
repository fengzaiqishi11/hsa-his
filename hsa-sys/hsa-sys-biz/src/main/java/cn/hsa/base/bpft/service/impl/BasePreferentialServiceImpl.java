package cn.hsa.base.bpft.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bpft.bo.BasePreferentialBO;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.bpft.service.BasePreferentialService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bpft.service.impl
 * @Class_name: BasePreferentialServiceImpl
 * @Describe: 优惠配置service接口实习层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 10:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/bpft")
@Slf4j
@Service("basePreferentialService_provider")
public class BasePreferentialServiceImpl extends HsafService implements BasePreferentialService {

  /**
   * 优惠配置信息业务逻辑接口
   */
  @Resource
  BasePreferentialBO basePreferentialBO;

  /**
  * @Menthod getById()
  * @Desrciption 根据主键获取优惠配置
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 11:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
  **/
  @Override
  public WrapperResponse<BasePreferentialDTO> getById(Map<String, Object> map) {
    BasePreferentialDTO basePreferentialDTO = basePreferentialBO.getById(map);
    return WrapperResponse.success(basePreferentialDTO);
  }

  /**
  * @Menthod queryPage()
  * @Desrciption 根据条件分页查询优惠配置信息
  *
  * @Param
  * 1. [basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 11:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(Map map) {
    BasePreferentialDTO basePreferentialDTO = MapUtils.get(map,"basePreferentialDTO");
    PageDTO pageDTO = basePreferentialBO.queryPage(basePreferentialDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询全部优惠配置信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/19 17:25
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>>
  **/
  @Override
  public WrapperResponse<List<BasePreferentialDTO>> queryAll(Map map) {
    BasePreferentialDTO basePreferentialDTO = MapUtils.get(map,"basePreferentialDTO");
    return WrapperResponse.success(basePreferentialBO.queryAll(basePreferentialDTO));
  }

  /**
  * @Menthod insert()
  * @Desrciption 新增优惠配置信息
  *
  * @Param
  * 1.[BasePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 11:28
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insert(Map map) {
    BasePreferentialDTO basePreferentialDTO = MapUtils.get(map,"basePreferentialDTO");
    return WrapperResponse.success(basePreferentialBO.insert(basePreferentialDTO));
  }


  /**
  * @Menthod delete()
  * @Desrciption  根据id批量删除优惠配置信息
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 16:25
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> delete(Map map) {
    BasePreferentialDTO basePreferentialDTO = MapUtils.get(map,"basePreferentialDTO");
    return WrapperResponse.success(basePreferentialBO.delete(basePreferentialDTO));
  }

  /**
  * @Menthod update()
  * @Desrciption 编辑优惠配置信息
  *
  * @Param
  * [1. basePreferentialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/14 16:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> update(Map map) {
    BasePreferentialDTO basePreferentialDTO = MapUtils.get(map,"basePreferentialDTO");
    return WrapperResponse.success(basePreferentialBO.update(basePreferentialDTO));
  }

  /**
  * @Menthod queryBsplTypePage()
  * @Desrciption 分页查询优惠类型
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/5 17:21
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryBsplTypePage(Map map) {
    BasePreferentialTypeDTO basePreferentialTypeDTO = MapUtils.get(map,"basePreferentialTypeDTO");
    PageDTO pageDTO = basePreferentialBO.queryBsplTypePage(basePreferentialTypeDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
   * @Method: queryPreferentials
   * @Description: 根据主表获取记录
   * @Param: [basePreferentialTypeDTO]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/7 12:02
   * @Return: java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
   **/
  @Override
  public WrapperResponse<List<BasePreferentialDTO>> queryPreferentials(Map map) {
    return WrapperResponse.success(basePreferentialBO.queryPreferentials(MapUtils.get(map,"basePreferentialTypeDTO")));
  }

  /**
  * @Menthod queryBsplTypeAll()
  * @Desrciption 根据条件分页查询优惠配置信息
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 15:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO>>
  **/
  @Override
  public WrapperResponse<List<BasePreferentialTypeDTO>> queryBsplTypeAll(Map map) {
    BasePreferentialTypeDTO basePreferentialTypeDTO = MapUtils.get(map,"basePreferentialTypeDTO");
    return WrapperResponse.success(basePreferentialBO.queryBsplTypeAll(basePreferentialTypeDTO));
  }

  /**
  * @Menthod saveBsplType()
  * @Desrciption  增加和修改优惠类型
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 11:01
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> saveBsplType(Map map) {
    BasePreferentialTypeDTO basePreferentialTypeDTO = MapUtils.get(map,"basePreferentialTypeDTO");
    return WrapperResponse.success(basePreferentialBO.saveBsplType(basePreferentialTypeDTO));
  }

  /**
  * @Menthod updateBsplTypeStatus
  * @Desrciption 批量删除优惠类型
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/6 16:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateBsplTypeStatus(Map map) {
    BasePreferentialTypeDTO basePreferentialTypeDTO = MapUtils.get(map,"basePreferentialTypeDTO");
    return WrapperResponse.success(basePreferentialBO.updateBsplTypeStatus(basePreferentialTypeDTO));
  }

  /**
   * @Method: calculatPreferential
   * @Description: 优惠处理
   * @Param: [map]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/11/10 10:39
   * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @Override
  public WrapperResponse<List<Map<String, Object>>> calculatPreferential(Map map) {
    return WrapperResponse.success(basePreferentialBO.calculatPreferential(MapUtils.get(map,"costList")));
  }
}

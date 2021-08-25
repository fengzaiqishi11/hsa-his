package cn.hsa.outpt.outptclassify.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.outptclassify.bo.OutptClassifyBO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassify.service.OutptClassifyService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptclassify.service.impl
 * @Class_name: OutptClassifyServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/8/10 17:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/outpt/outptClassify")
@Service("outptClassifyService_provider")
public class OutptClassifyServiceImpl extends HsafService implements OutptClassifyService {

  @Resource
  OutptClassifyBO outptClassifyBO;

  /**
  * @Menthod getById()
  * @Desrciption  根据id获取挂号类别设置
  *
  * @Param
  * [outptClassifyDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 17:16
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>
  **/
  @Override
  public WrapperResponse<OutptClassifyDTO> getById(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    return WrapperResponse.success(outptClassifyBO.getById(outptClassifyDTO));
  }

  /**
  * @Menthod queryPage
  * @Desrciption 根据条件分页查询挂号类别设置
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 17:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    return WrapperResponse.success(outptClassifyBO.queryPage(outptClassifyDTO));
  }


  /**
  * @Menthod queryAll()
  * @Desrciption 查询所有挂号类别
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/13 13:54
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO>>
  **/
  @Override
  public WrapperResponse<List<OutptClassifyDTO>> queryAll(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    return WrapperResponse.success(outptClassifyBO.queryAll(outptClassifyDTO));
  }

  /**
  * @Menthod save
  * @Desrciption 新增挂号类别
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 17:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> insert(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    if(StringUtils.isEmpty(outptClassifyDTO.getName()) || StringUtils.isEmpty(outptClassifyDTO.getVisitCode())){
      throw new AppException("参数不能为空");
    }
    return WrapperResponse.success(outptClassifyBO.insert(outptClassifyDTO));
  }

  /**
  * @Menthod update
  * @Desrciption 修改挂号类别
  *
  * @Param
  * [1， map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/11 14:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> update(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    if(StringUtils.isEmpty(outptClassifyDTO.getName()) || StringUtils.isEmpty(outptClassifyDTO.getVisitCode())){
      throw new AppException("参数不能为空");
    }
    return WrapperResponse.success(outptClassifyBO.update(outptClassifyDTO));
  }

  /**
  * @Menthod deleteById
  * @Desrciption 根据id删除挂号别设置
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/8/10 17:19
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteById(Map map) {
    OutptClassifyDTO outptClassifyDTO = MapUtils.get(map,"outptClassifyDTO");
    if(ListUtils.isEmpty(outptClassifyDTO.getIds())){
      throw new AppException("删除主键参数不能为空");
    }
    return WrapperResponse.success(outptClassifyBO.deleteById(outptClassifyDTO));
  }
}

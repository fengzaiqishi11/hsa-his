package cn.hsa.base.bspc.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bspc.bo.BaseSpecialCalcBO;
import cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO;
import cn.hsa.module.base.bspc.service.BaseSpecialCalcService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bspc.service.impl
 * @Class_name: BaseSpecialCalcServiceImpl
 * @Describe:  特殊药品计费service接口实习层（提供给dubbo调用）
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/15 17:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/bspc")
@Slf4j
@Service("baseSpecialCalcService_provider")
public class BaseSpecialCalcServiceImpl extends HsafService implements BaseSpecialCalcService {

  /**
   * 业务逻辑接口
   */
  @Resource
  private BaseSpecialCalcBO baseSpecialCalcBO;

  /**
  * @Menthod getById()
  * @Desrciption  根据id和医院编码查询特殊药品计费信息
  *
  * @Param
  * [1. map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:24
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>
  **/
  @Override
  public WrapperResponse<BaseSpecialCalcDTO> getById(Map<String, Object> map) {
    BaseSpecialCalcDTO baseSpecialCalcDTO = baseSpecialCalcBO.getById(map);
    return WrapperResponse.success(baseSpecialCalcDTO);
  }

  /**
  * @Menthod queryPage()
  * @Desrciption  根据条件分页查询特殊药品计费信息
  *
  * @Param
  * [1. baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryPage(Map map) {
    BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map,"baseSpecialCalcDTO");
    PageDTO pageDTO = baseSpecialCalcBO.queryPage(baseSpecialCalcDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询特殊所有药品计费信息
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/16 9:42
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<List<BaseSpecialCalcDTO>> queryAll(Map map) {
    BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map,"baseSpecialCalcDTO");
    return WrapperResponse.success(baseSpecialCalcBO.queryAll(baseSpecialCalcDTO));
  }

  /**
   * @Method: querySpecialCalcs
   * @Description: 根据参数获取特殊辅助计费
   * @Param: [map]
   * @Author: youxianlin
   * @Email: 254580179@qq.com
   * @Date: 2020/10/21 16:26
   * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bspc.dto.BaseSpecialCalcDTO>>
   **/
  @Override
  public WrapperResponse<List<BaseSpecialCalcDTO>> querySpecialCalcs(Map map) {
    BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map,"baseSpecialCalcDTO");
    return WrapperResponse.success(baseSpecialCalcBO.querySpecialCalcs(baseSpecialCalcDTO));
  }

  /**
  * @Menthod update()
  * @Desrciption  保存特殊药品计费可编辑表格
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/7/17 12:03
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> update(Map map) {
    return WrapperResponse.success(baseSpecialCalcBO.update(map));
  }

  /**
  * @Menthod delete()
  * @Desrciption 删除特殊药品计费信息
  *
  * @Param
  * [1. baseSpecialCalcDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/15 17:29
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> delete(Map map) {
    BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map,"baseSpecialCalcDTO");
    return WrapperResponse.success(baseSpecialCalcBO.delete(baseSpecialCalcDTO));
  }


  /**
  * @Menthod getDeptTree()
  * @Desrciption 获取科室树
  *
  * @Param
  * [1. hospCode  医院编码]
  *
  * @Author jiahong.yang
  * @Date   2020/7/21 14:02
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  *
   * @return*/
  @Override
  public WrapperResponse<? extends Object> getDeptTree(Map map) {
    String hospCode = MapUtils.get(map,"hospCode");
    String deptCode = MapUtils.get(map,"deptCode");
    BaseSpecialCalcDTO baseSpecialCalcDTO = MapUtils.get(map,"baseSpecialCalcDTO");

    if(baseSpecialCalcDTO != null && !StringUtils.isEmpty(baseSpecialCalcDTO.getDeptTypeCode())){
      String[] split = baseSpecialCalcDTO.getDeptTypeCode().split(",");
      baseSpecialCalcDTO.setDeptTypeCodes(new ArrayList<>(Arrays.asList(split)));
      map.put("deptTypeCodes",baseSpecialCalcDTO.getDeptTypeCodes());
      map.remove("baseSpecialCalcDTO");
    }

    if(StringUtils.isEmpty(deptCode)){
      return WrapperResponse.success(TreeUtils.buildByRecursive(baseSpecialCalcBO.getDeptTree(map),"-1"));
    }else{
      return WrapperResponse.success(TreeUtils.getChidldrenIds(baseSpecialCalcBO.getDeptTree(map),deptCode));
    }
  }
}

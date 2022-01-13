package cn.hsa.emr.emrElement.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrelement.bo.EmrElementBO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;
import cn.hsa.module.emr.emrelement.service.EmrElementServcie;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrElement.service.impl
 * @Class_name: EmrElementServcieImpl
 * @Describe: 电子病历元素设置
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/19 11:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrelement")
@Slf4j
@Service("emrElementServcie_provider")
public class EmrElementServiceImpl extends HsafService implements EmrElementServcie {
  @Resource
  private EmrElementBO emrElementBO;

  /**
  * @Menthod getById
  * @Desrciption 根据主键获取电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @Override
  public WrapperResponse<EmrElementDTO> getByIdorCode(Map<String, Object> map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    return WrapperResponse.success(emrElementBO.getByIdorCode(emrElementDTO));
  }

  /**
  * @Menthod queryAll
  * @Desrciption 获取电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>>
  **/
  @Override
  public WrapperResponse<List<EmrElementDTO>> queryAll(Map map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    return WrapperResponse.success(emrElementBO.queryAll(emrElementDTO));
  }

  /**
  * @Menthod queryElementCodes
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 11:18
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
  **/
  @Override
  public WrapperResponse<List<String>> queryElementCodes(Map map) {
    return WrapperResponse.success(emrElementBO.queryElementCodes(map));
  }

  /**
  * @Menthod save
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:12
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> save(Map map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    if(StringUtils.isEmpty(emrElementDTO.getName())){
      throw new AppException("元素名称不能为空");
    }
    if(StringUtils.isEmpty(emrElementDTO.getUpCode())){
      throw new AppException("上级编码不能为空");
    }
    if(StringUtils.isEmpty(emrElementDTO.getIsEnd())){
      throw new AppException("有效状态不能为空");
    }
    if(StringUtils.isEmpty(emrElementDTO.getTypeCode())){
      throw new AppException("元素类型不能为空");
    }
    return WrapperResponse.success(emrElementBO.save(emrElementDTO));
  }

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getEmrElementTree(Map map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(emrElementBO.getEmrElementTree(emrElementDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
   * @Menthod getEmrElementTree
   * @Desrciption 获取电子病历元素树(医保使用)
   *
   * @Param
   * [emrElementDTO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @Override
  public WrapperResponse<List<TreeMenuNode>> getInsureEmrElementTree(Map map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(emrElementBO.getInsureEmrElementTree(emrElementDTO), "-1");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
   * @param map
   * @Menthod getInsureDictEmrElementTree
   * @Desrciption 获取系统码表中电子病历元素树(医保使用)
   * @Param [emrElementDTO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.TreeMenuNode>>
   */
  @Override
  public WrapperResponse<List<TreeMenuNode>> getInsureDictEmrElementTree(Map map) {
    EmrElementDTO emrElementDTO = MapUtils.get(map,"emrElementDTO");
    List<TreeMenuNode> treeMenuNodes = TreeUtils.buildByRecursive(emrElementBO.getInsureDictEmrElementTree(emrElementDTO), "INSURE_EMR");
    return WrapperResponse.success(treeMenuNodes);
  }

  /**
   * @param map
   * @Menthod queryInsureEmrElementMatchInfo
   * @Desrciption 获取元素匹配关系(医保使用)
   * @Param [emrElementMatchDO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.EmrElementMatchDO>>
   */
  @Override
  public PageDTO queryInsureEmrElementMatchInfo(Map map) {
    return emrElementBO.queryInsureEmrElementMatchInfo(MapUtils.get(map,"emrElementMatchDO"));
  }

  /**
   * @param map
   * @Menthod saveInsureMatch
   * @Desrciption 保存病历元素匹配信息
   * @Param [emrElementMatchDO]
   * @Author jiguang.liao
   * @Date 2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public WrapperResponse<Boolean> saveInsureMatch(Map map) {
    return emrElementBO.saveInsureMatch(MapUtils.get(map,"emrElementMatchDO"));
  }

  /**
   * @param map
   * @Menthod deleteInsureMatch
   * @Desrciption 保存病历元素匹配信息
   * @Param [map]
   * @Author jiguang.liao
   * @Date 2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public WrapperResponse<Boolean> deleteInsureMatch(Map map) {
    return emrElementBO.deleteInsureMatch(map);
  }

}

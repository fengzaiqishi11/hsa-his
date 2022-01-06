package cn.hsa.module.emr.emrelement.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrclassifyelement.bo
 * @Class_name: EmrElementBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrElementBO {

  /**
  * @Menthod getById
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:45
  * @Return cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO
  **/
  EmrElementDTO getByIdorCode(EmrElementDTO emrElementDTO);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:46
  * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrElementDTO>
  **/
  List<EmrElementDTO> queryAll(EmrElementDTO emrElementDTO);

  /**
  * @Menthod queryElementCodes
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 10:59
  * @Return java.util.List<java.lang.String>
  **/
  List<String> queryElementCodes(Map map);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:46
  * @Return boolean
  **/
  boolean save(EmrElementDTO emrElementDTO);

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:36
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  List<TreeMenuNode> getEmrElementTree(EmrElementDTO emrElementDTO);

  /**
   * @Menthod getEmrElementTree
   * @Desrciption 获取电子病历元素树(医保使用)
   *
   * @Param
   * [emrElementDTO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return java.util.List<cn.hsa.base.TreeMenuNode>
   **/
  List<TreeMenuNode> getInsureEmrElementTree(EmrElementDTO emrElementDTO);

  /**
   * @param emrElementDTO
   * @Menthod getInsureDictEmrElementTree
   * @Desrciption 获取系统码表中电子病历元素树(医保使用)
   * @Param [emrElementDTO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.TreeMenuNode>>
   */
  List<TreeMenuNode> getInsureDictEmrElementTree(EmrElementDTO emrElementDTO);

  /**
   * @param emrElementMatchDO
   * @Menthod queryInsureEmrElementMatchInfo
   * @Desrciption 获取元素匹配关系(医保使用)
   * @Param [emrElementMatchDO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.EmrElementMatchDO>>
   */
  PageDTO queryInsureEmrElementMatchInfo(EmrElementMatchDO emrElementMatchDO);
}

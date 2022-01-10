package cn.hsa.module.emr.emrelement.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelement.service
 * @Class_name: EmrElementServcie
 * @Describe: 电子病历元素管理业务传输接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/17 16:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-emr")
public interface EmrElementServcie {

  /**
  * @Menthod getById
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:42
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @PostMapping("/service/emr/emrElement/getById")
  WrapperResponse<EmrElementDTO> getByIdorCode(Map<String, Object> map);

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>>
  **/
  @PostMapping("/service/emr/emrElement/queryAll")
  WrapperResponse<List<EmrElementDTO>> queryAll(Map map);

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 10:58
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
  **/
  WrapperResponse<List<String>> queryElementCodes(Map map);

  /**
  * @Menthod save
  * @Desrciption 保存电子病历元素
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/17 16:43
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/service/emr/emrElement/insert")
  WrapperResponse<Boolean> save(Map map);

  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取电子病历元素树
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 16:35
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
  **/
  @GetMapping("/service/emr/emrElement/getMenuTree")
  WrapperResponse<List<TreeMenuNode>> getEmrElementTree(Map map);

  /**
   * @Menthod getEmrElementTree
   * @Desrciption 获取电子病历元素树(医保使用)
   *
   * @Param
   * [map]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("/service/emr/emrElement/getInsureMenuTree")
  WrapperResponse<List<TreeMenuNode>> getInsureEmrElementTree(Map map);

  /**
   * @Menthod getInsureDictEmrElementTree
   * @Desrciption 获取系统码表中电子病历元素树(医保使用)
   *
   * @Param
   * [map]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
  @GetMapping("/service/emr/emrElement/getInsureDictEmrElementTree")
  WrapperResponse<List<TreeMenuNode>> getInsureDictEmrElementTree(Map map);

  /**
   * @Menthod queryInsureEmrElementMatchInfo
   * @Desrciption 获取元素匹配关系(医保使用)
   *
   * @Param
   * [map]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.EmrElementMatchDO>>
   **/
  @GetMapping("/service/emr/emrElement/queryInsureEmrElementMatchInfo")
  PageDTO queryInsureEmrElementMatchInfo(Map map);

  /**
   * @Menthod saveInsureMatch
   * @Desrciption 保存病历元素匹配信息
   *
   * @Param
   * [map]
   *
   * @Author jiguang.liao
   * @Date   2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  WrapperResponse<Boolean> saveInsureMatch(Map map);

  /**
   * @Menthod deleteInsureMatch
   * @Desrciption 保存病历元素匹配信息
   *
   * @Param
   * [map]
   *
   * @Author jiguang.liao
   * @Date   2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
  WrapperResponse<Boolean> deleteInsureMatch(Map map);
}

package cn.hsa.module.center.config.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;

import java.util.List;
import java.util.Map;

/**
 *  中心端全局配置访问业务层
 **/
public interface CenterGlobalConfigBO {


  /**
   *  查询中心端配置信息列表
   * @param centerGlobalConfigDTO  查询参数
   * @return 中心端配置信息列表
   */
  List<CenterGlobalConfigDTO> queryCenterGlobalConfigDTO(CenterGlobalConfigDTO centerGlobalConfigDTO);

  /**
   *  保存或编辑中心端配置信息
   * @param map 参数
   * @return 是否成功
   */
  Boolean save(Map map);

  /**
   *  删除中心端配置信息
   * @param centerGlobalConfigDTO  请求参数
   * @return 是否成功
   */
  Boolean modifyCenterGlobalConfig(CenterGlobalConfigDTO centerGlobalConfigDTO);

  /**
   *  根据主键查询配置信息
   * @param id 数据主键
   * @return
   */
  CenterGlobalConfigDTO getGlobalConfigById(String id);

  /**
   *  刷新中心端配置信息
   * @param centerConfigDTO 查询条件
   * @return
   */
  Map<String, Object> refreshGlobalConfig(CenterGlobalConfigDTO centerConfigDTO);
}

package cn.hsa.module.center.config.dao;

import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  中心端配置数据库访问层
 **/
public interface CenterGlobalConfigDAO {

  /**
   *  查询中心端配置列表信息
   * @param centerMenuDTO 查询参数
   * @return  中心端配置信息列表
   */
  List<CenterGlobalConfigDTO> queryCenterGlobalConfig(CenterGlobalConfigDTO centerMenuDTO);

  /**
   *  新增中心端配置信息
   * @param centerMenuDTO 查询参数
   * @return 是否成功
   */
  int insertCenterGlobalConfig(CenterGlobalConfigDTO centerMenuDTO);

  /**
   *  更新中心端配置信息
   * @param centerMenuDTO 查询参数
   * @return 是否成功
   */
  int updateCenterGlobalConfig(CenterGlobalConfigDTO centerMenuDTO);

  /**
   *  删除中心端配置信息
   * @param centerMenuDTO 查询参数
   * @return 是否成功
   */
  int deleteCenterGlobalConfig(CenterGlobalConfigDTO centerMenuDTO);

  /**
   *  修改配置有效状态
    * @param centerGlobalConfigDTO 请求参数
   * @return  受影响的行数
   */
  int modifyValidStatus(CenterGlobalConfigDTO centerGlobalConfigDTO);

  /**
   *  根据id查询配置信息
   * @param id 数据主键
   * @return  CenterGlobalConfigDTO 全局配置信息
   */
  CenterGlobalConfigDTO getGlobalConfigById(@Param("id") String id);

  /**
   *  根据参数代码查询系统参数
   * @param code 参数代码
   * @return
   */
  CenterParameterDTO getParameterByCode(String code);
}

package cn.hsa.module.center.config.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.config.dto.CenterGlobalConfigDTO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 *  中心端配置信息服务 
 *
 **/
@FeignClient(value = "hsa-center")
public interface CenterGlobalConfigService {

 
  @GetMapping("/service/center/config/queryCenterGlobalConfig")
  WrapperResponse<PageDTO> queryCenterGlobalConfig(CenterGlobalConfigDTO centerConfigDTO);

  /**
   *  获取中心端配置信息并存入redis
   * @param centerConfigDTO 参数
   * @return
   */
  Map<String,Object>  refreshGlobalConfig(CenterGlobalConfigDTO centerConfigDTO);
  /**
   *  保存中心端配置信息
   * @param map
   * @return
   */
  @PostMapping("/service/center/config/save")
  WrapperResponse<Boolean> save(Map map);


  /**
   *  删除中心端全局配置信息
   * @param centerMenuDTO
   * @return
   */
  @PostMapping("/service/center/config/modifyCenterGlobalConfig")
  WrapperResponse<Boolean> modifyCenterGlobalConfig(CenterGlobalConfigDTO centerMenuDTO);

  WrapperResponse<CenterGlobalConfigDTO> getGlobalConfigById(String id);

  /**
   *  获取中心端系统参数
   * @param  codeName
   * @return
   */
  WrapperResponse<CenterParameterDTO> getParameterByCode(String codeName);
}

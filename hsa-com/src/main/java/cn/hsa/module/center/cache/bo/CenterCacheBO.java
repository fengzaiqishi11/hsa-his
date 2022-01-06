package cn.hsa.module.center.cache.bo;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;

import java.util.List;
import java.util.Map;


public interface CenterCacheBO {

  /**
   * 根据编码获取值域代码值
   * @Return: java.util.List
   **/
  Map<String, List<SysCodeSelectDTO>> getByHospCode(String hospCode);

  /**
   * 查询医院数据源集合
   * @Author Ou·Mr
   * @Date   2020/7/20 19:15
   * @Return List<CenterDatasourceDTO> 结果集合
   */
  WrapperResponse<Boolean> refreshCenterHospitalDatasource();
}

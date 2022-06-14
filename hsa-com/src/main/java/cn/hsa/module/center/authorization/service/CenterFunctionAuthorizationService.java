package cn.hsa.module.center.authorization.service;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;


@FeignClient(value = "hsa-center")
public interface CenterFunctionAuthorizationService {

  /**
   *  根据医院编码与业务单据编码查询功能授权信息
   * @param params  调用参数类型为map<br/>
   *                其中 hospCode 医院编码为必传参数<br/>
   *                    orderTypeCode 单据类型 对应中心端码表 QXLX<br/>
   *                    1：DIP    2：DRG
   * @return 中心端增值功能授权
   */
  WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(Map<String,Object> params);


  /**
   *  新增功能授权数据
   * @param functionAuthorizationDO
   * @return
   */
  WrapperResponse<CenterFunctionAuthorizationDO> insertBizAuthorization(CenterFunctionAuthorizationDO functionAuthorizationDO);

}

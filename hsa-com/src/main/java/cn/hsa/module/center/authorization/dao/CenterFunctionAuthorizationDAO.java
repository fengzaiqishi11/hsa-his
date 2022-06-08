package cn.hsa.module.center.authorization.dao;


import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import org.apache.ibatis.annotations.Param;


public interface CenterFunctionAuthorizationDAO {

  /**
   *  根据医院编码与业务单据编码查询功能授权信息
   * @param hospCode 医院编码
   * @param orderTypeCode 单据编码
   * @return 中心端增值功能授权
   */
  CenterFunctionAuthorizationDO queryBizAuthorizationByOrderTypeCode(@Param("hospCode") String hospCode, @Param("orderTypeCode") String orderTypeCode);

}

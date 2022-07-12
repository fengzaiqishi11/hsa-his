package cn.hsa.module.center.authorization.dao;


import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CenterFunctionAuthorizationDAO {

  /**
   *  根据医院编码与业务单据编码查询功能授权信息
   * @param hospCode 医院编码
   * @param orderTypeCode 单据编码
   * @return 中心端增值功能授权
   */
  CenterFunctionAuthorizationDO queryBizAuthorizationByOrderTypeCode(@Param("hospCode") String hospCode, @Param("orderTypeCode") String orderTypeCode);

  /**
   *  新增一条增值功能授权数据
   * @param authorizationDO 授权数据
   * @return 受影响的行数
   */
  int insertAuthorization(CenterFunctionAuthorizationDO authorizationDO);

  /**
   *  修改一条增值功能授权数据
   * @param authorizationDO 授权数据
   * @return 受影响的行数
   */
  int updateAuthorization(CenterFunctionAuthorizationDO authorizationDO);

  /**
   *  修改一条增值功能授权数据的审核状态
   * @param authorizationDO 授权数据
   * @return 受影响的行数
   */
  int updateAuthorizationAudit(CenterFunctionAuthorizationDO authorizationDO);



  List<Map<String, Object>> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto);

  List<CenterFunctionAuthorizationDto> queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto);
}

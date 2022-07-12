package cn.hsa.insure.drgdip.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@HsafRestPath("/service/insure/drgDipResultService")
@Service("drgDipResultService")
public class DrgDipResultServiceImpl extends HsafService implements DrgDipResultService {

  @Resource
  private DrgDipResultBO drgDipResultBO;

  /**
   * 查询质控信息
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-06-07 15:48
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO>
   */
  @Override
  public WrapperResponse<DrgDipComboDTO> getDrgDipInfoByParam(HashMap map) {
    return WrapperResponse.success(drgDipResultBO.getDrgDipInfoByParam(map));
  }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip调用插入日志表
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertDrgDipQulityInfoLog(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.insertDrgDipQulityInfoLog(map));
    }

    /**
     * 前端调用DRG DIP接口授权校验
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 9:36
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<DrgDipAuthDTO> checkDrgDipBizAuthorization(Map<String, Object> map) {
      return WrapperResponse.success(drgDipResultBO.checkDrgDipBizAuthorization(map));
    }

  @Override
  public WrapperResponse<DrgDipAuthDTO> checkDrgDipBizAuthorizationSettle(Map<String, Object> map) {
    return WrapperResponse.success(drgDipResultBO.checkDrgDipBizAuthorizationSettle(map));
  }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip保存质控结果
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<DrgDipResultDTO> insertDrgDipResult(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.insertDrgDipResult(MapUtils.get(map,"drgDipResultDTO"),MapUtils.get(map,"drgDipResultDetailDTOList")));
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询汇总-结算清单
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryDrgDipResultSetlinfo(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.queryDrgDipResultSetlinfo(MapUtils.get(map,"drgDipResultDTO")));
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询汇总-结算清单
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryDrgDipResultSetlinfoSum(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.queryDrgDipResultSetlinfoSum(MapUtils.get(map,"drgDipResultDTO")));
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询-详细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryDrgDipResultDetail(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.queryDrgDipResultDetail(MapUtils.get(map,"drgDipResultDTO")));
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询-病案首页
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryDrgDipResultMris(Map<String,Object> map) {
        return WrapperResponse.success(drgDipResultBO.queryDrgDipResultMris(MapUtils.get(map,"drgDipResultDTO")));
    }

  /**
   * @return
   * @Method getInsureCost
   * @Desrciption 质控结果查询汇总-病案首页
   * @Param [insureSettleInfoDTO]
   * @Author zhangxuan
   * @Date 2021-04-11 22:54
   * @Return
   */
  @Override
   public WrapperResponse<Map<String, Object>> queryDrgDipResultMrisSum(Map<String, Object> map) {
     return WrapperResponse.success(drgDipResultBO.queryDrgDipResultMrisSum(MapUtils.get(map, "drgDipResultDTO")));
   }

   /**
    * @Author 医保二部-张金平
    * @Date 2022-07-04 14:50
    * @Description 质控违规信息查询-病案首页
    * @param map
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    */
  @Override
  public WrapperResponse<PageDTO> queryDrgDipNoRegulationsMris(Map<String, Object> map) {
    return WrapperResponse.success(drgDipResultBO.queryDrgDipNoRegulationsMris(MapUtils.get(map, "drgDipResultDTO")));
  }

  /**
   * @Author zhangjinping
   * @Date 2022-07-05 15:20
   * @Description 质控违规信息汇总-病案首页
   * @param map
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> queryDrgDipNoRegulationMrisSum(Map<String, Object> map) {
    return WrapperResponse.success(drgDipResultBO.queryDrgDipNoRegulationMrisSum(MapUtils.get(map, "drgDipResultDTO")));

  }
  /**
   * @Author zhangjinping
   * @Date 2022-07-06 10:03
   * @Description 质控违规信息查询-结算清单
   * @param map
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  @Override
  public WrapperResponse<PageDTO> queryDrgDipNoRegulationSetlinfo(Map<String, Object> map) {
    return WrapperResponse.success(drgDipResultBO.queryDrgDipNoRegulationSetlinfo(MapUtils.get(map, "drgDipResultDTO")));
  }
  /**
   * @Author zhangjinping
   * @Date 2022-07-05 15:16
   * @Description 质控违规信息汇总-结算清单
   * @param map
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> queryDrgDipNoRegulationSettleSum(Map<String, Object> map) {
    return WrapperResponse.success(drgDipResultBO.queryDrgDipNoRegulationSettleSum(MapUtils.get(map, "drgDipResultDTO")));
  }

}

package cn.hsa.module.insure.drgdip.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.advice.bo
 * @Class_name: AdviceBoImpl
 * @Describe(描述): 医嘱医保统一开放调用 Bo 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface DrgDipResultBO {

    /**
     * 获取质控信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-07 15:54
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    DrgDipComboDTO getDrgDipInfoByParam(HashMap map);

  /**
   * 质控插入日志
   * @param map
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Boolean insertDrgDipQulityInfoLog(Map<String, Object> map);


    /**
     * 前端调用DRG DIP接口授权校验
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 9:42
     * @return java.lang.Boolean
     */
    DrgDipAuthDTO checkDrgDipBizAuthorization(Map<String, Object> map);
    DrgDipAuthDTO checkDrgDipBizAuthorizationSettle(Map<String, Object> map);

  /**
   * 质控结果保存
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  DrgDipResultDTO insertDrgDipResult(DrgDipResultDTO drgDipResultDTO,List<DrgDipResultDetailDTO> drgDipResultDetailDTOList);

  /**
   * 质控结果查询-结算清单
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  PageDTO queryDrgDipResultSetlinfo(DrgDipResultDTO drgDipResultDTO);

  /**
   * 质控结果查询汇总-结算清单
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Map<String, Object>  queryDrgDipResultSetlinfoSum(DrgDipResultDTO drgDipResultDTO);

  /**
   * 质控结果查询-详细
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Map<String, Object>  queryDrgDipResultDetail(DrgDipResultDTO drgDipResultDTO);

  /**
   * 质控结果查询-详细
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  PageDTO  queryDrgDipResultMris(DrgDipResultDTO drgDipResultDTO);

  /**
   * 质控结果查询汇总-病案首页
   * @param
   * @Author
   * @Date 2022-06-07 15:54
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
   */
  Map<String, Object>  queryDrgDipResultMrisSum(DrgDipResultDTO drgDipResultDTO);

  /**
   * @Author 医保二部-张金平
   * @Date 2022-07-04 14:50
   * @Description 质控违规信息查询-病案首页
   * @param drgDipResultDTO
   * @return cn.hsa.base.PageDTO
   */
  PageDTO queryDrgDipNoRegulationsMris(DrgDipResultDTO drgDipResultDTO);
  /**
   * @Author zhangjinping
   * @Date 2022-07-05 15:20
   * @Description 质控违规信息汇总-病案首页
   * @param drgDipResultDTO
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  Map<String, Object> queryDrgDipNoRegulationMrisSum(DrgDipResultDTO drgDipResultDTO);
  /**
   * @Author zhangjinping
   * @Date 2022-07-06 10:03
   * @Description 质控违规信息查询-结算清单
   * @param drgDipResultDTO
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  PageDTO queryDrgDipNoRegulationSetlinfo(DrgDipResultDTO drgDipResultDTO);
  /**
   * @Author zhangjinping
   * @Date 2022-07-05 15:16
   * @Description 质控违规信息汇总-结算清单
   * @param drgDipResultDTO
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  Map<String, Object> queryDrgDipNoRegulationSettleSum(DrgDipResultDTO drgDipResultDTO);
}

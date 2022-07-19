package cn.hsa.module.insure.drgdip.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

import java.util.HashMap;

/**
 * @Package_name: cn.hsa.module.insure.advice.service
 * @Class_name: AdviceService
 * @Describe(描述): 医嘱医保统一开放调用 Service 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@FeignClient(value = "hsa-insure")
public interface DrgDipResultService {

    /**
     * 查询质控信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-07 15:48
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO>
     */
    WrapperResponse<DrgDipComboDTO> getDrgDipInfoByParam(HashMap map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip调用插入日志表
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<Boolean> insertDrgDipQulityInfoLog(Map<String, Object> map);

    /**
     * 前端调用DRG DIP接口授权校验
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-08 9:33
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    WrapperResponse<DrgDipAuthDTO> checkDrgDipBizAuthorization(Map<String, Object> map);
    WrapperResponse<DrgDipAuthDTO> checkDrgDipBizAuthorizationSettle(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption drg\dip保存质控结果
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<DrgDipResultDTO> insertDrgDipResult(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询-结算清单
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<PageDTO> queryDrgDipResultSetlinfo(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询汇总-结算清单
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<Map<String, Object>> queryDrgDipResultSetlinfoSum(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询汇总-详细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<Map<String, Object>> queryDrgDipResultDetail(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询-病案首页
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<PageDTO> queryDrgDipResultMris(Map<String, Object> map);

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 质控结果查询汇总-病案首页
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return
     */
    WrapperResponse<Map<String, Object>> queryDrgDipResultMrisSum(Map<String, Object> map);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-07-04 14:44
     * @Description 质控违规信息查询-病案首页
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> queryDrgDipNoRegulationsMris(Map<String, Object> map);

    /**
     * @Author zhangjinping
     * @Date 2022-07-05 15:20
     * @Description 质控违规信息汇总-病案首页
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> queryDrgDipNoRegulationMrisSum(Map<String, Object> map);
    /**
     * @Author zhangjinping
     * @Date 2022-07-06 10:03
     * @Description 质控违规信息查询-结算清单
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    WrapperResponse<PageDTO> queryDrgDipNoRegulationSetlinfo(Map<String, Object> map);
    /**
     * @Author zhangjinping
     * @Date 2022-07-05 15:16
     * @Description 质控违规信息汇总-结算清单
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> queryDrgDipNoRegulationSettleSum(Map<String, Object> map);
}

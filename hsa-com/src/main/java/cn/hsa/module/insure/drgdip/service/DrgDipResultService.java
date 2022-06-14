package cn.hsa.module.insure.drgdip.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import org.springframework.cloud.openfeign.FeignClient;

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
    WrapperResponse<Boolean> insertDrgDipResult(Map<String, Object> map);
}

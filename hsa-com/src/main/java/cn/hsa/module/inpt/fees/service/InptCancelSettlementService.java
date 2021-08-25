package cn.hsa.module.inpt.fees.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.service
 * @Class_name: InptCancelSettlementService
 * @Describe(描述): 住院取消结算Service
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptCancelSettlementService {

    /**
     * @Menthod querySettleVisitPage
     * @Desrciption 获取取消结算用户信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/inpt/inptCancelSettlement/querySettleVisitPage")
    WrapperResponse querySettleVisitPage(Map param);

    /**
     * @Menthod queryCostOrPayList
     * @Desrciption 获取患者费用信息、预交金信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/inpt/inptCancelSettlement/queryCostOrPayList")
    WrapperResponse queryCostOrPayList(Map param);

    /**
     * @Menthod editCancelSettlement
     * @Desrciption 取消结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/12 10:05 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping("/service/inpt/inptCancelSettlement/editCancelSettlement")
    WrapperResponse editCancelSettlement(Map param);

}

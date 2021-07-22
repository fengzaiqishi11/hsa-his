package cn.hsa.module.inpt.fees.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.bo
 * @Class_name: InptCancelSettlementBO
 * @Describe(描述): 住院取消结算BO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptCancelSettlementBO {

    /**
     * @Menthod querySettleVisitPage
     * @Desrciption 获取取消结算用户信息
     * @param inptSettleDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse querySettleVisitPage(InptSettleDTO inptSettleDTO);

    /**
     * @Menthod queryCostOrPayList
     * @Desrciption 获取患者费用信息、预交金信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryCostOrPayList(Map param);

    /**
     * @Menthod editCancelSettlement
     * @Desrciption 取消结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/12 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse editCancelSettlement(Map param);

}
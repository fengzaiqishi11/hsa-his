package cn.hsa.inpt.fees.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.fees.bo.InptCancelSettlementBO;
import cn.hsa.module.inpt.fees.service.InptCancelSettlementService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.fees.service.impl
 * @Class_name: InptCancelSettlementServiceImpl
 * @Describe(描述): 住院取消结算ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/inpt/inptCancelSettlement")
@Service("inptCancelSettlementService_provider")
public class InptCancelSettlementServiceImpl extends HsafService implements InptCancelSettlementService {

    @Resource
    private InptCancelSettlementBO inptCancelSettlementBO;

    /**
     * @Menthod querySettleVisitPage
     * @Desrciption 获取取消结算用户信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse querySettleVisitPage(Map param) {
        return inptCancelSettlementBO.querySettleVisitPage(MapUtils.get(param,"inptSettleDTO"));
    }

    /**
     * @Menthod queryCostOrPayList
     * @Desrciption 获取患者费用信息、预交金信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/12 10:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCostOrPayList(Map param) {
        return inptCancelSettlementBO.queryCostOrPayList(param);
    }

    /**
     * @Menthod editCancelSettlement
     * @Desrciption 取消结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/10/12 10:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editCancelSettlement(Map param) {
        return inptCancelSettlementBO.editCancelSettlement(param);
    }
}

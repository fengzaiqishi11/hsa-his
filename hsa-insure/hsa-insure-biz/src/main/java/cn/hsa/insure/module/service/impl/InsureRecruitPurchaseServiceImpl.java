package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureRecruitPurchaseBO;
import cn.hsa.module.insure.module.service.InsureRecruitPurchaseService;
import cn.hsa.module.insure.stock.entity.InsureGoodBuy;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseServiceImpl
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureRecruitPurchase")
@Service("insureRecruitPurchaseService_provider")
public class InsureRecruitPurchaseServiceImpl extends HsafService implements InsureRecruitPurchaseService {


    private InsureRecruitPurchaseBO insureRecruitPurchaseBO;

    /**
     * 获取当前医院库存列表
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryAll(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryAll(map));
    }

    @Override
    public WrapperResponse<Map<String, Object>> queryCommoditySalesRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureRecruitPurchaseBO.queryCommoditySalesRecord(map));
    }
}

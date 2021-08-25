package cn.hsa.module.insure.module.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseService
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureRecruitPurchaseService {

    /**
     * 获取当前医院库存列表
     *
     * @param map
     * @return
     */
    WrapperResponse<Map<String, Object>> queryAll(Map<String, Object> map);
    /**
     * @Description: 获取当前医院库存列表
     * @Param: [map]
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021/8/24 10:50
     */
    WrapperResponse<Map<String, Object>> queryCommoditySalesRecord(Map<String, Object> map);
}

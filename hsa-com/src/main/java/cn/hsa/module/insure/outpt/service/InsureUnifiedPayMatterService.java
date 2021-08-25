package cn.hsa.module.insure.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: InsureUnifiedPayMatterService
 * @Describe: 统一支付平台---事前事中分析
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-24 15:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedPayMatterService {
    /**
     * @Menthod: UP_3660
     * @Desrciption: 事前提醒
     * @Param: 
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return: 
     **/
    @PostMapping("/service/inpt/visit/UP_3660")
    WrapperResponse<Map<String, Object>> UP_3660(Map<String, Object> map);

    /**
     * @Menthod: UP_3661
     * @Desrciption: 事中预警
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    @PostMapping("/service/inpt/visit/UP_3661")
    WrapperResponse<Map<String, Object>> UP_3661(Map<String, Object> map);
}

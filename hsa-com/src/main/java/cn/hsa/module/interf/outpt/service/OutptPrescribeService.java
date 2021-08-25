package cn.hsa.module.interf.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.outpt.service
 * @Class_name: OutptPrescribeService
 * @Describe: 门诊处方接口
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2021/5/10 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-interf")
public interface OutptPrescribeService {

    /**
     * 用户
     * @param map
     * @return
     */
    @GetMapping("/service/interf/outptPrescribe/hisInferface")
    WrapperResponse<List<Map<String, Object>>> hisInferface(Map map) throws Exception;

}

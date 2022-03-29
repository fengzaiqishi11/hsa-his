package cn.hsa.module.stro.stroinvoicing.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Class_name: StroInvoicingMonthlyService
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:32
 **/
@FeignClient(value = "hsa-stro")
public interface StroInvoicingMonthlyService {

    @PostMapping("/service/web/stro/stroStock/stroInvoicingMonthly")
    WrapperResponse<Boolean> copyStroInvoicing(Map map);
}

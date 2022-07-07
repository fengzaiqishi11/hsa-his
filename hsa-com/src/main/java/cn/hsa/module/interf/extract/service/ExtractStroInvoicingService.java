package cn.hsa.module.interf.extract.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @author gory
 * @date 2022/7/6 10:38
 * 抽取进销存的数据到临时表中
 */
@FeignClient(value = "hsa-interf")
public interface ExtractStroInvoicingService {

    /**
     * @Author gory
     * @Description 抽取进销存的数据
     * @Date 2022/7/6 10:45
     * @Param [map]
     **/
    @PostMapping("/service/interf/ExtractStroInvoicingService/insertDataToExtractReport")
    WrapperResponse<Boolean> insertDataToExtractReport(Map<String,Object> map);
}

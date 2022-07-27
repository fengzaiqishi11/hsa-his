package cn.hsa.module.interf.extract.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/11 16:00
 *药房消耗Service
 */
@FeignClient(value = "hsa-interf")
public interface ExtractConsumptionService {
    /**
     * @Author gory
     * @Description 查询消耗报表
     * @Date 2022/7/12 8:54
     * @Param [extractConsumptionDTO]
     **/
    WrapperResponse<PageDTO> queryExtractConsumptions(Map map);
    /**
     * @Author pengbo
     * @Description 药房药库实时进销存统计分析
     * @Date 2022/7/15 9:26
     * @Param [extractConsumptionDTO, req, rep]
     **/
    @GetMapping("/service/interf/ExtractStroInvoicingService/extractStroInvoicingDetailDTO")
    WrapperResponse<PageDTO> extractStroInvoicingDetailDTO(Map map);


}

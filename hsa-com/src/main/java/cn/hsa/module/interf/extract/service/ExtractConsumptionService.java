package cn.hsa.module.interf.extract.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import org.springframework.cloud.openfeign.FeignClient;

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


}

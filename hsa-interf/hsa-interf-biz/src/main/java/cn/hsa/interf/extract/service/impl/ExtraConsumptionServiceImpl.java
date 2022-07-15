package cn.hsa.interf.extract.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.bo.ExtractConsumptionBO;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.service.ExtractConsumptionService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/12 9:38
 */
@Service("extraConsumptionService_provider")
public class ExtraConsumptionServiceImpl implements ExtractConsumptionService {

    @Resource
    private ExtractConsumptionBO extractConsumptionBO;
    /**
     * @Author gory
     * @Description 查询消耗报表
     * @Date 2022/7/12 9:39
     * @Param [map]
     **/
    @Override
    public WrapperResponse<PageDTO> queryExtractConsumptions(Map map) {
        return WrapperResponse.success(extractConsumptionBO.queryExtractConsumptions(MapUtils.get(map,"extractConsumptionDTO")));
    }
}

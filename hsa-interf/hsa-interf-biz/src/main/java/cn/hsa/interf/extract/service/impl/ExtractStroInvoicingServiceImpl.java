package cn.hsa.interf.extract.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.bo.ExtractStroInvoicingBO;
import cn.hsa.module.interf.extract.service.ExtractStroInvoicingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/6 11:16
 */
@Service("extractStroInvoicingService_provider")
public class ExtractStroInvoicingServiceImpl implements ExtractStroInvoicingService {
    @Resource
    private ExtractStroInvoicingBO extractStroInvoicingBO;
    /**
     * @Author gory
     * @Description 抽取进销存的数据
     * @Date 2022/7/6 11:18
     * @Param [map]
     **/
    @Override
    public WrapperResponse<Boolean> insertDataToExtractReport(Map<String, Object> map) {
        return WrapperResponse.success(extractStroInvoicingBO.insertDataToExtractReport(map));
    }
}

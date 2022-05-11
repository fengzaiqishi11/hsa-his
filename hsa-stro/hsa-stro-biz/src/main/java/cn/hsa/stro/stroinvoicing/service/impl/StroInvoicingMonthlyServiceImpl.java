package cn.hsa.stro.stroinvoicing.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingMonthlyBO;
import cn.hsa.module.stro.stroinvoicing.service.StroInvoicingMonthlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Class_name: StroInvoicingMonthlyServiceImpl
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:33
 **/
@HsafRestPath("/service/stro/stroinvoicingmonthly")
@Slf4j
@Service("stroInvoicingMonthlyService_provider")
public class StroInvoicingMonthlyServiceImpl extends HsafService implements StroInvoicingMonthlyService {

    @Resource
    private StroInvoicingMonthlyBO stroInvoicingMonthlyBO;
    @Override
    public WrapperResponse<Boolean> insertCopyStroInvoicing(Map map) {
        return WrapperResponse.success(stroInvoicingMonthlyBO.insertCopyStroInvoicing(map));
    }
}

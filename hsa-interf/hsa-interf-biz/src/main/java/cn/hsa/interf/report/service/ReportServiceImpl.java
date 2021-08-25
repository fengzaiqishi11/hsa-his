package cn.hsa.interf.report.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.report.bo.ReportBO;
import cn.hsa.module.interf.report.service.ReportService;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@HsafRestPath("/service/interf/report")
@Slf4j
@Service("reportService_provider")
public class ReportServiceImpl extends HsafService implements ReportService {

    @Resource
    private ReportBO reportBO;

    /**
     * 查询药库实时进销存报表
     *
     * @param map 查询参数
     * @return cn.hsa.hsaf.core.framework.web public class WrapperResponse 统一结果返回实体
     */
    @Override
    public WrapperResponse<PageDTO> queryStroInvoicingLedger(Map<String, Object> map) {
        return WrapperResponse.success(reportBO.queryStroInvoicingLedger(map));
    }

    @Override
    public WrapperResponse<PageDTO> queryByStrSQL(Map<String, Object> map) {
        Integer pageNo = Integer.valueOf(String.valueOf(map.get("pageNo")== null ? 1 : map.get("pageNo")));
        Integer pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")== null ? 10 : map.get("pageSize")));
        PageHelper.startPage(pageNo,pageSize);
        return WrapperResponse.success(reportBO.queryByStrSQL(map));
    }
}

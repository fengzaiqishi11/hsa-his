package cn.hsa.module.interf.report.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

public interface ReportService {

    /**
     *  查询药库实时进销存报表
     * @param map 查询参数
     * @return  cn.hsa.hsaf.core.framework.web public class WrapperResponse 统一结果返回实体
     */
    WrapperResponse<PageDTO> queryStroInvoicingLedger(Map<String,Object> map);
}

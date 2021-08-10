package cn.hsa.module.interf.report.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

public interface ReportBO {

    /**
     *  查询药库实时进销存报表
     * @param map 查询参数
     * @return  cn.hsa.hsaf.core.framework.web public class WrapperResponse 统一结果返回实体
     */
  PageDTO queryStroInvoicingLedger(Map<String,Object> map);

}

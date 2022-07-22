package cn.hsa.module.interf.extract.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @author gory
 * @date 2022/7/6 10:47
 * 抽取进销存的数据到临时表中
 */
public interface ExtractStroInvoicingBO {
    /**
     * @Author gory
     * @Description 抽取进销存的数据
     * @Date 2022/7/6 10:45
     * @Param [map]
     **/
    Boolean insertDataToExtractReport(Map<String,Object> map);
}

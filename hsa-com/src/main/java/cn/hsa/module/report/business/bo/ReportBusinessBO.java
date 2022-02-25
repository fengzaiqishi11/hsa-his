package cn.hsa.module.report.business.bo;

import java.util.Map;

/**
 * @ClassName ReportBusinessService
 * @Deacription 报表业务逻辑
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
public interface ReportBusinessBO {

    /**
     * 报表业务数据
     * @param map 基本信息
     * @return Map
     */
    Map getReportDataMap(Map map);

}

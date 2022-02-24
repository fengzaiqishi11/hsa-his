package cn.hsa.report.business.bo.impl.factory;

import java.util.Map;

/**
 * @ClassName SettleSheetProcess
 * @Deacription 结算单逻辑
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
public interface ReportBusinessProcess {

    /**
     * 结算单信息
     * @param map 基本信息
     * @return Map
     */
    Map getReportDataMap(Map map);

}

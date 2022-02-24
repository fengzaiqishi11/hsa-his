package cn.hsa.report.business.bo.impl.factory;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName SettleSheetProcess
 * @Deacription 结算单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Service
public class SettleSheetProcess implements ReportBusinessProcess {

    @Override
    public Map getReportDataMap(Map map) {
        return map;
    }

}

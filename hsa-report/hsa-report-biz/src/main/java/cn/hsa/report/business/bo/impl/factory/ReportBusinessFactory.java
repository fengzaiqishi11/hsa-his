package cn.hsa.report.business.bo.impl.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SettleSheetFactory
 * @Deacription 结算单工厂类
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
@Component
public class ReportBusinessFactory {

    @Autowired
    private Map<String, ReportBusinessProcess> processMap = new ConcurrentHashMap<>(6);

    public ReportBusinessProcess getReportBusinessProcess(String process) {
        return processMap.get(process);
    }

}

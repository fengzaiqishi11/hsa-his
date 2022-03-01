package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SettleSheetFactory
 * @Deacription 结算单工厂类
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Component
public class ReportBusinessFactory {

    @Autowired
    private Map<String, ReportBusinessBO> processMap = new ConcurrentHashMap<>(6);

    public ReportBusinessBO getReportBusinessProcess(String process) {
        return processMap.get(process);
    }

}

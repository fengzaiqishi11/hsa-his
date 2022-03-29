package cn.hsa.report.business.bo.impl.factory.settle;

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
public class SettleSheetFactory {

    @Autowired
    private Map<String, SettleSheetProcess> processMap = new ConcurrentHashMap<>(6);

    public SettleSheetProcess getSettleSheetProcess(String process) {
        return processMap.get(process);
    }

}

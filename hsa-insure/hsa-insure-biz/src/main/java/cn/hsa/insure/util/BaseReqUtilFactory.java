package cn.hsa.insure.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BaseReqUtilFactory
 * @Deacription 公共入参工厂类
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Component
public class BaseReqUtilFactory {

    @Autowired
    private Map<String, BaseReqUtil> utilMap = new ConcurrentHashMap<>(100);

    public BaseReqUtil getBaseReqUtil(String utilName) {
        return utilMap.get(utilName);
    }

}

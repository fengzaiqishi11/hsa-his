package cn.hsa.insure.unifiedpay.util.fixmedinsInfo;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FixmedinsInfoReqUtil
 * @Deacription 获取医疗机构信息-1201
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1201)
public class FixmedinsInfoReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fixmedins_type", MapUtils.get(map, "insureServiceType"));
        dataMap.put("fixmedins_name", MapUtils.get(map, "medinsName"));
        dataMap.put("fixmedins_code", MapUtils.get(map, "medinsCode"));

        checkRequest(dataMap);
        return JSON.toJSONString(dataMap);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

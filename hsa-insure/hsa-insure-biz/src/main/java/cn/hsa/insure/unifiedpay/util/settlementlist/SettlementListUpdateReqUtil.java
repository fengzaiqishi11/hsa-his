package cn.hsa.insure.unifiedpay.util.settlementlist;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettlementListUpdateReqUtil
 * @Deacription 医疗保障基金结算清单状态修改-4102
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4102)
public class SettlementListUpdateReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add((Map)map.get("stastinfo"));
        dataMap.put("stastinfo",list);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data",dataMap);
        map.put("input",inputMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_4102);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

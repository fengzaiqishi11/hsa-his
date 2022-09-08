package cn.hsa.insure.unifiedpay.util.settlementlist;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SettlementListUpdateReqUtil
 * @Deacription 医疗保障基金结算清单信息查询- 4103
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4103)
public class SettlementListQueryReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data",map.get("data"));
        map.put("input",inputMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_4103);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

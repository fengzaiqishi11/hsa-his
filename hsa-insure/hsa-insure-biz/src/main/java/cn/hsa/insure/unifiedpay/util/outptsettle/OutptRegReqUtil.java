package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OutptRegReqUtil
 * @Deacription 门诊挂号-2201
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2201)
public class OutptRegReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        Map<String, Object> inputMap = new HashMap<>(2);
        inputMap.put("data", map.get("dataMap"));
        inputMap.put("agnterinfo", initAgnterinfoMap());
        map.put("input", inputMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

    private Map<String, Object> initAgnterinfoMap() {
        Map<String, Object> agnterinfoMap = new HashMap<>(7);
        agnterinfoMap.put("agnter_name", "");
        agnterinfoMap.put("agnter_rlts", "");
        agnterinfoMap.put("agnter_cert_type", "");
        agnterinfoMap.put("agnter_certno", "");
        agnterinfoMap.put("agnter_tel", "");
        agnterinfoMap.put("agnter_addr", "");
        agnterinfoMap.put("agnter_photo", "");
        return agnterinfoMap;
    }

}

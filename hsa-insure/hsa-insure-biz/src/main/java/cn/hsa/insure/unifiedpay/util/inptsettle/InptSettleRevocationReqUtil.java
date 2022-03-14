package cn.hsa.insure.unifiedpay.util.inptsettle;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName InptSettleRevocationReqUtil
 * @Deacription 住院结算撤销-2305
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.INPT.UP_2305)
public class InptSettleRevocationReqUtil<T> implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);
        checkRequest(map);
        return paramJson;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

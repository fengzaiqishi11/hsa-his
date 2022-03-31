package cn.hsa.insure.unifiedpay.util.otherinfo;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InsurDictReqUtil
 * @Deacription 获取码表字典-1901
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1901)
public class InsurDictReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", MapUtils.get(map, "type"));
        dataMap.put("date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S));

        checkRequest(dataMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_1901);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

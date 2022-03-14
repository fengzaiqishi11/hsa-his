package cn.hsa.insure.unifiedpay.util;

import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InsureCommonUtil
 * @Deacription 湖南新医保公共参数
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Component
public class InsureCommonUtil {

    public String getInsurCommonParam(Map map) {
        Map<String, Object> httpMap = new HashMap<>(7);
        // 交易编号
        httpMap.put("infno", map.get("infno"));
        // 参保地医保区划
        httpMap.put("insuplc_admdvs", map.get("insuplcAdmdvs"));
        // 定点医药机构编号
        httpMap.put("medins_code", map.get("medisCode"));
        // 医保中心编码
        httpMap.put("insur_code", map.get("regCode"));
        // 就医地医保区划
        httpMap.put("mdtrtarea_admvs", map.get("mdtrtareaAdmvs"));
        httpMap.put("msgid", map.get("msgId"));
        httpMap.put("input", MapUtils.isEmpty(map.get("input")) ? getInputMap(map) : map.get("input"));
        return JSONObject.toJSONString(httpMap);
    }

    private Map<String, Object> getInputMap(Map map) {
        Map<String, Object> inputMap = new HashMap<>(1);
        inputMap.put("data", map.get("dataMap"));
        return inputMap;
    }

}

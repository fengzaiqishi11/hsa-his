package cn.hsa.insure.unifiedpay.util.idcard;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MD5Utils;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName IdCardPasswordUpdateReqUtil
 * @Deacription 身份证密码修改-2301
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.CARD.UP_1603)
public class IdCardPasswordUpdateReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("idcard",MapUtils.get(map, "idcard")); // 身份证号码
        dataMap.put("psnName",MapUtils.get(map, "psnName")); // 姓名
        dataMap.put("insuAdmdvs",MapUtils.get(map, "insuAdmdvs")); // 行政区划
        dataMap.put("password", MD5Utils.encrypt32(MapUtils.get(map, "password"))); // 修改后的密码
        dataMap.put("oldPassword",MD5Utils.encrypt32(MapUtils.get(map, "oldPassword"))); // 修改前的密码

        HashMap commParam = new HashMap();
        checkRequest(commParam);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.CARD.UP_1603);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));
        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

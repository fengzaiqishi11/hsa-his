package cn.hsa.insure.unifiedpay.util.psninfo;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1101)
public class PsnInfoReqUtil<T> implements BaseReqUtil<T> {

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

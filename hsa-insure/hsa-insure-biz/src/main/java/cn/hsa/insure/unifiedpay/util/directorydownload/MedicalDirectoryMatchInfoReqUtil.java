package cn.hsa.insure.unifiedpay.util.directorydownload;

import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName MedicalDirectoryMatchInfoReqUtil
 * @Deacription 医疗目录与医保目录匹配信息查询-1316
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_1316)
public class MedicalDirectoryMatchInfoReqUtil<T> implements BaseReqUtil<T> {

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

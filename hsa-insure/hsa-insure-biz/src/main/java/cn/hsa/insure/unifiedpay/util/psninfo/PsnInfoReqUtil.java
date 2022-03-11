package cn.hsa.insure.unifiedpay.util.psninfo;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
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
public class PsnInfoReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

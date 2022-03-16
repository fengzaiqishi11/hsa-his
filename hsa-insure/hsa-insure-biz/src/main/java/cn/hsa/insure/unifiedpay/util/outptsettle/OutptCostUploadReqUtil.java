package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName OutptCostUploadReqUtil
 * @Deacription 门诊费用明细信息上传-2204
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2204)
public class OutptCostUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

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

package cn.hsa.insure.unifiedpay.util.inventory;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName InventoryCheckUploadReqUtil
 * @Deacription 商品盘存上传-3501
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.KCGL.UP_3501)
public class InventoryCheckUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);
        checkRequest(map);
        map.put("infno",Constant.UnifiedPay.KCGL.UP_3501);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

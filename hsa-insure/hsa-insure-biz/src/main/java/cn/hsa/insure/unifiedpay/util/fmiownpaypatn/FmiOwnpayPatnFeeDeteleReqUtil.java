package cn.hsa.insure.unifiedpay.util.fmiownpaypatn;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4204)
public class FmiOwnpayPatnFeeDeteleReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {



    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("feedetail", map);
        if(!MapUtils.isEmpty(map,"bkkp_sn")){
            dataMap.put("feedetl",MapUtils.get(map,"bkkp_sn"));
        }
        checkRequest(dataMap);
        map.put("input", dataMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_4204);
        return getInsurCommonParam(map);
    }


    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

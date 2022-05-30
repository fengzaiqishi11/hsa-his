package cn.hsa.insure.unifiedpay.util.specialAttribute;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName specialAttributeReqUtil
 * @Deacription 就医特殊属性上传-2406
 * @Author yuelong.chen
 * @Date 2022/5/16 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_2406)
public class specialAttributeReqUploadUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {
    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data",map);
        Map resultMap = new HashMap();
        resultMap.put("hospCode",MapUtils.get(map,"hospCode"));
        resultMap.put("configCode", MapUtils.get(map,"configCode"));
        resultMap.put("orgCode", MapUtils.get(map,"orgCode"));
        resultMap.put("configRegCode", MapUtils.get(map,"configRegCode"));
        resultMap.put("input",dataMap);
        resultMap.put("infno",Constant.UnifiedPay.REGISTER.UP_2406);
        return getInsurCommonParam(resultMap);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

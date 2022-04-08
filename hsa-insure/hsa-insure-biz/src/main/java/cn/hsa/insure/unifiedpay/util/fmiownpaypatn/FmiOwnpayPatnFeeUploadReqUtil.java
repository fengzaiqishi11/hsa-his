package cn.hsa.insure.unifiedpay.util.fmiownpaypatn;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnFeeListDDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
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
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4201)
public class FmiOwnpayPatnFeeUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;

        List<Map<String, Object>> mapList = MapUtils.get(map, "mapList");
        Map<String, Object> dataMap = new HashMap<>(3);

        dataMap.put("feedetail", initFeeListDDTO(mapList));

        checkRequest(dataMap);
        map.put("input", dataMap);
        return getInsurCommonParam(map);
    }

    private Object initFeeListDDTO(List<Map<String, Object>> mapList) {
         return null;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

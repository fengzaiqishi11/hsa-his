package cn.hsa.insure.unifiedpay.util.clinical;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.clinica.dto.InsureBacterialReportDTO;
import cn.hsa.module.insure.clinica.dto.InsureDrugsensitiveReportDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.HumpUnderlineUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettlementListUpdateReqUtil
 * @Deacription 临床检查报告记录-4501
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4504)
public class DrugsensitiveReportReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO = (InsureDrugsensitiveReportDTO)map.get("insureDrugsensitiveReportDTO");
        List<InsureDrugsensitiveReportDTO> insureDrugsensitiveReportDTOList = new ArrayList<>();
        insureDrugsensitiveReportDTOList.add(insureDrugsensitiveReportDTO);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", HumpUnderlineUtils.humpToUnderlineArray(insureDrugsensitiveReportDTOList));
        map.put("input", inputMap);
        map.put("infno", Constant.UnifiedPay.REGISTER.UP_4504);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName OutptSettleRevocationReqUtil
 * @Deacription 门诊结算撤销-2208
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2208)
public class OutptSettleRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;

        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        Map<String, Object> dataMap = new HashMap<>(3);
        // 结算ID
        dataMap.put("setl_id", MapUtils.get(map, "insureSettleId"));
        // 就诊ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 人员编号
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        checkRequest(dataMap);
        map.put("dataMap", dataMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

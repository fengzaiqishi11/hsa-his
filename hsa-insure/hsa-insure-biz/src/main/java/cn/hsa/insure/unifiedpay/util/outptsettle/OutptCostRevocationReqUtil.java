package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OutptCostRevocationReqUtil
 * @Deacription 门诊费用明细信息撤销-2205
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2205)
public class OutptCostRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");

        Map<String, Object> dataMap = new HashMap<>(3);
        // 就诊ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 当费用批次号不为空时 撤销已经上传的费用信息  批次号为空时 撤销所有的费用的信息
        dataMap.put("chrg_bchno", "0000");
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

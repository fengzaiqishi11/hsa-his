package cn.hsa.insure.unifiedpay.util.outptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OutptRegCancelReqUtil
 * @Deacription 门诊挂号撤销-2202
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2202)
public class OutptRegCancelReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        Map<String, Object> dataMap = new HashMap<>(3);
        // 人员编号
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        // 就诊ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 住院/门诊号
        dataMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());
        // 校验参数
        checkRequest(dataMap);
        map.put("dataMap", dataMap);
        map.put("infno",Constant.UnifiedPay.OUTPT.UP_2202);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

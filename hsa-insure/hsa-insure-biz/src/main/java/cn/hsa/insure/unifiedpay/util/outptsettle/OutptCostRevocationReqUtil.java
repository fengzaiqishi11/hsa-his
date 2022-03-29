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
 * @ClassName OutptCostRevocationReqUtil
 * @Deacription 门诊费用明细信息撤销-2205
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2205)
public class OutptCostRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");

        Map<String, Object> dataMap = new HashMap<>(3);
        // 就诊ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 当费用批次号不为空时 撤销已经上传的费用信息  批次号为空时 撤销所有的费用的信息
        dataMap.put("chrg_bchno", "0000");
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());

        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("dataMap", dataMap);
        commParam.put("infno",Constant.UnifiedPay.OUTPT.UP_2205);

        commParam.put("msgId",MapUtils.get(map,"msgId"));
        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

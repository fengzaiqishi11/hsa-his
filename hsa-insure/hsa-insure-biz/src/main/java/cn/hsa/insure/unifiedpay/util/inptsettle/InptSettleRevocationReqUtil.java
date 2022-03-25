package cn.hsa.insure.unifiedpay.util.inptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InptSettleRevocationReqUtil
 * @Deacription 住院结算撤销-2305
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.INPT.UP_2305)
public class InptSettleRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(3);
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        //	就诊ID
        dataMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());
        //	人员编号
        dataMap.put("psn_no",insureIndividualVisitDTO.getAac001());
        //	人员编号
        dataMap.put("setl_id",insureIndividualVisitDTO.getInsureSettleId());

        checkRequest(dataMap);
        map.put("input", dataMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

package cn.hsa.insure.unifiedpay.util.inptregister;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInptOutFeeDTO;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InptRevocationReqUtil
 * @Deacription 入院办理撤销-2404
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_2404)
public class InptRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(3);
        InsureInptOutFeeDTO insureInptOutFeeDTO = (InsureInptOutFeeDTO) map.get("insureInptOutFeeDTO");
        //	就诊ID
        dataMap.put("mdtrt_id",insureInptOutFeeDTO.getAaz217());
        //	人员编号
        dataMap.put("psn_no",insureInptOutFeeDTO.getAac001());

        checkRequest(map);
        map.put("input", dataMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}


package cn.hsa.insure.unifiedpay.util.inptsettle;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InptCostRevocationReqUtil
 * @Deacription 住院费用明细撤销-2302
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.INPT.UP_2302)
public class InptCostRevocationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {

        Map map = (Map) param;

        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");

        Map<String, Object> dataMap = new HashMap<>(4);

         //费用明细流水号
        dataMap.put("feedetl_sn","0000");
        // 就诊ID
        dataMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());
        // 个人编号
        dataMap.put("psn_no",insureIndividualVisitDTO.getAac001());

        checkRequest(map);
        map.put("input", dataMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

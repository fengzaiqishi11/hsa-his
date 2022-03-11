package cn.hsa.insure.unifiedpay.util.outptsettle;

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
 * @ClassName OutptRegCancelReqUtil
 * @Deacription 门诊挂号撤销-2202
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.OUTPT.UP_2202)
public class OutptRegCancelReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public String initRequest(T param) {
        String paramJson = (String) param;
        Map map = JSON.parseObject(paramJson, Map.class);

        Map<String, Object> dataMap = new HashMap<>();
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisit");
        // 就诊ID
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        // 人员编号
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        // 住院/门诊号
        dataMap.put("ipt_otp_no", insureIndividualVisitDTO.getVisitNo());
        // 就诊凭证号
        dataMap.put("mdtrt_cert_no", insureIndividualVisitDTO.getMdtrtCertNo());
        // 就诊类型
        dataMap.put("mdtrt_cert_type", insureIndividualVisitDTO.getMdtrtCertType());
        // 校验参数
        checkRequest(dataMap);
        map.put("dataMap", dataMap);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

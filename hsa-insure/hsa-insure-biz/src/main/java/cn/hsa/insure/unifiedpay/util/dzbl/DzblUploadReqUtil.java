package cn.hsa.insure.unifiedpay.util.dzbl;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DzblUploadReqUtil
 * @Deacription 电子病历上传-4701
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4701)
public class DzblUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        InsureEmrDetailDTO insureEmrDetailDTO = MapUtils.get(map, "insureEmrDetailDTO");

        Map<String, Object> dataMap = new HashMap<>(3);
        // 入院信息
        dataMap.put("adminfo", insureEmrDetailDTO.getInsureEmrAdminfoDTO());
        // 诊断信息
        dataMap.put("diseinfo", insureEmrDetailDTO.getInsureEmrDiseinfoDTO());
        // 病程记录信息
        dataMap.put("coursrinfo", insureEmrDetailDTO.getInsureEmrCoursrinfoDTO());
        // 手术信息
        dataMap.put("oprninfo", insureEmrDetailDTO.getInsureEmrOprninfoDTO());
        // 抢救记录信息
        dataMap.put("rescinfo", insureEmrDetailDTO.getInsureEmrRescinfoDTO());
        // 死亡信息
        dataMap.put("dieinfo", insureEmrDetailDTO.getInsureEmrDiseinfoDTO());
        // 出院小结信息
        dataMap.put("dscginfo", insureEmrDetailDTO.getInsureEmrDscginfoDTO());

        checkRequest(dataMap);
        map.put("dataMap", dataMap);
        map.put("infno",Constant.UnifiedPay.REGISTER.UP_4701);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

package cn.hsa.insure.unifiedpay.util.dzbl;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.HumpUnderlineUtils;
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
        dataMap.put("adminfo", HumpUnderlineUtils.humpToUnderline(insureEmrDetailDTO.getInsureEmrAdminfoDTO()));

        // 诊断信息
        dataMap.put("diseinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDiseinfoDTOList()));

        // 病程记录信息
        dataMap.put("coursrinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrCoursrinfoDTOList()));

        // 手术信息
        dataMap.put("oprninfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrOprninfoDTOList()));

        // 抢救记录信息
        dataMap.put("rescinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrRescinfoDTOList()));

        // 死亡信息
        dataMap.put("dieinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDieinfoDTOList()));

        // 出院小结信息
        dataMap.put("dscginfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDscginfoDTOList()));


        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_4701);

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

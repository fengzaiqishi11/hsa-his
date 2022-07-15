package cn.hsa.insure.unifiedpay.util.dzbl;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.HumpUnderlineUtils;
import cn.hsa.util.MapUtils;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String insuplcAdmdvs = MapUtils.get(map, "insuplcAdmdvs");
        String regCode = MapUtils.get(map, "configCode");

        Map<String, Object> dataMap = new HashMap<>(3);
        // 入院信息
        dataMap.put("adminfo", HumpUnderlineUtils.humpToUnderline(insureEmrDetailDTO.getInsureEmrAdminfoDTO()));

        // 诊断信息
        dataMap.put("diseinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDiseinfoDTOList()));

        if (ObjectUtil.isNotEmpty(insuplcAdmdvs) && insuplcAdmdvs.startsWith("44")) {
            if (ObjectUtil.isNotEmpty(insureEmrDetailDTO.getInsureEmrCoursrinfoDTOList())) {
                dataMap.put("coursrinfo", HumpUnderlineUtils.humpToUnderline(insureEmrDetailDTO.getInsureEmrCoursrinfoDTOList().get(0)));
            }else {
                dataMap.put("coursrinfo", new JSONObject());
            }
        }else {
            // 病程记录信息
            dataMap.put("coursrinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrCoursrinfoDTOList()));
        }

        //广州医保如果没有手术记录信息则传null
        List<InsureEmrOprninfoDTO> oprninfoDTOList = insureEmrDetailDTO.getInsureEmrOprninfoDTOList();
        if (ObjectUtil.isNotEmpty(insuplcAdmdvs) && insuplcAdmdvs.startsWith("44")
                && ObjectUtil.isEmpty(oprninfoDTOList)) {
            dataMap.put("oprninfo","null");
        }else {
            // 手术信息
            dataMap.put("oprninfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrOprninfoDTOList()));
        }

        // 抢救记录信息
        dataMap.put("rescinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrRescinfoDTOList()));

        //广州医保如果没有病程死亡记录信息则传null
        List<InsureEmrDieinfoDTO> dieinfoDTOList = insureEmrDetailDTO.getInsureEmrDieinfoDTOList();
        if (ObjectUtil.isNotEmpty(insuplcAdmdvs) && insuplcAdmdvs.startsWith("44")) {
            if (ObjectUtil.isEmpty(dieinfoDTOList)) {
                dataMap.put("dieinfo","null");
            }else {
                dataMap.put("dieinfo", HumpUnderlineUtils.humpToUnderline(dieinfoDTOList.get(0)));
            }
        }else {
            // 死亡信息
            dataMap.put("dieinfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDieinfoDTOList()));
        }

        if (ObjectUtil.isNotEmpty(insuplcAdmdvs) && insuplcAdmdvs.startsWith("44")) {
            if (ObjectUtil.isNotEmpty(insureEmrDetailDTO.getInsureEmrDscginfoDTOList())) {
                dataMap.put("dscginfo", HumpUnderlineUtils.humpToUnderline(insureEmrDetailDTO.getInsureEmrDscginfoDTOList().get(0)));
            }else {
                dataMap.put("dscginfo", new JSONObject());
            }
        }else {
            // 出院小结信息
            dataMap.put("dscginfo", HumpUnderlineUtils.humpToUnderlineArray(insureEmrDetailDTO.getInsureEmrDscginfoDTOList()));
        }


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
        //广州4701接口不需要传参保区划，传了反而会导致报“未配置两定接口[4701]接口服务地址”，不信你试试
        if (ObjectUtil.isNotEmpty(regCode) && regCode.startsWith(Constant.UnifiedPay.YBBMQZ.GD)) {
            commParam.put("insuplcAdmdvs","");
        }

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

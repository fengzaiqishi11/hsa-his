package cn.hsa.insure.unifiedpay.util.clinical;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.clinica.dto.*;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.util.HumpUnderlineUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettlementListUpdateReqUtil
 * @Deacription 临床检查报告记录-4501
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4502)
public class ClinicalCheckoutReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        InsureClinicalCheckoutDTO insureClinicalCheckoutDTO = (InsureClinicalCheckoutDTO)map.get("insureClinicalCheckoutDTO");
        //检验信息
        InsureClinicalCheckoutLabinfoDTO insureClinicalCheckoutLabinfoDTO = new InsureClinicalCheckoutLabinfoDTO();
        BeanUtils.copyProperties(insureClinicalCheckoutDTO,insureClinicalCheckoutLabinfoDTO);
        //检验明细信息
        ClinicalExaminationIteminfoDTO clinicalExaminationIteminfoDTO = new ClinicalExaminationIteminfoDTO();
        BeanUtils.copyProperties(insureClinicalCheckoutDTO,clinicalExaminationIteminfoDTO);
        List<ClinicalExaminationIteminfoDTO> clinicalExaminationIteminfoDTOList = new ArrayList<>();
        clinicalExaminationIteminfoDTOList.add(clinicalExaminationIteminfoDTO);
        //检查标本信息
        ClinicalExaminationSampleinfoDTO clinicalExaminationSampleinfoDTO = new ClinicalExaminationSampleinfoDTO();
        BeanUtils.copyProperties(insureClinicalCheckoutDTO,clinicalExaminationSampleinfoDTO);
        List<ClinicalExaminationSampleinfoDTO> clinicalExaminationSampleinfoDTOList = new ArrayList<>();
        clinicalExaminationSampleinfoDTOList.add(clinicalExaminationSampleinfoDTO);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("labinfo", HumpUnderlineUtils.humpToUnderline(insureClinicalCheckoutLabinfoDTO));
        inputMap.put("iteminfo", HumpUnderlineUtils.humpToUnderlineArray(clinicalExaminationIteminfoDTOList));
        inputMap.put("sampleinfo", HumpUnderlineUtils.humpToUnderlineArray(clinicalExaminationSampleinfoDTOList));
        map.put("input", inputMap);
        map.put("infno", Constant.UnifiedPay.REGISTER.UP_4502);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

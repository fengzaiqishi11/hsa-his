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
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4501)
public class ClinicalExaminationReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        checkRequest(map);
        ClinicalExaminationInfoDTO clinicalExaminationInfoDTO = (ClinicalExaminationInfoDTO)map.get("clinicalExaminationInfoDTO");
        //检查记录
        ClinicalExaminationExaminfoDTO clinicalExaminationExaminfoDTO = new ClinicalExaminationExaminfoDTO();
        BeanUtils.copyProperties(clinicalExaminationInfoDTO,clinicalExaminationExaminfoDTO);
        //检查项目信息
        ClinicalExaminationIteminfoDTO clinicalExaminationIteminfoDTO = new ClinicalExaminationIteminfoDTO();
        BeanUtils.copyProperties(clinicalExaminationInfoDTO,clinicalExaminationIteminfoDTO);
        List<ClinicalExaminationIteminfoDTO> clinicalExaminationIteminfoDTOList = new ArrayList<>();
        clinicalExaminationIteminfoDTOList.add(clinicalExaminationIteminfoDTO);
        //检查标本信息
        ClinicalExaminationSampleinfoDTO clinicalExaminationSampleinfoDTO = new ClinicalExaminationSampleinfoDTO();
        BeanUtils.copyProperties(clinicalExaminationInfoDTO,clinicalExaminationSampleinfoDTO);
        List<ClinicalExaminationSampleinfoDTO> clinicalExaminationSampleinfoDTOList = new ArrayList<>();
        clinicalExaminationSampleinfoDTOList.add(clinicalExaminationSampleinfoDTO);
        //检查影像信息
        ClinicalExaminationImageinfoDTO clinicalExaminationImageinfoDTO = new ClinicalExaminationImageinfoDTO();
        BeanUtils.copyProperties(clinicalExaminationInfoDTO,clinicalExaminationImageinfoDTO);
        List<ClinicalExaminationImageinfoDTO> clinicalExaminationImageinfoDTOList = new ArrayList<>();
        clinicalExaminationImageinfoDTOList.add(clinicalExaminationImageinfoDTO);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("examinfo", HumpUnderlineUtils.humpToUnderline(clinicalExaminationExaminfoDTO));
        inputMap.put("iteminfo", HumpUnderlineUtils.humpToUnderlineArray(clinicalExaminationIteminfoDTOList));
        inputMap.put("sampleinfo", HumpUnderlineUtils.humpToUnderlineArray(clinicalExaminationSampleinfoDTOList));
        inputMap.put("imageinfo", HumpUnderlineUtils.humpToUnderlineArray(clinicalExaminationImageinfoDTOList));
        map.put("input", inputMap);
        map.put("infno", Constant.UnifiedPay.REGISTER.UP_4501);
        return getInsurCommonParam(map);
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}

package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.bo.CareToMedicApplyBO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.CareToMedicApplyService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:51
 * @desc
 **/
@HsafRestPath("/service/outpt/caretomedicapply")
@Slf4j
@Service("careToMedicApplyService_provider")
public class CareToMedicApplyServiceImpl implements CareToMedicApplyService {

    @Resource
    CareToMedicApplyBO careToMedicApplyBO;

    @Override
    public WrapperResponse<PageDTO> queryCareToMedicPage(Map map) {
        return WrapperResponse.success(careToMedicApplyBO.queryCareToMedicPage(MapUtils.get(map,"medicToCareDTO")));
    }

    @Override
    public WrapperResponse<Map<String, Object>> getMedicToCareInfoById(Map map) {
        return WrapperResponse.success(careToMedicApplyBO.getCareToMedicInfoById(map));
    }

    @Override
    public WrapperResponse<Boolean> updateCareToMedic(Map map) {
        return WrapperResponse.success(careToMedicApplyBO.updateCareToMedic(map));
    }
}
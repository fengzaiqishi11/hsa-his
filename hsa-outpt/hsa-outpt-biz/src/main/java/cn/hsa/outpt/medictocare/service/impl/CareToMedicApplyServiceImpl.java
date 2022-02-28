package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.CareToMedicApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:51
 * @desc
 **/
@HsafRestPath("/service/outpt/caretomedicapply")
@Slf4j
@Service("CareToMedicApplyService_provider")
public class CareToMedicApplyServiceImpl implements CareToMedicApplyService {
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<MedicToCareDTO> getMedicToCareInfoById(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<Boolean> updateMedicToCare(Map map) {
        return null;
    }
}
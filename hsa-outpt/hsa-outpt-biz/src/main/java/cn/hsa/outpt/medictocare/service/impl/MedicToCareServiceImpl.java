package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.MedicToCareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:48
 * @desc
 **/
@HsafRestPath("/service/outpt/medictocare")
@Slf4j
@Service("MedicToCareService_provider")
public class MedicToCareServiceImpl implements MedicToCareService {
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<PageDTO> queryMedicToCareInfoPage(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<MedicToCareDTO> getMedicToCareInfoById(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<PageDTO> queryHospitalPatientInfoPage(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<Boolean> insertMedicToCare(Map map) {
        return null;
    }

    @Override
    public WrapperResponse<Boolean> updateMedicToCare(Map map) {
        return null;
    }
}
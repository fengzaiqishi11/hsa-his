package cn.hsa.outpt.medictocare.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.CareToMedicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 15:51
 * @desc
 **/
@HsafRestPath("/service/outpt/caretomedic")
@Slf4j
@Service("CareToMedicService_provider")
public class CareToMedicServiceImpl implements CareToMedicService {
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
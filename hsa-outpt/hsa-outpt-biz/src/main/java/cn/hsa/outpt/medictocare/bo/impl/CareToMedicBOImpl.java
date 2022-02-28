package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.medictocare.bo.CareToMedicBO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author powersi
 * @create 2022-02-28 15:54
 * @desc
 **/
@Component
@Slf4j
public class CareToMedicBOImpl extends HsafBO implements CareToMedicBO {
    @Override
    public PageDTO queryPage(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public MedicToCareDTO getCareToMedicInfoById(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public Boolean updateCareToMedic(MedicToCareDTO medicToCareDTO) {
        return null;
    }
}
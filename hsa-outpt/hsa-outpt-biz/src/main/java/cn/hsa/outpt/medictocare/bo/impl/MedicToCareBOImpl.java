package cn.hsa.outpt.medictocare.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.outpt.medictocare.bo.MedicToCareBO;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yuelong.chen
 * @create 2022-02-28 9:26
 * @desc医转养controller层
 **/
@Component
@Slf4j
public class MedicToCareBOImpl extends HsafBO implements MedicToCareBO {

    @Override
    public PageDTO queryPage(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public PageDTO queryMedicToCareInfoPage(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public MedicToCareDTO getMedicToCareInfoById(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public PageDTO queryHospitalPatientInfoPage(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public Boolean insertMedicToCare(MedicToCareDTO medicToCareDTO) {
        return null;
    }

    @Override
    public Boolean updateMedicToCare(MedicToCareDTO medicToCareDTO) {
        return null;
    }
}
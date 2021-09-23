package cn.hsa.module.clinical.inptclinicalpathstate.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;

public interface InptClinicalPathStateBO {

    PageDTO queryClinicalPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO);
}

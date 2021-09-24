package cn.hsa.module.clinical.inptclinicalpathstate.dao;

import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;

import java.util.List;

public interface InptClinicalPathStateDAO {
    /**
     * @Meth: queryClinicalItem
     * @Description: 根据目录id 、阶段id查询阶段明细(供诊疗、其他、护理使用)
     * @Param: [inptClinicalPathStateDTO]
     * @return: java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    List<ClinicPathStageDetailDTO> queryClinicalItem(InptClinicalPathStateDTO inptClinicalPathStateDTO);
}

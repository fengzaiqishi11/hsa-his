package cn.hsa.module.clinical.clinicalpathstage.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;

public interface ClinicalPathStageBO {
    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询临床路径阶段信息
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    PageDTO queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
}

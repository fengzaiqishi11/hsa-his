package cn.hsa.module.clinical.clinicalpathstage.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;

public interface ClinicalPathStageBO {
    /**
     * @Meth: queryClinicalPathStage
     * @Description: 根据条件查询临床路径阶段信息
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    PageDTO queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);

    /**
     * @Meth: addOrupdateClinicalPathStage
     * @Description: 根据目录id 添加或者删除 路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    Boolean addOrUpdateClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除阶段描述
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    Boolean deleteClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
}

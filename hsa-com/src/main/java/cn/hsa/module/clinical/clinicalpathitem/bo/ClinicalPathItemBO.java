package cn.hsa.module.clinical.clinicalpathitem.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;

import java.util.List;

public interface ClinicalPathItemBO {
    /**
    * @Description:  根据条件查询满足条件的临床项目路径
    * @Param: [queryDTO]
    * @return: java.util.List<cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO>
    * @Author: zhangguorui
    * @Date: 2021/9/9
    */
    PageDTO queryAll(ClinicalPathItemDTO queryDTO);

    Boolean updateOrAddPathItem(ClinicalPathItemDTO clinicalPathItemDTO);

    Boolean deletePathItemBatch(ClinicalPathItemDTO clinicalPathItemDTO);
}

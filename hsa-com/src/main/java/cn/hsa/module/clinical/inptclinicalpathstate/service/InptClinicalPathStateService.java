package cn.hsa.module.clinical.inptclinicalpathstate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;

import java.util.Map;

public interface InptClinicalPathStateService {

    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 通过目录id、阶段id查询临床路径阶段明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<PageDTO> queryClinicalPathStageDetail(Map map);
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询病人病情记录表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<PageDTO> queryInptClinicalPathState(Map map);
    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过病人信息查询单个临床病人记录
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    WrapperResponse<InptClinicalPathStateDTO> queryInptClinicalPathStateByVisitId(Map map);
}

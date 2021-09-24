package cn.hsa.module.clinical.inptclinicalpathstate.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

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
}

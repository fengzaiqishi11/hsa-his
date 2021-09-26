package cn.hsa.clinical.inptclinicalpathstate.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.inptclinicalpathstate.bo.InptClinicalPathStateBO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.service.InptClinicalPathStateService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@HsafRestPath("/service/inpt/inptClinicalPathStateService")
@Slf4j
@Service("inptClinicalPathStateService_provider")
public class InptClinicalPathStateServiceImpl implements InptClinicalPathStateService {

    @Resource
    private InptClinicalPathStateBO inptClinicalPathStateBO;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 根据目录id、阶段id查询临床路径明细
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<PageDTO> queryClinicalPathStageDetail(Map map) {
        return WrapperResponse.success(inptClinicalPathStateBO.queryClinicalPathStageDetail(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询病人病情记录表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<PageDTO> queryInptClinicalPathState(Map map) {
        return  WrapperResponse.success(inptClinicalPathStateBO.queryInptClinicalPathState(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }

    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过病人信息查询单个病人记录
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public WrapperResponse<InptClinicalPathStateDTO> queryInptClinicalPathStateByVisitId(Map map) {

        return WrapperResponse.success(inptClinicalPathStateBO.queryInptClinicalPathStateByVisitId(MapUtils.get(map,"inptClinicalPathStateDTO")));
    }
}

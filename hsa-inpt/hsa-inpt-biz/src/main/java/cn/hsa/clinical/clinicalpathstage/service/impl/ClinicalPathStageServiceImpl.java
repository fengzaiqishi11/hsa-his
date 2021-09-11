package cn.hsa.clinical.clinicalpathstage.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathitem.bo.ClinicalPathItemBO;
import cn.hsa.module.clinical.clinicalpathstage.bo.ClinicalPathStageBO;
import cn.hsa.module.clinical.clinicalpathstage.service.ClinicalPathStageService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@HsafRestPath("/service/inpt/clinicalPathStageService")
@Slf4j
@Service("clinicalPathStageService_provider")
public class ClinicalPathStageServiceImpl implements ClinicalPathStageService {
    @Resource
    private ClinicalPathStageBO clinicalPathStageBO;
    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询 临床路径阶段
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    @Override
    public WrapperResponse<PageDTO> queryClinicalPathStage(Map map) {
        return WrapperResponse.success(clinicalPathStageBO.queryClinicalPathStage(MapUtils.get(map,"clinicalPathStageDTO")));
    }

    /**
     * @Meth:addOrupdateClinicalPathStage
     * @Description:  根据路径目录ID 添加或者编辑路径阶段
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public WrapperResponse<Boolean> addOrUpdateClinicalPathStage(Map map) {
        return WrapperResponse.success(clinicalPathStageBO.addOrUpdateClinicalPathStage(MapUtils.get(map,"clinicalPathStageDTO")));
    }
    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除阶段路径描述
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public WrapperResponse<Boolean> deleteClinicalPathStage(Map map) {
        return WrapperResponse.success(clinicalPathStageBO.deleteClinicalPathStage(MapUtils.get(map,"clinicalPathStageDTO")));
    }
}

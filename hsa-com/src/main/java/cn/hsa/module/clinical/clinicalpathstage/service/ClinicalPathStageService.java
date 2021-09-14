package cn.hsa.module.clinical.clinicalpathstage.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 表名含义：临床路径阶段描述(ClinicalPathStage)表服务接口
 *
 * @author makejava
 * @since 2021-09-10 16:35:01
 */
@FeignClient(name = "hsa-clinical")
public interface ClinicalPathStageService {

    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件，查询临床项目阶段
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    @GetMapping("/service/clinical/clinicalPathStage/queryClinicalPathStage")
    WrapperResponse<PageDTO> queryClinicalPathStage(Map map);

    /**
     * @Meth: addOrUpdateClinicalPathStage
     * @Description: 根据路径目录ID 添加或者编辑路径阶段
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @PostMapping("/service/clinical/clinicalPathStage/addOrUpdateClinicalPathStage")
    WrapperResponse<Boolean> addOrUpdateClinicalPathStage(Map map);

    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除临床阶段维护
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @PostMapping("/service/clinical/clinicalPathStage/deleteClinicalPathStage")
    WrapperResponse<Boolean> deleteClinicalPathStage(Map map);

    @GetMapping("/service/clinical/clinicalPathStage/deleteClinicalPathStage")
    WrapperResponse<ClinicalPathStageDTO> queryClinicalPathStageById(Map map);
}

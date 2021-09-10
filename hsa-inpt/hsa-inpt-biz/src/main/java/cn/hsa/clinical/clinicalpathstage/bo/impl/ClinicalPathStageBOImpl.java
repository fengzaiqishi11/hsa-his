package cn.hsa.clinical.clinicalpathstage.bo.impl;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.clinical.clinicalpathstage.dao.ClinicalPathStageDAO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import cn.hsa.module.clinical.clinicalpathstage.bo.ClinicalPathStageBO;

import javax.annotation.Resource;
import java.util.Optional;

@Component
@Slf4j
public class ClinicalPathStageBOImpl implements ClinicalPathStageBO {

    @Resource
    private ClinicalPathStageDAO clinicalPathStageDAO;
    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询临床路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    @Override
    public PageDTO queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() ->new AppException("医院编码不能为空"));
        PageHelper.startPage(clinicalPathStageDTO.getPageNo(),clinicalPathStageDTO.getPageSize());
        clinicalPathStageDAO.queryClinicalPathStage(clinicalPathStageDTO);
        return null;
    }
}

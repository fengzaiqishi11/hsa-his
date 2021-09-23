package cn.hsa.clinical.inptclinicalpathstate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.bo.InptClinicalPathStateBO;
import cn.hsa.module.clinical.inptclinicalpathstate.dao.InptClinicalPathStateDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class InptClinicalPathStateBOImpl implements InptClinicalPathStateBO {
    @Resource
    private InptClinicalPathStateDAO inptClinicalPathStateDAO;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 根据目录id 、阶段id查询阶段明细
     * @Param: [clinicPathStageDetailDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public PageDTO queryClinicalPathStageDetail(ClinicPathStageDetailDTO clinicPathStageDetailDTO) {
        return null;
    }
}

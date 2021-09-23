package cn.hsa.clinical.inptclinicalpathstate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.bo.InptClinicalPathStateBO;
import cn.hsa.module.clinical.inptclinicalpathstate.dao.InptClinicalPathStateDAO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Op;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class InptClinicalPathStateBOImpl implements InptClinicalPathStateBO {
    @Resource
    private InptClinicalPathStateDAO inptClinicalPathStateDAO;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 根据目录id 、阶段id查询阶段明细(供诊疗、其他、护理使用)
     * @Param: [clinicPathStageDetailDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public PageDTO queryClinicalPathStageDetail(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
        PageHelper.startPage(inptClinicalPathStateDTO.getPageNo(),inptClinicalPathStateDTO.getPageSize());
        // 判断
        Optional.ofNullable(inptClinicalPathStateDTO.getHospCode()).orElseThrow(()-> new AppException("医院编码不能为空"));
        Optional.ofNullable(inptClinicalPathStateDTO.getListId()).orElseThrow(()-> new AppException("路径目录id不能为空"));
        Optional.ofNullable(inptClinicalPathStateDTO.getStageId()).orElseThrow(()->new AppException("当前阶段id不能为空"));
        // 查询临床路径阶段明细表、临床路径项目表获得阶段项目名称
        List<ClinicPathStageDetailDTO> clinicPathStageDetailDTOS = inptClinicalPathStateDAO.queryClinicalItem(inptClinicalPathStateDTO);
        return PageDTO.of(clinicPathStageDetailDTOS);
    }
}

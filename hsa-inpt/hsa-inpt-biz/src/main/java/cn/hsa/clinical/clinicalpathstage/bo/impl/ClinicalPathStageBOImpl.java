package cn.hsa.clinical.clinicalpathstage.bo.impl;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.clinical.clinicalpathstage.dao.ClinicalPathStageDAO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Op;
import org.springframework.stereotype.Component;
import cn.hsa.module.clinical.clinicalpathstage.bo.ClinicalPathStageBO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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
        List<ClinicalPathStageDTO> clinicalPathStageDTOS = clinicalPathStageDAO.queryClinicalPathStage(clinicalPathStageDTO);
        return PageDTO.of(clinicalPathStageDTOS);
    }
    /**
     * @Meth: addOrupdateClinicalPathStage
     * @Description: 根据目录id 添加或者删除 路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public Boolean addOrUpdateClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathStageDTO.getListId()).orElseThrow(() -> new AppException("目录id不能为空"));
        if (StringUtils.isEmpty(clinicalPathStageDTO.getId())){ // 添加
            // 生成主键
            String id = SnowflakeUtils.getId();
            // 创建时间
            Date crteTime = DateUtils.getNow();
            // 封装参数
            clinicalPathStageDTO.setCrteTime(crteTime);
            clinicalPathStageDTO.setId(id);
            return clinicalPathStageDAO.insert(clinicalPathStageDTO) > 0;
        } else{ // 编辑
            Optional.ofNullable(clinicalPathStageDTO.getId()).orElseThrow(() ->new AppException("主键id不能为空"));
            return clinicalPathStageDAO.updateById(clinicalPathStageDTO) > 0;
        }

    }
    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除阶段描述
     * @Param: [clinicalPathStageDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @Override
    public Boolean deleteClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO) {
        Optional.ofNullable(clinicalPathStageDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathStageDTO.getId()).orElseThrow(() -> new AppException("要删除的数据主键不能为空"));
        Optional.ofNullable(clinicalPathStageDTO.getListId()).orElseThrow(() -> new AppException("目录id为空"));
        return clinicalPathStageDAO.deleteClinicalPathStage(clinicalPathStageDTO) > 0;
    }
}

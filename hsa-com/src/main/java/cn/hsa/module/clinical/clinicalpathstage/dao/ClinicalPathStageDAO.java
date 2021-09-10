package cn.hsa.module.clinical.clinicalpathstage.dao;


import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;

import java.util.List;

/**
 * 表名含义：临床路径阶段描述(ClinicalPathStage)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-10 16:35:00
 */
public interface ClinicalPathStageDAO {

    /**
     * @Meth:queryClinicalPathStage
     * @Description: 根据条件查询 临床路径阶段
     * @Param: [clinicalPathStageDTO]
     * @return: List<ClinicalPathStageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    List<ClinicalPathStageDTO> queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO);
}


package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 实验室检验报告Service类
 * @author liudawen
 * @date 2022/5/10
 */
public interface LabSurveyReportService {

    /**
     * 查询实验室检验报告主表
     * @param map
     * @return
     */
    WrapperResponse<List<TbJyzbg>> getLabSurveyReport(Map map);

    /**
     * 查询实验室检验报告项目分组记录
     * @param map
     * @return
     */
    WrapperResponse<List<TbJyxmfzjl>> getLabSurveyGroupRecord(Map map);

    /**
     * 查询实验室检验报告项目指标结果
     * @param map
     * @return
     */
    WrapperResponse<List<TbJyzbjg>> getLabSurveyIndexResult(Map map);

    /**
     * 查询实验室检验报告细菌结果
     * @param map
     * @return
     */
    WrapperResponse<List<TbJyxjjg>> getLabSurveyGermResult(Map map);

    /**
     * 查询实验室检验报告药敏结果
     * @param map
     * @return
     */
    WrapperResponse<List<TbJyymjg>> getLabSurveyDrugSensitivityResult(Map map);
}

package cn.hsa.module.interf.healthInfo.dao;

import cn.hsa.module.interf.healthInfo.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 实验室检验报告DAO类
 * @author liudawen
 * @date 2022/5/10
 */
public interface LabSurveyReportDAO {

    /**
     * 条件查询实验室检验报告主表信息
     * @param map
     * @return
     */
    List<TbJyzbg> listLabSurveyReport (Map map);

    /**
     * 条件查询实验室检验报告项目分组记录信息
     * @param map
     * @return
     */
    List<TbJyxmfzjl> listLabSurveyGroupRecord(Map map);

    /**
     * 条件查询实验室检验报告项目指标结果
     * @param map
     * @return
     */
    List<TbJyzbjg> listLabSurveyIndexResult(Map map);

    /**
     * 条件查询实验室检验报告细菌结果
     * @param map
     * @return
     */
    List<TbJyxjjg> listLabSurveyGermResult(Map map);

    /**
     * 条件查询实验室检验报告药敏结果
     * @param map
     * @return
     */
    List<TbJyymjg> listLabSurveyDrugSensitivityResult(Map map);
}

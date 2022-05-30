package cn.hsa.interf.healthInfo.bo.impl;

import cn.hsa.module.interf.healthInfo.bo.LabSurveyReportBO;
import cn.hsa.module.interf.healthInfo.dao.LabSurveyReportDAO;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liudawen
 * @date 2022/5/11
 */
@Component
public class LabSurveyReportBOImpl implements LabSurveyReportBO {
    @Resource
    private LabSurveyReportDAO labSurveyReportDAO;

    /**
     *  查询实验室报告主表信息
     * @Author liudawen
     * @Param [labSurveyReportDTO]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.dto.LabSurveyReportDTO>
     * @Throws
     * @Date 2022/5/11 15:35
     **/
    @Override
    public List<TbJyzbg> listLabSurveyReport(Map map) {
        return labSurveyReportDAO.listLabSurveyReport(map);
    }

    /**
     *  查询实验室检验报告项目分组记录信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyxmfzjl>
     * @Throws 
     * @Date 2022/5/12 15:10
     **/
    @Override
    public List<TbJyxmfzjl> listLabSurveyGroupRecord(Map map) {
        return labSurveyReportDAO.listLabSurveyGroupRecord(map);
    }

    /**
     *  查询实验室检验报告项目指标结果
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyzbjg>
     * @Throws
     * @Date 2022/5/13 10:41
     **/
    @Override
    public List<TbJyzbjg> listLabSurveyIndexResult(Map map) {
        return labSurveyReportDAO.listLabSurveyIndexResult(map);
    }

    /**
     *  查询实验室检验报告细菌结果
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyxjjg>
     * @Throws
     * @Date 2022/5/13 10:50
     **/
    @Override
    public List<TbJyxjjg> listLabSurveyGermResult(Map map) {
        return labSurveyReportDAO.listLabSurveyGermResult(map);
    }

    /**
     *  查询实验室检验报告药敏结果
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyymjg>
     * @Throws
     * @Date 2022/5/13 10:51
     **/
    @Override
    public List<TbJyymjg> listLabSurveyDrugSensitivityResult(Map map) {
        return labSurveyReportDAO.listLabSurveyDrugSensitivityResult(map);
    }
}

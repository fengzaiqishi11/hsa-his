package cn.hsa.interf.healthInfo.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.bo.LabSurveyReportBO;
import cn.hsa.module.interf.healthInfo.entity.*;
import cn.hsa.module.interf.healthInfo.service.LabSurveyReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liudawen
 * @date 2022/5/11
 */
@Service("labSurveyReportService_provider")
public class LabSurveyReportServiceImpl implements LabSurveyReportService {
    @Resource
    private LabSurveyReportBO labSurveyReportBO;

    /**
     *  查询实验室检验报告
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyzbg>>
     * @Throws
     * @Date 2022/5/18 15:12
     **/
    @Override
    public WrapperResponse<List<TbJyzbg>> getLabSurveyReport(Map map) {
        return WrapperResponse.success(labSurveyReportBO.listLabSurveyReport(map));
    }

    /**
     *  查询实验室检验报告项目分组记录信息
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyxmfzjl>>
     * @Throws
     * @Date 2022/5/18 15:14
     **/
    @Override
    public WrapperResponse<List<TbJyxmfzjl>> getLabSurveyGroupRecord(Map map) {
        return WrapperResponse.success(labSurveyReportBO.listLabSurveyGroupRecord(map));
    }

    /**
     *  查询实验室检验报告项目指标结果
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyzbjg>>
     * @Throws
     * @Date 2022/5/18 15:14
     **/
    @Override
    public WrapperResponse<List<TbJyzbjg>> getLabSurveyIndexResult(Map map) {
        return WrapperResponse.success(labSurveyReportBO.listLabSurveyIndexResult(map));
    }

    /**
     *  查询实验室检验报告细菌结果
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyxjjg>>
     * @Throws
     * @Date 2022/5/18 15:14
     **/
    @Override
    public WrapperResponse<List<TbJyxjjg>> getLabSurveyGermResult(Map map) {
        return WrapperResponse.success(labSurveyReportBO.listLabSurveyGermResult(map));
    }

    /**
     *  查询实验室检验报告药敏结果
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.interf.healthInfo.entity.TbJyymjg>>
     * @Throws
     * @Date 2022/5/18 15:14
     **/
    @Override
    public WrapperResponse<List<TbJyymjg>> getLabSurveyDrugSensitivityResult(Map map) {
        return WrapperResponse.success(labSurveyReportBO.listLabSurveyDrugSensitivityResult(map));
    }
}

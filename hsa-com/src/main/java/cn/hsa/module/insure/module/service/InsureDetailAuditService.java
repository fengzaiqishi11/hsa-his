package cn.hsa.module.insure.module.service;

import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import org.springframework.stereotype.Service;

/**
 * @Description 明细审核服务service
 * @ClassName InsureDetailAuditService
 * @Author 产品三部-郭来
 * @Date 2022/5/9 13:39
 * @Version 1.0
 **/
public interface InsureDetailAuditService {


    /**
     * @Description 明细审核事前分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     * @param analysisDTO
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     */
    public AnaResJudgeDTO upldBeforeAnalysisDTO(AnalysisDTO analysisDTO,String hospCode,String orgCode);

    /**
     * @Description 明细审核事中分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     * @param analysisDTO
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     */
    public AnaResJudgeDTO upldMidAnalysisDTO(AnalysisDTO analysisDTO,String hospCode,String orgCode);
}

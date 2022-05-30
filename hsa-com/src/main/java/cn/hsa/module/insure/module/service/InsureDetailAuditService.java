package cn.hsa.module.insure.module.service;

import cn.hsa.module.insure.module.dto.AnaResJudgeDTO;
import cn.hsa.module.insure.module.dto.AnalysisDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

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
     * @param map
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     */
    public AnaResJudgeDTO upldBeforeAnalysisDTO(Map map);

    /**
     * @Description 明细审核事中分析
     * @Author 产品三部-郭来
     * @Date 2022-05-09 14:30
     * @param map
     * @return cn.hsa.module.insure.module.dto.AnaResJudgeDTO
     */
    public AnaResJudgeDTO upldMidAnalysisDTO(Map map);
}

package cn.hsa.report.business.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import cn.hsa.module.report.business.service.ReportPrintService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName ReportPrintServiceImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/2/24 16:53
 * @Version 1.0
 **/
@HsafRestPath("/service/report/business")
@Service("reportPrintService_provider")
public class ReportPrintServiceImpl extends HsafService implements ReportPrintService {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;

    @Resource
    private ReportDataDownLoadService reportDataDownLoadService;

    @Override
    public WrapperResponse<ReportReturnDataDTO> sumDeclareInfoPrints(Map<String, Object> paraMap) {

        WrapperResponse<Map<String,Object>> result = insureUnifiedPayReversalTradeService_consumer.querySumDeclareInfoPrint(paraMap);
        result.getData().put("hospCode",paraMap.get("hospCode"));
        result.getData().put("crterId",paraMap.get("crteId"));
        result.getData().put("crterName",paraMap.get("crteName"));
        WrapperResponse<ReportReturnDataDTO> resultDTO = reportDataDownLoadService.saveBuild(result.getData());
        return resultDTO;
    }
}

package cn.hsa.module.report.business.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;

import java.util.Map;

public interface ReportPrintService {

    WrapperResponse<ReportReturnDataDTO> sumDeclareInfoPrints(Map<String, Object> paraMap);
}

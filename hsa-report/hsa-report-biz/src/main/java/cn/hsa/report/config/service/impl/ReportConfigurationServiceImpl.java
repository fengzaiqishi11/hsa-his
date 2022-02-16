package cn.hsa.report.config.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.report.config.bo.ReportConfigurationBO;
import cn.hsa.module.report.config.service.ReportConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ReportConfigurationServiceImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@HsafRestPath("/service/report/config")
@Service("reportConfigurationService_provider")
public class ReportConfigurationServiceImpl extends HsafService implements ReportConfigurationService {

    @Autowired
    private ReportConfigurationBO reportConfigurationBO;

}

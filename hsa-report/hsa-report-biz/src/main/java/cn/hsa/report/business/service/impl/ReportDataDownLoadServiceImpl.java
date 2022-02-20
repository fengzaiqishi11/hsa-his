package cn.hsa.report.business.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.bo.ReportDataDownLoadBO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName ReportConfigurationServiceImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@HsafRestPath("/service/report/business")
@Service("reportDataDownLoadService_provider")
public class ReportDataDownLoadServiceImpl extends HsafService implements ReportDataDownLoadService {

    @Autowired
    private ReportDataDownLoadBO reportDataDownLoadBO;

    @Override
    public WrapperResponse<String> saveBuild(Map map) {
        return WrapperResponse.success(reportDataDownLoadBO.saveBuild(map));
    }
}

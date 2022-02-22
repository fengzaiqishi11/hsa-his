package cn.hsa.report.config.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.config.bo.ReportConfigurationBO;
import cn.hsa.module.report.config.service.ReportConfigurationService;
import cn.hsa.util.MapUtils;
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
@HsafRestPath("/service/report/config")
@Service("reportConfigurationService_provider")
public class ReportConfigurationServiceImpl extends HsafService implements ReportConfigurationService {

    @Autowired
    private ReportConfigurationBO reportConfigurationBO;

    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = reportConfigurationBO.queryPage(MapUtils.get(map,"reportConfigurationDTO"));
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(reportConfigurationBO.insert(MapUtils.get(map,"reportConfigurationDTO")));
    }

    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(reportConfigurationBO.delete(MapUtils.get(map,"reportConfigurationDTO")));
    }

    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(reportConfigurationBO.update(MapUtils.get(map,"reportConfigurationDTO")));
    }

}

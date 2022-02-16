package cn.hsa.report.config.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.report.config.bo.ReportConfigurationBO;
import cn.hsa.module.report.config.dao.ReportConfigurationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ReportConfigurationBOImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Component
public class ReportConfigurationBOImpl extends HsafBO implements ReportConfigurationBO {

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;

}

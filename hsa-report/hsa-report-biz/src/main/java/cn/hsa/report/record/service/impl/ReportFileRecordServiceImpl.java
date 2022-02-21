package cn.hsa.report.record.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.report.record.bo.ReportFileRecordBO;
import cn.hsa.module.report.record.service.ReportFileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ReportFileRecordServiceImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@HsafRestPath("/service/report/record")
@Service("reportFileRecordService_provider")
public class ReportFileRecordServiceImpl extends HsafService implements ReportFileRecordService {

    @Autowired
    private ReportFileRecordBO reportFileRecordBO;


}

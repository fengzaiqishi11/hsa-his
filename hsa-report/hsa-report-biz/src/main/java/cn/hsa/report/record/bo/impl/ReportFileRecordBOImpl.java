package cn.hsa.report.record.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.report.record.bo.ReportFileRecordBO;
import cn.hsa.module.report.record.dao.ReportFileRecordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ReportFileRecordBOImpl
 * @Deacription 服务层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@Component
public class ReportFileRecordBOImpl extends HsafBO implements ReportFileRecordBO {

    @Autowired
    private ReportFileRecordDAO reportFileRecordDAO;

}

package cn.hsa.report.business.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import cn.hsa.module.report.business.bo.ReportDataDownLoadBO;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import cn.hsa.report.business.bo.impl.factory.ReportBusinessFactory;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ureport.hospCode}")
    private String ureportHospCode;

    @Value("${report.hospCode}")
    private String reportHospCode;

    @Autowired
    private ReportDataDownLoadBO reportDataDownLoadBO;

    @Autowired
    private ReportBusinessFactory reportBusinessFactory;

    @Override
    public WrapperResponse<ReportReturnDataDTO> saveBuild(Map map) {
        String hospCode = (String) map.get("hospCode");
        if(StringUtils.isNotEmpty(ureportHospCode)){
            if(ureportHospCode.contains(hospCode)&&"settleDeclareDetlSheetBOImpl".equals(map.get("businessType"))){
                map.put("businessType","settleDeclareDetlSheetmlyBOImpl");
            }
        }
        //益阳子仲肾脏病医院
        if(StringUtils.isNotEmpty(reportHospCode)){
            if(reportHospCode.contains(hospCode)&&"settleDeclareDetlSheetBOImpl".equals(map.get("businessType"))){
                map.put("businessType","settleDeclareDetlZZSheetBOImpl");
            }
        }
        ReportBusinessBO reportBusinessProcess = reportBusinessFactory.getReportBusinessProcess(map.get("businessType").toString());
        map = reportBusinessProcess.getReportDataMap(map);
        return WrapperResponse.success(reportDataDownLoadBO.saveBuild(map));
    }

    @Override
    public WrapperResponse<Boolean> deleteReport(Map map) {
        return WrapperResponse.success(reportDataDownLoadBO.deleteReport(map));
    }

}

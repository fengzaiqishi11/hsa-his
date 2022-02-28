package cn.hsa.module.report.business.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @ClassName ReportDataDownLoadService
 * @Deacription 服务接口层
 * @Author liuzhoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
@FeignClient(value = "hsa-report")
public interface ReportDataDownLoadService {

    /**
     * 报表生成
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @menthod saveBuild()
     * @author liuzhuoting
     * @date 2022/02/18 09:30
     **/
    @PostMapping("/service/report/business/saveBuild")
    WrapperResponse<ReportReturnDataDTO> saveBuild(Map map);

    /**
     * 报表删除
     *
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @menthod deleteReport()
     * @author liuzhuoting
     * @date 2022/02/18 09:30
     **/
    @PostMapping("/service/report/business/deleteReport")
    WrapperResponse<Boolean> deleteReport(Map map);


}

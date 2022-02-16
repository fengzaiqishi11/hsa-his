package cn.hsa.module.report.config.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName ReportConfigurationService
 * @Deacription 服务接口层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@FeignClient(value = "hsa-report")
public interface ReportConfigurationService {

}

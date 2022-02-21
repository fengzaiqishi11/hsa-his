package cn.hsa.module.report.record.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName ReportFileRecordService
 * @Deacription 服务接口层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@FeignClient(value = "hsa-report")
public interface ReportFileRecordService {

}
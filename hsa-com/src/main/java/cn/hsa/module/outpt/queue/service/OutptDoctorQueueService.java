package cn.hsa.module.outpt.queue.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.queue.service
 * @Class_name: OutptDoctorQueueService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptDoctorQueueService {

    @GetMapping("/service/outpt/doctorQueue/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    @GetMapping("/service/outpt/doctorQueue/queryAll")
    WrapperResponse<List<OutptDoctorQueueDto>> queryAll(Map map);
}

package cn.hsa.module.outpt.queue.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.queue.service
 * @Class_name: OutptClassesQueueService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptClassesQueueService {
    /**
     * @Method queryPage
     * @Desrciption 分页查询
       @params [map]
     * @Author chenjun
     * @Date   2020/10/15 8:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/outpt/queue/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method queryClassesDoctor
     * @Desrciption 查询排班医生
       @params [map]
     * @Author chenjun
     * @Date   2020/10/15 8:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
    **/
    @GetMapping("/service/outpt/queue/queryClassesDoctor")
    WrapperResponse<List<Map>> queryClassesDoctor(Map map);

    @PostMapping("/service/outpt/queue/saveClassesQueue")
    WrapperResponse<Boolean> saveClassesQueue(Map map);

    @PostMapping("/service/outpt/queue/saveProduceQueue")
    WrapperResponse<Boolean> saveProduceQueue(Map map);

    @PostMapping("/service/outpt/queue/deleteQueue")
    WrapperResponse<Boolean> deleteQueue(Map map);
}

package cn.hsa.outpt.queue.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.queue.bo.OutptDoctorQueueBO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.queue.service.OutptDoctorQueueService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.queue.service.impl
 * @Class_name: OutptDoctorQueueServiceImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/13 9:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/doctorQueue")
@Slf4j
@Service("outptDoctorQueueService_provider")
public class OutptDoctorQueueServiceImpl implements OutptDoctorQueueService {

    @Resource
    OutptDoctorQueueBO outptDoctorQueueBO;

    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = outptDoctorQueueBO.queryPage(MapUtils.get(map,"outptDoctorQueueDto"));
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<List<OutptDoctorQueueDto>> queryAll(Map map) {
        return WrapperResponse.success(outptDoctorQueueBO.queryAll(MapUtils.get(map,"outptDoctorQueueDto")));

    }
}
package cn.hsa.outpt.queue.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.queue.bo.OutptClassesQueueBO;
import cn.hsa.module.outpt.queue.service.OutptClassesQueueService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.queue.service.impl
 * @Class_name: OutptClassesQueueServiceImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 15:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/classesQueue")
@Slf4j
@Service("outptClassesQueueService_provider")
public class OutptClassesQueueServiceImpl implements OutptClassesQueueService {

    @Resource
    OutptClassesQueueBO outptClassesQueueBO;
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = outptClassesQueueBO.queryPage(MapUtils.get(map,"outptClassesQueueDto"));
        return WrapperResponse.success(pageDTO);
    }

    @Override
    public WrapperResponse<List<Map>> queryClassesDoctor(Map map) {
        return WrapperResponse.success(outptClassesQueueBO.queryClassesDoctor(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    @Override
    public WrapperResponse<Boolean> saveClassesQueue(Map map) {
        return WrapperResponse.success(outptClassesQueueBO.saveClassesQueue(MapUtils.get(map,"outptClassesQueueDto")));
    }

    @Override
    public WrapperResponse<Boolean> saveProduceQueue(Map map) {
        return WrapperResponse.success(outptClassesQueueBO.saveProduceQueue(MapUtils.get(map,"outptClassesQueueDto")));
    }

    @Override
    public WrapperResponse<Boolean> deleteQueue(Map map) {
        return WrapperResponse.success(outptClassesQueueBO.deleteQueue(MapUtils.get(map,"outptClassesQueueDto")));
    }
}
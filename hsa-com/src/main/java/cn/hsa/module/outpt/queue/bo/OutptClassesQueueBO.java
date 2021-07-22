package cn.hsa.module.outpt.queue.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.queue.bo
 * @Class_name: OutptClassesQueueBO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 15:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptClassesQueueBO {
    PageDTO queryPage(OutptClassesQueueDto outptClassesQueueDto);

    List<Map> queryClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO);

    boolean saveClassesQueue(OutptClassesQueueDto outptClassesQueueDto);

    boolean saveProduceQueue(OutptClassesQueueDto outptClassesQueueDto);

    boolean deleteQueue(OutptClassesQueueDto outptClassesQueueDto);

}

package cn.hsa.module.outpt.queue.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.queue.bo
 * @Class_name: OutptDoctorQueueBO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptDoctorQueueBO {

    PageDTO queryPage(OutptDoctorQueueDto outptDoctorQueueDto);

    List<OutptDoctorQueueDto> queryAll(OutptDoctorQueueDto outptDoctorQueueDto);
}

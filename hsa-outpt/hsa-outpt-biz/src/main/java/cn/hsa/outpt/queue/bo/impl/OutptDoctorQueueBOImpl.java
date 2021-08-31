package cn.hsa.outpt.queue.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.queue.bo.OutptDoctorQueueBO;
import cn.hsa.module.outpt.queue.dao.OutptDoctorQueueDao;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.outpt.queue.bo.impl
 * @Class_name: OutptDoctorQueueBOImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/13 9:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptDoctorQueueBOImpl implements OutptDoctorQueueBO {

    @Resource
    OutptDoctorQueueDao outptDoctorQueueDao;

    @Override
    public PageDTO queryPage(OutptDoctorQueueDto outptDoctorQueueDto) {
        // 设置分页信息
        PageHelper.startPage(outptDoctorQueueDto.getPageNo(), outptDoctorQueueDto.getPageSize());
        // 根据条件查询所
        List<OutptDoctorQueueDto> outptClassesDTOS = outptDoctorQueueDao.queryPage(outptDoctorQueueDto);
        return PageDTO.of(outptClassesDTOS);
    }

    @Override
    public List<OutptDoctorQueueDto> queryAll(OutptDoctorQueueDto outptDoctorQueueDto) {
        return outptDoctorQueueDao.queryAll(outptDoctorQueueDto);
    }
}
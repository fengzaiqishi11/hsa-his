package cn.hsa.module.outpt.queue.dao;

import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.queue.dao
 * @Class_name: OutptDoctorQueueDao
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptDoctorQueueDao {

    List<OutptDoctorQueueDto> queryPage(OutptDoctorQueueDto outptDoctorQueueDto);
    /** 查询分诊人数最少的医生 **/
    Map<String,String> queryLowestTriagePatientsDoctor(Map<String,Object> param);

    List<OutptDoctorQueueDto> queryAll(OutptDoctorQueueDto outptDoctorQueueDto);
    OutptDoctorQueueDto queryById(OutptDoctorQueueDto outptDoctorQueueDto);

    List<Map> queryDoctorQueueDel(Map map);

    List<String> queryDoctorQueueNotDel(Map map);

    List<Map> queryDoctorQueueByParam(Map map);

    void deleteDoctorQueue(List<String> list);

    void deleteDoctorRegister(List<String> list);

    void insertDoctorQueueBatch(List<Map> list);

    void deleteDoctorQueueByCqids(List<String> list);

}

package cn.hsa.module.outpt.queue.dao;

import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptDoctorRegisterDto;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.queue.dao
 * @Class_name: OutptClassesQueueDao
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptClassesQueueDao {

    List<OutptClassesQueueDto> queryPage(OutptClassesQueueDto outptClassesQueueDto);

    OutptClassesQueueDto queryByClassesQueueDTO(OutptClassesQueueDto outptClassesQueueDto);

    List<Map> queryClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO);

    List<OutptClassesQueueDto> queryClassesQueueByParam(Map map);

    List<Map> queryClassifyClassesData(Map map);

    List<Map> queryClassesDoctorData(Map map);

    List<OutptClassesQueueDto> queryClassesQueueParam(Map map);

    List<Map> queryHisGhlbYsData(Map map);

    List<Map> queryRegisterByCqId(OutptClassesQueueDto outptClassesQueueDto);

    void insertClassesQueue(OutptClassesQueueDto outptClassesQueueDto);
    /** 查询排班 **/
    Map<String,Object> queryClassifyClasses(Map<String,Object> map);

    void insertDoctorQueue(List<OutptDoctorQueueDto> list);
    /** 查询医生在某个班次是否存在排班 **/
    List<Map<String,Object>> queryDoctorQueueOfTodayByClassifyIdDoctorId(Map<String,Object> params);
    void insertDoctorRegisterBatch(List<OutptDoctorRegisterDto> list);

    void deleteClassesQueue(Map map);

    void insertClassesQueueBatch(List<Map> list);

    void deleteClassesQueueByIds(Map map);

    void updateClassesQueue(OutptClassesQueueDto outptClassesQueueDto);
}

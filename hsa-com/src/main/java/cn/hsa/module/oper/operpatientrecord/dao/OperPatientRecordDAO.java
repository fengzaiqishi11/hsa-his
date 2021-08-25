package cn.hsa.module.oper.operpatientrecord.dao;

import cn.hsa.module.oper.operpatientrecord.dto.OperPatientRecordDTO;

import java.util.List;
import java.util.Map;

/**
   * @Package_name: cn.hsa.module.oper.operpatientrecord.dao
   * @Class_name: OperPatientRecordDAO
   * @Describe: 手术病人查询数据库访问层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/23 11:20
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
public interface OperPatientRecordDAO {
    /**
     *
     *  返回手术病人信息列表
     * @Param operpatientrecord 手术病人信息
     * @Return:  List<OperPatientRecordDTO> 手术病人信息列表
    **/
    List<OperPatientRecordDTO> queryOperPatientRecordAll(OperPatientRecordDTO operPatientRecordDTO);

    /**
     *
     *  返回手术病人信息列表
     * @Param operpatientrecord 手术病人信息
     * @Return:  List<OperPatientRecordDTO> 手术病人信息列表
    **/
    List<OperPatientRecordDTO> queryOperPatientRecords(OperPatientRecordDTO operPatientRecordDTO);

    /**
     *  返回非手术疾病信息列表
     * @Param map 查询参数
     * @Return:  List<<Map<String,String>> 非手术类型疾病信息列表
    **/
    List<Map<String,String>> getNonSurgicalDiseasesInfo(Map<String,String> map);

}

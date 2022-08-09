package cn.hsa.module.mris.mrisHome.dao;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.inpt.dao
 * @Class_name: InptMrisInfoDAO
 * @Describe: 病案首页-基本信息接口dao
 * @Author: liuliyun
 * @Eamil: liuliyun@powersi.com
 * @Date: 2021-07-19 15:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptMrisInfoDAO {
    /**
     * @Menthod: getNameByCodeAndValue
     * @Desrciption: 基础业务代码查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-19 15:30
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> getSysValue(Map<String, Object> map);

    /**
     * @Menthod: getMrisBaseInfo
     * @Desrciption: 病案基础信息查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-20 9:46
     * @Return: List<LinkedHashMap < String, Object>>
     **/
    List<LinkedHashMap<String,Object>> getMrisBaseInfo(Map<String, Object> map);

    /**
     * @Menthod: getMrisDiagnose
     * @Desrciption: 病案诊断信息查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-21 11:46
     * @Return:  List<MrisDiagnoseDTO>
     **/
    List<Map<String, Object>> getMrisDiagnose(Map<String, Object> map);


    /**
     * @Menthod: getMrisOperInfo
     * @Desrciption: 病案手术信息查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-21 16:09
     * @Return:  List<Map<String, Object>>
     **/
    List<Map<String, Object>> getMrisOperInfo(Map<String, Object> map);


    /**
     * @Menthod: queryUploadType
     * @Desrciption: 查询需要导出的数据
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-22 19:02
     * @Return:  List<Map<String, Object>>
     **/
    List<Map<String,Object>>  queryUploadType(Map<String, Object> map);

    /**
     * @Menthod: queryData
     * @Desrciption: 执行数据查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: liyun.liu
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-07-22 19:08
     * @Return:  List<LinkedHashMap<String,Object>>
     **/
    List<LinkedHashMap<String,Object>> queryData(@Param(value = "sql") String sql);

    /**
     * @Menthod: getMrisBaseInfo
     * @Desrciption: 中医病案基础信息查询
     * @Param: 1. hospCode: 医院编码, 2、startTime:开始时间，3、endTime:结束时间，4、keyword: 搜索关键字, 5、statusCode: 在院状态
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-01-20 16:14
     * @Return: List<LinkedHashMap < String, Object>>
     **/
    List<LinkedHashMap<String,Object>> getTcmMrisBaseInfo(Map<String, Object> map);

    /**
     * @Menthod: getTcmMrisDiagnose
     * @Desrciption: 中医病案诊断信息查询（西医诊断）
     * @Param: 1. visitId 就诊id, 2 hospCode 医院编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-01-20 15:39
     * @Return:  List<MrisDiagnoseDTO>
     **/
    List<Map<String, Object>> getTcmMrisDiagnose(Map<String, Object> map);

    /**
     * @Menthod: getTcmDiagnose
     * @Desrciption: 中医病案诊断信息查询（中医诊断）
     * @Param: 1、visitId 就诊id, 2、hospCode 医院编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-01-20 15:35
     * @Return:  List<MrisDiagnoseDTO>
     **/
    List<Map<String, Object>> getTcmDiagnose(Map<String, Object> map);


    /**
     * @Menthod: getTcmMrisOperInfo
     * @Desrciption: 病案手术信息查询
     * @Param: 1、visitId： 就诊id, 2、hospCode： 医院编码
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-01-20 16:46
     * @Return:  List<Map<String, Object>>
     **/
    List<Map<String, Object>> getTcmMrisOperInfo(Map<String, Object> map);

    /**
     * @Menthod: updateInptVisitExportMris
     * @Desrciption: 批量更新住院病人病案导出状态
     * @Param: ids
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-08-02 16:33
     * @Return:  int
     **/
    int updateInptVisitExportMris(List<InptVisitDTO> inptVisitDTOS);

}

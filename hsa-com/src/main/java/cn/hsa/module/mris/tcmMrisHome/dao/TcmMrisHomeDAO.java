package cn.hsa.module.mris.tcmMrisHome.dao;

import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.entity.InptBedChangeDO;
import cn.hsa.module.mris.mrisHome.entity.MrisTurnDeptDO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmDiagnoseDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisDiagnoseDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.dao
 * @class_name: TcmMrisHomeDAO
 * @Description: 中医病案首页DAO
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/01/17 11:18
 * @Company: 创智和宇
 **/
public interface TcmMrisHomeDAO {



    /**
     * @Method: insertTcmMrisBaseInfo
     * @Description: 新增病案基本患者信息
     * @Param: [tcmMrisBaseInfoDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:18
     * @Return: 影响行数
     */
    int insertTcmMrisBaseInfo(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO);

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改中医病案基本患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:19
     * @Return: 影响行数
     **/
    int updateTcmMrisBaseInfo(TcmMrisBaseInfoDO tcmMrisBaseInfoDO);

    /**
     * @Method: deleteTcmBaseInfoByVisitId
     * @Description: 删除中医病案基本患者信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:20
     * @Return: 影响行数
     **/
    int deleteTcmMrisBaseInfoByVisitId(Map<String, Object> map);


    /**
     * @Method: deleteTcmMrisCostByVisitId
     * @Description: 删除中医病案费用信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:21
     * @Return: 影响行数
     **/
    int deleteTcmMrisCostByVisitId(Map<String, Object> map);

    /**
     * @Method: deleteTcmMrisDiagnoseByVisitId
     * @Description: 删除中医病案诊断信息(西医)
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:29
     * @Return: 影响行数
     **/
    int deleteTcmMrisDiagnoseByVisitId(Map<String, Object> map);

    /**
     * @Method: deleteTcmDiagnoseByVisitId
     * @Description: 删除中医病案诊断信息(中医)
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:29
     * @Return: 影响行数
     **/
    int deleteTcmDiagnoseByVisitId(Map<String, Object> map);

    /**
     * @Method: deleteTcmMrisOperInfoByVisitId
     * @Description: 删除病案手术信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:32
     * @Return: 影响行数
     **/
    int deleteTcmMrisOperInfoByVisitId(Map<String, Object> map);

    /**
     * @Method: getTcmMrisBaseInfoByVisitId
     * @Description: 抽取HIS患者基本信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:33
     * @Return: TcmMrisBaseInfoDTO
     **/
    TcmMrisBaseInfoDTO getTcmMrisBaseInfoByVisitId(Map<String, Object> map);


    /**
     * @Method: insertTcmMrisDiagnoseBatch
     * @Description: 批量插入中医病案诊断信息（西医诊断）
     * @Param: [tcmMrisDiagnoseDOS]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:38
     * @Return: 影响行数
     **/
    int insertTcmMrisDiagnoseBatch(List<TcmMrisDiagnoseDTO> tcmMrisDiagnoseDOS);

    /**
     * @Method: insertTcmDiagnoseBatch
     * @Description: 批量插入中医病案诊断信息（中医诊断）
     * @Param: [tcmMrisDiagnoseDOS]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:38
     * @Return: 影响行数
     **/
    int insertTcmDiagnoseBatch(List<TcmDiagnoseDTO> tcmDiagnoseDTOS);

    /**
     * @Method: queryHisBedChanfeInfo
     * @Description: 获取床位异动信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:36
     * @Return: java.util.List<cn.hsa.module.mris.entity.InptBedChangeDO>
     **/
    List<InptBedChangeDO> queryHisBedChanfeInfo(Map<String, Object> map);

    /**
     * @Method: deleteTcmMrisTurnDeptByVisitId
     * @Description: 删除中医病案转科信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:44
     * @Return: 影响行数
     **/
    int deleteTcmMrisTurnDeptByVisitId(Map<String,Object> map);

    /**
     * @Method: insertTcmMrisTurnDeptBatch
     * @Description: 批量新增转科信息
     * @Param: [tcmMrisTurnDeptDOS]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:45
     * @Return: 影响行数
     **/
    int insertTcmMrisTurnDeptBatch(List<TcmMrisTurnDeptDO> tcmMrisTurnDeptDOS);


    /**
     * @Method: insertTcmMrisOperBatch
     * @Description: 批量插入手术信息
     * @Param: [tcmMrisOperInfoDOS]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:46
     * @Return: 影响行数
     **/
    int insertTcmMrisOperBatch(List<TcmMrisOperInfoDO> tcmMrisOperInfoDOS);



    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: 查询中医病案患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:47
     * @Return: TcmMrisBaseInfoDTO
     **/
    TcmMrisBaseInfoDTO getTcmMrisBaseInfo(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queyTcmMrisTurnDeptPage
     * @Description: 查询病案转科信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:48
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisTurnDeptDO>
     **/
    List<TcmMrisTurnDeptDO> queyTcmMrisTurnDeptPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: getTcmMrisCost
     * @Description: 查询病案费用信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:53
     * @Return: TcmMrisCostDO
     **/
    TcmMrisCostDO getTcmMrisCost(InptVisitDTO inptVisitDTO);


    /**
     * @Method: queryTcmMrisOperInfoPage
     * @Description: 查询病案手术信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:54
     * @Return: List<TcmMrisOperInfoDO>
     **/
    List<TcmMrisOperInfoDO> queryTcmMrisOperInfoPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryTcmMrisDiagnosePage
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 13:32
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO>
     **/
    List<TcmMrisDiagnoseDO> queryTcmMrisDiagnosePage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryTcmMrisDiagnosePage
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 13:32
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO>
     **/
    List<TcmDiagnoseDO> queryTcmDiagnosePage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateTcmMrisCost
     * @Description: 修改病案费用信息
     * @Param: [tcmMrisCostDO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 13:32
     * @Return: 影响行数
     **/
    int updateTcmMrisCost(TcmMrisCostDO tcmMrisCostDO);


    /**
     * @Method: insertTcmMrisCost
     * @Description: 新增费用信息
     * @Param: [tcmMrisCostDO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 13:34
     * @Return: int
     **/
    int insertTcmMrisCost(TcmMrisCostDO tcmMrisCostDO);

    /**
     * @Method: getInCnt
     * @Description: 获取入院次数
     * @Param: [mrisBaseInfoDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 15:00
     * @Return: java.lang.Integer
     **/
    int getInCnt(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO);

    /**
     * @Method: queryTcmMrisTurnDeptPage
     * @Description: 查询病案转科信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 16:08
     * @Return: List<MrisTurnDeptDO>
     **/
    List<MrisTurnDeptDO> queryTcmMrisTurnDeptPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryHisDiagnoseInfo
     * @Description: 获取诊断信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/19 16:08
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO>
     **/
    List<InptDiagnoseDTO> queryHisDiagnoseInfo(Map<String,Object> map);


    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询中医已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:39
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryOutHospPatientPageZY(InptVisitDTO inptVisitDTO);


    /**
     * @Menthod: queryTcmExportNum
     * @Desrciption: 查询导出数据的数量
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-08-03 09:35
     * @Return:  Map
     **/
    Map queryTcmExportNum(Map<String,Object> map);

    List<InptVisitDTO> queryTcmUnExportData(Map<String,Object> map);

}
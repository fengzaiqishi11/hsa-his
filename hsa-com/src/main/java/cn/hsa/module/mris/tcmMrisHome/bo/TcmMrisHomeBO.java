package cn.hsa.module.mris.tcmMrisHome.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisBaseInfoDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.mris.tcmMrisHome.bo
* @class_name: TcmMrisHomeBO
* @Description: 中医病案首页BO
* @Author: liuliyun
* @Email: liyun.liu@powersi.com
* @Date: 2022/01/17 09:12
* @Company: 创智和宇
**/
public interface TcmMrisHomeBO {

    /**
     * @Method: loadMrisInfo
     * @Description: 载入病案信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 09:16
     * @Return: Map<String,Object>
     **/
    Map<String,Object> updateLoadMrisInfo(Map<String, Object> map);

    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: 查询中医病案患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 09:16
     * @Return: TcmMrisBaseInfoDO
     **/
    TcmMrisBaseInfoDTO getTcmMrisBaseInfo(InptVisitDTO inptVisitDTO);


    /**
     * @Method: getTcmMrisCost
     * @Description: 查询中医病案费用信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 10:50
     * @Return: TcmMrisCostDO
     **/
    TcmMrisCostDO getTcmMrisCost(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryTcmMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 10:50
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryTcmMrisOperInfo(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryTcmMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 10:54
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryTcmMrisDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改中医病案患者信息
     * @Param: [mrisBaseDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 10:54
     * @Return: Boolean
     **/
    Boolean updateTcmMrisBaseInfo(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO);

    /**
     * @Method: updateTcmMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:07
     * @Return: Boolean
     **/
    Boolean updateTcmMrisCost(TcmMrisCostDO tcmMrisCostDO);

    /**
     * @Method: queryTcmAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:08
     * @Return: java.util.Map
     **/
    Map<String, Object> queryAllTcmMrisInfo(Map<String, Object> map);

    /**
     * @Method: saveTcmMrisInfo
     * @Description: 保存中医病案信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:12
     * @Return: Boolean
     **/
    Boolean saveTcmMrisInfo(TcmMrisBaseInfoDTO tcmMrisBaseInfoDTO);

    /**
     * @Method: updateTcmMrisFeesInfo
     * @Description: 更新中医费用信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 11:13
     * @Return: Map<String,Object>
     **/
    Map<String,Object> updateTcmMrisFeesInfo(Map<String, Object> map);

    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:46
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryOutHospPatientPageZY(InptVisitDTO inptVisitDTO);


    /**@Method queryExportTcmNum
     * @Author liuliyun
     * @Description 获取中医病案导出的条数
     * @Date 2022/08/18 15:38
     * @Param [map]
     * @return  map
     **/
    Map<String,Object> queryExportTcmNum(Map<String, Object> map);


}

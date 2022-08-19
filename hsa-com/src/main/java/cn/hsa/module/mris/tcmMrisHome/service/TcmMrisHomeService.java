package cn.hsa.module.mris.tcmMrisHome.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisControlDO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.mris.tcmMrisHome.service
 * @class_name: TcmMrisHomeService
 * @Description: 病案首页Service
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:56
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-tcm")
public interface TcmMrisHomeService {



    /**
     * @Method: loadMrisInfo
     * @Description:载入病人信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:35
     * @Return:  Map<String,Object>
     **/
    @PutMapping("/service/tcmMris/mrisHome/loadMrisInfo")
    Map<String,Object> loadMrisInfo(Map<String, Object> selectMap);

    /**
     * @Method: getTcmMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:36
     * @Return: TcmMrisBaseInfoDTO
     **/
    @GetMapping("/service/tcmMris/mrisHome/getTcmMrisBaseInfo")
    TcmMrisBaseInfoDTO getTcmMrisBaseInfo(Map<String, Object> selectMap);

    /**
     * @Method: updateTcmMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:37
     * @Return: Boolean
     **/
    @PutMapping("/service/tcmMris/mrisHome/updateTcmMrisBaseInfo")
    Boolean updateTcmMrisBaseInfo(Map<String, Object> selectMap);

    /**
     * @Method: updateTcmMrisCost
     * @Description: 修改病案费用信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:40
     * @Return: Boolean
     **/
    @PutMapping("/service/tcmMris/mrisHome/updateTcmMrisCost")
    Boolean updateTcmMrisCost(Map<String, Object> selectMap);

    /**
     * @Method: getTcmMrisCost
     * @Description: 查询病案费用信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:41
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @GetMapping("/service/tcmMris/mrisHome/getTcmMrisCost")
    TcmMrisCostDO getTcmMrisCost(Map<String, Object> selectMap);

    /**
     * @Method: queryTcmMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:42
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/tcmMris/mrisHome/queryTcmMrisOperInfo")
    PageDTO queryTcmMrisOperInfo(Map<String, Object> selectMap);

    /**
     * @Method: queryTcmMrisDiagnose
     * @Description: 查询病案诊断信息(西医)
     * @Param: [selectMap]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:43
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/tcmMris/mrisHome/queryTcmMrisDiagnose")
    PageDTO queryTcmMrisDiagnose(Map<String, Object> selectMap);


    /**
     * @Method: queryAllTcmMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:46
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @GetMapping("/service/tcmMris/mrisHome/queryAllTcmMrisInfo")
    Map<String,Object> queryAllTcmMrisInfo(Map<String, Object> map);


    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:48
     * @Return: Boolean
     **/
    Boolean saveMrisInfo(Map map);

    /**
     * @Method: updateTcmMrisFeesInfo
     * @Description: 更新费用信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/17 14:48
     * @Return: Map<String,Object>
     **/
    Map<String,Object> updateTcmMrisFeesInfo(Map<String, Object> map);


    /**
     * @Method: importCSVTcmMrisInfo
     * @Description: 导出中医病案首页数据
     * @param map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/01/19 09:02
     * @return String
     */
    @GetMapping("/service/mris/mrisHome/importCSVTcmMrisInfo")
    WrapperResponse<String> importCSVTcmMrisInfo(Map map) throws Exception;


    /**
     * @Method: queryOutHospPatientPageZY
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/8 10:51
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/queryOutHospPatientPageZY")
    PageDTO queryOutHospPatientPageZY(Map<String,Object> selectMap);


    /**
     * @Method: exportTcmMrisInfoToCsv
     * @Description: 导出中医病案首页数据(hqms)
     * @param map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/08/18 14:40
     * @return String
     */
    @GetMapping("/service/tcmMris/mrisHome/exportTcmMrisInfoToCsv")
    WrapperResponse<String> exportTcmMrisInfoToCsv(Map map) throws Exception;


    /**@Method queryExportTcmNum
     * @Author liuliyun
     * @Description 获取中医病案导出的条数
     * @Date 2022/08/18 15:32
     * @Param [map]
     * @return  map
     **/
    @GetMapping("/service/tcmMris/mrisHome/queryExportTcmNum")
    WrapperResponse<Map<String, Object>> queryExportTcmNum(Map<String, Object> map);

}

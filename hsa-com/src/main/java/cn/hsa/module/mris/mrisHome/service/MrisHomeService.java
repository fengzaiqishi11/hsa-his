package cn.hsa.module.mris.mrisHome.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisControlDO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.mris.mrisHome.service
 * @class_name: mrisHomeService
 * @Description: 病案首页Service
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:56
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-mris")
public interface MrisHomeService {

    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/queryOutHospPatientPage")
    PageDTO queryOutHospPatientPage(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisInfo
     * @Description:载入病人信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 11:02
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisInfo")
    Map<String,Object> updateMrisInfo(Map<String,Object> selectMap);

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/getMrisBaseInfo")
    MrisBaseInfoDTO getMrisBaseInfo(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisBaseInfo")
    Boolean updateMrisBaseInfo(Map<String,Object> selectMap);

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案转科信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/queyMrisTurnDept")
    PageDTO queyMrisTurnDeptPage(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisCost")
    Boolean updateMrisCost(Map<String,Object> selectMap);

    /**
     * @Method: getMrisCost
     * @Description: 查询病案费用信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    @GetMapping("/service/mris/mrisHome/getMrisCost")
    MrisCostDO getMrisCost(Map<String,Object> selectMap);

    /**
     * @Method: getMrisControl
     * @Description: 查询病案控制信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisControlDO
     **/
    @GetMapping("/service/mris/mrisHome/getMrisControl")
    MrisControlDO getMrisControl(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案控制信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisControl")
    Boolean updateMrisControl(Map<String,Object> selectMap);

    /**
     * @Method: queryMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/queryMrisOperInfoPage")
    PageDTO queryMrisOperInfoPage(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisOperInfo
     * @Description: 修改病案手术信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisOperInfo")
    Boolean updateMrisOperInfo(Map<String,Object> selectMap);


    /**
     * @Method: queryMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/mris/mrisHome/queryMrisDiagnosePage")
    PageDTO queryMrisDiagnosePage(Map<String,Object> selectMap);

    /**
     * @Method: updateMrisDiagnose
     * @Description: 修改病案诊断信息
     * @Param: [selectMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:40
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisDiagnose")
    Boolean updateMrisDiagnose(Map<String,Object> selectMap);

    /**
     * @Method: queryAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 18:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    @GetMapping("/service/mris/mrisHome/queryAllMrisInfo")
    Map<String,Object> queryAllMrisInfo(Map<String, Object> map);

    @PutMapping("/service/mris/mrisHome/upMrisForDRG")
    Map<String, Object> upMrisForDRG(Map<String, Object> map);

    /**
     * @Method: updateMrisTurnDept
     * @Description: 修改病案转科信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisTurnDept")
    Boolean updateMrisTurnDept(Map<String, Object> map);

    /**
     * @Method: updateMrisOper
     * @Description: 修改病案手术操作信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: Boolean
     **/
    @PutMapping("/service/mris/mrisHome/updateMrisOper")
    Boolean updateMrisOper(Map<String, Object> map);

    /**
     * @Method: uploadMrisInfo
     * @Description: 上传病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    Boolean uploadMrisInfo(Map<String, Object> map);

    /**
     * @Method: deleteInsureMrisInfo
     * @Description: 删除病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     **/
    Boolean deleteInsureMrisInfo(Map<String, Object> map);

    /**
     * @Method: saveMrisInfo
     * @Description: 保存病案信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 09:39
     * @Return: Boolean
     **/
    Boolean saveMrisInfo(Map map);

    /**
     * @Method: updateMrisFeesInfo
     * @Description: 更新费用信息
     * @Author: 廖继广
     * @Email: 847025096@qq.com
     * @Date: 2020/12/09 08:57
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<java.util.Map>
     **/
    Map<String,Object> updateMrisFeesInfo(Map<String, Object> map);

    /**
     * @Method queryAllOperation
     * @Desrciption  查询所有的住院病案首页的手术信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 15:33
     * @Return
    **/
    List<MrisOperInfoDO> queryAllOperation(Map<String, Object> map);

    /**
     * @Method queryAllDiagnose
     * @Desrciption  查询住院病案首页的所有诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/28 19:29
     * @Return
    **/
    List<MrisDiagnoseDO> queryAllDiagnose(Map<String, Object> map);

    /**
     *
     * @param map
     * @return String
     */
    @GetMapping("/service/mris/mrisHome/importMrisInfo")
    WrapperResponse<List<LinkedHashMap<String,Object>>> importMrisInfo(Map map) throws Exception;

    /**
     *
     * @param map
     * @return String
     */
    @GetMapping("/service/mris/mrisHome/importCSVMrisInfo")
    WrapperResponse<String> importCSVMrisInfo(Map map) throws Exception;

    /**@description 获取导出表头
     * @auth liuliyun
     * @date 2021/07/30
     * @param map
     * @return String
     */
    @GetMapping("/service/mris/mrisHome/getTableConfig")
    WrapperResponse<Map> getTableConfig(Map map) throws Exception;

    @PutMapping("/service/mris/mrisHome/upMrisForDIP")
    Map<String, Object> upMrisForDIP(Map<String, Object> map);
    @PutMapping("/service/mris/mrisHome/upMrisForDIPorDRG")
    WrapperResponse<Map<String, Object>> upMrisForDIPorDRG(Map<String, Object> map);


    /**@Method queryExportNum
     * @Author liuliyun
     * @Description 获取病案导出的条数
     * @Date 2022/08/03 09:28
     * @Param [map]
     * @return  map
     **/
    @GetMapping("/service/mris/mrisHome/queryExportNum")
    WrapperResponse<Map<String, Object>> queryExportNum(Map<String, Object> map);
}

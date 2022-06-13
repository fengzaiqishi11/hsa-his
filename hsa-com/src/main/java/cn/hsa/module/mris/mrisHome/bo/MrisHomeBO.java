package cn.hsa.module.mris.mrisHome.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisOperDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisTurnDeptDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisControlDO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.mris.mrisHome.bo
* @class_name: mrisHomeBO
* @Description: 病案首页BO
* @Author: LiaoJiGuang
* @Email: 847025096@qq.com
* @Date: 2020/9/21 15:56
* @Company: 创智和宇
**/
public interface MrisHomeBO {

    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryOutHospPatientPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateMrisInfo
     * @Description: 载入病案信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:23
     * @Return: Boolean
     **/
    Map<String,Object> updateMrisInfo(Map<String,Object> map);

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    MrisBaseInfoDTO getMrisBaseInfo(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queyMrisTurnDeptPage
     * @Description: 查询病案转科信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queyMrisTurnDeptPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: getMrisCost
     * @Description: 查询病案费用信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    MrisCostDO getMrisCost(InptVisitDTO inptVisitDTO);

    /**
     * @Method: getMrisControl
     * @Description: 查询病案控制信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisControl
     **/
    MrisControlDO getMrisControl(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryMrisOperInfo
     * @Description: 查询病案手术信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryMrisOperInfoPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryMrisDiagnose
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO queryMrisDiagnosePage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案患者信息
     * @Param: [mrisBaseDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    Boolean updateMrisBaseInfo(MrisBaseInfoDTO mrisBaseDTO);

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    Boolean updateMrisCost(MrisCostDO mrisCostDO);

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案控制信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    Boolean updateMrisControl(MrisControlDO mrisControlDO);

    /**
     * @Method: updateMrisOperInfo
     * @Description: 修改病案手术信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    Boolean updateMrisOperInfo(Map<String,Object> map);

    /**
     * @Method: updateMrisDiagnose
     * @Description: 修改病案诊断信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:40
     * @Return: Boolean
     **/
    Boolean updateMrisDiagnose(Map<String,Object> map);

    /**
     * @Method: queryAllMrisInfo
     * @Description: 获取患者所有病案信息
     * @Param: [visitId]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 18:22
     * @Return: java.util.Map
     **/
    Map<String, Object> queryAllMrisInfo(Map<String, Object> map);

    Map<String, Object> insertMrisForDRG(Map<String, Object> map);

    /**
     * @Method: updateMrisTurnDept
     * @Description: 修改病案转科信息
     * @Param: [mrisTurnDeptDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: cn.hsa.hsaf.core.framework.web.cn.WrapperResponse<Boolean>
     **/
    Boolean updateMrisTurnDept(MrisTurnDeptDTO mrisTurnDeptDTO);

    /**
     * @Method: updateMrisOper
     * @Description: 修改病案手术操作信息
     * @Param: [mrisOperDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/10/9 19:22
     * @Return: Boolean
     **/
    Boolean updateMrisOper(MrisOperDTO mrisOperDTO);

    /**
     * @Method: uploadMrisInfo
     * @Description: 上传病案信息
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/11/25 11:35
     * @Return: Boolean
     *
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
     * @Date: 2020/12/09 11:35
     * @Return: Boolean
     **/
    Boolean saveMrisInfo(MrisBaseInfoDTO mrisBaseInfoDTO);

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
     * @param inptVisitDTO
     * @Method queryAllOperation
     * @Desrciption 查询所有的住院病案首页的手术信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/27 15:33
     * @Return
     */
    List<MrisOperInfoDO> queryAllOperation(InptVisitDTO inptVisitDTO);

    /**
     * @param inptVisitDTO
     * @Method queryAllDiagnose
     * @Desrciption 查询住院病案首页的所有诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 19:29
     * @Return
     */
    List<MrisDiagnoseDO> queryAllDiagnose(InptVisitDTO inptVisitDTO);

    Map<String, Object> insertMrisForDIP(Map<String, Object> map);

    Map<String,Object> insertMrisForDIPorDRG(Map<String, Object> map);
}

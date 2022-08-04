package cn.hsa.module.mris.mrisHome.dao;

import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.fees.dto.InptPayDTO;
import cn.hsa.module.inpt.pasttreat.dto.InptPastAllergyDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.PayInfoDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.*;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisDiagnoseDO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisOperInfoDO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.mris.mrisHome.dao
 * @class_name: mrisHomeDAO
 * @Description: 病案首页DAO
 * @Author: LiaoJiGuang
 * @Email: 847025096@qq.com
 * @Date: 2020/9/21 15:56
 * @Company: 创智和宇
 **/
public interface MrisHomeDAO {

    /**
     * @Method: queryOutHospPatientPage
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/21 16:16
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryOutHospPatientPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryOutHospPatientPageZ
     * @Description: 分页查询已出院的患者信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyn.liu@powersi.com
     * @Date: 2021/7/27 10:03
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryOutHospPatientPageZ(InptVisitDTO inptVisitDTO);

    /**
     * @Method: insertMrisBaseInfo
     * @Description: 新增病案基本患者信息
     * @Param: [mrisBaseInfoDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:05
     * @Return: 影响行数
     */
    int insertMrisBaseInfo(MrisBaseInfoDTO mrisBaseInfoDTO);

    /**
     * @Method: updateMrisBaseInfo
     * @Description: 修改病案基本患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int updateMrisBaseInfo(MrisBaseInfoDO mrisBaseInfoDO);

    /**
     * @Method: deleteMrisBaseInfoByVisitId
     * @Description: 删除病案基本患者信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisBaseInfoByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisBaseInfoByVisitId
     * @Description: 删除病案控制信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisControlByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisCostByVisitId
     * @Description: 删除病案费用信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisCostByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisDiagnoseByVisitId
     * @Description: 删除病案诊断信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisDiagnoseByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisOperInfoByVisitId
     * @Description: 删除病案手术信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisOperInfoByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisTcmInfoByVisitId
     * @Description: 删除病案中医药信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisTcmInfoByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisTumourChemoByVisitId
     * @Description: 删除病案肿瘤化疗信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisTumourChemoByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisTumourInfoByVisitId
     * @Description: 删除病案肿瘤信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisTumourInfoByVisitId(Map<String,Object> map);

    /**
     * @Method: deleteMrisTurnDeptByVisitId
     * @Description: 删除病案转科信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: 影响行数
     **/
    int deleteMrisTurnDeptByVisitId(Map<String,Object> map);

    /**
     * @Method: getMrisBaseInfoByVisitId
     * @Description: 抽取HIS患者基本信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: cn.hsa.module.mris.dto.MrisBaseInfoDTO
     **/
    MrisBaseInfoDTO getMrisBaseInfoByVisitId(Map<String, Object> map);

    /**
     * @Method: getSysCodeValueByCode
     * @Description: 码表值统一转义
     * @Param: [centerProfileFileDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 16:06
     * @Return: cn.hsa.module.mris.dto.MrisBaseInfoDTO
     **/
    MrisBaseInfoDTO getSysCodeValueByCode(CenterProfileFileDTO centerProfileFileDTO);

    /**
     * @Method: queryAllergyInfo
     * @Description: 获取药物过敏信息集合
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/22 20:59
     * @Return: java.util.List<cn.hsa.module.inpt.pasttreat.dto.InptPastAllergyDTO>
     **/
    List<InptPastAllergyDTO> queryAllergyInfo(Map<String, Object> map);

    /**
     * @Method: getDoctorInfo
     * @Description: 获取科室医生信息
     * @Param: [centerProfileFileDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: cn.hsa.module.mris.dto.MrisBaseInfoDTO
     **/
    MrisBaseInfoDTO getDoctorInfo(Map<String, Object> map);

    /**
     * @Method: getInCnt
     * @Description: 获取入院次数
     * @Param: [mrisBaseInfoDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: java.lang.Integer
     **/
    int getInCnt(MrisBaseInfoDTO mrisBaseInfoDTO);

    /**
     * @Method: getBloodInfo
     * @Description: 获取入院次数
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: java.util.Map
     **/
    Map<String, Object> getBloodInfo(Map<String,Object> map);

    /**
     * @Method: queryHisDiagnoseInfo
     * @Description: 获取诊断信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO>
     **/
    List<InptDiagnoseDTO> queryHisDiagnoseInfo(Map<String,Object> map);

    /**
     * @Method: insertMrisDiagnoseBatch
     * @Description: 批量插入病案诊断信息
     * @Param: [mrisDiagnoseDOList]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: 影响行数
     **/
    int insertMrisDiagnoseBatch(List<MrisDiagnoseDO> mrisDiagnoseDOList);

    /**
     * @Method: queryHisBedChanfeInfo
     * @Description: 获取床位异动信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: java.util.List<cn.hsa.module.mris.entity.InptBedChangeDO>
     **/
    List<InptBedChangeDO> queryHisBedChanfeInfo(Map<String, Object> map);

    /**
     * @Method: insetMrisTurnDeptBatch
     * @Description: 批量新增转科信息
     * @Param: [mrisTurnDeptDOList]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 16:06
     * @Return: 影响行数
     **/
    int insetMrisTurnDeptBatch(List<MrisTurnDeptDO> mrisTurnDeptDOList);

    /**
     * @Method: queryOperRecordInfo
     * @Description: 获取批量手术信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 19:20
     * @Return: java.util.List<cn.hsa.module.mris.entity.OperInfoRecordDO>
     **/
    List<OperInfoRecordDO> queryOperRecordInfo(Map<String, Object> map);

    /**
     * @Method: insertMrisOperBatch
     * @Description: 批量插入手术信息
     * @Param: [mrisOperInfoDOList]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 19:27
     * @Return: 影响行数
     **/
    int insertMrisOperBatch(List<MrisOperInfoDO> mrisOperInfoDOList);

    /**
     * @Method: insertMrisControl
     * @Description: 插入病案控制信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/23 19:27
     * @Return: 影响行数
     **/
    int insertMrisControl(MrisControlDO mrisControlDO);

    /**
     * @Method: queryInptCostByVisit
     * @Description: 获取住院费用信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/25 09:48
     * @Return: 影响行数
     **/
    List<InptCostDTO> queryInptCostByVisit(Map<String, Object> map);

    /**
     * @Method: getInptSettleTotalPrice
     * @Description: 获取住院结算总费用金额
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/25 09:48
     * @Return: java.lang.Integer
     **/
    BigDecimal getInptSettleTotalPrice(Map<String, Object> map);

    /**
     * @Method: getInptSettleSelfPrice
     * @Description: 获取住院结算个人自付金额
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/25 09:48
     * @Return: java.lang.Integer
     **/
    BigDecimal getInptSettleSelfPrice(Map<String, Object> map);

    /**
     * @Method: queryInptCostBfcInfo
     * @Description: 获取住院按财务类别分类的费用信息
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/25 09:48
     * @Return: java.util.List<java.util.Map>
     **/
    List<Map<String,Object>> queryInptCostBfcInfo(Map<String, Object> map);

    /**
     * @Method: qureyMrisBfcInfo
     * @Description: 获取病案费用关联的财务分类编码
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 09:48
     * @Return: java.util.List<cn.hsa.module.sys.code.dto.SysCodeDetailDTO>
     **/
    List<SysCodeDetailDTO> qureyMrisBfcInfo(Map<String, Object> map);

    /**
     * @Method: queryInptBfcInfo
     * @Description: 获取住院费用关联的财务分类编码
     * @Param: [map]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/25 09:48
     * @Return: java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>
     **/
    List<BaseFinanceClassifyDTO> queryInptBfcInfo(Map<String, Object> map);

    /**
     * @Method: getMrisBaseInfo
     * @Description: 查询病案患者信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO
     **/
    MrisBaseInfoDTO getMrisBaseInfo(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queyMrisTurnDeptPage
     * @Description: 查询病案转科信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisTurnDeptDO>
     **/
    List<MrisTurnDeptDO> queyMrisTurnDeptPage(InptVisitDTO inptVisitDTO);

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
     * @Method: getMrisCost
     * @Description: 查询病案控制信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: cn.hsa.module.mris.mrisHome.entity.MrisCostDO
     **/
    MrisControlDO getMrisControl(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryMrisOperInfoPage
     * @Description: 查询病案控制信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 15:59
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO>
     **/
    List<MrisOperInfoDO> queryMrisOperInfoPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: queryMrisDiagnosePage
     * @Description: 查询病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/27 18:46
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO>
     **/
    List<MrisDiagnoseDO> queryMrisDiagnosePage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateMrisCost
     * @Description: 修改病案费用信息
     * @Param: [mrisCostDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: 影响行数
     **/
    int updateMrisCost(MrisCostDO mrisCostDO);

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案控制信息
     * @Param: [mrisControlDO]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: 影响行数
     **/
    int updateMrisControl(MrisControlDO mrisControlDO);

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案手术信息
     * @Param: [mrisOperInfoList]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    int updateMrisOperInfo(List<MrisOperInfoDO> mrisOperInfoList);

    /**
     * @Method: updateMrisControl
     * @Description: 修改病案诊断信息
     * @Param: [mrisDiagnoseDOList]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/28 09:36
     * @Return: Boolean
     **/
    int updateMrisDiagnose(List<MrisDiagnoseDO> mrisDiagnoseDOList);

    /**
     * @Method: insertMrisCost
     * @Description: 新增费用信息
     * @Param: [inserMrisCost]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/30 14:51
     * @Return: int
     **/
    int insertMrisCost(MrisCostDO mrisCostDTO);

    /**
     * @Method: queryInptPayByVisitId
     * @Description: 获取医疗支付方式
     * @Param: [inserMrisCost]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/30 14:51
     * @Return: int
     **/
    List<InptPayDTO> queryInptPayByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * 获取医保登记人员信息
     * @param map
     * @return
     */
    InsureIndividualVisitDTO getInsureVisitByVisitId(Map<String, Object> map);

    /**
     * 根据id获取值
     * @param map
     * @return
     */
    InptVisitDTO getVisitById(Map<String, Object> map);

    /**
     * 根据id获取值
     * @param map
     * @return
     */
    InsureIndividualBasicDTO getInsureIndividualBasic(Map<String,Object> map);

    /**
     * @Method: getSysParamterByMap
     * @Description: 获取医疗机构编码/名称
     * @Param: [sysParamterMap]
     * @Author: LiaoJiGuang
     * @Email: 847025096@qq.com
     * @Date: 2020/9/30 14:51
     * @Return: SysParameterDTO
     **/
    SysParameterDTO getSysParamterByMap(Map<String, String> sysParamterMap);


    /**
     * @Method queryMriCost
     * @Desrciption  查询住院病案首页的的---费用数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/29 16:04
     * @Return
    **/
    MrisCostDO queryMriCost(Map<String, Object> map);

    /**
     * @Method: queryHisBabyInfo
     * @Description: 获取婴儿信息
     * @Param: [map]
     * @Author: liuliyun
     * @Date: 2021/5/17 8:52
     * @Return: java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     **/
    List<InptBabyDTO> queryHisBabyInfo(Map<String,Object> map);

    /**
     * @Method: queryMrisBabyInfoPage
     * @Description: 查询婴儿信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Date: 2021/5/14 17:18
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisBabyInfoDO>
     **/
    List<MrisBabyInfoDO> queryMrisBabyInfoPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: insertMrisBabyBatch
     * @Description: 新增病案婴儿信息
     * @Param: [MrisBabyInfoDO]
     * @Author: liuliyun
     * @Date: 2021/5/14 17:20
     * @Return: 影响行数
     */
    int insertMrisBabyBatch(List<MrisBabyInfoDO> mrisBabyInfoDOList);

    /**
     * @Method: deleteMrisBabyInfoByVisitId
     * @Description: 删除病案婴儿信息
     * @Param: [map]
     * @Author: liuliyun
     * @Date: 2021/5/14 17:23
     * @Return: 影响行数
     **/
    int deleteMrisBabyInfoByVisitId(Map<String,Object> map);

    /**
     * @Method: getInptSettleAdvicePrice
     * @Description: 获取住院结算个人预交金
     * @Param: [map]
     * @Author: liuliyun
     * @Date: 2021/06/28 11:23
     * @Return: java.lang.Integer
     **/
    BigDecimal getInptSettleAdvicePrice(Map<String, Object> map);

    /**
     * @Method: getInptSettleAdvicePrice
     * @Description: 获取住院结算个人退款
     * @Param: [map]
     * @Author: liuliyun
     * @Date: 2021/06/28 11:23
     * @Return: java.lang.Integer
     **/
    BigDecimal getInptSettlebackPrice(Map<String, Object> map);

    /**
     * @Method: getInptCostTotalPrice
     * @Description: 获取住院总费用
     * @Param: [map]
     * @Author: liuliyun
     * @Date: 2021/07/01 11:23
     * @Return: java.lang.Integer
     **/
    BigDecimal getInptCostTotalPrice(Map<String, Object> map);

    int insertMrisInptDiagnoseBatch(List<MrisDiagnoseDO> mrisDiagnoseDOList);

    /**
     * @Method: queryMrisDiagnoseRowPage
     * @Description: 查询分行病案诊断信息
     * @Param: [inptVisitDTO]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/18/01 18:46
     * @Return: java.util.List<cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO>
     **/
    List<MrisDiagnoseDO> queryMrisDiagnoseRowPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method: deleteRowMrisDiagnoseByVisitId
     * @Description: 删除分行病案诊断信息
     * @Param: [map]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/18 19:06
     * @Return: 影响行数
     **/
    int deleteRowMrisDiagnoseByVisitId(Map<String,Object> map);

    /**
     * @Method: updateRowMrisDiagnose
     * @Description: 修改分行病案诊断信息
     * @Param: [mrisDiagnoseDOList]
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/18 19:07
     * @Return: Boolean
     **/
    int updateRowMrisDiagnose(List<MrisDiagnoseDO> mrisDiagnoseDOList);

    /**
     * @Description: 查询病案首页诊断信息，用于drg
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2022/2/11 11:34
     * @Return
     */
	List<Map<String, Object>> getMrisDiagnosePage(Map<String, Object> map);

	/**
	 * @Description: 查询病案首页手术信息，用于DRG
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2022/2/11 11:35
	 * @Return
	 */
    List<Map<String, Object>> getMrisOperInfoForDRG(Map<String, Object> map);


    Map<String, Object> getMrisPatientBaseInfo(Map<String, Object> map);

    /**
     * @Method queryMriCost
     * @Desrciption  查询住院中医病案首页的的---费用数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/29 16:04
     * @Return
     **/
    TcmMrisCostDO queryTcmMriCost(Map<String, Object> map);

    List<TcmMrisOperInfoDO> queryTcmMrisOperInfoPage(InptVisitDTO inptVisitDTO);

    List<TcmMrisDiagnoseDO> queryTcmMrisDiagnosePage(InptVisitDTO inptVisitDTO);

    /**
     * @Author gory
     * @Description 查询医保结算信息
     * @Date 2022/6/14 15:43
     * @Param [deptMap]
     **/
    PayInfoDTO queryInsureSettlePrice(Map<String, Object> map);

    /**
     * @Author gory
     * @Description 查询患者信息
     * @Date 2022/6/14 15:43
     * @Param [deptMap]
     **/
    String queryInptVist(Map<String, Object> map);

    /**
     * @Menthod: queryExportNum
     * @Desrciption: 查询导出数据的数量
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-08-03 09:35
     * @Return:  Map
     **/
    Map queryExportNum(Map<String,Object> map);

   List<InptVisitDTO> queryUnExportData(Map<String,Object> map);
}
package cn.hsa.module.interf.wxBasicInfo.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.register.entity.OutptRegisterDetailDO;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.ListUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxBasicInfo.dao
 * @Class_name: WxBasicInfoDAO
 * @Describe: 微信小程序-基本信息接口dao
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 16:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface WxBasicInfoDAO {

    /**
     * @Menthod: getDeptInfo
     * @Desrciption: 全院科室分布情况介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> getDeptInfo(Map<String, Object> map);

    /**
     * @Menthod: doctorInformationInquiry
     * @Desrciption: 医生列表或医生信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 {doctorId:医生id, deptCode:医生所属科室}
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 09:33
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> getDoctorListByIdOrDeptCode(Map<String, Object> map);

    /**
     * @Menthod: getNameByCodeAndValue
     * @Desrciption: 基础业务代码查询
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 type-查询的类型、code-值域代码、value-值域值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 15:50
     * @Return: List<Map < String, Object>>
     **/
    List<Map<String, Object>> getSysValue(Map<String, Object> map);

    /**
     * @Menthod: queryProfileByCertNo
     * @Desrciption: 个人信息查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 certNo 身份证号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 15:04
     * @Return: OutptProfileFileDTO
     **/
    OutptProfileFileDTO queryProfileByCertNo(Map<String, Object> map);

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码, 为空时查询所有科室
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: List<BaseDeptDTO>
     **/
    List<BaseDeptDTO> queryDeptByCode(Map<String, Object> map);

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 根据科室ids查询对应的挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 ids-科室ids
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: List<BaseDeptDTO>
     **/
    List<OutptClassifyDTO> queryClassify(Map<String, Object> map);

    /**
     * @Menthod: queryDoctorShiftInformation
     * @Desrciption: 查询科室医生及其班次信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id， hospCode-医院编码；startDate-队列开始日期；endDate-队列结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptDoctorQueueDto>
     **/
//    List<OutptClassesDoctorDTO> queryDoctorAndClassesByDeptId(Map<String, Object> map);
    List<OutptDoctorQueueDto> queryDoctorAndClassesByDeptId(Map<String, Object> map);

    /**
     * @Menthod: queryDoctorNumberSourceInformation
     * @Desrciption: 查询医生号源信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id， hospCode-医院编码，queueDateStart-队列开始时间，queueDateEnd-队列结束时间，doctorId-医生id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptDoctorQueueDto>
     **/
    List<OutptDoctorQueueDto> queryDoctorNumberSourceInformation(Map<String, Object> map);

    /**
     * @Menthod: queryClassifyCost
     * @Desrciption: 查询挂号类别费用
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 cyId-挂号类别id， hospCode-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptClassifyCostDTO>
     **/
    List<OutptClassifyCostDTO> queryClassifyCost(Map<String, Object> map);

    /**
     * @Menthod: queryBookingRecords
     * @Desrciption: 查询所有的预约挂号记录列表
     * @Param: 1.startDate-挂号开始日期(非必填) 默认查询一个月时间内
     * 2.endDate-挂号结束日期(非必填)
     * 3.deptId-挂号科室ID(非必填)
     * 4.keyword-关键词(非必填) 身份证、挂号单号、姓名
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 09:49
     * @Return: List<OutptRegisterDTO>
     **/
    List<OutptRegisterDTO> queryOutptRegister(Map<String, Object> map);

    /**
     * @Menthod: queryVisitList
     * @Desrciption: 查询存在待缴费的处方的就诊记录列表
     * @Param: hosCode-医院编码, data-入参
     * 1.startDate-就诊开始日期：string/yyyy-MM-dd(非必填)
     * 2.endDate-就诊结束日期：string/yyyy-MM-dd(非必填)
     * 3.keyword-关键词(非必填) 就诊号/姓名/证件号/挂号单号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:02
     * @Return: List<OutptVisitDTO>
     **/
    List<OutptVisitDTO> queryVisitList(Map<String, Object> map);

    /**
     * @Menthod: queryPrescribeList
     * @Desrciption: 根据visitId查询待缴费的处方信息
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 visitId-就诊id(必填)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:23
     * @Return: List<OutptPrescribeDTO>
     **/
    List<OutptPrescribeDTO> queryPrescribeList(Map<String, Object> map);

    /**
     * @Menthod: queryPrescriptionDetails
     * @Desrciption: 根据传入的处方号查询处方明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 opId-处方id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-24 20:49
     * @Return: List<OutptPrescribeDetailsDTO>
     **/
    List<OutptPrescribeDetailsDTO> queryPrescriptionDetails(Map<String, Object> map);

    // 根据医生队列id(dqId)、预约时段开始时间、结束时间查询出未锁号、未预约的号源
    List<OutptDoctorRegisterDto> queryDoctorRegisterList(Map<String, Object> map);

    // 根据dqId、profileId、startTime、endTime判断人员再选择的时段内是否已经锁定过号源
    OutptDoctorRegisterDto queryDoctorRegister(OutptDoctorRegisterDto dto);

    // 锁定/解锁号源
    Integer updateIsLock(OutptDoctorRegisterDto outptDoctorRegisterDto);

    // 查询单个号源id记录，判断是否未当前人员的锁的号
    OutptDoctorRegisterDto getDoctorRegisterById(String sourceId, String hospCode);

    //根据档案id、就诊查询起止时间查询出患者的就诊记录
    List<OutptVisitDTO> getVistiListByProfileId(Map visitMap);

    /**
     * @Menthod: paymentRecordQuery
     * @Desrciption: 缴费记录查询，根据档案id查询出就诊人一个月内的就诊记录、及处方
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（profileId-档案id；startTime-开始时间；endTime-结束时间；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-28 20:19
     * @Return: String json串
     **/
    //根据visitIds查询已缴费的处方列表
    List<OutptSettleDTO> queryPaidPrescribeList(Map perscribeMap);

    /**
     * @Menthod: reportListQuery
     * @Desrciption: 报告列表查询；根据档案id查询出的就诊ids，查询对应的医技记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；visitIds；
     * 非必填：typeCode-查询类型(4lis/5pacs)；startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 15:28
     * @Return:
     **/
    List<MedicalApplyDTO> queryMedicApplyList(Map<String, Object> data);

    /**
     * @Menthod: queryMedicApplyResult
     * @Desrciption: 根据医技申请id、申请单号查询医技结果
     * @Param: 1.hsopCode：医院编码
     * 2.data：入参（applyId-医技申请id；applyNo-申请号；typeCode）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 19:17
     * @Return:
     **/
    List<MedicalResultDTO> queryMedicApplyResult(Map<String, Object> data);

    List<TreeMenuNode> queryDeptTree(Map<String, Object> map);

    /**
     * @Menthod: advancePaymentForHospitalization
     * @Desrciption: 预缴金充值
     * @Param: 1.hospCoe：医院编码
     * 2.data：入参（visitId-就诊id；payCode-支付方式代码；orderNo-支付订单号；price-预交金额；crteId-创建人id；crteName-创建人姓名）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 21:12
     * @Return:
     **/
    void saveAdvancePayment(InptAdvancePayDTO advancePayDTO);

    /**
     * @Menthod: prepaymentRechargeRecordQuery
     * @Desrciption: 预缴金充值记录查询
     * @Param: 1.hospCoe：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；visitIds-就诊ids
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 21:12
     * @Return:
     **/
    List<InptAdvancePayDTO> queryAdvancePayment(Map<String, Object> data);

    /**
     * @Menthod: inpatientInformationInquiry
     * @Desrciption: 住院病人信息查询：查询病人住院信息情况，为已入院记录，目前只取最近的住院记录给到移动端
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；statusCode-病人状态；
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-01 10:35
     * @Return:
     **/
    List<InptVisitDTO> queryInptVisitRecord(Map<String, Object> data);

    /**
     * @Menthod: oneDayListRecordQuery
     * @Desrciption: 病人住院日清单记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（inNo-住院号；costStartTime-费用开始时间；costStopTime-费用结束时间）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 14:19
     * @Return:
     **/
    List<InptCostDTO> queryOneDayCostListRecord(Map<String, Object> data);

    //查询日结费用清单明细
    List<InptCostDTO> queryOneDayCostListRecordDetail(Map<String, Object> data);

    // 根据deptId、certNo、挂号日期查询是否已挂号
    List<OutptRegisterDTO> queryIsRepeatRegister(Map outptRegisterMap);

    // 插入挂号表数据(outpt_register)
    void insertOuptRegister(OutptRegisterDTO outptRegisterDTO);

    // 插入挂号费用表数据(outpt_register_detail)
    void insertOuptRegisterDetail(@Param("list") List<OutptRegisterDetailDto> list);

    // 插入就诊表(outpt_visit)数据处理
    void insertOutptVisit(OutptVisitDTO outptVisitDTO);

    // 根据医生队列id获取所属诊室信息
    OutptDoctorQueueDto queryDoctorQueueById(OutptDoctorQueueDto doctorQueueDto);

    List<OutptClassesQueueDto> queryClassesQueueParam(Map mapParam);

    OutptClassifyClassesDTO getClassifyClassesById(OutptClassifyClassesDTO classifyClassesDTO);

    // 分诊队列表数据(outpt_triage_visit)
    void insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);

    //挂号结算表数据(outpt_register_settle)
    void insertOutptRegisterSettle(OutptRegisterSettleDto outptRegisterSettleDto);

    // 结算支付表数据(outpt_register_pay)
    void insertOutptRegisterPay(@Param("payList") List<OutptRegisterPayDto> payList);

    // 根据住院号查询费用清单
    List<InptCostDTO> queryInptCostRecord(Map<String, Object> data);

    // 门诊划价收费结算，插入费用数据到(outpt_cost)表中
    void insertOuptCost(@Param("costList") List<OutptCostDTO> outptCostDTOList);

    // 更新号源表数据(outpt_doctor_register)
    int updateOuptDoctorRegister(OutptDoctorRegisterDto outptDoctorRegisterDto);

    // 根据挂号id、医院编码查询挂号记录
    OutptRegisterDTO getOutptRegisterById(Map<String, Object> data);

    // 根据visitId查询就诊表
    OutptVisitDTO getOutptVisitById(OutptRegisterDTO outptRegisterDTO);

    // 挂号信息表作废，更新作废状态
    int updateOuptRegister(OutptRegisterDTO registerDTO);

    // 根据挂号id、就诊id查询挂号明细记录
    List<OutptRegisterDetailDto> queryOutptRegisterDetailByRegisterId(OutptRegisterDTO outptRegisterDTO);

    // 挂号明细表原费用被冲红
    int updateOutptRegisterDetailByRegisterId(@Param("updateDetailList") List<OutptRegisterDetailDto> list);

    // 根据挂号id查询挂号结算表记录
    OutptRegisterSettleDto queryOutptRegisterSettleByRegisterId(OutptRegisterDTO outptRegisterDTO);

    // 挂号结算表数据冲红
    int updateOutptRegisterSettleByRegisterId(OutptRegisterSettleDto outptRegisterSettleDto);

    // 根据挂号id查询挂号支付方式记录
    List<OutptRegisterPayDto> queryOutptRegisterPayByRegisterId(OutptRegisterDTO outptRegisterDTO);

    //更新病人就诊信息标志为作废
    int deleteOutptVisitByVisitId(OutptRegisterDTO outptRegisterDTO);

    // 挂号退号解锁号源信息
    int updateOutptDoctorRegister(OutptRegisterDTO outptRegisterDTO);

    // 根据微信订单号查询预交金记录是否存在
    InptAdvancePayDTO queryInptAdvancePayByOrderNo(String hospCode, String orderNo);

    // 根据id查询就诊记录
    InptVisitDTO getInptVisitById(InptVisitDTO inptVisitDTO);

    // 根据visitId查询出所有状态为0正常的预交金记录
    List<InptAdvancePayDTO> queryInptAdvancePayByVisitId(InptAdvancePayDTO inptAdvancePayDTO);

    // 更新住院就诊表累计预交金和累计余额
    int updateInptVisitTotalAdvanceAndBalance(InptVisitDTO inptVisitDTOById);

    // 查询优惠类别，默认普通病人
    List<BasePreferentialTypeDTO> queryPreferentialTypeList(String hospCode);
    /**
     * 查询所有科室下所有七天内有排班的医生
     * pengbo
     */
    List<Map<String,Object>> querySevenQueueDoctor(Map<String, Object> map);

    /**
     * 根据profileId和时间段查询门诊和住院的就诊id集合
     * @param data
     * @return
     */
    List<String> queryVisitIdsByProfileId(Map<String, Object> data);

    /**
     * 根据visitIds 集合查询门诊和住院的检验检查报告列表
     * @param data
     * @return
     */
    List<MedicalApplyDTO> queryReportListByVisitIds(Map<String, Object> data);
}

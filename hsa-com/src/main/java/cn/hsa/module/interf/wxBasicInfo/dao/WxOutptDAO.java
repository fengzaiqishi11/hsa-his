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
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.*;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
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
public interface WxOutptDAO {

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

    /**
     * 根据医生队列id(dqId)、预约时段开始时间、结束时间查询出未锁号、未预约的号源
     */
    List<OutptDoctorRegisterDto> queryDoctorRegisterList(Map<String, Object> map);

    /**
     * 根据dqId、profileId、startTime、endTime判断人员再选择的时段内是否已经锁定过号源
     */
    OutptDoctorRegisterDto queryDoctorRegister(OutptDoctorRegisterDto dto);

    /**
     * 锁定/解锁号源
     */
    Integer updateIsLock(OutptDoctorRegisterDto outptDoctorRegisterDto);

    /**
     * 查询单个号源id记录，判断是否未当前人员的锁的号
     */
    OutptDoctorRegisterDto getDoctorRegisterById(String sourceId, String hospCode);

    /**
     * 根据档案id、就诊查询起止时间查询出患者的就诊记录
     */
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

    /**
     * 根据deptId、certNo、挂号日期查询是否已挂号
     */
    List<OutptRegisterDTO> queryIsRepeatRegister(Map outptRegisterMap);

    /**
     * 插入挂号表数据(outpt_register)
     */
    void insertOuptRegister(OutptRegisterDTO outptRegisterDTO);

    /**
     * 插入挂号费用表数据(outpt_register_detail)
     */
    void insertOuptRegisterDetail(@Param("list") List<OutptRegisterDetailDto> list);

    /**
     * 插入就诊表(outpt_visit)数据处理
     */
    void insertOutptVisit(OutptVisitDTO outptVisitDTO);

    /**
     * 根据医生队列id获取所属诊室信息
     */
    OutptDoctorQueueDto queryDoctorQueueById(OutptDoctorQueueDto doctorQueueDto);

    /**
     * 获取分诊室信息
     *
     * @param mapParam
     * @return
     */
    List<OutptClassesQueueDto> queryClassesQueueParam(Map mapParam);

    /**
     * 获取班次是否排队叫号信息
     *
     * @param classifyClassesDTO
     * @return
     */
    OutptClassifyClassesDTO getClassifyClassesById(OutptClassifyClassesDTO classifyClassesDTO);

    /**
     * 分诊队列表数据(outpt_triage_visit)
     */
    void insertOutptTriageVisit(OutptTriageVisitDTO outptTriageVisitDTO);

    /**
     * 挂号结算表数据(outpt_register_settle)
     */
    void insertOutptRegisterSettle(OutptRegisterSettleDto outptRegisterSettleDto);

    /**
     * 结算支付表数据(outpt_register_pay)
     */
    void insertOutptRegisterPay(@Param("payList") List<OutptRegisterPayDto> payList);

    /**
     * 门诊划价收费结算，插入费用数据到(outpt_cost)表中
     */
    void insertOuptCost(@Param("costList") List<OutptCostDTO> outptCostDTOList);

    /**
     * 更新号源表数据(outpt_doctor_register)
     */
    int updateOuptDoctorRegister(OutptDoctorRegisterDto outptDoctorRegisterDto);

    /**
     * 根据挂号id、医院编码查询挂号记录
     */
    OutptRegisterDTO getOutptRegisterById(Map<String, Object> data);

    /**
     * 根据visitId查询就诊表
     */
    OutptVisitDTO getOutptVisitById(OutptRegisterDTO outptRegisterDTO);

    /**
     * 挂号信息表作废，更新作废状态
     */
    int updateOuptRegister(OutptRegisterDTO registerDTO);

    /**
     * 根据挂号id、就诊id查询挂号明细记录
     */
    List<OutptRegisterDetailDto> queryOutptRegisterDetailByRegisterId(OutptRegisterDTO outptRegisterDTO);

    /**
     * 挂号明细表原费用被冲红
     */
    int updateOutptRegisterDetailByRegisterId(@Param("updateDetailList") List<OutptRegisterDetailDto> list);

    /**
     * 根据挂号id查询挂号结算表记录
     */
    OutptRegisterSettleDto queryOutptRegisterSettleByRegisterId(OutptRegisterDTO outptRegisterDTO);

    /**
     * 挂号结算表数据冲红
     */
    int updateOutptRegisterSettleByRegisterId(OutptRegisterSettleDto outptRegisterSettleDto);

    /**
     * 根据挂号id查询挂号支付方式记录
     */
    List<OutptRegisterPayDto> queryOutptRegisterPayByRegisterId(OutptRegisterDTO outptRegisterDTO);

    /**
     * 更新病人就诊信息标志为作废
     */
    int deleteOutptVisitByVisitId(OutptRegisterDTO outptRegisterDTO);

    /**
     * 挂号退号解锁号源信息
     */
    int updateOutptDoctorRegister(OutptRegisterDTO outptRegisterDTO);


    /**
     * 查询所有科室下所有七天内有排班的医生
     * pengbo
     */
    List<Map<String, Object>> querySevenQueueDoctor(Map<String, Object> map);
}

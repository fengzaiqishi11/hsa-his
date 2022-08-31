package cn.hsa.module.interf.wxBasicInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxBasicInfo.service
 * @Class_name: WxBasicInfoService
 * @Describe: 微信小程序-基本信息接口service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 15:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-interf")
public interface WxBasicInfoService  {

    /**
     * @Menthod: hospitalInformationIntroduction
     * @Desrciption: 医院信息介绍
     * @Param:
     * 1. hospCode: 医院编码
     * 2. data: 入参 code-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/hospitalInformationIntroduction")
    WrapperResponse<String> hospitalInformationIntroduction(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: departmentDistribution
     * @Desrciption: 全院科室分布情况介绍
     * @Param:
     * 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/departmentDistribution")
    WrapperResponse<String> departmentDistribution(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: doctorInformationInquiry
     * @Desrciption: 医生列表或医生信息
     * @Param:
     * 1. hospCode: 医院编码
     * 2.data: 入参 doctorId-医生id、deptCode-医生所属科室
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 09:33
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/doctorInformationInquiry")
    WrapperResponse<String> doctorInformationInquiry(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: basicBusinessCodeQuery
     * @Desrciption: 基础业务代码查询
     * @Param:
     * 1. hospCode: 医院编码
     * 2.data: 入参 type-查询的类型、code-值域代码、name-值域名称、value-值域值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 15:50
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/basicBusinessCodeQuery")
    WrapperResponse<String> basicBusinessCodeQuery(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: personalInformationQuery
     * @Desrciption: 个人信息查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 certNo 身份证号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 15:04
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/personalInformationQuery")
    WrapperResponse<String> personalInformationQuery(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: registrationOrModification
     * @Desrciption: 档案登记或修改
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 个人信息map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 17:29
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/registrationOrModification")
    WrapperResponse<String> registrationOrModification(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室及其对应挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码，typeCode-科室性质
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryRegistrationDepartment")
    WrapperResponse<String> queryRegistrationDepartment(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryDoctorShiftInformation
     * @Desrciption: 查询科室医生及其班次信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id， hospCode-医院编码；startDate-队列开始日期；endDate-队列结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryDoctorShiftInformation")
    WrapperResponse<String> queryDoctorShiftInformation(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryDoctorNumberSourceInformation
     * @Desrciption: 查询医生号源信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id，queueDate-队列时间，doctorId-医生id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryDoctorNumberSourceInformation")
    WrapperResponse<String> queryDoctorNumberSourceInformation(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: inquiryOfRegistrationFee
     * @Desrciption: 查询挂号类别费用
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 cyId-挂号类别id， hospCode-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/inquiryOfRegistrationFee")
    WrapperResponse<String> inquiryOfRegistrationFee(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: lockSignalSource
     * @Desrciption: 锁定号源
     * @Param:
     * 1.hospCode：医院编码
     * 2.data：入参（
     *      必填：dqId-医生队列id；queueDate-号源日期；startTime-分时开始时间；endTime-分时结束时间；profileId-个人档案id
     *    非必填：deptId-科室Id；doctorId-医生id；queueDate-号源日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/lockSignalSource")
    WrapperResponse<String> lockSignalSource(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: registeredPayment
     * @Desrciption: 挂号支付
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/registeredPayment")
    WrapperResponse<String> registeredPayment(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: unlockSignalSource
     * @Desrciption: 解锁号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（sourceId-号源Id；profileId-档案id；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/unlockSignalSource")
    WrapperResponse<String> unlockSignalSource(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: withdrawalNumber
     * @Desrciption: 退号
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（id-挂号id）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/withdrawalNumber")
    WrapperResponse<String> withdrawalNumber(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryBookingRecords
     * @Desrciption: 查询所有的预约挂号记录列表
     * @Param:
     * 1.startDate-挂号开始日期(非必填)
     * 2.endDate-挂号结束日期(非必填)
     * 3.deptId-挂号科室ID(非必填)
     * 4.keyword-关键词(必填) 身份证、挂号单号、姓名
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 09:49
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryBookingRecords")
    WrapperResponse<String> queryBookingRecords(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryToBePaidList
     * @Desrciption: 查询存在待缴费的处方的就诊记录列表
     * @Param: hosCode-医院编码, data-入参
     * 1.startDate-就诊开始日期：string/yyyy-MM-dd(非必填)
     * 2.endDate-就诊结束日期：string/yyyy-MM-dd(非必填)
     * 3.keyword-关键词(必填) 就诊号/姓名/证件号/挂号单号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:02
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryToBePaidList")
    WrapperResponse<String> queryToBePaidList(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryPaymentPrescriptionInformation
     * @Desrciption: 根据visitId查询待缴费的处方信息
     * @Param:
     * 1.hospCode：医院编码
     * 2.data：入参 visitId-就诊id(必填)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:23
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryPaymentPrescriptionInformation")
    WrapperResponse<String> queryPaymentPrescriptionInformation(@RequestBody Map<String, Object> map);

    /**
     * @Menthod: queryPrescriptionDetails
     * @Desrciption: 根据传入的处方号查询处方明细
     * @Param:
     * 1.hospCode：医院编码
     * 2.data：入参 opId-处方id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-24 20:49
     * @Return: String json串
     **/
    @PostMapping("/service/interf/wxBasicInfo/queryPrescriptionDetails")
    WrapperResponse<String> queryPrescriptionDetails(@RequestBody Map<String, Object> map);

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
    @PostMapping("/service/interf/wxBasicInfo/paymentRecordQuery")
    WrapperResponse<String> paymentRecordQuery(Map<String, Object> map);

    /**
     * @Menthod: paymentRecordDetails
     * @Desrciption: 缴费记录明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（opId-处方id）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 11:39
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/paymentRecordDetails")
    WrapperResponse<String> paymentRecordDetails(Map<String, Object> map);

    /**
     * @Menthod: reportListQuery
     * @Desrciption: 报告列表查询
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；
     * 非必填：typeCode-查询类型(4lis/5pacs)；startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 15:28
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/reportListQuery")
    WrapperResponse<String> reportListQuery(Map<String, Object> map);

    /**
     * @Menthod: LISMedicalTechnologyResultDocumentQuery
     * @Desrciption: LIS医技结果查询
     * @Param: 1.hsopCode：医院编码
     * 2.data：入参（applyId-医技申请id；applyNo-申请号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 19:17
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/LISMedicalTechnologyResultDocumentQuery")
    WrapperResponse<String> LISMedicalTechnologyResultDocumentQuery(Map<String, Object> map);

    /**
     * @Menthod: PACSMedicalTechnologyResultDocumentQuery
     * @Desrciption: PACS医技结果查询
     * @Param: 1.hsopCode：医院编码
     * 2.data：入参（applyId-医技申请id；applyNo-申请号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 19:17
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/PACSMedicalTechnologyResultDocumentQuery")
    WrapperResponse<String> PACSMedicalTechnologyResultDocumentQuery(Map<String, Object> map);

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
    @PostMapping("/service/interf/wxBasicInfo/advancePaymentForHospitalization")
    WrapperResponse<String> advancePaymentForHospitalization(Map<String, Object> map);

    /**
     * @Menthod: prepaymentRechargeRecordQuery
     * @Desrciption: 预缴金充值记录查询
     * @Param: 1.hospCoe：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 21:12
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/prepaymentRechargeRecordQuery")
    WrapperResponse<String> prepaymentRechargeRecordQuery(Map<String, Object> map);

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
    @PostMapping("/service/interf/wxBasicInfo/inpatientInformationInquiry")
    WrapperResponse<String> inpatientInformationInquiry(Map<String, Object> map);

    /**
     * @Menthod: inpatientRecordQuery
     * @Desrciption: 查询病人住院记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：profileId-档案id；statusCode-病人状态；
     * 非必填：startDate-开始日期；endDate-结束日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-01 16:52
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/inpatientRecordQuery")
    WrapperResponse<String> inpatientRecordQuery(Map<String, Object> map);

    /**
     * @Menthod: detailedQueryOfInpatientRecords
     * @Desrciption: 查询病人住院信息情况，为已入院记录
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（visitId-就诊id；inNo-住院号）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 11:15
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/detailedQueryOfInpatientRecords")
    WrapperResponse<String> detailedQueryOfInpatientRecords(Map<String, Object> map);

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
    @PostMapping("/service/interf/wxBasicInfo/oneDayListRecordQuery")
    WrapperResponse<String> oneDayListRecordQuery(Map<String, Object> map);

    /**
     * @Menthod: dailyListDetailsQuery
     * @Desrciption: 病人住院日清单明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（inNo-住院号；costStartTime-费用开始时间；costStopTime-费用结束时间）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-05 17:19
     * @Return:
     **/
    @PostMapping("/service/interf/wxBasicInfo/dailyListDetailsQuery")
    WrapperResponse<String> dailyListDetailsQuery(Map<String, Object> map);

    /**
     * 查询所有科室下所有七天内有排班的医生
     * pengbo
     */
    @PostMapping("/service/interf/wxBasicInfo/querySevenQueueDoctor")
    WrapperResponse<String> querySevenQueueDoctor(Map<String, Object> map);

    /**
     *
     * @param map
     */
    @PostMapping("/service/interf/wxBasicInfo/querySevenQueueDoctor")
    void removeLockByProfileId(Map<String, Object> map);
    /**
     * 核酸检验申请
     */
    @PostMapping("/service/interf/wxBasicInfo/hsjcApply")
    WrapperResponse<String> saveHsjcApply(Map<String, Object> map);
    @PostMapping("/service/interf/wxBasicInfo/queryBaseDisease")
    WrapperResponse<String> queryBaseDisease(Map<String, Object> map);
}

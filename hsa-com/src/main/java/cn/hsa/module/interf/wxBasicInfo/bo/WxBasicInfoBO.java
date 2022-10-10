package cn.hsa.module.interf.wxBasicInfo.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptDoctorRegisterDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.wxBasicInfo.bo
 * @Class_name: WxBasicInfoBO
 * @Describe: 微信小程序-基本信息接口bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 15:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface WxBasicInfoBO {

    /**
     * @Menthod: getHospitalInfo
     * @Desrciption: 医院信息介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: Map<String, Object>
     **/
    WrapperResponse<String> getHospitalInfo(Map<String, Object> map);

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
    WrapperResponse<String> getDeptInfo(Map<String, Object> map);

    /**
     * @Menthod: doctorInformationInquiry
     * @Desrciption: 医生列表或医生信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 doctorId-医生id、deptCode-医生所属科室
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 09:33
     * @Return: List<Map < String, Object>>
     **/
    WrapperResponse<String> getDoctorListByIdOrDeptCode(Map<String, Object> map);

    /**
     * @Menthod: basicBusinessCodeQuery
     * @Desrciption: 基础业务代码查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 type-查询的类型、code-值域代码、name-值域名称、value-值域值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 15:50
     * @Return: List<Map < String, Object>>
     **/
    WrapperResponse<String> getSysValue(Map<String, Object> map);

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
    WrapperResponse<String> queryProfileByCertNo(Map<String, Object> map);

    /**
     * @Menthod: registrationOrModification
     * @Desrciption: 档案登记或修改
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 个人信息map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 17:29
     * @Return: OutptProfileFileExtendDTO
     **/
    WrapperResponse<String> saveProfileFile(Map<String, Object> map);

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室及其对应挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码，typeCode-科室性质
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: List<BaseDeptDTO>
     **/
    WrapperResponse<String> queryDeptAndClassify(Map<String, Object> map);


    /**
     * @Menthod: queryDoctorShiftInformation
     * @Desrciption: 查询科室医生及其班次信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id， hospCode-医院编码；startDate-队列开始日期；endDate-队列结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: WrapperResponse<String>
     **/
    WrapperResponse<String> queryDoctorAndClassesByDeptId(Map<String, Object> map);

    /**
     * @Menthod: queryDoctorNumberSourceInformation
     * @Desrciption: 查询医生号源信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 deptId-科室id，queueDate-队列时间，doctorId-医生id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 14:47
     * @Return: List<OutptDoctorQueueDto>
     **/
    WrapperResponse<String> queryDoctorNumberSourceInformation(Map<String, Object> map);

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
    WrapperResponse<String> queryClassifyCost(Map<String, Object> map);

    /**
     * @Menthod: queryBookingRecords
     * @Desrciption: 查询所有的预约挂号记录列表
     * @Param: 1.startDate-挂号开始日期(非必填)
     * 2.endDate-挂号结束日期(非必填)
     * 3.deptId-挂号科室ID(非必填)
     * 4.keyword-关键词(必填) 身份证、挂号单号、姓名
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 09:49
     * @Return: WrapperResponse<String>
     **/
    WrapperResponse<String> queryOutptRegister(Map<String, Object> map);

    /**
     * @Menthod: addInLock
     * @Desrciption: 锁定号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：dqId-医生队列id；queueDate-号源日期；startTime-分时开始时间；endTime-分时结束时间；profileId-个人档案id
     * 非必填：deptId-科室Id；doctorId-医生id；queueDate-号源日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: OutptDoctorRegisterDto
     **/
    WrapperResponse<String> addInLock(Map<String, Object> map);

    /**
     * @Menthod: saveRegisteredPayment
     * @Desrciption: 挂号支付
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: OutptRegisterDTO
     **/
    WrapperResponse<String> saveRegisteredPayment(Map<String, Object> map);

    /**
     * @Menthod: removeLock
     * @Desrciption: 解锁号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（sourceId-号源Id；profileId-档案id；）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: WrapperResponse<String>
     **/
    WrapperResponse<String> removeLock(Map<String, Object> map);

    /**
     * @Menthod: deleteRegister
     * @Desrciption: 退号
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（id-挂号id）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: boolean
     **/
    WrapperResponse<String> deleteRegister(Map<String, Object> map);

    /**
     * @Menthod: queryVisitList
     * @Desrciption: 查询存在待缴费的处方的就诊记录列表
     * @Param: hosCode-医院编码, data-入参
     * 1.startDate-就诊开始日期：string/yyyy-MM-dd(非必填)
     * 2.endDate-就诊结束日期：string/yyyy-MM-dd(非必填)
     * 3.keyword-关键词(必填) 就诊号/姓名/证件号/挂号单号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:02
     * @Return: List<OutptVisitDTO>
     **/
    WrapperResponse<String> queryVisitList(Map<String, Object> map);

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
    WrapperResponse<String> queryPrescribeList(Map<String, Object> map);

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
    WrapperResponse<String> queryPrescriptionDetails(Map<String, Object> map);

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
    WrapperResponse<String> queryPaymentRecord(Map<String, Object> map);

    /**
     * @Menthod: queryPaymentRecordDetails
     * @Desrciption: 缴费记录明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（opId-处方id）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-29 11:39
     * @Return:
     **/
    WrapperResponse<String> queryPaymentRecordDetails(Map<String, Object> map);

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
    WrapperResponse<String> queryReportList(Map<String, Object> map);

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
    WrapperResponse<String> queryLISMedicResult(Map<String, Object> map);

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
    WrapperResponse<String> queryPACSMedicResult(Map<String, Object> map);

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
    WrapperResponse<String> saveAdvancePayment(Map<String, Object> map);

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
    WrapperResponse<String> queryAdvancePayment(Map<String, Object> map);

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
    WrapperResponse<String> queryInptVisitRecord(Map<String, Object> map);

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
    WrapperResponse<String> queryInpatientRecord(Map<String, Object> map);

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
    WrapperResponse<String> queryInpatientRecordDetail(Map<String, Object> map);

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
    WrapperResponse<String> queryOneDayCostListRecord(Map<String, Object> map);

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
    WrapperResponse<String> queryDailyCostListDetails(Map<String, Object> map);
    /**
     * 查询所有科室下所有七天内有排班的医生
     * pengbo
     */
    WrapperResponse<String> querySevenQueueDoctor(Map<String, Object> map);

    /**
     * 查询所有疾病信息
     * @param map
     * @return
     */
    WrapperResponse<String> queryBaseDisease(Map<String, Object> map);

    void removeLockByProfileId(Map<String, Object> map);

    WrapperResponse<String> saveHsjcApply(Map<String, Object> map);

    /**
     * @Menthod: queryPrescribeListForQRcode
     * @Desrciption: 根据opId查询待缴费的处方信息
     * @Param: 1.hospCode：医院编码 2.data：入参 visitId-就诊id(必填) opId 处方id(必填)
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-10-10 15:21
     * @Return: WrapperResponse<String>
     **/
    WrapperResponse<String> queryPrescribeListForQRcode (Map<String, Object> map);

}

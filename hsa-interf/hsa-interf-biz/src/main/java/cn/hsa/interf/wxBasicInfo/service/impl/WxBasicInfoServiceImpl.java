package cn.hsa.interf.wxBasicInfo.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.interf.wxBasicInfo.bo.WxBasicInfoBO;
import cn.hsa.module.interf.wxBasicInfo.service.WxBasicInfoService;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyCostDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dto.OutptDoctorRegisterDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.AsymmetricEncryption;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.wxBasicInfo.service.impl
 * @Class_name: WxBasicInfoServiceImpl
 * @Describe: 微信小程序-基本信息接口service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 15:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Service("/wxBasicInfoService_provider")
@HsafRestPath("/service/interf/wxBasicInfo")
public class WxBasicInfoServiceImpl extends HsafService implements WxBasicInfoService {

    @Resource
    private WxBasicInfoBO wxBasicInfoBO;


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
    @Override
    public WrapperResponse<String> hospitalInformationIntroduction(Map<String, Object> map) {
        return wxBasicInfoBO.getHospitalInfo(map);
    }

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
    @Override
    public WrapperResponse<String> departmentDistribution(Map<String, Object> map) {
        return wxBasicInfoBO.getDeptInfo(map);
    }

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
    @Override
    public WrapperResponse<String> doctorInformationInquiry(Map<String, Object> map) {
        return wxBasicInfoBO.getDoctorListByIdOrDeptCode(map);
    }

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
    @Override
    public WrapperResponse<String> basicBusinessCodeQuery(Map<String, Object> map) {
        return wxBasicInfoBO.getSysValue(map);
    }

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
    @Override
    public WrapperResponse<String> personalInformationQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryProfileByCertNo(map);
    }

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
    @Override
    public WrapperResponse<String> registrationOrModification(Map<String, Object> map) {
        return wxBasicInfoBO.saveProfileFile(map);
    }

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室及挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码，typeCode-科室性质
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: String json串
     **/
    @Override
    public WrapperResponse<String> queryRegistrationDepartment(Map<String, Object> map) {
        return wxBasicInfoBO.queryDeptAndClassify(map);
    }

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
    @Override
    public WrapperResponse<String> queryDoctorShiftInformation(Map<String, Object> map) {
        return wxBasicInfoBO.queryDoctorAndClassesByDeptId(map);
    }

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
    @Override
    public WrapperResponse<String> queryDoctorNumberSourceInformation(Map<String, Object> map) {
        return wxBasicInfoBO.queryDoctorNumberSourceInformation(map);
    }

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
    @Override
    public WrapperResponse<String> inquiryOfRegistrationFee(Map<String, Object> map) {
        return wxBasicInfoBO.queryClassifyCost(map);
    }

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
    @Override
    public WrapperResponse<String> lockSignalSource(Map<String, Object> map) {
       return wxBasicInfoBO.addInLock(map);
    }

    /**
     * @Menthod: registeredPayment
     * @Desrciption: 挂号支付
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @Override
    public WrapperResponse<String> registeredPayment(Map<String, Object> map) {
        return wxBasicInfoBO.saveRegisteredPayment(map);
    }

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
    @Override
    public WrapperResponse<String> unlockSignalSource(Map<String, Object> map) {
        return wxBasicInfoBO.removeLock(map);
    }

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
    @Override
    public WrapperResponse<String> withdrawalNumber(Map<String, Object> map) {
        return  wxBasicInfoBO.deleteRegister(map);
    }

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
    @Override
    public WrapperResponse<String> queryBookingRecords(Map<String, Object> map) {
        return wxBasicInfoBO.queryOutptRegister(map);
    }

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
    @Override
    public WrapperResponse<String> queryToBePaidList(Map<String, Object> map) {
        // 查询待缴费的处方列表
        return wxBasicInfoBO.queryVisitList(map);
    }

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
    @Override
    public WrapperResponse<String> queryPaymentPrescriptionInformation(Map<String, Object> map) {
        // 查询待缴费的处方列表
        return wxBasicInfoBO.queryPrescribeList(map);
    }

    /**
     * @Menthod: queryPrescriptionDetails
     * @Desrciption: 根据传入的处方号查询处方明细
     * @Param:
     * 1.hospCode：医院编码
     * 2.data：入参 opId-处方id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-24 20:49
     * @Return: List<OutptPrescribeDetailsDTO>
     **/
    @Override
    public WrapperResponse<String> queryPrescriptionDetails(Map<String, Object> map) {
        return wxBasicInfoBO.queryPrescriptionDetails(map);
    }

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
    @Override
    public WrapperResponse<String> paymentRecordQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryPaymentRecord(map);
    }

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
    @Override
    public WrapperResponse<String> paymentRecordDetails(Map<String, Object> map) {
        return wxBasicInfoBO.queryPaymentRecordDetails(map);
    }

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
    @Override
    public WrapperResponse<String> reportListQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryReportList(map);
    }

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
    @Override
    public WrapperResponse<String> LISMedicalTechnologyResultDocumentQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryLISMedicResult(map);
    }

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
    @Override
    public WrapperResponse<String> PACSMedicalTechnologyResultDocumentQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryPACSMedicResult(map);
    }

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
    @Override
    public WrapperResponse<String> advancePaymentForHospitalization(Map<String, Object> map) {
        return wxBasicInfoBO.saveAdvancePayment(map);
    }

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
    @Override
    public WrapperResponse<String> prepaymentRechargeRecordQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryAdvancePayment(map);
}

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
    @Override
    public WrapperResponse<String> inpatientInformationInquiry(Map<String, Object> map) {
        return wxBasicInfoBO.queryInptVisitRecord(map);
    }

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
    @Override
    public WrapperResponse<String> inpatientRecordQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryInpatientRecord(map);
    }

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
    @Override
    public WrapperResponse<String> detailedQueryOfInpatientRecords(Map<String, Object> map) {
        return wxBasicInfoBO.queryInpatientRecordDetail(map);
    }

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
    @Override
    public WrapperResponse<String> oneDayListRecordQuery(Map<String, Object> map) {
        return wxBasicInfoBO.queryOneDayCostListRecord(map);
    }

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
    @Override
    public WrapperResponse<String> dailyListDetailsQuery(Map<String, Object> map) {
        return  wxBasicInfoBO.queryDailyCostListDetails(map);
    }

    /**
     * 查询所有科室下所有七天内有排班的医生
     *
     * @param map
     */
    @Override
    public WrapperResponse<String> querySevenQueueDoctor(Map<String, Object> map) {
        return  wxBasicInfoBO.querySevenQueueDoctor(map);
    }

    /**
     * @param map
     */
    @Override
    public void removeLockByProfileId(Map<String, Object> map) {
        wxBasicInfoBO.removeLockByProfileId(map);
    }

    /**
     * 核酸检验申请
     *
     * @param map
     */
    @Override
    public WrapperResponse<String> hsjcApply(Map<String, Object> map) {
        return  wxBasicInfoBO.hsjcApply(map);
    }

    @Override
    public WrapperResponse<String> queryBaseDisease(Map<String, Object> map) {
        return wxBasicInfoBO.queryBaseDisease(map);
    }

}

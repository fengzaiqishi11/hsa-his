package cn.hsa.interf.wxBasicInfo.controller;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.wxBasicInfo.service.WxBasicInfoService;
import cn.hsa.util.AsymmetricEncryption;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.wxBasicInfo.controller
 * @Class_name: WxBasicInfoController
 * @Describe: 微信小程序-基本信息接口controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-06-16 14:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/interf/wxBasicInfo")
public class WxBasicInfoController {

    /**
     * 微信小程序-基本信息服务
     */
    @Resource
    private WxBasicInfoService wxBasicInfoService_consumer;

    /**
     * @Menthod: hospitalInformationIntroduction
     * @Desrciption: 医院信息介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-医院编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: String json串
     **/
    @PostMapping("/hospitalInformationIntroduction")
    public WrapperResponse<String> hospitalInformationIntroduction(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) return WrapperResponse.error(500, "入参错误，请传入医院编码！", null);

        String data = null;
        log.debug("微信小程序【医院基本信息】入参解密前：" + MapUtils.get(paramMap, "data"));
        try {
            data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
            log.debug("微信小程序【医院基本信息】入参解密后：" + (Map<String, Object>) JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【医院基本信息】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", (Map<String, Object>) JSON.parse(data));
        }
        return wxBasicInfoService_consumer.hospitalInformationIntroduction(map);
    }

    /**
     * @Menthod: departmentDistribution
     * @Desrciption: 全院科室分布情况介绍
     * @Param: 1. hospCode: 医院编码
     * 2. data: 入参 code-科室编码
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-16 14:54
     * @Return: String json串
     **/
    @PostMapping("/departmentDistribution")
    public WrapperResponse<String> departmentDistribution(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) return WrapperResponse.error(500, "入参错误，请传入医院编码！", null);

        String data = null;
        log.debug("微信小程序【科室分布情况】入参解密前：" + (String) paramMap.get("data"));
        try {
            data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
            log.debug("微信小程序【科室分布情况】入参解密后：" + (Map<String, Object>) JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【科室分布情况】入参解密错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", (Map<String, Object>) JSON.parse(data));
        }
        return wxBasicInfoService_consumer.departmentDistribution(map);
    }

    /**
     * @Menthod: doctorInformationInquiry
     * @Desrciption: 医生列表或医生信息
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 doctorId-医生id、deptCode-医生所属科室
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 09:33
     * @Return: String json串
     **/
    @PostMapping("/doctorInformationInquiry")
    public WrapperResponse<String> doctorInformationInquiry(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        log.debug("微信小程序【医生信息查询】入参解密前：" + (String) paramMap.get("data"));
        try {
            data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
            log.debug("微信小程序【医生信息查询】入参解密后：" + (Map<String, Object>) JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【医生信息查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", (Map<String, Object>) JSON.parse(data));
        }
        return wxBasicInfoService_consumer.doctorInformationInquiry(map);
    }

    /**
     * @Menthod: basicBusinessCodeQuery
     * @Desrciption: 基础业务代码查询
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 type-查询的类型、code-值域代码、name-值域名称、value-值域值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-17 15:50
     * @Return: String json串
     **/
    @PostMapping("/basicBusinessCodeQuery")
    public WrapperResponse<String> basicBusinessCodeQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        log.debug("微信小程序【基础业务代码查询】入参解密前：" + (String) paramMap.get("data"));
        try {
            data = AsymmetricEncryption.pubdecrypt((String) paramMap.get("data"));
            log.debug("微信小程序【基础业务代码查询】入参解密后：" + (Map<String, Object>) JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【基础业务代码查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", (Map<String, Object>) JSON.parse(data));
        }
        return wxBasicInfoService_consumer.basicBusinessCodeQuery(map);
    }

    /**
     * @Menthod: personalInformationQuery
     * @Desrciption: 个人信息查询
     * @Param:1. hospCode: 医院编码
     * 2.data: 入参 certNo 身份证号
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-18 15:04
     * @Return: String json串
     **/
    @PostMapping("/personalInformationQuery")
    public WrapperResponse<String> personalInformationQuery(@RequestBody Map<String, Object> paramMap) {

        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【个人信息查询】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【个人信息查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【个人信息查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", (Map<String, Object>) JSON.parse(data));
        }
        return wxBasicInfoService_consumer.personalInformationQuery(map);
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
    @PostMapping("/registrationOrModification")
    public WrapperResponse<String> registrationOrModification(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【档案登记或修改】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【档案登记或修改】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【档案登记或修改】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.registrationOrModification(map);
    }

    /**
     * @Menthod: queryRegistrationDepartment
     * @Desrciption: 查询挂号科室及其对应挂号类别
     * @Param: 1. hospCode: 医院编码
     * 2.data: 入参 code-科室编码, typeCode-科室性质
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-21 09:31
     * @Return: String json串
     **/
    @PostMapping("/queryRegistrationDepartment")
    public WrapperResponse<String> queryRegistrationDepartment(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【挂号科室及挂号类别】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【挂号科室及挂号类别】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【挂号科室及挂号类别】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryRegistrationDepartment(map);
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
    @PostMapping("/queryDoctorShiftInformation")
    public WrapperResponse<String> queryDoctorShiftInformation(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【科室医生及班次信息】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【科室医生及班次信息】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【科室医生及班次信息】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryDoctorShiftInformation(map);
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
    @PostMapping("/queryDoctorNumberSourceInformation")
    public WrapperResponse<String> queryDoctorNumberSourceInformation(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【医生号源信息】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【医生号源信息】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【医生号源信息】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryDoctorNumberSourceInformation(map);
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
    @PostMapping("/inquiryOfRegistrationFee")
    public WrapperResponse<String> inquiryOfRegistrationFee(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【挂号类别费用】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【挂号类别费用】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【挂号类别费用】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.inquiryOfRegistrationFee(map);
    }

    /**
     * @Menthod: lockSignalSource
     * @Desrciption: 锁定号源
     * @Param: 1.hospCode：医院编码
     * 2.data：入参（
     * 必填：dqId-医生队列id；queueDate-号源日期；startTime-分时开始时间；endTime-分时结束时间；profileId-个人档案id
     * 非必填：deptId-科室Id；doctorId-医生id；queueDate-号源日期）
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-22 20:01
     * @Return: String json串
     **/
    @PostMapping("/lockSignalSource")
    public WrapperResponse<String> lockSignalSource(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【锁定号源】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【锁定号源】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【锁定号源】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.lockSignalSource(map);
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
    @PostMapping("/registeredPayment")
    public WrapperResponse<String> registeredPayment(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【挂号支付】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【挂号支付】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【挂号支付】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.registeredPayment(map);
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
    @PostMapping("/unlockSignalSource")
    public WrapperResponse<String> unlockSignalSource(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【解锁号源】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【解锁号源】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【解锁号源】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.unlockSignalSource(map);
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
    @PostMapping("/withdrawalNumber")
    public WrapperResponse<String> withdrawalNumber(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【挂号退号】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【挂号退号】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【挂号退号】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.withdrawalNumber(map);
    }

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
     * @Return: String json串
     **/
    @PostMapping("/queryBookingRecords")
    public WrapperResponse<String> queryBookingRecords(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【预约挂号查询】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【预约挂号查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【预约挂号查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryBookingRecords(map);
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
    @PostMapping("/queryToBePaidList")
    public WrapperResponse<String> queryToBePaidList(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【待缴费的就诊记录列表】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【待缴费的就诊记录列表】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【待缴费的就诊记录列表】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryToBePaidList(map);
    }

    /**
     * @Menthod: queryPaymentPrescriptionInformation
     * @Desrciption: 根据visitId查询待缴费的处方信息
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 visitId-就诊id(必填)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-23 17:23
     * @Return: String json串
     **/
    @PostMapping("/queryPaymentPrescriptionInformation")
    public WrapperResponse<String> queryPaymentPrescriptionInformation(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【待缴费的处方信息】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【待缴费的处方信息】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【待缴费的处方信息】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryPaymentPrescriptionInformation(map);
    }

    /**
     * @Menthod: queryPrescriptionDetails
     * @Desrciption: 根据传入的处方号查询处方明细
     * @Param: 1.hospCode：医院编码
     * 2.data：入参 opId-处方id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-06-24 20:49
     * @Return: String json串
     **/
    @PostMapping("/queryPrescriptionDetails")
    public WrapperResponse<String> queryPrescriptionDetails(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【查询处方明细列表】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【查询处方明细列表】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【查询处方明细列表】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryPrescriptionDetails(map);
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
    @PostMapping("/paymentRecordQuery")
    public WrapperResponse<String> paymentRecordQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【缴费记录查询】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【缴费记录查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【缴费记录查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.paymentRecordQuery(map);
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
    @PostMapping("/paymentRecordDetails")
    public WrapperResponse<String> paymentRecordDetails(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【缴费记录明细】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【缴费记录明细】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【缴费记录明细】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.paymentRecordDetails(map);
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
    @PostMapping("/reportListQuery")
    public WrapperResponse<String> reportListQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【报告列表查询】入参解密前：" + (String) paramMap.get("data"));
            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【报告列表查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【报告列表查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.reportListQuery(map);
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
    @PostMapping("/LISMedicalTechnologyResultDocumentQuery")
    public WrapperResponse<String> LISMedicalTechnologyResultDocumentQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【LIS医技结果查询】入参解密前：" + (String) paramMap.get("data"));
            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【LIS医技结果查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【LIS医技结果查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.LISMedicalTechnologyResultDocumentQuery(map);
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
    @PostMapping("/PACSMedicalTechnologyResultDocumentQuery")
    public WrapperResponse<String> PACSMedicalTechnologyResultDocumentQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【PACS医技结果查询】入参解密前：" + (String) paramMap.get("data"));
            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【PACS医技结果查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【PACS医技结果查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.PACSMedicalTechnologyResultDocumentQuery(map);
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
    @PostMapping("/advancePaymentForHospitalization")
    public WrapperResponse<String> advancePaymentForHospitalization(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【预缴金充值】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【预缴金充值】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【预缴金充值】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.advancePaymentForHospitalization(map);
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
    @PostMapping("/prepaymentRechargeRecordQuery")
    public WrapperResponse<String> prepaymentRechargeRecordQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【预缴金记录查询】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【预缴金记录查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【预缴金记录查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.prepaymentRechargeRecordQuery(map);
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
    @PostMapping("/inpatientInformationInquiry")
    public WrapperResponse<String> inpatientInformationInquiry(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【住院病人信息查询】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【住院病人信息查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【住院病人信息查询】入参错误，请联系管理员！" + e.getMessage());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.inpatientInformationInquiry(map);
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
    @PostMapping("/inpatientRecordQuery")
    public WrapperResponse<String> inpatientRecordQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【病人住院记录】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【病人住院记录】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【病人住院记录】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.inpatientRecordQuery(map);
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
    @PostMapping("/detailedQueryOfInpatientRecords")
    public WrapperResponse<String> detailedQueryOfInpatientRecords(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = null;
        try {
            log.debug("微信小程序【病人住院记录明细】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【病人住院记录明细】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【病人住院记录明细】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.detailedQueryOfInpatientRecords(map);
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
    @PostMapping("/oneDayListRecordQuery")
    public WrapperResponse<String> oneDayListRecordQuery (@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【住院病人日费用清单】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【住院病人日费用清单】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【住院病人日费用清单】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.oneDayListRecordQuery(map);
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
    @PostMapping("/dailyListDetailsQuery")
    public WrapperResponse<String> dailyListDetailsQuery(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【住院病人日费用清单明细】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【住院病人日费用清单明细】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【住院病人日费用清单明细】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.dailyListDetailsQuery(map);
    }

    /**
     * 查询所有科室下所有七天内有排班的医生
     */
    @PostMapping("/querySevenQueueDoctor")
    public WrapperResponse<String> querySevenQueueDoctor(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【查询所有科室下所有七天内有排班的医生】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【查询所有科室下所有七天内有排班的医生】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【查询所有科室下所有七天内有排班的医生】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.querySevenQueueDoctor(map);
    }


    /**
     * 基础信息疾病查询
     */
    @PostMapping("/queryBaseDisease")
    public WrapperResponse<String> queryBaseDisease(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【基础信息疾病查询】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【基础信息疾病查询】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【基础信息疾病查询】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryBaseDisease(map);
    }

    /**
     * 核酸检验申请
     */
    @PostMapping("/hsjcApply")
    public WrapperResponse<String> saveHsjcApply(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【核酸检验申请】入参解密前：" + MapUtils.get(paramMap, "data"));
            data = AsymmetricEncryption.pubdecrypt(MapUtils.get(paramMap, "data"));
            log.debug("微信小程序【核酸检验申请】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【核酸检验申请】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.saveHsjcApply(map);
    }

    /**
     * @Menthod: queryPrescribeListForQRcode
     * @Desrciption: 根据传入的处方号查询处方及处方明细
     * @Param: 1.hospCode：医院编码 2.data：入参 opId-处方id visiId-就诊id
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-10-10 16:08
     * @Return: String json串
     **/
    @PostMapping("/queryPrescribeListForQRcode")
    public WrapperResponse<String> queryPrescribeListForQRcode(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }

        String data = null;
        try {
            log.debug("微信小程序【查询处方明细列表】入参解密前：" + (String) paramMap.get("data"));

            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
            log.debug("微信小程序【查询处方明细列表】入参解密后：" + JSON.parse(data));
        } catch (Exception e) {
            throw new AppException("【查询处方明细列表】入参错误，请联系管理员！" + e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.queryPrescribeListForQRcode(map);
    }

    /**
     * @Menthod: querySettleCostListForQRcode
     * @Desrciption: 根据结算id查询已缴费费用明细
     * @Param: 1.hospCode：医院编码 2.data：入参 settleId 结算id  visitId 急诊id
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022-10-14 09:08
     * @Return: String json串
     **/
    @PostMapping("/querySettleCostList")
    public WrapperResponse<String> querySettleCostList(@RequestBody Map<String, Object> paramMap) {
        String hospCode = MapUtils.get(paramMap, "hospCode");
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("入参错误，请传入医院编码！");
        }
        String data = (String) paramMap.get("data");
//        try {
//            log.debug("微信小程序【查询已缴费费用明细】入参解密前：" + (String) paramMap.get("data"));
//
//            data = AsymmetricEncryption.pubdecrypt(paramMap.get("data").toString());
//            log.debug("微信小程序【查询已缴费费用明细】入参解密后：" + JSON.parse(data));
//        } catch (Exception e) {
//            throw new AppException("【查询已缴费费用明细】入参错误，请联系管理员！" + e.getMessage());
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        if (StringUtils.isNotEmpty(data)) {
            map.put("data", JSON.parse(data));
        }
        return wxBasicInfoService_consumer.querySettleCostList(map);
    }

}

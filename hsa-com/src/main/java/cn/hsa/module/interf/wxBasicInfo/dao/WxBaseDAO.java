package cn.hsa.module.interf.wxBasicInfo.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
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
public interface WxBaseDAO {

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


    List<TreeMenuNode> queryDeptTree(Map<String, Object> map);


    // 查询优惠类别，默认普通病人
    List<BasePreferentialTypeDTO> queryPreferentialTypeList(String hospCode);

    List<BaseDiseaseDTO> queryBaseDisease(Map<String, Object> map);
}

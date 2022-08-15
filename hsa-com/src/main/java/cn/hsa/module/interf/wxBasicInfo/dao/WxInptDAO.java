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
public interface WxInptDAO {

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

    // 根据住院号查询费用清单
    List<InptCostDTO> queryInptCostRecord(Map<String, Object> data);

    // 根据微信订单号查询预交金记录是否存在
    InptAdvancePayDTO queryInptAdvancePayByOrderNo(String hospCode, String orderNo);

    // 根据id查询就诊记录
    InptVisitDTO getInptVisitById(InptVisitDTO inptVisitDTO);

    // 根据visitId查询出所有状态为0正常的预交金记录
    List<InptAdvancePayDTO> queryInptAdvancePayByVisitId(InptAdvancePayDTO inptAdvancePayDTO);

    // 更新住院就诊表累计预交金和累计余额
    int updateInptVisitTotalAdvanceAndBalance(InptVisitDTO inptVisitDTOById);
}

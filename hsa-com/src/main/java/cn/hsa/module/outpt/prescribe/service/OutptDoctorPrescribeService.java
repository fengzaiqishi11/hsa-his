package cn.hsa.module.outpt.prescribe.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.redis.bo.RedisBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.service
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptDoctorPrescribeService {


    /**
     * @Menthod queryPatientByOperType
     * @Desrciption  查询未就诊、已就诊数据
     * @param map loginDeptId：登录科室  userId：登录人
     *            visitStartTime ：就诊开始时间 visitEntTime：就诊结算时间 concent : 查询条件
     *            hospCode ：医院编码  operType：1未就诊 2：已就诊
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/queryPatientByOperType")
    WrapperResponse<List<Map<String, Object>>> queryPatientByOperType(Map map);

    /**
     * @Menthod queryVisit
     * @Desrciption  查询
     * @param map:就诊类型
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return PageDTO
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/queryVisit")
    WrapperResponse<PageDTO> queryVisit(Map map);

    /**
     * @Menthod queryMedicalRecord
     * @Desrciption  获取历史病历记录
     * @param map profileId：个人档案ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/queryMedicalRecord")
    WrapperResponse<List<Map<String, Object>>> queryMedicalRecord(Map map);

    /**
     * @Menthod getMedicalRecord
     * @Desrciption  获取病历记录
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getMedicalRecord")
    WrapperResponse<List<Map<String, Object>>> getMedicalRecord(Map map);

    /**
     * @Menthod saveMedicalRecord
     * @Desrciption  保存病历记录
     * @param map outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/outptMedicalRecordDTO")
    WrapperResponse<Boolean> saveMedicalRecord(Map map);

    /**
     * @Menthod updateVisit
     * @Desrciption  修改个人信息
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updateVisit")
    WrapperResponse<Boolean> updateVisit(Map map);

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  保存住院证信息
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updateVisitInHospital")
    WrapperResponse<Boolean> updateVisitInHospital(Map map);

    /**
     * @Menthod updateIsVisit
     * @Desrciption  接诊接口
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updateIsVisit")
    WrapperResponse<Boolean> updateIsVisit(Map map);


    /**
     * @Method getPrescribeAllDetail
     * @Desrciption 查询处方信息
     * @param map：outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/9 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/outpt/prescribe/getPrescribeAllDetail")
    WrapperResponse<PageDTO> getPrescribeAllDetail(Map<String, Object> map);

    @GetMapping("/service/outpt/prescribe/getPrescribeAllDetailBySettleNo")
    WrapperResponse<List<OutptPrescribeDTO>> getPrescribeAllDetailBySettleNo(Map<String, Object> map);

    /**
     * @Menthod getPrescribe
     * @Desrciption  查询处方信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribe")
    WrapperResponse<List<Map<String, Object>>> getPrescribe(Map map);

    /**
     * @Menthod getPrescribeDetail
     * @Desrciption  查询处方明细信息
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribeDetail")
    WrapperResponse<List<Map<String, Object>>> getPrescribeDetail(Map map);

    /**
     * @Menthod getPrescribeDetail
     * @Desrciption  获取处方明细明细
     * @param map opdId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribeDetailExt")
    WrapperResponse<List<Map<String, Object>>> getPrescribeDetailExt(Map map);

    /**
     * @Menthod getPrescribeDetailAll
     * @Desrciption  获取处方明细记录（处方查询使用）
     * @param  map : outptPrescribeDTO  就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/10/9 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribeDetailAll")
    WrapperResponse<List<Map<String, Object>>> getPrescribeDetailAll(Map map);

    /**
     * @Menthod getVisitById
     * @Desrciption  获取就诊信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getVisitById")
    WrapperResponse<List<OutptVisitDTO>> getVisitById(Map map);

    /**
     * @Menthod getPrescribeCost
     * @Desrciption  获取处方费用
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribeCost")
    WrapperResponse<List<OutptCostDTO>> getPrescribeCost(Map map);

    /**
     * @Menthod getOutptRegisterDetailDO
     * @Desrciption  获取挂号费用信息
     * @param map ：outptRegisterDTO: preferentialTypeId:优惠类型  cyId：挂号类型ID
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDetailDto
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getOutptRegisterDetailDO")
    WrapperResponse<List<OutptRegisterDetailDto>> getOutptRegisterDetailDO(Map map);

    /**
     * @Menthod saveDirectVisit
     * @Desrciption  保存直接就诊
     * @param map：挂号信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/saveDirectVisit")
    WrapperResponse<OutptVisitDTO> saveDirectVisit(Map map);

    /**
     * @Menthod getCfData
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map : baseDrugDTO 药品信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getCfData")
    WrapperResponse<PageDTO> getCfData(Map map);

    /**
     * @Menthod savePrescribe
     * @Desrciption  保存处方信息
     * @param map ： outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/savePrescribe")
    WrapperResponse<Boolean> savePrescribe(Map map);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 处方模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @GetMapping("/service/outpt/outptDoctorPrescribeService/queryOutptPrescribeTempDTO")
    WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(Map map);

    /**
     * @Menthod queryOutptPrescribeTempDetails
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map : outptPrescribeTempDTO : 处方模块
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/queryOutptPrescribeTempDetails")
    WrapperResponse<List<OutptPrescribeTempDetailDTO>> queryOutptPrescribeTempDetails(Map map);

    /**
     * @Menthod getByCode
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map:sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getByCode")
    WrapperResponse<List<SysCodeDetailDTO>> getByCode(Map map);

    /**
     * @Menthod getByCodeDetail
     * @Desrciption  获取药品、医嘱第二级目录
     * @param map: sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getByCodeDetail")
    WrapperResponse<List<SysCodeDetailDTO>> getByCodeDetail(Map map);

    /**
     * @Menthod getDrugData
     * @Desrciption  获取药品信息
     * @param map: baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getDrugData")
    WrapperResponse<PageDTO> getDrugData(Map map);

    /**
     * @Menthod calculateYp
     * @Desrciption  计算剂量、用量、频率、总数量、用药天数
     * @param map: outptPrescribeDetailsDTO: itemId ：药品ID RateId：频率ID  Dosage：剂量 num：用量
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return Map : jl:剂量  yl:用量 zsl:总数量 yyts:用药天数
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/calculateYp")
    WrapperResponse<Map> calculateYp(Map map);

    /**
     * @Menthod deleteOutptPrescribe
     * @Desrciption  删除处方信息
     * @param map: outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/deleteOutptPrescribe")
    WrapperResponse<Boolean> deleteOutptPrescribe(Map map);

    /**
     * @Menthod updatePrescribeIsCancel
     * @Desrciption  作废处方信息
     * @param map: outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updatePrescribeIsCancel")
    WrapperResponse<Boolean> updatePrescribeIsCancel(Map map);


    /**
     * @Menthod getOutptDiagnose
     * @Desrciption  获取诊断信息
     * @param map: outptPrescribeDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/getOutptDiagnose")
    WrapperResponse<List<OutptDiagnoseDTO>> getOutptDiagnose(Map map);

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param map: outptPrescribeDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/saveOutptDiagnose")
    WrapperResponse<Boolean> saveOutptDiagnose(Map map);

    /**
     * @Menthod deleteDiagnoseById
     * @Desrciption  根据诊断ID删除处方诊断信息
     * @param map: outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/deleteDiagnoseById")
    WrapperResponse<Boolean> deleteDiagnoseById(Map map);

    /**
     * @Desrciption 获取药品单位
     * @param map ：baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getDrugUnitCode")
    WrapperResponse<List<Map<String, Object>>> getDrugUnitCode(Map map);

    /**
     * @Menthod updatePrescribeSubmit
     * @Desrciption  批量提交处方
     * @param map : outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updatePrescribeSubmit")
    WrapperResponse<Boolean> updatePrescribeSubmit(Map map);

    /**
     * @Menthod updateIsCanPrescribeSubmit
     * @Desrciption  批量取消提交处方
     * @param map : outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/updateIsCanPrescribeSubmit")
    WrapperResponse<Boolean> updateIsCanPrescribeSubmit(Map map);

    /**
     * @Menthod getOperInfoRecord
     * @Desrciption  获取手术申请单
     * @param map：operInfoRecordDTO: 手术申请
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return OperInfoRecordDTO
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/getOperInfoRecord")
    WrapperResponse<OperInfoRecordDTO> getOperInfoRecord(Map map);

    /**
     * @Method queryOutptDiagnose
     * @Desrciption  长沙市医保：门特病人做试算，结算操作时要传主，副诊断信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/3 16:33
     * @Return OutptDiagnoseDTO集合
    **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/queryOutptDiagnose")
    WrapperResponse<List<OutptDiagnoseDTO>> queryOutptDiagnose(Map map);

    /**
     * @Menthod findVisitListById
     * @Desrciption  根据此次的就只能ID 查询此人的就诊记录
     * @param  map
     * @Author pengbo
     * @Date   2020/10/26 10:24
     * @Return outptPrescribeDTO
     **/
    @PostMapping("/web/outpt/outptDoctorPrescribeService/findVisitListById")
    WrapperResponse<PageDTO> findVisitListById(Map map);


    List<OutptPrescribeDetailsDTO> queryAllPrescribe(Map<String, Object> map);

    /**
     * @Menthod isNeedReportInfectiousDisease
     * @Desrciption  根据疾病id判断是否需要上报传染病
     * @param  map
     * @Author liuliyun
     * @Date   2021/04/21
     * @Return Boolean
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/isNeedReportInfectiousDisease")
    WrapperResponse<BaseDiseaseDTO> isNeedReportInfectiousDisease(Map map);


    /**
     * @Desrciption 检查库存
     * @param map
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    WrapperResponse<List<Map<String, Object>>>checkStock(Map map);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: outptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     **/
    @GetMapping("/service/outpt/outptDoctorPrescribe/queryLimitDrugList")
    WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap);

    /**
     * @Menthod: updateOuptCostAndPreDetailExt
     * @Desrciption: 更新费用表以及处方明细表副表限制用药相关字段()
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    @PostMapping("/service/outpt/outptDoctorPrescribe/updateOuptCostAndPreDetailExt")
    WrapperResponse<Boolean> updateOuptCostAndPreDetailExt(Map map);
    /**
     * @Menthod: getBaseDrug
     * @Desrciption: 获取药品及取整方式
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    @PostMapping("/service/outpt/outptDoctorPrescribe/getBaseDrug")
    WrapperResponse<BaseDrugDTO> getBaseDrug(Map parmMap);
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getCfData2")
    WrapperResponse<PageDTO> getCfData2(Map paramMap);

    @PostMapping("/web/outpt/outptDoctorPrescribeService/queryOutptDiagnoseByVisitIds")
    WrapperResponse<List<OutptDiagnoseDTO>> queryOutptDiagnoseByVisitIds(Map map);

    @PostMapping("/web/outpt/outptDoctorPrescribeService/queryOutptMatchDiagnose")
    WrapperResponse<List<OutptDiagnoseDTO>> queryOutptMatchDiagnose(Map<String, Object> reqMap);
    /**
     * @param paramMap
     * @Menthod: queryDrugCount
     * @Desrciption: 获取精麻药品允许时间内开药次数
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-23 19:51
     * @Return: List<Map>
     */
    WrapperResponse<List<Map>> queryDrugCount(Map paramMap);

    /**
     * @Menthod getPrescribeDetailForEncode
     * @Desrciption  查询处方明细信息(带二维码)
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author liuliyun
     * @Date   2022/10/09 14:24
     * @Return Map<String, Object>
     **/
    @GetMapping("/web/outpt/outptDoctorPrescribeService/getPrescribeDetailForEncode")
    WrapperResponse<Map<String, Object>> getPrescribeDetailForEncode(Map map);
}

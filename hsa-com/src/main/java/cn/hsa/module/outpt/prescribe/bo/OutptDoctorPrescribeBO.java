package cn.hsa.module.outpt.prescribe.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.bo
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块业务逻辑
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptDoctorPrescribeBO {

    /**
     * @Menthod queryPatientByOperType
     * @Desrciption  查询未就诊、已就诊数据
     * @param outptVisitDTO loginDeptId：登录科室  userId：登录人
     *            visitStartTime ：就诊开始时间 visitEntTime：就诊结算时间 concent : 查询条件
     *            hospCode ：医院编码  operType：1未就诊 2：已就诊
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> queryPatientByOperType(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod queryPatientByOperType
     * @Desrciption  查询未就诊、已就诊数据
     * @param outptVisitDTO loginDeptId：登录科室  userId：登录人
     *            visitStartTime ：就诊开始时间 visitEntTime：就诊结算时间 concent : 查询条件
     *            hospCode ：医院编码  operType：1未就诊 2：已就诊
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    PageDTO queryVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod queryMedicalRecord
     * @Desrciption  获取历史病历记录
     * @param map profileId：个人档案ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> queryMedicalRecord(Map map);

    /**
     * @Menthod getMedicalRecord
     * @Desrciption  获取病历记录
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getMedicalRecord(Map map);

    /**
     * @Menthod saveMedicalRecord
     * @Desrciption  保存病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean saveMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO);

    /**
     * @Menthod insertMedicalRecord
     * @Desrciption  新增病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean insertMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO);

    /**
     * @Menthod updateMedicalRecord
     * @Desrciption  修改病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean updateMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO);

    /**
     * @Menthod updateVisit
     * @Desrciption  修改个人信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean updateVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  保存住院证信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean updateVisitInHospital(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod updateIsVisit
     * @Desrciption  接诊接口
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     **/
    boolean updateIsVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Method getPrescribeAllDetail
     * @Desrciption 查询处方信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/9 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO getPrescribeAllDetail(OutptPrescribeDTO outptPrescribeDTO);

    List<OutptPrescribeDTO> getPrescribeAllDetailBySettleNo(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod getPrescribe
     * @Desrciption  查询处方信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getPrescribe(Map map);

    /**
     * @Menthod getPrescribeDetail
     * @Desrciption  查询处方明细信息
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getPrescribeDetail(Map map);

    /**
     * @Menthod getPrescribeDetailExt
     * @Desrciption  获取处方明细明细
     * @param map opdId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getPrescribeDetailExt(Map map);

    /**
     * @Menthod getPrescribeDetailAll
     * @Desrciption  获取处方明细记录（处方查询使用）
     * @param  outptPrescribeDTO  就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/10/9 10:24
     * @Return List<Map<String, Object>>
     **/
    List<Map<String, Object>> getPrescribeDetailAll(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod getVisitById
     * @Desrciption  获取就诊信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<OutptVisitDTO> getVisitById(Map map);

    /**
     * @Menthod getPrescribeCost
     * @Desrciption  获取处方费用
     * @param map opdId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     **/
    List<OutptCostDTO> getPrescribeCost(Map map);

    /**
     * @Menthod saveDirectVisit
     * @Desrciption  保存直接就诊
     * @param outptRegisterDTO：挂号信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    OutptVisitDTO saveDirectVisit(OutptRegisterDTO outptRegisterDTO);

    /**
     * @Menthod getCfData
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    PageDTO getCfData(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod savePrescribe
     * @Desrciption  保存处方信息
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    boolean savePrescribe(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Menthod queryOutptPrescribeTempDetails
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param outptPrescribeTempDTO : 处方模块
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Menthod getByCode
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    List<SysCodeDetailDTO> getByCode(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Menthod getByCodeDetail
     * @Desrciption  获取药品、医嘱第二级目录
     * @param sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    List<SysCodeDetailDTO> getByCodeDetail(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Menthod getDrugData
     * @Desrciption  获取药品信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return WrapperResponse<PageDTO>
     **/
    PageDTO getDrugData(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod getOutptRegisterDetailDO
     * @Desrciption  获取挂号费用信息
     * @param outptRegisterDTO: preferentialTypeId:优惠类型  cyId：挂号类型ID
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDetailDto
     **/
    List<OutptRegisterDetailDto> getOutptRegisterDetailDO(OutptRegisterDTO outptRegisterDTO);

    /**
     * @Menthod calculateYp
     * @Desrciption  计算剂量、用量、频率、总数量、用药天数
     * @param outptPrescribeDetailsDTO: itemId ：药品ID RateId：频率ID  Dosage：剂量 num：用量
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return Map : jl:剂量  yl:用量 zsl:总数量 yyts:用药天数
     **/
    Map calculateYp(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Menthod deleteOutptPrescribe
     * @Desrciption  删除处方信息
     * @param outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    boolean deleteOutptPrescribe(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod updatePrescribeIsCancel
     * @Desrciption  作废处方信息
     * @param outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    boolean updatePrescribeIsCancel(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod getOutptDiagnose
     * @Desrciption  获取诊断信息
     * @param outptPrescribeDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    List<OutptDiagnoseDTO> getOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    boolean saveOutptDiagnose(OutptDiagnoseDTO outptDiagnoseDTO);

    /**
     * @Menthod deleteDiagnoseById
     * @Desrciption  根据诊断ID删除处方诊断信息
     * @param outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    boolean deleteDiagnoseById(OutptDiagnoseDTO outptDiagnoseDTO);

    /**
     * @Desrciption 获取药品单位
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> getDrugUnitCode(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod updatePrescribeSubmit
     * @Desrciption  批量提交处方
     * @param outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    boolean updatePrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO);

    boolean updatePrescribeSubmit2(Map map,OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod updateIsCanPrescribeSubmit
     * @Desrciption  批量取消提交处方
     * @param outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    boolean updateIsCanPrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod getOperInfoRecord
     * @Desrciption  获取手术申请单
     * @param operInfoRecordDTO: 手术申请
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return OperInfoRecordDTO
     **/
    OperInfoRecordDTO getOperInfoRecord(OperInfoRecordDTO operInfoRecordDTO);

    /**
     *
     * @Method queryOutptDiagnose
     * @Desrciption 长沙市医保：门特病人做试算，结算操作时要传主，副诊断信息
     * @Param outptDiagnoseDTO
     * @Author fuhui
     * @Date 2021/2/3 16:33
     * @Return OutptDiagnoseDTO集合
     */
    List<OutptDiagnoseDTO> queryOutptDiagnose(OutptDiagnoseDTO outptDiagnoseDTO);
    /**
     * @Menthod findVisitListById
     * @Desrciption  根据此次的就只能ID 查询此人的就诊记录
     * @param  outptPrescribeDTO
     * @Author pengbo
     * @Date   2020/10/26 10:24
     * @Return outptPrescribeDTO
     **/
    PageDTO  findVisitListById(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 检查库存
     * @param outptPrescribeDetailsDTO
     * @Author luonianxin
     * @Date   2021/5/28 14:24
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> checkStock(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    List<InsureItemMatchDTO> queryLimitDrugList(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Menthod: updateOuptCostAndPreDetailExt
     * @Desrciption: 更新费用表以及处方明细表副表限制用药相关字段()
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    Boolean updateOuptCostAndPreDetailExt(List<InsureItemMatchDTO> insureItemMatchDTOS);
    /**
     * @Menthod: getBaseDrug
     * @Desrciption: 获取药品及取整方式
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    BaseDrugDTO getBaseDrug(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);


    List<OutptDiagnoseDTO> queryOutptDiagnoseByVisitIds(OutptVisitDTO outptVisitDTO);


    List<OutptDiagnoseDTO> queryOutptMatchDiagnose(OutptVisitDTO outptVisitDTO);

    PageDTO getCfData2(BaseDrugDTO baseDrugDTO);
    /**
     * @param outptPrescribeDTO
     * @Menthod: queryDrugCount
     * @Desrciption: 获取精麻药品允许时间内开药次数
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-23 19:51
     * @Return: List<Map>
     */
    List<Map> queryDrugCount(OutptPrescribeDTO outptPrescribeDTO);
}

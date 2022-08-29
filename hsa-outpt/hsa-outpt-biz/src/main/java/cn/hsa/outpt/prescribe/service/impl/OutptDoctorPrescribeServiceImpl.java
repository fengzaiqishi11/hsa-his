package cn.hsa.outpt.prescribe.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.infectious.bo.OutptInfectiousDiseaExecBO;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import cn.hsa.module.outpt.outptclassify.bo.OutptClassifyBO;
import cn.hsa.module.outpt.prescribe.bo.OutptDoctorPrescribeBO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.service
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块service接口实现类（提供给dubbo）
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/outptDoctorPrescribe")
@Slf4j
@Service("outptDoctorPrescribeService_provider")
public class OutptDoctorPrescribeServiceImpl extends HsafService implements OutptDoctorPrescribeService {

    /**
     * 处方管理逻辑接口
     */
    @Resource
    private OutptDoctorPrescribeBO outptDoctorPrescribeBO;

    /**
     * 挂号逻辑接口
     */
    @Resource
    OutptClassifyBO outptClassifyBO;

    @Resource
    private OutptInfectiousDiseaExecBO outptInfectiousDiseaExecBO;

    @Resource
    private BaseDiseaseService baseDiseaseService_consumer;


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
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryPatientByOperType(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryPatientByOperType(outptVisitDTO));
    }

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
    @Override
    public WrapperResponse<PageDTO> queryVisit(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryVisit(outptVisitDTO));
    }

    /**
     * @Menthod queryMedicalRecord
     * @Desrciption  获取历史病历记录
     * @param map profileId：个人档案ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryMedicalRecord(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.queryMedicalRecord(map));
    }

    /**
     * @Menthod getMedicalRecord
     * @Desrciption  获取病历记录
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> getMedicalRecord(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getMedicalRecord(map));
    }

    /**
     * @Menthod saveMedicalRecord
     * @Desrciption  保存病历记录
     * @param map outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveMedicalRecord(Map map) {
        OutptMedicalRecordDTO outptMedicalRecordDTO = MapUtils.get(map,"outptMedicalRecordDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.saveMedicalRecord(outptMedicalRecordDTO));
    }

    /**
     * @Menthod updateVisit
     * @Desrciption  修改个人信息
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateVisit(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updateVisit(outptVisitDTO));
    }

    /**
     * @Menthod updateIsVisit
     * @Desrciption  接诊接口
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateIsVisit(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updateIsVisit(outptVisitDTO));
    }

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  保存住院证信息
     * @param map outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateVisitInHospital(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map,"outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updateVisitInHospital(outptVisitDTO));
    }

    /**
     * @Menthod getPrescribe
     * @Desrciption  查询处方信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> getPrescribe(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getPrescribe(map));
    }

    /**
     * @Method getPrescribeAllDetail
     * @Desrciption 查询处方信息
     * @param map：outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/9 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> getPrescribeAllDetail(Map<String, Object> map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        PageDTO pageDTO = outptDoctorPrescribeBO.getPrescribeAllDetail(outptPrescribeDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: getPrescribeAllDetailBySettleNo
     * @Description: 根据结算单号查询处方信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/14 14:39
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<List<OutptPrescribeDTO>> getPrescribeAllDetailBySettleNo(Map<String, Object> map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getPrescribeAllDetailBySettleNo(outptPrescribeDTO));
    }

    /**
     * @Menthod getPrescribeDetail
     * @Desrciption  查询处方明细信息
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> getPrescribeDetail(Map map) {
        List a = outptDoctorPrescribeBO.getPrescribeDetail(map);
        return WrapperResponse.success(a);
    }

    /**
     * @Menthod getPrescribeDetailExt
     * @Desrciption  获取处方明细明细
     * @param map opdId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailExt(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getPrescribeDetailExt(map));
    }

    /**
     * @Menthod getPrescribeDetailAll
     * @Desrciption  获取处方明细记录（处方查询使用）
     * @param  map ： outptPrescribeDTO  就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/10/9 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<Map<String, Object>>> getPrescribeDetailAll(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getPrescribeDetailAll(outptPrescribeDTO));
    }

    /**
     * @Menthod getVisitById
     * @Desrciption  获取就诊信息
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    @Override
    public WrapperResponse<List<OutptVisitDTO>> getVisitById(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getVisitById(map));
    }

    /**
     * @Menthod getPrescribeCost
     * @Desrciption  获取处方费用
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     **/
    @Override
    public WrapperResponse<List<OutptCostDTO>> getPrescribeCost(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getPrescribeCost(map));
    }

    /**
     * @Menthod getOutptRegisterDetailDO
     * @Desrciption  获取挂号费用信息
     * @param map ：outptRegisterDTO: preferentialTypeId:优惠类型  cyId：挂号类型ID
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return OutptRegisterDetailDto
     **/
    @Override
    public WrapperResponse<List<OutptRegisterDetailDto>> getOutptRegisterDetailDO(Map map) {
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map,"outptRegisterDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getOutptRegisterDetailDO(outptRegisterDTO));
    }

    /**
     * @Menthod saveDirectVisit
     * @Desrciption  保存直接就诊
     * @param map outptRegisterDTO 挂号信息
     * @Author zengfeng
     * @Date   2020/9/8 10:24
     * @Return Boolean
     **/
    @Override
    public WrapperResponse<OutptVisitDTO> saveDirectVisit(Map map) {
        OutptRegisterDTO outptRegisterDTO = MapUtils.get(map,"outptRegisterDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.saveDirectVisit(outptRegisterDTO));
    }

    /**
     * @Menthod getCfData
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map : baseDrugDTO 药品信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getCfData(Map map) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(map, "baseDrugDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getCfData(baseDrugDTO));
    }

    /**
     * @Menthod savePrescribe
     * @Desrciption  保存处方信息
     * @param map ： outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    @Override
    public WrapperResponse<Boolean> savePrescribe(Map map) {
        OutptPrescribeDTO outptPrescribeDTOList = MapUtils.get(map,"outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.savePrescribe(outptPrescribeDTOList));
    }

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param map ： outptPrescribeTempDTO 处方模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public WrapperResponse<List<OutptPrescribeTempDTO>> queryOutptPrescribeTempDTO(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryOutptPrescribeTempDTO(outptPrescribeTempDTO));
    }


    /**
     * @Menthod queryOutptPrescribeTempDetails
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map : outptPrescribeTempDTO : 处方模块
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @Override
    public WrapperResponse<List<OutptPrescribeTempDetailDTO>> queryOutptPrescribeTempDetails(Map map) {
        OutptPrescribeTempDTO outptPrescribeTempDTO = MapUtils.get(map, "outptPrescribeTempDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryOutptPrescribeTempDetails(outptPrescribeTempDTO));
    }

    /**
     * @Menthod getByCode
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param map:sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<SysCodeDetailDTO>
     **/
    @Override
    public WrapperResponse<List<SysCodeDetailDTO>> getByCode(Map map) {
        SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getByCode(sysCodeDetailDTO));
    }

    /**
     * @Menthod getByCodeDetail
     * @Desrciption  获取药品、医嘱第二级目录
     * @param map: sysCodeDetailDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<SysCodeDetailDTO>
     **/
    @Override
    public WrapperResponse<List<SysCodeDetailDTO>> getByCodeDetail(Map map) {
        SysCodeDetailDTO sysCodeDetailDTO = MapUtils.get(map, "sysCodeDetailDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getByCodeDetail(sysCodeDetailDTO));
    }

    /**
     * @Menthod getDrugData
     * @Desrciption  获取药品信息
     * @param map: baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getDrugData(Map map) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(map, "baseDrugDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getDrugData(baseDrugDTO));
    }

    /**
     * @Menthod calculateYp
     * @Desrciption  计算剂量、用量、频率、总数量、用药天数
     * @param map: outptPrescribeDetailsDTO: itemId ：药品ID RateId：频率ID  Dosage：剂量 num：用量
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return Map : jl:剂量  yl:用量 zsl:总数量 yyts:用药天数
     **/
    @Override
    public WrapperResponse<Map> calculateYp(Map map) {
        OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = MapUtils.get(map, "outptPrescribeDetailsDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.calculateYp(outptPrescribeDetailsDTO));
    }

    /**
     * @Menthod deleteOutptPrescribe
     * @Desrciption  删除处方信息
     * @param map: outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> deleteOutptPrescribe(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.deleteOutptPrescribe(outptPrescribeDTO));
    }

    /**
     * @Menthod updatePrescribeIsCancel
     * @Desrciption  作废处方信息
     * @param map: outptPrescribeDTO: id ：处方ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> updatePrescribeIsCancel(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updatePrescribeIsCancel(outptPrescribeDTO));
    }

    /**
     * @Menthod getOutptDiagnose
     * @Desrciption  获取诊断信息
     * @param map: outptPrescribeDTO: 就诊ID
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @Override
    public WrapperResponse<List<OutptDiagnoseDTO>> getOutptDiagnose(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getOutptDiagnose(outptPrescribeDTO));
    }

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param map: outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> saveOutptDiagnose(Map map) {
        OutptDiagnoseDTO outptDiagnoseDTO = MapUtils.get(map, "outptDiagnoseDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.saveOutptDiagnose(outptDiagnoseDTO));
    }

    /**
     * @Menthod deleteDiagnoseById
     * @Desrciption  根据诊断ID删除处方诊断信息
     * @param map: outptDiagnoseDTO: 诊断信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> deleteDiagnoseById(Map map) {
        OutptDiagnoseDTO outptDiagnoseDTO = MapUtils.get(map, "outptDiagnoseDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.deleteDiagnoseById(outptDiagnoseDTO));
    }

    /**
     * @Desrciption 获取药品单位
     * @param map：baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> getDrugUnitCode(Map map) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(map,"baseDrugDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getDrugUnitCode(baseDrugDTO));
    }

    /**
     * @Menthod updatePrescribeSubmit
     * @Desrciption  批量提交处方
     * @param map： outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> updatePrescribeSubmit(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map,"outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updatePrescribeSubmit2(map,outptPrescribeDTO));
    }

    /**
     * @Menthod updateIsCanPrescribeSubmit
     * @Desrciption  批量取消提交处方
     * @param map： outptPrescribeDTO: 处方信息
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @Override
    public WrapperResponse<Boolean> updateIsCanPrescribeSubmit(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map,"outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.updateIsCanPrescribeSubmit(outptPrescribeDTO));
    }


    /**
     * @Menthod getOperInfoRecord
     * @Desrciption  获取手术申请单
     * @param map：operInfoRecordDTO: 手术申请
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return OperInfoRecordDTO
     **/
    @Override
    public WrapperResponse<OperInfoRecordDTO> getOperInfoRecord(Map map) {
        OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map,"operInfoRecordDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.getOperInfoRecord(operInfoRecordDTO));
    }

    /**
     * @Method queryOutptDiagnose
     * @Desrciption 长沙市医保：门特病人做试算，结算操作时要传主，副诊断信息
     * @Param map
     * @Author fuhui
     * @Date 2021/2/3 16:33
     * @Return OutptDiagnoseDTO集合
     */
    @Override
    public WrapperResponse<List<OutptDiagnoseDTO>> queryOutptDiagnose(Map map) {
        OutptDiagnoseDTO  outptDiagnoseDTO = MapUtils.get(map,"outptDiagnoseDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryOutptDiagnose(outptDiagnoseDTO));
    }

    /**
     * @param map
     * @Menthod findVisitListById
     * @Desrciption 根据此次的就只能ID 查询此人的就诊记录
     * @Author pengbo
     * @Date 2020/10/26 10:24
     * @Return outptPrescribeDTO
     **/
    @Override
    public WrapperResponse<PageDTO> findVisitListById(Map map) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(map, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.findVisitListById(outptPrescribeDTO));
    }

    /**
     * @Method queryAllPrescribe
     * @Desrciption  查询所有的处方明细信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/28 20:55
     * @Return
    **/
    @Override
    public List<OutptPrescribeDetailsDTO> queryAllPrescribe(Map<String, Object> map) {
        return null;
    }

    @Override
    public WrapperResponse<BaseDiseaseDTO> isNeedReportInfectiousDisease(Map map) {
        //BaseDiseaseDTO diseaseDTO = baseDiseaseService_consumer.getById(map).getData();
        List<BaseDiseaseDTO> diseaseDTOS = baseDiseaseService_consumer.queryAllInfectious(map).getData();
        if (diseaseDTOS!=null&&diseaseDTOS.size()>0){
            for(BaseDiseaseDTO diseaseDTO: diseaseDTOS) {
                if (diseaseDTO != null) {
                    if (!StringUtils.isEmpty(diseaseDTO.getIsCrb()) && diseaseDTO.getIsCrb().equals(Constants.SF.S)) {
                        String profileId = MapUtils.get(map, "profileId");
                        String hospCode = MapUtils.get(map, "hospCode");
                        InfectiousDiseaseDO infectiousDiseaseDO = new InfectiousDiseaseDO();
                        infectiousDiseaseDO.setHospCode(hospCode);
                        infectiousDiseaseDO.setProfileId(profileId);
                        infectiousDiseaseDO.setInfectiousCode(diseaseDTO.getCrbName());
                        List<InfectiousDiseaseDO> infectiousDiseaseDOS = outptInfectiousDiseaExecBO.findInfectiousById(infectiousDiseaseDO);
                        //传染病无间隔日期需添加传染病病历
                        if (diseaseDTO.getCrbInterval() == null) {
                            //传染病上报，无间隔日期，默认只上报一次
                            if (infectiousDiseaseDOS != null && infectiousDiseaseDOS.size() > 0) {
                                continue;
                            } else {
                                diseaseDTO.setIsNeedReport(true);
                                return WrapperResponse.success(diseaseDTO);
                            }
                        } else if (diseaseDTO.getCrbInterval() != null) {
                            if (infectiousDiseaseDOS != null && infectiousDiseaseDOS.size() > 0) {
                                InfectiousDiseaseDO infectious = infectiousDiseaseDOS.get(0);
                                if (DateUtils.differentDays(infectious.getCrteTime(),new Date()) > diseaseDTO.getCrbInterval()) {
                                    //传染病有间隔日期需判断病历日期是否超过间隔，超过间隔需添加病历
                                    diseaseDTO.setIsNeedReport(true);
                                    return WrapperResponse.success(diseaseDTO);
                                }
                            }else {
                                // 无上报数据 需填写传染病报告卡
                                diseaseDTO.setIsNeedReport(true);
                                return WrapperResponse.success(diseaseDTO);
                            }
                        }
                    }
                }
            }
        }
        BaseDiseaseDTO baseDiseaseDTO=new BaseDiseaseDTO();
        baseDiseaseDTO.setIsNeedReport(false);
        return WrapperResponse.success(baseDiseaseDTO);
    }

    /**
     * @param map
     * @Desrciption 检查库存
     * @Author zengfeng
     * @Date 2020/10/26 14:44
     * @Return List<Map < String, Object>>
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> checkStock(Map map) {
        OutptPrescribeDetailsDTO outptPrescribeDetailsDTO = MapUtils.get(map,"outptPrescribeDetailsDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.checkStock(outptPrescribeDetailsDTO));
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: outptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     **/
    @Override
    public WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(Map paramMap) {
        return WrapperResponse.success(outptDoctorPrescribeBO.queryLimitDrugList(MapUtils.get(paramMap, "outptPrescribeDTO")));
    }

    /**
     * @Menthod: updateOuptCostAndPreDetailExt
     * @Desrciption: 更新费用表以及处方明细表副表限制用药相关字段()
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> updateOuptCostAndPreDetailExt(Map map) {
        return WrapperResponse.success(outptDoctorPrescribeBO.updateOuptCostAndPreDetailExt(MapUtils.get(map, "insureItemMatchDTOS")));
    }

    /**
     * @param parmMap
     * @Menthod: getBaseDrug
     * @Desrciption: 获取药品及取整方式
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 19:51
     * @Return:
     */
    @Override
    public WrapperResponse<BaseDrugDTO> getBaseDrug(Map parmMap) {
        return WrapperResponse.success(outptDoctorPrescribeBO.getBaseDrug(MapUtils.get(parmMap, "outptPrescribeDetailsDTO")));
    }

    @Override
    public WrapperResponse<List<OutptDiagnoseDTO>> queryOutptDiagnoseByVisitIds(Map map) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryOutptDiagnoseByVisitIds(outptVisitDTO));
    }

    @Override
    public WrapperResponse<List<OutptDiagnoseDTO>> queryOutptMatchDiagnose(Map<String, Object> reqMap) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(reqMap, "outptVisitDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryOutptMatchDiagnose(outptVisitDTO));
    }
    /**
     * @param paramMap
     * @Menthod: queryDrugCount
     * @Desrciption: 获取精麻药品允许时间内开药次数
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-23 19:51
     * @Return: List<Map>
     */
    @Override
    public WrapperResponse<List<Map>> queryDrugCount(Map paramMap) {
        OutptPrescribeDTO outptPrescribeDTO = MapUtils.get(paramMap, "outptPrescribeDTO");
        return WrapperResponse.success(outptDoctorPrescribeBO.queryDrugCount(outptPrescribeDTO));
    }

    @Override
    public WrapperResponse<PageDTO> getCfData2(Map paramMap) {
        BaseDrugDTO baseDrugDTO = MapUtils.get(paramMap, "baseDrugDTO");
        baseDrugDTO.setPageNo(1);
        baseDrugDTO.setPageSize(9999);
        return WrapperResponse.success(outptDoctorPrescribeBO.getCfData2(baseDrugDTO));
    }
}

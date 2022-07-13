package cn.hsa.module.outpt.prescribe.dao;


import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDetailDto;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.outptDoctor.prescribe.dao
 * @Class_name: OutptDoctorPrescribeService
 * @Describe: 处方管理模块数据库
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/9/2 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptDoctorPrescribeDAO {

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
     * @Menthod insertMedicalRecord
     * @Desrciption  新增病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int insertMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO);

    /**
     * @Menthod updateMedicalRecord
     * @Desrciption  修改病历记录
     * @param outptMedicalRecordDTO 病历信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updateMedicalRecord(OutptMedicalRecordDTO outptMedicalRecordDTO);

    /**
     * @Menthod updateVisit
     * @Desrciption  修改个人信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updateVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod updateVisitInHospital
     * @Desrciption  保存住院证信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updateVisitInHospital(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod updateIsVisit
     * @Desrciption  接诊接口
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updateIsVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod updateOutptCostDoctor
     * @Desrciption  更新费用医生信息
     * @param outptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updateOutptCostDoctor(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod getPrescribeAllDetail
     * @Desrciption  查询处方信息
     * @param outptPrescribeDTO visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/9 10:24
     * @Return List<Map<String, Object>>
     **/
    List<OutptPrescribeDTO> getPrescribeAllDetail(OutptPrescribeDTO outptPrescribeDTO);

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
     * @Menthod getPrescribeCost
     * @Desrciption  获取处方费用
     * @param map opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     **/
    List<OutptCostDTO> getPrescribeCost(Map map);

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
     * @Menthod getOutptRegisterDetailDO
     * @Desrciption  查询处方主从表信息
     * @param outptRegisterDTO opId：处方ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<OutptRegisterDetailDto> getOutptRegisterDetailDO(OutptRegisterDTO outptRegisterDTO);

    /**
     * @Menthod getCfData
     * @Desrciption  获取开处方药品、材料医嘱数据
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    List<BaseDrugDTO> getCfData(BaseDrugDTO baseDrugDTO);


    /**
    * @Menthod getNewCfData
    * @Desrciption 获取开处方药品、材料医嘱数据(多药房)
    *
    * @Param
    * [baseDrugDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/5 14:46
    * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
    **/
    List<BaseDrugDTO> getNewCfData(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod insertPrescribe
     * @Desrciption  新增处方信息
     * @param outptPrescribeDTO 处方信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int insertPrescribe(List<OutptPrescribeDTO> outptPrescribeDTO);

    /**
     * @Menthod updatePrescribe
     * @Desrciption  修改处方信息
     * @param outptPrescribeDTO 处方信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updatePrescribe(List<OutptPrescribeDTO> outptPrescribeDTO);

    /**
     * @Menthod insertPrescribe
     * @Desrciption  新增处方明细信息
     * @param outptPrescribeDetailsDTO 处方明细信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int insertPrescribeDetail(List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTO);

    /**
     * @Menthod updatePrescribeDetail
     * @Desrciption  修改处方明细信息
     * @param outptPrescribeDetailsDTO 处方明细信息
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return int
     **/
    int updatePrescribeDetail(List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTO);

    /**
     * @Menthod getByCode
     * @Desrciption  获取药品、医嘱大类
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
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/18 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    List<TreeMenuNode> getCodeTree(@Param("code") String code, @Param("hospCode") String hospCode);

    /**
     * @Menthod getDrugData
     * @Desrciption  获取药品信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    List<BaseDrugDTO> getDrugData(BaseDrugDTO baseDrugDTO);

    /**
     * @Menthod getAdviceData
     * @Desrciption  获取医嘱信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<BaseDrugDTO>
     **/
    List<BaseDrugDTO> getAdviceData(BaseDrugDTO baseDrugDTO);

    /**
     * @Method queryOutptPrescribeTempDTO
     * @Desrciption 查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO>
     **/
    List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method queryOutptPrescribeTempDetails
     * @Desrciption 根据处方模板id查询出处方模板明细
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO>
     **/
    List<OutptPrescribeTempDetailDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO);

    /**
     * @Method queryOutptPrescribeTempDetails
     * @Desrciption 根据处方模板id查询出处方模板明细
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    BaseRateDTO queryBaseRate(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getBaseDrug
     * @Desrciption 获取药品信息
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseDrugDTO
     **/
    BaseDrugDTO getBaseDrug(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getBaseItem
     * @Desrciption 获取项目信息
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    BaseItemDTO getBaseItem(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getBaseMaterial
     * @Desrciption 获取材料信息
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseMaterialDTO
     **/
    BaseMaterialDTO getBaseMaterial(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getAdviceDetail
     * @Desrciption 获取医嘱信息
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    List<BaseItemDTO> getAdviceDetail(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getDtfy
     * @Desrciption 获取已生成的动态费用
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return OutptPrescribeExecDTO
     **/
    List<OutptPrescribeExecDTO> getDtfy(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method getDtScfy
     * @Desrciption 获取已生成的动态首次计费费用
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return OutptPrescribeExecDTO
     **/
    List<OutptPrescribeExecDTO> getDtScfy(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method getDtData
     * @Desrciption 获取所有需要生成动态费用的数据
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return OutptPrescribeExecDTO
     **/
    List<OutptPrescribeDetailsDTO> getDtData(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method checkIsSettle
     * @Desrciption 判断是否结算
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int checkIsSettle(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method checkIsSubimt
     * @Desrciption 判断是否提交
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int checkIsSubimt(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method updatePrescribeIsCancel
     * @Desrciption 作废处方
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int updatePrescribeIsCancel(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deletePrescribe
     * @Desrciption 删除处方
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deletePrescribe(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deletePrescribeDetail
     * @Desrciption 删除处方明细
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deletePrescribeDetail(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deletePrescribeDetailExt
     * @Desrciption 删除处方明细执行
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deletePrescribeDetailExt(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deleteDiagnose
     * @Desrciption 删除处方诊断
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deleteDiagnose(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deletePrescribeExec
     * @Desrciption 删除处方执行表
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deletePrescribeExec(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deleteOutptCost
     * @Desrciption 删除处方费用信息表
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deleteOutptCost(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deleteOutptCostDt
     * @Desrciption 删除处方动态费用信息表
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deleteOutptCostDt(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method deleteOperInfoRecord
     * @Desrciption 删除处方动态费用信息表(2021-06-16只能删除未安排的手术信息)
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deleteOperInfoRecord(OutptPrescribeDTO outptPrescribeDTO);

    /**
       * 新增手术申请
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/21 11:02
    **/
    int insertSurgery(OperInfoRecordDTO operInfoRecordDTO);

    /**
     * @Method insertPrescribeDetailExt
     * @Desrciption 新增处方执行明细
     * @param outptPrescribeDetailsExtDTOList
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int insertPrescribeDetailExt(List<OutptPrescribeDetailsExtDTO> outptPrescribeDetailsExtDTOList);

    /**
     * @Method insertDiagnose
     * @Desrciption 新增处方诊断
     * @param outptDiagnoseDTOList
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int insertDiagnose(List<OutptDiagnoseDTO> outptDiagnoseDTOList);

    /**
     * @Method insertPrescribeDetailExt
     * @Desrciption 新增处方执行记录
     * @param outptPrescribeExecDTOList
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int insertPrescribeExec(List<OutptPrescribeExecDTO> outptPrescribeExecDTOList);


    /**
     * @Method getDailyfirstCalc
     * @Desrciption 获取首次计费主表
     * @param map
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseDailyfirstCalcDTO
     **/
    List<BaseDailyfirstCalcDTO> getDailyfirstCalc(Map map);

    /**
     * @Method getAssist
     * @Desrciption 获取辅助计费主表
     * @param map
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseAssistCalcDTO
     **/
    List<BaseAssistCalcDTO> getAssist(Map map);

    /**
     * @Method getAssistDetail
     * @Desrciption 获取辅助计费明细数据
     * @param baseAssistCalcDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    List<BaseAssistCalcDetailDTO> getAssistDetail(BaseAssistCalcDTO baseAssistCalcDTO);

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取最大组号
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    int getMaxGroupNo(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Method getOutptPrescribeDiagnose
     * @Desrciption 获取处方诊断信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     **/
    List<OutptDiagnoseDTO> getOutptPrescribeDiagnose(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method getOutptDiagnose
     * @Desrciption 获取诊断信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     **/
    List<OutptDiagnoseDTO> getOutptDiagnose(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Method getOutptMedicalRecordDiagnose
     * @Desrciption 获取门诊病历诊断信息
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     **/
    List<OutptDiagnoseDTO> getOutptMedicalRecordDiagnose(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 获取系统参数信息
     * @param hospCode
     * @param code
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     */
    List<SysParameterDTO> getParameterValue(@Param("hospCode") String hospCode, @Param("code") String[] code);

    /**
     * @Desrciption 更新诊断新增数据
     * @param outptDiagnoseDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int updatePrescribeDiagnose(OutptDiagnoseDTO outptDiagnoseDTO);

    /**
     * @Desrciption 根据诊断ID删除处方诊断信息
     * @param outptDiagnoseDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int deleteDiagnoseById(OutptDiagnoseDTO outptDiagnoseDTO);

    /**
     * @Desrciption 检查处方是否存在该诊断
     * @param outptDiagnoseDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int checkDiagnosePrescribe(OutptDiagnoseDTO outptDiagnoseDTO);

    /**
     * @Desrciption 获取药品单位
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> getDrugUnitCode(BaseDrugDTO baseDrugDTO);

    /**
     * @Desrciption 获取材料单位
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> getMaterialUnitCode(BaseDrugDTO baseDrugDTO);

    /**
     * @Desrciption 检查库存
     * @param outptPrescribeDetailsDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> checkStock(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Desrciption 批量提交处方
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int updatePrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO);


    /**
     * @Desrciption 检查处方是否结算
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int checkIsPrescribeSettle(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 批量取消提交处方
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int updateIsCanPrescribeSubmit(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 获取手术申请单
     * @param operInfoRecordDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return OperInfoRecordDTO
     */
    OperInfoRecordDTO getOperInfoRecord(OperInfoRecordDTO operInfoRecordDTO);

    /**
     * @Desrciption 新增处方医技申请
     * @param medicalApplyDTOList
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int insertMedicalApply(List<MedicalApplyDTO> medicalApplyDTOList);

    /**
     * @Desrciption 新增处方医技申请明细
     * @param medicalApplyDetailDTOList
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int insertMedicalApplyDetail(List<MedicalApplyDetailDTO> medicalApplyDetailDTOList);

    /**
     * @Desrciption 删除处方医技申请
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int deleteMedicApply(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 删除处方医技申请明细
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int deleteMedicApplyDetail(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 删除处方医技申请计费
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int deleteMedicApplyCost(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 获取医技申请
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    List<MedicalApplyDTO> getMedicApply(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 获取医技需收费数据（过滤合管数据）
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    List<MedicalApplyDTO> getMedicApplyCost(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 获取医技数据
     * @param outptPrescribeDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<OutptCostDTO>
     */
    List<OutptCostDTO> getMedicApplyFy(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Desrciption 更新合管数据
     * @param medicalApplyDTOList
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    int updateMedicApply(List<MedicalApplyDTO> medicalApplyDTOList);

    /**
    * @Menthod updateIsFoodBorne
    * @Desrciption 更新就诊表是否食源性
    *
    * @Param
    * [outptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/3/18 20:08
    * @Return int
    **/
    int updateIsFoodBorne(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod findVisitListById
     * @Desrciption  根据此次的就只能ID 查询此人的就诊记录
     * @param  outptPrescribeDTO
     * @Author pengbo
     * @Date   2020/10/26 10:24
     * @Return outptPrescribeDTO
     **/
    List<OutptVisitDTO> findVisitListById(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @param outptDiagnoseDTO
     * @Method queryOutptDiagnose
     * @Desrciption 长沙市医保：门特病人做试算，结算操作时要传主，副诊断信息
     * @Param outptDiagnoseDTO
     * @Author fuhui
     * @Date 2021/2/3 16:33
     * @Return OutptDiagnoseDTO集合
     */
    List<OutptDiagnoseDTO> queryOutptDiagnose(OutptDiagnoseDTO outptDiagnoseDTO);

    //根据visitIds，itemIds查询出对应的费用表数据
    List<OutptCostDTO> queryOuptCost(Map map);

    //根据visitIds，itemIds查询出对应的处方明细表副表数据
    List<OutptPrescribeDetailsExtDTO> queryOuptPreDetailExt(Map map);

    // 更新费用表数据，限制用药字段
    int updateOuptCost(@Param("costDTOS") List<OutptCostDTO> costDTOS);

    // 更新处方明细表副表数据，限制用药字段
    int updateOuptPreDetailExt(@Param("detailsExtDTOS")List<OutptPrescribeDetailsExtDTO> detailsExtDTOS);

    // 根据处方ids和visitId从处方明细表副表查询出处方列表
    List<OutptPrescribeDetailsExtDTO> queryPrescribeListByIdsAndVisitId(OutptPrescribeDTO outptPrescribeDTO);

    /**
     * @Description: 查询处方费用信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/9/22 15:25
     * @Return
     */
    List<OutptCostDTO> selectCost(OutptPrescribeDTO outptPrescribeDTO);

    int updateOutptRegister(OutptVisitDTO outptVisitDTO);
    /**
     * @Meth: getCfDataOnlyOpenItem
     * @Description: 只能开检查项目 和材料
     * @Param: [baseDrugDTO]
     * @return: java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     * @Author: zhangguorui
     * @Date: 2021/12/16
     */
    List<BaseDrugDTO> getCfDataOnlyOpenItem(BaseDrugDTO baseDrugDTO);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-17 10:17
     * @Description 根据门诊就诊ids查询门诊诊断信息
     * @param outptVisitDTO
     * @return java.util.List<cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO>
     */
    List<OutptDiagnoseDTO> queryOutptDiagnoseByVisitIds(OutptVisitDTO outptVisitDTO);


    List<OutptDiagnoseDTO> queryOutptMatchDiagnose(OutptVisitDTO outptVisitDTO);

    List<BaseDrugDTO> getCfData2(BaseDrugDTO baseDrugDTO);

    /**
     * @Method checkCostIsSettle
     * @Desrciption 判断处方费用是否结算
     * @param outptPrescribeDTO
     * @Author liuliyun
     * @Date   2022/7/13 08:40
     * @Return int
     **/
    int checkCostIsSettle(OutptPrescribeDTO outptPrescribeDTO);
}

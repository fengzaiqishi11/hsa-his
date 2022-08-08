package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *@Package_name: cn.hsa.module.inpt.dao
 *@Class_name: InptAdviceDao
 *@Describe: 医嘱数据访问层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceDAO {

    /**
    * @Method queryInptAdviceById
    * @Desrciption 单个查询
    * @param inptAdviceDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:51
    * @Return cn.hsa.module.inpt.dto.InptAdviceDTO
    **/
    InptAdviceDTO getInptAdviceById(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method queryAllByLimit
    * @Desrciption 分页查询
    * @param inptAdviceDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:51
    * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceDTO>
    **/
    List<InptAdviceDTO> queryInptAdvicePage(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method insertInptAdvice
    * @Desrciption 单个新增
    * @param inptAdviceDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:51
    * @Return int
    **/
    int insertInptAdvice(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method updateInptAdvice
    * @Desrciption 单个更新
    * @param inptAdviceDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:51
    * @Return int
    **/
    int updateInptAdvice(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method updateInptAdviceBatch
    * @Desrciption 批量更新医嘱
    * @param inptAdviceDTOs
    * @Author liuqi1
    * @Date   2020/9/2 15:32
    * @Return int
    **/
    int updateInptAdviceBatch(List<InptAdviceDTO> inptAdviceDTOs);

    /**
    * @Method: updateStopInptAdviceBatch
    * @Description: 更新停嘱核收信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/23 9:19
    * @Return:
    **/
    int updateStopInptAdviceBatch(@Param("inptAdviceDTOs") List<InptAdviceDTO> inptAdviceDTOs);

    /**
     * @Method: updateStopInptAdviceBatch
     * @Description: 更新开嘱核收信息
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/23 9:19
     * @Return:
     **/
    int updateStartInptAdviceBatch(@Param("inptAdviceDTOs") List<InptAdviceDTO> inptAdviceDTOs);


    /**
     * @Method: getMedicalAdvices
     * @Description: 获取未核收医嘱列表
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 11:19
     * @Return: cn.hsa.base.PageDTO
     **/
    List<MedicalAdviceDTO> getMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO);

    /**
     * @Method: modifyMedicalAdvices
     * @Description: 医嘱拒收  更新医嘱表is_reject,reject_remark
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/9 14:16
     * @Return: java.lang.Boolean
     **/
    int modifyMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO);

    /**
    * @Method: updateInptAdvice
    * @Description: 更新医嘱表核收信息
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 9:03
    * @Return:
    **/
    int updateInptAdviceCheckInfo(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds);

    /**
     * @Method: updateInptAdviceStopCheckInfo
     * @Description: 更新停止医嘱表核收信息
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 9:03
     * @Return:
     **/
    int updateInptAdviceStopCheckInfo(MedicalAdviceDTO medicalAdviceDTO, List<String> adviceIds);

    /**
    * @Method: getTzInptAdvices
    * @Description: 根据医嘱ID查询停同类、停非同类、停自身的医嘱列表
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 10:05
    * @Return: List<InptAdviceDTO>
    **/
    List<InptAdviceDTO> getTzInptAdvices(List<String> adviceIds, String hospCode);

    /**
    * @Method: getFtlAdvices
    * @Description: 根据参数查询非同类医嘱
     * 同一个患者的医嘱，开嘱时间在此嘱之前，医嘱类别不同，不包括本身
    * @Param: List<InptAdviceDTO>
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 11:56
    * @Return:
    **/
    List<InptAdviceDTO> getFtlAdvices(InptAdviceDTO adviceDTO);

    /**
    * @Method: getTlAdvices
    * @Description: 根据参数查询同类医嘱
     * 同一个患者的医嘱，开嘱时间在此嘱之前，医嘱类别相同，不包括本身
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 11:56
    * @Return: List<InptAdviceDTO>
    **/
    List<InptAdviceDTO> getTlAdvices(InptAdviceDTO adviceDTO);

    /**
    * @Method: getInptAdviceByIds
    * @Description: 根据医嘱ID获取医嘱
    * @Param: List<InptAdviceDTO>
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 17:23
    * @Return:
    **/
    List<InptAdviceDTO> getInptAdviceByIds(String hospCode, List<String> adviceIds);


    /**
     * @Method queryInptAdviceFeeCheckPage
     * @Desrciption 医嘱费用核对分页查询
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 09:55
     * @Return cn.hsa.base.PageDTO
     **/
    List<InptAdviceDTO> queryInptAdviceFeeCheckPage(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method queryOperationNameList
    * @Param [inptAdviceDetailDTO]
    * @description    获取手术名称
    * @author marong
    * @date 2020/9/18 11:29
    * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO>
    * @throws
    */
    List<InptAdviceDetailDTO> queryOperationNameList(InptAdviceDetailDTO inptAdviceDetailDTO);

    ;/**
     * @Method queryAdviceNumByVisitId
     * @Desrciption 通过住院就诊ID查询医嘱记录条数
     * @Param
     * [inptAdviceDTO]
     * @Author liaojunjie
     * @Date   2020/9/17 10:51
     * @Return java.lang.Integer
     **/
    Integer queryAdviceNumByVisitId(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱核对 - 获取本科室住院在院人员的患者信息
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 14:14
     * @Return  java.util.Map
     **/
    List<InptVisitDTO> queryInptPatientPage(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param list 住院费用实体DTO
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:39
     * @Return int
     **/
    int updateAdviceFeeCheck(List<InptCostDTO> list);

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return List集合
     **/
    List<InptCostDTO> queryInptAdviceFeeInfoPage(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateInptAdviceBatchByIds
     * @Desrciption 根据ID批量更新医嘱表信息
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:39
     * @Return Boolean
     **/
    int updateInptAdviceBatchByIds(InptAdviceDTO inptAdviceDTO);

    /**
    * @Method: getAdviceByGroupNo
    * @Description: 根据组号,就诊ID获取医嘱列表
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/22 16:34
    * @Return:
    **/
    List<InptAdviceDTO> getAdviceByGroupNo(String visitId, Integer groupNo, String hospCode);



    List<InptAdviceDTO> queryNoMedical(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method queryAll
     * @Desrciption 列表查询
     @params [inptAdviceDTO]
      * @Author chenjun
     * @Date   2020/9/23 9:55
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
     **/
    List<InptAdviceDTO> queryAll(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院医生站就诊信息
     * @param inptVisitDTO 就诊信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    List<InptVisitDTO> queryInptVisitPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    List<InptAdviceDTO> queryInptAdviceVisitID(InptAdviceDTO inptAdviceDTO);


    /**
     * @Method insertBathInptAdvice
     * @Desrciption 保存医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int insertBathInptAdvice(List<InptAdviceDTO> inptAdviceDTO);

    /**
     * @Method insertBathInptAdviceDetail
     * @Desrciption 保存医嘱明细信息
     * @param inptAdviceDetailDTO 医嘱明细信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int insertBathInptAdviceDetail(List<InptAdviceDetailDTO> inptAdviceDetailDTO);

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int deleteInptAdvice(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method deleteInptAdviceDetail
     * @Desrciption 删除医嘱明细信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int deleteInptAdviceDetail(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医嘱信息(皮试)
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int deleteInptAdvicePs(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method deleteInptAdviceDetail
     * @Desrciption 删除医嘱明细信息(皮试)
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int deleteInptAdviceDetailPs(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int updateBatchInptAdviceStop(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int updateBatchInptAdviceplan(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method getAdviceDetail
     * @Desrciption 批量更新医嘱预停时间
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    List<BaseItemDTO> getAdviceDetail(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method checkIsCheck
     * @Desrciption 检查医嘱是否核收
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    InptAdviceDTO checkIsCheck(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取最大组号
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return int
     **/
    int getMaxGroupNo(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method getInptDiagnose
     * @Desrciption 获取诊断信息
     * @param inptVisitDTO
     * @Author zengfeng
     * @Date   2020/10/19 14:44
     * @Return List<OutptDiagnoseDTO>
     **/
    List<InptDiagnoseDTO> getInptDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @Method insertDiagnose
     * @Desrciption 新增诊断
     * @param inptDiagnoseDTODTOList
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int insertDiagnose(List<InptDiagnoseDTO> inptDiagnoseDTODTOList);

    /**
     * @Method deleteDiagnose
     * @Desrciption 新增诊断
     * @param inptDiagnoseDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    int deleteDiagnose(InptDiagnoseDTO inptDiagnoseDTO);

    /**
    * @Method: queryAdvicesToLongCost
    * @Description: 获取需要滚动长期费用的医嘱集合(长期医嘱、已核收、未停止、在院病人、拒收标志正常)
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/2 10:33
    * @Return:
    **/
    List<String> queryAdvicesToLongCost(MedicalAdviceDTO medicalAdviceDTO);

    /**
    * @Method: updateLastExeTime
    * @Description: 更新医嘱表的最近执行时间
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/2 16:50
    * @Return:
    **/
    int updateLastExeTime(@Param("medicalAdviceDTO") MedicalAdviceDTO medicalAdviceDTO, @Param("adviceIds") List<String> adviceIds);

    /**
     * @Method getAdviceData
     * @Desrciption 获取医嘱信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    BaseAdviceDTO getAdviceData(BaseDrugDTO baseDrugDTO);

    /**
     * @Method getAdviceData
     * @Desrciption 获取医嘱信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    BaseDrugDTO getBaseDrug(BaseDrugDTO baseDrugDTO);

    /**
     * @Method getBaseMaterial
     * @Desrciption 获取材料信息
     * @param baseDrugDTO
     * @Author zengfeng
     * @Date   2020/9/10 14:44
     * @Return int
     **/
    BaseMaterialDTO getBaseMaterial(BaseDrugDTO baseDrugDTO);

    /**
     * @Desrciption 检查库存
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return List<Map<String, Object>>
     */
    List<Map<String, Object>> checkStock(InptAdviceDTO inptAdviceDTO);

    /**
     * @Desrciption 检查余额
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    InptVisitDTO checkBalance(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method updateBatchAdvice
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int updateBatchAdvice(List<InptAdviceDTO> inptAdviceDTO);

    /**
     * @Method updateInptAdviceBatchSumbit
     * @Desrciption 提交
     * @param inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int updateInptAdviceBatchSumbit(InptAdviceTDTO inptAdviceTDTO);

    /**
     * @Method getExecNum
     * @Desrciption 获取执行次数
     * @param entity 医嘱信息
     * @Author liaojiguang
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    Integer getExecNum(InptAdviceDTO entity);

    /**
     * @Method getAssistDetail
     * @Desrciption 获取辅助计费明细数据
     * @Author youxianlin
     * @Date   2020/9/10 14:44
     * @Return BaseRateDTO
     **/
    List<BaseAssistCalcDetailDTO> getAssistDetail(String hospCode, String code);

    /**
    * @Method: getAdvicesByVisitId
    * @Description: 根据患者ID获取未核收的医嘱
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/19 15:29
    * @Return:
    **/
    List<InptAdviceDTO> getAdvicesByVisitId(String hospCode, String visitId);

    /**
    * @Method: updateLastExeTimeById
    * @Description: 更新医嘱表的最近执行时间
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/1 21:47
    * @Return:
    **/
    void updateLastExeTimeById(String id, Date date, String hospCode);

    /**
    * @Method: modifyLastExeTimeById
    * @Description: 还原医嘱表的最近执行时间
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/1 21:54
    * @Return:
    **/
    void modifyLastExeTimeById(String id, Date date, String hospCode);

    List<BaseAdviceDTO> queryAdvicesByVisitId(String hospCode, String visitId, List<String> adviceIds);

    /**
    * @Method: queryApplyByAdviceId
    * @Description: 根据医嘱明细ID查询申请单
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/14 16:39
    * @Return:
    **/
    MedicalApplyDTO queryApplyByAdviceId(String hospCode, String opdId, String visitId);

    /**
    * @Method: insertMedicalApply
    * @Description: 医技申请单入库
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/14 17:07
    * @Return:
    **/
    void insertMedicalApply(MedicalApplyDTO medicalApplyDTO);

    /**
    * @Method: insertMedicalApplyDetail
    * @Description: 医技申请单明细入库
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/14 17:07
    * @Return:
    **/
    void insertMedicalApplyDetail(MedicalApplyDetailDTO medicalApplyDetailDTO);

    /**
     * @Method updateBatchInptAdviceCancel
     * @Desrciption 取消医嘱
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return int
     **/
    int updateBatchInptAdviceCancel(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method: queryInptCostList
     * @Description: 获取费用信息
     * @Param:
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return:
     **/
    List<InptCostDTO> queryInptCostList(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method: getInptAdviceByIds
     * @Description: 根据医嘱ID获取医嘱
     * @Param: List<InptAdviceDTO>
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 17:23
     * @Return:
     **/
    List<InptVisitDTO> getVisitByAdviceIds(String hospCode, List<String> adviceIds);

    /**
     * @Desrciption 获取
     * @param inptAdviceDTO
     * @Author zengfeng
     * @Date   2020/10/26 14:44
     * @Return int
     */
    InptVisitDTO getInptVisit(InptAdviceDTO inptAdviceDTO);
    /**
     * @Desrciption 保存医技申请的以后回写医嘱中的医技申请单号
     * @param adviceDTO
     * @Author pengbo
     * @Date   2021/03/16 14:44
     * @Return int
     */
    void updateTechnologyNoById(InptAdviceDTO adviceDTO);

    /**
     * @Desrciption 根据IDS修改医嘱最新执行时间
     * @param inptAdviceExecDTOs
     * @Author pengbo
     * @Date   2021/03/16 14:44
     * @Return int
     */
    int updateLastExeTimeByIds(@Param("inptAdviceExecDTOs")List<InptAdviceExecDTO> inptAdviceExecDTOs);

    /**
     * @param inptVisitDTO
     * @Method queryInptDiagnose
     * @Desrciption 查询医保病人的诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/11 19:42
     * @Return
     */
    List<InptDiagnoseDTO> queryInptDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @Desrciption 根据固定条件查询需要修改 提前领药天数的医嘱
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    List<InptAdviceDTO> queryInptAdviceAdvanceTake(Map<String, Object> map);

    /**
     * @Desrciption 根据ids修改医嘱提前领药天数
     * @param inptAdviceDTOList ,days
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    void updateAdvanceDays(@Param("list") List<InptAdviceDTO>  inptAdviceDTOList,@Param("advance_days") String days);

    /**
     * @Desrciption 根据项目ID查询最合适的库位
     * @param paramMap ,days
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    List<Map<String, Object>> getStroStockById(Map<String, Object> paramMap);

    /**
     * @Desrciption 查询提前领药记录
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    List<Map<String, Object>> selectMedicineAdvance(Map<String, Object> map);

    /**
     * @Desrciption 新增提前领药记录
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    void insertMedicineAdvance(Map<String, Object> map);


    /**
     * @Desrciption 修改提前领药记录
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    void updateMedicineAdvance(Map<String, Object> map);

    List<BaseAdviceDTO> getIllnessAdviceByVisitId(@Param("list") List<InptVisitDTO> inptVisitDTOList);
    /**
     * @Desrciption 取消提前领药记录
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    Boolean canlceMedicineAdvance(Map<String, Object> map);
    /**
     * @Desrciption 新增提前领药记录
     * @param mapList
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    Boolean insertMedicineAdvanceAdvice(@Param("list") List<Map<String, Object>> mapList);
    /**
     * @Desrciption 删除提前领药记录
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    Boolean deleteMedicineAdvanceAdvice(Map<String, Object> map);

    /**
     * @Desrciption 修改提前领药医嘱天数
     * @param map
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    Boolean  updateInptAdviceAdvanceDays(Map<String, Object> map);


    /**
     * @Desrciption 修改提前领药医嘱天数,最后执行时间
     * @param inptAdviceDTOList,days
     * @Author pengbo
     * @Date   2021/05/12 14:44
     * @Return list
     */
    Boolean  updateAdvanceDaysLastExcTime(@Param("list") List<String>  inptAdviceDTOList,@Param("advance_days") String days);


    //根据visitIds，itemIds查询出对应的医嘱明细表数据
    List<InptAdviceDetailDTO> queryInptAdviceDetail(Map map);

    // 更新医嘱明细表副表数据，限制用药字段
    int updateInptAdviceDetail(@Param("detailsExtDTOS") List<InptAdviceDetailDTO> detailsExtDTOS);

    // 根据医嘱ids字符串和visitId从处方明细表副表查询出处方列表
    List<InptAdviceDetailDTO> queryAdviceByIdsAndVisitId(InptAdviceDTO inptAdviceDTO);
    /**
     * @Desrciption 根据单据类型过滤查询有效医嘱
     * @param map
     * @Author pengbo
     * @Date   2021/08/02 14:44
     * @Return list
     */
    List<InptAdviceDTO> selectAdviceByDeptAndType (Map map);

    InptAdviceDTO getLisInptAdvice(InptAdviceDTO inptAdviceDTO);

    List<InptAdviceDTO> queryLisAdvice(InptAdviceDTO inptAdviceDTO);

    /**
     * @Method queryGroupAdvice
     * @Desrciption 查询同组
     @params [inptAdviceDTO]
      * @Author liuliyun
     * @Date   2021/11/16 10：56
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>
     **/
    List<InptAdviceDTO> queryGroupAdvice(InptAdviceDTO inptAdviceDTO);

    /**
     * @Menthod: getZyAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药医嘱列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-26 11:31
     * @Return:
     **/
    List<InptAdviceDTO> getZyAdviceByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * 医嘱最近执行时间更新（最外面的更新执行时间方法（updateLastExeTime）替换成下面这个） - 解决周期费用生成问题
     * @param medicalAdviceDTO
     * @param adviceIdCostTime
     */
    void newUpdateLastExeTime(@Param("medicalAdviceDTO")MedicalAdviceDTO medicalAdviceDTO, @Param("adviceMap")Map<String, Date> adviceIdCostTime);


    /**
     * 根据医嘱ID，医嘱组号进行查询（防止漏掉同组核收的数据）
     * @param inptAdviceDTOList
     * @return
     */
    List<InptAdviceDTO> findGroupAdvice(@Param("listAdvice") List<InptAdviceDTO> inptAdviceDTOList);

    /**
     * 批量修改医嘱核收跟停嘱核收信息
     * @param adviceDTOList
     */
    void updateStopAndCheckInfo(List<InptAdviceDTO> adviceDTOList);

    List<BaseAdviceDTO> getLongIllnessAdviceByAdviceId(@Param("longAdviceDTOList") List<InptAdviceDTO> inptAdviceDTOList);

    List<BaseAdviceDTO> getShortIllnessAdviceByAdviceId(@Param("shortAdviceDTOList") List<InptAdviceDTO> inptAdviceDTOList);

    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询未提交医嘱信息
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-01 09:58
     * @Return: List<InptVisitDTO>
     **/
    List<InptVisitDTO> queryUnsubmitAdviceList(Map map);

    /**
     * @Menthod: queryRejectAdviceList
     * @Desrciption: 查询已拒收医嘱信息
     * @Param: medicalAdviceDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-02 10:40
     * @Return: List<InptVisitDTO>
     **/
    List<InptVisitDTO> queryRejectAdviceList(Map param);

    /**
     * @Menthod: querySubmitAdviceList
     * @Desrciption: 查询提交医嘱信息
     * @Param: medicalAdviceDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-02 14:44
     * @Return: List<InptVisitDTO>
     **/
    List<InptVisitDTO> querySubmitAdviceList(Map param);

    List<BaseAdviceDetailDTO> getAdviceDataDetail(BaseDrugDTO baseDrugDTO);

    /**
     * 根据就诊ID查询对应的LIS医嘱，PACS医嘱
     * @param inptAdviceDTO
     * @return
     */
    List<InptAdviceDTO> queryLisOrPacsAdvice(InptAdviceDTO inptAdviceDTO);

    List<InptVisitDTO> queryInptVisitPageNoMerge(InptVisitDTO inptVisitDTO);
    /**
     * @Menthod: getCFAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药、精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    List<InptAdviceDTO> getDMAdviceByVisitId(InptVisitDTO inptVisitDTO);
}

package cn.hsa.module.insure.module.dao;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualVisit.dao
 * @Class_name: InsureIndividualVisitDAO
 * @Describe(描述): 医保就诊信息表DAO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 19:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureIndividualVisitDAO {

    /**
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键获取医保就诊信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/18 19:47
     * @Return cn.hsa.module.insure.insureIndividualVisit.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO selectByPrimaryKey(@Param("id") String id);

    /**
     * @Menthod insert
     * @Desrciption 保存医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 19:49
     * @Return int 受影响行数
     */
    int insert(InsureIndividualVisitDO insureIndividualVisitDO);

    /**
     * @Menthod insertSelective
     * @Desrciption  保存医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 19:49
     * @Return int 受影响行数
     */
    int insertSelective(InsureIndividualVisitDO insureIndividualVisitDO);

    /**
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 19:50
     * @Return int 受影响行数
     */
    int updateByPrimaryKey(InsureIndividualVisitDO insureIndividualVisitDO);

    /**
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 19:51
     * @Return int 受影响行数
     */
    int updateByPrimaryKeySelective(InsureIndividualVisitDO insureIndividualVisitDO);

    /**
     * @Menthod deleteById
     * @Desrciption 根据主键id删除医保就诊信息
     * @param id 主键id
     * @Author Ou·Mr
     * @Date 2020/11/18 19:52
     * @Return int 受影响行数
     */
    int deleteById(@Param("id") String id);

    /**
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除医保就诊信息
     * @param ids ids
     * @Author Ou·Mr
     * @Date 2020/11/18 19:53
     * @Return int 受影响行数
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询医保就诊信息
     * @param insureIndividualVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/18 19:55
     * @Return java.util.List<cn.hsa.module.insure.insureIndividualVisit.dto.InsureIndividualVisitDTO>
     */
    List<InsureIndividualVisitDTO> findByCondition(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Menthod findByCondition
     * @Desrciption  查询医保就诊信息
     * @param insureIndividualVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/18 19:55
     * @Return java.util.List<cn.hsa.module.insure.insureIndividualVisit.dto.InsureIndividualVisitDTO>
     */
    InsureIndividualVisitDTO getByVisitNo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Menthod getInsureIndividualVisitById
     * @Desrciption 查询医保信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 19:59
     * @Return cn.hsa.module.insure.insureIndividualVisit.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO getInsureIndividualVisitById(Map param);

    /**
     * @Menthod deleteByMap
     * @Desrciption 根据就诊id删除
     * @param visitId 就诊id
     * @Author Ou·Mr
     * @Date 2020/11/19 19:34
     * @Return int 受影响行数
     */
    int deleteByVisitId(@Param("visitId")String visitId);
    /**
     * @Menthod selectInsureIndividualVisit
     * @Desrciption 根据就诊id,医院编码查询医保病人的就诊信息
     * @param insureIndividualVisitDTO 医保病就诊信息数据传输对象
     * @Author fuhui
     * @Date 2021/1/20 19:34
     * @Return int 受影响行数
     */
    InsureIndividualVisitDTO selectInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    List<Map<String, Object>> getOutptCostAndInsure(OutptVisitDTO outptVisitDTO);

    Map<String, Object> selectCode(Map<String, Object> map);

    Boolean  deleteInsureVisitById(Map<String, Object> map);

    /**
     * @Menthod selectOutptRegisterById
     * @Desrciption 获取患者挂号信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/15 16:16
     * @return: map 挂号信息
     */
    Map<String,Object> selectOutptRegisterById(Map<String,String> param);

    /**
     * @Menthod selectSysUserByid
     * @Desrciption 根据id查询用户信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/16 11:09
     * @return: map
     */
    Map<String,Object> selectSysUserByid(Map<String,String> param);

    /**
     * @param insureIndividualVisitDTO
     * @Method queryPage
     * @Desrciption 分页查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/26 9:20
     * @Return
     */
    List<InsureIndividualVisitDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param inptVisitDTO
     * @Method updateInsureInidivdual
     * @Desrciption 修改医保病人信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/20 22:41
     * @Return
     */
    Boolean updateInsureInidivdual(InptVisitDTO inptVisitDTO);

    InsureIndividualVisitDTO queryInsureIndividualVisitByIds(Map<String,Object> insureVisitParam);

    List<InsureIndividualVisitDTO> queryAllInsureRegister(Map<String,String> selectMap);

    /**
     * @Method getMedicalRegNo
     * @Desrciption  获取未结算的就医登记号
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/30 14:38
     * @Return
    **/
    String getMedicalRegNo(Map<String, Object> map);

    /**
     * @param insureIndividualVisitDTO
     * @Method selectInsureInfo
     * @Desrciption 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/7/30 15:32
     * @Return
     */
    InsureIndividualVisitDTO selectInsureInfo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method selectIsHalfSettleInfo
     * @Desrciption  查询中途结算的就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/3 19:54
     * @Return
    **/
    InsureIndividualVisitDTO selectIsHalfSettleInfo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method updateInsureSettleId
     * @Desrciption  取消结算更新医保就诊表的结算id
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/4 10:18
     * @Return
     **/

    Boolean updateInsureSettleId(Map<String, Object> settleMap);
    
    /**
     * @Method queryLasterCounter
     * @Desrciption  查询中途结算次数
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/8/4 16:19
     * @Return 
    **/
    Integer  queryLasterCounter(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method queryInsureIndividualVisit
     * @Desrciption  查询医保就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/5 10:33
     * @Return
    **/
    InsureIndividualVisitDTO queryInsureIndividualVisit(Map<String, Object> dataMap);

    /**
     * @param insureUnifiedPayParam
     * @Method queryAllInsureIndiviualVisit
     * @Desrciption 根据就诊id 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 19:59
     * @Return
     */
    List<InsureIndividualVisitDTO> queryAllInsureIndiviualVisit(Map<String, Object> insureUnifiedPayParam);

    /**
     * @param insureUnifiedPayParam
     * @Method selectMaxAndMinRegisterTime
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 20:56
     * @Return
     */
    InsureIndividualVisitDTO selectMaxAndMinRegisterTime(Map<String, Object> insureUnifiedPayParam);

    SysUserDTO queryDoctorPracCertiNo(OutptVisitDTO outptVisitDTO);

    /**
     * @Method queryInsureVisitInfo
     * @Desrciption  通过就诊id，医院编码查询医保登记信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/8 19:34
     * @Return
    **/
    InsureIndividualVisitDTO queryInsureVisitInfo(Map<String, Object> map);

    /**
     * @Method updateInptPatientCode
     * @Desrciption  修改病人类型
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 11:04
     * @Return
    **/
    void updateInptPatientCode(Map<String, Object> map);

    void updateInptSettlePatientCode(Map<String, Object> map);

    /**
     * @Method updateOutptPatientCode
     * @Desrciption  修正门诊医保病人类型
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/9 9:02
     * @Return
    **/
    void updateOutptPatientCode(Map<String, Object> map);


    void updateOutptSettlePatientCode(Map<String, Object> map);

    /**
     * @param insureIndividualVisitDTO
     * @Method 查询住院医保病人类型
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/10/9 9:42
     * @Return
     */
    List<InsureIndividualVisitDTO> queryInptInsurePatient(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param insureIndividualVisitDTO
     * @Method 查询门诊医保病人类型
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/10/9 9:42
     * @Return
     */
    List<InsureIndividualVisitDTO> queryOutptInsurePatient(InsureIndividualVisitDTO insureIndividualVisitDTO);

    void insertCommonLog(InsureFunctionLogDO insureFunctionDO);

    /**
     * @Method querySelectInsureSumInfo
     * @Desrciption  查询个人累计信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/25 17:28
     * @Return
    **/
    List<Map<String, Object>> querySelectInsureSumInfo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method selectOutDiagnose
     * @Desrciption  查询出院诊断名称
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/26 20:07
     * @Return
    **/
    String selectOutDiagnose(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method deletePatientSumInfo
     * @Desrciption  删除个人累计信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/29 10:58
     * @Return
    **/
    void deletePatientSumInfo(Map<String, Object> map);

    /**
     * @Method insertPatientSumInfo
     * @Desrciption  费用传输时,保存个人累计信息
     * @Param resultDataMap
     *
     * @Author fuhui
     * @Date   2021/10/29 10:58
     * @Return
    **/
    void insertPatientSumInfo(@Param("resultDataMap") List<Map<String, Object>> resultDataMap);
}


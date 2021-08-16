package cn.hsa.module.insure.module.dao;


import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptCostDO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InsureIndividualCostDAO {

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    List<InptCostDTO> queryPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptCostByVisitId
     * @Desrciption
     * @Param
     *      *      1.hospCode: 医院编码
     *      *      2.insureOrgCode 参保机构编码
     *      *      3.visited 就诊id
     * @Author fuhui
     * @Date   2020/11/10 16:44
     * @Return
     **/
    List<InptCostDTO> queryInptCostByVisitId(InptVisitDTO inptVisitDTO);



    /**
     * @Method insertInsureCost()
     * @Desrciption  把住院费用表里面的数据插入到医保就诊费用表里面去
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/11 11:43
     * @Return
     **/
    int insertInsureCost(@Param("costDTOList") List<InsureIndividualCostDO> costDTOList);

    void updateTransmitFlag(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptPatientPage（）
     * @Desrciption  查询医保住院病人的信息
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 11:00
     * @Return
     **/
    List<InptVisitDTO> queryInptPatientPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method deleteIndivdidualCost()
     * @Desrciption  删除除insure_individual_cost更新失败的数据
     * @Param errorCostIdList失败的费用明细id
     *
     * @Author fuhui
     * @Date   2020/11/18 15:30
     * @Return
    **/
    void deleteIndivdidualCost(@Param("errorCostIdList") List<String> errorCostIdList);

    /**
     * @Menthod editInsureCostByCostIDS
     * @Desrciption 根据费用id修改 医保费用信息
     * @param insureIndividualCostDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 15:49 
     * @Return int 受影响行数
     */
    int editInsureCostByCostIDS(InsureIndividualCostDTO insureIndividualCostDTO);
    
    /**
     * @Menthod batchInsertInsureCost
     * @Desrciption 批量增加医保费用信息
     * @param insureIndividualCostDOList 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/13 16:35 
     * @Return int 受影响行数
     */
    int batchInsertInsureCost(List<InsureIndividualCostDO> insureIndividualCostDOList);

    /**
     * @Menthod queryInsureCostByVisit
     * @Desrciption 查询医保费用未传输、已传输费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/21 21:17
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryInsureCostByVisit(Map<String,String> param);


    /**
     * @Method: queryLastestOrderNo()
     * @Desription: 获取最新的顺序号
     * @Pramas: inpVisitDTO：id:就诊id  hosp_code医院编码
     * @Author: fuhui
       @Email: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: 循环号
     */
    String queryLastestOrderNo(InptVisitDTO inpVisitDTO);

    /**
     * @Menthod delInsureCost
     * @Desrciption 根据就诊id删除费用信息
     * @param visitId 就诊id
     * @Author Ou·Mr
     * @Date 2020/12/22 17:29
     * @Return int 受影响行数
     */
    int delInsureCost(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method queryInptCostList
     * @Desrciption  查询患者的总费用
     * @Param costParam
     *
     * @Author fuhui
     * @Date   2021/2/26 14:54
     * @Return
    **/
    String queryInptCostList(Map<String, Object> costParam);

    /**
     * @Method selectInsureIndividualCost
     * @Desrciption  根据就诊id ,医院编码查询费用上传到医保的信息
     * @Param insureIndividualVisitDTO
     *
     * @Author fuhui
     * @Date   2021/2/24 14:57
     * @Return
    **/
    List<InsureIndividualCostDTO> selectInsureIndividualCost(InsureIndividualVisitDTO insureIndividualVisitDTO);

    List<InsureIndividualCostDTO> queryInsureCost(InsureIndividualVisitDTO insureIndividualVisitDTO);

    Boolean updateInsureCost(@Param("list") List<InsureIndividualCostDTO>costDTOList);

    /**
     * @param insureCostParam
     * @Method queryOutptInsureCostByVisit
     * @Desrciption 查询门诊未传输费用信息
     * @Param insureCostParam
     * @Author fuhui
     * @Date 2021/3/4 9:32
     * @Return
     */
    List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, String> insureCostParam);

    /**
     * @param insureVisitParam
     * @Method deleteOutptInsureCost
     * @Desrciption 根据就诊id和医院编码删除医保门诊费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:23
     * @Return
     */
    void deleteOutptInsureCost(Map<String, Object> insureVisitParam);

    /**
     * @param insureCostParam
     * @Method selectCostIsTransmit
     * @Desrciption 查询门诊医保已经上传的费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:40
     * @Return
     */
    List<InsureIndividualCostDTO> selectCostIsTransmit(Map<String, Object> insureCostParam);

    /**
     * @param insureIndividualCostDTO
     * @Method updateInsureSettleCost
     * @Desrciption 统一支付平台：门诊结算成功以后，更新医保费用的结算id
     * @Param insureIndividualCostDTO
     * @Author fuhui
     * @Date 2021/4/15 15:42
     * @Return
     */
    Boolean updateInsureSettleCost(InsureIndividualCostDTO insureIndividualCostDTO);


    void updateFeeUploadResult(@Param("individualCostDTOList") List<InsureIndividualCostDTO> individualCostDTOList);


    List<InptCostDTO> queryBackInptFee(InsureIndividualVisitDTO insureIndividualVisitDTO);


    List<Map<String,Object>> queryInsureInptCost(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method updateCostInsureStatus
     * @Desrciption  医保费用传输成功以后 更新传输标识
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/21 11:52
     * @Return
    **/
    void updateCostInsureStatus(@Param("insureIndividualCostDOList") List<Map<String,Object>> list);

    /**
     * @param map
     * @Method selectLastCostInfo
     * @Desrciption 查询最新的费用批次号
     * @Param insureIndividualVisitDTO
     * @Author fuhui
     * @Date 2021/5/26 14:39
     * @Return
     */
    String selectLastCostInfo(Map<String,Object> map);



    /**
     * @Method selectIsSetlleFee
     * @Desrciption  查询已经结算完成的费用明细集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/2 15:35
     * @Return
    **/
    List<Map<String, Object>> selectIsSetlleFee(Map<String, Object> map);

    /**
     * @Method updateInptCost
     * @Desrciption  修改医保费用表数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/8 15:50
     * @Return
    **/
    void updateInptCost(@Param("insureIndividualCostDOList") List<Map<String, Object>> mapList);
    
    /**
     * @Method 查询住院的费用
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/6/8 16:12 
     * @Return 
    **/
    List<InptCostDO> queryInptFeeCost(Map<String, Object> map);

    /**
     * @param inptVisitDTO
     * @Method queryUnMatchPage
     * @Desrciption 查询没有匹配的费用数据集合
     * @Param
     * @Author fuhui
     * @Date 2021/6/20 9:55
     * @Return
     */
    List<InptCostDTO> queryUnMatchPage(InptVisitDTO inptVisitDTO);

    /**
     * @param selectList
     * @param inptVisitDTO
     * @Method queryAllPage
     * @Desrciption 查询已经匹配的费用数据集合
     * @Param
     * @Author lioajiguang
     * @Date 2021/6/21 9:55
     * @Return
     */
    List<InptCostDTO> queryAllPage(InptVisitDTO inptVisitDTO);

    /**
     * @param selectList
     * @Method queryAllPage
     * @Desrciption 查询没有匹配的费用数据集合
     * @Param
     * @Author lioajiguang
     * @Date 2021/6/21 9:55
     * @Return
     */
    List<InptCostDTO> queryAllQueryUnMatchPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method selectLastFeedSn
     * @Desrciption  查询最新的费用流水号
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/16 11:11
     * @Return
    **/
    String selectLastFeedSn(Map<String, Object> map);
    /**
     * @param map
     * @Method updateLimitUserFlag
     * @Desrciption 住院医生站开完医嘱保存，填写报销标识以后。修改这些报销标识
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 9:20
     * @Return
     */
    Boolean updateLimitUserFlag(Map<String, Object> map);

    /**
     * @param inptVisitDTO
     * @Method queryInptCostPage
     * @Desrciption 根据就诊id 查询住院费用明细数据
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 13:49
     * @Return
     */
    List<InptCostDTO> queryInptCostPage(InptVisitDTO inptVisitDTO);
    
    /**
     * @Method 查询中途结算时：选择的费用区间
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/7/28 9:31
     * @Return 
    **/
    InsureIndividualCostDTO selectHalfTransmitFee(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method batchInsertCost
     * @Desrciption  批量更新费用数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/31 18:42
     * @Return
    **/
    void batchInsertCost(@Param("individualCostDTOList") List<InsureIndividualCostDTO> individualCostDTOList);

    /**
     * @Method queryLasterCounter
     * @Desrciption  获取中途结算次数
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/2 17:53
     * @Return
    **/
    Integer queryLasterCounter(   InsureIndividualVisitDTO insureIndividualVisitDTO );

    /**
     * @Method queryIsSettleFee
     * @Desrciption  查询已经结算的费用明细信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/3 11:15
     * @Return
    **/
    InptCostDTO queryIsSettleFee(Map<String, Object> map);

    /**
     * @Method queryIsTransmitFee
     * @Desrciption  查询已经上传的费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/3 18:25
     * @Return
    **/
    InsureIndividualCostDTO queryIsTransmitFee(Map<String, Object> map);

    /**
     * @param inptVisitDTO
     * @Method deleteInptHisCost
     * @Desrciption 删除his本地费用
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 10:59
     * @Return
     */
    Boolean deleteInptHisCost(InptVisitDTO inptVisitDTO);
    
    /**
     * @Method queryIsTransmitInptFee
     * @Desrciption  查询his已经上传的费用数据
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/8/9 17:22
     * @Return 
    **/
    List<InsureIndividualCostDTO> queryIsTransmitInptFee(InptVisitDTO inptVisitDTO);
}

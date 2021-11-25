package cn.hsa.module.insure.module.bo;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.util.MapUtils;

import java.util.List;
import java.util.Map;

public interface InsureIndividualCostBO {

    /**
     * @Method saveCostByRemoteInpt
     * @Param [map]
     * @description   保存异地就医费用信息
     * @author marong
     * @date 2020/11/4 20:52
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    Boolean saveCostByRemoteInpt(InsureIndividualCostDTO insureIndividualCostDTO);

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    PageDTO queryPage(InptVisitDTO inptVisitDTO);


    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人费用传输
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/10 15:18
     * @Return
     **/
    Map<String,Object> saveFeeTransmit(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInptPatientPage（）
     * @Desrciption  查询医保住院病人的信息
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 11:00
     * @Return
     **/
    PageDTO queryInptPatientPage(InptVisitDTO inptVisitDTO);

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
     * @Menthod queryInsureCostByVisit
     * @Desrciption 查询住院费用传输，未传输、已传输费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/22 11:40 
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryInsureCostByVisit(Map<String,String> param);

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
     * @param insureCostParam
     * @Method queryOutptInsureCostByVisit
     * @Desrciption 查询门诊未传输费用信息
     * @Param insureCostParam
     * @Author fuhui
     * @Date 2021/3/4 9:32
     * @Return
     */
    List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, Object> insureCostParam);

    /**
     * @Method insertInsureCost()
     * @Desrciption  把住院费用表里面的数据插入到医保就诊费用表里面去
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/11 11:43
     * @Return
     **/
    void insertInsureCost(List<InsureIndividualCostDO> insureIndividualCostDOList);

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
     * @Method updateInsureCost
     * @Desrciption  批量更新费用传输以后，医保统一支付平台的反参
     * @Param data
     *
     * @Author fuhui
     * @Date   2021/3/4 13:49
     * @Return
     **/
    Boolean updateInsureCost(List<InsureIndividualCostDTO>costDTOList);

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

    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人批量费用传输
     * @Param inptVisitDTO
     *
     * @Author 廖继广
     * @Date   2021/04/13 19:51
     * @Return
     **/
    Boolean saveFeesTransmit(InptVisitDTO inptVisitDTO);

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
     * @param inptVisitDTO
     * @Method queryUnMatchPage
     * @Desrciption 查询没有匹配的费用数据集合
     * @Param
     * @Author fuhui
     * @Date 2021/6/20 9:55
     * @Return
     */
    PageDTO queryUnMatchPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method insurCostTransmissionJob
     * @Desrciption  医保定时费用上传
     * @Param
     *
     * @Author liaojiguang
     * @Date   2021/7/14 14:58
     * @Return
     **/
    Map<String,String> insurCostTransmissionJob(InptVisitDTO inptVisitDTO);
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
    PageDTO queryInptCostPage( InptVisitDTO inptVisitDTO );

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
     * @param insureIndividualVisitDTO
     * @Method selectInsureIndividualCost
     * @Desrciption 查询已经保存到医保费用表的数据
     * @Param insureIndividualVisitDTO：医保患者个人就诊信息
     * @Author fuhui
     * @Date 2021/8/16 8:55
     * @Return
     */
    List<InsureIndividualCostDTO> selectInsureIndividualCost(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param insureIndividualVisitDTO
     * @Method delInsureCost
     * @Desrciption 删除医保本地费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/8/16 9:15
     * @Return
     */
    Integer delInsureCost(InsureIndividualVisitDTO insureIndividualVisitDTO);
}

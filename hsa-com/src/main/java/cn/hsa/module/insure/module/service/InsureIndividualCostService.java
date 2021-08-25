package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureIndividualCostService {

    /**
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     * @Method saveCostByRemoteInpt
     * @Param [map]
     * @description 住院保存异地就医费用信息
     * @author marong
     * @date 2020/11/4 20:52
     */
    @PostMapping("/service/insure/insureIndividualCost/saveCostByRemoteInpt")
    WrapperResponse<Boolean> saveCostByRemoteInpt(Map map);


    /**
     * @Method queryPage()
     * @Desrciption 分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @GetMapping("/service/insure/insureIndividualCost/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);


    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人费用传输
     * @Param
     * @Author fuhui
     * @Date 2020/11/10 15:18
     * @Return
     **/
    @PostMapping("/service/insure/insureIndividualCost/feeTransmit")
    WrapperResponse<Map<String,Object>> saveFeeTransmit(Map map);

    /**
     * @Method queryInptPatientPage（）
     * @Desrciption 查询医保住院病人的信息
     * @Param
     * @Author fuhui
     * @Date 2020/11/18 11:00
     * @Return
     **/
    @GetMapping("/service/insure/insureIndividualCost/queryInptPatientPage")
    WrapperResponse<PageDTO> queryInptPatientPage(Map map);

    /**
     * @param param 请求参数
     * @Menthod editInsureCostByCostIDS
     * @Desrciption 根据费用id修改 医保费用信息
     * @Author Ou·Mr
     * @Date 2020/12/3 15:49
     * @Return int 受影响行数
     */
    @PutMapping("/service/insure/insureIndividualCost/editInsureCostByCostIDS")
    int editInsureCostByCostIDS(Map<String, Object> param);

    /**
     * @param param 请求参数
     * @Menthod queryInsureCostByVisit
     * @Desrciption 查询住院费用传输，未传输、已传输费用信息
     * @Author Ou·Mr
     * @Date 2020/12/22 11:40
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @GetMapping("/service/insure/insureIndividualCost/queryInsureCostByVisit")
    List<Map<String, Object>> queryInsureCostByVisit(Map<String, String> param);

    /**
     * @Method queryOutptInsureCostByVisit
     * @Desrciption 查询门诊未传输费用信息
     * @Param insureCostParam
     * @Author fuhui
     * @Date 2021/3/4 9:32
     * @Return
     **/
    @GetMapping("/service/insure/insureIndividualCost/queryOutptInsureCostByVisit")
    List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, String> insureCostParam);

    /**
     * @Method insertInsureCost()
     * @Desrciption 把住院或者门诊产生的费用数据插入到医保就诊费用表里面去
     * @Param
     * @Author fuhui
     * @Date 2020/11/11 11:43
     * @Return
     **/
    void insertInsureCost(Map<String, Object> outptCostMap);

    /**
     * @Method deleteOutptInsureCost
     * @Desrciption 根据就诊id和医院编码删除医保门诊费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:23
     * @Return
     **/
    void deleteOutptInsureCost(Map<String, Object> insureVisitParam);

    /**
     * @Method selectCostIsTransmit
     * @Desrciption 查询门诊医保已经上传的费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:40
     * @Return
     **/
    List<InsureIndividualCostDTO> selectCostIsTransmit(Map<String, Object> insureCostParam);

    /**
     * @Method updateInsureCost
     * @Desrciption 批量更新费用传输以后，医保统一支付平台的反参
     * @Param data
     * @Author fuhui
     * @Date 2021/3/4 13:49
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureCost(Map<String, Object> data);

    /**
     * @Method updateInsureSettleCost
     * @Desrciption  统一支付平台：门诊结算成功以后，更新医保费用的结算id
     * @Param insureIndividualCostDTO
     *
     * @Author fuhui
     * @Date   2021/4/15 15:42
     * @Return
    **/
    WrapperResponse<Boolean>  updateInsureSettleCost(Map<String,Object> map);

    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人批量费用传输
     * @Param inptVisitDTO
     *
     * @Author 廖继广
     * @Date   2021/04/13 19:51
     * @Return
     **/
    WrapperResponse<Boolean> saveFeesTransmit(Map<String, Object> map);

    /**
     * @Method map
     * @Desrciption  查询最新的费用批次号
     * @Param insureIndividualVisitDTO
     *
     * @Author fuhui
     * @Date   2021/5/26 14:39
     * @Return
    **/
    WrapperResponse<String> selectLastCostInfo(Map<String,Object> map);

    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询没有匹配的费用数据集合
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/20 9:55
     * @Return
     **/
    WrapperResponse<PageDTO> queryUnMatchPage(Map<String, Object> param);

    /**
     * @Method insurCostTransmissionJob
     * @Desrciption  医保定时费用上传
     * @Param
     *
     * @Author liaojiguang
     * @Date   2021/7/14 14:58
     * @Return
     **/
    Map<String,String> insurCostTransmissionJob(Map map);
    /**
     * @Method updateLimitUserFlag
     * @Desrciption  住院医生站开完医嘱保存，填写报销标识以后。修改这些报销标识
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/20 9:20
     * @Return
     **/
    WrapperResponse<Boolean> updateLimitUserFlag(Map<String, Object> map);

    /**
     * @Method queryInptCostPage
     * @Desrciption  根据就诊id 查询住院费用明细数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/20 13:49
     * @Return
     **/
    WrapperResponse<PageDTO> queryInptCostPage(Map<String, Object> map);

    /**
     * @Method deleteInptHisCost
     * @Desrciption  删除his本地费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/9 10:59
     * @Return
     **/
    WrapperResponse<Boolean> deleteInptHisCost(Map<String, Object> map);

    /**
     * @Method selectInsureIndividualCost
     * @Desrciption  查询已经保存到医保费用表的数据
     * @Param insureIndividualVisitDTO：医保患者个人就诊信息
     *
     * @Author fuhui
     * @Date   2021/8/16 8:55
     * @Return
    **/
    WrapperResponse<List<InsureIndividualCostDTO> > selectInsureIndividualCost(Map<String, Object> insureVisitParam);

    /**
     * @Method delInsureCost
     * @Desrciption  删除医保本地费用数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/16 9:15
     * @Return
    **/
    WrapperResponse<Integer> delInsureCost(Map<String, Object> insureVisitParam);
}

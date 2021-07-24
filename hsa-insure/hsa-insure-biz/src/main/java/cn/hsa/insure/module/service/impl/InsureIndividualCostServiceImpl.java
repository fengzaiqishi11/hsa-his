package cn.hsa.insure.module.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.bo.InsureIndividualCostBO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualCostService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/insure/insureIndividualCost")
@Service("insureIndividualCostService_provider")
public class InsureIndividualCostServiceImpl implements InsureIndividualCostService {

    @Resource
    private InsureIndividualCostBO insureIndividualCostBO;

    /**
     * @Method saveCostByRemoteInpt
     * @Param [map]
     * @description   保存异地就医费用信息
     * @author marong
     * @date 2020/11/4 20:52
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @throws
     */
    @Override
    public WrapperResponse<Boolean> saveCostByRemoteInpt(Map map) {
        return WrapperResponse.success(insureIndividualCostBO.saveCostByRemoteInpt(MapUtils.get(map,"insureIndividualCostDTO")));
    }

    /**
     * @Method queryPage()
     * @Desrciption  分页查询医保住院费用传输数据
     * @Param insureIndividualCostDTO数据传输对象
     *
     * @Author fuhui
     * @Date   2020/11/5 10:58
     * @Return insureIndividualCostDTO分页数据传输对象
     **/
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return WrapperResponse.success(insureIndividualCostBO.queryPage(inptVisitDTO));
    }


    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人费用传输
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/10 15:18
     * @Return
     *
     * @return*/
    @Override
    @HsafRestPath(value = "/feeTransmit", method = RequestMethod.GET)
    public WrapperResponse<Map<String,Object>> saveFeeTransmit(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return WrapperResponse.success(insureIndividualCostBO.saveFeeTransmit(inptVisitDTO));
    }


    /**
     * @Method queryInptPatientPage（）
     * @Desrciption  查询医保住院病人的信息
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/18 11:00
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryInptPatientPage(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return WrapperResponse.success(insureIndividualCostBO.queryInptPatientPage(inptVisitDTO));
    }

    /**
     * @Menthod editInsureCostByCostIDS
     * @Desrciption 根据费用id修改 医保费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/3 15:49
     * @Return int 受影响行数
     */
    @Override
    public int editInsureCostByCostIDS(Map<String, Object> param) {
        return insureIndividualCostBO.editInsureCostByCostIDS(MapUtils.get(param,"insureIndividualCostDTO"));
    }

    /**
     * @Menthod queryInsureCostByVisit
     * @Desrciption 查询住院费用传输，未传输、已传输费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/22 11:40
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> queryInsureCostByVisit(Map<String, String> param) {
        return insureIndividualCostBO.queryInsureCostByVisit(param);
    }

    /**
     * @param insureCostParam
     * @Method selectCostIsTransmit
     * @Desrciption 查询门诊医保已经上传的费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:40
     * @Return
     */
    @Override
    public List<InsureIndividualCostDTO> selectCostIsTransmit(Map<String, Object> insureCostParam) {
        return insureIndividualCostBO.selectCostIsTransmit(insureCostParam);
    }

    /**
     * @param insureCostParam
     * @Method queryOutptInsureCostByVisit
     * @Desrciption 查询门诊未传输费用信息
     * @Param insureCostParam
     * @Author fuhui
     * @Date 2021/3/4 9:32
     * @Return
     */
    @Override
    public List<Map<String, Object>> queryOutptInsureCostByVisit(Map<String, String> insureCostParam) {
        return insureIndividualCostBO.queryOutptInsureCostByVisit(insureCostParam);
    }

    /**
     * @Method insertInsureCost()
     * @Desrciption  把住院费用表里面的数据插入到医保就诊费用表里面去
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/11 11:43
     * @Return
     **/
    @Override
    public void insertInsureCost(Map<String, Object> outptCostMap) {
        insureIndividualCostBO.insertInsureCost(MapUtils.get(outptCostMap, "insureIndividualCostDOList"));
    }

    /**
     * @param insureVisitParam
     * @Method deleteOutptInsureCost
     * @Desrciption 根据就诊id和医院编码删除医保门诊费用数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 10:23
     * @Return
     */
    @Override
    public void deleteOutptInsureCost(Map<String, Object> insureVisitParam) {
        insureIndividualCostBO.deleteOutptInsureCost(insureVisitParam);
    }

    /**
     * @param data
     * @Method updateInsureCost
     * @Desrciption 批量更新费用传输以后，医保统一支付平台的反参
     * @Param data
     * @Author fuhui
     * @Date 2021/3/4 13:49
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureCost(Map<String, Object> data) {
        return WrapperResponse.success(insureIndividualCostBO.updateInsureCost(MapUtils.get(data,"costDTOList")));
    }

    /**
     * @param map
     * @Method updateInsureSettleCost
     * @Desrciption 统一支付平台：门诊结算成功以后，更新医保费用的结算id
     * @Param insureIndividualCostDTO
     * @Author fuhui
     * @Date 2021/4/15 15:42
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureSettleCost(Map<String,Object> map) {
        InsureIndividualCostDTO insureIndividualCostDTO = MapUtils.get(map,"insureIndividualCostDTO");
        return WrapperResponse.success(insureIndividualCostBO.updateInsureSettleCost(insureIndividualCostDTO));
    }

    /**
     * @Method feeTransmit()
     * @Desrciption 住院病人批量费用传输
     * @Param inptVisitDTO
     *
     * @Author 廖继广
     * @Date   2021/04/13 19:51
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> saveFeesTransmit(Map<String, Object> map) {
        return WrapperResponse.success(insureIndividualCostBO.saveFeesTransmit(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @param map
     * @Method selectLastCostInfo
     * @Desrciption 查询最新的费用批次号
     * @Param insureIndividualVisitDTO
     * @Author fuhui
     * @Date 2021/5/26 14:39
     * @Return
     */
    @Override
    public WrapperResponse<String> selectLastCostInfo(Map<String,Object> map) {
        return WrapperResponse.success(insureIndividualCostBO.selectLastCostInfo(map));
    }

    /**
     * @param param
     * @Method queryUnMatchPage
     * @Desrciption 查询没有匹配的费用数据集合
     * @Param
     * @Author fuhui
     * @Date 2021/6/20 9:55
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryUnMatchPage(Map<String, Object> param) {
        InptVisitDTO inptVisitDTO = MapUtils.get(param, "inptVisitDTO");
        return WrapperResponse.success(insureIndividualCostBO.queryUnMatchPage(inptVisitDTO));
    }

    /**
     * @Method insurCostTransmissionJob
     * @Desrciption  医保定时费用上传
     * @Param
     *
     * @Author liaojiguang
     * @Date   2021/7/14 14:58
     * @Return
     **/
    @Override
    public  Map<String,String> insurCostTransmissionJob(Map map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return insureIndividualCostBO.insurCostTransmissionJob(inptVisitDTO);
    }
    /**
     * @param map
     * @Method updateLimitUserFlag
     * @Desrciption 住院医生站开完医嘱保存，填写报销标识以后。修改这些报销标识
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 9:20
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateLimitUserFlag(Map<String, Object> map) {

        return WrapperResponse.success(insureIndividualCostBO.updateLimitUserFlag(map));
    }

    /**
     * @param map
     * @Method queryInptCostPage
     * @Desrciption 根据就诊id 查询住院费用明细数据
     * @Param
     * @Author fuhui
     * @Date 2021/7/20 13:49
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInptCostPage(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map,"inptVisitDTO");
        return WrapperResponse.success(insureIndividualCostBO.queryInptCostPage(inptVisitDTO));
    }
}

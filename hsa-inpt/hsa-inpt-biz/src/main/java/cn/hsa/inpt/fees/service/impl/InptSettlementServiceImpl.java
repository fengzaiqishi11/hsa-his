package cn.hsa.inpt.fees.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.fees.bo.InptSettlementBO;
import cn.hsa.module.inpt.fees.service.InptSettlementService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.fees.service.impl
 * @Class_name: InptSettlementServiceImpl
 * @Describe(描述): 住院结算ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/inpt/inptSettlement")
@Service("inptSettlementService_provider")
public class InptSettlementServiceImpl extends HsafService implements InptSettlementService {

    @Resource
    private InptSettlementBO inptSettlementBO;

    /**
     * @Menthod queryInptvisitByPage
     * @Desrciption 查询可结算的患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/25 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryInptvisitByPage(Map param) {
        return inptSettlementBO.queryInptvisitByPage(MapUtils.get(param,"inptVisitDTO"));
    }

    /**
     * @Menthod queryInptCostByList
     * @Desrciption 查询患者住院费用信息、预交金信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryInptCostByList(Map param) {
        return inptSettlementBO.queryInptCostByList(param);
    }

    /**
     * @Menthod saveCostTrial
     * @Desrciption 住院试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse saveCostTrial(Map param) {
        return inptSettlementBO.saveCostTrial(MapUtils.get(param,"inptVisitDTO"));
    }

    /**
     * @Menthod saveSettle
     * @Desrciption 住院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse saveSettle(Map param) {
        return inptSettlementBO.saveSettle(param);
    }

    /**
     * @Method insureUnifiedPayInpt
     * @Desrciption 住院预结算
     * @Param
     *
     * @Author 曹亮
     * @Date   2021/7/14 11:17
     * @Return
     **/
    public WrapperResponse insureUnifiedPayInpt(Map param) {
        return inptSettlementBO.insureUnifiedPayInpt(param);
    }

    /**
     * @Method: querySettle
     * @Description: 获取住院发票数据
     * @Param: [params]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/8 14:27
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @Override
    public WrapperResponse<Map<String,Object>> querySettle(Map<String, Object> params) {
        return WrapperResponse.success(inptSettlementBO.querySettle(MapUtils.get(params,"settleId"),MapUtils.get(params,"hospCode"),MapUtils.get(params,"userName")));
    }

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryDiagnose(Map map) {
        return WrapperResponse.success(inptSettlementBO.queryDiagnose(MapUtils.get(map,"inptVisitDTO")));
    }

    /**
     * @param map
     * @Method editDischargeInpt
     * @Desrciption 医保出院办理
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> editDischargeInpt(Map<String, Object> map) {
        return WrapperResponse.success(inptSettlementBO.editDischargeInpt(map));
    }

    /**
     * @param map
     * @Method editCancelDischargeInpt
     * @Desrciption 医保出院办理撤销
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> editCancelDischargeInpt(Map<String, Object> map) {
        return WrapperResponse.success(inptSettlementBO.editCancelDischargeInpt(map));
    }

    @Override
    public WrapperResponse saveBabyCostTrial(Map param) {
        return inptSettlementBO.saveBabyCostTrial(MapUtils.get(param,"inptVisitDTO"));
    }

    @Override
    public WrapperResponse saveBabySettle(Map param) {
        return inptSettlementBO.saveBabySettle(param);
    }
}

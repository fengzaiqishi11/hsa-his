package cn.hsa.outpt.outptvisit.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.visit.bo.OutptVisitBO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptvisit.service.impl
 * @Class_name: OutptVisitServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/3 16:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/visit")
@Slf4j
@Service("outptVisitService_provider")
public class OutptVisitServiceImpl extends HsafService implements OutptVisitService {

    /**
     * 门诊就诊查询业务逻辑层接口
     */
    @Resource
    private OutptVisitBO outptVisitBO;


    /**
     * @Method queryVisitRecords
     * @Desrciption
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/3 22:08
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryVisitRecords(Map map) {
        return WrapperResponse.success(outptVisitBO.queryVisitRecords(MapUtils.get(map,"outptVisitDTO")));
    }

    /**
     * @Method updateTranInCode
     * @Desrciption 更新转入院代码
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/25 9:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateTranInCode(Map map) {
        return WrapperResponse.success(outptVisitBO.updateTranInCode(MapUtils.get(map,"outptVisitDTO")));
    }

    /**
     * @Menthod queryByVisitID
     * @Desrciption 根据ID查询门诊患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/17 21:54
     * @Return cn.hsa.module.outpt.visit.dto.OutptVisitDTO
     */
    @Override
    public OutptVisitDTO queryByVisitID(Map<String, String> param) {
        return outptVisitBO.queryByVisitID(param);
    }

    /**
     * @Method updateOutptVisit
     * @Desrciption  走统一支付平台时，登记挂号成功以后，修改病人类型
     * @Param outptVisitMap
     *
     * @Author fuhui
     * @Date   2021/3/8 15:25
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateOutptVisit(Map<String, Object> outptVisitMap) {
        return WrapperResponse.success(outptVisitBO.updateOutptVisit(MapUtils.get(outptVisitMap,"outptVisitDTO")));
    }

    /**
     * @param map
     * @Method selectOutptVisitById
     * @Desrciption 根据就诊id查询门诊患者
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:27
     * @Return
     */
    @Override
    public WrapperResponse<OutptVisitDTO> selectOutptVisitById(Map<String, Object> map) {
        return WrapperResponse.success(outptVisitBO.selectOutptVisitById(map));
    }

    /**
     * @param map
     * @Method selectOutptSettleById
     * @Desrciption 根据就诊id查询门诊结算信息
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:34
     * @Return
     */
    @Override
    public WrapperResponse<OutptSettleDTO> selectOutptSettleById(Map<String, Object> map) {
        return WrapperResponse.success(outptVisitBO.selectOutptSettleById(map));
    }

    /**
     * @param map
     * @Method updateOutptAcctPay
     * @Desrciption 修改
     * @Param
     * @Author fuhui
     * @Date 2021/12/15 11:16
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateOutptAcctPay(Map<String,Object> map) {
        OutptSettleDO outptSettleDO = MapUtils.get(map,"outptSettleDO");
        return WrapperResponse.success(outptVisitBO.updateOutptAcctPay(outptSettleDO));
    }

    @Override
    public List<OutptVisitDTO> queryOutptVisitSelfFeePatient(Map<String, String> param) {
        return outptVisitBO.queryOutptVisitSelfFeePatient(param);
    }

    @Override
    public List<OutptCostDTO> queryOutptCostByvisitIds(Map<String, Object> reqMap) {
        return outptVisitBO.queryOutptCostByvisitIds(reqMap);
    }

    @Override
    public WrapperResponse<Boolean> updateUplod(Map<String, Object> map) {
        return WrapperResponse.success(outptVisitBO.updateUplod(MapUtils.get(map,"outptVisitDTO")));
    }

    @Override
    public WrapperResponse<Boolean> updateOutptVisitUploadFlag(Map<String, Object> map) {
        return WrapperResponse.success(outptVisitBO.updateOutptVisitUploadFlag(MapUtils.get(map,"outptVisitDTO")));
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     * @method selectOutptVisitByCertNo
     * @author wang'qiao
     * @date 2022/6/21 21:29
     * @description 根据证件类型和证件号码 查询信息
     **/
    @Override
    public WrapperResponse<OutptVisitDTO> selectOutptVisitByCertNo(Map<String, Object> map) {
        return WrapperResponse.success(outptVisitBO.selectOutptVisitByCertNo(MapUtils.get(map, "outptVisitDTO")));
    }

    @Override
    public WrapperResponse<OutptVisitDTO> queryInptVisitInfo(Map map) {
        return WrapperResponse.success(outptVisitBO.queryInptVisitInfo(MapUtils.get(map, "outptVisitDTO")));
    }

    @Override
    public WrapperResponse<InsureIndividualVisitDTO> queryInsureVisitInfo(Map map) {
        return WrapperResponse.success(outptVisitBO.queryInsureVisitInfo(MapUtils.get(map, "outptVisitDTO")));
    }
}

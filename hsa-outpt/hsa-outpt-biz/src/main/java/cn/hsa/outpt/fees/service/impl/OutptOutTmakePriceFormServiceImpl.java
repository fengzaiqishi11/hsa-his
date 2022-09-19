package cn.hsa.outpt.fees.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.bo.OutptOutTmakePriceFormBO;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.service.OutptOutTmakePriceFormService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.fees.service.impl
 * @Class_name: OutptTmakePriceFormServiceImpl
 * @Describe(描述):门诊退费ServiceImpl
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/09/06 10:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/outTmakePriceForm")
@Service("outptOutTmakePriceFormService_provider")
public class OutptOutTmakePriceFormServiceImpl extends HsafService implements OutptOutTmakePriceFormService {

    @Resource
    private OutptOutTmakePriceFormBO outptOutTmakePriceFormBO;

    /**
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/05 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateOutptOutFee(Map param) {
        // 获取个人信息
        OutptVisitDTO outptVisitDTO = MapUtils.get(param,"outptVisitDTO");

        // 获取结算信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(param,"outptSettleDTO");

        // 支付信息
        List<OutptPayDTO> outptPayDOList = MapUtils.get(param,"outptPayDOList");
        return outptOutTmakePriceFormBO.updateOutptOutFee(outptVisitDTO,outptSettleDTO,outptPayDOList);
    }

    /**
     * @Menthod queryOutChargePage
     * @Desrciption 门诊退费 - 查询门诊已结算信息
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutChargePage(Map param) {
        OutptSettleDTO outptSettleDTO = MapUtils.get(param,"outptSettleDTO");
        return outptOutTmakePriceFormBO.queryOutChargePage(outptSettleDTO);
    }

    /**
     * @Menthod queryOutptPrescribes
     * @Desrciption 门诊退费 - 查询门诊处方信息
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptPrescribes(Map param) {
        return outptOutTmakePriceFormBO.queryOutptPrescribes(param);
    }

    /**
     * @Menthod queryOutptPrescribe
     * @Desrciption 查询门诊处方详细信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptPrescribe(Map param) {
        return outptOutTmakePriceFormBO.queryOutptPrescribe(param);
    }

    /**
     * @Menthod getDiagnoseInfo
     * @Desrciption 查询门诊诊断信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getDiagnoseInfo(Map param) {
        return outptOutTmakePriceFormBO.getDiagnoseInfo(param);
    }

    /**
     * @Menthod getInvoiceInfo
     * @Desrciption 获取发票信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getInvoiceInfo(Map param) {
        return outptOutTmakePriceFormBO.getInvoiceInfo(param);
    }

    /**
     * @Menthod updateOutptOPharInfo
     * @Desrciption 门诊退费 - 判断是否已经完成发药或退药
     * @param params
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateOutptOPharInfo(Map params) {
        // 获取个人信息
        OutptVisitDTO outptVisitDTO = MapUtils.get(params,"outptVisitDTO");

        // 获取结算信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(params,"outptSettleDTO");
        return outptOutTmakePriceFormBO.updateOutptOPharInfo(outptVisitDTO,outptSettleDTO);
    }

    /**
     * @param map
     * @Method updateOutptRegister
     * @Desrciption 医保统一支付平台：门诊挂号取消
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 8:37
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateOutptRegister(Map<String, Object> map) {
        return WrapperResponse.success(outptOutTmakePriceFormBO.updateOutptRegister(map));
    }

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门急诊诊疗记录【4301】
     * @Param id-就诊id
     *
     * @Author luoyong
     * @Date   2021/8/20 8:37
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> addOutptVisitRecordUpload(Map<String, Object> map) {
        return WrapperResponse.success(outptOutTmakePriceFormBO.addOutptVisitRecordUpload(map));
    }

    /**
     * @Menthod: addOperAndRescue
     * @Desrciption: 统一支付平台-急诊留观手术及抢救信息【4302】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-23 13:50
     * @Return:
     **/
    @Override
    public WrapperResponse<Boolean> addOperAndRescue(Map<String, Object> map) {
        return WrapperResponse.success(outptOutTmakePriceFormBO.addOperAndRescue(map));
    }

    /**
     * @param params
     * @Menthod: getPayInfoByParams
     * @Desrciption: 获取支付信息
     * @Param: outptPayDTO
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2021-10-13 13:44
     * @Return:
     */
    @Override
    public WrapperResponse<OutptPayDTO> getPayInfoByParams(Map<String,Object> params) {
        OutptPayDTO outptPayDTO = MapUtils.get(params,"outptPayDTO");
        return WrapperResponse.success(outptOutTmakePriceFormBO.getPayInfoByParams(outptPayDTO));
    }

    @Override
    public WrapperResponse<Boolean> saveBackCostWithOutpt(Map<String, Object> map) {
        return WrapperResponse.success(outptOutTmakePriceFormBO.saveBackCostWithOutpt(map));
    }


    /**
     * @Menthod updateOutptOnlinePayOutFee
     * @Desrciption 门诊诊间支付退费
     * @param param
     * @Author liuliyun
     * @Date 2022/9/19 10:09
     * @email liyun.liu@powersi.com
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateOutptOnlinePayOutFee(Map param) {
        // 获取个人信息
        OutptVisitDTO outptVisitDTO = MapUtils.get(param,"outptVisitDTO");

        // 获取结算信息
        OutptSettleDTO outptSettleDTO = MapUtils.get(param,"outptSettleDTO");

        // 支付信息
        List<OutptPayDTO> outptPayDOList = MapUtils.get(param,"outptPayDOList");
        return outptOutTmakePriceFormBO.updateOutptOnlinePayOutFee(outptVisitDTO,outptSettleDTO,outptPayDOList);
    }

}

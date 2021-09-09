package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedPrescripBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPrescripService;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.*;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayRestBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedPrescripServiceImpl
 * @Description: 电子处方服务
 * @Author: LiaoJiGuang
 * @Email: jiguang.liao@qq.com
 * @Date: 2021/9/6 15:14
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPrescrip")
@Service("insureUnifiedPrescripService_provider")
public class InsureUnifiedPrescripServiceImpl extends HsafService implements InsureUnifiedPrescripService {

    @Resource
    private InsureUnifiedPrescripBO insureUnifiedPrescripBO;

    /**
     * @param map
     * @Method updatePrescripUpload_7101
     * @Desrciption 电子处方上传
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/3 17:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updatePrescripUpload_7101(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPrescripBO.updatePrescripUpload_7101(map));
    }

    /**
     * @param map
     * @Method updatePrescripAudit_7102
     * @Desrciption 处方审核结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updatePrescripAudit_7102(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPrescripBO.updatePrescripAudit_7102(map));
    }

    /**
     * @param map
     * @Method updatePrescripAudit_7103
     * @Desrciption 处方购药结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updatePrescripBuyMedicine_7103(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPrescripBO.updatePrescripBuyMedicine_7103(map));
    }

    /**
     * @param map
     * @Method updatePrescripRevoke_7104
     * @Desrciption 电子处方撤销
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updatePrescripRevoke_7104(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPrescripBO.updatePrescripRevoke_7104(map));
    }

    /**
     * @param map
     * @Method updatePrescripPay_7105
     * @Desrciption 处方支付状态同步
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updatePrescripPay_7105(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPrescripBO.updatePrescripPay_7105(map));
    }
}

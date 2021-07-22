package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedEmrUploadBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedEmrUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedEmrUploadServiceImpl
 * @Description: 电子处方，住院病案首页 ，电子病历
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/29 8:54
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedEmrUpload")
@Service("insureUnifiedEmrUploadService_provider")
public class InsureUnifiedEmrUploadServiceImpl extends HsafService implements InsureUnifiedEmrUploadService {

    @Resource
    InsureUnifiedEmrUploadBO insureUnifiedEmrUploadBO;

    /**
     * @param map
     * @Method updateInsureUnifiedMri
     * @Desrciption 医保统一支付平台：住院病案首页信息上传
     * @Param map
     * @Author fuhui
     * @Date 2021/4/29 10:03
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureUnifiedMri(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedEmrUploadBO.updateInsureUnifiedMri(map));
    }

    /**
     * @param map
     * @Method updateInsureUnifiedPrescrib
     * @Desrciption 医保统一支付平台：电子处方上传
     * @Param map
     * @Author fuhui
     * @Date 2021/4/29 10:03
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureUnifiedPrescrib(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedEmrUploadBO.updateInsureUnifiedPrescrib(map));
    }
}

package cn.hsa.module.insure.inpt.service;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedEmrUploadService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/27 10:43
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface  InsureUnifiedEmrUploadService {

    /**
     * @Method updateInsureUnifiedMri
     * @Desrciption  医保统一支付平台：住院病案首页信息上传
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/29 10:03
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureUnifiedMri(Map<String, Object> map);

    /**
     * @Method updateInsureUnifiedPrescrib
     * @Desrciption  医保统一支付平台：电子处方上传
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/29 10:03
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureUnifiedPrescrib(Map<String, Object> map);
}

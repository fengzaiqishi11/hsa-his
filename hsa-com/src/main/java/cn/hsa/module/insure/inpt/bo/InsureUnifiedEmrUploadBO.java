package cn.hsa.module.insure.inpt.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureUnifiedEmrUploadBO
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/27 10:42
 * @Company: 创智和宇
 **/
public interface InsureUnifiedEmrUploadBO {

    /**
     * @param map
     * @Method updateInsureUnifiedMri
     * @Desrciption 医保统一支付平台：住院病案首页信息上传
     * @Param map
     * @Author fuhui
     * @Date 2021/4/29 10:03
     * @Return
     */
    Boolean updateInsureUnifiedMri(Map<String, Object> map);

    /**
     * @param map
     * @Method updateInsureUnifiedPrescrib
     * @Desrciption 医保统一支付平台：电子处方上传
     * @Param map
     * @Author fuhui
     * @Date 2021/4/29 10:03
     * @Return
     */
    Boolean updateInsureUnifiedPrescrib(Map<String, Object> map);

    /**
     * @param map
     * @Method updateInsureUnifiedEmr
     * @Desrciption 医保统一支付平台：电子病历上传
     * @Param map
     * @Author liuliyun
     * @Date 2021/8/21 11:20
     * @Return
     */
    Boolean updateInsureUnifiedEmr(Map<String, Object> map);
}

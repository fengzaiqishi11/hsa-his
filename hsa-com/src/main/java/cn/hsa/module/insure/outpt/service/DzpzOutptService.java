package cn.hsa.module.insure.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: OutptService
 * @Describe(描述): 门诊医保统一开放调用 Service 接口
 * @Author: xingyu.xie
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface DzpzOutptService {


    /**
     * @Menthod uploadFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("serive/insure/outpt/ecQuery")
    WrapperResponse ecQuery(Map<String,Object> param);

    /**
     * @Menthod hosQueryInsu
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("serive/insure/outpt/hosQueryInsu")
    WrapperResponse hosQueryInsu(Map<String,Object> param);


    /**
     * @Menthod uploadFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("serive/insure/outpt/backFee")
    WrapperResponse backFee(Map<String,Object> param);

    /**
     * @Menthod uploadFee
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author xingyu.xie
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PostMapping("/saveUpLoadFeeResult")
    WrapperResponse saveUpLoadFeeResult(Map<String,Object> param);
}

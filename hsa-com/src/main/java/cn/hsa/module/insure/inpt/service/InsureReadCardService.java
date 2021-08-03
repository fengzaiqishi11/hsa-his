package cn.hsa.module.insure.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureReadCardService
 * @Description: 读卡服务
 * @Author: LiaoJiGuang
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/7/29 15:07
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureReadCardService {

    /**
     * @Method getReadIdCard
     * @Desrciption 读身份证校验密码
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/7/29 15:11
     * @Return
     **/
    WrapperResponse<Map<String, Object>> getReadIdCard(Map<String, Object> map);

    /**
     * @Method getReadIdCard
     * @Desrciption 读身份证修改密码
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/7/30 15:11
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateReadIdCard(Map<String, Object> map);
}

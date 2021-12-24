package cn.hsa.module.insure.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @class_name: InsureUnifiedUniversityService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/13 16:05
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedUniversityService {

    /**
     * @Method insertUniversityInsure
     * @Desrciption  大学生医保单独结算
     * @Param map：封装包含就诊id：visitId  settleId:结算id
     *
     * @Author fuhui
     * @Date   2021/12/13 16:13
     * @Return
     **/
    WrapperResponse<Boolean> insertUniversityInsure(Map<String, Object> map);
}

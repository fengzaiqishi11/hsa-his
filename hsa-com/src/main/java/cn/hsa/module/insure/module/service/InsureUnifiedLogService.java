package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.util.MapUtils;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedLogService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 13:34
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedLogService {

    /**
     * @Method queryPage
     * @Desrciption  分页查询所有调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
     **/
    WrapperResponse<PageDTO> queryPage(Map<String,Object> map);

    /**
     * @Method insertInsureFunctionLog
     * @Desrciption  保存调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
     **/
    WrapperResponse<Boolean> insertInsureFunctionLog(Map<String,Object> map);

    /**
     * @Method selectInsureLogs
     * @Desrciption  his日志转医保日志入参
     * @Param
     *
     * @Author fuhui
     * @Date   2022/1/4 9:37
     * @Return
     **/
    WrapperResponse<String> selectInsureLogs(Map<String, Object> map);
}

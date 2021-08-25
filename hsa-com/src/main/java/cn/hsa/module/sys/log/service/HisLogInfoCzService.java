package cn.hsa.module.sys.log.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
* @Package_name: cn.hsa.module.base.code.bo
* @class_name: HisLogInfoCzService
* @Description:  日志服务service
* @Author: yzb
 * @Date: 2020/11/25 11:31
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-sys")
public interface HisLogInfoCzService {
    /***
     * @Description 保存操作日志
     * @param map
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sys/log/saveLogCz")
    WrapperResponse<Boolean> saveLogCz(Map map);


}

package cn.hsa.module.sys.log.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
* @Package_name: cn.hsa.module.base.code.bo
* @class_name: HisLogInfoCzService
* @Description:  异常日志服务service
* @Author: yzb
 * @Date: 2020/11/25 11:31
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-sys")
public interface HisLogInfoYcService {
    /***
     * @Description 保存异常信息
     * @param map
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/sys/log/saveLogCz")
    WrapperResponse<Boolean> saveLogYc(Map map);


    /**
    * @Method queryLogInfo
    * @Desrciption 查询日志信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/9 14:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/service/sys/log/queryLogInfo")
    WrapperResponse<PageDTO> queryLogInfo(Map map);

    /**
    * @Method queryLogFileInfo
    * @Desrciption 查询日志文件信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/11 15:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/service/sys/log/queryLogFileInfo")
    WrapperResponse<PageDTO> queryLogFileInfo(Map map);


}

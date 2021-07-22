package cn.hsa.module.sys.log.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name
 * @class_nameSysLogDAO
 * @Description 日志收集DAO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/11/30 10:32
 * @Company 创智和宇
 **/
@FeignClient(value = "hsa-sys-log")
public interface SysLogService {

    /**
    * @Method: insertMenuButton
    * @Description: 日志入库
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/30 10:32
    * @Return:
    **/
    @PostMapping("/service/sys/log/insertLog")
    WrapperResponse<Boolean> insertLog(Map map);
    
}

package cn.hsa.module.center.log.service;

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
@FeignClient(value = "hsa-center-log")
public interface CenterPasswordModifyLogService {


    /**
     *  系统日志记录器
     * @param map 参数 CenterPasswordModifyLogDo 必传系统日志参数
     * @return
     */
    WrapperResponse<Boolean> insertCenterPasswordModifyLog(Map map);

    /**
     *  系统操作日志记录
     * @param map
     * @return
     */
    WrapperResponse<Boolean> insertCenterOperationLog(Map map);

}

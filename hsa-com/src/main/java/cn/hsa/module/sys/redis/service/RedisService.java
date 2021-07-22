package cn.hsa.module.sys.redis.service;

/**
 * @Package_ame: cn.hsa.module.sys.redis.service
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.redis.service
 * @Class_name: SysParameterservice
 * @Describe:  参数信息维护service接口层（提供给dubbo调用）
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-sys")
public interface RedisService {

    /**
     * @Menthod getData()
     * @Desrciption 获取基础数据
     * @Param
     *  map
     * @Author zengfeng
     * @Date   2021/1/15  10:48
     * @Return cn.hsa.module.sys.redis.service
     **/
    @PostMapping("/service/sys/rdis/getData")
    WrapperResponse<List<Map<String, Object>>> getData(Map map);
}

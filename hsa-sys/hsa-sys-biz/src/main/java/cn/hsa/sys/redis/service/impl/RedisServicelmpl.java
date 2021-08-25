package cn.hsa.sys.redis.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.redis.bo.RedisBO;
import cn.hsa.module.sys.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.sys.redis.service.impl
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sys/redis")
@Slf4j
@Service("redisService_provider")
public class RedisServicelmpl extends HsafService implements RedisService {

    /**
     * 业务逻辑接口
     */
    @Resource
    private RedisBO redisBO;

    @Override
    public WrapperResponse<List<Map<String, Object>>> getData(Map map) {
        return WrapperResponse.success(redisBO.getData(map));
    }
}

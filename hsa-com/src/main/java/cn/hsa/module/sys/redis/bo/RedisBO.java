package cn.hsa.module.sys.redis.bo;

import cn.hsa.module.sys.redis.dao.RedisDAO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.sys.redis.bo
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/15  10:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface RedisBO {

    /**
     * @Menthod getData()
     * @Desrciption 获取基础数据
     * @Param map
     * @Author zengfeng
     * @Date   2021/1/15  10:48
     * @Return cn.hsa.module.sys.redis.service
     **/
    List<Map<String, Object>> getData(Map map);
}

package cn.hsa.filter;

import cn.hsa.util.Constants;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 *  基于guava 布隆过滤器算法实现的 redis布隆过滤器
 *  参考自：https://www.appblog.cn/2021/04/27/SpringBoot%E5%9C%A8Redis%E4%B8%AD%E4%BD%BF%E7%94%A8BloomFilter%E5%B8%83%E9%9A%86%E8%BF%87%E6%BB%A4%E5%99%A8%E6%9C%BA%E5%88%B6/#%E9%A1%B9%E7%9B%AE%E4%BB%A3%E7%A0%81
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/8 16:21
 */
@Component
public class RedisBloomFilter {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final Map<String,Boolean> USER_READ_MSG_CONTAINER = new ConcurrentHashMap<String, Boolean>(100);
    /**
     *  根据给定的布隆过滤器添加值
     * @param bloomFilterHelper 布隆过滤器计算值
     * @param key 布隆过滤器名称
     * @param value 需要存入过滤器的值
     * @param <T> 泛型
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            System.out.println("key : " + key + " " + "value : " + i);
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            System.out.println("key : " + key + " " + "value : " + i);
            if (Boolean.FALSE.equals(redisTemplate.opsForValue().getBit(key, i))) {
                return false;
            }
        }
        return true;
    }

    /**
     *  将值存入已读容器列表中
     * @param hashKey
     * @param value
     */
    public void addByHashContainer(String hashKey,Integer value) {
       USER_READ_MSG_CONTAINER.put(hashKey,1 == value ? Boolean.TRUE : Boolean.FALSE);
       redisTemplate.boundHashOps(Constants.REDISKEY.USER_READ_MSG_KEY).put(hashKey,1 == value);
    }

    /**
     *  判断消息是否已读
     * @param hashKey 消息key
     * @param value 值
     * @return
     */
    public boolean includeByHashContainer(String hashKey,Integer value){
        boolean exists = USER_READ_MSG_CONTAINER.containsKey(hashKey);
        if(!exists){
            exists = redisTemplate.boundHashOps(Constants.REDISKEY.USER_READ_MSG_KEY).hasKey(hashKey);
            // 在redis中存在说明该值已经存储过了，系统重启后需要重新加载进入内存
            if(exists) {
                USER_READ_MSG_CONTAINER.put(hashKey, 1 == value ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        return exists;
    }
}

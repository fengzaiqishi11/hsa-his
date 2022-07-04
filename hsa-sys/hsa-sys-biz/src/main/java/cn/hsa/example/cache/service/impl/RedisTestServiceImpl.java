//package cn.hsa.example.cache.service.impl;
//
//import cn.hsa.example.cache.constant.Constants;
//import cn.hsa.example.cache.entity.UserDO;
//import cn.hsa.example.cache.service.RedisTestService;
//import cn.hsa.example.cache.service.UserService;
//import cn.hsa.hsaf.core.cache.HsafCacheManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.Cache;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.SecureRandom;
//
//@Service
//public class RedisTestServiceImpl implements RedisTestService {
//
//    private static final Logger logger = LoggerFactory.getLogger(RedisTestServiceImpl.class);
//    private final static String K1 = "tmplk1";
//
//    private @Resource(name = "hsafRedisTemplate") RedisTemplate<String, Serializable> redisTemplate;
//
//    private @Resource HsafCacheManager hsafCacheManager;
//    private @Resource UserService userService;
//
//
//
//    @Override
//    public String tmplSetTest(String v) throws NoSuchProviderException, NoSuchAlgorithmException {
//
//
//        StringBuffer buffer = new StringBuffer("");
//        Object obj = redisTemplate.opsForValue().get(K1);
//        buffer.append("执行set之前get:");
//        buffer.append("key = "+K1+",value = "+ (obj==null?null:obj));
//
//        Integer id = 123;
//        if(v == null){
//            SecureRandom random= SecureRandom.getInstance("SHA1PRNG","RUN");
//            v = "张三"+random.nextInt(100);
//        }
//        UserDO user = new UserDO(id, v);
//        redisTemplate.opsForValue().set(K1, user);
//        UserDO u = (UserDO) redisTemplate.opsForValue().get(K1);
//        buffer.append("  ");
//        buffer.append(",执行set,再次get:");
//        buffer.append("key = "+K1+",value = "+ (u==null?null:u));
//        return buffer.toString();
//    }
//
//    @Override
//    public String annotationSetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException {
//        if(id == null){
//            SecureRandom random= SecureRandom.getInstance("SHA1PRNG","RUN");
//            id = "value-"+ random.nextInt(100);
//        }
//        String name = userService.getName(id);
//        return name;
//    }
//
//
//    @Override
//    public Serializable annotationGetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException {
//        Serializable value = redisTemplate.opsForValue().get(UserService.ANT_PREFIX+id);
//        return value;
//    }
//
//
//    @Override
//    public String interTest() {
//
//        StringBuffer buffer = new StringBuffer();
//
//        String k = "7894";
//        String v = "hello" + k;
//        hsafCacheManager.getCache(Constants.CACHE_NAME).put(k, v);
//        buffer.append("通过CacheManager 设设置：k=").append(k).append(",v=").append(v);
//
//        Cache.ValueWrapper valueWrapper = hsafCacheManager.getCache(Constants.CACHE_NAME).get(k);
//        buffer.append(" ").append("通过CacheManager 注解方法读取:k=").append(k).append(",v=").append(valueWrapper.get());
//
//
//        // 使用redisTemplate 进行读取
//        Object name4Template = redisTemplate.opsForValue().get(k);
//
//        buffer.append(" ").append("通过redisTemplate获取:k=").append(k).append(",v=").append(name4Template);
//        return buffer.toString();
//    }
//}
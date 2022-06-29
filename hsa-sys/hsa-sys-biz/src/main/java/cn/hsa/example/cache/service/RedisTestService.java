//package cn.hsa.example.cache.service;
//
//import java.io.Serializable;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//
//public interface RedisTestService {
//
//    /**
//     * redisTemplate API 使用方式
//     */
//    public String tmplSetTest(String v) throws NoSuchProviderException, NoSuchAlgorithmException;
//
//
//    /**
//     * 方法注解使用方式
//     * @param id
//     * @return
//     * @throws NoSuchProviderException
//     * @throws NoSuchAlgorithmException
//     */
//    public String annotationSetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException;
//
//    /**
//     * 方法注解使用方式- 用redisTemplate验证
//     * @param id
//     * @return
//     * @throws NoSuchProviderException
//     * @throws NoSuchAlgorithmException
//     */
//    public Serializable annotationGetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException;
//
//    /**
//     * 互操作测试(cacheManager设置值、获取值后、用RedisTemplate读取)
//     */
//    public String interTest();
//
//}

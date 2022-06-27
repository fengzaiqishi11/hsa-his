package cn.hsa.example.cache.controller;


import cn.hsa.example.cache.service.RedisTestService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RestController
@RequestMapping(value = "/web/redis")
@Slf4j
public class RedisController {
    @Autowired private RedisTestService redisTestService;

    @GetMapping("/tmpl/set")
    public WrapperResponse<String> tmplSetTest(String v) throws NoSuchProviderException, NoSuchAlgorithmException {
        String result = redisTestService.tmplSetTest(v);
        return WrapperResponse.success(result);
    }

    /**
     * 方法注解-通过给定key设定一个值
     * @param id
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/ant/set")
    public WrapperResponse<String> antSetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException {
        String result = redisTestService.annotationSetTest(id);
        return WrapperResponse.success(result);
    }

    /**
     * 通过给定key获取此前设置的值
     * @param id
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/ant/get")
    public WrapperResponse<String> antGetTest(String id) throws NoSuchProviderException, NoSuchAlgorithmException {
        String result = redisTestService.annotationSetTest(id);
        return WrapperResponse.success(result);
    }


    @GetMapping("/inter")
    public WrapperResponse<String> interTest() {
        String result = redisTestService.interTest();
        return WrapperResponse.success(result);
    }
}

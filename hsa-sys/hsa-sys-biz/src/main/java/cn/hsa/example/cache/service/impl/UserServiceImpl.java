package cn.hsa.example.cache.service.impl;

import cn.hsa.example.cache.constant.Constants;
import cn.hsa.example.cache.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Cacheable(cacheNames = Constants.CACHE_NAME, key = "#id")
    @Override
    public String getName(String id) {
        return ANT_PREFIX + id;
    }

    @Override
    @CacheEvict(cacheNames = Constants.CACHE_NAME, key = "#id")
    public void cacheDemo(String id, String name) {

    }
}

package cn.hsa.center.cache.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.cache.bo.CenterCacheBO;
import cn.hsa.module.center.cache.service.CacheService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("sysCodeService_provider")
public class CacheServiceImpl extends HsafService implements CacheService {

    @Resource
    private RedisUtils redisUtils;
    /**
     *  中心端缓存操作业务层
     */
    @Resource
    private CenterCacheBO centerCacheBO;

    private final String patternChar = "*";
    /***
     *  根据医院编码刷新redis缓存中的码表数据
     * @param map 参数 hospCode为必传参数
     * @return 是否成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public WrapperResponse<Boolean> refreshCodeDetailsCacheByHospCode(Map<String,String> map) {

        String hospCode = (String) Optional.ofNullable(MapUtils.get(map, "hospCode")).orElseThrow(()-> new AppException("必传医院编码不能为空!"));

        String codeCacheRedisKey = StringUtils.createKey(hospCode, Constants.REDISKEY.CODEDETAIL);
         redisUtils.del(codeCacheRedisKey); // 更新之前先删除缓存
         Map codeDetailMap = centerCacheBO.getByHospCode(hospCode);
         if(codeDetailMap.isEmpty()){
             log.warn("更新医院编码为【{}】的缓存失败,具体失败原因请查看日志",hospCode);
             return WrapperResponse.success(Boolean.FALSE);
         }
         redisUtils.hmset(codeCacheRedisKey,codeDetailMap);
        return WrapperResponse.success(Boolean.TRUE);
    }

    @Override
    public WrapperResponse<Boolean> refreshCenterHospitalDatasource() {
        return centerCacheBO.refreshCenterHospitalDatasource();
    }

    /**
     * 获取redis缓存数据
     *
     * @param params 缓存key值名
     * @return
     */
    @Override
    public WrapperResponse<Set<Map<String, String>>> getRedisCacheFilteredKey(Map<String, String> params) {
        String rKeyName = MapUtils.get(params,"rKeyName");
        if(rKeyName.contains("*")){
            throw new AppException("页面参数包含非法字符！");
        }
        Set<String> results = redisUtils.keys(rKeyName+patternChar);
        Set<Map<String, String>> responseData = new HashSet<>();
        results.stream().filter(key -> !key.contains("spring")).forEach(k1 -> {
            Map<String,String> map = new HashMap<>();
            map.put("rKeyName",k1);
            responseData.add(map);
        });
        return WrapperResponse.success(responseData);
    }

    /**
     * 根据redis 获取缓存数据
     *
     * @param params 缓存key值名
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse<Object> getRedisCacheDataByKey(Map<String, String> params) {
        String rKeyName = MapUtils.get(params,"rKeyName");
        String retMsg = "暂时只支持value类型为String,Hash的数据查看";
        // 获取value值类型为String 的缓存值
        if(0 == DataType.STRING.compareTo(redisUtils.type(rKeyName))){
            return WrapperResponse.success(redisUtils.get(rKeyName));
        }else if(0 == DataType.HASH.compareTo(redisUtils.type(rKeyName))){
            Map<Object,Object> map = redisUtils.hmget(rKeyName);
            List<Map<String,Object>> result = new ArrayList<>();
            map.entrySet().parallelStream().forEach(entry ->{
                Map<String,Object> m = new HashMap<>();
                m.put("hashItemKey", entry.getKey());
                m.put("hashItemValue", JSON.toJSONString(entry.getValue()));
                result.add(m);
            });
            return WrapperResponse.success("hash",result);
        }
        return WrapperResponse.success(retMsg);
    }

    /**
     * 根据 key 删除 redis缓存数据
     *
     * @param keyName 缓存key值名
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse<Boolean> deleteFromCacheByKey(String keyName) {
        redisUtils.del(keyName);
        return WrapperResponse.success(Boolean.TRUE);
    }
}

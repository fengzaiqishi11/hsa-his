package cn.hsa.center.cache.controller;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.cache.service.CacheService;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/center/cache")
@Slf4j
public class CacheController {

    @Resource
    private CacheService cacheService;

    /**
     *  初次使用时初始化数据源, 此处必须在controller层调用避免第一次使用时数据源未初始化,导致动态切换失败
     */
    private final AtomicInteger dataSourceInitCount = new AtomicInteger(0);



    @GetMapping("/refreshCodeDetailsCache/{hospCodes}")
    public WrapperResponse<Boolean> refreshCodeDetailsCacheByHospCode(@PathVariable("hospCodes") String strHospCodes){
        if(dataSourceInitCount.get() < 1){
            // 初次使用先初始化数据源信息
            dataSourceInitCount.incrementAndGet();
            cacheService.refreshCenterHospitalDatasource();
        }
        Optional.ofNullable(strHospCodes).orElseThrow(()-> new AppException("必传医院编码不能为空,多个医院之间以','分割"));
        String[] hospCodes = strHospCodes.split(",");
        Boolean result = Boolean.TRUE;
        String retMsg = "";
        for(String hospCode : hospCodes){
            Map<String,String> params = new HashMap<>();
            params.put("hospCode",hospCode);
            try{
                result &= cacheService.refreshCodeDetailsCacheByHospCode(params).getData();
            }catch(Exception e){
                log.error("更新缓存出现异常: 医院编码为：{}",hospCode,e);
                result = Boolean.FALSE;
                retMsg = e.getMessage();
            }
        }
        return WrapperResponse.success(retMsg,result);
    }

    /**
     *  刷新数据源缓存信息
     * @return java.lang.Boolean
     */
    @GetMapping("/refreshCenterHospitalDatasource")
    public WrapperResponse<Boolean> refreshCenterHospitalDatasource(){
        // 初始化次数+1
        dataSourceInitCount.incrementAndGet();
        return cacheService.refreshCenterHospitalDatasource();
    }

    /**
     *  获取key值统配符
     * @param keyName 缓存key名称
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/getCacheKeysByKey/{key}")
    public WrapperResponse<Set<Map<String,String>>> getCacheKeysByKey(@PathVariable("key") String keyName){
        if(StringUtils.isEmpty(keyName.trim())){
            return WrapperResponse.success(Collections.EMPTY_SET);
        }
        Map<String,String> m = new HashMap<>();
        m.put("rKeyName",keyName);
        return cacheService.getRedisCacheFilteredKey(m);
    }

    /**
     *  根据Key值获取redis缓存数据
     * @param keyName 缓存key名称
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/getCacheDataByKey/{key}")
    public WrapperResponse<Object> getRedisCacheDataByKey(@PathVariable("key") String keyName){
        if(StringUtils.isEmpty(keyName.trim())){
            return WrapperResponse.success(Collections.EMPTY_SET);
        }
        Map<String,String> m = new HashMap<>();
        m.put("rKeyName",keyName);
        return cacheService.getRedisCacheDataByKey(m);
    }

    /**
     *  根据Key值删除redis缓存数据
     * @param keyName 缓存key名称
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @DeleteMapping("/deleteFromCacheByKey/{key}")
    public WrapperResponse<Boolean> deleteFromCacheByKey(@PathVariable("key") String keyName){
        if(StringUtils.isEmpty(keyName.trim())){
            return WrapperResponse.success(Boolean.FALSE);
        }
        return cacheService.deleteFromCacheByKey(keyName);
    }
}

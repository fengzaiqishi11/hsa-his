package cn.hsa.module.center.cache.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;
import java.util.Set;

public interface CacheService {

    /***
     *  根据医院编码刷新redis缓存中的码表数据
     * @param map 参数 hospCode为必传参数
     * @return 是否成功
     */
    WrapperResponse<Boolean> refreshCodeDetailsCacheByHospCode(Map<String,String> map);

    /**
     *  刷新中心端数据源信息
     * @return
     */
    WrapperResponse<Boolean>  refreshCenterHospitalDatasource();

    /**
     *  获取redis缓存数据
     * @param params 缓存key值名
     * @return
     */
    WrapperResponse<Set<Map<String,String>>>  getRedisCacheFilteredKey(Map<String,String> params);

    /**
     *  根据redis 获取缓存数据
     * @param params 缓存key值名
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse<Object>  getRedisCacheDataByKey(Map<String,String> params);

    /**
     *  根据 key 删除 redis缓存数据
     * @param keyName 缓存key值名
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse<Boolean>  deleteFromCacheByKey(String keyName);
}

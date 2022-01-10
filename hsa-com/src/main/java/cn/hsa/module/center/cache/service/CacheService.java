package cn.hsa.module.center.cache.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

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
}

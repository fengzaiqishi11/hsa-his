package cn.hsa.center.cache.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.cache.bo.CenterCacheBO;
import cn.hsa.module.center.cache.dao.CacheDao;
import cn.hsa.module.center.cache.service.CacheService;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    private AtomicInteger dataSourceInitCount = new AtomicInteger(0);

    /***
     *  根据医院编码刷新redis缓存中的码表数据
     * @param map 参数 hospCode为必传参数
     * @return 是否成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public WrapperResponse<Boolean> refreshCodeDetailsCacheByHospCode(Map<String,String> map) {
        if(dataSourceInitCount.get() < 1){
            // 初次使用先初始化数据源信息
            centerCacheBO.refreshCenterHospitalDatasource();
        }
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
        // 初始化次数+1
        dataSourceInitCount.incrementAndGet();
        return centerCacheBO.refreshCenterHospitalDatasource();
    }
}

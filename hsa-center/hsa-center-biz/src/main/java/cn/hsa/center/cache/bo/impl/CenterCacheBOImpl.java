package cn.hsa.center.cache.bo.impl;

import cn.hsa.center.cache.config.MultipleDataSource;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.cache.bo.CenterCacheBO;
import cn.hsa.module.center.cache.dao.CacheDao;
import cn.hsa.module.center.datasource.dao.CenterDatasourceDAO;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SpringUtils;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CenterCacheBOImpl implements CenterCacheBO {

    /**
     *  中心端数据源信息访问层
     */
    @Resource
    private CenterDatasourceDAO centerDatasourceDAO;

    /**
     *  中心端数据源缓冲
     */
    private final Map<String, CenterDatasourceDTO> centerDataSourceCacheContainer = new ConcurrentHashMap<>(160);

    private final Map<String, List<SysCodeSelectDTO>> emptyMap = new HashMap<>(1);

    /**
     *  获取码表信息
     * @param hospCode 医院编码
     * @return
     */
    @Override
    public Map<String, List<SysCodeSelectDTO>> getByHospCode(String hospCode) {
        List<SysCodeDTO> list = getSysCodeDTOsByHospCode(hospCode);
        if (!ListUtils.isEmpty(list)) {
            return list.stream().collect(HashMap::new,(map, p)->map.put(p.getCCode(),p.getSysCodeSelectDTOList()),Map::putAll);
        }
        return emptyMap;
    }

    private List<SysCodeDTO> getSysCodeDTOsByHospCode(String hospCode) {
        List<String> codeList = new ArrayList<>();
        CacheDao  cacheDao = SpringUtils.getBean(CacheDao.class);
        if(!centerDataSourceCacheContainer.containsKey(hospCode)){
            refreshCenterHospitalDatasource();
            if(!centerDataSourceCacheContainer.containsKey(hospCode)){
                throw new AppException("未查询到医院编码为：【"+hospCode+"】 的数据源");
            }
        }
        // 切换数据源
        routeDataSource(centerDataSourceCacheContainer.get(hospCode));
        List list = cacheDao.getByCode(codeList, hospCode);
        MultipleDataSource.clearDataSourceKey();
       return list;
    }

    /**
     *  jdbc数据源动态切换路由
     * @param dto 中心端数据源信息
     */
    private void routeDataSource(CenterDatasourceDTO dto) {
        try{
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(dto.getDriverName());
            dataSource.setUrl(dto.getUrl());
            dataSource.setUsername(dto.getUsername());
            dataSource.setPassword(dto.getPassword());
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(0);
            dataSource.setMaxWait(60000L);
            dataSource.setTimeBetweenEvictionRunsMillis(60000L);
            dataSource.setMinEvictableIdleTimeMillis(120000L);
            dataSource.init();
            MultipleDataSource dynamicSource = SpringUtils.getBean(MultipleDataSource.class);
            Map<Object,Object> dataSources = new HashMap<>(5);
            dataSources.put(dto.getCode(),dataSource);
            dynamicSource.setTargetDataSources(dataSources);
            // 设置当前路由医院 key
            MultipleDataSource.setDataSourceKey(dto.getCode());
        } catch (Exception e) {
            log.error("获取 {} 连接失败",dto.getCode(),e);
        }
    }

    /**
     *  查询医院数据源集合并刷新缓存
     *
     * @Author Ou·Mr
     * @Date 2020/7/20 19:15
     * @Return List<CenterDatasourceDTO> 结果集合
     * @return
     */
    @Override
    public WrapperResponse<Boolean> refreshCenterHospitalDatasource() {
        centerDataSourceCacheContainer.clear();
        List<CenterDatasourceDTO> list = centerDatasourceDAO.queryCenterHospitalDatasourceAll();
        Map<String,CenterDatasourceDTO> map = list.stream().collect(Collectors.toMap(CenterDatasourceDTO::getCode,i->i));
        centerDataSourceCacheContainer.putAll(map);
        return WrapperResponse.success(Boolean.TRUE);
    }
}

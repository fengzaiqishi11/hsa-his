package cn.hsa.center.cache.config;


import cn.hsa.util.SpringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MultipleDataSource extends AbstractRoutingDataSource {

    /**
     *  当前数据源路由 key值存放
     */
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
    /**
     *  默认的空目标数据源
     */
    private static final Map<Object,Object> defaultEmptyDataSource = Collections.emptyMap();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    /**
     *  清空路由Key信息,避免干扰默认数据源使用
     */
    public static void clearDataSourceKey() {
        dataSourceKey.remove();
        restoreTargetDataSources(defaultEmptyDataSource);
    }


    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }

    /**
     *  重置目标数据源为空，否则页面会间断性报错
     * @param targetDataSources 空数据源
     */
    public static void restoreTargetDataSources(Map<Object, Object> targetDataSources) {
        MultipleDataSource dynamicSource = SpringUtils.getBean(MultipleDataSource.class);
        dynamicSource.setTargetDataSources(targetDataSources);
    }
}

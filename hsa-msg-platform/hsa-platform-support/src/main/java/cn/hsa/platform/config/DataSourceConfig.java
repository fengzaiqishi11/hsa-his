package cn.hsa.platform.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 *  全局数据源配置
 */
@Configuration
public class DataSourceConfig {

    @Value("${db.hikaricp.jdbcUrl}")
    private  String jdbcUrl ="";
    @Value("${db.hikaricp.driverClassName}")
    private  String driverClassName ="";
    @Value("${db.hikaricp.username}")
    private  String username ="";
    @Value("${db.hikaricp.password}")
    private  String password ="";
    @Value("${db.hikaricp.validationQuery}")
    private  String validationQuery ="SELECT 'x'";
    @Value("${db.hikaricp.dataSource.cachePrepStmts}")
    private  boolean cachePrepStmts = true;
    @Value("${db.hikaricp.dataSource.prepStmtCacheSize}")
    private  Integer prepStmtCacheSize = 250;
    private  Integer prepStmtCacheSqlLimit = 2048;
    private  boolean useServerPrepStmts = true;
    private  boolean useLocalSessionState = true;
    private  boolean rewriteBatchedStatements = true;
    private  boolean cacheResultSetMetadata = true;
    private  boolean cacheServerConfiguration = true;
    private  boolean elideSetAutoCommits = true;
    private  boolean maintainTimeStats = true;

    @Bean(name = "dataSource")
    public DataSource initDataSource(){
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(jdbcUrl);
        dataSourceConfig.setDriverClassName(driverClassName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setConnectionTestQuery(validationQuery);
        dataSourceConfig.setPoolName("spring-hikaricp-pool");
        dataSourceConfig.setIdleTimeout(8000);
        Properties properties = new Properties();
        properties.put("dataSource.cachePrepStmts",cachePrepStmts);
        properties.put("dataSource.prepStmtCacheSize",prepStmtCacheSize);
        properties.put("dataSource.prepStmtCacheSqlLimit",prepStmtCacheSqlLimit);
        properties.put("dataSource.useServerPrepStmts",useServerPrepStmts);
        properties.put("dataSource.useLocalSessionState",useLocalSessionState);
        properties.put("dataSource.rewriteBatchedStatements",rewriteBatchedStatements);
        properties.put("dataSource.cacheResultSetMetadata",cacheResultSetMetadata);
        properties.put("dataSource.cacheServerConfiguration",cacheServerConfiguration);
        properties.put("dataSource.elideSetAutoCommits",elideSetAutoCommits);
        properties.put("dataSource.maintainTimeStats",maintainTimeStats);
        dataSourceConfig.setDataSourceProperties(properties);
        HikariDataSource dataSource = new HikariDataSource(dataSourceConfig);

        return dataSource;

    }
}

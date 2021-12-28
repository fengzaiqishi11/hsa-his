package cn.hsa.interf.config;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 *  elasticsearch 客户端连接配置
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String elasticsearchHostAndPort;
    @Value("${elasticsearch.username}")
    private String elasticsearchUserName;
    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config  = ClientConfiguration.builder()
                .connectedTo(elasticsearchHostAndPort)
                .withBasicAuth(elasticsearchUserName,elasticsearchPassword)
                .build();
        return RestClients.create(config).rest();
    }
}


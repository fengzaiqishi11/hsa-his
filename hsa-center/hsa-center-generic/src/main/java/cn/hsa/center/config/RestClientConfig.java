package cn.hsa.center.config;


import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import java.net.SocketTimeoutException;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    private final Logger log = LoggerFactory.getLogger(RestClientConfig.class);

    @Value("${elasticsearch.host}")
    private String elasticsearchHostAndPort;
    @Value("${elasticsearch.username}")
    private String elasticsearchUserName;
    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config  = ClientConfiguration.builder()
                .connectedTo(elasticsearchHostAndPort)
                .withBasicAuth(elasticsearchUserName,elasticsearchPassword)
                .build();
        return RestClients.create(config).rest();
    }


    @Override
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter) {
        return new ElasticsearchRestTemplate(elasticsearchClient(), elasticsearchConverter) {
            @Override
            public <T> T execute(ClientCallback<T> callback) {
                int retryCount = 0;
                T t = null;
                while (retryCount <= RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE && t == null) {
                    try {
                        t = super.execute(callback);
                    } catch (DataAccessResourceFailureException e) {
                        // retry
                        if (e.getCause() != null && (e.getCause().getCause() instanceof SocketTimeoutException) &&
                                (retryCount < RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE)) {
                            retryCount++;
                            log.warn("Elasticsearch client - performing retry {} after caught DataAccessResourceFailureException: {}", retryCount, e.getMessage());
                        }
                        else {
                            throw e;
                        }
                    }
                }
                return t;
            }
        };
    }
}


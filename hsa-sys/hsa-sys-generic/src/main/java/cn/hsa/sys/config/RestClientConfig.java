//package cn.hsa.sys.config;
//
//
//import org.elasticsearch.client.RestHighLevelClient;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//
//@Configuration
//public class RestClientConfig extends AbstractElasticsearchConfiguration {
//
//    @Value("${elasticsearch.host}")
//    private String elasticsearchHostAndPort;
//
//    @Override
//    public RestHighLevelClient elasticsearchClient() {
//        return RestClients.create(ClientConfiguration.create(elasticsearchHostAndPort)).rest();
//    }
//
//    // no special bean creation needed
//}
//

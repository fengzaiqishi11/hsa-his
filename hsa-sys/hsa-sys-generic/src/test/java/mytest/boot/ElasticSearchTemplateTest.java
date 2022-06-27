package mytest.boot;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.similarity.ScriptedSimilarity;
import org.junit.Test;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


public class ElasticSearchTemplateTest {

    public static void main(String[] args){
        ElasticSearchTemplateTest test = new ElasticSearchTemplateTest();
        test.myTest();
    }


    public void myTest(){
        RestHighLevelClient client =  RestClients.create(ClientConfiguration.create("172.18.100.103:9200")).rest();

        ElasticsearchOperations elasticsearchOperations = new ElasticsearchRestTemplate(client);
        Object o = elasticsearchOperations.get("/", null);
        System.out.println("Elasticsearch"+ elasticsearchOperations);
        System.out.println("Elasticsearch"+ o);

    }
}

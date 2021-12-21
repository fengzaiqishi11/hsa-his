package cn.hsa.sys.config;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.search.service.NationStandardDrugService;
import cn.hsa.search.service.impl.NationStandardDrugServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;

@Configuration
public class ApplicationConfig {

    @Bean
    public ElasticsearchRepositoryFactory getElasticsearchRepositoryFactory(@Autowired ElasticsearchOperations elasticsearchOperations){
       return new ElasticsearchRepositoryFactory(elasticsearchOperations);
    }

    @Bean("searchableNationStandardDrugService")
    public NationStandardDrugService getNationStandardDrugService(@Autowired ElasticsearchRepositoryFactory repositoryFactory,@Autowired  ElasticsearchOperations elasticsearchOperations){
        ElasticsearchEntityInformation<NationStandardDrugDO,String> metadata = repositoryFactory.getEntityInformation(NationStandardDrugDO.class) ;
        return new NationStandardDrugServiceImpl(metadata,elasticsearchOperations);
    }
}

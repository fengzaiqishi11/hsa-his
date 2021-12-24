package cn.hsa.center.config;

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

    /**
     *  生成elasticsearch实体信息用于 repository的构建以及查询结果的自动映射
     * @param repositoryFactory 查询实体工厂
     * @return  org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation
     */
    @Bean
    public ElasticsearchEntityInformation<NationStandardDrugDO,String> getNationStandardDrugElasticsearchEntityInformation(@Autowired ElasticsearchRepositoryFactory repositoryFactory){
        return repositoryFactory.getEntityInformation(NationStandardDrugDO.class) ;
    }
}

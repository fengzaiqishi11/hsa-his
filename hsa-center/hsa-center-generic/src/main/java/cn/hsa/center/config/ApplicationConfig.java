package cn.hsa.center.config;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import cn.hsa.module.interf.search.repository.SearchableNationStandardDrugRepository;
import cn.hsa.module.interf.search.repository.SearchableNationStandardTCMDrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.session.web.http.DefaultCookieSerializer;

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

    /**
     *  生成elasticsearch实体中药信息用于 repository的构建以及查询结果的自动映射
     * @param repositoryFactory 查询实体工厂
     * @return  org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation
     */
    @Bean
    public ElasticsearchEntityInformation<NationStandardDrugZYDO,String> getNationStandardTCMDrugElasticsearchEntityInformation(@Autowired ElasticsearchRepositoryFactory repositoryFactory){
        return repositoryFactory.getEntityInformation(NationStandardDrugZYDO.class) ;
    }

    /**
     *  操作es国标西药中成药的实现,用到的模块按需则手动在配置文件中创建
     * @param metaMappingData 西药实体映射信息
     * @param elasticsearchOperations es操作模板类
     * @return
     */
    @Bean
    public SearchableNationStandardDrugRepository searchableNationStandardDrugRepository(ElasticsearchEntityInformation<NationStandardDrugDO,String> metaMappingData,ElasticsearchOperations elasticsearchOperations){
        return new SearchableNationStandardDrugRepository(metaMappingData,elasticsearchOperations);
    }

    /**
     *  操作es国标中药中成药的实现,用到的模块按需则手动在配置文件中创建
     * @param metaMappingData 中药实体映射信息
     * @param elasticsearchOperations es操作模板类
     * @return
     */
    @Bean
    public SearchableNationStandardTCMDrugRepository searchableNationStandardTCMDrugRepository(ElasticsearchEntityInformation<NationStandardDrugZYDO,String> metaMappingData, ElasticsearchOperations elasticsearchOperations){
        return new SearchableNationStandardTCMDrugRepository(metaMappingData,elasticsearchOperations);
    }
    
    /**
     *  <p>自定义 Cookie序列化器，解决同一服务器不同应用串 Session的问题
     *  <p> 注意 cookiePath需要与对应应用的servlet.context-path 保持一致,
     *  <p>否则会出现验证码一直不正确导致无法登录的情况
     * @see org.springframework.session.web.http.DefaultCookieSerializer
     * @return org.springframework.session.web.http.DefaultCookieSerializer
     */
    @Bean
    public DefaultCookieSerializer getDefaultCookieSerializer(){
        DefaultCookieSerializer  cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookiePath("/hsa-center");
        return cookieSerializer;
    }
}

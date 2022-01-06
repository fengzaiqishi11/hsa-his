package cn.hsa.module.interf.search.repository;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 *  es数据访问层,通过继承自springboot提供的简单实现类来操作es搜索引擎
 *  <p>注意的是 实体映射信息需要我们自己手动构建
 */
public class SearchableNationStandardDrugRepository extends SimpleElasticsearchRepository<NationStandardDrugDO,String> {

    public SearchableNationStandardDrugRepository(ElasticsearchEntityInformation<NationStandardDrugDO, String> metadata, ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }
}

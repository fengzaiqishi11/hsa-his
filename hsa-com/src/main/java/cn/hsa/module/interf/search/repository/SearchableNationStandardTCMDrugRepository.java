package cn.hsa.module.interf.search.repository;

import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 *  es数据访问层,通过继承自springboot提供的简单实现类来操作es搜索引擎
 * <p>注意的是: 实体映射信息需要我们自己手动构建</p>
 */
public class SearchableNationStandardTCMDrugRepository extends SimpleElasticsearchRepository<NationStandardDrugZYDO,String> {

    /**
     *   国标中药es数据访问层
     * @param metadata 中药DO对应的实体映射数据用于结果集的处理
     * @param elasticsearchOperations 构建的es模板
     */
    public SearchableNationStandardTCMDrugRepository(ElasticsearchEntityInformation<NationStandardDrugZYDO, String> metadata, ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }
}

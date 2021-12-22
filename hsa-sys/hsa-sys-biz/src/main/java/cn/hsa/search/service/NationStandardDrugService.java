package cn.hsa.search.service;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 *  国家标准药品搜索服务
 * @author  luonianxin
 * @since  2021-12-20
 */

public interface NationStandardDrugService extends ElasticsearchRepository<NationStandardDrugDO,String> {

    /**
     *  删除建立的索引
     * @param indexName 索引名
     */
    void deleteIndex(String indexName);

    /**
     *  返回搜索分页结果
     * @param queryCondition 查询条件
     * @return java.util.List
     */
    PageDTO searchByConditions(NationStandardDrugDTO queryCondition);

}

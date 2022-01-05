package cn.hsa.search.service;


/**
 *  国家标准药品搜索服务
 * @author  luonianxin
 * @since  2021-12-20
 */

public interface NationStandardDrugService {

    /**
     *  删除建立的索引
     * @param indexName 索引名
     */
    void deleteIndex(String indexName);

    /**
     *  刷新elasticsearch中西药与中成药的数据
     * @return java.lang.Long 总更新的数据行数
     */
    Long refreshWesternDrugDataOfElasticSearch();
    /**
     *  刷新elasticsearch 中的 中药的数据
     * @return java.lang.Long 总更新的数据行数
     */
    Long refreshTCMDrugDataOfElasticSearch();

    /**
     *  返回es中的数据行数
     * @param flag 标志位 1,0,查询西药中成药数据,否则查询中药数据
     * @return java.lang.Long
     */
    Long dataCountOfElasticSearch( int flag);
}

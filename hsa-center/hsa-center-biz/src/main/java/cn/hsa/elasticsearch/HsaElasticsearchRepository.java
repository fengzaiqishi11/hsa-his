package cn.hsa.elasticsearch;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.util.Assert;

/**
 *   通用 Elasticsearch层
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnBean({RestHighLevelClient.class})
public class HsaElasticsearchRepository {
    /**
     *  ElasticsearchRestTemplate的父类接口,必须使用父类接口定义,否则默认bean注入会失败
     */
    private ElasticsearchOperations elasticsearchRestTemplate;

    public HsaElasticsearchRepository(ElasticsearchOperations elasticsearchRestTemplate){
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> search(QueryBuilder query, Pageable pageable, Class<T> entityClass) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withPageable(pageable).build();
        SearchHits<T> searchHits = elasticsearchRestTemplate.search(searchQuery, entityClass);
        SearchPage<T>  page = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> search(Query query,Class<T> entityClass) {
        SearchHits<T> searchHits = elasticsearchRestTemplate.search(query, entityClass);
        SearchPage<T>  page = SearchHitSupport.searchPageFor(searchHits, query.getPageable());
        return (Page<T>) SearchHitSupport.unwrapSearchHits(page);
    }

    /**
     *  保存单个数据
     * @param entity 需要保留的文档实体
     * @param <T> 泛型类
     * @return 保存后的实体对象
     */
    public <T> T save(T entity){
        Assert.notNull(entity,"需要保存的文档实体不能为空！");
        return (T) elasticsearchRestTemplate.save(entity);
    }

    /**
     * 批量保存带有 @Document 注解的文档信息
     *
     * @param entities must not be {@literal null}
     * @param <T> the entity type
     * @return 返回保存的数据主
     */
    public <T> Iterable<T> save(Iterable<T> entities){
        Assert.notNull(entities,"需要批量保存的文档实体不能为空！");
        return elasticsearchRestTemplate.save(entities);
    }
    /**
     * Delete the one object with provided id.
     *
     * @param id the document ot delete
     * @param entityType must not be {@literal null}.
     * @return documentId of the document deleted
     */
    public String delete(String id, Class<?> entityType){
        return elasticsearchRestTemplate.delete(id,entityType);
    }

    /**
     * Deletes the given entity
     *
     * @param entity the entity to delete
     * @return documentId of the document deleted
     */
    public String delete(Object entity){
        return elasticsearchRestTemplate.delete(entity);
    }

    /**
     * Deletes the given entity
     *
     * @param entity the entity to delete
     * @param index the index from which to delete
     * @return documentId of the document deleted
     */
    public String delete(Object entity, IndexCoordinates index){
        Document doc = entity.getClass().getAnnotation(Document.class);
        String indexName = doc.indexName();
        return elasticsearchRestTemplate.delete(entity,IndexCoordinates.of(indexName));
    }

    /**
     * Delete all records matching the query.
     *
     * @param query query defining the objects
     * @param clazz The entity class, must be annotated with
     *          {@link org.springframework.data.elasticsearch.annotations.Document}
     * @return response with detailed information
     * @since 4.1
     */
    public void delete(Query query, Class<?> clazz){
        Document doc = clazz.getClass().getAnnotation(Document.class);
        String indexName = doc.indexName();
        elasticsearchRestTemplate.delete(query,clazz,IndexCoordinates.of(indexName));
    }
}

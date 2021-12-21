package cn.hsa.search.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.search.service.NationStandardDrugService;
import org.elasticsearch.index.query.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.querydsl.QPageRequest;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *  国家基础药品搜索服务实现
 *
 */
public class NationStandardDrugServiceImpl extends SimpleElasticsearchRepository<NationStandardDrugDO,String> implements NationStandardDrugService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final String [] searchFieldNames = new String[]{"prod","goodName","registerName","wbm","pym","code","dan"};

    /**
     *  构建服务实现实体类
     * @param metadata 文档实体类元数据信息
     * @param elasticsearchOperations elatisSearchTemplate实现类
     */
    public NationStandardDrugServiceImpl(ElasticsearchEntityInformation<NationStandardDrugDO,String> metadata, ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }


    /**
     *  删除建立的索引
     * @param indexName 索引名
     */
    @Override
    public void deleteIndex(String indexName) {
        elasticsearchRestTemplate.indexOps(NationStandardDrugDO.class).delete();
    }

    /**
     * 返回搜索的分页结果,注意pageNo从 0开始,与Mybatis 定义的pageNo 1不同
     * <p>
     *     Creates a new QPageRequest. Pages are zero indexed, thus providing 0 for page will return the first page.
     * <p> 注意：使用设置 MultiMatchQueryBuilder多关键字匹配操作符为OR,只要有一个满足便匹配,默认操作符为AND
     * @param queryCondition 查询条件
     * @return java.util.Iterator
     */
    @Override
    public PageDTO searchByConditions(NationStandardDrugDTO queryCondition) {
        String provinceCode = Optional.ofNullable(queryCondition.getProvinceCode()).orElseThrow(()-> new AppException("省份编码不能为空!!!"));
        // TODO 根据省份代码结合条件来执行搜索过滤,要支持分词
        int pageNo = Math.max((queryCondition.getPageNo() - 1), 0);
        Pageable pageable = QPageRequest.of(pageNo,queryCondition.getPageSize());
        QueryBuilder queryBuilder = null;
        if(null != queryCondition.getKeyword() && !"".equals(queryCondition.getKeyword())){
            queryBuilder = QueryBuilders.multiMatchQuery(queryCondition.getKeyword(),searchFieldNames);
            ((MultiMatchQueryBuilder)queryBuilder).operator(Operator.OR);
        }else{
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("provinceCode",provinceCode);
        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        booleanQueryBuilder.must(queryBuilder);
        booleanQueryBuilder.must(termQueryBuilder);

        Page<NationStandardDrugDO>  page = search(booleanQueryBuilder,pageable);
        return PageDTO.of(page);
    }

}

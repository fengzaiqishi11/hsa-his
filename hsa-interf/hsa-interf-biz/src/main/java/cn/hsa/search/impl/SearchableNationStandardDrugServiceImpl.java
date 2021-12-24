package cn.hsa.search.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.search.SearchableNationStandardDrugService;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *  国家基础药品搜索服务实现
 *  加上component注解让spring容器初始化,注意的是 实体映射信息需要我们自己手动构建
 */
@Slf4j
@Component
public class SearchableNationStandardDrugServiceImpl extends SimpleElasticsearchRepository<NationStandardDrugDO,String> implements SearchableNationStandardDrugService {

    /**
     *  es 操作模板,repository中未支持的方法可使用该模板来实现
     */
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    private final String [] searchFieldNames = new String[]{"prod","goodName","registerName","wbm","pym","code","dan"};

    /**
     *  构建服务实现实体类
     * @param metadata 文档实体类元数据信息
     * @param elasticsearchOperations elatisSearchTemplate实现类
     */
    public SearchableNationStandardDrugServiceImpl(ElasticsearchEntityInformation<NationStandardDrugDO,String> metadata, ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }


    /**
     *  interf模块默认不实现该接口
     * @param indexName 索引名
     */
    @Override
    public void deleteIndex(String indexName) {
    }

    /**
     * 返回搜索的分页结果,注意pageNo从 0开始,与Mybatis 定义的pageNo 1不同
     * <p>
     *     Creates a new QPageRequest. Pages are zero indexed, thus providing 0 for page will return the first page.
     * <p> 注意：使用设置 MultiMatchQueryBuilder多关键字匹配操作符为OR,只要有一个满足便匹配,默认操作符为AND
     * <p> minimumShouldMatch 设置匹配度百分比 85%
     * <p> 详情请参考：https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-minimum-should-match.html
     * <p> 中文ik分词器安装参考：https://github.com/medcl/elasticsearch-analysis-ik
     * @param queryCondition 查询条件
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO searchByConditions(NationStandardDrugDTO queryCondition) {
        String provinceCode = Optional.ofNullable(queryCondition.getProvinceCode()).orElseThrow(()-> new AppException("省份编码不能为空!!!"));
        int pageNo = Math.max((queryCondition.getPageNo() - 1), 0);
        Pageable pageable = QPageRequest.of(pageNo,queryCondition.getPageSize());
        QueryBuilder queryBuilder = null;
        if(null != queryCondition.getKeyword() && !"".equals(queryCondition.getKeyword())){
            queryBuilder = QueryBuilders.multiMatchQuery(getFuzzyQueryString(queryCondition.getKeyword()),searchFieldNames);
            ((MultiMatchQueryBuilder)queryBuilder).operator(Operator.OR);
            ((MultiMatchQueryBuilder)queryBuilder).minimumShouldMatch("85%");
        }else{
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        booleanQueryBuilder.must(queryBuilder);
        booleanQueryBuilder.filter(QueryBuilders.termQuery("provinceCode",provinceCode));
        Page<NationStandardDrugDO>  page = search(booleanQueryBuilder,pageable);
        return PageDTO.of(page);
    }


    /**
     *  获取分词后的五笔码拼音码 字符串
     * @param sourceQueryString 需要分词的的字符串
     * @return 分词后的字符串(包含中文的不添加空格)
     */
    private String getFuzzyQueryString(String sourceQueryString){
        if(!StringUtils.isContainChinese(sourceQueryString)){
            return StringUtils.getFuzzyQueryString(sourceQueryString);
        }
        return sourceQueryString;
    }

    /**
     *  interf模块默认不实现该接口返回-1
     * @return java.lang.Long 总更新的数据行数
     */
    @Override
    public Long refreshDataOfElasticSearch() {

        return Long.parseLong("-1");
    }
}

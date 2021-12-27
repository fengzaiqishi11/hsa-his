package cn.hsa.search.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import cn.hsa.module.interf.search.bo.SearchableNationStandardDrugBO;
import cn.hsa.module.interf.search.repository.SearchableNationStandardDrugRepository;
import cn.hsa.module.interf.search.repository.SearchableNationStandardTCMDrugRepository;
import cn.hsa.util.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 *  国标药品搜索业务层具体实现
 *   加上component注解让spring容器初始化
 */
@Component
public class SearchableNationStandardDrugBOImpl implements SearchableNationStandardDrugBO {

    /**
     *  es 普通药品数据访问层
     */
    private SearchableNationStandardDrugRepository standardDrugRepository;
    /**
     *  es 中药数据访问
     */
    private SearchableNationStandardTCMDrugRepository standardTCMDrugRepository;
    /**
     *  es 模板访问方法,后续可以拓展
     */
    private ElasticsearchOperations elasticsearchTemplate;

    private final String [] searchFieldNames = new String[]{"prod","goodName","registerName","wbm","pym","code","dan"};

    public SearchableNationStandardDrugBOImpl(SearchableNationStandardDrugRepository standardDrugRepository,ElasticsearchOperations elasticsearchTemplate,SearchableNationStandardTCMDrugRepository standardTCMDrugRepository) {
        this.standardDrugRepository = standardDrugRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.standardTCMDrugRepository = standardTCMDrugRepository;
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


        if(StringUtils.isEmpty(queryCondition.getFlag())) {
            return PageDTO.of(Collections.EMPTY_LIST);
        }
       if("1".equals(queryCondition.getFlag()) || "0".equals(queryCondition.getFlag())) {
            Page<NationStandardDrugDO> page = standardDrugRepository.search(booleanQueryBuilder,pageable);
            return PageDTO.of(page);
        } else {
            Page<NationStandardDrugZYDO> pageOfTCM = standardTCMDrugRepository.search(queryBuilder,pageable);
            return PageDTO.of(pageOfTCM);
        }

    }

    /**
     * 刷新elasticsearch中的数据
     * interf 模块默认不实现
     * @return java.lang.Long 总更新的数据行数
     */
    @Override
    public Long refreshDataOfElasticSearch() {
        return Long.parseLong("-1");
    }


    /**
     *  获取分词后的五笔码拼音码 字符串
     * @param sourceQueryString 需要分词的的字符串
     * @return 分词后的字符串(包含中文的不添加空格)
     */
    private String getFuzzyQueryString(String sourceQueryString){
        // 2021-12-24 fix 编码code字段被分词后无法查询出数据
        if(sourceQueryString.matches(".*[a-zA-Z].*") && sourceQueryString.matches(".*[0-9].*")){
            return sourceQueryString;
        }
        if(!StringUtils.isContainChinese(sourceQueryString)){
            return StringUtils.getFuzzyQueryString(sourceQueryString);
        }
        return sourceQueryString;
    }
}

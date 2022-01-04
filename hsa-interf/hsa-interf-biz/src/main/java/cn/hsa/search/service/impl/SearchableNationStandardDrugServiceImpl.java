package cn.hsa.search.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.interf.search.bo.SearchableNationStandardDrugBO;
import cn.hsa.module.interf.search.service.SearchableNationStandardDrugService;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  国家基础药品搜索服务实现
 */
@Slf4j
@Service("searchableNationStandardDrugService_provider")
public class SearchableNationStandardDrugServiceImpl  implements SearchableNationStandardDrugService {

    /**
     *  es 操作模板,repository中未支持的方法可使用该模板来实现
     */
    @Resource
    private SearchableNationStandardDrugBO searchableNationStandardDrugBO;


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
    public WrapperResponse<PageDTO> searchByConditions(NationStandardDrugDTO queryCondition) {

        return WrapperResponse.success(searchableNationStandardDrugBO.searchByConditions(queryCondition));
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

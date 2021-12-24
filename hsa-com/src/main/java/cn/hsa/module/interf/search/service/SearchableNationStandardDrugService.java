package cn.hsa.module.interf.search.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 *  国家标准药品搜索服务
 * @author  luonianxin
 * @since  2021-12-20
 */
@FeignClient("hsa-search")
public interface SearchableNationStandardDrugService {

    /**
     *  返回搜索分页结果
     * @param queryCondition 查询条件
     * @return java.util.List
     */
    WrapperResponse<PageDTO> searchByConditions(NationStandardDrugDTO queryCondition);

    /**
     *  刷新elasticsearch中的数据
     * @return java.lang.Long 总更新的数据行数
     */
    Long refreshDataOfElasticSearch();
}

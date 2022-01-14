package cn.hsa.search.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugDAO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugZYDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.interf.search.repository.SearchableNationStandardDrugRepository;
import cn.hsa.module.interf.search.repository.SearchableNationStandardTCMDrugRepository;
import cn.hsa.search.service.NationStandardDrugService;
import cn.hsa.util.Constants;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.WuBiUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.List;

/**
 *  国家基础药品搜索服务实现
 *
 */
@Slf4j
@Component
public class NationStandardDrugServiceImpl  implements NationStandardDrugService {

    /**
     *  es 操作模板,repository中未支持的方法可使用该模板来实现
     */
    @Resource
    private ElasticsearchOperations elasticsearchRestTemplate;

    /**
     *  中心端西药药品数据库访问层
     */
    @Resource
    private NationStandardDrugDAO nationStandardDrugDAO;
    /**
     *  中心端中药药品数据库访问层
     */
    @Resource
    private NationStandardDrugZYDAO nationStandardTCMDrugDAO;

    @Resource
    private SearchableNationStandardTCMDrugRepository searchableTCMDrugRepository;
    @Resource
    private SearchableNationStandardDrugRepository searchableWesternMedicineDrugRepository;

    private final String [] searchFieldNames = new String[]{"prod","goodName","registerName","wbm","pym","code","dan"};


    /**
     *  删除建立的索引
     * @param indexName 索引名
     */
    @Override
    public void deleteIndex(String indexName) {
        elasticsearchRestTemplate.indexOps(NationStandardDrugDO.class).delete();
    }

    /**
     * 刷新elasticsearch中的数据
     *
     * @return java.lang.Long 总更新的数据行数
     */
    @Override
    public Long refreshWesternDrugDataOfElasticSearch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setIsValid(Constants.SF.S); // 只查询有效状态数据
        PageHelper.startPage(1,1);
        List<NationStandardDrugDTO> list = nationStandardDrugDAO.queryNationStandardDrugPage(param);
        PageDTO pageDTO = PageDTO.of(list);
        long total = pageDTO.getTotal();
        // 计算总页数,每次只查询500行数据
        int totalPages = (int) Math.ceil(total/500.0);
        for(int i=0;i<totalPages;i++){
            try {
                PageHelper.startPage(i + 1, 500);
                list = nationStandardDrugDAO.queryNationStandardDrugPage(param);
            }catch(Exception e){
                PageHelper.startPage(i + 1, 500);
                list = nationStandardDrugDAO.queryNationStandardDrugPage(param);
            }
            fillWubiCodeAndNamepyIfNull(list);
            searchableWesternMedicineDrugRepository.saveAll(list);
        }
        stopWatch.stop();
        log.debug("本次一共索引数据："+total +" 条，耗时："+stopWatch.prettyPrint());
        return total;
    }

    /**
     * 刷新elasticsearch 中的 中药的数据
     *
     * @return java.lang.Long 总更新的数据行数
     */
    @Override
    public Long refreshTCMDrugDataOfElasticSearch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        NationStandardDrugZYDTO param = new NationStandardDrugZYDTO();
        param.setIsValid(Constants.SF.S); // 只查询有效状态数据
        PageHelper.startPage(1,1);
        List<NationStandardDrugZYDTO> list = nationStandardTCMDrugDAO.queryNationStandardDrugZYPage(param);
        PageDTO pageDTO = PageDTO.of(list);
        long total = pageDTO.getTotal();
        // 计算总页数,每次只查询500行数据
        int totalPages = (int) Math.ceil(total/500.0);
        for(int i=0;i<totalPages;i++){
            PageHelper.startPage(i+1,500);
            list = nationStandardTCMDrugDAO.queryNationStandardDrugZYPage(param);
            fillWubiCodeAndNamepyIfNull2(list);
            searchableTCMDrugRepository.saveAll(list);
        }
        stopWatch.stop();
        log.debug("本次一共索引数据："+total +" 条，耗时："+stopWatch.prettyPrint());
        return total;
    }

    /**
     * 返回es中的数据行数
     *
     * @param flag 标志位 1,0,查询西药中成药数据,否则查询中药数据
     * @return java.lang.Long
     */
    @Override
    public Long dataCountOfElasticSearch(int flag) {
        if(flag == 1 || flag == 0){
            return searchableWesternMedicineDrugRepository.count();
        }
        return searchableTCMDrugRepository.count();
    }

    /**
     *  如果拼音码或五笔码为空则填充数据
     * @param list 需要填充五笔码的数据
     */
    private void fillWubiCodeAndNamepyIfNull(List<NationStandardDrugDTO> list) {
        list.stream().forEach(drugDO ->{
            drugDO.setWbm(WuBiUtils.getWBCodeSplitWithWhiteSpace(drugDO.getRegisterName()));
            drugDO.setPym(PinYinUtils.toFirstPYWithWhiteSpace(drugDO.getRegisterName()));
        });
    }
    /**
     *  如果拼音码或五笔码为空则填充数据
     * @param list 需要填充五笔码的数据
     */
    private void fillWubiCodeAndNamepyIfNull2(List<NationStandardDrugZYDTO> list) {
        list.stream().forEach(drugDO ->{
            drugDO.setWbm(WuBiUtils.getWBCodeSplitWithWhiteSpace(drugDO.getRegisterName()));
            drugDO.setPym(PinYinUtils.toFirstPYWithWhiteSpace(drugDO.getRegisterName()));
        });
    }
}

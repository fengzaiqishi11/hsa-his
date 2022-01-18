package cn.hsa.listener.elasticsearch;

import cn.hsa.event.ElasticsearchUpdateEvent;
import cn.hsa.search.service.NationStandardDrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *  elaticsearch 更新事件监听器
 * @author luonianxin
 * @date 2022-01-13
 */
@Component
public class ElasticsearchUpdateEventListener {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchUpdateEventListener.class);
    private NationStandardDrugService nationStandardDrugService;

    public ElasticsearchUpdateEventListener (NationStandardDrugService nationStandardDrugService) {
        this.nationStandardDrugService = nationStandardDrugService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(ElasticsearchUpdateEvent event) {
        if(log.isDebugEnabled()){
            log.debug(Thread.currentThread().getName()+" 处理applicationEvent事件");
        }
        int type = event.getDataType();
        if(0 == type){
            // 刷新es西药数据信息
            nationStandardDrugService.refreshWesternDrugDataOfElasticSearch();
        }else if(1 == type){
            // 刷新es中药数据信息
            nationStandardDrugService.refreshTCMDrugDataOfElasticSearch();
        }

    }
}

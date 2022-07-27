package cn.hsa.listener;

import cn.hsa.event.CenterOperationLogRecordEvent;
import cn.hsa.module.center.log.entity.CenterOperationLogDo;
import cn.hsa.module.center.log.service.CenterPasswordModifyLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  日志记录时间监听器
 * @author luonianxin
 * @date 2022-01-13
 */
@Component
public class CenterOperationRecordEventListener {

    private final Logger log = LoggerFactory.getLogger(CenterOperationRecordEventListener.class);
    private CenterPasswordModifyLogService centerPasswordModifyLogService;

    public CenterOperationRecordEventListener(CenterPasswordModifyLogService centerPasswordModifyLogService) {
        this.centerPasswordModifyLogService = centerPasswordModifyLogService;
    }

    @EventListener
    @Async
    public void onApplicationEvent(CenterOperationLogRecordEvent event) {
        if(log.isDebugEnabled()){
            log.debug(Thread.currentThread().getName()+" 处理applicationEvent事件");
        }
        CenterOperationLogDo renewalLog = event.getOperationLogDo();
        Map<String,Object> params = new HashMap<>();
        renewalLog.setCrteTime(new Date());
        params.put("renewalLog",renewalLog);
        centerPasswordModifyLogService.insertCenterOperationLog(params);

    }
}

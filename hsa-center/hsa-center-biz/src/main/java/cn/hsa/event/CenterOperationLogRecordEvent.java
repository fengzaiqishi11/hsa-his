package cn.hsa.event;

import cn.hsa.module.center.log.entity.CenterOperationLogDo;
import org.springframework.context.ApplicationEvent;

/**
 *  中心端日志记录事件
 */
public class CenterOperationLogRecordEvent extends ApplicationEvent {

    /**
     *  事件保存的记录实体
     */
    private final CenterOperationLogDo operationLogDo ;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param operationLogDo 日志记录实体
     */
    public CenterOperationLogRecordEvent(Object source, CenterOperationLogDo operationLogDo) {
        super(source);
        this.operationLogDo = operationLogDo;
    }

    public CenterOperationLogDo getOperationLogDo() {
        return operationLogDo;
    }
}

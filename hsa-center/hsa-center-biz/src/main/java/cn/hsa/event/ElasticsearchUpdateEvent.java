package cn.hsa.event;

import org.springframework.context.ApplicationEvent;

/**
 *  elasticsearch 搜索数据更新事件类
 */
public class ElasticsearchUpdateEvent extends ApplicationEvent {

    /**
     *  更新的数据类型，0表示 西药,1表示中药
     */
    private int dataType = 0;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param dataType 更新的数据类型
     */
    public ElasticsearchUpdateEvent(Object source,int dataType) {
        super(source);
        this.dataType = dataType;
    }

    /**
     *  获取更新事件数据类型
     * @return java.lang.int
     */
    public int getDataType() {
        return dataType;
    }
}

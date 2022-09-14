package cn.hsa.platform.event;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationEvent;

/**
 *  收到消息事件
 * @author luonianxin
 * @version 1.0
 * @date 2022/9/1 20:12
 */
public class MessagePublishEvent extends ApplicationEvent {


    private static final long serialVersionUID = 4997669397040184451L;

    /**
     *  消息体
     */
    private String publishedEventJsonObj;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @param msgJSONString  消息体
     */
    public MessagePublishEvent(String msgJSONString, Object source) {
        super(source);
        this.publishedEventJsonObj = msgJSONString;
    }

    /**
     *  获取消息数据体
     * @return json字符串
     */
    public String getPublishedEventJsonObj() {
        return publishedEventJsonObj;
    }
}

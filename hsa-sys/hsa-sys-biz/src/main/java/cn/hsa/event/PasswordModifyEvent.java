package cn.hsa.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 *  用户密码修改事件，用户修改密码时触发该事件,日志记录
 */
public class PasswordModifyEvent extends ApplicationEvent {
    /**
     *  密码更新参数
     */
    private Map<String,Object> modifyLogParams;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public PasswordModifyEvent(Object source,Map<String,Object> modifyLogParams) {
        super(source);
        this.modifyLogParams = modifyLogParams;
    }

    /**
     *  返回密码更新过程中的参数
     * @return
     */
    public Map<String, Object> getModifyLogParams() {
        return modifyLogParams;
    }
}

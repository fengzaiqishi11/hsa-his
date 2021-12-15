package cn.hsa.platform.handler;

import cn.hsa.platform.domain.TaskInfo;

/**
 * @author unkown
 * 发送各个渠道的handler
 */
public interface Handler {

    /**
     * 统一处理的handler接口
     *
     * @param taskInfo
     * @return
     */
    boolean doHandler(TaskInfo taskInfo);


}

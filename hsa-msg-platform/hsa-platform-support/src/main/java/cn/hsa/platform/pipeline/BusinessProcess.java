package cn.hsa.platform.pipeline;

/**
 * 业务执行器
 *
 * @author unkown
 */
public interface BusinessProcess {

    /**
     * 真正处理逻辑
     * @param context
     */
    void process(ProcessContext context);
}

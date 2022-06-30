//package cn.hsa.example.workflow.service;
//
//import cn.hsa.cep.wfc.pojo.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// *  忘记打卡流程服务
// */
//public interface MyProcessService {
//
//    /**
//     * 启动流程
//     * 读取流程图文件，进行发布操作
//     * @param processDefName 流程名称
//     */
//    void deployProcessDef(String processDefName);
//
//    /**
//     * 发起流程
//     * @param processDefName 流程名称
//     * @param starter 发起人
//     * @param nextExecutor 下一节点执行人集合
//     * @param map 流程变量（可存入业务的相关数据）
//     * @return
//     */
//    SignalResult startProcess(String processDefName, Actor starter, ActorSet nextExecutor, Map map);
//
//    /**
//     * 提交任务
//     * @param taskId  任务ID
//     * @param starter 执行人
//     * @param nextExecutor 下一节点执行人集合
//     * @param map 流程变量
//     * @return
//     */
//    public SignalResult commitTask(Long taskId, Actor starter, ActorSet nextExecutor, Map map);
//
//    /**
//     * 回退流程
//     */
//    List<Task> retraceProcess(long processId);
//
//    /**
//     * 恢复任务
//     */
//    void resumeTask(long taskId);
//
//    /**
//     * 回退任务
//     */
//    List<Task> rejectTask(long taskId);
//
//    /**
//     * 查看待办流程
//     */
//    ProcessInst getProcessById(long processInstId);
//
//    /**
//     * 根据具体的任务ID获取任务详细信息
//     * @param taskId
//     * @return
//     */
//    Task getTaskById(long taskId);
//
//    /**
//     * 查询用户ID
//     * @param userId
//     * @return
//     */
//    List<Task> queryTaskByUserId(String userId);
//
//}

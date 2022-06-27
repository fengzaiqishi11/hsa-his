package cn.hsa.example.workflow.service;

import cn.hsa.cep.wfc.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * 流程相关服务
 * @Author zhangjian
 * @Date 2019/8/17 10:35
 * @Version 1.0
 */
public interface ProcessService {

    /**
     * 启动流程
     * @param processDefName 流程名称
     */
    void deployProcessDef(String processDefName);


    /**
     * 发起流程
     * @param processDefName 流程名称
     * @param starter 发起人
     * @param nextExecutor 下一节点执行人集合
     * @param map 流程变量（可存入业务的相关数据）
     * @return
     */
    SignalResult startProcess(String processDefName, Actor starter, ActorSet nextExecutor, Map map);

    /**
     * 根据流程实例id查询流程信息
     * @param processInstId 流程实例id
     * @return
     */
    ProcessInst getProcessById(long processInstId);

    /**
     * 根据userid查询流程信息
     * @param userId
     * @return
     */
    List<ProcessInst> getProcessByUserId(String userId);


    /**
     * 待办任务及历史任务列表：根据用户id
     * @param actorId 用户id
     * @return
     */
    List<Task> findTasksByActorId(String actorId);

    /**
     * 根据具体的任务ID获取任务详细信息
     * @param taskId
     * @return
     */
    Task getTaskById(long taskId);

    /**
     * 根据流程实例ID获取任务变量信息
     * @param processId 流程实例id
     * @return
     */
    Map<String,Object> getTaskVariables(long processId);

    /**
     * 任务提交
     * @param taskId 任务id
     * @param executor 执行人
     * @param nextExecutor 下一节点执行人集合
     * @param inMap 流程变量
     * @return
     */
    SignalResult commitTask(Long taskId, Actor executor, ActorSet nextExecutor, Map inMap);

    /**
     * 根据流程实例id查询任务列表
     * @param processId 流程实例id
     * @return
     */
    List<Task> findTasksByProcessInstId(long processId);

}

package cn.hsa.example.workflow.service.impl;

import cn.hsa.cep.wfc.api.ProcessApplication;
import cn.hsa.cep.wfc.pojo.*;
import cn.hsa.example.workflow.service.MyProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class MyProcessServiceImpl implements MyProcessService {

    @Autowired
    private ProcessApplication processApplication;

    private static final String PAR_PATH = "/";
    private static final String PAR_SUFFIX = ".par";

    @Override
    public void deployProcessDef(String processDefName) {
        // parName为所需部署jar包的名称，根据具体情况替换；部署par包，需要放在src/test/java下
        URL url = this.getClass().getResource(PAR_PATH + processDefName + PAR_SUFFIX);

        InputStream inputStream2;
        try {
            inputStream2 = new FileInputStream(new File(url.getFile()));
            // processDefName为所需部署流程定义名称，根据具体情况替换
            ProcessDef processDef = processApplication.deployProcessDef(inputStream2, processDefName);
            System.out.println("部署流程定义如下：");
            System.out.println(processDef.toXml());
            System.out.println("==================流程部署结束=====================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SignalResult startProcess(String processDefName, Actor starter, ActorSet nextExecutor, Map map) {

        ProcessStart processStart = new ProcessStart();
        processStart.setDefName(processDefName);
        processStart.setStarter(starter);
        //流程开始
        SignalResult signalResult = processApplication.startProcess(processStart);

        //获取当前任务并且进行提交
        //Long taskId = signalResult.getNewTasks().get(0).getId();
        //signalResult = commitTask(taskId,starter,nextExecutor,map);

        return signalResult;
    }


    /**
     * 提交任务
     * @param taskId
     * @param starter
     * @param nextExecutor
     * @param map
     * @return
     */
    @Override
    public SignalResult commitTask(Long taskId,Actor starter,ActorSet nextExecutor,Map map){
        SignalResult signalResult = null;
        try {
            //当前任务信息
            TaskUpdate taskUpdate = new TaskUpdate();
            taskUpdate.setId(taskId);
            taskUpdate.setCommitVariables(map);
            taskUpdate.setExecutor(starter);

            SignalResult sr = processApplication.commitTask(taskUpdate,nextExecutor);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signalResult;
    }

    @Override
    public List<Task> retraceProcess(long processId) {


        return processApplication.retrieveTask(processId);
    }

    @Override
    public void resumeTask(long taskId) {
        //processApplication.
        processApplication.rejectTask(taskId);
    }

    @Override
    public List<Task> rejectTask(long taskId) {
        return processApplication.rejectTask(taskId);
    }

    @Override
    public ProcessInst getProcessById(long processInstId) {
        return processApplication.getProcess(processInstId);
    }

    @Override
    public Task getTaskById(long taskId) {
        return processApplication.getTask(taskId);
    }

    @Override
    public List<Task> queryTaskByUserId(String userId) {
        TaskQuery taskQuery = TaskQuery.all().byActorId(userId);
        List<Task> taskResult = processApplication.findTasks(taskQuery);
        return taskResult;
    }


}

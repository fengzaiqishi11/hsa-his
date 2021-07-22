package cn.hsa.example.workflow.service.impl;

import cn.hsa.cep.wfc.api.ProcessApplication;
import cn.hsa.cep.wfc.pojo.*;
import cn.hsa.example.workflow.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service("processService")
public class ProcessServiceImpl implements ProcessService {

//	private static ProcessManagement processManagement = ClientFactory.getInstance().getProcessManagement();
//	private static ProcessApplication processApplication = ClientFactory.getInstance().getProcessApplication();
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
		SignalResult signalResult = null;
		try {
			ProcessStart psi = new ProcessStart();
			psi.setDefName(processDefName); 
			psi.setStarter(starter);
			signalResult = processApplication.startProcess(psi);

			Long taskId = signalResult.getNewTasks().get(0).getId();
			signalResult = commitTask(taskId,starter,nextExecutor,map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signalResult;
	}

	@Override
	public SignalResult commitTask(Long taskId,Actor executor,ActorSet nextExecutor,Map inMap) {
		SignalResult signalResult = null;
		try {
			//当前任务信息
			TaskUpdate taskUpdate = new TaskUpdate();
			taskUpdate.setId(taskId);
			taskUpdate.setCommitVariables(inMap);
			taskUpdate.setExecutor(executor);

			SignalResult sr = processApplication.commitTask(taskUpdate,nextExecutor);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signalResult;
	}

	@Override
	public ProcessInst getProcessById(long processInstId) {
		ProcessInst processInst = processApplication.getProcess(processInstId);
		return processInst;
	}

	@Override
	public List<ProcessInst> getProcessByUserId(String userId) {
        ProcessQuery pq = ProcessQuery.all().setStarterId(userId);
		List<ProcessInst> processInsts = processApplication.findProcesses(pq);
		return processInsts;
	}

	@Override
	public List<Task> findTasksByActorId(String actorId) {
		TaskQuery tq = TaskQuery.all().byActorId(actorId);
		List<Task> taskResult = processApplication.findTasks(tq);
		return taskResult;
	}

	@Override
	public List<Task> findTasksByProcessInstId(long processId) {
		TaskQuery tq = TaskQuery.all().byProcess(processId);
		List<Task> taskResult = processApplication.findTasks(tq);
		return taskResult;
	}

	@Override
	public Task getTaskById(long taskId) {
		Task task = processApplication.getTask(taskId);
		return task;
	}
	@Override
	public Map<String,Object> getTaskVariables(long processId) {
		Map<String,Object> map = processApplication.getProcessVariables(processId);
		return map;
	}
}

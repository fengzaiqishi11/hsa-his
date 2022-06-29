//package cn.hsa.example.workflow.service.impl;
//
//import cn.hsa.cep.wfc.pojo.*;
//import cn.hsa.example.workflow.dto.ApproveDTO;
//import cn.hsa.example.workflow.dto.BusinessTripDTO;
//import cn.hsa.example.workflow.dto.ProcessDTO;
//import cn.hsa.example.workflow.dto.TaskDTO;
//import cn.hsa.example.workflow.service.BusinessTripService;
//import cn.hsa.example.workflow.service.ProcessService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Service("businessTripService")
//public class BusinessTripServiceImpl implements BusinessTripService {
//	@Autowired
//	private ProcessService processService;
//	private static final String PROCESS_DEF_NAME = "businesstripflow";
//	@Override
//	public void addBusinessTripInfo(BusinessTripDTO dto) {
//		String userId = dto.getUserId();
//		String userName = dto.getUserName();
//		String executorId = dto.getApproverId();
//		String executorName = dto.getApproverName();
//		String days = dto.getDays();
//		String cause = dto.getCause();
//		String beginDate = dto.getBeginDate();
//		String endDate = dto.getEndDate();
//
//		// 1. 流程部署,这里应该有个统一流程部署功能，在功能上进行流程部署
////        processService.deployProcessDef(PROCESS_DEF_NAME);
//
//		// 2. 启动出差申请流程
//		// 下一节点执行人集合，这个可根据业务具体情况放入
//		ActorSet nextExecutor = new ActorSet();
//		Actor executor = new Actor(executorId,executorName);
//		nextExecutor.add(executor);
//
//		// 业务数据，可传入流程平台保存，也可存入自己的表中保存
//		Map<String,Object> map = new HashMap<>();
//		map.put("days",days);
//		map.put("cause",cause);
//		map.put("beginDate",beginDate);
//		map.put("endDate",endDate);
//		map.put("userId",userId);
//		map.put("userName",userName);
//		SignalResult signalResult = processService.startProcess(PROCESS_DEF_NAME,new Actor(userId,userName),nextExecutor,map);
//	}
//	@Override
//	public List<ProcessDTO> queryBusinessTripProcess(String userId) {
//		List<ProcessInst> processInsts = processService.getProcessByUserId(userId);
//
//		List<ProcessDTO> reList = new ArrayList<>();
//		for (ProcessInst processInst : processInsts) {
//			ProcessDTO processDTO = new ProcessDTO();
//			BeanUtils.copyProperties(processInst,processDTO);
//			reList.add(processDTO);
//		}
//		return reList;
//	}
//	@Override
//	public List<TaskDTO> queryBusinessTripTask(String userId) {
//		List<Task> task = processService.findTasksByActorId(userId);
//		List<TaskDTO> reList = new ArrayList<>();
//		for (Task task1 : task) {
//			TaskDTO dto = new TaskDTO();
//			BeanUtils.copyProperties(task1,dto);
//			dto.setTaskName(task1.getName());
//			reList.add(dto);
//		}
//		return reList;
//	}
//	@Override
//	public TaskDTO queryBusinessTripInfo(long taskId,long processId) {
//		Task task = processService.getTaskById(taskId);
//		//获取任务变量信息及出差申请信息，此处是从流程中心获取，如是存入系统自己的表，则是从自己的表中取
//		Map<String,Object> map = processService.getTaskVariables(processId);
//		TaskDTO dto = new TaskDTO();
//		BeanUtils.copyProperties(task,dto);
//		dto.setTaskName(task.getName());
//		dto.setTaskVariables(map);
//		return dto;
//	}
//	@Override
//	public Map<String,Object> getTaskVariables(long processId) {
//		return processService.getTaskVariables(processId);
//	}
//	@Override
//	public void saveApproveInfo(ApproveDTO dto) {
//		long taskId = dto.getTaskId();
//		//当前节点审核人
//		Actor actor = new Actor();
//		actor.setId(dto.getApproverId());
//		actor.setName(dto.getApproverName());
//
//		// 下一节点执行人集合，这个可根据业务具体情况放入
//		ActorSet nextExecutor = new ActorSet();
//		Actor executor = new Actor(dto.getReviewerId(),dto.getReviewerName());
//		nextExecutor.add(executor);
//
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//
//		Map map = new HashMap();
//		if("组长审核".equals(dto.getNodeName())){
//			map.put("groupManagerResult",dto.getApproverResults());
//			map.put("groupManagerRemarks",dto.getApproverRemarks());
//			map.put("groupManagerDate",df.format(new Date()));
//			map.put("groupManager",dto.getApproverName());
//		}else if("经理审批".equals(dto.getNodeName())){
//			map.put("orgManagerResult",dto.getApproverResults());
//			map.put("orgManagerRemarks",dto.getApproverRemarks());
//			map.put("orgManagerDate", df.format(new Date()));
//			map.put("orgManager", dto.getApproverName());
//		}
//		processService.commitTask(taskId,actor,nextExecutor,map);
//	}
//	@Override
//	public List<TaskDTO> queryProcessHistory(long processId){
//		List<Task> tasks = processService.findTasksByProcessInstId(processId);
//		List<TaskDTO> taskDTOS = new ArrayList<>();
//		for (Task task : tasks) {
//			TaskDTO taskDTO = new TaskDTO();
//			BeanUtils.copyProperties(task,taskDTO);
//			taskDTO.setTaskName(task.getName());
//			taskDTO.setExecutorName(task.getActor().getName());
//			taskDTOS.add(taskDTO);
//		}
//		return taskDTOS;
//	}
//
//
//
//}

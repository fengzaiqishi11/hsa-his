package cn.hsa.example.workflow.service.impl;

import cn.hsa.cep.wfc.pojo.Actor;
import cn.hsa.cep.wfc.pojo.ActorSet;
import cn.hsa.cep.wfc.pojo.Task;
import cn.hsa.example.workflow.dto.ApproveDTO;
import cn.hsa.example.workflow.dto.DailyAttendanceDTO;
import cn.hsa.example.workflow.service.DailyAttendanceService;
import cn.hsa.example.workflow.service.MyProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

    /**
     * 定义忘记打卡流程
     */
    private static final String PROCESS_DEF_NAME = "DailyAttendanceTest";
    @Autowired
    MyProcessService myProcessService;

    @Override
    public void addDailyAttendance(DailyAttendanceDTO dailyAttendanceDto) {

        String userId = dailyAttendanceDto.getUserId();
        String username = dailyAttendanceDto.getUserName();

        String approveId = dailyAttendanceDto.getApproverId();
        String approveName = dailyAttendanceDto.getApproverName();

        //设置开启人
        Actor actor = new Actor();
        actor.setId(userId);
        actor.setName(username);

        //下一个执行人集合点
        ActorSet nextExecutor = new ActorSet();
        Actor executor = new Actor(approveId,approveName);
        nextExecutor.add(executor);

        //自定义变量
        Map<String,String> map = new HashMap<String,String>();
        map.put("startDate",dailyAttendanceDto.getBeginDate());
        map.put("endDate",dailyAttendanceDto.getEndDate());

        myProcessService.startProcess(PROCESS_DEF_NAME,actor,nextExecutor,map);
    }

    /**
     * 查询忘记打卡任务
     */
    public Task queryDailyAttendance(Long taskId){
        return myProcessService.getTaskById(taskId);
    }

    @Override
    public List<Task> queryDailyAttendanceTask(String userID) {
        return myProcessService.queryTaskByUserId(userID);
    }

    @Override
    public void saveApproveInfo(ApproveDTO dto) {
        long taskId = dto.getTaskId();
        //当前节点审核人
        Actor actor = new Actor();
        actor.setId(dto.getApproverId());
        actor.setName(dto.getApproverName());

        // 下一节点执行人集合，这个可根据业务具体情况放入
        ActorSet nextExecutor = new ActorSet();
        Actor executor = new Actor(dto.getReviewerId(),dto.getReviewerName());
        nextExecutor.add(executor);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        Map map = new HashMap();
        if("组长审核".equals(dto.getNodeName())){
            map.put("groupManagerResult",dto.getApproverResults());
            map.put("groupManagerRemarks",dto.getApproverRemarks());
            map.put("groupManagerDate",df.format(new Date()));
            map.put("groupManager",dto.getApproverName());
        }else if("经理审批".equals(dto.getNodeName())){
            map.put("orgManagerResult",dto.getApproverResults());
            map.put("orgManagerRemarks",dto.getApproverRemarks());
            map.put("orgManagerDate", df.format(new Date()));
            map.put("orgManager", dto.getApproverName());
        }
        myProcessService.commitTask(taskId,actor,nextExecutor,map);
    }

    @Override
    public void cancelDailyAttendance(Long proId) {
        myProcessService.retraceProcess(proId);
    }
}

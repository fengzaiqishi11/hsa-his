package cn.hsa.example.workflow.service;

import cn.hsa.cep.wfc.pojo.Task;
import cn.hsa.example.workflow.dto.ApproveDTO;
import cn.hsa.example.workflow.dto.DailyAttendanceDTO;

import java.util.List;

/**
 * 忘记打卡流程接口方法定义
 */
public interface DailyAttendanceService {

    /**
     * 申请忘记打卡，开始执行流程操作
     * @param dailyAttendanceDto
     */
    public void addDailyAttendance(DailyAttendanceDTO dailyAttendanceDto);

    /**
     * 通过任务ID查询任务
     * @param taskId 任务ID
     * @return 返回任务详情
     */
    public Task queryDailyAttendance(Long taskId);

    /**
     * 通过用户ID查询待办任务
     * @param userID 用户ID
     * @return 返回任务列表
     */
    public List<Task> queryDailyAttendanceTask(String userID);

    /**
     * 处理当前任务，完成审批
     * @param dto
     */
    public void saveApproveInfo(ApproveDTO dto);

    /**
     * 取消忘记打卡
     * @param proId 流程实例ID
     */
    public void cancelDailyAttendance(Long proId);

}

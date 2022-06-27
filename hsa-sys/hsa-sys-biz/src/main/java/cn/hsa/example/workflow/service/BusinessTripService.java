package cn.hsa.example.workflow.service;


import cn.hsa.example.workflow.dto.ApproveDTO;
import cn.hsa.example.workflow.dto.BusinessTripDTO;
import cn.hsa.example.workflow.dto.ProcessDTO;
import cn.hsa.example.workflow.dto.TaskDTO;

import java.util.List;
import java.util.Map;

/**
 * 出差申请服务
 * @Author zhangjian
 * @Date 2019/8/17 10:35
 * @Version 1.0
 */
public interface BusinessTripService {
    /**
     * 新增出差流程
     * @param dto
     * @return
     */
    void addBusinessTripInfo(BusinessTripDTO dto);

    /**
     * 查询流程实例信息
     * @param userId
     * @return
     */
    List<ProcessDTO> queryBusinessTripProcess(String userId);

    /**
     * 查询任务列表
     * @param userId
     * @return
     */
    List<TaskDTO> queryBusinessTripTask(String userId);

    /**
     * 查询任务明细信息
     * @param taskId
     * @return
     */
    TaskDTO queryBusinessTripInfo(long taskId, long processId);
    /**
     * 查询任务变量信息
     * @param processId
     * @return
     */
    Map<String,Object> getTaskVariables(long processId);

    /**
     * 保存审批信息
     * @param dto
     * @return
     */
    void saveApproveInfo(ApproveDTO dto);


    /**
     * 根据流程实例id查询流程历史任务
     * @param processId
     * @return
     */
    List<TaskDTO> queryProcessHistory(long processId);
}

//package cn.hsa.example.workflow.controller;
//
//import cn.hsa.example.workflow.dto.ApproveDTO;
//import cn.hsa.example.workflow.dto.BusinessTripDTO;
//import cn.hsa.example.workflow.dto.ProcessDTO;
//import cn.hsa.example.workflow.dto.TaskDTO;
//import cn.hsa.example.workflow.service.BusinessTripService;
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author zhangjian
// * @Date 2019/8/17 10:35
// * @Version 1.0
// */
//@RestController
//@CrossOrigin
//@RequestMapping("/web/businessTrip")
//@Api(tags = "工作流演示")
//public class BusinessTripController {
//
//    @Autowired
//    private BusinessTripService businessTripService;
//    /**
//     * 新增出差申请信息
//     * @return
//     */
//    @PostMapping("/addBusinessTrip")
//    public WrapperResponse<String> addBusinessTripInfo(@RequestBody(required = false) BusinessTripDTO dto) throws Exception {
//        businessTripService.addBusinessTripInfo(dto);
//        return WrapperResponse.success("保存成功");
//    }
//
//    /**
//     * 查询出差流程实例信息
//     * @return
//     */
//    @GetMapping("/queryBusinessTripProcess")
//    public WrapperResponse<List<ProcessDTO>> queryBusinessTripProcess(@RequestParam(value = "userId") String userId) throws Exception {
//        List<ProcessDTO> reList = businessTripService.queryBusinessTripProcess(userId);
//        reList.sort(Comparator.comparing(ProcessDTO::getId).reversed());
//        return WrapperResponse.success(reList);
//    }
//
//    /**
//     * 查询待办任务信息
//     * @return
//     */
//    @GetMapping("/queryBusinessTripTask")
//    public WrapperResponse queryBusinessTripTask(@RequestParam(value = "userId") String userId) throws Exception {
//        List<TaskDTO> reList = businessTripService.queryBusinessTripTask(userId);
//        reList.sort(Comparator.comparing(TaskDTO::getCreateDate).reversed());
//        return WrapperResponse.success(reList);
//    }
//
//    /**
//     * 查询任务明细信息
//     * @return
//     */
//    @GetMapping("/queryBusinessTrip")
//    public WrapperResponse queryBusinessTripInfo(@RequestParam(value = "taskId") long taskId, @RequestParam(value = "processId") long processId) throws Exception {
//        TaskDTO taskDto = businessTripService.queryBusinessTripInfo(taskId,processId);
//        return WrapperResponse.success(taskDto);
//    }
//
//    /**
//     * 保存审批信息
//     * @return
//     */
//    @PostMapping("/saveApproveInfo")
//    public WrapperResponse saveApproveInfo(@RequestBody(required = false) ApproveDTO dto) throws Exception {
//        businessTripService.saveApproveInfo(dto);
//        return WrapperResponse.success("保存成功");
//    }
//
//    /**
//     * 查询流程实例执行历史
//     * @return
//     */
//    @GetMapping("/queryProcessHistory")
//    public WrapperResponse queryProcessHistory(@RequestParam(value = "processId") long processId) throws Exception {
//        List<TaskDTO> taskList = businessTripService.queryProcessHistory(processId);
//        taskList.sort(Comparator.comparing(TaskDTO::getId));
//        return WrapperResponse.success(taskList);
//    }
//
//    /**
//     * 查询流程的变量信息
//     * @return
//     */
//    @GetMapping("/queryProcessIdVariables")
//    public WrapperResponse getProcessIdVariables(@RequestParam(value = "processId") long processId) throws Exception {
//        Map<String,Object> map = businessTripService.getTaskVariables(processId);
//        return WrapperResponse.success(map);
//    }
//
//
//}

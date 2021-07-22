package cn.hsa.example.workflow.controller;

import cn.hsa.cep.wfc.pojo.Task;
import cn.hsa.example.workflow.dto.ApproveDTO;
import cn.hsa.example.workflow.dto.DailyAttendanceDTO;
import cn.hsa.example.workflow.service.DailyAttendanceService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/web/dailyAttendance")
@Api(tags = "工作流演示-忘记打卡")
public class DailyAttendanceController {

    @Autowired
    DailyAttendanceService dailyAttendanceService;

    /**
     * 新增忘记打卡申请
     * @return
     */
    @PostMapping("/addDailyAttendance")
    public WrapperResponse<String> addDailyAttendance(DailyAttendanceDTO dailyAttendanceDto){

        dailyAttendanceService.addDailyAttendance(dailyAttendanceDto);

        return WrapperResponse.success("保存成功");
    }

    /**
     * 查询忘记打卡实例信息
     * @return
     */
    @PostMapping("/queryDailyAttendance")
    public WrapperResponse<Task> queryDailyAttendance(Long taskId){

        Task task = dailyAttendanceService.queryDailyAttendance(taskId);

        return WrapperResponse.success(task);
    }

    /**
     * 查看待办任务信息
     * @return
     */
    @PostMapping("/queryDailyAttendanceTask")
    public WrapperResponse queryDailyAttendanceTask(String userId){

        List<Task> tasks = dailyAttendanceService.queryDailyAttendanceTask(userId);

        return WrapperResponse.success(tasks);
    }

    /**
     * 保存审批
     * @param dto 审批人实例对象
     * @return
     */
    @PostMapping("/saveApproveInfo")
    public WrapperResponse saveApproveInfo(ApproveDTO dto){

        dailyAttendanceService.saveApproveInfo(dto);

        return WrapperResponse.success("保存成功");
    }

    /**
     * 取消流程成功
     * @param proid
     * @return
     */
    @PostMapping("/cancelFlow")
    public WrapperResponse cancelFlow(Long proid){

        dailyAttendanceService.cancelDailyAttendance(proid);

        return WrapperResponse.success("取消成功");
    }

}

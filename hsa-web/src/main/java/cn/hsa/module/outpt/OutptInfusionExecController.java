package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infusionExec.service.OutptInfusionExecService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptInfusionExecController
 * @Describe: 门诊输液执行控制器
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@RestController
@RequestMapping("/web/outpt/outptInfusionExec")
public class OutptInfusionExecController extends BaseController {

    @Resource
    private OutptInfusionExecService outptInfusionExecService_consumer;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: outptPrescribeDetailsDTO 门诊处方明细DTO传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptPrescribeDetailsDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptPrescribeDetailsDTO", outptPrescribeDetailsDTO);
        return outptInfusionExecService_consumer.queryPage(map);
    }

    /**
     * @Menthod: updateExec()
     * @Desrciption: 执行签名(签名 、 取消)
     * @Param: List<OutptPrescribeExecDTO> execDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/updateExec")
    public WrapperResponse<Boolean> updateExec(@RequestBody List<OutptPrescribeExecDTO> execDTOS,HttpServletRequest req, HttpServletResponse res) {
        List<OutptPrescribeExecDTO> execList = new ArrayList<>();
        List<OutptPrescribeExecDTO> cancelList = new ArrayList<>();
        SysUserDTO userDTO = getSession(req, res) ;
        //封装参数
        for (OutptPrescribeExecDTO execDTO : execDTOS) {
            execDTO.setHospCode(userDTO.getHospCode());
            if ("2".equals(execDTO.getSignCode())) {

                if (!StringUtils.isEmpty(execDTO.getExecDate())) {
                    //将页面选择的String日期格式转换中数据库Data类型
                    String execDate = execDTO.getExecDate();
                    Date date = DateUtils.parse(execDate, "yyyy-MM-dd HH:mm:ss");
                    execDTO.setExecTime(date);
                }
                if (execDTO.getExecTime() == null) {
                    execDTO.setExecTime(DateUtils.getNow());
                }
                if (execDTO.getExecId() == null) {
                    execDTO.setExecId(userDTO.getId());
                }
                if (execDTO.getExecName() == null) {
                    execDTO.setExecName(userDTO.getName());
                }
                execList.add(execDTO);
            } else {
                cancelList.add(execDTO);
            }
        }

        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("execList", execList);
        map.put("cancelList", cancelList);
        return outptInfusionExecService_consumer.updateExec(map);
    }

    /**
     * @Menthod: update()
     * @Desrciption: 输液执行
     * @Param: opdIds-处方明细idList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody OutptPrescribeDetailsDTO outptPrescribeDetailsDTO,HttpServletRequest req, HttpServletResponse res) {
        List<String> opdIds = outptPrescribeDetailsDTO.getOpdIds();
        SysUserDTO userDTO = getSession(req, res) ;
        //封装Sql参数
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("opdIds", opdIds);
        map.put("execId", userDTO.getId());
        map.put("execName", userDTO.getName());
        map.put("execDeptId", userDTO.getDeptId());
        map.put("execDate", DateUtils.getNow());
        return outptInfusionExecService_consumer.update(map);
    }

    /**
     * @Menthod: remove()
     * @Desrciption: 取消输液执行
     * @Param: opdIds-处方明细idList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/remove")
    public WrapperResponse<Boolean> remove(@RequestBody OutptPrescribeDetailsDTO outptPrescribeDetailsDTO,HttpServletRequest req, HttpServletResponse res) {
        List<String> opdIds = outptPrescribeDetailsDTO.getOpdIds();
        //封装Sql参数
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        map.put("opdIds", opdIds);
        map.put("execId", userDTO.getId());
        map.put("execName", userDTO.getName());
        map.put("exec_dept_id", userDTO.getDeptId());
        map.put("execDate", DateUtils.getNow());
        return outptInfusionExecService_consumer.remove(map);
    }
}

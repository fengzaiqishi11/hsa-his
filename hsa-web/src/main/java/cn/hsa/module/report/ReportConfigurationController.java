package cn.hsa.module.report;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import cn.hsa.module.report.config.service.ReportConfigurationService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ReportConfigurationController
 * @Deacription 控制层
 * @Author liuzhoting
 * @Date 2022-02-16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/web/report/config")
public class ReportConfigurationController extends BaseController {

    @Resource
    private ReportConfigurationService reportConfigurationService_consumer;

    @ApiOperation("分页查询")
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(ReportConfigurationDTO reportConfigurationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap(2);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("reportConfigurationDTO", reportConfigurationDTO);
        return reportConfigurationService_consumer.queryPage(map);
    }

    @ApiOperation("新增")
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody ReportConfigurationDTO reportConfigurationDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        reportConfigurationDTO.setHospCode(sysUserDTO.getHospCode());
        reportConfigurationDTO.setCrterId(sysUserDTO.getId());
        reportConfigurationDTO.setCrterName(sysUserDTO.getName());
        reportConfigurationDTO.setCrteTime(DateUtils.getNow());
        reportConfigurationDTO.setUpdtTime(reportConfigurationDTO.getCrteTime());
        Map map = new HashMap(2);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("reportConfigurationDTO", reportConfigurationDTO);
        return reportConfigurationService_consumer.insert(map);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody ReportConfigurationDTO reportConfigurationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        reportConfigurationDTO.setUpdtTime(DateUtils.getNow());
        Map map = new HashMap(2);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("reportConfigurationDTO", reportConfigurationDTO);
        return reportConfigurationService_consumer.update(map);
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody ReportConfigurationDTO reportConfigurationDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap(2);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("reportConfigurationDTO", reportConfigurationDTO);
        return reportConfigurationService_consumer.delete(map);
    }

}


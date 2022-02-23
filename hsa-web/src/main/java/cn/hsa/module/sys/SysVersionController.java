package cn.hsa.module.sys;

import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.hsa.base.BaseController;
import org.springframework.web.bind.annotation.RestController;
import cn.hsa.module.sys.version.service.VersionInfoService;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SysVersionController
 * @Deacription 控制层
 * @Author liuhuiming
 * @Date 2022-02-10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/web/sys/versionInfo")
public class SysVersionController extends BaseController {

    @Resource
    private VersionInfoService sysVersionInfoService_consumer;

    @ApiOperation("分页查询")
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(VersionInfoDTO versionInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("versionInfoDTO",versionInfoDTO);
        return sysVersionInfoService_consumer.queryVersionInfoListByPage(map);
    }

    @ApiOperation("查询最新的一条版本信息")
    @GetMapping("/queryVersionInfo")
    public WrapperResponse<VersionInfoDTO> queryVersionInfo(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        return sysVersionInfoService_consumer.queryVersionInfo(map);
    }

    @ApiOperation("新增")
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody VersionInfoDTO versionInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("versionInfoDTO",versionInfoDTO);
        return sysVersionInfoService_consumer.saveVersionInfo(map);
    }

}


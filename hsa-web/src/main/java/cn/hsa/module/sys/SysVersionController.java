package cn.hsa.module.sys;

import cn.hsa.base.PageDO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
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
import java.util.*;

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

    /**
     *   查询历史升级公告消息
     * @param req
     * @param res
     * @param pageNo 分页号
     * @return
     */
    @GetMapping("/queryHistoryVersionInfo/{pageNo}")
    public WrapperResponse<PageDTO> queryHistoryVersionInfo(HttpServletRequest req,HttpServletResponse res, @PathVariable("pageNo") int pageNo) {

        SysUserDTO sysUserDTO = getSession(req, res);
        VersionInfoDTO versionInfoDTO = new VersionInfoDTO();
        Map<String,Object> map = new HashMap<>();
        versionInfoDTO.setPageNo(pageNo);
        map.put("hospCode",sysUserDTO.getHospCode());
        versionInfoDTO.setHospCode(sysUserDTO.getHospCode());
        versionInfoDTO.setUserId(sysUserDTO.getId());
        map.put("versionInfoDTO",versionInfoDTO);

        return sysVersionInfoService_consumer.queryHistoryVersionInfo(map);
    }

    /**
     *  更新公告信息为已读状态 ,必传参数messageIds
     * @param req
     * @param res
     * @param params 参数名
     * @return
     */
    @PostMapping("/updateStatus2Read")
    public  WrapperResponse<Boolean> updateStatus2Read(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,Object> params){
        SysUserDTO sysUserDTO = getSession(req, res);
        String messageIdsStr =  MapUtils.get(params,"messageIds");
        if(null == messageIdsStr){
            throw new AppException("必传参数【messageIds】不能为空,多条以','分隔");
        }
        params.put("hospCode",sysUserDTO.getHospCode());
        params.put("userId",sysUserDTO.getId());
        return sysVersionInfoService_consumer.updateStatus2Read(params);
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


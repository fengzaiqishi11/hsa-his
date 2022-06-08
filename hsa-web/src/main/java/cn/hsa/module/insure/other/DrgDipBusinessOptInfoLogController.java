package cn.hsa.module.insure.other;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.drgdip.service.DrgDipBusinessOptInfoLogService;
import cn.hsa.module.insure.other.dto.PolicyRequestDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/web/insure/drgdip")
@Slf4j
public class DrgDipBusinessOptInfoLogController extends BaseController {
    @Resource
    DrgDipBusinessOptInfoLogService drgDipBusinessOptInfoLogService_consumer;
    @PostMapping("/drgDipBusinessOptInfoLog")
    public WrapperResponse<Boolean> insertDrgDipBusinessOptInfoLog(@RequestBody  Map<String,Object> map,
                                                      HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return drgDipBusinessOptInfoLogService_consumer.insertDrgDipBusinessOptInfoLog(map);
    }
}

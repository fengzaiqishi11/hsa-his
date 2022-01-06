package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedLogController
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 14:02
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedLog")
@Slf4j
public class InsureUnifiedLogController extends BaseController {

    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;

    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureFunctionLogDO insureFunctionLogDO, HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        map.put("hospCode", hospCode);
        insureFunctionLogDO.setHospCode(hospCode);
        map.put("insureFunctionLogDO",insureFunctionLogDO);
        return insureUnifiedLogService_consumer.queryPage(map);
    }
    
    /**
     * @Method selectInsureLogs
     * @Desrciption  his日志转医保日志入参
     * @Param 
     * 
     * @Author fuhui
     * @Date   2022/1/4 9:37 
     * @Return 
    **/
    @PostMapping("/selectInsureLogs")
    public WrapperResponse<String> selectInsureLogs(@RequestBody InsureFunctionLogDO insureFunctionLogDO, HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        map.put("hospCode", hospCode);
        insureFunctionLogDO.setHospCode(hospCode);
        map.put("insureFunctionLogDO",insureFunctionLogDO);
        return insureUnifiedLogService_consumer.selectInsureLogs(map);
    }

}

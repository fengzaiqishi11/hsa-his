package cn.hsa.module.aop;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;
import cn.hsa.module.sys.log.service.HisLogInfoCzService;
import cn.hsa.module.sys.log.service.HisLogInfoYcService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name:cn.hsa.module.aop
 * @Class_name:HisLogInfoController
 * @Project_name:hsa-his
 * @Describe: 日志服务
 * @Author: zibo.yuan
 * @Date: 2020/12/2 9:03
 * @Email: zibo.yuan@powersi.com.cn
 */
@RestController
@RequestMapping("/web/log/logInfo")
@Slf4j
public class HisLogInfoController  extends BaseController {

    @Resource
    private HisLogInfoCzService hisLogInfoCzService_consumer;

    @Resource
    private HisLogInfoYcService hisLogInfoYcService_consumer;

    /**
    * @Method queryLogInfo
    * @Desrciption 日志信息查询
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/9 15:24
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryLogInfo")
    public WrapperResponse<PageDTO> queryLogInfo(HisLogInfoYcDTO hisLogInfoYcDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        hisLogInfoYcDTO.setHospCode(sysUserDTO.getHospCode());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("hisLogInfoYcDTO", hisLogInfoYcDTO);

        WrapperResponse<PageDTO> response = hisLogInfoYcService_consumer.queryLogInfo(map);
        return response;
    }


    /**
    * @Method queryLogFileInfo
    * @Desrciption 日志信息文件查询
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/11 15:07
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryLogFileInfo")
    public WrapperResponse<PageDTO> queryLogFileInfo(HisLogInfoYcDTO hisLogInfoYcDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        hisLogInfoYcDTO.setHospCode(sysUserDTO.getHospCode());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("hisLogInfoYcDTO", hisLogInfoYcDTO);

        WrapperResponse<PageDTO> response = hisLogInfoYcService_consumer.queryLogFileInfo(map);
        return response;
    }

}

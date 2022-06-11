package cn.hsa.module.insure.other;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.drgdip.service.DrgDipBusinessOptInfoLogService;
import cn.hsa.module.insure.drgdip.dto.DrgDipBusinessOptInfoLogDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipBusinessOptInfoLogDO;
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

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 13:56
     * @Description 查询dip、drg质控过程日志记录
     * @param drgDipBusinessOptInfoLogDO
     * @param req
     * @param res
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @PostMapping("/queryDrgDipBusinessOptInfoLogList")
    public WrapperResponse<PageDTO> queryDrgDipBusinessOptInfoLogList(@RequestBody DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO,
                                                                      HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        drgDipBusinessOptInfoLogDO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("drgDipBusinessOptInfoLogDO",drgDipBusinessOptInfoLogDO);
        return drgDipBusinessOptInfoLogService_consumer.queryDrgDipBusinessOptInfoLogList(map);
    }

    @PostMapping("/getDrgDipBusinessOptInfoLogDetail")
    public WrapperResponse<PageDTO> getDrgDipBusinessOptInfoLogDetail(@RequestBody DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO,
                                                                      HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        drgDipBusinessOptInfoLogDO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("drgDipBusinessOptInfoLogDO",drgDipBusinessOptInfoLogDO);
        return drgDipBusinessOptInfoLogService_consumer.getDrgDipBusinessOptInfoLogDetail(map);
    }

    /**
     * 质控信息查询统计
     * @param drgDipResultDTO
     * @param req
     * @param res
     * @Author 医保开发二部-湛康
     * @Date 2022-06-09 10:11
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @PostMapping("/getDrgDipInfoByParam")
    public WrapperResponse<PageDTO> getDrgDipInfoByParam(@RequestBody DrgDipResultDTO drgDipResultDTO,
                                                         HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      drgDipResultDTO.setHospCode(sysUserDTO.getHospCode());
      Map<String,Object> map = new HashMap<>();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("drgDipResultDTO",drgDipResultDTO);
      return drgDipBusinessOptInfoLogService_consumer.getDrgDipInfoByParam(map);
    }

}

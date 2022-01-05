package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedUniversityService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedUniversityController
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/13 16:09
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedUniversity")
@Slf4j
public class InsureUnifiedUniversityController extends BaseController {
    @Resource
    private InsureUnifiedUniversityService insureUnifiedUniversityService_consumer;

    /**
     * @Method insertUniversityInsure
     * @Desrciption  大学生医保单独结算
     * @Param map：封装包含就诊id：visitId  settleId:结算id
     *
     * @Author fuhui
     * @Date   2021/12/13 16:13
     * @Return
    **/
    @PostMapping("/insertUniversityInsure")
    public WrapperResponse<Boolean> insertUniversityInsure(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteName",sysUserDTO.getName());
        map.put("crteId",sysUserDTO.getUsId());
        return insureUnifiedUniversityService_consumer.insertUniversityInsure(map);
    }
}

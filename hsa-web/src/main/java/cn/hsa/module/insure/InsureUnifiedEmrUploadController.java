package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.service.InsureUnifiedEmrUploadService;
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
 * @class_name: InsureUnifiedEmrUploadController
 * @Description: 电子处方，住院病案首页 ，电子病历 医保上传接业务控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/29 8:58
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedEmrUpload")
@Slf4j
public class InsureUnifiedEmrUploadController extends BaseController {

    @Resource
    InsureUnifiedEmrUploadService insureUnifiedEmrUploadService_comsumer;
    
    /**
     * @Method updateInsureUnifiedMri
     * @Desrciption  医保统一支付平台：住院病案首页信息上传
     * @Param map
     * 
     * @Author fuhui
     * @Date   2021/4/29 10:03 
     * @Return 
    **/
    @PostMapping("/updateInsureUnifiedMri")
    public WrapperResponse<Boolean> updateInsureUnifiedMri(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrUploadService_comsumer.updateInsureUnifiedMri(map);
    }

    /**
     * @Method updateInsureUnifiedPrescrib
     * @Desrciption  医保统一支付平台：电子处方上传
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/29 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedPrescrib")
    public WrapperResponse<Boolean> updateInsureUnifiedPrescrib(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrUploadService_comsumer.updateInsureUnifiedPrescrib(map);
    }

}

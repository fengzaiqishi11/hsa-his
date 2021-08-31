package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.service.InsureUnifiedRemoteService;
import cn.hsa.module.insure.module.entity.InsureUnifiedRemoteDO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedRemoteController
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 13:51
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedRemote")
@Slf4j
public class InsureUnifiedRemoteController extends BaseController {

    @Resource
    InsureUnifiedRemoteService insureUnifiedRemoteService_consumer;

    /**
     * @Method selectRemoteDetail
     * @Desrciption  提取异地清分明细:就医地使用此交易提取省内异地外来就医月度结算清分明细,供医疗机构进行确认处理
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
    **/
    @PostMapping("/insertRemoteDetail")
    public WrapperResponse<Map<String,Object>> insertRemoteDetail(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        return insureUnifiedRemoteService_consumer.selectRemoteDetail(map);
    }

    /**
     * @Method selectRemoteConfirmResult
     * @Desrciption  异地清分结果确认:就医地使用此交易提交省内异地外来就医月度结算清分确认结果。
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    @PostMapping("/selectRemoteConfirmResult")
    public WrapperResponse<Map<String,Object>> selectRemoteConfirmResult(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureUnifiedRemoteService_consumer.selectRemoteConfirmResult(map);
    }

    /**
     * @Method selectRemoteConfirmResult
     * @Desrciption  异地清分结果确认回退:就医地使用此交易回退已经提交的就医月度结算清分确认结果。
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    @PostMapping("/updateRemoteConfirmBack")
    public WrapperResponse<Map<String,Object>> updateRemoteConfirmBack(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureUnifiedRemoteService_consumer.updateRemoteConfirmBack(map);
    }

    /**
     * @Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/8 20:22
     * @Return
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureUnifiedRemoteDO insureUnifiedRemoteDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureUnifiedRemoteDO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureUnifiedRemoteDO",insureUnifiedRemoteDO);
        return insureUnifiedRemoteService_consumer.queryPage(map);
    }
}

package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.lis.service.PacsService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import com.alibaba.fastjson.JSONObject;
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

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: PacsController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/pacs")
@Slf4j
public class PacsController extends BaseController {

    @Resource
    PacsService pacsService;

    /**
     * @Method getPacsDeptList
     * @Desrciption 查询机构下的科室列表
       @params [map]
     * @Author chenjun
     * @Date   2021-01-29 10:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/getPacsDeptList")
    public WrapperResponse<Boolean> getPacsDeptList(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return pacsService.getPacsDeptList(map);
    }

    /**
     * @Method getPacsDocList
     * @Desrciption 人员列表
       @params [map]
     * @Author chenjun
     * @Date   2021-01-29 10:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/getPacsDocList")
    public WrapperResponse<Boolean> getPacsDocList(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return pacsService.getPacsDocList(map);
    }

    /**
     * @Method getPacsItemList
     * @Desrciption 收费项目
       @params [map]
     * @Author chenjun
     * @Date   2021-01-29 10:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/getPacsItemList")
    public WrapperResponse<Boolean> getPacsItemList(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return pacsService.getPacsItemList(map);
    }

    /**
     * @Method savePacsInspectApply
     * @Desrciption 
       @params [map]
     * @Author chenjun
     * @Date   2021-01-29 10:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/savePacsInspectApply")
    public WrapperResponse<Boolean> savePacsInspectApply(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return pacsService.savePacsInspectApply(map);
    }

    /**
     * @Method SaveInspectCallback
     * @Desrciption 结果回传
       @params [map]
     * @Author chenjun
     * @Date   2021-01-29 10:25
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.String>
    **/
    @PostMapping("/SaveInspectCallback")
    public WrapperResponse<String> SaveInspectCallback(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        pacsService.SavePacsInspectCallback(map);
        Map retMap = new HashMap();
        retMap.put("errmsg", "大于0");
        return WrapperResponse.success(JSONObject.toJSONString(retMap));
    }
}
package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;
import cn.hsa.module.outpt.skin.service.OutptSkinService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptSkinController
 * @Description: 皮试处理控制层
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/skin")
@Slf4j
public class OutptSkinController extends BaseController {
    /**
     * 皮试处理dubbo‘消费者接口
     */
    @Resource
    private OutptSkinService outptSkinService_consumer;

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * outptSkinDTO 皮试处理数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020-08-14 11:42
     * @Return map
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.queryPage(map);
    }
    
    /**
     * @Method queryAll
     * @Desrciption  
     * @Param 
     * 
     * @Author zhangxuan
     * @Date   2020-08-14 15:03 
     * @Return 
    **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<OutptSkinDTO>> queryAll(OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.queryAll(map);
    }
    /**
     * @Method getById
     * @Desrciption  根据主键查询皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 15:58
     * @Return map
    **/
    @GetMapping("/getById")
    public WrapperResponse<OutptSkinDTO> getById(OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.getById(map);
    }

    /**
     * @Method insert
     * @Desrciption  新增皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:07
     * @Return map
    **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        outptSkinDTO.setExecId(userDTO.getId());
        outptSkinDTO.setExecName(userDTO.getName());
        outptSkinDTO.setExecDeptId(userDTO.getDeptId());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.insert(map);
    }
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:07
     * @Return map
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        outptSkinDTO.setExecId(userDTO.getId());
        outptSkinDTO.setExecName(userDTO.getName());
        outptSkinDTO.setExecDeptId(userDTO.getDeptId());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.update(map);
    }
    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-14 16:07
     * @Return map
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody OutptSkinDTO outptSkinDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptSkinDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("outptSkinDTO",outptSkinDTO);
        return outptSkinService_consumer.delete(map);
    }


}

package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bdc.dto.BaseDailyfirstCalcDTO;
import cn.hsa.module.base.bdc.service.BaseDailyfirstCalcService;
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
 * @Package_ame: cn.hsa.module.base
 * @Class_name: BaseDailyfirstCalcController
 * @Description:  首日计费
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/BaseDailyfirstCalc")
@Slf4j
public class BaseDailyfirstCalcController extends BaseController {
    /**
     * 首日计费dubbo消费者接口
     */
    @Resource
    BaseDailyfirstCalcService baseDailyfirstCalcService_consumer;
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<SyncAssistDetailDTO>>
     **/
    @PostMapping("/queryAll")
    public WrapperResponse<List<BaseDailyfirstCalcDTO>> queryAll(@RequestBody BaseDailyfirstCalcDTO baseDailyfirstCalcDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDailyfirstCalcDTO.setHospCode(hospCode);
        baseDailyfirstCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseDailyfirstCalcDTO);
        return baseDailyfirstCalcService_consumer.queryAll(map);
    }
    /**
     * @Method insert()
     * @Description 更新
     * @Param
     * 1、baseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody List<BaseDailyfirstCalcDTO> baseDailyfirstCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        for (int i = 0; i < baseDailyfirstCalcDTO.size(); i++) {
            //baseDailyfirstCalcDTO.get(i).setHospCode(hospCode);
            baseDailyfirstCalcDTO.get(i).setHospCode(sysUserDTO.getHospCode());
            //baseDailyfirstCalcDTO.get(i).setCrteId(userId);
            baseDailyfirstCalcDTO.get(i).setCrteId(sysUserDTO.getId());
            //baseDailyfirstCalcDTO.get(i).setCrteName(userName);
            baseDailyfirstCalcDTO.get(i).setCrteName(sysUserDTO.getName());
        }
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseDailyfirstCalcDTO);
        return baseDailyfirstCalcService_consumer.insert(map);
    }
    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * 1、baseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    @PostMapping("/deleteById")
    public WrapperResponse<Boolean> deleteById(@RequestBody BaseDailyfirstCalcDTO baseDailyfirstCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDailyfirstCalcDTO.setHospCode(hospCode);
        baseDailyfirstCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseDailyfirstCalcDTO);
        return baseDailyfirstCalcService_consumer.deleteById(map);
    }
    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * 1、SyncAssistDetailDTO
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseDailyfirstCalcDTO baseDailyfirstCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDailyfirstCalcDTO.setHospCode(hospCode);
        baseDailyfirstCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseDailyfirstCalcDTO);
        return baseDailyfirstCalcService_consumer.queryPage(map);
    }
}
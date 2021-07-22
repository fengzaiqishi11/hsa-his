package cn.hsa.module.base;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bb.dto.BaseBedItemDTO;
import cn.hsa.module.base.bb.service.BaseBedService;
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
 * @Class_name: BaseBedController
 * @Description: 床位管理
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@RestController
@RequestMapping("/web/base/bb")
@Slf4j
public class BaseBedController extends BaseController {
    /**
     * 床位管理dubbo消费者接口
     */
    @Resource
    private BaseBedService baseBedService_consumer;

    /**
     * @param baseBedDTO
     * @Menthod queryAll
     * @Desrciption 通过实体作为筛选条件查询
     * @Author
     * @Date 2020/8/6 10:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.base.bb.dto.BaseBedDTO>>
     **/
    @PostMapping("/queryAll")
    public WrapperResponse<List<BaseBedDTO>> queryAll(@RequestBody BaseBedDTO baseBedDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        //baseBedDTO.setDeptId(loginDeptId);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            baseBedDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        Map map = new HashMap();
       // map.put("hospCode", hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        log.debug("BaseBedDTO:{}", baseBedDTO);
        return baseBedService_consumer.queryAll(map);
    }

    /**
     * @param baseBedDTO
     * @Menthod insert
     * @Desrciption 新增
     * @Author
     * @Date 2020/8/6 10:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseBedDTO baseBedDTO,HttpServletRequest req, HttpServletResponse res) throws Exception {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        //baseBedDTO.setCrteId(userId);
        baseBedDTO.setCrteId(sysUserDTO.getId());
        //baseBedDTO.setCrteName(userName);
        baseBedDTO.setCrteName(sysUserDTO.getName());
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        log.debug("BaseBedDTO:{}", baseBedDTO);
        return baseBedService_consumer.insert(map);
    }

    /**
     * @param baseBedDTO
     * @Menthod update
     * @Desrciption 通过实体作为筛选条件查询
     * @Author
     * @Date 2020/8/6 10:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseBedDTO baseBedDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        log.debug("BaseBedDTO:{}", baseBedDTO);
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        return baseBedService_consumer.update(map);
    }

    /**
     * @param baseBedDTO
     * @Menthod updateStatus
     * @Desrciption 更新状态
     * @Author
     * @Date 2020/8/6 10:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> deleteById(@RequestBody BaseBedDTO baseBedDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        log.debug("id:{}", baseBedDTO);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        return baseBedService_consumer.updateStatus(map);
    }

    /**
     * @param baseBedDTO
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Author
     * @Date 2020/8/6 10:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseBedDTO baseBedDTO,HttpServletRequest req, HttpServletResponse res) {
       //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        return baseBedService_consumer.queryPage(map);
    }


    /**
     * @param baseBedItemDTO
     * @Menthod itemqueryPage
     * @Desrciption 分页查询
     * @Author
     * @Date 2020/8/6 10:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/ItemqueryPage")
    public WrapperResponse<PageDTO> itemqueryPage(BaseBedItemDTO baseBedItemDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedItemDTO.setHospCode(hospCode);
        baseBedItemDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedItemDTO);
        return baseBedService_consumer.itemqueryPage(map);
    }

    @GetMapping("/getMaxSeq")
    public WrapperResponse<Integer> getMaxSeq(BaseBedDTO baseBedDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseBedDTO.setHospCode(hospCode);
        baseBedDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", baseBedDTO);
        return baseBedService_consumer.getMaxSeq(map);
    }
}

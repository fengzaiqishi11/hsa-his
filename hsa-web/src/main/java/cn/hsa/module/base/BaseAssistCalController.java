package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bac.service.BaseAssistCalcService;
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
 * @Package_ame: cn.hsa.web.base
 * @Class_name: BaseAssistCalcDetailController
 * @Description:  辅助计费分类Controller层
 * @Author: ljh
 * @Email: 307753910@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/bac")
@Slf4j
public class BaseAssistCalController  extends BaseController {
    /**
     * 辅助计费dubbo消费者接口
     */
    @Resource
    private BaseAssistCalcService baseAssistCalcService;
    /**
     * @Menthod queryAll
     * @Desrciption 查询
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>>
     **/
    @PostMapping("/queryAll")
    public WrapperResponse<List<BaseAssistCalcDTO>> queryAll(@RequestBody BaseAssistCalcDTO baseAssistCalcDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
       // map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDTO);
        return baseAssistCalcService.queryAll(map);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseAssistCalcDTO baseAssistCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseAssistCalcDTO.setHospCode(hospCode);
        baseAssistCalcDTO.setHospCode(sysUserDTO.getHospCode());
        //baseAssistCalcDTO.setCrteId(userId);
        baseAssistCalcDTO.setCrteId(sysUserDTO.getId());
        //baseAssistCalcDTO.setCrteName(userName);
        baseAssistCalcDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDTO);
        return baseAssistCalcService.insert(map);
    }

    /**
     * @Menthod update
     * @Desrciption 更新
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseAssistCalcDTO baseAssistCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseAssistCalcDTO.setHospCode(hospCode);
        baseAssistCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDTO);
        return baseAssistCalcService.update(map);
    }



    /**
    * @Menthod deleteById
    * @Desrciption   删除
     * @param baseAssistCalcDTO
    * @Author ljh
    * @Date   2020/8/6 10:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> deleteById(@RequestBody BaseAssistCalcDTO baseAssistCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseAssistCalcDTO.setHospCode(hospCode);
        baseAssistCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDTO);
        return baseAssistCalcService.updateStatus(map);
    }
    /**
     * @Menthod queryPage
     * @Desrciption   分页
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseAssistCalcDTO baseAssistCalcDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseAssistCalcDTO.setHospCode(hospCode);
        baseAssistCalcDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
       // map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDTO);
        return baseAssistCalcService.queryPage(map);
    }
    /**
     * @Menthod detailqueryPage
     * @Desrciption 查询明细
     * @param baseAssistCalcDetailDTO
     * @Author ljh
     * @Date   2020/8/6 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/detailqueryPage")
    public WrapperResponse<PageDTO> detailqueryPage( BaseAssistCalcDetailDTO baseAssistCalcDetailDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseAssistCalcDetailDTO.setHospCode(hospCode);
        baseAssistCalcDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",baseAssistCalcDetailDTO);
        return baseAssistCalcService.detailqueryPage(map);

    }
}

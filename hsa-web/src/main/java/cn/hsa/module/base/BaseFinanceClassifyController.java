package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bfc.service.BaseFinanceClassifyService;
import cn.hsa.module.sys.log.dto.SysLogDTO;
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
 * @Class_name: BaseFinanceClassifyController
 * @Description: 财务分类控制层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseFinanceClassify")
@Slf4j
public class BaseFinanceClassifyController  extends BaseController {
    /**
     * 财务分类dubbo消费者接口
     */
    @Resource
    private BaseFinanceClassifyService baseFinanceClassifyService_consumer;

    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseFinanceClassifyDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseFinanceClassifyDTO> getById(BaseFinanceClassifyDTO baseFinanceClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.getById(map);
    }

    /**
     * @Menthod queryTree
     * @Desrciption 财务分类树状查询
     * @param
     * @Author xingyu.xie
     * @Date   2020/7/18 10:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>>
     **/
    @GetMapping("/queryTree")
    public WrapperResponse<List<TreeMenuNode>> queryTree(BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.queryTree(map);
    }

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  
     * @param 
    * @Author xingyu.xie
    * @Date   2020/7/18 10:42 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>>
    **/
    @GetMapping("/queryDropDownEnd")
    public WrapperResponse<List<BaseFinanceClassifyDTO>> queryDropDownEnd(HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        return baseFinanceClassifyService_consumer.queryDropDownEnd(map);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);

        return baseFinanceClassifyService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll()
     * @Description 查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseFinanceClassifyDTO>> queryAll(BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.queryAll(map);
    }

    /**
     * @Method insert()
     * @Description 新增或修改单条财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        //baseFinanceClassifyDTO.setCrteName(userName);
        baseFinanceClassifyDTO.setCrteName(sysUserDTO.getName());
        //baseFinanceClassifyDTO.setCrteId(userId);
        baseFinanceClassifyDTO.setCrteId(sysUserDTO.getId());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.save(map);
    }


    /**
     * @Method updateStatus()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、ids：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.updateStatus(map);
    }

    /**
    * @Menthod isNameExist
    * @Desrciption  判断财务分类名称是否重复
     * @param baseFinanceClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/11/25 16:47 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/isNameExist")
    public WrapperResponse<Boolean> isNameExist(@RequestBody BaseFinanceClassifyDTO baseFinanceClassifyDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseFinanceClassifyDTO",baseFinanceClassifyDTO);
        return baseFinanceClassifyService_consumer.isNameExist(map);
    }
}

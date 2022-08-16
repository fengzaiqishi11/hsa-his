package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseProfileFileController
 * @Describe: 本地档案管理controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-06 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/web/base/baseProfileFile")
public class BaseProfileFileController extends BaseController {

    @Resource
    private BaseProfileFileService baseProfileFileService_consumer;

    /**
     * @Menthod: save
     * @Desrciption: 档案新增、编辑
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-06 14:42
     * @Return: Boolean
     **/
    @PostMapping("/save")
    public WrapperResponse<OutptProfileFileDTO> save(@RequestBody OutptProfileFileDTO outptProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outptProfileFileDTO.setHospCode(sysUserDTO.getHospCode());
        outptProfileFileDTO.setCrteId(sysUserDTO.getId());
        outptProfileFileDTO.setCrteName(sysUserDTO.getName());
        outptProfileFileDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.save(map);
    }

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outptProfileFileDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.queryPage(map);
    }

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/isCertNoExist")
    public WrapperResponse<Boolean> isCertNoExist(OutptProfileFileDTO outptProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(outptProfileFileDTO.getCertNo())){
            throw new RuntimeException("未检查到证件号");
        }
        outptProfileFileDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.isCertNoExist(map);
    }

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<OutptProfileFileDTO> getById(OutptProfileFileDTO outptProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outptProfileFileDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.getById(map);
    }

    /**
     * @Method getAddressTree
     * @Desrciption  获取地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/getAddressTree")
    public WrapperResponse<List<TreeMenuNode>> getAddressTree(OutptProfileFileDTO outptProfileFileDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outptProfileFileDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.getAddressTree(map);
    }

    @GetMapping("/queryAll")
    public WrapperResponse<List<OutptProfileFileDTO>> queryAll(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptProfileFileDTO", outptProfileFileDTO);
        return baseProfileFileService_consumer.queryAll(map);
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param[id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/deleteProfileFile")
    public WrapperResponse<Boolean> deleteProfileFile(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("id",id);
        return baseProfileFileService_consumer.deleteProfileFile(map);
    }
}

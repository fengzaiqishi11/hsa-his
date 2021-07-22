package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.careTemplate.dto.InptNurseTemplateDTO;
import cn.hsa.module.inpt.careTemplate.service.InptNurseTemplateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
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
 * @PackageName: cn.hsa.module.inpt
 * @Class_name: InptNurseTemplateController
 * @Description: 护理模板控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 14:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/inptNurseTemplate")
@Slf4j
public class InptNurseTemplateController extends BaseController {

    @Resource
    private InptNurseTemplateService inptNurseTemplateService_consumer;

    /**
     * @Method queryPage
     * @Desrciption  分页查询所有的护理模板记录
     *      * @Param 护理模板数据传输对象
     *      *
     *      * @Author fuhui
     *      * @Date   2020/9/16 21:01
     *      * @Return pageDTO 分页对象
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptNurseTemplateDTO",inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.queryPage(map);
    }
    /**
     * @Method getById
     * @Desrciption  根据id查询护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return pageDTO 分页对象
     **/
    @GetMapping("/getById")
    public WrapperResponse<InptNurseTemplateDTO> getById(InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptNurseTemplateDTO.getId())){
            throw new AppException("编辑查询参数为空");
        }
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptNurseTemplateDTO",inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.getById(map);
    }
    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  修改护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> updateInptNurseTemplate(@RequestBody InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptNurseTemplateDTO.getId())) {
            throw new AppException("修改护理模板的参数为空");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setCrteId(sysUserDTO.getId());
        inptNurseTemplateDTO.setCrteName(sysUserDTO.getName());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptNurseTemplateDTO",inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.updateInptNurseTemplate(map);
    }

    /**
     * @Method updateInptNurseTemplate
     * @Desrciption  作废护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @PostMapping("/updateIsValid")
    public WrapperResponse<Boolean> updateIsValidInptNurseTemplate(@RequestBody InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptNurseTemplateDTO.getId())){
            throw new AppException("修改护理模板的参数为空");
        }
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptNurseTemplateDTO",inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.updateIsValidInptNurseTemplate(map);
    }
    /**
     * @Method addInptNurseTemplate
     * @Desrciption  新增护理模板记录
     * @Param 护理模板数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 21:01
     * @Return boolean
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> addInptNurseTemplate(@RequestBody InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inptNurseTemplateDTO ==null){
            throw new AppException("新增护理模板的参数为空");
        }
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setCrteId(sysUserDTO.getId());
        inptNurseTemplateDTO.setCrteName(sysUserDTO.getName());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptNurseTemplateDTO",inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.addInptNurseTemplate(map);
    }

    /**
     * @Method queryAllTemplate
     * @Desrciption  查询所有护理模板
     * @Param inptNurseTemplateDTO
     *
     * @Author luoyong
     * @Date   2020/9/16 21:01
     * @Return List<InptNurseTemplateDTO>
     **/
    @GetMapping("/queryAllTemplate")
    public WrapperResponse<List<InptNurseTemplateDTO>> queryAllTemplate(InptNurseTemplateDTO inptNurseTemplateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        inptNurseTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inptNurseTemplateDTO.setIsValid(Constants.SF.S);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseTemplateDTO", inptNurseTemplateDTO);
        return inptNurseTemplateService_consumer.queryAllTemplate(map);
    }
}

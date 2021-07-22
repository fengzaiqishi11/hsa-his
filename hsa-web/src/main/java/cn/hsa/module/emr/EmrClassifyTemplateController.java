package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.module.emr.emrclassifytemplate.service.EmrClassifyTemplateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name:: EmrClassifyTemplateController
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/10/12 16:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/emr/emrClassifyTemplate")
@Slf4j
public class EmrClassifyTemplateController extends BaseController {
    @Resource
    private EmrClassifyTemplateService emrClassifyTemplateService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过模板id查询模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/21 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<EmrClassifyTemplateDTO> getById(EmrClassifyTemplateDTO emrClassifyTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("emrClassifyTemplateDTO", emrClassifyTemplateDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrClassifyTemplateService_consumer.getById(map);
    }

    /**
     * @Method save
     * @Desrciption 派发模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody EmrClassifyTemplateDTO emrClassifyTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        emrClassifyTemplateDTO.setCrteId(sysUserDTO.getId());
        emrClassifyTemplateDTO.setCrteName(sysUserDTO.getName());
        emrClassifyTemplateDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("emrClassifyTemplateDTO", emrClassifyTemplateDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrClassifyTemplateService_consumer.save(map);
    }

    /**
     * @Method saveTemplate
     * @Desrciption 新增、修改模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
     **/
    @PostMapping("/saveTemplate")
    public WrapperResponse<EmrClassifyTemplateDTO> saveTemplate(@RequestBody EmrClassifyTemplateDTO emrClassifyTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        emrClassifyTemplateDTO.setCrteId(sysUserDTO.getId());
        emrClassifyTemplateDTO.setCrteName(sysUserDTO.getName());
        emrClassifyTemplateDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyTemplateDTO", emrClassifyTemplateDTO);
        return this.emrClassifyTemplateService_consumer.saveTemplate(map);
    }

    /**
     * @Method queryCheckCodes
     * @Desrciption 查询已经派发并且有效的模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.lang.String>>
     **/
    @GetMapping("/queryCheckCodes")
    public WrapperResponse<List<String>> queryCheckCodes(EmrClassifyTemplateDTO emrClassifyTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyTemplateDTO", emrClassifyTemplateDTO);
        return this.emrClassifyTemplateService_consumer.queryCheckCodes(map);
    }

    /**
     * @Method queryTemplateTree
     * @Desrciption 查询模板树
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("queryTemplateTree")
    public WrapperResponse<List<TreeMenuNode>> queryTemplateTree(EmrClassifyTemplateDTO emrClassifyTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("emrClassifyTemplateDTO",emrClassifyTemplateDTO);
        return emrClassifyTemplateService_consumer.queryTemplateTree(map);
    }

}
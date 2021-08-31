package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassify.service.EmrClassifyServcie;
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
 * @Class_name: EmrClassifyController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/27 16:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrClassify")
@Slf4j
public class EmrClassifyController extends BaseController {
    @Resource
    private EmrClassifyServcie emrClassifyServcie_consumer;

    /**
     * @Method getByIdorCode
     * @Desrciption 通过id或者code进行查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    @GetMapping("/getByIdorCode")
    public WrapperResponse<EmrClassifyDTO> getByIdorCode(EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("emrClassifyDTO", emrClassifyDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrClassifyServcie_consumer.getByIdorCode(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<EmrClassifyDTO>> queryAll(EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyDTO", emrClassifyDTO);
        return this.emrClassifyServcie_consumer.queryAll(map);
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyDTO", emrClassifyDTO);
        return this.emrClassifyServcie_consumer.queryPage(map);
    }

    /**
     * @Method save
     * @Desrciption  修改、新增电子病历分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        emrClassifyDTO.setCrteId(sysUserDTO.getId());
        emrClassifyDTO.setCrteName(sysUserDTO.getName());
        emrClassifyDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyDTO", emrClassifyDTO);
        return emrClassifyServcie_consumer.save(map);
    }

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:06getByIdorCode
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("getEmrClassifyTree")
    public WrapperResponse<List<TreeMenuNode>> getEmrClassifyTree(EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyDTO", emrClassifyDTO);
        return emrClassifyServcie_consumer.getEmrClassifyTree(map);
    }

    @GetMapping("getMaxCode")
    public WrapperResponse<Integer> getMaxCode(EmrClassifyDTO emrClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("emrClassifyDTO", emrClassifyDTO);
        return emrClassifyServcie_consumer.getMaxCode(map);
    }
}
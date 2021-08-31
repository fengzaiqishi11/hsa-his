package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifyelement.service.EmrClassifyElementService;
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
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrClassifyElementController
 * @Describe:
 * @Author: liaojunjie
 * @Date: 2020/9/28 8:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/emr/emrClassifyElement")
@Slf4j
public class EmrClassifyElementController extends BaseController {

    @Resource
    EmrClassifyElementService emrClassifyElementServcie_consumer;


    /**
    * @Menthod queryAll
    * @Desrciption  根据条件筛选电子病历文档元素管理表中的数据
     * @param emrClassifyElementDTO
    * @Author liaojunjie
    * @Date   2020/9/28 10:15 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>>
    **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<EmrClassifyElementDTO>> queryAll(EmrClassifyElementDTO emrClassifyElementDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyElementDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("emrClassifyElementDTO",emrClassifyElementDTO);
        return  emrClassifyElementServcie_consumer.queryAll(map);
    }

    /**
    * @Menthod save
    * @Desrciption  修改文档分类已勾选的元素节点
     * @param emrClassifyElementDTO
    * @Author liaojunjie
    * @Date   2020/9/28 10:15 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody EmrClassifyElementDTO emrClassifyElementDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyElementDTO.setHospCode(sysUserDTO.getHospCode());
        emrClassifyElementDTO.setCrteId(sysUserDTO.getId());
        emrClassifyElementDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("emrClassifyElementDTO",emrClassifyElementDTO);
        return  emrClassifyElementServcie_consumer.save(map);
    }

    /**
    * @Menthod queryTreeByEmrClassify
    * @Desrciption  根据文档分类已选择的元素分类节点生成树
     * @param emrClassifyElementDTO
    * @Author liaojunjie
    * @Date   2020/9/28 10:15 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
    **/
    @GetMapping("/queryTreeByEmrClassify")
    public WrapperResponse<List<TreeMenuNode>> queryTreeByEmrClassify(EmrClassifyElementDTO emrClassifyElementDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyElementDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("emrClassifyElementDTO",emrClassifyElementDTO);
        return  emrClassifyElementServcie_consumer.queryTreeByEmrClassify(map);
    }


    /**
    * @Menthod queryTreeIsAble
    * @Desrciption  根据文档分类code查询元素分类树和已勾选的节点
     * @param emrClassifyElementDTO
    * @Author liaojunjie
    * @Date   2020/9/28 10:14 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
    **/
    @GetMapping("/queryTreeIsAble")
    public WrapperResponse<List<TreeMenuNode>> queryTreeIsAble(EmrClassifyElementDTO emrClassifyElementDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrClassifyElementDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("emrClassifyElementDTO",emrClassifyElementDTO);
        return  emrClassifyElementServcie_consumer.queryTreeIsAble(map);
    }
}

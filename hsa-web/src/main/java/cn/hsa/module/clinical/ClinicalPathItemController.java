package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;
import cn.hsa.module.clinical.clinicalpathitem.service.ClinicalPathItemService;
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
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径项目维护控制层
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/9/9 14:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/clinical/clinicalPathItemController")
@Slf4j
public class ClinicalPathItemController extends BaseController {
    @Resource
    private ClinicalPathItemService clinicalPathItemService_consumer;

    /**
     * @Description: 查询所有满足条件的临床目录项目
     * @Param: [req, res]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @GetMapping("/queryAll")
    public WrapperResponse<PageDTO> queryPathItem(ClinicalPathItemDTO queryDTO,
                                                  HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        queryDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("queryDTO",queryDTO);
        return clinicalPathItemService_consumer.queryAll(map);
    }
    /**
     * @Description: 通过id 查询临床路径项目
     * @Param: [req, res]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @GetMapping("/queryPathItemById")
    public WrapperResponse<ClinicalPathItemDTO> queryPathItemById(ClinicalPathItemDTO queryDTO,
                                                  HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        queryDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("queryDTO",queryDTO);
        return clinicalPathItemService_consumer.queryPathItemById(map);
    }
    /**
     * @Meth:updateOrAddPathItem
     * @Description: 修改或者添加临床项目路径
     * @Param: [queryDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @PostMapping("/updateOrAddPathItem")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateOrAddPathItem(@RequestBody ClinicalPathItemDTO clinicalPathItemDTO,
                                                        HttpServletRequest req, HttpServletResponse res){

        SysUserDTO sysUserDTO = getSession(req, res);
        clinicalPathItemDTO.setHospCode(sysUserDTO.getHospCode());
        clinicalPathItemDTO.setCrteName(sysUserDTO.getName());
        clinicalPathItemDTO.setCrteId(sysUserDTO.getId());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("clinicalPathItemDTO",clinicalPathItemDTO);
        return clinicalPathItemService_consumer.updateOrAddPathItem(map);
    }

    /**
     * @Meth: deletePathItemBatch
     * @Description:  删除路径项目
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/9
    */
    @PostMapping("/deletePathItemBatch")
    public WrapperResponse<Boolean> deletePathItemBatch(@RequestBody ClinicalPathItemDTO clinicalPathItemDTO,
                                                        HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        clinicalPathItemDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("clinicalPathItemDTO",clinicalPathItemDTO);
        return clinicalPathItemService_consumer.deletePathItemBatch(map);
    }
}

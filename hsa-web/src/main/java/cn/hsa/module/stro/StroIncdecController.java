package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import cn.hsa.module.stro.incdec.service.StroIncdecService;
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
 *@Package_name: cn.hsa.module.stro
 *@Class_name: StroIncdecController
 *@Describe: 药库报损
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 11:00
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/incdec")
@Slf4j
public class StroIncdecController extends BaseController {

    @Resource
    StroIncdecService stroIncdecService_consumer;

    @Resource
    BaseDrugService baseDrugService_consumer;

    /**
     * @Method queryStroIncdecDaoPage
     * @Desrciption  分页查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 11:00
     * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @GetMapping("/queryStroIncdecDtoPage")
    public WrapperResponse<PageDTO> queryStroIncdecDtoPage(StroIncdecDTO stroIncdecDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroIncdecDTO.setHospCode(sysUserDTO.getHospCode());
        stroIncdecDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroIncdecDTO",stroIncdecDTO);

        return stroIncdecService_consumer.queryStroIncdecDtoPage(map);
    }

    /**
    * @Method queryStockDrugInfoPage
    * @Desrciption 查询某库位的项目(药品或材料)信息
    * @param baseDeptDTO
    * @Author liuqi1
    * @Date   2020/9/9 10:26
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryStockItemInfoPage")
    public WrapperResponse<PageDTO> queryStockItemInfoPage(BaseDeptDTO baseDeptDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseDeptDTO.setHospCode(sysUserDTO.getHospCode());
        if(!"N".equals(baseDeptDTO.getQueryByDeptId())){
            //按登录科室查询，如果不传或不是N,按登录科室查询
            baseDeptDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        baseDeptDTO.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDeptDTO",baseDeptDTO);
        return baseDrugService_consumer.queryStockItemInfoPage(map);
    }

    /**
     * @Method queryStroIncdecDtoById
     * @Desrciption 单个查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.stro.incdec.dto.StroIncdecDTO>
     **/
    @GetMapping("/queryStroIncdecDtoById")
    public WrapperResponse<StroIncdecDTO> queryStroIncdecDtoById(StroIncdecDTO stroIncdecDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroIncdecDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroIncdecDTO",stroIncdecDTO);
        return stroIncdecService_consumer.queryStroIncdecDtoById(map);
    }

    /**
     * @Method queryStroIncdecDetailDtoPage
     * @Desrciption 获得报损明细
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PostMapping("/queryStroIncdecDetailDtos")
    public WrapperResponse<List<StroIncdecDetailDTO>> queryStroIncdecDetailDtos(@RequestBody StroIncdecDTO stroIncdecDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroIncdecDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroIncdecDTO",stroIncdecDTO);

        return stroIncdecService_consumer.queryStroIncdecDetailDtos(map);
    }

    /**
     * @Method insertStroIncdecDao
     * @Desrciption 新增或编辑，按单个报损单来更新
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 11:00
     * @Return int
     **/
    @PostMapping("/insertOrUpdateStroIncdecDto")
    public WrapperResponse<Boolean> insertOrUpdateStroIncdecDto(@RequestBody StroIncdecDTO stroIncdecDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroIncdecDTO.setHospCode(sysUserDTO.getHospCode());
        stroIncdecDTO.setCrteId(sysUserDTO.getId());
        stroIncdecDTO.setCrteName(sysUserDTO.getName());
        stroIncdecDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroIncdecDTO",stroIncdecDTO);
        WrapperResponse w = stroIncdecService_consumer.insertOrUpdateStroIncdecDto(map);
        return w;
    }

    /**
     * @Method updateStroIncdecDao
     * @Desrciption 批量审核或作废
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 11:00
     * @Return int
     **/
    @PostMapping("/updateOrCancelStroIncdecDto")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateOrCancelStroIncdecDto(@RequestBody StroIncdecDTO stroIncdecDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroIncdecDTO.setAuditId(sysUserDTO.getId());
        stroIncdecDTO.setAuditName(sysUserDTO.getName());
        stroIncdecDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroIncdecDTO",stroIncdecDTO);

        return stroIncdecService_consumer.updateOrCancelStroIncdecDto(map);
    }
}

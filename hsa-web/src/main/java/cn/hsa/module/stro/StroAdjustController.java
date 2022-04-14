package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import cn.hsa.module.stro.adjust.service.StroAdjustService;
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
 *@Class_name: StroAdjustController
 *@Describe: 调价Controller
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 22:08
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/adjust")
@Slf4j
public class StroAdjustController extends BaseController {

    @Resource
    StroAdjustService stroAdjustService_consumer;

    /**
    * @Method queryStroAdjustDtoPage
    * @Desrciption 查询出调价信息
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/3 19:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryStroAdjustDtoPage")
    public WrapperResponse<PageDTO> queryStroAdjustDtoPage (StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());
        stroAdjustDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroAdjustDTO",stroAdjustDTO);

        return stroAdjustService_consumer.queryStroAdjustDtoPage(map);
    }

    /**
    * @Menthod queryStroAdjustDtoAll
    * @Desrciption  查询所有调价单 根据id
    *
    * @Param
    * [stroAdjustDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 15:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>>
    **/
    @PostMapping("/queryStroAdjustDtoAll")
    public WrapperResponse<List<StroAdjustDTO>> queryStroAdjustDtoAll(@RequestBody StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());
      stroAdjustDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("stroAdjustDTO",stroAdjustDTO);
      return stroAdjustService_consumer.queryStroAdjustDtoAll(map);
    }

    /**
    * @Method queryStroAdjustDtoById
    * @Desrciption 单个查询出调价信息
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/5 11:16
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryStroAdjustDtoById")
    public WrapperResponse<PageDTO> queryStroAdjustDtoById (StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());
        stroAdjustDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroAdjustDTO",stroAdjustDTO);

        return stroAdjustService_consumer.queryStroAdjustDtoById(map);
    }



    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 查询调价明细
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 10:23
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("/queryStroAdjustDetailDtos")
    public WrapperResponse<List<StroAdjustDetailDTO>> queryStroAdjustDetailDtos (@RequestBody StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroAdjustDTO",stroAdjustDTO);

        return stroAdjustService_consumer.queryStroAdjustDetailDtos(map);
    }


    /**
    * @Method insertOrUpdateStroAdjustDto
    * @Desrciption 新增或更新调价信息
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/3 19:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/insertOrUpdateStroAdjustDto")
    public WrapperResponse<Boolean> insertOrUpdateStroAdjustDto(@RequestBody StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());
        stroAdjustDTO.setCrteId(sysUserDTO.getId());
        stroAdjustDTO.setCrteName(sysUserDTO.getName());
        stroAdjustDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroAdjustDTO",stroAdjustDTO);
        WrapperResponse w = stroAdjustService_consumer.insertOrUpdateStroAdjustDto(map);
        return w;
    }



    /**
    * @Method updateOrCancelStroAdjustDto
    * @Desrciption 批量审核或作废调价
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/3 19:42
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateOrCancelStroAdjustDto")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateOrCancelStroAdjustDto(@RequestBody StroAdjustDTO stroAdjustDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroAdjustDTO.setAuditId(sysUserDTO.getId());
        stroAdjustDTO.setAuditName(sysUserDTO.getName());
        stroAdjustDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroAdjustDTO",stroAdjustDTO);

        return stroAdjustService_consumer.updateOrCancelStroAdjustDto(map);
    }

}

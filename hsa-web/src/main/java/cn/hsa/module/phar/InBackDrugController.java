package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.InBackDrugService;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
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
 * @Package_name: cn.hsa.module.stro
 * @Class_name: InBackDrugController
 * @Describe: 住院退药控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/inBackDrug")
@Slf4j
public class InBackDrugController extends BaseController {

    @Resource
    InBackDrugService inBackDrugService_consumer;

    @Resource
    PharInWaitReceiveService pharInWaitReceiveService_consumer;


    /**
     * @Method queryWaitReceiveGroupByDeptId
     * @Desrciption 按申请科室分组查询出待退药的信息
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/queryWaitReceiveGroupByDeptId")
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryWaitReceiveGroupByDeptId(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInWaitReceiveDTO.setCrteId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }


        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);

        WrapperResponse<List<PharInWaitReceiveDTO>> listWrapperResponse = inBackDrugService_consumer.queryWaitReceiveGroupByDeptId(map);
        return listWrapperResponse;
    }

    /**
     * @Method queryWaitReceiveGroupByItemIdPage
     * @Desrciption 按项目id分组查询出科室待退药的信息
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/queryWaitReceiveGroupByItemIdPage")
    public WrapperResponse<PageDTO> queryWaitReceiveGroupByItemIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInWaitReceiveDTO.setCrteId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inBackDrugService_consumer.queryWaitReceiveGroupByItemIdPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method queryWaitReceiveByDeptId
     * @Desrciption 查询出申请科室的退药明细
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
     **/
    @GetMapping("/queryWaitReceiveByDeptIdPage")
    public WrapperResponse<PageDTO> queryWaitReceiveByDeptIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInWaitReceiveDTO.setCrteId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }


        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inBackDrugService_consumer.queryWaitReceiveByDeptIdPage(map);
        return pageDTOWrapperResponse;
    }


    /**
     * @Method updaeInHospitalBackDrug
     * @Desrciption 住院退药
     * @param pharInWaitReceiveDTO
     * @Author liuqi1
     * @Date   2020/8/28 15:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updaeInHospitalBackDrug")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updaeInHospitalBackDrug(@RequestBody PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);

        pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharInWaitReceiveDTO.setCrteId(sysUserDTO.getId());
        pharInWaitReceiveDTO.setCrteName(sysUserDTO.getName());
        WrapperResponse<Boolean> booleanWrapperResponse = inBackDrugService_consumer.updateInHospitalBackDrug(map);
        return booleanWrapperResponse;
    }

    /**
     * @Menthod queryPharInWaitReceive
     * @Desrciption 住院查询个人领药申请
     *
     * @Param
     * [1. pharInWaitReceiveDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/9/8 16:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPharInWaitReceive")
    public WrapperResponse<PageDTO> queryPharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
      pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
      Map<String,Object> map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);
      WrapperResponse<PageDTO> pageDTOWrapperResponse = pharInWaitReceiveService_consumer.queryPharInWaitReceivePage(map);
      return pageDTOWrapperResponse;
    }

    /**未退药查询
     *条件：
     * 1、phar_in_wait_receive 已发药、需要退药
     * 2、inpt_cost 已退费未退药、未退费确认
    * @Method queryBackDrugPage
    * @Desrciption
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2021/4/23 14:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryBackDrugPage")
    public WrapperResponse<PageDTO> queryBackDrugPage(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());

        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInWaitReceiveDTO",pharInWaitReceiveDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = inBackDrugService_consumer.queryBackDrugPage(map);
        return pageDTOWrapperResponse;
    }

}

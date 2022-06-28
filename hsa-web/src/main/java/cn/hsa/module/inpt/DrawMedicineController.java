package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.drawMedicine.service.DrawMedicineService;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: DrawMedicineController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/11 15:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/drawMedicine")
@Slf4j
public class DrawMedicineController extends BaseController {

    @Resource
    DrawMedicineService drawMedicineService;


    /**
     * @Method beforehandDrawMedicine
     * @Desrciption 预配药
       @params [id]
     * @Author chenjun
     * @Date   2020-12-17 15:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping(value = "/beforehandDrawMedicine")
    @NoRepeatSubmit
    WrapperResponse<Boolean> beforehandDrawMedicine(String id, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("crteId", sysUserDTO.getId());
        map.put("crteName", sysUserDTO.getName());
        map.put("id", id);
        WrapperResponse<Boolean> pageDTOWrapperResponse = drawMedicineService.saveBeforehandDrawMedicine(map);
        return pageDTOWrapperResponse;
    }


    /**
    * @Menthod checkDrawMedicineStock
    * @Desrciption 预配药的时候校验库存
    *
    * @Param
    * [id, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/7 10:35
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping(value = "/checkDrawMedicineStock")
    @NoRepeatSubmit
    WrapperResponse<PharInWaitReceiveDTO> checkDrawMedicineStock(String id, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap<>();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
      map.put("crteId", sysUserDTO.getId());
      map.put("crteName", sysUserDTO.getName());
      map.put("id", id);
      WrapperResponse<PharInWaitReceiveDTO> pageDTOWrapperResponse = drawMedicineService.checkDrawMedicineStock(map);
      return pageDTOWrapperResponse;
    }

    /**
     * @Method getApplyDetailsList
     * @Desrciption 查询领药明细数据
       @params [id]
     * @Author chenjun
     * @Date   2020-12-17 15:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping(value = "/getApplyDetailsList")
    WrapperResponse<PageDTO> getApplyDetailsList(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("id", pharInWaitReceiveDTO.getId());
        map.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = drawMedicineService.getApplyDetailsList(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method getApplySummaryList
     * @Desrciption 查询领药汇总数据
       @params [id]
     * @Author chenjun
     * @Date   2020-12-17 15:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping(value = "/getApplySummaryList")
    WrapperResponse<PageDTO> getApplySummaryList(PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("id", pharInWaitReceiveDTO.getId());
        map.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = drawMedicineService.getApplySummaryList(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单集合
       @params [code]
     * @Author chenjun
     * @Date   2020-12-17 15:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping(value = "/getPharInReceiveList")
    WrapperResponse<PageDTO> getPharInReceiveList(@RequestParam Map map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(MapUtils.get(map, "code"))){
          codeList.add(MapUtils.get(map, "code"));
        }
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("codeList", codeList);
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        WrapperResponse<PageDTO> pageDTOWrapperResponse = drawMedicineService.getPharInReceiveList(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method getPharInReceiveListDetail
     * @Desrciption 查询领药单明细集合
       @params [receiveId]
     * @Author chenjun
     * @Date   2020-12-17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping(value = "/getPharInReceiveListDetail")
    WrapperResponse<PageDTO> getPharInReceiveListDetail(@RequestParam Map map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String receiveId = MapUtils.get(map, "receiveId");
        if(StringUtils.isEmpty(receiveId)){
            return new WrapperResponse<PageDTO>();
        }
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("receiveId", receiveId);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = drawMedicineService.getPharInReceiveListDetail(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method cancelDrawMedicine
     * @Desrciption 取消配药
       @params [pharInReceiveDTO]
     * @Author chenjun
     * @Date   2020-12-17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping(value = "/cancelDrawMedicine")
    @NoRepeatSubmit
    WrapperResponse<Boolean> cancelDrawMedicine(@RequestBody PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("pharInReceiveDTO", pharInReceiveDTO);
        WrapperResponse<Boolean> wrapperResponse = drawMedicineService.cancelDrawMedicine(map);
        return wrapperResponse;
    }

    @PostMapping(value = "/updateUrgentMedicine")
    @NoRepeatSubmit
    WrapperResponse<Boolean> updateUrgentMedicine(@RequestBody PharInWaitReceiveDTO pharInWaitReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
      pharInWaitReceiveDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap<>();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("pharInWaitReceiveDTO", pharInWaitReceiveDTO);
      WrapperResponse<Boolean> wrapperResponse = drawMedicineService.updateUrgentMedicine(map);
      return wrapperResponse;
    }

    /**
     * @Method saveAdvanceTakeMedicine
     * @Desrciption 提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/saveAdvanceTakeMedicine")
    @NoRepeatSubmit
    WrapperResponse<Boolean> saveAdvanceTakeMedicine(@RequestBody Map<String,Object> param, HttpServletRequest req, HttpServletResponse res){
        int advanceDays =  Integer.parseInt(MapUtils.get(param,"advanceDays","0"));
        SysUserDTO sysUserDTO = getSession(req, res);
        param.put("hospCode",sysUserDTO.getHospCode());
        param.put("deptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        param.put("crteTime", DateUtils.format(DateUtils.Y_M_DH_M_S));
        param.put("startDate",DateUtils.format(DateUtils.dateAdd(new Date(),1),DateUtils.Y_M_D));
        param.put("endDate",DateUtils.format(DateUtils.dateAdd(new Date(),advanceDays),DateUtils.Y_M_D));
        param.put("crteId",sysUserDTO.getId());
        param.put("crteName",sysUserDTO.getName());
        WrapperResponse<Boolean> wrapperResponse = drawMedicineService.saveAdvanceTakeMedicine(param);
        return wrapperResponse;
    }

    /**
     * @Method getTableTqlyData
     * @Desrciption 提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping(value = "/getTableTqlyData")
    WrapperResponse<List<Map<String, Object>>> getTableTqlyData(HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("deptId", sysUserDTO.getLoginBaseDeptDTO().getId());
        WrapperResponse<List<Map<String, Object>>> wrapperResponse = drawMedicineService.getTableTqlyData(map);
        return wrapperResponse;
    }

    /**
     * @Method updateAdvance
     * @Desrciption 取消提前领药
     * @params map
     * @Author pengbo
     * @Date   2021-05-12 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/updateAdvance")
    @NoRepeatSubmit
    WrapperResponse<Boolean> updateAdvance(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("deptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        WrapperResponse<Boolean> wrapperResponse = drawMedicineService.updateAdvance(map);
        return wrapperResponse;
    }

}

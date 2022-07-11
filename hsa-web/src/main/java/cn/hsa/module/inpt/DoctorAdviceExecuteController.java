package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.inpt.nurse.service.DoctorAdviceExecuteService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: DoctorAdviceExecuteController
 *@Describe: 医嘱执行
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 14:16
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/nurse")
@Slf4j
public class DoctorAdviceExecuteController extends BaseController {

    @Resource
    DoctorAdviceExecuteService doctorAdviceExecuteService_consumer;
    @Resource
    SysUserService sysUserService_consumer;

    /**
    * @Method queryDoctorAdviceExecuteInfo
    * @Desrciption 医嘱执行查询
    * @param
    * @Author liuqi1
    * @Date   2020/9/2 16:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping(value = "/queryDoctorAdviceExecuteInfo")
    WrapperResponse<PageDTO> queryDoctorAdviceExecuteInfo(InptAdviceExecDTO inptAdviceExecDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceExecDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceExecDTO", inptAdviceExecDTO);

        System.out.println(inptAdviceExecDTO.getColumnName());

        WrapperResponse<PageDTO> pageDTOWrapperResponse = doctorAdviceExecuteService_consumer.queryDoctorAdviceExecuteInfo(map);
        return pageDTOWrapperResponse;
    }


    /**
    * @Method queryExecuteNurseInfo
    * @Desrciption 查询执行护士信息
    * @param sysUserDTO
    * @Author liuqi1
    * @Date   2020/9/18 11:02
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
    **/
    @PostMapping(value = "/queryExecuteNurseInfo")
    WrapperResponse<List<SysUserDTO>> queryExecuteNurseInfo(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO1 = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTO1.getHospCode());

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO1.getHospCode());
        map.put("sysUserDTO",sysUserDTO);


        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryAll(map);
        return listWrapperResponse;
    }

    /**
    * @Method updateDoctorAdviceExecute
    * @Desrciption 医嘱执行修改
    * @param inptAdviceExecDTOs
    * @Author liuqi1
    * @Date   2020/9/2 17:01
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping(value = "/updateDoctorAdviceExecute")
    WrapperResponse<Boolean> updateDoctorAdviceExecute(@RequestBody List<InptAdviceExecDTO> inptAdviceExecDTOs, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceExecDTOs", inptAdviceExecDTOs);

        WrapperResponse<Boolean> pageDTOWrapperResponse = doctorAdviceExecuteService_consumer.updateDoctorAdviceExecute(map);
        return pageDTOWrapperResponse;
    }

    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [inptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping(value = "/updateAdviceExecuteCance")
    WrapperResponse<Boolean> updateAdviceExecuteCance(@RequestBody List<InptAdviceExecDTO> inptAdviceExecDTOs, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
      Map<String,Object> map = new HashMap<>();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("inptAdviceExecDTOs", inptAdviceExecDTOs);

      WrapperResponse<Boolean> pageDTOWrapperResponse = doctorAdviceExecuteService_consumer.updateAdviceExecuteCance(map);
      return pageDTOWrapperResponse;
    }
    /**
     * @Package_name: cn.hsa.module.inpt
     * @Class_name:: DoctorAdviceExecuteController
     * @Description: 未停医嘱
     * @Author: ljh
     * @Date: 2020/9/24 17:25
     * @Company1: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     */
    @GetMapping(value = "/queryNoMedical")
    WrapperResponse<PageDTO> queryNoMedical(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", inptAdviceDTO);
        return doctorAdviceExecuteService_consumer.queryNoMedical(map);
    }
}

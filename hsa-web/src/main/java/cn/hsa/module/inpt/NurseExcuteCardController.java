package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurseexcutecard.service.NurseExcuteCardService;
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
 * @PackageName: cn.hsa.module.inpt
 * @Class_name: NurseExcuteCardController
 * @Description: 护理执行卡控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 14:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/nurseExcuteCard")
@Slf4j
public class NurseExcuteCardController extends BaseController {

    @Resource
    private NurseExcuteCardService nurseExcuteCardService_consumer;

    /**
     * @Method queryPatient()
     * @Desrciption 分页查询病人就诊信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 病人分页信息
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPatient(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptVisitDTO", inptVisitDTO);
        return nurseExcuteCardService_consumer.queryPatient(map);
    }

    /**
     * @Method queryDocterAdvice()
     * @Desrciption 根据诊断id 查询医嘱信息
     * @Param InptVisitDTO 住院就诊病人DTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/8 10:01
     * @Return 医嘱分页信息
     **/
    @GetMapping("/queryDocterAdvicePage")
    public WrapperResponse<PageDTO> queryDocterAdvice(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptVisitDTO", inptVisitDTO);
        return nurseExcuteCardService_consumer.queryDocterAdvice(map);
    }


    /**
    * @Menthod queryDocterAdviceAll
    * @Desrciption 护理执行卡
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/3 14:27
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptAdviceDTO>>
    **/
    @GetMapping("/queryDocterAdviceAll")
    public WrapperResponse<List<InptAdviceDTO>> queryDocterAdviceAll(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
      Map map = new HashMap<>();
      map.put("hospCode", sysUserDTO.getHospCode());
      inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
      inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      map.put("inptVisitDTO", inptVisitDTO);
      return nurseExcuteCardService_consumer.queryDocterAdviceAll(map);
    }

    /**
     * @Method: AllPrinting()
     * @Descrition: 护理执行卡批量打印病人数据的功能
     * @Pramas: AllPrinting
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/21
     * @Retrun: List<List < InptAdviceDTO>>
     */
    @PostMapping("/AllPrinting")
    public WrapperResponse<List<List<InptAdviceDTO>>> AllPrinting(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return nurseExcuteCardService_consumer.AllPrinting(map);
    }

    /**
     * @Method: updatePrintFlag()
     * @Descrition: 打印完成以后 修改打印的标识符
     * @Pramas: inptAdviceDTO包含：打印的医嘱id集合 对应的病人的就诊id
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/22
     * @Retrun: boolean
     */
    @PostMapping("/updatePrintFlag")
    public WrapperResponse<Boolean> updatePrintFlag(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptAdviceDTO",inptAdviceDTO);
        return nurseExcuteCardService_consumer.updatePrintFlag(map);
    }

    /**
    * @Menthod queryPatientNurseCard
    * @Desrciption 根据护理执行卡 查询患者信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/2 10:22
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPatientNurseCard")
    public WrapperResponse<PageDTO> queryPatientNurseCard(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
    Map map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
    inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
    map.put("inptVisitDTO", inptVisitDTO);
    return nurseExcuteCardService_consumer.queryPatientNurseCard(map);
  }
}

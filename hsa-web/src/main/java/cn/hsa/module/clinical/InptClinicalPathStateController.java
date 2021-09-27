package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.service.InptClinicalPathStateService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
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
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径病人记录控制层
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/9/22 14:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/clinical/inptClinicalPathStateController")
@Slf4j
public class InptClinicalPathStateController extends BaseController {
    @Resource
    private InptClinicalPathStateService inptClinicalPathStateService_consumer;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 通过目录id、阶段id查询
     * @Param: [clinicPathStageDetailDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @GetMapping("/queryClinicalPathStageDetail")
    public WrapperResponse<PageDTO> queryClinicalPathStageDetail(InptClinicalPathStateDTO inptClinicalPathStateDTO,
                                                                 HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
        return inptClinicalPathStateService_consumer.queryClinicalPathStageDetail(map);
    }
    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询临床路径病人记录表
     * @Param: [inptClinicalPathStateDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @GetMapping("/queryInptClinicalPathState")
    public WrapperResponse<PageDTO> queryInptClinicalPathState(InptClinicalPathStateDTO inptClinicalPathStateDTO,
                                                               HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
        inptClinicalPathStateDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inptClinicalPathStateDTO.setZgDoctorId(sysUserDTO.getId());

        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
        return inptClinicalPathStateService_consumer.queryInptClinicalPathState(map);
    }
    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过就诊id查询单个临床路径病人的记录
     * @Param: [inptClinicalPathStateDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @GetMapping("/queryInptClinicalPathStateByVisitId")
    public WrapperResponse<InptClinicalPathStateDTO> queryInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO, HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
        return inptClinicalPathStateService_consumer.queryInptClinicalPathStateByVisitId(map);
    }

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [inptClinicalPathStateDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:28
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/insertInptClinicalPathStateByVisitId")
    public WrapperResponse<Boolean> insertInptClinicalPathStateByVisitId(@RequestBody InptClinicalPathStateDTO inptClinicalPathStateDTO, HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
        inptClinicalPathStateDTO.setPathCrteId(sysUserDTO.getId());
        inptClinicalPathStateDTO.setPathCrteName(sysUserDTO.getName());
        inptClinicalPathStateDTO.setPathCrteTime(DateUtils.getNow());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
        return inptClinicalPathStateService_consumer.insertInptClinicalPathStateByVisitId(map);
    }

    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [inptClinicalPathStateDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:28
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateInptClinicalPathStateByVisitId")
    public WrapperResponse<Boolean> updateInptClinicalPathStateByVisitId(@RequestBody InptClinicalPathStateDTO inptClinicalPathStateDTO, HttpServletRequest req, HttpServletResponse res) {
      Map map = new HashMap();
      SysUserDTO sysUserDTO = getSession(req, res);
      inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
      return inptClinicalPathStateService_consumer.updateInptClinicalPathStateByVisitId(map);
    }

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 查询科室
    *
    * @Param
    * [inptClinicalPathStateDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>>
    **/
    @RequestMapping("/queryDeptByClinicalPathDeptId")
    public WrapperResponse<List<BaseDeptDTO>> queryDeptByClinicalPathDeptId(InptClinicalPathStateDTO inptClinicalPathStateDTO, HttpServletRequest req, HttpServletResponse res) {
      Map map = new HashMap();
      SysUserDTO sysUserDTO = getSession(req, res);
      inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
      return inptClinicalPathStateService_consumer.queryDeptByClinicalPathDeptId(map);
    }


    /**
    * @Menthod queryPatientPage
    * @Desrciption 查询患者信息
    *
    * @Param
    * [inptVisitDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:26
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryPatientPage")
    public WrapperResponse<PageDTO> queryPatientPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
      Map map = new HashMap();
      SysUserDTO sysUserDTO = getSession(req, res);
      inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
      inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("inptVisitDTO",inptVisitDTO);
      return inptClinicalPathStateService_consumer.queryPatientPage(map);
    }

}

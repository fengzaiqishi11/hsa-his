package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharindistributedrug.service.InDistributeDrugService;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
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
* @Package_name: cn.hsa.module.phar
* @class_name: InDistributeController
* @Description: 住院发药控制层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:45
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/stro/inDistribute")
@Slf4j
public class InDistributeController  extends BaseController {

    @Resource
    private InDistributeDrugService inDistributeDrugService_consumer;

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 13:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getInRecivePage")
    public WrapperResponse<PageDTO> getInRecivePage(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.getInRecivePage(map);
    }

    /**
     * @Method: getSendInRecivePage
     * @Description: 住院发药记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:00
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getSendInRecivePage")
    public WrapperResponse<PageDTO> getSendInRecivePage(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.getSendInRecivePage(map);
    }

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [pharInReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 13:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getInReviceDetailPage")
    public WrapperResponse<PageDTO> getInReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharInReceiveDetailDTO",pharInReceiveDetailDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.getInReviceDetailPage(map);
    }

    /**
    * @Menthod getInReviceDetail
    * @Desrciption  住院配药的明细单打印 按医嘱类型和病人分组
     * @param pharInReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/12/25 9:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>>>
    **/
    @GetMapping("/getInReviceDetail")
    public WrapperResponse<Map<String, List<PharInReceiveDetailDTO>>> getInReviceDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharInReceiveDetailDTO",pharInReceiveDetailDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.getInReviceDetail(map);
    }

    /**
     * @Method: getInSumReviceDetailPage
     * @Description: getInRecivePage--取药合计单
     * @Param: [pharInReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 13:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getInSumReviceDetailPage")
    public WrapperResponse<PageDTO> getInSumReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("pharInReceiveDetailDTO",pharInReceiveDetailDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.getInSumReviceDetailPage(map);
    }

    /**
     * @Method: outDispense
     * @Description: 住院配药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 13:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/inDispense")
    @NoRepeatSubmit
    public WrapperResponse<PharInReceiveDTO> inDispense(@RequestBody PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药信息
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharInReceiveDTO.setAssignUserId(sysUserDTO.getId());
        pharInReceiveDTO.setAssignUserName(sysUserDTO.getName());
        pharInReceiveDTO.setAssignTime(DateUtils.getNow());
        pharInReceiveDTO.setStatusCode(Constants.FYZT.PY);

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.inDispense(map);
    }

    /**
     * @Method: inDispense
     * @Description: 取消配药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/cancelInDispense")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> cancelInDispense(@RequestBody PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //配药信息
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharInReceiveDTO.setAssignUserId(null);
        pharInReceiveDTO.setAssignUserName(null);
        pharInReceiveDTO.setAssignTime(null);
        pharInReceiveDTO.setStatusCode(Constants.FYZT.QL);

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.cancelInDispense(map);
    }

    /**
     * @Method: inDistribute
     * @Description: 住院发药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/11 13:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/inDistribute")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> inDistribute(@RequestBody PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //发药信息
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        pharInReceiveDTO.setDistUserId(sysUserDTO.getId());
        pharInReceiveDTO.setDistUserName(sysUserDTO.getName());
        pharInReceiveDTO.setDistTime(DateUtils.getNow());
        pharInReceiveDTO.setStatusCode(Constants.FYZT.FY);

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.inDistribute(map);
    }

    /**
    * @Menthod queryDeptInquiryMedica
    * @Desrciption 科室领药查询
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/12/16 15:04
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryDeptInquiryMedica")
    public WrapperResponse<PageDTO> queryDeptInquiryMedica(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

      Map map = new HashMap();
      map.put("pharInReceiveDTO",pharInReceiveDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return inDistributeDrugService_consumer.getInRecivePage(map);
    }
    /**
    * @Menthod queryPatientB
    * @Desrciption 根据配药单号查询患者
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:06
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPatientByOrder")
    public WrapperResponse<List<InptVisitDTO>> queryPatientByOrder(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

      Map map = new HashMap();
      map.put("pharInReceiveDTO",pharInReceiveDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return inDistributeDrugService_consumer.queryPatientByOrder(map);
    }

    /**
    * @Menthod queryPatientB
    * @Desrciption 根据配药单号,就诊id查询病人配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:07
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryDrugByOrderAndVisitId")
    public WrapperResponse<List<InptAdviceDTO>> queryDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

      Map map = new HashMap();
      map.put("pharInReceiveDTO",pharInReceiveDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return inDistributeDrugService_consumer.queryDrugByOrderAndVisitId(map);
    }

    /**
    * @Menthod updatePremedication
    * @Desrciption 选择性取消预配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 10:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updatePremedication")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updatePremedication(@RequestBody PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
      SysUserDTO sysUserDTO = getSession(req, res);
      //配药信息
      pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());

      Map map = new HashMap();
      map.put("pharInReceiveDTO",pharInReceiveDTO);
      map.put("hospCode",sysUserDTO.getHospCode());
      return inDistributeDrugService_consumer.updatePremedication(map);
  }
    /**
     * @Menthod: queryDMDrugByOrderAndVisitId
     * @Desrciption: 根据就诊id查询精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @GetMapping("/queryDMDrugByOrderAndVisitId")
    public WrapperResponse<List<InptAdviceDTO>> queryDMDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.queryDMDrugByOrderAndVisitId(map);
    }
    /**
     * @Menthod: queryDMPatientByOrder
     * @Desrciption: 查询配药发药精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    @GetMapping("/queryDMPatientByOrder")
    public WrapperResponse<List<InptVisitDTO>> queryDMPatientByOrder(PharInReceiveDTO pharInReceiveDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        pharInReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            pharInReceiveDTO.setPharId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map map = new HashMap();
        map.put("pharInReceiveDTO",pharInReceiveDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return inDistributeDrugService_consumer.queryDMPatientByOrder(map);
    }
}

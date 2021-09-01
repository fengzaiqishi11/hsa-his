package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.costclassification.service.CostClassificationService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.PatientCompreHensiveQueryDTO;
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
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: CostClassificationController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/8/19 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/costClassificationController")
@Slf4j
public class CostClassificationController extends BaseController {

   @Resource
   private CostClassificationService costclassificationService_consumer;

     /**
     * @Menthod queryCostByAdviceId
     * @Desrciption 根据患者查询和类型该医嘱，补记帐产生的所有费用
     *
     * @Param
     * [inptAdviceDTO, req, res]
     *
     * @Author jiahong.yang
     * @Date   2021/8/19 14:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>>
     **/
    @PostMapping("/costClassificationController")
    WrapperResponse<List<PatientCompreHensiveQueryDTO>> queryCostByAdviceId(@RequestBody PatientCompreHensiveQueryDTO patientCompreHensiveQueryDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      patientCompreHensiveQueryDTO.setHospCode(sysUserDTO.getHospCode());
      if ("Y".equals(patientCompreHensiveQueryDTO.getSourceDeptId())){
        patientCompreHensiveQueryDTO.setSourceDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
      }else {
        patientCompreHensiveQueryDTO.setSourceDeptId(null);
      }
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("patientCompreHensiveQueryDTO", patientCompreHensiveQueryDTO);
      return costclassificationService_consumer.queryCostByAdviceId(map);
    }


    /**
    * @Menthod queryCostByAdviceId
    * @Desrciption 修改费用的类型
    *
    * @Param
    * [inptCostDTO, req, res]
    *
    * @Author jiahong.yang
    * @Date   2021/8/19 14:50
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateCostAttributionCode")
    WrapperResponse<Boolean> updateCostAttributionCode(@RequestBody InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      inptCostDTO.setHospCode(sysUserDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode", sysUserDTO.getHospCode());
      map.put("inptCostDTO", inptCostDTO);
      return costclassificationService_consumer.updateCostAttributionCode(map);
    }
}

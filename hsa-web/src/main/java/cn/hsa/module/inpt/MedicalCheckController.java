package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.medicalcheck.service.MedicalCheckService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: MedicalCheckController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/22 10:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/medicalCheck")
@Slf4j
public class MedicalCheckController extends BaseController {

    @Resource
    private MedicalCheckService medicalCheckService_consumer;

    @NoRepeatSubmit
    @PostMapping("/getBeforeCheckAdvice")
    WrapperResponse<Map> getBeforeCheckAdvice(@RequestBody InptAdviceTDTO inptAdviceTDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inptAdviceTDTO == null || ListUtils.isEmpty(inptAdviceTDTO.getInptAdviceDTOList()) || inptAdviceTDTO.getInptAdviceDTOList().size() == 0){
            throw new RuntimeException("医嘱信息为空");
        }
        for(InptAdviceDTO inptAdviceDTO : inptAdviceTDTO.getInptAdviceDTOList()){
            //医院编码
            inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
            //医生
            inptAdviceDTO.setCrteId(sysUserDTO.getId());
            //医生姓名
            inptAdviceDTO.setCrteName(sysUserDTO.getName());
            //开方时间
            inptAdviceDTO.setCrteTime(DateUtils.getNow());
            //就诊科室
            inptAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        Map map = new HashMap();
        //医院编码
        map.put("hospCode", sysUserDTO.getHospCode());
        //医嘱信息
        map.put("inptAdviceDTOList", inptAdviceTDTO.getInptAdviceDTOList());
        return medicalCheckService_consumer.getBeforeCheckAdvice(map);
    }

    @NoRepeatSubmit
    @PostMapping("/getBeforeCheckAdd")
    WrapperResponse<Map> getBeforeCheckAdd(@RequestBody List<InptCostDTO> inptCostDTOS, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptCostDTOS", inptCostDTOS);
        //当前操作人id
        map.put("userId", sysUserDTO.getId());
        //当前操作人科室id
        map.put("deptId", sysUserDTO.getBaseDeptDTO().getId());

        return medicalCheckService_consumer.getBeforeCheckAdd(map);
    }

    @NoRepeatSubmit
    @PostMapping("/getBeforeOutHosp")
    WrapperResponse<Map> getBeforeOutHosp(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return medicalCheckService_consumer.getBeforeOutHosp(map);
    }

    /**
     * @Method 诊断辅助填报
     * @Desrciption  诊断填报系统，开立诊断的时候调用接口，传入分组器执行需要的参数，
     * 以页面的形式返回执行结果数据，*号为必传，其余参数有尽量传，例如手术操作，
     * 例如年龄不足一岁，新生儿的相关字段需要传
     * @Param
     * xm姓名,xb性别,nl年龄,zfy总费用,sjzyts实际住院天数,rybq入院病情,rytj入院途径
     * lyfs离院方式,zyzd主要诊断名称,jbdm主诊断代码
     * @Author zhangxuan
     * @Date   2021-01-23 9:40
     * @Return
    **/
    @NoRepeatSubmit
    @PostMapping("/dagns")
    WrapperResponse<Map> dagns(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);
        map.put("inptDiagnoseDTOS",inptVisitDTO.getInptDiagnoseDTOS());
        //当前操作人id
        map.put("userId", sysUserDTO.getId());
        //当前操作人科室id
        map.put("deptId", sysUserDTO.getBaseDeptDTO().getId());

        return medicalCheckService_consumer.dagns(map);
    }

    @NoRepeatSubmit
    @PostMapping("/dagnsDIP")
    WrapperResponse<Map> dagnsDIP(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);
        map.put("inptDiagnoseDTOS",inptVisitDTO.getInptDiagnoseDTOS());
        //当前操作人id
        map.put("userId", sysUserDTO.getId());
        //当前操作人科室id
        map.put("deptId", sysUserDTO.getBaseDeptDTO().getId());

        return medicalCheckService_consumer.dagnsDIP(map);
    }

    @NoRepeatSubmit
    @PostMapping("/getBeforeRecord")
    WrapperResponse<Map> getBeforeRecord(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);

        return medicalCheckService_consumer.getBeforeRecord(map);
    }


    @NoRepeatSubmit
    @PostMapping("/getBeforeDRG")
    WrapperResponse<Map> getBeforeDRG(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        map.put("deptCode", sysUserDTO.getBaseDeptDTO().getId());
        map.put("doctorId", sysUserDTO.getId());
        return medicalCheckService_consumer.getBeforeDRG(map);
    }

    @NoRepeatSubmit
    @PostMapping("/getBeforeDIP")
    WrapperResponse<Map> getBeforeDIP(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        map.put("deptCode", sysUserDTO.getBaseDeptDTO().getId());
        map.put("doctorId", sysUserDTO.getId());
        return medicalCheckService_consumer.getBeforeDIP(map);
    }

    /**
     * @Description: 校验当前就诊id的患者的费用是否都已经确费
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/15 15:35
     * @Return
     */
    @NoRepeatSubmit
    @PostMapping("/checkConfirmCost")
    WrapperResponse<Boolean> checkConfirmCost(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return medicalCheckService_consumer.checkConfirmCost(map);
    }
}

package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infectious.dto.InfectiousDiseaseDto;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import cn.hsa.module.outpt.infectious.service.OutptInfectiousDiseaExecService;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptInfectiousDiseaController
 * @Describe: 传染病执行控制器
 * @Author: liuliyun
 * @Date: 2021/4/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@RestController
@RequestMapping("/web/outpt/outptInfectiousExec")
public class OutptInfectiousDiseaController extends BaseController {
    @Resource
    private OutptInfectiousDiseaExecService outptInfectiousExecService_consumer;
    /**
     * 处方管理dubbo消费者接口
     */
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService_consumer;

    /**
     * @Menthod: save()
     * @Desrciption: 保存传染病信息
     * @Param: InfectiousDiseaseDO 传染病DO传输对象
     * @Author: liuliyun
     * @Date: 2021/04/21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody InfectiousDiseaseDto infectiousDiseaseDO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        infectiousDiseaseDO.setHospCode(userDTO.getHospCode());
        infectiousDiseaseDO.setCreatId(userDTO.getId());
        infectiousDiseaseDO.setCreatName(userDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptInfectiousDiseaDao", infectiousDiseaseDO);
        return outptInfectiousExecService_consumer.save(map);
//        if (wrapperResponse.getCode()==0){
//            OutptMedicalRecordDTO outptMedicalRecordDTO=infectiousDiseaseDO.getOutptMedicalRecordDTO();
//            Map medicalMap=new HashMap();
//            medicalMap.put("hospCode", hospCode);
//            medicalMap.put("outptMedicalRecordDTO", outptMedicalRecordDTO);
//           return outptDoctorPrescribeService_consumer.saveMedicalRecord(medicalMap);
//        }else {
//            return WrapperResponse.fail(false);
//        }
    }

    /**
     * @Menthod: queryInfectiousById()
     * @Desrciption: 通过id查询传染病信息
     * @Param: InfectiousDiseaseDO 传染病DO传输对象
     * @Author: liuliyun
     * @Date: 2021/04/21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<List<InfectiousDiseaseDO>>
     **/
    @GetMapping("/queryInfectiousById")
    public WrapperResponse<List<InfectiousDiseaseDO>> queryInfectiousById(String infectiousId,HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap<>();
        InfectiousDiseaseDO infectiousDiseaseDO=new InfectiousDiseaseDO();
        SysUserDTO userDTO = getSession(req, res) ;
        infectiousDiseaseDO.setHospCode(userDTO.getHospCode());
        infectiousDiseaseDO.setId(infectiousId);
        map.put("hospCode", userDTO.getHospCode());
        map.put("infectiousDiseaseDO", infectiousDiseaseDO);
        return  outptInfectiousExecService_consumer.queryInfoById(map);
    }

    /**
     * @Menthod: queryInfectiousPage()
     * @Desrciption: 通过条件查询传染病信息
     * @Param: InfectiousDiseaseDO 传染病DO传输对象
     * @Author: liuliyun
     * @Date: 2021/04/21
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryInfectiousPage")
    public WrapperResponse<PageDTO> queryInfectiousPage(InfectiousDiseaseDto infectiousDiseaseDto,HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        infectiousDiseaseDto.setHospCode(userDTO.getHospCode());
        map.put("hospCode", userDTO.getHospCode());
        map.put("infectiousDiseaseDto", infectiousDiseaseDto);
        return  outptInfectiousExecService_consumer.queryInfectiousPage(map);
    }

    /**
     * @Menthod fectiousDiseaseAudit
     * @Desrciption 传染病信息审核
     * @param
     * @Author liuliyun
     * @Date 2021/04/25 17:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping("/fectiousDiseaseAudit")
    @NoRepeatSubmit
    public WrapperResponse fectiousDiseaseAudit(@RequestBody Map<String,String> params,HttpServletRequest req, HttpServletResponse res) {
        if (!params.containsKey("ids") || StringUtils.isEmpty(params.get("ids"))){
            return WrapperResponse.fail("参数错误。",null);
        }
        InfectiousDiseaseDto infectiousDiseaseDto = new InfectiousDiseaseDto();
        infectiousDiseaseDto.setIds(params.get("ids").split(","));//id
        SysUserDTO userDTO = getSession(req, res) ;
        infectiousDiseaseDto.setAuditId(userDTO.getId());//审核人id
        infectiousDiseaseDto.setAuditName(userDTO.getName());//审核人姓名
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",userDTO.getHospCode());//医院编码
        param.put("infectiousDiseaseDto",infectiousDiseaseDto);//参数
        return outptInfectiousExecService_consumer.InfectiousDiseaseAudit(param);
    }

}

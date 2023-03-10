package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDiseaseRecordDTO;
import cn.hsa.module.insure.module.dto.InsureFixPersonnalRecordDTO;
import cn.hsa.module.insure.module.dto.InsureInptRecordDTO;
import cn.hsa.module.insure.module.dto.InsureSpecialRecordDTO;

import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureInptRecordDO;
import cn.hsa.module.insure.module.service.InsurePersonnalRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsurePersonnalRecordController
 * @Description: 医保统一支付平台:人员备案业务控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/30 16:00
 * @Company: 创智和宇
 **/

@RestController
@RequestMapping("/web/insure/insurePersonnalRecord")
@Slf4j
public class InsurePersonnalRecordController extends BaseController {

    @Resource
    private InsurePersonnalRecordService insurePersonnalRecordService_consumer;
    
    /**
     * @Method deleteInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备案撤销
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13 
     * @Return Boolean
    **/
    @PostMapping("/deleteInsureDiseaseRecord")
    public WrapperResponse<Boolean> deleteInsureDiseaseRecord(@RequestBody InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureDiseaseRecordDTO.setHospName(sysUserDTO.getHospName());
        insureDiseaseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);
        return insurePersonnalRecordService_consumer.deleteInsureDiseaseRecord(map);
    }

    /**
     * @Method queryAllInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备查查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 16:32
     * @Return
    **/
    @GetMapping("/queryPageInsureDiseaseRecord")
    public WrapperResponse<PageDTO> queryPageInsureDiseaseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureDiseaseRecordDTO.setHospName(sysUserDTO.getHospName());
        insureDiseaseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);
        return insurePersonnalRecordService_consumer.queryPageInsureDiseaseRecord(map);
    }

    /**
     * @Method insertInsureDiseaseRecord
     * @Desrciption  医保统一支付平台：慢特病备案
     * @Param insureDiseaseRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    @PostMapping("/insertInsureDiseaseRecord")
    public WrapperResponse<Boolean> insertInsureDiseaseRecord(@RequestBody InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureDiseaseRecordDTO.setCrteName(sysUserDTO.getName());
        insureDiseaseRecordDTO.setCrteId(sysUserDTO.getId());
        insureDiseaseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);
        return insurePersonnalRecordService_consumer.insertInsureDiseaseRecord(map);
    }

    /**
     * @Method insertInsureFixRecord
     * @Desrciption  医保统一支付平台：新增人员定点备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 17:36
     * @Return
    **/
    @PostMapping("/insertInsureFixRecord")
    public WrapperResponse<Boolean> insertInsureFixRecord(@RequestBody InsureFixPersonnalRecordDTO fixPersonnalRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        fixPersonnalRecordDTO.setFixmedinsName(sysUserDTO.getHospName());
        fixPersonnalRecordDTO.setCrteId(sysUserDTO.getId());
        fixPersonnalRecordDTO.setCrteName(sysUserDTO.getName());
        fixPersonnalRecordDTO.setCrteTime(DateUtils.getNow());
        fixPersonnalRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("fixPersonnalRecordDTO",fixPersonnalRecordDTO);
        return insurePersonnalRecordService_consumer.insertInsureFixRecord(map);
    }
    /**
     * @Method deleteInsureFixRecord
     * @Desrciption  医保统一支付平台：撤销人员定点备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 17:36
     * @Return
     **/
    @PostMapping("/deleteInsureFixRecord")
    public WrapperResponse<Boolean> deleteInsureFixRecord(@RequestBody InsureFixPersonnalRecordDTO fixPersonnalRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        fixPersonnalRecordDTO.setHospName(sysUserDTO.getHospName());
        fixPersonnalRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("fixPersonnalRecordDTO",fixPersonnalRecordDTO);
        return insurePersonnalRecordService_consumer.deleteInsureFixRecord(map);
    }

    /**
     * @Method queryPageInsureFixRecord
     * @Desrciption  医保统一支付平台：人员定点备案查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/30 16:32
     * @Return
     **/
    @GetMapping("/queryPageInsureFixRecord")
    public WrapperResponse<PageDTO> queryPageInsureFixRecord(InsureFixPersonnalRecordDTO fixPersonnalRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        fixPersonnalRecordDTO.setHospName(sysUserDTO.getHospName());
        fixPersonnalRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("fixPersonnalRecordDTO",fixPersonnalRecordDTO);
        return insurePersonnalRecordService_consumer.queryPageInsureFixRecord(map);
    }

    /**
     * @Method queryPageInptRecord
     * @Desrciption  转院备案查询
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:26
     * @Return PageDTO
    **/
    @GetMapping("/queryPageInptRecord")
    public WrapperResponse<PageDTO> queryPageInptRecord(InsureInptRecordDTO insureInptRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureInptRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureInptRecordDTO",insureInptRecordDTO);
        return insurePersonnalRecordService_consumer.queryPageInptRecord(map);
    }
    /**
     * @Method insertInptRecord
     * @Desrciption  新增人员转院备案
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/4/7 10:30
     * @Return Boolean
    **/
    @PostMapping("/insertInptRecord")
    public WrapperResponse<Boolean> insertInptRecord(@RequestBody  InsureInptRecordDTO insureInptRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureInptRecordDTO.setCrteId(sysUserDTO.getId());
        insureInptRecordDTO.setCrteName( sysUserDTO.getName());
        insureInptRecordDTO.setCrteTime(DateUtils.getNow());
        insureInptRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureInptRecordDTO",insureInptRecordDTO);
        return insurePersonnalRecordService_consumer.insertInptRecord(map);
    }

    /**
     * @Method deleteInptRecord
     * @Desrciption  医保统一支付平台：转院备案撤销
     * @Param insureInptRecordDTO
     *
     * @Author fuhui
     * @Date   2021/3/30 16:13
     * @Return Boolean
     **/
    @PostMapping("/deleteInptRecord")
    public WrapperResponse<Boolean> deleteInptRecord(@RequestBody InsureInptRecordDTO insureInptRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureInptRecordDTO.setHospName(sysUserDTO.getHospName());
        insureInptRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureInptRecordDTO",insureInptRecordDTO);
        return insurePersonnalRecordService_consumer.deleteInptRecord(map);
    }

    /**
     * @Description: 门诊两病备案
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @PostMapping("/insertOuptTwoDiseRecord")
    public WrapperResponse<Boolean> insertOuptTowDiseRecord(@RequestBody InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        insureDiseaseRecordDTO.setHospName(sysUserDTO.getHospName());
        insureDiseaseRecordDTO.setCrteId(sysUserDTO.getId());
        insureDiseaseRecordDTO.setCrteName(sysUserDTO.getName());
        insureDiseaseRecordDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);
        return insurePersonnalRecordService_consumer.insertOuptTowDiseRecord(map);
    }

    /**
     * @Description: 门诊两病备案撤销
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @PostMapping("deleteOutptTwoDiseRecord")
    public WrapperResponse<Boolean> deleteOutptTwoDiseRecord(@RequestBody InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDiseaseRecordDTO.setHospName(sysUserDTO.getHospName());
        insureDiseaseRecordDTO.setHospCode( sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);

        return insurePersonnalRecordService_consumer.deleteOutptTwoDiseRecord(map);

    }

    /**
     * @Description: 门诊两病查询
     * @Param: [insureDiseaseRecordDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangxuan
     * @Date: 2021-04-29
     */
    @GetMapping("/queryPageOutptTwoDiseRecord")
    public WrapperResponse<PageDTO> queryPageOutptTwoDiseRecord(InsureDiseaseRecordDTO insureDiseaseRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        insureDiseaseRecordDTO.setHospName(sysUserDTO.getHospName());
        insureDiseaseRecordDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDiseaseRecordDTO",insureDiseaseRecordDTO);
        return insurePersonnalRecordService_consumer.queryPageOutptTwoDiseRecord(map);
    }

    /**
     * @Description: 门诊两病查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-30
     */
    @PostMapping("queryOutptTwoDiseInfo")
    public WrapperResponse<Map<String, Object>> queryOutptTwoDiseInfo(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insurePersonnalRecordService_consumer.queryOutptTwoDiseInfo(map);
    }
    

    /**
     * @Method insertSpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
    **/
    @PostMapping("/insertSpecialOutptRecord")
    public WrapperResponse<Boolean> insertSpecialOutptRecord(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insurePersonnalRecordService_consumer.insertSpecialOutptRecord(map);
    }

    /**
     * @Method insertSpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
     **/
    @PostMapping("/deleteSpecialOutptRecord")
    public WrapperResponse<Boolean> deleteSpecialOutptRecord(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insurePersonnalRecordService_consumer.deleteSpecialOutptRecord(map);
    }

    /**
     * @Method querySpecialOutptRecord
     * @Desrciption  江西省：门诊单病种备案登记查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/25 10:33
     * @Return
     **/
    @GetMapping("/querySpecialOutptRecord")
    public WrapperResponse<PageDTO> querySpecialOutptRecord(@RequestParam Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insurePersonnalRecordService_consumer.querySpecialOutptRecord(map);
    }

    /**
     * @Method queryPageSpecialRecord
     * @Desrciption  江西省：门诊单病种备案分页查询（his）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/29 10:24
     * @Return
    **/
    @GetMapping("/queryPageSpecialRecord")
    public WrapperResponse<PageDTO> queryPageSpecialRecord(InsureSpecialRecordDTO insureSpecialRecordDTO, HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        insureSpecialRecordDTO.setHospCode(hospCode);
        map.put("hospCode",hospCode);
        map.put("insureSpecialRecordDTO",insureSpecialRecordDTO);
        return insurePersonnalRecordService_consumer.queryPageSpecialRecord(map);
    }
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @param req
     * @param res
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @GetMapping("/queryPageInsureAccidentInjureRecord")
    public WrapperResponse<PageDTO> queryPageInsureAccidentInjureRecord(InsureAccidentalInjuryDTO insureAccidentalInjuryDTO, HttpServletRequest req, HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        SysUserDTO sysUserDTO = getSession(req, res);
        String hospCode = sysUserDTO.getHospCode();
        insureAccidentalInjuryDTO.setHospCode(hospCode);
        map.put("hospCode",hospCode);
        map.put("insureAccidentalInjuryDTO",insureAccidentalInjuryDTO);
        return insurePersonnalRecordService_consumer.queryPageInsureAccidentInjureRecord(map);
    }
    /**
     * @Author 医保二部-张金平
     * @Date 2022-05-07 10:27
     * @Description 人员意外伤害备案
     * @param insureAccidentalInjuryDTO
     * @param req
     * @param res
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @PostMapping("/insertInsureAccidentInjureRecord")
    public  WrapperResponse<Boolean> insertInsureAccidentInjureRecord(@RequestBody InsureAccidentalInjuryDTO insureAccidentalInjuryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureAccidentalInjuryDTO.setHospCode(sysUserDTO.getHospCode());
        insureAccidentalInjuryDTO.setHospName(sysUserDTO.getHospName());
        insureAccidentalInjuryDTO.setCrteId(sysUserDTO.getId());
        insureAccidentalInjuryDTO.setCrteName(sysUserDTO.getName());
        insureAccidentalInjuryDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureAccidentalInjuryDTO",insureAccidentalInjuryDTO);
        return insurePersonnalRecordService_consumer.insertInsureAccidentInjureRecord(map);
    }
}

package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.*;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
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
 *@Class_name: DoctorAdviceController
 *@Describe: 医嘱管理
 *@Author: zengfeng
 *@Eamil: 954123283@powersi.com.cn
 *@Date: 2020/9/27 14:16
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/doctorAdviceController")
@Slf4j
public class DoctorAdviceController extends BaseController {

    @Resource
    DoctorAdviceService doctorAdviceService_consumer;

    @Resource
    SysUserService sysUserService_consumer ;

    /**
     * @Method queryInptVisitPage
     * @Desrciption 查询住院就诊信息
     * @param inptVisitDTO
     * @Author zengfeng
     * @Date   2020/9/27 15:14
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/queryInptVisitPage")
    public WrapperResponse<PageDTO> queryInptVisitPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        //医院编码
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        //医院编码
        map.put("hospCode", sysUserDTO.getHospCode());
        //就诊记录
        map.put("inptVisitDTO",inptVisitDTO);
        inptVisitDTO.setZgDoctorId(sysUserDTO.getId());
        WrapperResponse<PageDTO> pageDTOWrapperResponse = doctorAdviceService_consumer.queryInptVisitPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method queryInptAdviceVisitID
     * @Desrciption 查询住院医嘱信息
     * @param inptAdviceDTO 医嘱信息 visitId:就诊ID
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return List<InptAdviceDTO>
     **/
    @GetMapping("/queryInptAdviceVisitID")
    public WrapperResponse<List<InptAdviceDTO>> queryInptAdviceVisitID(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptAdviceDTO.getVisitId())){
            throw new RuntimeException("就诊ID不能为空");
        }
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceDTO",inptAdviceDTO);
        return doctorAdviceService_consumer.queryInptAdviceVisitID(paramMap);
    }

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/saveInptAdvice")
    public WrapperResponse<Boolean> saveInptAdvice(@RequestBody InptAdviceTDTO inptAdviceTDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inptAdviceTDTO == null || ListUtils.isEmpty(inptAdviceTDTO.getInptAdviceDTOList()) || inptAdviceTDTO.getInptAdviceDTOList().size() == 0){
            throw new RuntimeException("医嘱信息为空");
        }

        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        for(InptAdviceDTO inptAdviceDTO : inptAdviceTDTO.getInptAdviceDTOList()){
            //医院编码
            inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
            //医生
            inptAdviceDTO.setCrteId(sysUserDTO.getId());
            //医生姓名
            inptAdviceDTO.setCrteName(sysUserDTO.getName());
            if (StringUtils.isEmpty(inptAdviceDTO.getBabyId())){
                inptAdviceDTO.setBabyId(null);
            }
            //就诊科室，会诊病人开嘱科室前端赋值过来
            if(StringUtils.isEmpty(inptAdviceDTO.getDeptId())) {
                inptAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }
        //医嘱信息
        paramMap.put("inptAdviceDTOList", inptAdviceTDTO.getInptAdviceDTOList());
        // 保存处方信息
        return doctorAdviceService_consumer.saveInptAdvice(paramMap);
    }

    /**
     * @Method deleteInptAdvice
     * @Desrciption 删除医院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/deleteInptAdvice")
    public WrapperResponse<Boolean> deleteInptAdvice(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceDTO", inptAdviceDTO);
        // 删除医院医嘱信息
        return doctorAdviceService_consumer.deleteInptAdvice(paramMap);
    }

    /**
     * @Method updateBatchInptAdviceStop
     * @Desrciption 批量更新医嘱停嘱或者取消停嘱
     * @param inptAdviceDTO 医嘱信息 type：1:停嘱 2：取消停嘱
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/updateBatchInptAdviceStop")
    public WrapperResponse<Boolean> updateBatchInptAdviceStop(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(inptAdviceDTO.getType())){
            throw new RuntimeException("类型不能为空信息为空");
        }
        if("1".equals(inptAdviceDTO.getType())){
            //停嘱核收标志
            inptAdviceDTO.setIsStop(Constants.SF.S);
            //医生ID
            inptAdviceDTO.setStopDoctorId(sysUserDTO.getId());
            //医生
            inptAdviceDTO.setStopDoctorName(sysUserDTO.getName());
        }else if("2".equals(inptAdviceDTO.getType())){
            //停嘱核收标志
            inptAdviceDTO.setIsStop(Constants.SF.F);
        }else{
            throw new RuntimeException("类型值错误");
        }
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceDTO", inptAdviceDTO);
        // 更新医嘱信息
        return doctorAdviceService_consumer.updateBatchInptAdviceStop(paramMap);
    }

    /**
     * @Method updateBatchInptAdviceplan
     * @Desrciption 批量更新医嘱预停时间
     * @param inptAdviceDTO 医嘱信息 type：1:预停 2：取消预停
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/updateBatchInptAdviceplan")
    public WrapperResponse<Boolean> updateBatchInptAdviceplan(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if("1".equals(inptAdviceDTO.getType())){
            if(inptAdviceDTO.getPlanStopTime() == null){
                throw new RuntimeException("预停时间不能为空");
            }
        }else if("2".equals(inptAdviceDTO.getType())){
            inptAdviceDTO.setPlanStopTime(null);
        }
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceDTO", inptAdviceDTO);
        // 更新医嘱信息
        return doctorAdviceService_consumer.updateBatchInptAdviceplan(paramMap);
    }

    /**
     * @Menthod getInptDiagnose
     * @Desrciption  获取诊断信息
     * @param  inptVisitDTO: id ：就诊ID
     * @Author zengfeng
     * @Date   2020/9/19 10:24
     * @Return List<OutptDiagnoseDTO>
     **/
    @GetMapping("/getInptDiagnose")
    public WrapperResponse<List<InptDiagnoseDTO>> getInptDiagnose(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);
        return doctorAdviceService_consumer.getInptDiagnose(map);
    }

    /**
     * @Menthod saveOutptDiagnose
     * @Desrciption  保存诊断信息
     * @param inptDiagnoseDTO: visitId:就诊ID  outptDiagnoseDOList：诊断明细
     * @Author zengfeng
     * @Date   2020/10/26 10:24
     * @Return 是否成功
     **/
    @NoRepeatSubmit
    @PostMapping("/saveOutptDiagnose")
    public WrapperResponse<Boolean> saveInptDiagnose(@RequestBody InptDiagnoseDTO inptDiagnoseDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        inptDiagnoseDTO.setHospCode(sysUserDTO.getHospCode());
        inptDiagnoseDTO.setCrteName(sysUserDTO.getName());
        inptDiagnoseDTO.setCrteId(sysUserDTO.getId());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptDiagnoseDTO",inptDiagnoseDTO);
        return doctorAdviceService_consumer.saveInptDiagnose(map);
    }

    /**
     * @Method saveInptAdvice
     * @Desrciption 保存医院医嘱信息
     * @param inptAdviceTDTO 医嘱信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/updateInptAdviceIsSubmit")
    public WrapperResponse<Boolean> updateInptAdviceIsSubmit(@RequestBody InptAdviceTDTO inptAdviceTDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inptAdviceTDTO == null || StringUtils.isEmpty(inptAdviceTDTO.getIds())){
            throw new RuntimeException("提交医嘱不能为空");
        }
        Map paramMap = new HashMap();
        SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
        sysUserSystemDTO.setDeptCode(sysUserDTO.getLoginBaseDeptDTO().getCode());
        sysUserSystemDTO.setSystemCode(sysUserDTO.getSystemCode());
        sysUserSystemDTO.setHospCode(sysUserDTO.getHospCode());
        sysUserSystemDTO.setUserCode(sysUserDTO.getCode());
        paramMap.put("sysUserSystemDTO",sysUserSystemDTO);
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //带教医生
        SysUserDTO sysUserDTO1 = sysUserService_consumer.querySysUserHaveTeachDoctor(paramMap).getData();
        if(sysUserDTO1 != null){
            inptAdviceTDTO.setTeachDoctorId(sysUserDTO1.getId());
            inptAdviceTDTO.setTeachDoctorName(sysUserDTO1.getName());
        }

        for(InptAdviceDTO inptAdviceDTO : inptAdviceTDTO.getInptAdviceDTOList()){
            //医院编码
            inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        }
        //医院编码
        inptAdviceTDTO.setHospCode(sysUserDTO.getHospCode());
        //提交人ID
        inptAdviceTDTO.setSubmitId(sysUserDTO.getId());
        //提交人
        inptAdviceTDTO.setSubmitName(sysUserDTO.getName());
        //操作科室
        inptAdviceTDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());

        paramMap.clear();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceTDTO", inptAdviceTDTO);
        // 提交医嘱信息
        return doctorAdviceService_consumer.updateInptAdviceIsSubmit(paramMap);
    }

    /**
     * @Method updateBatchInptAdviceCancel
     * @Desrciption 取消医嘱信息
     * @param inptAdviceDTO 医嘱信息 IatIds，取消ID
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/updateBatchInptAdviceCancel")
    public WrapperResponse<Boolean> updateBatchInptAdviceCancel(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inptAdviceDTO == null || StringUtils.isEmpty(inptAdviceDTO.getIatIds())){
            throw new RuntimeException("取消医嘱不能为空");
        }
        //医院编码
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        //取消人ID
        inptAdviceDTO.setCancelId(sysUserDTO.getId());
        //取消人
        inptAdviceDTO.setCancelName(sysUserDTO.getName());
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医嘱信息
        paramMap.put("inptAdviceDTO", inptAdviceDTO);
        // 取消医嘱信息
        return doctorAdviceService_consumer.updateBatchInptAdviceCancel(paramMap);
    }

    /**
     * @Method saveInptAdviceZCY
     * @Desrciption 保存医院医嘱信息
     * @param inptAdviceDTO 医嘱信息
     * @Author pengbo
     * @Date   2021/06/01 14:44
     * @Return boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/saveInptAdviceZCY")
    public WrapperResponse<Boolean> saveInptAdviceZCY(@RequestBody InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (inptAdviceDTO == null || ListUtils.isEmpty(inptAdviceDTO.getInptAdviceDTOList())) {
            throw new RuntimeException("保存失败,医嘱信息为空！");
        }
        inptAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        inptAdviceDTO.setCrteId(sysUserDTO.getId());
        inptAdviceDTO.setCrteName(sysUserDTO.getName());

        SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
        sysUserSystemDTO.setDeptCode(sysUserDTO.getLoginBaseDeptDTO().getId());
        sysUserSystemDTO.setSystemCode(sysUserDTO.getSystemCode());
        sysUserSystemDTO.setHospCode(sysUserDTO.getHospCode());
        sysUserSystemDTO.setUserCode(sysUserDTO.getCode());

        Map paramMap = new HashMap();
        paramMap.put("sysUserSystemDTO", sysUserSystemDTO);
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医嘱主表
        paramMap.put("inptAdviceDTO",inptAdviceDTO);
        //带教医生
        List<SysUserSystemDTO> sysUserSystemDTOS = (List<SysUserSystemDTO>) sysUserService_consumer.queryUserSysPage(paramMap).getData().getResult();

        if (!ListUtils.isEmpty(sysUserSystemDTOS)) {
            inptAdviceDTO.setTeachDoctorId(sysUserSystemDTOS.get(0).getTeacherCode());
            inptAdviceDTO.setTeachDoctorName(sysUserSystemDTOS.get(0).getTeacherName());
        }
        //医嘱信息
        paramMap.put("inptAdviceList", inptAdviceDTO.getInptAdviceDTOList());
        // 保存处方信息
        return doctorAdviceService_consumer.saveInptAdviceZCY(paramMap);
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 08:48
     * @Return:
     **/
    @GetMapping("/queryLimitDrugList")
    public WrapperResponse<List<InsureItemMatchDTO>> queryLimitDrugList(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map paramMap = new HashMap();
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        paramMap.put("inptAdviceDTO", inptAdviceDTO);
        return doctorAdviceService_consumer.queryLimitDrugList(paramMap);
    }

    /**
     * @Menthod: updateInptAdviceDetailLmt
     * @Desrciption: 更新医嘱明细表限制用药相关字段
     * @Param: insureItemMatchDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-22 10:39
     * @Return:
     **/
    @PostMapping("/updateInptAdviceDetailLmt")
    public WrapperResponse<Boolean> updateInptAdviceDetailLmt(@RequestBody List<InsureItemMatchDTO> insureItemMatchDTOS, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTOS.forEach(insureItemMatchDTO -> insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode()));
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureItemMatchDTOS", insureItemMatchDTOS);
        return doctorAdviceService_consumer.updateInptAdviceDetailLmt(map);
    }

    /**
     * @Menthod: queryLisAdvice
     * @Desrciption: 根据合管条件查询同类型的lis医嘱，用于合并打印lis申请单
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-11 10:24
     * @Return:
     **/
    @GetMapping("/queryLisAdvice")
    public WrapperResponse<List<InptAdviceDTO>> queryLisAdvice(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceDTO", inptAdviceDTO);
        return doctorAdviceService_consumer.queryLisAdvice(map);
    }

    /**
     * @Menthod: getZyAdviceByVisitId
     * @Desrciption: 根据就诊id查询中药医嘱列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-26 11:31
     * @Return:
     **/
    @GetMapping("/getZyAdviceByVisitId")
    public WrapperResponse<List<InptAdviceDTO>> getZyAdviceByVisitId(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new RuntimeException("未选择就诊人信息");
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return doctorAdviceService_consumer.getZyAdviceByVisitId(map);
    }

    /**
     * @Menthod: queryUnsubmitAdviceList
     * @Desrciption: 查询未提交医嘱信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-12-01 10:29
     * @Return:
     **/
    @GetMapping("/queryUnsubmitAdviceList")
    public WrapperResponse<Boolean> queryUnsubmitAdviceList(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteName",sysUserDTO.getName());
        map.put("crteId",sysUserDTO.getId());
        return doctorAdviceService_consumer.queryUnsubmitAdviceList(map);
    }



    /**
     * @Menthod: queryLisAdvice
     * @Desrciption: 根据合管条件查询同类型的lis医嘱，用于合并打印lis申请单
     * @Param: inptAdviceDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-11 10:24
     * @Return:
     **/
    @GetMapping("/queryLisOrPacsAdvice")
    public WrapperResponse<List<InptAdviceDTO>> queryLisOrPacsAdvice(InptAdviceDTO inptAdviceDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        inptAdviceDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceDTO", inptAdviceDTO);
        return doctorAdviceService_consumer.queryLisOrPacsAdvice(map);
    }
}

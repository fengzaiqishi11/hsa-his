package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO;
import cn.hsa.module.inpt.medical.service.MedicalAdviceService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.inpt
* @class_name: MedicalAdviceController
* @Description: 医嘱核收控制层(查询，核收，拒收)
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/24 16:20
* @Company: 创智和宇
**/

@RestController
@RequestMapping("/web/inpt/medicalAdvice")
@Slf4j
public class MedicalAdviceController extends BaseController {

    @Resource
    private DoctorAdviceService doctorAdviceService_consumer;

    @Resource
    private MedicalAdviceService medicalAdviceService_consumer;

    @Resource
    private RedisUtils redisUtils;

    /**
    * @Method queryDoctorAdvice
    * @Param [medicalAdviceDTO]
    * @description   根据就诊id，手术名称查询
    * @author marong
    * @date 2020/9/22 19:58
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.medical.dto.MedicalAdviceDTO>>
    * @throws
    */
    @GetMapping(value = "/getOperationName")
    WrapperResponse<List<InptAdviceDetailDTO>> getOperationName(InptAdviceDetailDTO inptAdviceDetailDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptAdviceDetailDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptAdviceDetailDTO", inptAdviceDetailDTO);
        WrapperResponse<List<InptAdviceDetailDTO>> operationNameList = doctorAdviceService_consumer.queryOperationNameList(map);
        return operationNameList;
    }

    /**
     * @Method: getMedicalAdvices
     * @Description: 根据条件查询医嘱列表
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 16:23
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/getMedicalAdvices")
    public WrapperResponse<PageDTO> getMedicalAdvices(MedicalAdviceDTO medicalAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        medicalAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", medicalAdviceDTO);
        return medicalAdviceService_consumer.getMedicalAdvices(map);
    }

    /**
     * @Method: acceptMedicalAdvices
     * @Description: 医嘱核收
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/acceptMedicalAdvices")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> acceptMedicalAdvices(@RequestBody MedicalAdviceDTO medicalAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        medicalAdviceDTO.setCheckId(sysUserDTO.getId());
        medicalAdviceDTO.setCheckName(sysUserDTO.getName());
        medicalAdviceDTO.setCheckTime(DateUtils.getNow());
        medicalAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", medicalAdviceDTO);
        String key = sysUserDTO.getHospCode() + medicalAdviceDTO.getDeptId() + "_YZHS";
        if(redisUtils.hasKey(key)){
            throw  new RuntimeException("当前科室正在做医嘱核收,请耐心等待!");
        }
        try{
            redisUtils.set(key,key);
            medicalAdviceService_consumer.acceptMedicalAdvices(map);
        }catch (Exception e){
            throw  new RuntimeException("医嘱核收失败,原因：" + e.getMessage());
        } finally {
            redisUtils.del(key);
        }
        return WrapperResponse.success(true);
    }

    /**
     * @Method: refuseMedicalAdvices
     * @Description: 医嘱拒收
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/refuseMedicalAdvices")
    public WrapperResponse<Boolean> refuseMedicalAdvices(@RequestBody MedicalAdviceDTO medicalAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        medicalAdviceDTO.setCheckTime(DateUtils.getNow());
        medicalAdviceDTO.setCrteId(sysUserDTO.getId());
        medicalAdviceDTO.setCrteName(sysUserDTO.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", medicalAdviceDTO);
        return medicalAdviceService_consumer.refuseMedicalAdvices(map);
    }


    /**
     * @Method: updateAdviceInChecked
     * @Description: 修改医嘱信息，核收人，核对签名人，核收状态
     * isChecked: 0：未核收，1：已核对，2：已核收未核对，3：核收拒绝，4：核对拒绝
     * @Param: [medicalAdviceDTO]
     * @Author: pengbo
     * @Date: 2022/08/24 16:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateAdviceInChecked")
    public WrapperResponse<Boolean> updateAdviceInChecked(@RequestBody MedicalAdviceDTO medicalAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        medicalAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String, Object> map = new HashMap<>();
        // 0：未核收，1：已核对，2：已核收未核对，3：核收拒绝，4：核对拒绝
        if("2".equals(medicalAdviceDTO.getIsNewStatus())){
            /**
             * 2.已核收未核对
             * 1.开嘱核收：都不为空,
             * 2.停嘱核收：都不为空
             */
            medicalAdviceDTO.setCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setCheckTime(new Date());

            medicalAdviceDTO.setStopCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setStopCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setStopCheckTime(new Date());
            if("0".equals(medicalAdviceDTO.getIsMyself())){
                medicalAdviceDTO.setCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setStopCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }else{
                medicalAdviceDTO.setCheckSecondId(medicalAdviceDTO.getCheckSecondId());
                medicalAdviceDTO.setCheckSecondName(medicalAdviceDTO.getCheckSecondName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(medicalAdviceDTO.getStopCheckSecondId());
                medicalAdviceDTO.setStopCheckSecondName(medicalAdviceDTO.getStopCheckSecondName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }

        }else if ("3".equals(medicalAdviceDTO.getIsNewStatus())){
            /**
             * 3.核收拒绝
             * 1.开嘱拒收：核收时间，核收人不为空，其他为空
             * 2.停嘱拒收：停嘱核收时间，停嘱核收人不为空，其他为空
             */
            medicalAdviceDTO.setCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setCheckTime(new Date());
            medicalAdviceDTO.setCheckSecondId(null);
            medicalAdviceDTO.setCheckSecondName(null);
            medicalAdviceDTO.setCheckSecondTime(null);

            medicalAdviceDTO.setStopCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setStopCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setStopCheckTime(new Date());
            medicalAdviceDTO.setStopCheckSecondId(null);
            medicalAdviceDTO.setStopCheckSecondName(null);
            medicalAdviceDTO.setStopCheckSecondTime(null);
            map.put("hospCode", sysUserDTO.getHospCode());
            map.put("medicalAdviceDTO", medicalAdviceDTO);
            medicalAdviceService_consumer.refuseMedicalAdvices(map);

        } else if ("4".equals(medicalAdviceDTO.getIsNewStatus())) {
            /**
             * 4.核对拒绝.
             * 1.开嘱核收：都不为空,但是不需要改变核收人
             * 2.停嘱核收：都不为空,但是不需要改变停嘱核收人
             */
            medicalAdviceDTO.setCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setCheckTime(new Date());
            medicalAdviceDTO.setStopCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setStopCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setStopCheckTime(new Date());

            if("0".equals(medicalAdviceDTO.getIsMyself())){
                medicalAdviceDTO.setCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setStopCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }else{
                medicalAdviceDTO.setCheckSecondId(medicalAdviceDTO.getCheckSecondId());
                medicalAdviceDTO.setCheckSecondName(medicalAdviceDTO.getCheckSecondName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(medicalAdviceDTO.getStopCheckSecondId());
                medicalAdviceDTO.setStopCheckSecondName(medicalAdviceDTO.getStopCheckSecondName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }
        } else if ("1".equals(medicalAdviceDTO.getIsNewStatus())) {
            /**
             * 1.核对
             * 1.开嘱核收：都不为空,但是不需要改变核收人
             * 2.停嘱核收：都不为空,但是不需要改变停嘱核收人
             */
            if("0".equals(medicalAdviceDTO.getIsMyself())){
                medicalAdviceDTO.setCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(sysUserDTO.getId());
                medicalAdviceDTO.setStopCheckSecondName(sysUserDTO.getName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }else{
                medicalAdviceDTO.setCheckSecondId(medicalAdviceDTO.getCheckSecondId());
                medicalAdviceDTO.setCheckSecondName(medicalAdviceDTO.getCheckSecondName());
                medicalAdviceDTO.setCheckSecondTime(new Date());
                medicalAdviceDTO.setStopCheckSecondId(medicalAdviceDTO.getStopCheckSecondId());
                medicalAdviceDTO.setStopCheckSecondName(medicalAdviceDTO.getStopCheckSecondName());
                medicalAdviceDTO.setStopCheckSecondTime(new Date());
            }
            medicalAdviceDTO.setCheckId(sysUserDTO.getId());
            medicalAdviceDTO.setCheckName(sysUserDTO.getName());
            medicalAdviceDTO.setCheckTime(new Date());
            map.put("hospCode", sysUserDTO.getHospCode());
            map.put("medicalAdviceDTO", medicalAdviceDTO);
           medicalAdviceService_consumer.acceptMedicalAdvices(map);
        }
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", medicalAdviceDTO);

        return medicalAdviceService_consumer.updateAdviceInChecked(map);
    }


    /**
     * @Method: getMedicalAdvices
     * @Description: 根据条件查询医嘱列表
     * @Param: [medicalAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/24 16:23
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/getMedicalAdvicesNew")
    public WrapperResponse<PageDTO> getMedicalAdvicesNew(MedicalAdviceDTO medicalAdviceDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        medicalAdviceDTO.setHospCode(sysUserDTO.getHospCode());
        medicalAdviceDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("medicalAdviceDTO", medicalAdviceDTO);
        return medicalAdviceService_consumer.getMedicalAdvicesNew(map);
    }

}

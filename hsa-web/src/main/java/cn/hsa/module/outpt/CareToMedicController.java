package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.CareToMedicApplyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-03-02 17:06
 * @desc
 **/
@RestController
@RequestMapping("/web/outpt/careToMedic")
@Slf4j
public class CareToMedicController extends BaseController {
    @Resource
    private CareToMedicApplyService careToMedicApplyService_consumer;
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询出医院病人信息表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @PostMapping("/queryCareToMedicPage")
    public WrapperResponse<PageDTO> queryCareToMedicPage(@RequestBody MedicToCareDTO medicToCareDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res);
        medicToCareDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("medicToCareDTO",medicToCareDTO);
        return careToMedicApplyService_consumer.queryCareToMedicPage(map);
    }
    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<MedicToCareDTO>
     **/
    @PostMapping("/getMedicToCareInfoById")
    public WrapperResponse<Map<String, Object>> getMedicToCareInfoById(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        map.put("hospCode",userDTO.getHospCode());
        return careToMedicApplyService_consumer.getMedicToCareInfoById(map);
    }
    /**
     * @Menthod getMedicToCareInfoById()
     * @Desrciption   根据主键id查询申请明细详细信息
     * @Param medicToCareDTO
     * @Author yuelong.chen
     * @Date   2022/2/28 9:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<MedicToCareDTO>
     **/
    @PostMapping("/updateCareToMedic")
    public WrapperResponse<Boolean> updateCareToMedic(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        map.put("hospCode",userDTO.getHospCode());
        map.put("crteId",userDTO.getId());
        map.put("crteName",userDTO.getName());
        map.put("crteTime",new Date());
        return  careToMedicApplyService_consumer.updateCareToMedic(map);
    }

}
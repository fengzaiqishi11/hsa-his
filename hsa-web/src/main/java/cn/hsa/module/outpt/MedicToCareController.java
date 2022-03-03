package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.medictocare.service.MedicToCareService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuelong.chen
 * @create 2022-02-28 9:12
 * @desc 医转养controller层
 **/
@RestController
@RequestMapping("/web/outpt/medicToCare")
@Slf4j
public class MedicToCareController extends BaseController {

    @Resource
    private MedicToCareService medicToCareService_consumer;
    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询出医院病人信息表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @date 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @PostMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(@RequestBody MedicToCareDTO medicToCareDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res);
        medicToCareDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("medicToCareDTO",medicToCareDTO);
        return medicToCareService_consumer.queryPage(map);
    }
    /**
     * @Menthod: insertMedicToCare()
     * @Desrciption: 医转养申请
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: Boolean
     **/
    @PostMapping("/insertMedicToCare")
    public WrapperResponse<Boolean> insertMedicToCare(@RequestBody MedicToCareDTO medicToCareDTO,HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res) ;
        medicToCareDTO.setHospCode(userDTO.getHospCode());
        medicToCareDTO.setId(SnowflakeUtils.getId());
        medicToCareDTO.setApplyId(userDTO.getId());
        medicToCareDTO.setApplyName(userDTO.getName());
        medicToCareDTO.setApplyTime(new Date());
        medicToCareDTO.setCrteId(userDTO.getId());
        medicToCareDTO.setCrteName(userDTO.getName());
        medicToCareDTO.setCrteTime(new Date());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("medicToCareDTO",medicToCareDTO);
        return medicToCareService_consumer.insertMedicToCare(map);
    }
    /**
     * @Menthod: queryHospitalPatientInfoPage()
     * @Desrciption: 分页查询查询医转养患者列表
     * @Param: medicToCareDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022/2/28 9:38
     * @Return: cn.hsa.base.PageDTO
     **/
    @PostMapping("/queryMedicToCareInfoPage")
    public WrapperResponse<PageDTO>  queryMedicToCareInfoPage(@RequestBody MedicToCareDTO medicToCareDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO userDTO = getSession(req, res);
        medicToCareDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",userDTO.getHospCode());
        map.put("medicToCareDTO",medicToCareDTO);
        return medicToCareService_consumer.queryMedicToCareInfoPage(map);
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
        return medicToCareService_consumer.getMedicToCareInfoById(map);
    }
}
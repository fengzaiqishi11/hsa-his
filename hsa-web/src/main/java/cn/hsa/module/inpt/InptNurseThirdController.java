package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.inpt.inptnursethird.service.InptNurseThirdService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
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
 * @Package_name: cn.hsa.module.inpt
 * @Class_name:: InptNurseThirdController
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/InptNurseThird")
@Slf4j
public class InptNurseThirdController extends BaseController {

    @Resource
    InptNurseThirdService inptNurseThirdService_consumer;


    @PostMapping("/insertList")
    public WrapperResponse<Boolean> insertList(@RequestBody InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        List<InptNurseThirdDTO> list = inptNurseThirdDTO.getInptNurseThirds();
        if(ListUtils.isEmpty(list)){
           throw new AppException("数据不能为空！");
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setHospCode(sysUserDTO.getHospCode());
            list.get(i).setCrteId(sysUserDTO.getId());
            list.get(i).setCrteName(sysUserDTO.getName());
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", inptNurseThirdDTO);
        return inptNurseThirdService_consumer.insertList(map);
    }


    @GetMapping("/queryAll")
    public WrapperResponse<List<InptNurseThirdDTO>> queryAll(InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", inptNurseThirdDTO);

        return inptNurseThirdService_consumer.queryAll(map);
    }

    @GetMapping("/queryAllRecordTime")
    public WrapperResponse<List<InptNurseThirdDTO>> queryAllRecordTime(InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res) throws Exception {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", inptNurseThirdDTO);
        return inptNurseThirdService_consumer.queryAllRecordTime(map);
    }


    /**
     * @Method getPageList
     * @Param [inptNurseThirdDTO]
     * @description   获取分页信息
     * @author marong
     * @date 2020/10/29 10:21
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @throws
     */
    @GetMapping("/getPageList")
    public WrapperResponse<List<Map<String, Object>>> getPageList(InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res)  {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("bean", inptNurseThirdDTO);
        return inptNurseThirdService_consumer.getPageList(map);
    }

    /**
     * @Method queryAllByTimeSlot
     * @Param [inptNurseThirdDTO]
     * @description   查询三测单
     * @author marong
     * @date 2020/10/29 9:53
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>>
     * @throws
     */
    @GetMapping("/queryAllByTimeSlot")
    public WrapperResponse<Map<String, Object>> queryAllByTimeSlot(InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res)  {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseThirdDTO", inptNurseThirdDTO);

        return inptNurseThirdService_consumer.queryAllByTimeSlot(map);
    }

    @GetMapping("/saveInptNurseThird")
    public WrapperResponse<Boolean> saveInptNurseThird(InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res)  {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseThirdDTO", inptNurseThirdDTO);
        return inptNurseThirdService_consumer.saveInptNurseThird(map);
    }

    /**
     * @Method queryOperByVisitId
     * @Param [OperInfoRecordDTO]
     * @description   根据就诊visitId查询已完成手术列表
     * @author luoyong
     * @date 2020/10/29 9:53
     * @return List<OperInfoRecordDTO>
     * @throws
     */
    @GetMapping("/queryOperByVisitId")
    public WrapperResponse<List<OperInfoRecordDTO>> queryOperByVisitId(OperInfoRecordDTO operInfoRecordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        operInfoRecordDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("operInfoRecordDTO", operInfoRecordDTO);
        return inptNurseThirdService_consumer.queryOperByVisitId(map);
    }

    /**
     * @Menthod: queryInptThirdRecordByBatch
     * @Desrciption: 根据三测单节点时间点查询科室下在院病人列表
     * @Param: inptNurseThirdDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-17 16:56
     * @Return: List<InptNurseThirdDTO>
     **/
    @GetMapping("/queryInptThirdRecordByBatch")
    public WrapperResponse<List<InptNurseThirdDTO>> queryInptThirdRecordByBatch( InptNurseThirdDTO inptNurseThirdDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(inptNurseThirdDTO.getDeptId())) {
            inptNurseThirdDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseThirdDTO", inptNurseThirdDTO);
        return inptNurseThirdService_consumer.queryInptThirdRecordByBatch(map);
    }

    /**
     * @Menthod: saveBatch
     * @Desrciption: 批量保存三测单
     * @Param: inptNurseThirdDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 15:59
     * @Return: boolean
     **/
    @PostMapping("/saveBatch")
    public WrapperResponse<Boolean> saveBatch(@RequestBody List<InptNurseThirdDTO> inptNurseThirdDTOS, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (ListUtils.isEmpty(inptNurseThirdDTOS)){
            throw new RuntimeException("未选择需要保存的数据");
        }
        for (InptNurseThirdDTO inptNurseThirdDTO : inptNurseThirdDTOS) {
            if (StringUtils.isEmpty(inptNurseThirdDTO.getQueryTime())) {
                throw new RuntimeException("未选择录入三测单日期");
            }
            if (StringUtils.isEmpty(inptNurseThirdDTO.getSjd())) {
                throw new RuntimeException("未选择录入三测单时间点");
            }
            inptNurseThirdDTO.setHospCode(sysUserDTO.getHospCode());
            inptNurseThirdDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
            inptNurseThirdDTO.setCrteId(sysUserDTO.getId());
            inptNurseThirdDTO.setCrteName(sysUserDTO.getName());
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptNurseThirdDTOS", inptNurseThirdDTOS);
        return inptNurseThirdService_consumer.saveBatch(map);
    }
}

package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureSpecialAttributeService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/insure/insureSpecialAttribute")
@Slf4j
public class InsureSpecialAttributeController extends BaseController {

    @Resource
    private InsureSpecialAttributeService insureSpecialAttributeService_consumer;
    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  就医特殊属性上传病人查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    @PostMapping("/queryInsureSpecialAttributeInfoPage")
    public WrapperResponse<PageDTO> queryInsureSpecialAttributeInfoPage(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return insureSpecialAttributeService_consumer.queryInsureSpecialAttributeInfoPage(map);
    }
    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    @PostMapping("/uploadInsureSpecialAttribute")
    public WrapperResponse uploadInsureSpecialAttribute(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        insureSpecialAttributeService_consumer.UPloadInsureSpecialAttribute(map);
        return WrapperResponse.success("上传成功");
    }
    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    @PostMapping("/qureyInsureSpecialAttribute")
    public WrapperResponse<List<Map<String, Object>>> qureyInsureSpecialAttribute(@RequestBody InsureIndividualVisitDTO insureIndividualVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureIndividualVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map =new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return  insureSpecialAttributeService_consumer.qureyInsureSpecialAttribute(map);
    }
}

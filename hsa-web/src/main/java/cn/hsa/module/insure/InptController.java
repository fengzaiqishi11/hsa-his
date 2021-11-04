package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.inpt.service.InptService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name: InptController
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/22 16:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping(value = "/web/insure/inpt")
public class InptController extends BaseController {

    @Resource
    private InptService inptService;

    @Resource
    private InptVisitService inptVisitService;

    /**
     * @Menthod delInptCostTransmit
     * @Desrciption 删除住院已传输业务信息
     * @param inpt 就诊id
     * @Author Ou·Mr
     * @Date 2020/12/22 16:30 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping(value = "delInptCostTransmit")
    public WrapperResponse delInptCostTransmit(@RequestBody InptVisitDTO inpt, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        String id = inpt.getId();
        if (StringUtils.isEmpty(id)){
            throw new NullPointerException("参数错误。");
        }
        //获取个人信息
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode",sysUserDTO.getHospCode());//医院编码
        param.put("visitId",id);//就诊id

        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(id);//id

        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        param.put("inptVisitDTO",inptVisitDTO);
        param.put("crteName",sysUserDTO.getCrteName());
        param.put("crteId",sysUserDTO.getCrteId());
        param.put("userName",sysUserDTO.getName()); // 操作人
        param.put("medicalRegNo",inpt.getMedicalRegNo());
        param.put("code",sysUserDTO.getCode()); // 操作员编码
        InptVisitDTO inptVisitDTO1 = inptVisitService.getInptVisitById(param).getData();
        if (inptVisitDTO1 == null){
            throw new AppException("未找到患者个人信息。");
        }
        inptVisitDTO1.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptVisitDTO1.setUserCode(sysUserDTO.getCode());
        inptVisitDTO1.setCrteName(sysUserDTO.getName());
        param.put("inptVisitDTO",inptVisitDTO1);
        return inptService.delInptCostTransmit(param);
    }
}

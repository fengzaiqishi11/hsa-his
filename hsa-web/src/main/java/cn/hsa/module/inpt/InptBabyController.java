package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.InptBabyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: InptBabyController
 * @Describe: 新生婴儿controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-19 14:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/web/inpt/baby")
public class InptBabyController extends BaseController {

    /**
     * 新生婴儿服务
     */
    @Resource
    private InptBabyService inptBabyService_consumer;

    /**
     * @Menthod: queryByVisitId
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: PageDTO
     **/
    @GetMapping("/queryByVisitId")
    public WrapperResponse<PageDTO> queryByVisitId(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new RuntimeException("未选择就诊人信息");
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptBabyService_consumer.queryByVisitId(map);
    }

    /**
     * @Menthod: saveBaby
     * @Desrciption: 保存/编辑新生儿
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: Boolean
     **/
    @PostMapping("/saveBaby")
    public WrapperResponse<Boolean> saveBaby(@RequestBody InptBabyDTO inptBabyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptBabyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptBabyDTO", inptBabyDTO);
        return inptBabyService_consumer.saveBaby(map);
    }

    /**
     * @Menthod: getById
     * @Desrciption: 根据婴儿id查询婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: InptBabyDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<InptBabyDTO> getById(InptBabyDTO inptBabyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptBabyDTO.getId())){
            throw new RuntimeException("未选择修改信息");
        }
        inptBabyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptBabyDTO", inptBabyDTO);
        return inptBabyService_consumer.getById(map);
    }

}

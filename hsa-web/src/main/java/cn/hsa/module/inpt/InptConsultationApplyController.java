package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.consultation.dto.InptConsultationApplyDTO;
import cn.hsa.module.inpt.consultation.service.InptConsultationApplyServcie;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
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
 * @Class_name: InptConsultationApplyController
 * @Describe: 会诊申请记录controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-11-04 20:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@RestController
@RequestMapping("/web/inpt/consultationApply")
public class InptConsultationApplyController extends BaseController {

    @Resource
    private InptConsultationApplyServcie inptConsultationApplyServcie_consumer;

    /**
     * @Menthod: saveConsultationApply
     * @Desrciption: 保存会诊申请记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @PostMapping("/saveConsultationApply")
    public WrapperResponse<String> saveConsultationApply(@RequestBody InptConsultationApplyDTO inptConsultationApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptConsultationApplyDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getApplyDeptid())) {
            // 申请科室id为空，默认当前登陆科室
            inptConsultationApplyDTO.setApplyDeptid(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getApplyUserid())) {
            // 申请人id为空，默认当前登陆人id
            inptConsultationApplyDTO.setApplyUserid(sysUserDTO.getId());
        }
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getApplyUsername())) {
            // 申请人姓名为空，默认当前登陆人姓名
            inptConsultationApplyDTO.setApplyUsername(sysUserDTO.getName());
        }
        // 创建人id
        inptConsultationApplyDTO.setCrteId(sysUserDTO.getId());
        // 创建人姓名
        inptConsultationApplyDTO.setCrteName(sysUserDTO.getName());
        // 创建时间
        inptConsultationApplyDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptConsultationApplyDTO", inptConsultationApplyDTO);
        return inptConsultationApplyServcie_consumer.saveConsultationApply(map);
    }

    /**
     * @Menthod: queryConsultationApply
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @GetMapping("/queryConsultationApply")
    public WrapperResponse<PageDTO> queryConsultationApply(InptConsultationApplyDTO inptConsultationApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptConsultationApplyDTO.setHospCode(sysUserDTO.getHospCode());
        inptConsultationApplyDTO.setApplyDeptid(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptConsultationApplyDTO", inptConsultationApplyDTO);
        return inptConsultationApplyServcie_consumer.queryConsultationApply(map);
    }

    /**
     * @Menthod: getById
     * @Desrciption: 分页查询会诊记录
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:36
     * @Return:
     **/
    @GetMapping("/getById")
    public WrapperResponse<InptConsultationApplyDTO> getById(InptConsultationApplyDTO inptConsultationApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptConsultationApplyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptConsultationApplyDTO", inptConsultationApplyDTO);
        return inptConsultationApplyServcie_consumer.getById(map);
    }

    /**
     * @Menthod: updateStatus
     * @Desrciption: 更改会诊状态
     * @Param: inptConsultationApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-11-04 20:42
     * @Return:
     **/
    @GetMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(InptConsultationApplyDTO inptConsultationApplyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptConsultationApplyDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(inptConsultationApplyDTO.getConsulState())) {
            throw new RuntimeException("未选择操作类型，提交或完成");
        }
        if (Constants.HZZT.WC.equals(inptConsultationApplyDTO.getConsulState())) {
            // 完成人id
            inptConsultationApplyDTO.setFinishUserid(sysUserDTO.getId());
            // 完成人姓名
            inptConsultationApplyDTO.setFinishUsername(sysUserDTO.getName());
            // 完成人时间
            inptConsultationApplyDTO.setFinishTime(DateUtils.getNow());
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptConsultationApplyDTO", inptConsultationApplyDTO);
        return inptConsultationApplyServcie_consumer.updateStatus(map);
    }
}

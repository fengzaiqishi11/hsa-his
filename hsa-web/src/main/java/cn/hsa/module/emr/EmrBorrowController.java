package cn.hsa.module.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;
import cn.hsa.module.emr.emrborrow.service.EmrBorrowService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr
 * @Class_name: EmrBorrowController
 * @Describe: TODO
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date:  2021/08/25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/emr/emrBorrow")
@Slf4j
public class EmrBorrowController extends BaseController {
    @Resource
    private EmrBorrowService emrBorrowService_consumer;

    /**
     * @Method saveEmrBorrowInfo
     * @Desrciption 保存借阅信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/25 17:10
     * @Return Boolean
     **/
    @PostMapping("/saveEmrBorrowInfo")
    public WrapperResponse<Boolean> saveEmrBorrowInfo(@RequestBody EmrBorrowDTO emrBorrowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrBorrowDTO.setCrteName(sysUserDTO.getName());
        emrBorrowDTO.setCrteId(sysUserDTO.getId());
        emrBorrowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("emrBorrowDTO", emrBorrowDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrBorrowService_consumer.insertEmrBorrow(map);
    }

    /**
     * @Method updateEmrBorrowInfo
     * @Desrciption 更新借阅信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/25 17:10
     * @Return Boolean
     **/
    @PostMapping("/updateEmrBorrowInfo")
    public WrapperResponse<Boolean> updateEmrBorrowInfo(@RequestBody EmrBorrowDTO emrBorrowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrBorrowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("emrBorrowDTO", emrBorrowDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrBorrowService_consumer.updateEmrBorrowInfo(map);
    }


    /**
     * @Method queryEmrBorrowInfo
     * @Desrciption 查询借阅信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/25 17:20
     * @Return EmrBorrowDTO
     **/
    @GetMapping("/queryEmrBorrowInfo")
    public WrapperResponse<EmrBorrowDTO> queryEmrBorrowInfo(EmrBorrowDTO emrBorrowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            emrBorrowDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }
        emrBorrowDTO.setHospCode(sysUserDTO.getHospCode());
        return emrBorrowService_consumer.getEmrBorrow(emrBorrowDTO);
    }

    /**
     * @Method queryEmrBorrowInfo
     * @Desrciption 查询借阅信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/25 17:20
     * @Return EmrBorrowDTO
     **/
    @GetMapping("/queryEmrBorrowInfoList")
    public WrapperResponse<PageDTO> queryEmrBorrowInfoList(EmrBorrowDTO emrBorrowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        emrBorrowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("emrBorrowDTO", emrBorrowDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return emrBorrowService_consumer.queryEmrBorrowList(map);
    }


}

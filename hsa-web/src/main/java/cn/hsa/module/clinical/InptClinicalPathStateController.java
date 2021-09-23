package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.service.InptClinicalPathStateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径病人记录控制层
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/9/22 14:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/clinical/inptClinicalPathStateController")
@Slf4j
public class InptClinicalPathStateController extends BaseController {
    @Resource
    private InptClinicalPathStateService inptClinicalPathStateService_consumer;
    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 通过目录id、阶段id查询
     * @Param: [clinicPathStageDetailDTO]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @GetMapping("/queryClinicalPathStageDetail")
    public WrapperResponse<PageDTO> queryClinicalPathStageDetail(InptClinicalPathStateDTO inptClinicalPathStateDTO,
                                                                 HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        inptClinicalPathStateDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptClinicalPathStateDTO",inptClinicalPathStateDTO);
        return inptClinicalPathStateService_consumer.queryClinicalPathStageDetail(map);
    }

}

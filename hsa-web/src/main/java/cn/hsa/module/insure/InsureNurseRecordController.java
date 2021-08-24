package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.service.InsureNurseRecordService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name: InsureNurseRecordController
 * @Describe: 统一支付平台-护理生命记录controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-08-21 17:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@RestController
@RequestMapping("/web/insure/insureNurseRecord")
public class InsureNurseRecordController extends BaseController {

    @Resource
    private InsureNurseRecordService insureNurseRecordService_consumer;

    /**
     * @Menthod: addInsureNurseRecord
     * @Desrciption: 统一支付平台-护理生命记录上传【4602】
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 08:55
     * @Return:
     **/
    @PostMapping("/addInsureNurseRecord")
    public WrapperResponse<Map<String, Object>> addInsureNurseRecord(@RequestBody Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureNurseRecordService_consumer.addInsureNurseRecord(map);
    }
}

package cn.hsa.module.report;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportPrintService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName ReportPrintController
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/2/24 16:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("/web/report/print")
public class ReportPrintController extends BaseController {

    @Autowired
    ReportPrintService reportPrintService_consumer;
    /**
     * @Method querySumDeclareInfoPrints
     * @Desrciption 清算申报合计报表打印
     * @param paraMap
     * @Author liuhuiming
     * @Date   2022/2/21 09:01
     * @Return
     **/
    @GetMapping("/sumDeclareInfoPrints")
    public WrapperResponse<ReportReturnDataDTO> sumDeclareInfoPrints(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        paraMap.put("crteId",sysUserDTO.getId());
        paraMap.put("crteName",sysUserDTO.getName());
        WrapperResponse<ReportReturnDataDTO> resultDTO = reportPrintService_consumer.sumDeclareInfoPrints(paraMap);
        return resultDTO;
    }
}

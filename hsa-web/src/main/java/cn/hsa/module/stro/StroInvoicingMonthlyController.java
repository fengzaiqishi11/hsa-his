package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.stro.stroinvoicing.service.StroInvoicingMonthlyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gory
 * @date 2022/5/10 20:03
 */
@RestController
@RequestMapping("/web/stro/stroInvoicingMonthly")
@Slf4j
public class StroInvoicingMonthlyController extends BaseController {

    @Resource
    private StroInvoicingMonthlyService stroInvoicingMonthlyService_consumer;

    @PostMapping("/copyStroInvoicing")
    public WrapperResponse<Boolean> copyStroInvoicing(StroInvoicingDTO stroInventoryDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDetail.setHospCode(sysUserDTO.getHospCode());
        stroInventoryDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("date", DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D));
        return  stroInvoicingMonthlyService_consumer.insertCopyStroInvoicing(map);
    }

}

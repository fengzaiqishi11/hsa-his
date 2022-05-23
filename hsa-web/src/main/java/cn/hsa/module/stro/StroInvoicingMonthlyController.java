package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;
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
    /**
     * @Author gory
     * @Description 同步进销存
     * @Date 2022/5/11 19:51
     * @Param [stroInventoryDetail, req, res]
     **/
    @PostMapping("/copyStroInvoicing")
    public WrapperResponse<Boolean> copyStroInvoicing(StroInvoicingMonthlyDTO stroInventoryDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDetail.setHospCode(sysUserDTO.getHospCode());
        stroInventoryDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("date", DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D));
        return  stroInvoicingMonthlyService_consumer.insertCopyStroInvoicing(map);
    }
    /**
     * @Author gory
     * @Description 分页查询同步主表信息
     * @Date 2022/5/11 19:55
     * @Param [stroInventoryDTO, req, res]
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(StroInvoicingMonthlyDTO stroInventoryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroInvoicingMonthlyDTO",stroInventoryDTO);
        return stroInvoicingMonthlyService_consumer.queryPage(map);
    }
    /**
     * @Author gory
     * @Description 根据月度主表id查询明细
     * @Date 2022/5/11 20:00
     * @Param [stroInventoryDTO, req, res]
     **/
    @GetMapping("/queryDetailByMonthlyId")
    public WrapperResponse<PageDTO> queryDetailByMonthlyId(StroInvoicingMonthlyDTO stroInventoryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroInvoicingMonthlyDTO",stroInventoryDTO);
        return stroInvoicingMonthlyService_consumer.queryDetailByMonthlyId(map);
    }
}

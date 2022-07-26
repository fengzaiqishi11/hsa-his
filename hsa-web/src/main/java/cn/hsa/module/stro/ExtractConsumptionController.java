package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.dto.ExtractStroInvoicingDetailDTO;
import cn.hsa.module.interf.extract.service.ExtractConsumptionService;
import cn.hsa.module.interf.extract.service.ExtractStroInvoicingService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/12 9:15
 * 消耗报表controller
 */
@RestController
@RequestMapping("/web/stro/extractCon")
@Slf4j
public class ExtractConsumptionController extends BaseController {

    @Resource
    private ExtractConsumptionService extraConsumptionService_consumer;
    /**
     * @Author gory
     * @Description 药房药库消耗报表统计
     * @Date 2022/7/12 9:26
     * @Param [extractConsumptionDTO, req, rep]
     **/
    @GetMapping("/queryExtractConsumptions")
    public WrapperResponse<PageDTO>
    queryExtractConsumptions(ExtractConsumptionDTO extractConsumptionDTO,
                             HttpServletRequest req, HttpServletResponse rep) {
        SysUserDTO userDTO = getSession(req, rep);
        Map map = new HashMap<>();
        map.put("hospCode",userDTO.getHospCode());
        extractConsumptionDTO.setHospCode(userDTO.getHospCode());
        map.put("extractConsumptionDTO",extractConsumptionDTO);
        return extraConsumptionService_consumer.queryExtractConsumptions(map);
    }


    /**
     * @Author pengbo
     * @Description 药房药库实时进销存统计分析
     * @Date 2022/7/15 9:26
     * @Param [extractConsumptionDTO, req, rep]
     **/
    @GetMapping("/queryStroInvoicing")
    public WrapperResponse<PageDTO> queryStroInvoicing(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO,
                             HttpServletRequest req, HttpServletResponse rep) {
        SysUserDTO userDTO = getSession(req, rep);
        Map map = new HashMap<>();
        map.put("hospCode",userDTO.getHospCode());
        extractStroInvoicingDetailDTO.setHospCode(userDTO.getHospCode());
        map.put("extractStroInvoicingDetailDTO",extractStroInvoicingDetailDTO);
        return extraConsumptionService_consumer.extractStroInvoicingDetailDTO(map);
    }

}

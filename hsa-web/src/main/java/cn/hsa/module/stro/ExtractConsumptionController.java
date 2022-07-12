package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.service.ExtractConsumptionService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ExtractConsumptionService extractConsumptionService_consumer;
    /**
     * @Author gory
     * @Description 药房药库消耗报表统计
     * @Date 2022/7/12 9:26
     * @Param [extractConsumptionDTO, req, rep]
     **/
    @GetMapping("/queryExtractConsumptions")
    public WrapperResponse<PageDTO>
    queryExtractConsumptions(@RequestBody ExtractConsumptionDTO extractConsumptionDTO,
                             HttpServletRequest req, HttpServletResponse rep) {
        SysUserDTO userDTO = getSession(req, rep);
        Map map = new HashMap<>();
        map.put("hospCode",userDTO.getHospCode());
        map.put("extractConsumptionDTO",extractConsumptionDTO);
        return extractConsumptionService_consumer.queryExtractConsumptions(map);
    }


}

package cn.hsa.module.stro;


import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.entity.ExtractDataDO;
import cn.hsa.module.interf.extract.service.ExtractStroInvoicingService;

import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/7 9:07
 */
@RestController
@RequestMapping("/web/stro/extract")
@Slf4j
public class ExtractStroInvoicingController {
    @Resource
    ExtractStroInvoicingService extractStroInvoicingService_consumer;
    /**
     * @Author gory
     * @Description
     * @Date 2022/7/7 9:19
     * @Param [request, response]
     **/
    @GetMapping("/insertDataToExtractReport/{hospCode}")
    private WrapperResponse<Boolean> insertDataToExtractReport(@PathVariable String hospCode, HttpServletRequest req, HttpServletResponse res){
        Map map = new HashMap<>();
        map.put("hospCode",hospCode);
        return extractStroInvoicingService_consumer.insertDataToExtractReport(map);
    }
}

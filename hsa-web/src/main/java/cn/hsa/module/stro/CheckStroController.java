package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;
import cn.hsa.module.stro.stock.service.CheckStockService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name: CheckStroController
 * @Describe:
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 20:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/checkStroStock")
@Slf4j
public class CheckStroController extends BaseController {
    @Resource
    private CheckStockService checkStockService_consumer;
    /**
     * @Method checkOutDrugOrMeterialStock
     * @Desrciption 获取剩余可用的库存数量
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/8/9 20:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.math.BigDecimal>
     */

    @GetMapping("/checkOutDrugOrMeterialStock")
    WrapperResponse<CheckStockRespDTO> checkOutDrugOrMeterialStock(@RequestBody OutptPrescribeDetailsDTO outptPrescribeDetailsDTO,
                                                                   HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        outptPrescribeDetailsDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("outptPrescribeDetailsDTO",outptPrescribeDetailsDTO);
        return checkStockService_consumer.checkOutDrugOrMeterialStock(map);
    }
}

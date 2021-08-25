package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.stro.stroinvoicing.service.StroInvoicingService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
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
 * @Package_ame: cn.hsa.module.stro
 * @Class_name: StroInvoicingController
 * @Description:  进销存查询
 * @Author: ljh
 * @Date: 2020/7/30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/stro/stroInvoicing")
@Slf4j
public class StroInvoicingController extends BaseController {
    /**
     * 进销存查询dubbo消费者接口
     */
    @Resource
    private StroInvoicingService stroInvoicingService_consumer;

    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
   @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(StroInvoicingDTO stroInventoryDetail, HttpServletRequest req, HttpServletResponse res) {
       SysUserDTO sysUserDTO = getSession(req, res);
       stroInventoryDetail.setHospCode(sysUserDTO.getHospCode());
       stroInventoryDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
       Map map = new HashMap();
       map.put("hospCode",sysUserDTO.getHospCode());
       map.put("bean",stroInventoryDetail);
        return  stroInvoicingService_consumer.queryPage(map);
    }


    /**
     * @Method queryPage()
     * @Description 出库报表通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPageck")
    public WrapperResponse<PageDTO> queryPagerkck(StroInvoicingDTO stroInventoryDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDetail.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroInventoryDetail);
        return  stroInvoicingService_consumer.queryPageck(map);
    }


    /**
     * @Method queryPage()
     * @Description 入库报表通过实体作为筛选条件查询
     *
     * @Param
     * 1、StroInvoicingDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPagerk")
    public WrapperResponse<PageDTO> queryPagerk(StroInvoicingDTO stroInventoryDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroInventoryDetail.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroInventoryDetail);
        return  stroInvoicingService_consumer.queryPagerk(map);
    }
}

package cn.hsa.stro.stroinvoicing.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stroinvoicing.bo.StroInvoicingBO;
import cn.hsa.module.stro.stroinvoicing.service.StroInvoicingService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.stro.stroinvoicing.service.impl
 * @Class_name:: StroInvoicingServiceImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/8/20 9:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/stro/stroinvoicing")
@Slf4j
@Service("stroInvoicingService_provider")

public class StroInvoicingServiceImpl extends HsafService implements StroInvoicingService {
    @Resource
    private StroInvoicingBO StroInvoicingBO;
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、Map map
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success( StroInvoicingBO.queryPage(MapUtils.get(map,"bean")));
    }
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、Map map
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPagerk", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPagerk(Map map) {
        return WrapperResponse.success( StroInvoicingBO.queryPagerk(MapUtils.get(map,"bean")));
    }
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * 1、Map map
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPageck", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPageck(Map map) {
        return WrapperResponse.success( StroInvoicingBO.queryPageck(MapUtils.get(map,"bean")));
    }

}

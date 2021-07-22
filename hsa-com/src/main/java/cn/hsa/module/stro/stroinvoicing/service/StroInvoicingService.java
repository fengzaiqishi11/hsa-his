package cn.hsa.module.stro.stroinvoicing.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author ljh
 * @date 2020/07/30.
 */
@FeignClient(value = "hsa-stro")
public interface StroInvoicingService {
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

    @GetMapping("/service/stro/StroInvoicing/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
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
    @GetMapping("/service/stro/StroInvoicing/queryPagerk")
    WrapperResponse<PageDTO> queryPagerk(Map map);

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
    @GetMapping("/service/stro/StroInvoicing/queryPageck")
    WrapperResponse<PageDTO> queryPageck(Map map);


}

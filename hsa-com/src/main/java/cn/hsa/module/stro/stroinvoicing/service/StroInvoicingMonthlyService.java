package cn.hsa.module.stro.stroinvoicing.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Class_name: StroInvoicingMonthlyService
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:32
 **/
@FeignClient(value = "hsa-stro")
public interface StroInvoicingMonthlyService {
    /**
     * @Author gory
     * @Description 同步进销存
     * @Date 2022/5/11 20:01
     * @Param [map]
     **/
    @PostMapping("/service/web/stro/stroStock/stroInvoicingMonthly")
    WrapperResponse<Boolean> insertCopyStroInvoicing(Map map);
    /**
     * @Author gory
     * @Description 分页查询月度主表
     * @Date 2022/5/11 20:01
     * @Param [map]
     **/
    @PostMapping("/service/web/stro/stroStock/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Author gory
     * @Description 根据主表id查询明细表
     * @Date 2022/5/11 20:02
     * @Param
     **/
    @PostMapping("/service/web/stro/stroStock/queryDetailByMonthlyId")
    WrapperResponse<PageDTO> queryDetailByMonthlyId(Map map);
}

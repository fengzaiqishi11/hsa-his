package cn.hsa.module.interf.bill.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.bill.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * 电子票据service接口
 * @author liudawen
 * @date 2021/11/9
 */
@FeignClient(value = "his-outpt")
public interface BillService {

    /**
     * 根据查询条件查询门诊票据主体视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryMzZtView")
    WrapperResponse<List<MzpjZtDTO>> queryMzZtView(Map map);

    /**
     * 根据查询条件查询门诊票据费用明细视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryMzFyView")
    WrapperResponse<List<MzpjFyDTO>> queryMzFyView(Map map);

    /**
     * 根据查询条件查询门诊票据医疗明细视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryMzYlView")
    WrapperResponse<List<MzylMxDTO>> queryMzYlView(Map map);

    /**
     * 根据查询条件查询住院票据主体视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryZyZtView")
    WrapperResponse<List<ZypjZtDTO>> queryZyZtView(Map map);

    /**
     * 根据查询条件查询住院票据费用明细视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryZyFyView")
    WrapperResponse<List<ZypjFyDTO>> queryZyFyView(Map map);

    /**
     * 根据查询条件查询住院票据医疗明细视图
     * @param map
     * @return
     */
    @GetMapping("/service/outpt/bill/queryZyYlView")
    WrapperResponse<List<ZyylMxDTO>> queryZyYlView(Map map);
}

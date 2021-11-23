package cn.hsa.interf.bill.controller;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.bill.dto.*;
import cn.hsa.module.interf.bill.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 电子票据上传controller
 * 查询电子票据上传情况信息，手动上传电子票据信息
 * @author liudawen
 * @date 2021/11/9
 */
@RestController
@RequestMapping("/interf/billView")
@Slf4j
public class BillController extends BaseController {

    @Resource
    private BillService billService;


    /**
     * 按条件分页查询门诊票据主体视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:00
     **/
    @PostMapping("/queryMzZtView")
    public WrapperResponse<List<MzpjZtDTO>> queryMzZtView(@RequestBody Map map) {
        return billService.queryMzZtView(map);
    }

    /**
     *  按条件分页查询门诊票据费用明细视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:00
     **/
    @PostMapping("/queryMzFyView")
    public WrapperResponse<List<MzpjFyDTO>> queryMzFyView(@RequestBody Map map) {
        return billService.queryMzFyView(map);
    }

    /**
     * 按条件分页查询门诊票据医疗明细视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:01
     **/
    @PostMapping("/queryMzYlView")
    public WrapperResponse<List<MzylMxDTO>> queryMzYlView(@RequestBody Map map) {
        return billService.queryMzYlView(map);
    }

    /**
     * 按条件分页查询住院主体视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:01
     **/
    @PostMapping("/queryZyZtView")
    public WrapperResponse<List<ZypjZtDTO>> queryZyZtView(@RequestBody Map map) {
        return billService.queryZyZtView(map);
    }


    /**
     * 按条件分页查询住院费用明细视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:02
     **/
    @PostMapping("/queryZyFyView")
    public WrapperResponse<List<ZypjFyDTO>> queryZyFyView(@RequestBody Map map) {
        return billService.queryZyFyView(map);
    }

    /**
     * 按条件分页查询住院医疗明细视图
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2021/11/15 10:02
     **/
    @PostMapping("/queryZyYlView")
    public WrapperResponse<List<ZyylMxDTO>> queryZyYlView(@RequestBody Map map) {
        return billService.queryZyYlView(map);
    }

}

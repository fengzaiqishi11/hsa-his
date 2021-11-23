package cn.hsa.interf.bill.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.bill.bo.BillBO;
import cn.hsa.module.interf.bill.dto.*;
import cn.hsa.module.interf.bill.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 电子票据服务实现类
 * @author liudawen
 * @date 2021/11/9
 */
@HsafRestPath("/service/outpt/bill")
@Slf4j
@Service("billService_provider")
public class BillServiceImpl implements BillService{

    @Resource
    private BillBO billBO;

    @Override
    public WrapperResponse<List<MzpjZtDTO>> queryMzZtView(Map map) {
        return WrapperResponse.success(billBO.queryMzZtView(map));
    }

    @Override
    public WrapperResponse<List<MzpjFyDTO>> queryMzFyView(Map map) {
        return WrapperResponse.success(billBO.queryMzFyView(map));
    }

    @Override
    public WrapperResponse<List<MzylMxDTO>> queryMzYlView(Map map) {
        return WrapperResponse.success(billBO.queryMzYlView(map));
    }

    @Override
    public WrapperResponse<List<ZypjZtDTO>> queryZyZtView(Map map) {
        return WrapperResponse.success(billBO.queryZyZtView(map));
    }

    @Override
    public WrapperResponse<List<ZypjFyDTO>> queryZyFyView(Map map) {
        return WrapperResponse.success(billBO.queryZyFyView(map));
    }

    @Override
    public WrapperResponse<List<ZyylMxDTO>> queryZyYlView(Map map) {
        return WrapperResponse.success(billBO.queryZyYlView(map));
    }
}

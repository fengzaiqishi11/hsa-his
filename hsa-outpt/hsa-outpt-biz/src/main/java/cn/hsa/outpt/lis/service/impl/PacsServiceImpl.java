package cn.hsa.outpt.lis.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.lis.bo.PacsBO;
import cn.hsa.module.outpt.lis.service.PacsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.lis.service.impl
 * @Class_name: PacsServiceImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/pacs")
@Slf4j
@Service("pacsService_provider")
public class PacsServiceImpl implements PacsService {

    @Resource
    PacsBO pacsBO;

    @Override
    public WrapperResponse<Boolean> getPacsDeptList(Map map) {
        pacsBO.getPacsDeptList(map);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Boolean> getPacsDocList(Map map) {
        pacsBO.getPacsDocList(map);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Boolean> getPacsItemList(Map map) {
        pacsBO.getPacsItemDocList(map);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Boolean> savePacsInspectApply(Map map) {
        pacsBO.savePacsInspectApply(map);
        return WrapperResponse.success(true);
    }

    @Override
    public WrapperResponse<Integer> SavePacsInspectCallback(Map map) {
        return WrapperResponse.success( pacsBO.SavePacsInspectCallback(map));
    }
}
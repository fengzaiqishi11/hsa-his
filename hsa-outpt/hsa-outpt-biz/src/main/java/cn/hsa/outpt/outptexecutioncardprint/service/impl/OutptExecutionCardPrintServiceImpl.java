package cn.hsa.outpt.outptexecutioncardprint.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.executioncardprint.bo.OutptExecutionCardPrintBO;
import cn.hsa.module.outpt.executioncardprint.service.OutptExecutionCardPrintService;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptexecutioncardprint.service.impl
 * @Class_name:: OutptExecutionCardPrintServiceImpl
 * @Description: 执行卡打印service层接口实现类（提供给dubbo）
 * @Author: zhangxuan
 * @Date: 2020-08-18 10:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/executionCardPrint")
@Slf4j
@Service("outptExecutionCardPrintService_provider")
public class OutptExecutionCardPrintServiceImpl implements OutptExecutionCardPrintService {
    /**
     * 执行卡打印业务逻辑接口
     */
    @Resource
    OutptExecutionCardPrintBO outptExecutionCardPrintBO;
    /**
     * @Method queryPage
     * @Desrciption  根据条件分页查询执行卡打印数据
     * @Param
     * map
     * @Author zhangxuan
     * @Date   2020-08-18 10:23
     * @Return
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map){
        PageDTO pageDTO = outptExecutionCardPrintBO.queryPage(MapUtils.get(map,"outptInfusionRegisterDTO"));
        return WrapperResponse.success(pageDTO);

    }
    /**
     * @Method update
     * @Desrciption  打印后改变打印状态
     * @Param 
     * 
     * @Author zhangxuan
     * @Date   2020-08-26 13:54 
     * @Return 
    **/
    @Override
    public WrapperResponse<Boolean> update(Map map){
        return WrapperResponse.success(outptExecutionCardPrintBO.update(MapUtils.get(map,"outptInfusionRegisterDTO")));
    }

    @Override
    public WrapperResponse<List<OutptInfusionRegisterDTO>> queryInfusionRegisterList(Map map) {
        List<OutptInfusionRegisterDTO> outptInfusionRegisterDTO = outptExecutionCardPrintBO.queryInfusionRegisterList(MapUtils.get(map,"pharOutReceiveDTO"));
        return WrapperResponse.success(outptInfusionRegisterDTO);
    }
}

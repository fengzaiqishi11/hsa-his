package cn.hsa.outpt.infusionexec.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infusionExec.bo.OutptInfusionExecBO;
import cn.hsa.module.outpt.infusionExec.service.OutptInfusionExecService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.infusionexec.service.impl
 * @Class_name:: OutptInfusionExecServiceImpl
 * @Description: 门诊输液执行service实现类
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/infusionExec")
@Service("outptInfusionExecService_provider")
public class OutptInfusionExecServiceImpl extends HsafService implements OutptInfusionExecService {

    @Resource
    private OutptInfusionExecBO outptInfusionExecBO;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = outptInfusionExecBO.queryPage(MapUtils.get(map, "outptPrescribeDetailsDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod: update()
     * @Desrciption: 输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(outptInfusionExecBO.update(map));
    }

    /**
     * @Menthod: remove()
     * @Desrciption: 取消输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> remove(Map map) {
        return WrapperResponse.success(outptInfusionExecBO.remove(map));
    }

    /**
     * @Menthod: updateExec()
     * @Desrciption: 执行签名(签名 、 取消)
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateExec(Map map) {
        return WrapperResponse.success(outptInfusionExecBO.updateExec(map));
    }
}

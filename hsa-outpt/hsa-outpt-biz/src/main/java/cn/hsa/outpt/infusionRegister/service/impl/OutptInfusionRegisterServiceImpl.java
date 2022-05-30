package cn.hsa.outpt.infusionRegister.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infusionRegister.bo.OutptInfusionRegisterBO;
import cn.hsa.module.outpt.infusionRegister.service.OutptInfusionRegisterService;
import cn.hsa.util.MapUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.infusionRegister.service.impl
 * @Class_name:: OutptInfusionRegisterServiceImpl
 * @Description: 门诊输液登记service实现层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Service("outptInfusionRegisterService_provider")
@Slf4j
@HsafRestPath("/service/outpt/infusionRegister")
public class OutptInfusionRegisterServiceImpl extends HsafService implements OutptInfusionRegisterService {

    @Resource
    private OutptInfusionRegisterBO outptInfusionRegisterBO;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO pageDTO = outptInfusionRegisterBO.queryPage(MapUtils.get(map, "outptInfusionRegisterDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPageByInfu(Map map) {
        PageDTO pageDTO = outptInfusionRegisterBO.queryPageByInfu(MapUtils.get(map, "outptInfusionRegisterDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod: save()
     * @Desrciption: 输液登记
     * @Param: outptVisitDTO 门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(outptInfusionRegisterBO.save(map));
    }

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getByVisitId(Map map) {
        PageDTO pageDTO = outptInfusionRegisterBO.getByVisitId(MapUtils.get(map, "outptVisitDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod: queryCost()
     * @Desrciption: 根据患者visitId分页查询出对应的费用列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCost(Map map) {
        PageDTO pageDTO = outptInfusionRegisterBO.queryCost(MapUtils.get(map, "outptVisitDTO"));
        return WrapperResponse.success(pageDTO);
    }
}

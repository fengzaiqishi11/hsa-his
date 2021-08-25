package cn.hsa.module.outpt.infusionRegister.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infusionregister.service
 * @Class_name:: OutptInfusionRegisterService
 * @Description: 门诊输液登记service接口层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptInfusionRegisterService {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/service/outpt/outptInfusionRegister/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/service/outpt/outptInfusionRegister/queryPageByInfu")
    WrapperResponse<PageDTO> queryPageByInfu(Map map);

    /**
     * @Menthod: save()
     * @Desrciption: 输液登记
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/outpt/outptInfusionRegister/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/service/outpt/outptInfusionRegister/getByVisitId")
    WrapperResponse<PageDTO> getByVisitId(Map map);

    /**
     * @Menthod: queryCost()
     * @Desrciption: 根据患者visitId分页查询出对应的费用列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/service/outpt/outptInfusionRegister/queryCost")
    WrapperResponse<PageDTO> queryCost(Map map);
}

package cn.hsa.module.outpt.outptquery.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptquery.service
 * @class_name: OutptRegisterQueryService
 * @Description: 门诊挂号查询服务层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 21:09
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-outpt")
public interface OutptRegisterQueryService {

    /**
     * @Method: queryPage()
     * @Description: 门诊挂号分页查询
     * @Param: map
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/10 14:38
     * @Return: outptRegisterDTO门诊挂号数据传输对象集合
     */
    @GetMapping("/web/outpt/outptRegisterQuery/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);


}
package cn.hsa.module.outpt.infusionExec.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infusionExec.service
 * @Class_name:: OutptInfusionExecService
 * @Description: 门诊输液执行service接口层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "has-outpt")
public interface OutptInfusionExecService {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/servcie/outpt/outptInfusionExec/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod: update()
     * @Desrciption: 输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/outpt/outptInfusionExec/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Menthod: remove()
     * @Desrciption: 取消输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/outpt/outptInfusionExec/remove")
    WrapperResponse<Boolean> remove(Map map);

    /**
     * @Menthod: updateExec()
     * @Desrciption: 执行签名(签名 、 取消)
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/outpt/outptInfusionExec/updateExec")
    WrapperResponse<Boolean> updateExec(Map map);
}

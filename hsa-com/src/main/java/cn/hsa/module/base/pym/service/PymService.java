package cn.hsa.module.base.pym.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.base.pym.service
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/12/6  16:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface PymService {

    @GetMapping("/service/base/pym/updatePym")
    Boolean updatePym(Map map);
}

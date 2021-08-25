package cn.hsa.module.base.home.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.home.home.service
 *@Class_name: BaseHomeService
 *@Describe: 首页数据查询service
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/10/29 9:06
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-home")
public interface BaseHomeService {

    /**
    * @Method queryHomeShowData
    * @Desrciption 首页数据查询入口
    * @param map
    * @Author liuqi1
    * @Date   2020/10/29 9:30
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @GetMapping("/service/home/BaseHomeService/queryHomeShowData")
    WrapperResponse<Map<String, Object>> queryHomeShowData(Map<String, Object> map);
}

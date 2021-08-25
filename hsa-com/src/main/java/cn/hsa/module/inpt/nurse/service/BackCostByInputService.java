package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.service
 *@Class_name: BackCostByInputService
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 11:35
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface BackCostByInputService {

    /**
    * @Method queryBackCostInfoPage
    * @Desrciption 住院退费费用分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 11:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/inpt/nurse/queryBackCostInfoPage")
    WrapperResponse<PageDTO> queryBackCostInfoPage(Map<String,Object> map);
    /**
     * @Method queryBackCostInfoPage
     * @Desrciption 住院退费费用分页查询
     * @param map 参数
     * @Author luonianxin
     * @Date   2021/06/02
    **/
    WrapperResponse<PageDTO> querySurgeryBackCostInfoPage(Map<String,Object> map);

    /**
    * @Method saveBackCostWithInpt
    * @Desrciption 住院退费保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 14:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/nurse/saveBackCostWithInpt")
    WrapperResponse<Boolean> saveBackCostWithInpt(Map<String,Object> map);
}

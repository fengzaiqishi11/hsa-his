package cn.hsa.module.inpt.nurse.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.service
 *@Class_name: BackCostSureWithInptService
 *@Describe: 退费确认
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 16:04
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface BackCostSureByInptService {

    /**
    * @Method queryBackCostSurePage
    * @Desrciption 退费确认分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 16:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/nurse/queryBackCostSurePage")
    WrapperResponse<PageDTO> queryBackCostSurePage(Map<String,Object> map);

    /**
    * @Method updateBackCostSure
    * @Desrciption 退费确认
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 16:06
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/nurse/updateBackCostSure")
    WrapperResponse<Boolean> updateBackCostSure(Map<String,Object> map);

    /**
     * 查询手术补记账全部类型的病人补记账记录
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/31 20:00
     **/
    WrapperResponse<PageDTO> queryOutpatientSurgeryCostPage(Map<String,Object> map);
}

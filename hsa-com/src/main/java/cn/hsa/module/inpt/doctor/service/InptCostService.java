package cn.hsa.module.inpt.doctor.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.doctor.service
 *@Class_name: InptCostService
 *@Describe: 住院退药
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/15 9:52
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptCostService {

    /**
    * @Method updateInptCostBatchWithBackDrug
    * @Desrciption 住院退药批量更新
    * @param map
    * @Author liuqi1
    * @Date   2020/9/15 9:53
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/service/inpt/doctor/updateInptCostBatchWithBackDrug")
    WrapperResponse<Boolean> updateInptCostBatchWithBackDrug(Map<String,Object> map);

    /**
    * @Method insertInptCost
    * @Param [map]  新增住院费用
    * @description
    * @author marong
    * @date 2020/9/19 13:58
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @GetMapping("/service/inpt/doctor/insertInptCost")
    WrapperResponse<Boolean> insertInptCost(Map map);

    /**
    * @Method updateInptCostList
    * @Param [map]  住院费用修改
    * @description
    * @author marong
    * @date 2020/9/21 14:23
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @GetMapping("/service/inpt/doctor/updateInptCost")
    WrapperResponse<Boolean> updateInptCostList(Map map);
}
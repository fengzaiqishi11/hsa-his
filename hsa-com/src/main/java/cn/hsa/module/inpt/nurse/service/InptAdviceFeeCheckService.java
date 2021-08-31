package cn.hsa.module.inpt.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.service
 *@Class_name: inptAdviceFeeCheckService
 *@Describe: 医嘱费用核对Service接口层
 *@Author: LiaoJiGuang
 *@Eamil: jiguang.liao@powersi.com.cn
 *@Date: 2020/9/15 10:09
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptAdviceFeeCheckService {

    /**
     * @Method queryInptAdviceCheckPage
     * @Desrciption 医嘱核对分页查询
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:10
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping("/service/inpt/nurse/queryInptAdviceCheckPage")
    WrapperResponse<PageDTO> queryInptAdviceCheckPage(Map<String,Object> map);

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱核对 - 获取本科室住院在院人员的患者信息
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/15 14:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/nurse/queryInptPatientPage")
    WrapperResponse<PageDTO> queryInptPatientPage(Map<String, Object> map);

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PutMapping("/service/inpt/nurse/updateAdviceFeeCheck")
    WrapperResponse<Boolean> updateAdviceFeeCheck(Map map);

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param map
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/nurse/queryInptAdviceFeeInfoPage")
    WrapperResponse<PageDTO> queryInptAdviceFeeInfoPage(Map map);
}

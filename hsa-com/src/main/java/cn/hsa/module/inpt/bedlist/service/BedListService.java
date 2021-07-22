package cn.hsa.module.inpt.bedlist.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.bedlist.server
 * @Class_name: BedListServer
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 15:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface BedListService {

    /**
     * @Method queryPage
     * @Desrciption 分页查询床位信息
     * @params [map]
     * @Author chenjun
     * @Date 2020/9/8 15:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/inpt/bedlist/queryPage")
    WrapperResponse<Map<String, Object>> queryPage(Map map);

    /**
     * @Method 床位变动接口
     * @Description
     * 1、安床 = '0'
     * 2、换床 = '1'
     * 3、包床 = '2'
     * 4、转科 = '3'
     * 5、包床取消 = '4'
     * 6、预出院 = '5'
     * 7、出院召回/召回费用 = '6'
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 11:03
     * @Return
     **/
    @PostMapping("/service/inpt/bedlist/saveBedChange")
    WrapperResponse<Boolean> saveBedChange(Map map);

    /**
     * @Method 根据病区查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 9:43
     * @Return
     **/
    @GetMapping("/service/inpt/bedlist/queryDeptByWardId")
    WrapperResponse<Map<String, Object>> queryDeptByWardId(Map map);
}

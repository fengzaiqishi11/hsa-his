package cn.hsa.module.outpt.daily.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.daily.dto.OutinDailyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.daily.service
 * @Class_name: OutinDaliyService
 * @Description: 日结缴款Service层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface OutinDailyService {
    /**
     * 新增数据
     *
     * @param map
     * @return 实例对象
     */
    @PostMapping("/service/outpt/daily/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * 确认缴款
     *
     * @param map
     * @return 实例对象
     */
    @PostMapping("/service/outpt/daily/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * 取消缴款
     *
     * @param map
     * @return 实例对象
     */
    @PostMapping("/service/outpt/daily/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Method 分页查询日结缴款主表
     * @Description 
     * 
     * @Param 
     *
     * @Author zhongming
     * @Date 2020/9/24 11:58
     * @Return 
     **/
    @GetMapping("/service/outpt/daily/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method 获取最后一次缴款信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/service/outpt/daily/getLastDaily")
    WrapperResponse<OutinDailyDTO> getLastDaily(Map map);

    /**
     * @Method 日结缴款 - 缴款报表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/service/outpt/daily/getOutinDaily")
    WrapperResponse<Map<String, Object>> getOutinDaily(Map map);

    /**
     * @Method 日结缴款 - 结算明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @GetMapping("/service/outpt/daily/querySettle")
    WrapperResponse<PageDTO> querySettle(Map map);

    /**
     * @Method 日结缴款 - 预交金明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @GetMapping("/service/outpt/daily/queryAdvancePay")
    WrapperResponse<PageDTO> queryAdvancePay(Map map);

    /**
     * @Method 日结缴款 - 预交金冲抵明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/30 16:33
     * @Return
     **/
    @GetMapping("/service/outpt/daily/queryAdvancePayCd")
    WrapperResponse<PageDTO> queryAdvancePayCd(Map map);
}

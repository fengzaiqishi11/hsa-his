package cn.hsa.module.base.bor.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_ame: cn.hsa.base.bfc.service
 * @Class_name: BaseFinanceClassifyService
 * @Description: 单据生成规则业务服务层接口
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-base")
public interface BaseOrderRuleService {
    /**
     * @Method getById()
     * @Description 根据主键ID查询单据生成规则信息
     *
     * @Param
     * 1、id：单据生成规则信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseOrderRuleDTO>
     **/
    @GetMapping("/service/base/bor/getById")
    WrapperResponse<BaseOrderRuleDTO> getById(Map map);

    /**
     * @Method queryPage()
     * @Description 分页查询单据生成规则信息
     *
     * @Param
     * 1、baseOrderRuleDTO：单据生成规则数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/base/bor/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method insert()
     * @Description 新增单条单据生成规则信息
     *
     * @Param
     * 1、baseOrderRuleDTO：单据生成规则数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/bor/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Method update()
     * @Description 新增单条单据生成规则信息
     *
     * @Param
     * 1、baseOrderRuleDTO：单据生成规则数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/bor/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     *
     * @Param
     * 1、id：单据生成规则信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/bor/delete")
    WrapperResponse<Boolean> delete(Map map);

    /**
     * @Method 根据医院编码、单据类型获取下一个单据号
     * @Description
     *
     * @Param
     * 1、hospCode：医院编码
     * 2、typeCode：单据类型
     *
     * @Author zhongming
     * @Date 2020/7/13 21:23
     * @Return 下一个单据号
     **/
    @GetMapping("/service/base/bor/getOrderNo")
    WrapperResponse<String> getOrderNo(Map map);

}
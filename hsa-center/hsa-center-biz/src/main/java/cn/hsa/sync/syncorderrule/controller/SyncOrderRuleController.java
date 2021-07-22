package cn.hsa.sync.syncorderrule.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncorderrule.dto.SyncOrderRuleDTO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Package_ame: cn.hsa.web.base
 * @Class_name: BaseOrderRuleController
 * @Description: 单据生成规则控制层
 * @Author: ljh
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/sor")
@Slf4j
public class SyncOrderRuleController extends CenterBaseController {
    /**
     * 单据生成规则dubbo消费者接口
     */
    @Resource
    private SyncOrderRuleService syncOrderRuleService_consumer;

    /**
     * @Method getById()
     * @Description 根据主键ID查询
     * @Param 1、id：主键ID
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<syncOrderRuleDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SyncOrderRuleDTO> getById(String id) {
        return syncOrderRuleService_consumer.getById(id);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param 1、syncOrderRuleDTO：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SyncOrderRuleDTO syncOrderRuleDTO) {

        return syncOrderRuleService_consumer.queryPage(syncOrderRuleDTO);
    }

    /**
     * @Method insert()
     * @Description 新增单条
     * @Param 1、syncOrderRuleDTO：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody SyncOrderRuleDTO syncOrderRuleDTO) {
        syncOrderRuleDTO.setCrteId(userId);
        syncOrderRuleDTO.setCrteName(userName);
        return syncOrderRuleService_consumer.insert(syncOrderRuleDTO);

    }

    /**
     * @Method update()
     * @Description 修改单条
     * @Param 1、syncOrderRuleDTO：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody SyncOrderRuleDTO syncOrderRuleDTO) {

        return syncOrderRuleService_consumer.update(syncOrderRuleDTO);
    }

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     * @Param 1、ids：主键ID集合
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestParam String ids) {
        List idsa = Arrays.asList(ids.split(","));
        return syncOrderRuleService_consumer.delete(idsa);
    }
}

package cn.hsa.sync.syncorderrule.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncorderrule.bo.SyncOrderRuleBO;
import cn.hsa.module.center.syncorderrule.dto.SyncOrderRuleDTO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@HsafRestPath("/service/base/bor")
@Slf4j
@Service("syncOrderRuleService_provider")
public class SyncOrderRuleServiceImpl implements SyncOrderRuleService {

    @Resource
    private SyncOrderRuleBO syncOrderRuleBO;

    /**
     * @param id
     * @Method getById()
     * @Description 根据主键ID查询单据生成规则信息
     * @Param 1、id：单据生成规则信息表主键ID
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseOrderRuleDTO>
     */
    @Override
    public WrapperResponse<SyncOrderRuleDTO> getById(String id) {

        return WrapperResponse.success(syncOrderRuleBO.getById(id));
    }

    /**
     * @param syncOrderRuleDTO
     * @Method queryPage()
     * @Description 分页查询单据生成规则信息
     * @Param 1、baseOrderRuleDTO：单据生成规则数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(SyncOrderRuleDTO syncOrderRuleDTO) {
        return WrapperResponse.success(syncOrderRuleBO.queryPage(syncOrderRuleDTO));
    }

    /**
     * @param syncOrderRuleDTO
     * @Method insert()
     * @Description 新增单条单据生成规则信息
     * @Param 1、baseOrderRuleDTO：单据生成规则数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     */
    @Override
    public WrapperResponse<Boolean> insert(SyncOrderRuleDTO syncOrderRuleDTO) {
        return WrapperResponse.success(syncOrderRuleBO.insert(syncOrderRuleDTO));
    }

    /**
     * @param syncOrderRuleDTO
     * @Method update()
     * @Description 新增单条单据生成规则信息
     * @Param 1、baseOrderRuleDTO：单据生成规则数据参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     */
    @Override
    public WrapperResponse<Boolean> update(SyncOrderRuleDTO syncOrderRuleDTO) {
        return WrapperResponse.success(syncOrderRuleBO.update(syncOrderRuleDTO));
    }

    /**
     * @param idsa
     * @Method delete()
     * @Description 单个或者批量删除
     * @Param 1、id：单据生成规则信息表主键ID
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     */
    @Override
    public WrapperResponse<Boolean> delete(List idsa) {
        return WrapperResponse.success(syncOrderRuleBO.delete(idsa));
    }

    /**
     * @param typeCode
     * @Method 根据医院编码、单据类型获取下一个单据号
     * @Description
     * @Param 1、hospCode：医院编码
     * 2、typeCode：单据类型
     * @Author ljh
     * @Date 2020/7/13 21:23
     * @Return 下一个单据号
     */
    @Override
    public WrapperResponse<String> getOrderNo(String typeCode) {
        return WrapperResponse.success(syncOrderRuleBO.updateOrderNo(typeCode));
    }
}

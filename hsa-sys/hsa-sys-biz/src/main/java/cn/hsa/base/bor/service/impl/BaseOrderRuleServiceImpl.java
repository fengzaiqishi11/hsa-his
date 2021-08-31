package cn.hsa.base.bor.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.bo.BaseOrderRuleBO;
import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bor.service.impl
 * @Class_name: BaseOrderRuleApiImpl
 * @Description: 单据生成规则API接口实现层（提供给dubbo调用）
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/07/13 20:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/base/bor")
@Slf4j
@Service("baseOrderRuleService_provider")
public class BaseOrderRuleServiceImpl extends HsafService implements BaseOrderRuleService {
    /**
     * 单据生成规则业务逻辑接口
     */
    @Resource
    private BaseOrderRuleBO baseOrderRuleBO;

    /**
     * @Method getById()
     * @Description 根据主键ID
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseOrderRuleDTO>
     **/
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    @Override
    public WrapperResponse<BaseOrderRuleDTO> getById(Map map) {
        return WrapperResponse.success(baseOrderRuleBO.getById(MapUtils.get(map, "id")));
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseOrderRuleBO.queryPage(MapUtils.get(map, "baseOrderRuleDTO")));
    }

    /**
     * @Method insert()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(baseOrderRuleBO.insert(MapUtils.get(map, "baseOrderRuleDTO")));
    }

    /**
     * @Method update()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(baseOrderRuleBO.update(MapUtils.get(map, "baseOrderRuleDTO")));
    }

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     *
     * @Param
     * 1、ids：主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @HsafRestPath(value = "/delete", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(baseOrderRuleBO.delete(MapUtils.get(map, "ids")));
    }

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
    @HsafRestPath(value = "/getOrderNo", method = RequestMethod.POST)
    @Override
    public WrapperResponse<String> getOrderNo(Map map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String typeCode = MapUtils.get(map, "typeCode");
        // 参数校验
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(typeCode)) {
            throw new AppException("必填参数为空");
        }
        return WrapperResponse.success(baseOrderRuleBO.updateOrderNo(hospCode, typeCode));
    }
}

package cn.hsa.module.base.bor.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bor.bo
 * @Class_name: BaseOrderRuleBO
 * @Description: 单据生成规则业务逻辑实现层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseOrderRuleBO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return baseOrderRuleDTO
     **/
    BaseOrderRuleDTO getById(Long id);

    /**
     * @Method queryPage()
     * @Description 分页查询
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    PageDTO queryPage(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method insert()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return true|false
     **/
    boolean insert(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method update()
     * @Description 修改单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return true|false
     **/
    boolean update(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     *
     * @Param
     * 1、ids：主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return boolean
     **/
    boolean delete(List ids);

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
    String updateOrderNo(String hospCode, String typeCode);
}

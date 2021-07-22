package cn.hsa.module.center.syncorderrule.dao;

import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import cn.hsa.module.center.syncorderrule.dto.SyncOrderRuleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bor.dao
 * @Class_name: BaseOrderRuleDAO
 * @Description: 单据生成规则数据访问层接口
 * @Author: ljh
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SyncOrderRuleDAO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询
     * @Param 1、id：主键ID
     * @Author ljh
     * @Date 2020/7/1 20:53
     * @Return BaseOrderRuleDTO
     **/
    SyncOrderRuleDTO getById(String id);

    /**
     * @Method queryAll()
     * @Description 查询所有
     * @Param 1、baseOrderRule：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return List<BaseOrderRuleDTO>
     **/
    List<BaseOrderRuleDTO> queryAll(SyncOrderRuleDTO syncOrderRuleDTO);

    /**
     * @Method queryAll()
     * @Description 查询所有
     * @Param 1、baseOrderRule：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return List<BaseOrderRuleDTO>
     **/
    List<BaseOrderRuleDTO> queryAllS(SyncOrderRuleDTO syncOrderRuleDTO);

    /**
     * @Method insert()
     * @Description 新增单条
     * @Param 1、baseOrderRule：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int insert(SyncOrderRuleDTO syncOrderRuleDTO);

    /**
     * @Method update()
     * @Description 修改单条
     * @Param 1、baseOrderRule：参数对象
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int update(SyncOrderRuleDTO syncOrderRuleDTO);

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     * @Param 1、ids：主键ID集合
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int delete(@Param("ids") List ids);

    /**
     * @Method 根据医院编码、单据类型查询单
     * @Description
     * @Param 1、hospCode：医院编码
     * 2、typeCode：单据类型
     * @Author ljh
     * @Date 2020/7/13 21:28
     * @Return
     **/
    SyncOrderRuleDTO getByHcAndTc(@Param("typeCode") String typeCode);

    /**
     * @Method 根据医院编码、单据类型查询单（行锁）
     * @Description
     * @Param 1、hospCode：医院编码
     * 2、typeCode：单据类型
     * @Author ljh
     * @Date 2020/7/13 21:28
     * @Return
     **/
    SyncOrderRuleDTO getByRowLock(@Param("typeCode") String typeCode);

    /**
     * @Method 根据主键ID，修改最新单据号
     * @Description
     * @Param
     * @Author ljh
     * @Date 2020/7/13 23:17
     * @Return
     **/
    int updateCurrNo(@Param("id") String id, @Param("orderNo") String orderNo);
}
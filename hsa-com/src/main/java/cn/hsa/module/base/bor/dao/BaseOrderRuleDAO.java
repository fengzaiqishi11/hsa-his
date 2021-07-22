package cn.hsa.module.base.bor.dao;

import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bor.dao
 * @Class_name: BaseOrderRuleDAO
 * @Description: 单据生成规则数据访问层接口
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseOrderRuleDAO {
    /**
     * @Method getById()
     * @Description 根据主键ID查询
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return BaseOrderRuleDTO
     **/
    BaseOrderRuleDTO getById(Long id);

    /**
     * @Method queryAll()
     * @Description 查询所有
     *
     * @Param
     * 1、baseOrderRule：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return List<BaseOrderRuleDTO>
     **/
    List<BaseOrderRuleDTO> queryAll(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method insert()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRule：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int insert(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method update()
     * @Description 修改单条
     *
     * @Param
     * 1、baseOrderRule：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int update(BaseOrderRuleDTO baseOrderRuleDTO);

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     *
     * @Param
     * 1、ids：主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return int
     **/
    int delete(@Param("ids") List ids);

    /**
     * @Method 根据医院编码、单据类型查询单
     * @Description
     *
     * @Param
     * 1、hospCode：医院编码
     * 2、typeCode：单据类型
     *
     * @Author zhongming
     * @Date 2020/7/13 21:28
     * @Return
     **/
    BaseOrderRuleDTO getByHcAndTc(@Param("hospCode") String hospCode, @Param("typeCode") String typeCode);

    /**
     * @Method 根据医院编码、单据类型查询单（行锁）
     * @Description
     *
     * @Param
     * 1、hospCode：医院编码
     * 2、typeCode：单据类型
     *
     * @Author zhongming
     * @Date 2020/7/13 21:28
     * @Return
     **/
    BaseOrderRuleDTO getByRowLock(@Param("hospCode") String hospCode, @Param("typeCode") String typeCode);

    /**
     * @Method 根据主键ID，修改最新单据号
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/13 23:17
     * @Return 
     **/
    int updateCurrNo(@Param("id") String id, @Param("orderNo") String orderNo);
}